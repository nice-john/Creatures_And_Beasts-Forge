package com.cgessinger.creaturesandbeasts.entities;

import com.cgessinger.creaturesandbeasts.init.CNBSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Saddleable;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.AirAndWaterRandomPos;
import net.minecraft.world.entity.ai.util.HoverRandomPos;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;
import software.bernie.geckolib.util.RenderUtils;

import java.util.EnumSet;

import static com.cgessinger.creaturesandbeasts.init.CNBTags.Items.END_WHALE_FOOD;

public class EndWhaleEntity extends TamableAnimal implements FlyingAnimal, Saddleable, GeoAnimatable {
    private static final EntityDataAccessor<Boolean> SADDLED =
            SynchedEntityData.defineId(EndWhaleEntity.class, EntityDataSerializers.BOOLEAN);

    // Geckolib 4 cache
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public EndWhaleEntity(EntityType<EndWhaleEntity> entityType, Level level) {
        super(entityType, level);
        this.setTame(false);
        this.moveControl = new FlyingMoveControl(this, 1, true);
        this.lookControl = new EndWhaleLookControl(this);
        this.setNoGravity(true); // flyers feel better with gravity disabled
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 160.0D)
                .add(Attributes.MOVEMENT_SPEED, 1.0D)
                .add(Attributes.FOLLOW_RANGE, 100.0D)
                .add(Attributes.FLYING_SPEED, 1.0D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new EndWhaleTemptGoal(this, 1.25D, Ingredient.of(END_WHALE_FOOD)));
        this.goalSelector.addGoal(1, new EndWhaleWanderGoal(this));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SADDLED, false);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("Saddled", this.isSaddled());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.getBoolean("Saddled")) {
            this.equipSaddle(SoundSource.PLAYERS);
        }
    }

    @Override
    protected BodyRotationControl createBodyControl() {
        return new EndWhaleBodyRotationControl(this);
    }

    @Override public int getMaxHeadYRot() { return 0; }
    @Override public int getHeadRotSpeed() { return 20; }

    // Saddleable
    @Override public boolean isSaddleable() { return this.isTame(); }

    @Override
    public void equipSaddle(@Nullable SoundSource soundSource) {
        this.entityData.set(SADDLED, true);
        this.playSound(SoundEvents.HORSE_SADDLE, 1.0F, 1.0F);
    }

    public void removeSaddle() {
        this.entityData.set(SADDLED, false);
        this.spawnAtLocation(Items.SADDLE);
        this.playSound(SoundEvents.HORSE_SADDLE, 0.8F, 1.0F);
    }

    @Override public boolean isSaddled() { return this.entityData.get(SADDLED); }

    private void mountWhale(Player player) {
        if (!this.level().isClientSide) {
            player.setYRot(this.getYRot());
            player.setXRot(this.getXRot());
            player.startRiding(this);
        }
    }

    // 1.20.x name is still positionRider

    public void positionPassenger(Entity rider) {
        if (this.hasPassenger(rider)) {
            double verticalOffset = this.getPassengersRidingOffset() + rider.getMyRidingOffset();
            float whaleRoll = this.getWhaleRoll(rider) * Mth.DEG_TO_RAD;
            float whalePitch = this.getWhalePitch(rider) * Mth.DEG_TO_RAD;

            double xOffset = Mth.cos(this.getYRot() * Mth.DEG_TO_RAD) * verticalOffset * Mth.sin(whaleRoll)
                    + Mth.sin(this.getYRot() * Mth.DEG_TO_RAD) * verticalOffset * Mth.sin(whalePitch);
            double yOffset = verticalOffset * Mth.cos(whaleRoll) * Mth.cos(whalePitch);
            double zOffset = Mth.sin(this.getYRot() * Mth.DEG_TO_RAD) * verticalOffset * Mth.sin(whaleRoll)
                    - Mth.cos(this.getYRot() * Mth.DEG_TO_RAD) * verticalOffset * Mth.sin(whalePitch);

            // server positions the passenger; client interpolates
            if (!this.level().isClientSide) {
                rider.setPos(this.getX() + xOffset, this.getY() + yOffset, this.getZ() + zOffset);
                this.clampRotation(rider);
            }
        }
    }

    protected void clampRotation(Entity rider) {
        rider.setYBodyRot(this.getYRot());
        float f = Mth.wrapDegrees(rider.getYRot() - this.getYRot());
        float f1 = Mth.clamp(f, -90.0F, 90.0F);
        rider.yRotO += f1 - f;
        rider.setYRot(rider.getYRot() + f1 - f);
        rider.setYHeadRot(rider.getYRot());
    }

    @Override
    public double getPassengersRidingOffset() {
        return this.getDimensions(this.getPose()).height * 0.70D;
    }

    private float getWhaleRoll(Entity rider) {
        return Mth.wrapDegrees(this.getYRot() - rider.getYRot()) / 2.0F;
    }

    private float getWhalePitch(Entity rider) {
        return Mth.wrapDegrees(this.getXRot() - rider.getXRot());
    }

    // Mount control helpers (1.20.x)
    @Override
    public boolean isControlledByLocalInstance() {
        return this.getControllingPassenger() instanceof LivingEntity;
    }

    @Nullable
    @Override
    public LivingEntity getControllingPassenger() {
        Entity passenger = this.getFirstPassenger();
        return passenger instanceof LivingEntity ? (LivingEntity) passenger : null;
    }

    public boolean rideableUnderWater() { return true; }
    @Override public boolean canBreatheUnderwater() { return true; }

    // Movement
    public void travel(Vec3 travelVector) {
        if (!this.isAlive()) return;

        if (this.isVehicle() && this.isSaddled()) {
            LivingEntity livingentity = this.getControllingPassenger();
            if (livingentity != null) {
                // rotations on both sides (visuals smooth)
                this.setYRot(Mth.rotLerp(0.05F, this.getYRot(), livingentity.getYRot()));
                this.yRotO = this.getYRot();
                this.setXRot(livingentity.getXRot() * 0.5F);
                this.setRot(this.getYRot(), this.getXRot());
                this.yBodyRot = this.getYRot();
                this.yHeadRot = this.yBodyRot;

                float forwardMovement = livingentity.zza;
                if (forwardMovement <= 0.0F) forwardMovement *= 0.25F;
                float verticalMovement = 0.0F;

                if (Mth.abs(livingentity.getXRot()) > 7.0F) {
                    verticalMovement = Mth.rotLerp(0.01F, this.getXRot(), livingentity.getXRot()) * -forwardMovement / 50.0F;
                }

                // Movement on controlling client
                if (this.isControlledByLocalInstance()) {
                    this.setSpeed((float) this.getAttributeValue(Attributes.FLYING_SPEED));

                    Vec3 proposedMovement = new Vec3(0.0D, verticalMovement, forwardMovement);

                    if (this.isInLava()) {
                        this.moveRelative(0.02F, proposedMovement);
                        this.move(MoverType.SELF, this.getDeltaMovement());
                        this.setDeltaMovement(this.getDeltaMovement().scale(0.5D));
                    } else {
                        BlockPos ground = BlockPos.containing(this.getX(), this.getY() - 1.0D, this.getZ());
                        float f = 0.91F;
                        if (this.onGround()) {
                            f = this.level().getBlockState(ground).getFriction(this.level(), ground, this) * 0.91F;
                        }
                        float f1 = 0.16277137F / (f * f * f);
                        this.moveRelative(this.onGround() ? 0.06F * f1 : 0.06F, proposedMovement);
                        this.move(MoverType.SELF, this.getDeltaMovement());
                        this.setDeltaMovement(this.getDeltaMovement().scale(f));
                    }
                } else if (livingentity instanceof Player) {
                    this.setDeltaMovement(Vec3.ZERO);
                }

                this.calculateEntityAnimation(false);
                this.tryCheckInsideBlocks();
                return;
            }
        }

        // not ridden
        if (this.isInLava()) {
            this.moveRelative(0.02F, travelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.5D));
        } else {
            BlockPos ground = BlockPos.containing(this.getX(), this.getY() - 1.0D, this.getZ());
            float f = 0.91F;
            if (this.onGround()) {
                f = this.level().getBlockState(ground).getFriction(this.level(), ground, this) * 0.91F;
            }
            float f1 = 0.16277137F / (f * f * f);

            // server-only physics; client interpolates
            if (!this.level().isClientSide) {
                this.moveRelative(this.onGround() ? 0.1F * f1 : 0.02F, travelVector);
                this.move(MoverType.SELF, this.getDeltaMovement());
                this.setDeltaMovement(this.getDeltaMovement().scale(f));
            }
        }
        this.calculateEntityAnimation(false);
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (this.level().isClientSide) {
            boolean flag = this.isOwnedBy(player) || this.isTame() || (itemstack.is(END_WHALE_FOOD) && !this.isTame());
            return flag ? InteractionResult.CONSUME : InteractionResult.PASS;
        } else if (this.isSaddled() && player.isSecondaryUseActive()) {
            this.removeSaddle();
            return InteractionResult.CONSUME;
        } else if (this.isSaddled()) {
            this.mountWhale(player);
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        } else if (!this.isTame()) {
            if (itemstack.is(END_WHALE_FOOD)) {
                if (!player.getAbilities().instabuild) itemstack.shrink(1);
                if (this.random.nextInt(10) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, player)) {
                    this.tame(player);
                    this.navigation.stop();
                    this.setTarget(null);
                    this.setOrderedToSit(true);
                    this.level().broadcastEntityEvent(this, (byte) 7);
                } else {
                    this.level().broadcastEntityEvent(this, (byte) 6);
                }
                return InteractionResult.SUCCESS;
            }
        }
        return super.mobInteract(player, hand);
    }

    @Override
    public void onPassengerTurned(Entity entity) { this.clampRotation(entity); }

    @Override public boolean isFood(ItemStack stack) { return false; }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob endWhale) { return null; }

    @Override
    public int getExperienceReward() {
        return 12 + this.level().random.nextInt(5);
    }

    public static boolean checkEndWhaleSpawnRules(EntityType<EndWhaleEntity> animal, LevelAccessor worldIn, MobSpawnType reason, BlockPos pos, RandomSource randomIn) {
        return true;
    }

    @Override
    public float getWalkTargetValue(BlockPos pos, LevelReader level) { return 10.0F; }

    @Override public boolean isFlying() { return true; }
    @Override public boolean causeFallDamage(float a, float b, DamageSource c) { return false; }
    @Override protected void checkFallDamage(double a, boolean b, BlockState c, BlockPos d) {}

    /**
     * Default {@code Mob.getMaxSpawnClusterSize} is 4; vanilla pack-spawn caps any
     * single spawn event at this number regardless of what the biome modifier
     * asks for. We ask for max=8 in the biome modifier, so raising this lets the
     * full pod actually spawn. (The "canSpawnFarFromPlayer" half of this fix is
     * on the {@code EntityType.Builder} in {@code CNBEntityTypes} since it's
     * builder-level state, not an entity-class override.)
     */
    @Override public int getMaxSpawnClusterSize() { return 8; }

    @Override
    protected PathNavigation createNavigation(Level level) {
        FlyingPathNavigation nav = new FlyingPathNavigation(this, level);
        nav.setCanOpenDoors(false);
        nav.setCanFloat(false);
        nav.setCanPassDoors(false);
        return nav;
    }

    @Nullable
    @Override
    public SoundEvent getAmbientSound() { return CNBSoundEvents.END_WHALE_AMBIENT.get(); }

    @Override public int getAmbientSoundInterval() { return 800; }
    @Override protected float getSoundVolume() { return 5.0F; }

    // ---------------- Geckolib 4 ----------------

    private static final RawAnimation FLY = RawAnimation.begin().thenLoop("whale_fly");

    private <E extends GeoAnimatable> PlayState animationPredicate(AnimationState<E> event) {
        event.getController().setAnimation(RawAnimation.begin().thenPlay("whale_fly"));
        return PlayState.CONTINUE;
    }


    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 5, this::animationPredicate));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() { return this.cache; }

    @Override
    public double getTick(Object animatable) {
        return RenderUtils.getCurrentTick();
    }

    // ---------------- Controls/Goals ----------------

    static class EndWhaleLookControl extends LookControl {
        private final EndWhaleEntity endWhale;
        public EndWhaleLookControl(EndWhaleEntity endWhale) {
            super(endWhale);
            this.endWhale = endWhale;
        }
        @Override
        public void tick() {
            // keep simple; avoid fighting travel() while ridden
            if (!endWhale.isVehicle()) super.tick();
        }
    }

    static class EndWhaleBodyRotationControl extends BodyRotationControl {
        private final EndWhaleEntity endWhale;
        private int headStableTime;
        private float lastStableYHeadRot;

        public EndWhaleBodyRotationControl(EndWhaleEntity endWhale) {
            super(endWhale);
            this.endWhale = endWhale;
        }
        @Override
        public void clientTick() {
            if (this.endWhale.isVehicle()) return; // avoid conflict while ridden
            if (this.isMoving()) {
                this.endWhale.yBodyRot = Mth.rotLerp(0.05F, this.endWhale.yBodyRot, this.endWhale.getYRot());
                this.rotateHeadIfNecessary();
                this.lastStableYHeadRot = this.endWhale.yHeadRot;
                this.headStableTime = 0;
            } else if (this.notCarryingMobPassengers()) {
                if (Math.abs(this.endWhale.yHeadRot - this.lastStableYHeadRot) > 15.0F) {
                    this.headStableTime = 0;
                    this.lastStableYHeadRot = this.endWhale.yHeadRot;
                    this.rotateHeadIfNecessary();
                } else {
                    ++this.headStableTime;
                    if (this.headStableTime > 10) {
                        this.rotateHeadTowardsFront();
                    }
                }
            }
        }
        private void rotateHeadIfNecessary() {
            this.endWhale.yHeadRot = Mth.rotLerp(0.05F, this.endWhale.yHeadRot, this.endWhale.yBodyRot);
        }
        private void rotateHeadTowardsFront() {
            this.endWhale.yHeadRot = Mth.rotLerp(0.05F, this.endWhale.yHeadRot, this.endWhale.yBodyRot);
        }
        private boolean notCarryingMobPassengers() { return !(this.endWhale.getFirstPassenger() instanceof Mob); }
        private boolean isMoving() {
            double d0 = this.endWhale.getX() - this.endWhale.xo;
            double d1 = this.endWhale.getZ() - this.endWhale.zo;
            return d0 * d0 + d1 * d1 > 1.0E-5D; // slightly higher epsilon than 1.19
        }
    }

    static class EndWhaleWanderGoal extends Goal {
        private final EndWhaleEntity endWhale;
        EndWhaleWanderGoal(EndWhaleEntity endWhale) {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
            this.endWhale = endWhale;
        }
        @Override
        public boolean canUse() {
            return this.endWhale.navigation.isDone()
                    && !this.endWhale.isVehicle()
                    && !this.endWhale.isLeashed();
        }
        @Override
        public boolean canContinueToUse() {
            return this.endWhale.navigation.isInProgress()
                    && !this.endWhale.isVehicle()
                    && !this.endWhale.isLeashed();
        }
        @Override
        public void start() {
            Vec3 vec3 = this.findPos();
            if (vec3 != null) {
                this.endWhale.navigation.moveTo(
                        this.endWhale.navigation.createPath(BlockPos.containing(vec3), 3), 1.0D);
            }
        }
        @Override
        public void stop() { this.endWhale.navigation.stop(); }

        @Nullable
        private Vec3 findPos() {
            Vec3 vec3 = this.endWhale.getViewVector(0.5F);
            Vec3 v = net.minecraft.world.entity.ai.util.HoverRandomPos.getPos(this.endWhale, 30, 12, vec3.x, vec3.z, (float) (Math.PI / 6), 80, 15);
            v = v != null ? v : net.minecraft.world.entity.ai.util.AirAndWaterRandomPos.getPos(this.endWhale, 30, 12, -2, vec3.x, vec3.z, (float) (Math.PI / 6));
            if (this.endWhale.isSaddled() && v != null && this.endWhale.getOwner() != null
                    && v.distanceTo(this.endWhale.getOwner().position()) > 100.0D) {
                v = null;
            }
            return v;
        }
    }

    static class EndWhaleTemptGoal extends Goal {
        protected final EndWhaleEntity endWhale;
        private final double speedModifier;
        @Nullable protected Player player;
        private int calmDown;
        private final Ingredient items;

        public EndWhaleTemptGoal(EndWhaleEntity endWhale, double speedModifier, Ingredient temptIngredient) {
            this.endWhale = endWhale;
            this.speedModifier = speedModifier;
            this.items = temptIngredient;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            if (this.calmDown > 0) { --this.calmDown; return false; }
            if (this.endWhale.isVehicle()) return false;
            Player nearest = this.endWhale.level().getNearestPlayer(this.endWhale, 100.0D);
            if (nearest == null || nearest.isSpectator() || !this.shouldFollow(nearest)) {
                this.player = null;
                return false;
            }
            this.player = nearest;
            return true;
        }

        private boolean shouldFollow(LivingEntity entity) {
            return this.items.test(entity.getMainHandItem()) || this.items.test(entity.getOffhandItem());
        }

        @Override
        public boolean canContinueToUse() {
            // less thrashy than calling canUse() again each tick
            return this.player != null && this.player.isAlive()
                    && !this.endWhale.isVehicle() && !this.endWhale.isLeashed()
                    && this.shouldFollow(this.player)
                    && this.endWhale.distanceToSqr(this.player) >= 6.25D;
        }

        @Override
        public void stop() {
            this.player = null;
            this.endWhale.getNavigation().stop();
            this.calmDown = reducedTickDelay(100);
        }

        @Override
        public void tick() {
            if (this.player == null) return;
            this.endWhale.getLookControl().setLookAt(this.player,
                    (float) (this.endWhale.getMaxHeadYRot() + 20),
                    (float) this.endWhale.getMaxHeadXRot());
            if (this.endWhale.distanceToSqr(this.player) < 6.25D) {
                this.endWhale.getNavigation().stop();
            } else {
                this.endWhale.getNavigation().moveTo(this.player, this.speedModifier);
            }
        }
    }
}