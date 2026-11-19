package com.cgessinger.creaturesandbeasts.client.entity.render;

import com.cgessinger.creaturesandbeasts.CreaturesAndBeasts;
import com.cgessinger.creaturesandbeasts.client.entity.model.MinipadModel;
import com.cgessinger.creaturesandbeasts.entities.MinipadEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;
import net.neoforged.api.distmarker.Dist;
public class MinipadRenderer extends CnbEntityRenderer<MinipadEntity> {
    private static final Identifier EYES_GLOW = Identifier.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "textures/entity/minipad/minipad_eyes_glow.png");

    public MinipadRenderer(EntityRendererProvider.Context context) {
        super(context, new MinipadModel(), 0.4F, 0.5F, 0.8F, false, id -> RenderTypes.entityTranslucent(id));

        // Glow passes, only while the minipad is glowing. Fade curves follow the time of day, peaking at midnight.
        this.withRenderLayer(new TintedTextureLayer<MinipadEntity, net.minecraft.client.renderer.entity.state.LivingEntityRenderState>(this,
                minipad -> minipad.isGlowing() && !minipad.getSheared() ? new TintedTextureLayer.Pass(minipad.getMinipadType().getGlowTextureLocation(), 0xFFFFFFFF) : null,
                RenderTypes::eyes, 1));
        this.withRenderLayer(new TintedTextureLayer<MinipadEntity, net.minecraft.client.renderer.entity.state.LivingEntityRenderState>(this,
                minipad -> {
                    if (!minipad.isGlowing() || minipad.getSheared()) return null;
                    int alpha = (int) (Math.pow((nightTime(minipad) - 18000) / 5000f, 2) * 0xFF);
                    return new TintedTextureLayer.Pass(minipad.getMinipadType().getGlowTextureLocation(), (Math.max(0, Math.min(0xFF, alpha)) << 24) | 0x00FFFFFF);
                },
                id -> RenderTypes.entityTranslucent(id), 2));
        this.withRenderLayer(new TintedTextureLayer<MinipadEntity, net.minecraft.client.renderer.entity.state.LivingEntityRenderState>(this,
                minipad -> {
                    if (!minipad.isGlowing()) return null;
                    int alpha = (int) (((float) -Math.pow((nightTime(minipad) - 18000) / 5000f, 2) + 1) * 0xFF);
                    return new TintedTextureLayer.Pass(EYES_GLOW, (Math.max(0, Math.min(0xFF, alpha)) << 24) | 0x00FFFFFF);
                },
                RenderTypes::eyes, 3));
    }

    private static long nightTime(MinipadEntity minipad) {
        return minipad.level().getOverworldClockTime();
    }
}