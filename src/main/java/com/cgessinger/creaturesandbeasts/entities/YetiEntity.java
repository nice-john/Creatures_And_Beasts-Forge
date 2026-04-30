package com.cgessinger.creaturesandbeasts.entities;

import com.cgessinger.creaturesandbeasts.init.CNBEntityTypes;
import com.cgessinger.creaturesandbeasts.init.CNBSoundEvents;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.keyframe.event.ParticleKeyframeEvent;
import software.bernie.geckolib.core.keyframe.event.SoundKeyframeEvent;
import software.bernie.geckolib.core.object.DataTicket;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;
import software.bernie.geckolib.constant.DataTickets;


import org.jetbrains.annotations.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class YetiEntity extends TamableAnimal implements Enemy, NeutralMob, GeoAnimatable {
    public static final EntityDataAccessor<Boolean> ATTACKING = SynchedEntityData.defineId(YetiEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> EATING = SynchedEntityData.defineId(YetiEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> PASSIVE = SynchedEntityData.defineId(YetiEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<ItemStack> HELD_ITEM = SynchedEntityData.defineId(YetiEntity.class, EntityDataSerializers.ITEM_STACK);

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private final UUID healthReductionUUID = UUID.fromString("189faad9-35de-4e15-a598-82d147b996d7");
    private final float babyHealth = 20.0F;

    private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
    private int remainingPersistentAngerTime;
    @Nullable
    private UUID persistentAngerTarget;

    private int eatTimer;
    private int attackTimer;

    public YetiEntity(EntityType<YetiEntity> type, Level worldIn) {
        super(type, worldIn);
        this.setTame(false);
        this.eatTimer = 0;
        this.setTame(false);
        this.eatTimer = 0;

        // Step over full blocks smoothly
        this.setMaxUpStep(1.3F);

        // (optional) teach pathfinder to avoid problem blocks
        this.setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.LAVA, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, 0.0F);
        this.setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, 0.0F);
        this.setPathfindingMalus(BlockPathTypes.LEAVES, -1.0F);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ATTACKING, false);
        this.entityData.define(EATING, false);
        this.entityData.define(PASSIVE, false);
        this.entityData.define(HELD_ITEM, ItemStack.EMPTY);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("Passive", this.isPassive());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("Passive")) {
            this.setPassive(compound.getBoolean("Passive"));
        }
    }


    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 120.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.ATTACK_DAMAGE, 16.0D)
                .add(Attributes.ATTACK_SPEED, 0.1D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.7D);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(1, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(2, new YetiAttackGoal(this, 1.5D, true));
        this.goalSelector.addGoal(3, new FollowParentGoal(this, 1.0D));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 12.0F));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0D, 0.01F));
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, (new HurtByTargetGoal(this)).setAlertOthers());
        this.targetSelector.addGoal(4, new TargetPlayerGoal(this));
        this.targetSelector.addGoal(5, new ResetUniversalAngerTargetGoal<>(this, true));
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if (this.isEating()) {
            this.navigation.stop();
            this.eatTimer--;
        }

        if (this.isAttacking()) {
            this.navigation.stop();
            this.attackTimer--;
        }

        if (this.eatTimer == 40) {
            if (this.isBaby()) {
                this.ageUp((int) (-this.getAge() / 20F * 0.1F), true);
            }
            if (this.getHolding().is(Items.MELON_SLICE)) {
                this.setTarget(null);
                this.setPassive(true);
            }
            this.setHolding(ItemStack.EMPTY);
        } else if (this.eatTimer == 0) {
            this.setEating(false);
        }

        if (this.attackTimer == 10 && !this.isDeadOrDying()) {
            this.performAttack();
        } else if (this.attackTimer == 0) {
            this.setAttacking(false);
        }
    }

    @Override
    public boolean canBeLeashed(Player player) {
        return false;
    }

    @Override
    public int getRemainingPersistentAngerTime() {
        return this.remainingPersistentAngerTime;
    }

    @Override
    public void setRemainingPersistentAngerTime(int angerTime) {
        this.remainingPersistentAngerTime = angerTime;
    }

    @Nullable
    @Override
    public UUID getPersistentAngerTarget() {
        return this.persistentAngerTarget;
    }

    @Override
    public void setPersistentAngerTarget(@Nullable UUID uuid) {
        this.persistentAngerTarget = uuid;
    }

    @Override
    public void startPersistentAngerTimer() {
        this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
    }

    @Override
    public float getWalkTargetValue(BlockPos pos, LevelReader level) {
        return 10.0F;
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, SpawnGroupData spawnDataIn, CompoundTag dataTag) {
        if (spawnDataIn == null) {
            spawnDataIn = new AgeableMobGroupData(1.0F);
        }

        return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack item = player.getItemInHand(hand);

        if (!(this.isEating() || this.isAttacking())) {
            if (!this.level().isClientSide && item.getItem() == Items.MELON_SLICE && !this.isPassive()) {
                this.setOwnerUUID(player.getUUID());
                return this.startEat(player, item.copy());
            } else if (item.getItem() == Items.SWEET_BERRIES) {
                if (!this.level().isClientSide && this.getAge() == 0 && this.canFallInLove()) {
                    this.setInLove(player);
                    return this.startEat(player, item.copy());
                } else if (this.isBaby()) {
                    return this.startEat(player, item.copy());
                }
            }
        }

        if (this.level().isClientSide) {
            return InteractionResult.CONSUME;
        }

        return InteractionResult.PASS;
    }

    /*
     * If a Yeti is a baby, apply the max health reduction to the yeti and set its health to the new max
     */
    @Override
    public void setAge(int age) {
        super.setAge(age);
        double MAX_HEALTH = this.getAttribute(Attributes.MAX_HEALTH).getValue();
        if (isBaby() && MAX_HEALTH > this.babyHealth) {
            Multimap<Attribute, AttributeModifier> multimap = HashMultimap.create();
            multimap.put(Attributes.MAX_HEALTH, new AttributeModifier(this.healthReductionUUID, "yeti_health_reduction", this.babyHealth - MAX_HEALTH, AttributeModifier.Operation.ADDITION));
            this.getAttributes().addTransientAttributeModifiers(multimap);
            this.setHealth(this.babyHealth);
        }
    }

    /*
     * When a Yeti baby grows up, remove the max health debuff, maintain the same percentage of max health
     */
    @Override
    protected void ageBoundaryReached() {
        super.ageBoundaryReached();
        float percentHealth = this.getHealth() / this.babyHealth;
        this.getAttribute(Attributes.MAX_HEALTH).removeModifier(this.healthReductionUUID);
        this.setHealth(percentHealth * (float) this.getAttribute(Attributes.MAX_HEALTH).getValue());
        this.setEating(false);
        this.setHolding(ItemStack.EMPTY);

        if (!this.level().isClientSide && this.isPassive() && this.getOwner() != null && this.getOwner() instanceof ServerPlayer player) {
            this.tame(player);
            this.setPassive(false);
            this.navigation.stop();
            this.setTarget(null);
            this.level().broadcastEntityEvent(this, (byte) 7);
        }
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob mob) {
        return CNBEntityTypes.YETI.create(level);
    }

    public void setEating(boolean isEating) {
        this.eatTimer = isEating ? 60 : 0;
        this.entityData.set(EATING, isEating);
    }

    public boolean isEating() {
        return this.entityData.get(EATING);
    }

    public void setAttacking(boolean isAttacking) {
        this.entityData.set(ATTACKING, isAttacking);
        this.attackTimer = isAttacking ? 24 : 0;
    }

    public boolean isAttacking() {
        return this.entityData.get(ATTACKING);
    }

    public boolean isPassive() {
        return this.entityData.get(PASSIVE);
    }

    public void setPassive(boolean isPassive) {
        this.entityData.set(PASSIVE, isPassive);
    }

    public ItemStack getHolding() {
        return this.entityData.get(HELD_ITEM);
    }

    public void setHolding(ItemStack stack) {
        this.entityData.set(HELD_ITEM, stack);
    }

    private InteractionResult startEat(Player player, ItemStack stack) {
        this.setHolding(stack);
        this.usePlayerItem(player, player.getUsedItemHand(), stack);
        this.setEating(true);
        this.gameEvent(GameEvent.ENTITY_INTERACT, player);
        SoundEvent sound = this.isBaby() ? CNBSoundEvents.YETI_BABY_EAT : CNBSoundEvents.YETI_ADULT_EAT;
        this.playSound(sound, 1.1F, 1F);
        return InteractionResult.SUCCESS;
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return false;
    }

    @Override
    public boolean isInSittingPose() {
        return false;
    }

    @Override
    public void setInSittingPose(boolean p_21838_) {
    }

    @Override
    public boolean isOrderedToSit() {
        return false;
    }

    @Override
    public void setOrderedToSit(boolean p_21840_) {
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return !this.isTame() && !this.hasCustomName();
    }

    /**
     * 1.3-block jump height. Vanilla {@link LivingEntity#getJumpPower()} returns 0.42F,
     * which produces a ~1.25-block peak via vanilla gravity (0.08) + Y drag (0.98). Bumping
     * to 0.43F yields ~1.31 blocks.
     */
    @Override
    protected float getJumpPower() {
        return 0.43F;
    }

    // 2) performAttack: level -> level()
    // AOE inflate of 3.0 horizontal (was 1.5) so the swing covers a wide arc once
    // the yeti commits to attacking. The goal-trigger reach (when the yeti decides
    // to swing) stays at vanilla MeleeAttackGoal default — testing showed the
    // earlier 2x trigger range had yetis windmilling at distant targets.
    private void performAttack() {
        List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(3.0D, 1.0D, 3.0D));

        for (LivingEntity entity : list) {
            if ((entity instanceof Player && entity.getUUID().equals(this.getOwnerUUID())) || (entity instanceof YetiEntity && Objects.equals(this.getOwnerUUID(), ((YetiEntity) entity).getOwnerUUID()))) {
                continue;
            }

            this.playSound(CNBSoundEvents.YETI_HIT, this.getSoundVolume() * 0.3F, this.getVoicePitch());

            this.doHurtTarget(entity);
        }
    }


    // 3) hurt: level -> level()
    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (this.isBaby()) {
            List<YetiEntity> list = this.level().getEntitiesOfClass(
                    YetiEntity.class, this.getBoundingBox().inflate(8.0D, 4.0D, 8.0D));

            for (YetiEntity yeti : list) {
                if (!yeti.isBaby() && !yeti.isTame()) {
                    yeti.setPassive(false);
                    yeti.setOwnerUUID(null);
                }
            }
        }

        if (!this.isTame()) {
            this.setPassive(false);
            this.setOwnerUUID(null);
        }
        return super.hurt(source, amount);
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        if (!this.level().getFluidState(pos).is(FluidTags.WATER)) { // Check if the block is not a liquid
            this.playSound(CNBSoundEvents.YETI_STEP, this.getSoundVolume() * 0.3F, this.getVoicePitch());
        }
    }

    @Override
    public float getVoicePitch() {
        float pitch = super.getVoicePitch();
        return this.isBaby() ? pitch * 1.5F : pitch;
    }

    @Override
    public int getMaxHeadYRot() {
        return 50;
    }

    @Override
    public int getMaxHeadXRot() {
        return 25;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return this.isBaby() ? null : CNBSoundEvents.YETI_AMBIENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return this.isBaby() ? null : CNBSoundEvents.YETI_HURT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return this.isBaby() ? null : CNBSoundEvents.YETI_HURT;
    }

    private <E extends GeoAnimatable> PlayState animationPredicate(AnimationState<E> event) {
        var controller = event.getController();
        if (this.isEating()) {
            controller.setAnimation(
                    RawAnimation.begin().thenPlay(this.isBaby() ? "yeti_baby_eat" : "yeti_adult_eat")
            );
        } else if (this.isAttacking()) {
            controller.setAnimation(
                    RawAnimation.begin().thenPlay("yeti_attack")
            );
        } else if (this.getDeltaMovement().lengthSqr() > 0.01) { // Check if the entity is moving
            controller.setAnimation(
                    RawAnimation.begin().thenPlay(this.isBaby() ? "yeti_baby_walk" : "yeti_adult_walk")
            );
        } else {
            return PlayState.STOP;
        }
        return PlayState.CONTINUE;
    }


    private <E extends GeoAnimatable> void particleListener(ParticleKeyframeEvent<E> event) {
        String effect = event.getKeyframeData().getEffect();

        if ("eat.particle".equals(effect)) {
            spawnParticles(ParticleTypes.HAPPY_VILLAGER);
        }
        // "hit.ground.particle" is intentionally handled inside soundListener (below) so
        // it can use Minecraft.getInstance().particleEngine on the client thread.
    }

    private <E extends GeoAnimatable> void soundListener(SoundKeyframeEvent<E> event) {
        String sound = event.getKeyframeData().getSound(); // already working

        if ("hit.ground.sound".equals(sound)) {
            // play the sound (as before)
            this.playSound(CNBSoundEvents.YETI_HIT, 0.4F, 1.0F);

            // spawn the particles at the same moment (client-only)
            if (this.level().isClientSide) {
                net.minecraft.client.particle.ParticleEngine pe = net.minecraft.client.Minecraft.getInstance().particleEngine;
                net.minecraft.core.BlockPos pos = this.blockPosition();
                for (int x = pos.getX() - 1; x <= pos.getX() + 1; x++) {
                    for (int z = pos.getZ() - 1; z <= pos.getZ() + 1; z++) {
                        net.minecraft.core.BlockPos p = new net.minecraft.core.BlockPos(x, pos.getY() - 1, z);
                        pe.destroy(p, this.level().getBlockState(p));
                    }
                }
            }
        } else if ("yeti_ambient".equals(sound)) {
            this.playSound(CNBSoundEvents.YETI_AMBIENT, 1.0F, 1.0F);
        }
    }



    private static float parseFloatSafe(String s, float def) {
        try { return Float.parseFloat(s); } catch (Exception e) { return def; }
    }

    public void spawnParticles(ParticleOptions data) {
        for (int i = 0; i < 7; ++i) {
            double d0 = this.random.nextGaussian() * 0.02D;
            double d1 = this.random.nextGaussian() * 0.02D;
            double d2 = this.random.nextGaussian() * 0.02D;
            this.level().addParticle(data, this.getRandomX(1.0D), this.getRandomY() + 0.5D, this.getRandomZ(1.0D), d0, d1, d2);
        }
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        AnimationController<YetiEntity> controller = new AnimationController<>(this, "controller", 1, this::animationPredicate);

        controller.setSoundKeyframeHandler(this::soundListener);
        controller.setParticleKeyframeHandler(this::particleListener);

        controllers.add(controller);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public double getTick(Object animatable) {
        return this.tickCount; // Use the entity's internal tick count
    }


    static class TargetPlayerGoal extends NearestAttackableTargetGoal<Player> {
        private final YetiEntity yeti;

        public TargetPlayerGoal(YetiEntity yeti) {
            super(yeti, Player.class, 20, true, true, null);
            this.yeti = yeti;
        }

        @Override
        public boolean canUse() {
            if (!this.yeti.isBaby() && !this.yeti.isPassive() && super.canUse()) {
                for (YetiEntity y : yeti.level().getEntitiesOfClass(YetiEntity.class,
                        yeti.getBoundingBox().inflate(8.0D, 4.0D, 8.0D))) {
                    if (y.isBaby()) return true;
                }
            }
            return false;
        }

        @Override
        protected double getFollowDistance() {
            return super.getFollowDistance() * 0.5D;
        }
    }

    static class YetiAttackGoal extends MeleeAttackGoal {
        private final YetiEntity yeti;

        public YetiAttackGoal(YetiEntity yeti, double speedModifier, boolean requiresLineOfSight) {
            super(yeti, speedModifier, requiresLineOfSight);
            this.yeti = yeti;
        }

        @Override
        public boolean canContinueToUse() {
            return super.canContinueToUse() && !this.yeti.isBaby();
        }

        @Override
        public boolean canUse() {
            if (this.yeti.getTarget() instanceof TamableAnimal tam
                    && this.yeti.isTame()
                    && this.yeti.getOwner() != null
                    && this.yeti.getOwner().equals(tam.getOwner())) {
                return false;
            }
            return super.canUse() && !this.yeti.isBaby() && this.yeti.getTarget() != this.yeti.getOwner();
        }

        @Override
        protected void checkAndPerformAttack(LivingEntity entity, double distance) {
            double reach = this.getAttackReachSqr(entity);
            if (distance <= reach && this.yeti.attackTimer <= 0 && this.ticksUntilNextAttack <= 0) {
                this.resetAttackCooldown();
            }
        }

        // Goal-trigger reach uses vanilla MeleeAttackGoal default. We want yetis to
        // chase all the way up to the target (i.e. into actual melee distance) and
        // only then swing — the 2x override stopped pursuit too early, leaving
        // attacks falling short. AOE inflate(3.0) in performAttack still gives the
        // swing a wide arc once the yeti commits.

        @Override
        public void stop() {
            super.stop();
            this.yeti.setAttacking(false);
        }

        @Override
        protected void resetAttackCooldown() {
            this.ticksUntilNextAttack = this.adjustedTickDelay(25);
            this.yeti.setAttacking(true);
        }
    }
}
