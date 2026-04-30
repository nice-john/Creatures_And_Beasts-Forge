package com.cgessinger.creaturesandbeasts.entities;

import com.cgessinger.creaturesandbeasts.containers.CinderFurnaceContainer;
import com.cgessinger.creaturesandbeasts.init.CNBEntityTypes;
import com.cgessinger.creaturesandbeasts.init.CNBItems;
import com.cgessinger.creaturesandbeasts.init.CNBSoundEvents;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

import org.jetbrains.annotations.Nullable;

import static com.cgessinger.creaturesandbeasts.init.CNBTags.Items.CINDERSHELL_FOOD;

public class CindershellEntity extends Animal implements GeoAnimatable, Bucketable, Container, MenuProvider {
    private static final EntityDataAccessor<Boolean> EATING = SynchedEntityData.defineId(CindershellEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> FROM_BUCKET = SynchedEntityData.defineId(CindershellEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> FURNACE = SynchedEntityData.defineId(CindershellEntity.class, EntityDataSerializers.BOOLEAN);

    private static final net.minecraft.resources.ResourceLocation BABY_HEALTH_REDUCTION_ID =
            net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("cnb", "cindershell_baby_health_reduction");
    private static final float BABY_HEALTH = 10.0F;
    private static final EntityDimensions SLEEPING_DIMENSIONS = EntityDimensions.fixed(0.4F, 0.4F);

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private int eatTimer;

    // 2-slot inventory: input + result. Cooking machinery is still TODO[1.21.1 port],
    // but the menu must be openable so the player can put items in the slots.
    private final NonNullList<ItemStack> items = NonNullList.withSize(2, ItemStack.EMPTY);
    private int cookingProgress;
    private int cookingTotalTime;
    private final ContainerData dataAccess = new SimpleContainerData(2) {
        @Override public int get(int i) { return i == 0 ? cookingProgress : i == 1 ? cookingTotalTime : 0; }
        @Override public void set(int i, int v) { if (i == 0) cookingProgress = v; else if (i == 1) cookingTotalTime = v; }
    };
    private final Object2IntOpenHashMap<ResourceLocation> recipesUsed = new Object2IntOpenHashMap<>();

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
        builder.define(FURNACE, false);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("FromBucket", this.fromBucket());
        tag.putBoolean("HasFurnace", this.hasFurnace());
        ContainerHelper.saveAllItems(tag, this.items, this.registryAccess());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.setFromBucket(tag.getBoolean("FromBucket"));
        this.setFurnace(tag.getBoolean("HasFurnace"));
        this.items.clear();
        ContainerHelper.loadAllItems(tag, this.items, this.registryAccess());
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
                this.playSound(CNBSoundEvents.CINDERSHELL_ADULT_EAT, 1.2F, 1F);
                this.setHolding(stack);
                return InteractionResult.SUCCESS;
            }
            if (this.isBaby()) {
                this.playSound(CNBSoundEvents.CINDERSHELL_BABY_EAT, 1.3F, 1F);
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
        } else if (!this.isBaby() && !this.hasFurnace() && item.is(com.cgessinger.creaturesandbeasts.init.CNBItems.CINDERSHELL_FURNACE)) {
            this.setFurnace(true);
            if (!player.getAbilities().instabuild) {
                item.shrink(1);
            }
            this.playSound(SoundEvents.HORSE_SADDLE, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        } else if (this.hasFurnace() && player.isSecondaryUseActive()) {
            // Shift-right-click while furnace is mounted: drop it back (along with any contents).
            if (!this.level().isClientSide) {
                this.spawnAtLocation(com.cgessinger.creaturesandbeasts.init.CNBBlocks.CINDER_FURNACE);
                for (int i = 0; i < this.items.size(); i++) {
                    ItemStack stack = this.items.get(i);
                    if (!stack.isEmpty()) this.spawnAtLocation(stack);
                }
                this.clearContent();
                this.setFurnace(false);
                this.playSound(SoundEvents.HORSE_SADDLE, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 0.8F);
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        } else if (this.hasFurnace()) {
            if (!this.level().isClientSide && player instanceof ServerPlayer serverPlayer) {
                // Fabric: vanilla ServerPlayer.openMenu(MenuProvider). Forge/NeoForge added a
                // 2-arg overload taking a buf consumer for extended menu data — we don't
                // pass any extra payload, so the simple vanilla form suffices.
                serverPlayer.openMenu(this);
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
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
        return new ItemStack(CNBItems.CINDERSHELL_BUCKET);
    }

    @Override
    public SoundEvent getPickupSound() {
        return SoundEvents.BUCKET_FILL_LAVA;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob mob) {
        return CNBEntityTypes.CINDERSHELL.create(level);
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
        return CNBSoundEvents.CINDERSHELL_AMBIENT;
    }

    @Override
    public int getAmbientSoundInterval() {
        return 120;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return CNBSoundEvents.CINDERSHELL_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return CNBSoundEvents.CINDERSHELL_HURT;
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
        return this.entityData.get(FURNACE);
    }

    public void setFurnace(boolean hasFurnace) {
        this.entityData.set(FURNACE, hasFurnace);
    }

    // ---- Container ----
    @Override public int getContainerSize() { return this.items.size(); }
    @Override public boolean isEmpty() { for (ItemStack s : this.items) if (!s.isEmpty()) return false; return true; }
    @Override public @NotNull ItemStack getItem(int slot) { return this.items.get(slot); }
    @Override public @NotNull ItemStack removeItem(int slot, int amount) { return ContainerHelper.removeItem(this.items, slot, amount); }
    @Override public @NotNull ItemStack removeItemNoUpdate(int slot) { return ContainerHelper.takeItem(this.items, slot); }
    @Override public void setItem(int slot, @NotNull ItemStack stack) {
        this.items.set(slot, stack);
        if (stack.getCount() > this.getMaxStackSize()) stack.setCount(this.getMaxStackSize());
    }
    @Override public void setChanged() {}
    @Override public boolean stillValid(@NotNull Player player) { return this.isAlive() && player.distanceToSqr(this) < 64.0D; }
    @Override public void clearContent() { this.items.clear(); }

    // ---- MenuProvider ----
    @Override public @NotNull Component getDisplayName() { return Component.translatable("entity.cnb.cindershell"); }
    @Override
    public AbstractContainerMenu createMenu(int id, @NotNull Inventory playerInventory, @NotNull Player player) {
        return new CinderFurnaceContainer(id, playerInventory, this, this.dataAccess);
    }

    // ---- Cooking ----
    private Optional<RecipeHolder<? extends AbstractCookingRecipe>> findRecipe(ItemStack input) {
        if (input.isEmpty()) return Optional.empty();
        return this.level().getRecipeManager()
                .getRecipeFor(RecipeType.SMELTING, new SingleRecipeInput(input), this.level())
                .map(h -> (RecipeHolder<? extends AbstractCookingRecipe>) h);
    }

    private boolean canBurn(@Nullable RecipeHolder<? extends AbstractCookingRecipe> holder, NonNullList<ItemStack> stack, int maxStack) {
        if (holder == null || stack.get(0).isEmpty()) return false;
        ItemStack assembled = holder.value().assemble(new SingleRecipeInput(stack.get(0)), this.level().registryAccess());
        if (assembled.isEmpty()) return false;
        ItemStack current = stack.get(1);
        if (current.isEmpty()) return true;
        if (!ItemStack.isSameItemSameComponents(current, assembled)) return false;
        int total = current.getCount() + assembled.getCount();
        return total <= maxStack && total <= current.getMaxStackSize();
    }

    private boolean smelt(@Nullable RecipeHolder<? extends AbstractCookingRecipe> holder, NonNullList<ItemStack> stack, int maxStack) {
        if (!canBurn(holder, stack, maxStack)) return false;
        ItemStack input = stack.get(0);
        ItemStack assembled = holder.value().assemble(new SingleRecipeInput(input), this.level().registryAccess());
        ItemStack current = stack.get(1);
        if (current.isEmpty()) {
            stack.set(1, assembled.copy());
        } else if (ItemStack.isSameItemSameComponents(current, assembled)) {
            current.grow(assembled.getCount());
        }
        input.shrink(1);
        return true;
    }

    private int getTotalCookTime() {
        Optional<RecipeHolder<? extends AbstractCookingRecipe>> opt = findRecipe(this.items.get(0));
        if (opt.isEmpty()) return 200;
        int base = opt.get().value().getCookingTime();
        // Cooks at base speed in the Nether, slower elsewhere (~67% slower) — matches original 1.18 behavior.
        boolean inNether = this.level().dimension().equals(Level.NETHER);
        return (int) (base * (inNether ? 1.0F : 1.667F));
    }

    public void setRecipeUsed(@Nullable RecipeHolder<?> holder) {
        if (holder != null) {
            this.recipesUsed.addTo(holder.id(), 1);
        }
    }

    public void awardUsedRecipesAndPopExperience(ServerPlayer player) {
        List<RecipeHolder<?>> list = new ArrayList<>();
        for (var entry : this.recipesUsed.object2IntEntrySet()) {
            player.serverLevel().getRecipeManager().byKey(entry.getKey()).ifPresent(holder -> {
                list.add(holder);
                if (holder.value() instanceof AbstractCookingRecipe cooking) {
                    createExperience(player.serverLevel(), player.position(), entry.getIntValue(), cooking.getExperience());
                }
            });
        }
        player.awardRecipes(list);
        this.recipesUsed.clear();
    }

    private static void createExperience(ServerLevel level, Vec3 vec3, int value, float experience) {
        int i = Mth.floor((float) value * experience);
        float f = Mth.frac((float) value * experience);
        if (f != 0.0F && Math.random() < (double) f) ++i;
        ExperienceOrb.award(level, vec3, i);
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

        if (!this.level().isClientSide && this.hasFurnace() && !this.items.get(0).isEmpty()) {
            RecipeHolder<? extends AbstractCookingRecipe> holder = findRecipe(this.items.get(0)).orElse(null);
            if (canBurn(holder, this.items, 64)) {
                if (this.random.nextDouble() < 0.1D) {
                    this.playSound(SoundEvents.FURNACE_FIRE_CRACKLE, 1.0F, 1.0F);
                }
                if (this.cookingTotalTime <= 0) {
                    this.cookingTotalTime = getTotalCookTime();
                }
                ++this.cookingProgress;
                if (this.cookingProgress >= this.cookingTotalTime) {
                    this.cookingProgress = 0;
                    if (smelt(holder, this.items, 64)) {
                        setRecipeUsed(holder);
                    }
                    this.cookingTotalTime = getTotalCookTime();
                }
            } else {
                this.cookingProgress = 0;
            }
        } else if (!this.level().isClientSide) {
            this.cookingProgress = 0;
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
                player.playSound(this.isBaby() ? CNBSoundEvents.CINDERSHELL_BABY_EAT : CNBSoundEvents.CINDERSHELL_ADULT_EAT, 0.4F, 1F);
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

    // ---- Sizing / eye height (1.20.1 parity) ----
    // Babies render at 0.55× width / 0.35× height; sleeping pose uses fixed small dims.
    // Eye height is 20% of body height (cindershell head sits low on the shell).
    // (1.21 made getDimensions final on LivingEntity; getDefaultDimensions is the new hook.)
    @Override
    public EntityDimensions getDefaultDimensions(Pose pose) {
        if (pose == Pose.SLEEPING) return SLEEPING_DIMENSIONS;
        EntityDimensions base = super.getDefaultDimensions(pose);
        EntityDimensions scaled = this.isBaby() ? base.scale(0.55F, 0.35F) : base;
        return scaled.withEyeHeight(scaled.height() * 0.2F);
    }

    // ---- Baby health reduction ----
    // Adult MAX_HEALTH = 80. Babies get a transient -70 modifier so they sit at 10 HP.
    // Restored on growth (ageBoundaryReached).
    @Override
    public void setAge(int age) {
        super.setAge(age);
        var maxHealthAttr = this.getAttribute(Attributes.MAX_HEALTH);
        if (maxHealthAttr == null) return;
        double max = maxHealthAttr.getValue();
        if (this.isBaby() && max > BABY_HEALTH) {
            maxHealthAttr.addOrUpdateTransientModifier(new net.minecraft.world.entity.ai.attributes.AttributeModifier(
                    BABY_HEALTH_REDUCTION_ID,
                    BABY_HEALTH - max,
                    net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation.ADD_VALUE));
            this.setHealth(BABY_HEALTH);
        }
    }

    @Override
    protected void ageBoundaryReached() {
        super.ageBoundaryReached();
        var maxHealthAttr = this.getAttribute(Attributes.MAX_HEALTH);
        if (maxHealthAttr == null) return;
        maxHealthAttr.removeModifier(BABY_HEALTH_REDUCTION_ID);
        this.setHealth((float) maxHealthAttr.getValue());
    }

    // ---- Short death animation ----
    // Cindershell death broadcasts event 60 to clients (animation hook) and removes after 23 ticks.
    @Override
    protected void tickDeath() {
        ++this.deathTime;
        if (this.deathTime == 23 && !this.level().isClientSide()) {
            this.level().broadcastEntityEvent(this, (byte) 60);
            this.remove(net.minecraft.world.entity.Entity.RemovalReason.KILLED);
        }
    }

    // ---- Drop equipment on death ----
    // If furnace is mounted, drop it back as an item plus any contents.
    @Override
    protected void dropEquipment() {
        super.dropEquipment();
        if (this.hasFurnace()) {
            this.playSound(SoundEvents.HORSE_SADDLE, 1.0F,
                    (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 0.8F);
            if (!this.level().isClientSide) {
                this.spawnAtLocation(com.cgessinger.creaturesandbeasts.init.CNBBlocks.CINDER_FURNACE);
                for (int i = 0; i < this.items.size(); i++) {
                    ItemStack stack = this.items.get(i);
                    if (!stack.isEmpty()) this.spawnAtLocation(stack);
                }
                this.clearContent();
            }
            this.setFurnace(false);
        }
    }

    // ---- Block portal traversal while cooking ----
    // 1.20.1 used handleNetherPortal; in 1.21 we override canChangeDimensions which gates the
    // dimension-transition step. Visual portal effect still plays but the entity stays put.
    @Override
    public boolean canChangeDimensions(net.minecraft.world.level.Level oldLevel, net.minecraft.world.level.Level newLevel) {
        if (this.hasFurnace()) return false;
        return super.canChangeDimensions(oldLevel, newLevel);
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
