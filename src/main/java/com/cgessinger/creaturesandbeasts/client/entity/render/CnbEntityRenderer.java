package com.cgessinger.creaturesandbeasts.client.entity.render;

import com.geckolib.animatable.GeoAnimatable;
import com.geckolib.constant.dataticket.DataTicket;
import com.geckolib.model.GeoModel;
import com.geckolib.renderer.GeoEntityRenderer;
import com.geckolib.renderer.base.BoneSnapshots;
import com.geckolib.renderer.base.RenderPassInfo;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.api.distmarker.Dist;
import org.jspecify.annotations.Nullable;

import java.util.function.Function;

/** Shared behaviour of the mod's GeckoLib mob renderers: baby/adult scaling, render type, optional head tracking. */
public abstract class CnbEntityRenderer<T extends LivingEntity & GeoAnimatable> extends GeoEntityRenderer<T, LivingEntityRenderState> {
    public static final DataTicket<Boolean> BABY = DataTicket.create("cnb_baby", Boolean.class);

    private final float babyScale;
    private final float adultScale;
    private final boolean headLook;
    private final Function<Identifier, RenderType> renderType;

    protected CnbEntityRenderer(EntityRendererProvider.Context context, GeoModel<T> model, float shadowRadius,
                                float babyScale, float adultScale, boolean headLook, Function<Identifier, RenderType> renderType) {
        super(context, model);
        this.shadowRadius = shadowRadius;
        this.babyScale = babyScale;
        this.adultScale = adultScale;
        this.headLook = headLook;
        this.renderType = renderType;
    }

    @Override
    public void addRenderData(T animatable, @Nullable Void relatedObject, LivingEntityRenderState renderState, float partialTick) {
        super.addRenderData(animatable, relatedObject, renderState, partialTick);
        renderState.addGeckolibData(BABY, animatable.isBaby());
    }

    protected static boolean isBaby(RenderPassInfo<LivingEntityRenderState> info) {
        return info.renderState().getOrDefaultGeckolibData(BABY, false);
    }

    @Override
    public void scaleModelForRender(RenderPassInfo<LivingEntityRenderState> info, float widthScale, float heightScale) {
        float scale = isBaby(info) ? this.babyScale : this.adultScale;
        super.scaleModelForRender(info, widthScale * scale, heightScale * scale);
    }

    @Override
    public void adjustModelBonesForRender(RenderPassInfo<LivingEntityRenderState> info, BoneSnapshots snapshots) {
        super.adjustModelBonesForRender(info, snapshots);
        if (this.headLook) {
            HeadLook.apply(info, snapshots, headYawOnZ(info));
        }
    }

    protected boolean headYawOnZ(RenderPassInfo<LivingEntityRenderState> info) {
        return false;
    }

    @Override
    public @Nullable RenderType getRenderType(LivingEntityRenderState renderState, Identifier texture) {
        return this.renderType.apply(texture);
    }
}
