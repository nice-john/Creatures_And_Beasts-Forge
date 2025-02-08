package com.cgessinger.creaturesandbeasts.mixin;

import com.cgessinger.creaturesandbeasts.entities.SporelingEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

@Mixin(GeoEntityRenderer.class)
public class MixinGeoEntityRenderer<T extends LivingEntity & GeoEntity> {

    //@ModifyVariable(
        //    method = "render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
        //    at = @At(value = "STORE"),
       //     remap = false
    //)
    private boolean CNB_stopSporelingRotatingOnPlayer(boolean value, T entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        if (entity instanceof SporelingEntity && entity.getVehicle() instanceof Player) {
            return false; // Prevent rotation if the Sporeling is riding a Player
        } else {
            return value; // Otherwise, use the default behavior
        }
    }
}
