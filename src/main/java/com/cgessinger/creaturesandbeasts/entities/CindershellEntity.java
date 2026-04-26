package com.cgessinger.creaturesandbeasts.entities;

import com.cgessinger.creaturesandbeasts.init.CNBEntityTypes;
import com.cgessinger.creaturesandbeasts.init.CNBItems;
import com.cgessinger.creaturesandbeasts.init.CNBSoundEvents;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.keyframe.event.SoundKeyframeEvent;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;

import static com.cgessinger.creaturesandbeasts.init.CNBTags.Items.CINDERSHELL_FOOD;

// TODO[1.21.1 port]: re-implement the "carry a furnace" feature.
// Recipe API rewrite needs: RecipeHolder<SmeltingRecipe>, SingleRecipeInput input, RecipeManager.getRecipeFor returning Optional<RecipeHolder<R>>,
// MenuProvider/Container interfaces, plus the inventory/cooking-progress sync. Stripped here for compile parity.
public class CindershellEntity extends Animal implements GeoAnimatable, Bucketable {
    private static final EntityDataAccessor<Boolean> EATING = SynchedEntityData.defineId(CindershellEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> FROM_BUCKET = SynchedEntityData.defineId(CindershellEntity.class, EntityDataSerializers.BOOLEAN);

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private int eatTimer;

    public CindershellEntity(EntityType<CindershellEntity> type, Level worldIn) {
        super(type, worldIn);
        this.eatTimer = 0;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 80.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.1D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 100D);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(EATING, false);
        builder.define(FROM_BUCKET, false);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("FromBucket", this.fromBucket());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.setFromBucket(tag.getBoolean("FromBucket"));
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new CindershellFloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
        this.goalSelector.addGoal(2, new CindershellBreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.0D, Ingredient.of(CINDERSHELL_FOOD), false));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.getEating()) {
            this.navigation.stop();
            this.eatTimer--;
        }
        if (this.eatTimer == 10) {
            this.setHolding(ItemStack.EMPTY);
        } else if (this.eatTimer == 0) {
            this.setEating(false);
        }
    }

    @Override
    public float getWalkTargetValue(BlockPos pos, LevelReader level) {
        return 10.0F;
    }

    @Override
    public boolean isSensitiveToWater() {
        return true;
    }

    public static boolean checkCindershellSpawnRules(EntityType<CindershellEntity> entity, LevelAccessor level, MobSpawnType mobSpawnType, BlockPos pos, RandomSource random) {
        return pos.getY() <= 50;
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return Ingredient.of(CINDERSHELL_FOOD).test(stack);
    }

    public InteractionResult tryStartEat(Player player, ItemStack stack) {
        if (stack.is(CINDERSHELL_FOOD)) {
            int i = this.getAge();
            if (!this.level().isClientSide && i == 0 && this.canFallInLove()) {
                this.usePlayerItem(player, player.getUsedItemHand(), stack);
                this.setEating(true);
                this.setInLove(player);
                this.playSound(CNBSoundEvents.CINDERSHELL_ADULT_EAT.get(), 1.2F, 1F);
                this.setHolding(stack);
                return InteractionResult.SUCCESS;
            }
            if (this.isBaby()) {
                this.playSound(CNBSoundEvents.CINDERSHELL_BABY_EAT.get(), 1.3F, 1F);
                this.usePlayerItem(player, player.getUsedItemHand(), stack);
                this.ageUp((int) (-i / 20F * 0.1F), true);
                return InteractionResult.sidedSuccess(this.level().isClientSide);
            }
            if (this.level().isClientSide) {
                return InteractionResult.CONSUME;
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack item = player.getItemInHand(hand);

        if (item.is(Items.LAVA_BUCKET) && this.isAlive() && this.isBaby()) {
            this.playSound(this.getPickupSound(), 1.0F, 1.0F);
            ItemStack bucketItem = this.getBucketItemStack();
            this.saveToBucketTag(bucketItem);
            ItemStack bucketWithData = ItemUtils.createFilledResult(item, player, bucketItem, false);
            player.setItemInHand(hand, bucketWithData);
            Level level = this.level();
            if (!level.isClientSide) {
                CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayer) player, bucketItem);
            }
            this.discard();
            return InteractionResult.sidedSuccess(level.isClientSide);
        } else if (this.isFood(item) && !this.getEating()) {
            return this.tryStartEat(player, item);
        }
        return InteractionResult.PASS;
    }

    @Override
    public boolean fromBucket() {
        return this.entityData.get(FROM_BUCKET);
    }

    @Override
    public void setFromBucket(boolean fromBucket) {
        this.entityData.set(FROM_BUCKET, fromBucket);
    }

    @Override
    public void saveToBucketTag(ItemStack stack) {
        Bucketable.saveDefaultDataToBucketTag(this, stack);
    }

    @Override
    public void loadFromBucketTag(CompoundTag compound) {
        Bucketable.loadDefaultDataFromBucketTag(this, compound);
    }

    @Override
    public ItemStack getBucketItemStack() {
        return new ItemStack(CNBItems.CINDERSHELL_BUCKET.get());
    }

    @Override
    public SoundEvent getPickupSound() {
        return SoundEvents.BUCKET_FILL_LAVA;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob mob) {
        return CNBEntityTypes.CINDERSHELL.get().create(level);
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }

    @Override
    public boolean causeFallDamage(float distance, float damageMultiplier, DamageSource source) {
        return false;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return CNBSoundEvents.CINDERSHELL_AMBIENT.get();
    }

    @Override
    public int getAmbientSoundInterval() {
        return 120;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return CNBSoundEvents.CINDERSHELL_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return CNBSoundEvents.CINDERSHELL_HURT.get();
    }

    @Override
    protected float getSoundVolume() {
        return super.getSoundVolume() * 2;
    }

    @Override
    public int getMaxHeadYRot() {
        return 50;
    }

    @Override
    public int getMaxHeadXRot() {
        return 25;
    }

    public ItemStack getHolding() {
        return this.getItemBySlot(EquipmentSlot.MAINHAND);
    }

    public void setHolding(ItemStack stack) {
        this.setItemSlot(EquipmentSlot.MAINHAND, stack);
    }

    public void setEating(boolean isEating) {
        this.eatTimer = isEating ? 40 : 0;
        this.entityData.set(EATING, isEating);
    }

    public boolean getEating() {
        return this.entityData.get(EATING);
    }

    public boolean hasFurnace() {
        return false; // TODO[1.21.1 port]: restore furnace feature
    }

    public void awardUsedRecipesAndPopExperience(ServerPlayer player) {
        // TODO[1.21.1 port]: restore furnace feature
    }

    @Override
    public void tick() {
        super.tick();
        if (this.hasFurnace() && this.random.nextDouble() <= 0.25) {
            this.level().addParticle(ParticleTypes.LARGE_SMOKE,
                    this.getX() + (this.random.nextDouble() * 0.5D - 0.25),
                    this.getY() + 2.5 + (this.random.nextDouble() * 0.1D - 0.05),
                    this.getZ() + (this.random.nextDouble() * 0.5D - 0.25),
                    this.getDeltaMovement().x, 0, this.getDeltaMovement().z);
        }
    }

    private <E extends GeoAnimatable> PlayState animationPredicate(AnimationState<E> state) {
        if (!(walkAnimation.speed() > -0.05F && walkAnimation.speed() < 0.05F)) {
            state.getController().setAnimation(RawAnimation.begin().thenLoop(this.isBaby() ? "baby_cindershell_walk" : "cindershell_walk"));
        } else if (this.getEating()) {
            state.getController().setAnimation(RawAnimation.begin().thenLoop("cindershell_idle_eat"));
        } else if (this.isDeadOrDying()) {
            state.getController().setAnimation(RawAnimation.begin().thenPlay("cindershell_death"));
        } else {
            state.getController().setAnimation(RawAnimation.begin().thenLoop("cindershell_idle"));
        }
        return PlayState.CONTINUE;
    }

    private <E extends GeoAnimatable> PlayState eatAnimationPredicate(AnimationState<E> state) {
        if (this.getEating()) {
            state.getController().setAnimation(RawAnimation.begin().thenPlay("cindershell_eat"));
            return PlayState.CONTINUE;
        }
        state.getController().forceAnimationReset();
        return PlayState.STOP;
    }

    private <E extends GeoAnimatable> void soundListener(SoundKeyframeEvent<E> event) {
        String sound = event.getKeyframeData().getSound();
        if (sound.equals("cindershell_eat")) {
            LocalPlayer player = Minecraft.getInstance().player;
            if (player != null) {
                player.playSound(this.isBaby() ? CNBSoundEvents.CINDERSHELL_BABY_EAT.get() : CNBSoundEvents.CINDERSHELL_ADULT_EAT.get(), 0.4F, 1F);
            }
        }
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        AnimationController<CindershellEntity> mainController = new AnimationController<>(this, "controller", 0, this::animationPredicate);
        AnimationController<CindershellEntity> eatController = new AnimationController<>(this, "eatController", 0, this::eatAnimationPredicate);
        eatController.setSoundKeyframeHandler(this::soundListener);
        controllers.add(mainController);
        controllers.add(eatController);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public double getTick(Object animatable) {
        return this.tickCount;
    }

    static class CindershellFloatGoal extends FloatGoal {
        private final CindershellEntity cindershell;

        public CindershellFloatGoal(CindershellEntity cindershell) {
            super(cindershell);
            this.cindershell = cindershell;
        }

        @Override
        public boolean canUse() {
            return this.cindershell.isInLava();
        }
    }

    static class CindershellBreedGoal extends BreedGoal {
        public CindershellBreedGoal(Animal cindershell, double speedModifier) {
            super(cindershell, speedModifier);
        }

        @Override
        protected void breed() {
            int range = this.animal.getRandom().nextInt(4) + 3;
            for (int i = 0; i <= range; i++) {
                super.breed();
            }
        }
    }
}
