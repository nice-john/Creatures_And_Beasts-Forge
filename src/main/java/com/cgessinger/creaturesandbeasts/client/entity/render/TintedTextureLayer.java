package com.cgessinger.creaturesandbeasts.client.entity.render;

import com.geckolib.animatable.GeoAnimatable;
import com.geckolib.constant.dataticket.DataTicket;
import com.geckolib.renderer.base.GeoRenderState;
import com.geckolib.renderer.base.GeoRenderer;
import com.geckolib.renderer.base.RenderPassInfo;
import com.geckolib.renderer.layer.GeoRenderLayer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.OrderedSubmitNodeCollector;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;
import net.neoforged.api.distmarker.Dist;
import org.jspecify.annotations.Nullable;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

/**
 * Re-renders the whole model with another texture / render type / ARGB tint (glow overlays).
 * GeckoLib's built-in texture layer can't be tinted per pass, which the Minipad's time-dependent glow needs.
 */
public class TintedTextureLayer<T extends GeoAnimatable, R extends GeoRenderState> extends GeoRenderLayer<T, Void, R> {
    private static final AtomicInteger IDS = new AtomicInteger();

    /** What to draw this frame. A null pass, or one with zero alpha, draws nothing. */
    public record Pass(Identifier texture, int argb) {}

    private final DataTicket<Pass> ticket = DataTicket.create("cnb_tinted_layer_" + IDS.getAndIncrement(), Pass.class);
    private final Function<T, @Nullable Pass> pass;
    private final Function<Identifier, RenderType> renderType;
    private final int order;

    public TintedTextureLayer(GeoRenderer<T, Void, R> renderer, Function<T, @Nullable Pass> pass,
                              Function<Identifier, RenderType> renderType, int order) {
        super(renderer);
        this.pass = pass;
        this.renderType = renderType;
        this.order = order;
    }

    @Override
    public void addRenderData(T animatable, @Nullable Void relatedObject, R renderState, float partialTick) {
        Pass p = this.pass.apply(animatable);
        if (p != null) {
            renderState.addGeckolibData(this.ticket, p);
        }
    }

    @Override
    public void submitRenderTask(RenderPassInfo<R> renderPassInfo, SubmitNodeCollector renderTasks) {
        if (!renderPassInfo.willRender()) {
            return;
        }
        Pass p = renderPassInfo.renderState().getGeckolibData(this.ticket);
        if (p == null || (p.argb() >>> 24) == 0) {
            return;
        }

        final RenderType type = this.renderType.apply(p.texture());
        final int packedLight = renderPassInfo.packedLight();
        final int packedOverlay = renderPassInfo.packedOverlay();
        final int color = p.argb();
        OrderedSubmitNodeCollector ordered = renderTasks.order(this.order);
        ordered.submitCustomGeometry(renderPassInfo.poseStack(), type, (pose, vertexConsumer) -> {
            final PoseStack poseStack = renderPassInfo.poseStack();
            poseStack.pushPose();
            poseStack.last().set(pose);
            renderPassInfo.renderPosed(() -> renderPassInfo.model().render(renderPassInfo, vertexConsumer, packedLight, packedOverlay, color));
            poseStack.popPose();
        });
    }
}
