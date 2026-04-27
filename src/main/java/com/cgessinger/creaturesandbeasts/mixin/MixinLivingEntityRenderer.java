package com.cgessinger.creaturesandbeasts.mixin;

import com.cgessinger.creaturesandbeasts.entities.EndWhaleEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public class MixinLivingEntityRenderer<T extends LivingEntity> {

    // 1.21.1: setupRotations gained a trailing `float scale` parameter.
    @Inject(
            method = "setupRotations(Lnet/minecraft/world/entity/LivingEntity;Lcom/mojang/blaze3d/vertex/PoseStack;FFFF)V",
            at = @At("RETURN")
    )
    private void cnb$setupWhaleRidingRotations(LivingEntity entity, PoseStack stack, float bob, float yBodyRot, float partialTick, float scale, CallbackInfo ci) {
        if (entity.getVehicle() instanceof EndWhaleEntity endWhale) {
            float whaleRotY = endWhale.getViewYRot(partialTick);
            float playerRotY = entity.getViewYRot(partialTick);
            float whaleRotX = endWhale.getViewXRot(partialTick);
            float playerRotX = entity.getViewXRot(partialTick);

            stack.mulPose(Axis.ZP.rotationDegrees(Mth.wrapDegrees(whaleRotY - playerRotY) / 2));
            stack.mulPose(Axis.XP.rotationDegrees(Mth.wrapDegrees(whaleRotX - playerRotX)));
        }
    }

    // Suppresses vanilla rider-rotation snapping that causes jitter while riding the whale.
    // require = 0: if vanilla shifts the call site in a future patch, fail-soft (we just lose
    // smoothing until we adjust the ordinal) instead of crashing the whole renderer.
    @Redirect(
            method = "render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getVehicle()Lnet/minecraft/world/entity/Entity;", ordinal = 2),
            require = 0
    )
    private Entity cnb$redirectPlayerRotOnWhale(LivingEntity entity) {
        if (entity.getVehicle() instanceof EndWhaleEntity) {
            return null;
        }
        return entity.getVehicle();
    }
}
