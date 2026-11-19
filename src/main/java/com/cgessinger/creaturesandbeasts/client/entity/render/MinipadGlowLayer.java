package com.cgessinger.creaturesandbeasts.client.entity.render;

import com.cgessinger.creaturesandbeasts.CreaturesAndBeasts;
import com.cgessinger.creaturesandbeasts.entities.MinipadEntity;
import com.geckolib.constant.dataticket.DataTicket;
import com.geckolib.renderer.base.GeoRenderer;
import com.geckolib.renderer.base.RenderPassInfo;
import com.geckolib.renderer.layer.GeoRenderLayer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;

/**
 * Night-time glow for minipads: a full-bright flower pass, a fading translucent flower pass and a
 * fading eyes pass, driven by the time of day exactly like the 1.21.1 layer. GeckoLib 5 re-renders
 * the baked model via {@code submitRenderTasks} with a different render type; the per-pass alpha
 * of the old layer (a vertex colour) is not available there, so the passes use the model's own
 * alpha and the fade only gates whether a pass is drawn.
 */
@Environment(EnvType.CLIENT)
public class MinipadGlowLayer extends GeoRenderLayer<MinipadEntity, Void, LivingEntityRenderState> {
    private static final Identifier EYES_GLOW = Identifier.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "textures/entity/minipad/minipad_eyes_glow.png");
    private static final DataTicket<Boolean> GLOWING = DataTicket.create("cnb_minipad_glowing", Boolean.class);
    private static final DataTicket<Boolean> SHEARED = DataTicket.create("cnb_minipad_sheared", Boolean.class);
    private static final DataTicket<Identifier> FLOWER_GLOW = DataTicket.create("cnb_minipad_flower_glow", Identifier.class);
    private static final DataTicket<Float> EYES_ALPHA = DataTicket.create("cnb_minipad_eyes_alpha", Float.class);
    private static final DataTicket<Float> FLOWER_ALPHA = DataTicket.create("cnb_minipad_flower_alpha", Float.class);

    public MinipadGlowLayer(GeoRenderer<MinipadEntity, Void, LivingEntityRenderState> renderer) {
        super(renderer);
    }

    @Override
    public void addRenderData(MinipadEntity entity, Void relatedObject, LivingEntityRenderState renderState, float partialTick) {
        long time = entity.level().getOverworldClockTime() % 24000L;
        renderState.addGeckolibData(GLOWING, entity.isGlowing());
        renderState.addGeckolibData(SHEARED, entity.getSheared());
        renderState.addGeckolibData(FLOWER_GLOW, entity.getMinipadType().getGlowTextureLocation());
        float f = (time - 18000) / 5000f;
        renderState.addGeckolibData(FLOWER_ALPHA, clamp01(f * f));
        renderState.addGeckolibData(EYES_ALPHA, clamp01(1.0F - f * f));
    }

    private static float clamp01(float v) {
        return Math.max(0.0F, Math.min(1.0F, v));
    }

    @Override
    public void submitRenderTask(RenderPassInfo<LivingEntityRenderState> info, SubmitNodeCollector collector) {
        if (!info.willRender() || !info.getOrDefaultGeckolibData(GLOWING, false)) {
            return;
        }
        if (!info.getOrDefaultGeckolibData(SHEARED, false)) {
            Identifier flower = info.getGeckolibData(FLOWER_GLOW);
            this.getRenderer().submitRenderTasks(info, collector.order(1), RenderTypes.eyes(flower));
            if (info.getOrDefaultGeckolibData(FLOWER_ALPHA, 0.0F) > 0.0F) {
                this.getRenderer().submitRenderTasks(info, collector.order(2), RenderTypes.entityTranslucent(flower));
            }
        }
        if (info.getOrDefaultGeckolibData(EYES_ALPHA, 0.0F) > 0.0F) {
            RenderType eyes = RenderTypes.eyes(EYES_GLOW);
            this.getRenderer().submitRenderTasks(info, collector.order(3), eyes);
        }
    }
}