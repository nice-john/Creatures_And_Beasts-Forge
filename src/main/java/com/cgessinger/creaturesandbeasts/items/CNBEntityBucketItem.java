package com.cgessinger.creaturesandbeasts.items;

import net.minecraft.world.InteractionResult;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.Bucketable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.BlockHitResult;

import org.jetbrains.annotations.Nullable;
import java.util.function.Supplier;

public class CNBEntityBucketItem extends BucketItem {
    private final Supplier<? extends EntityType<?>> entityTypeSupplier;
    private final Supplier<SoundEvent> emptyingSoundSupplier;

    public CNBEntityBucketItem(Supplier<EntityType<?>> entityType, Fluid fluid, Supplier<SoundEvent> emptyingSound, Properties builder) {
        super(fluid, builder);
        this.entityTypeSupplier = entityType;
        this.emptyingSoundSupplier = emptyingSound;
    }

    @Override
    public void checkExtraContent(@Nullable LivingEntity player, Level worldIn, ItemStack stack, BlockPos pos) {
        if (worldIn instanceof ServerLevel) {
            this.placeEntity((ServerLevel) worldIn, stack, pos);
        }
    }

    @Override
    protected void playEmptySound(@Nullable LivingEntity player, LevelAccessor worldIn, BlockPos pos) {
        worldIn.playSound(player instanceof Player p ? p : null, pos, this.emptyingSoundSupplier.get(), SoundSource.NEUTRAL, 1.0F, 1.0F);
    }

    private void placeEntity(ServerLevel worldIn, ItemStack stack, BlockPos pos) {
        Entity entity = this.entityTypeSupplier.get().spawn(worldIn, stack, null, pos, EntitySpawnReason.BUCKET, true, true);
        if (entity instanceof Bucketable bucketable) {
            // 1.20.5+ vanilla migrated bucket data to DataComponents.BUCKET_ENTITY_DATA. The stored
            // CompoundTag holds the same fields as the old getOrCreateTag() flow (NoAI/Health/etc).
            net.minecraft.world.item.component.CustomData stored =
                    stack.get(net.minecraft.core.component.DataComponents.BUCKET_ENTITY_DATA);
            net.minecraft.nbt.CompoundTag tag =
                    stored != null ? stored.copyTag() : new net.minecraft.nbt.CompoundTag();
            bucketable.loadFromBucketTag(tag);
            bucketable.setFromBucket(true);
        }
    }

    @Override
    public InteractionResult use(Level worldIn, Player playerIn, InteractionHand handIn) {
        InteractionResult actionResult = super.use(worldIn, playerIn, handIn);
        ItemStack heldItem = playerIn.getItemInHand(handIn);
        BlockHitResult rayTraceResult = getPlayerPOVHitResult(worldIn, playerIn, ClipContext.Fluid.SOURCE_ONLY);
        if (rayTraceResult.getType() != BlockHitResult.Type.BLOCK) {
            return InteractionResult.PASS;
        } else if (!(worldIn instanceof ServerLevel)) {
            return InteractionResult.SUCCESS;
        } else {
            BlockPos pos = rayTraceResult.getBlockPos();
            if (!(worldIn.getFluidState(pos).getType() instanceof FlowingFluid)) {
                return InteractionResult.PASS;
            } else {
                return actionResult;
            }
        }
    }
}
