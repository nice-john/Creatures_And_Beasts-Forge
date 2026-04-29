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

    @Inject(method = "setupRotations(Lnet/minecraft/world/entity/LivingEntity;Lcom/mojang/blaze3d/vertex/PoseStack;FFF)V", at = @At("RETURN"))
    private void CNB_setupWhaleRidingRotations(LivingEntity entity, PoseStack stack, float ageInTicks, float rotationYaw, float partialTicks, CallbackInfo ci) {
        if (entity.getVehicle() instanceof EndWhaleEntity endWhale) {
            float whaleRotY = endWhale.getViewYRot(partialTicks);
            float playerRotY = entity.getViewYRot(partialTicks);
            float whaleRotX = endWhale.getViewXRot(partialTicks);
            float playerRotX = entity.getViewXRot(partialTicks);

            // Apply rotations to the PoseStack
            stack.mulPose(Axis.ZP.rotationDegrees(Mth.wrapDegrees(whaleRotY - playerRotY) / 2));
            stack.mulPose(Axis.XP.rotationDegrees(Mth.wrapDegrees(whaleRotX - playerRotX)));
        }
    }


    /**
     * Suppress vanilla rider-rotation when the player is on an End Whale (the whale's
     * Y-rotation differs from the player's because of the whale's pitch animation).
     * Targets the 3rd getVehicle() call in LivingEntityRenderer.render — but the call
     * count varies across MC builds and obfuscation passes, so {@code require=0} lets
     * the injection silently no-op if the ordinal can't be resolved (worst case: the
     * whale rider's rotation looks slightly off; game still loads). Without require=0
     * a missing target is fatal at class-transform time and the world fails to render.
     */
    @Redirect(method = "render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getVehicle()Lnet/minecraft/world/entity/Entity;", ordinal = 2),
            require = 0)
    private Entity CNB_redirectPlayerRotOnWhale(LivingEntity entity) {
        if (entity.getVehicle() instanceof EndWhaleEntity) {
            return null;
        } else {
            return entity.getVehicle();
        }
    }
}
