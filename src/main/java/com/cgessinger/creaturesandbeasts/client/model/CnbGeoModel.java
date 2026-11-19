package com.cgessinger.creaturesandbeasts.client.model;

import com.geckolib.animatable.GeoAnimatable;
import com.geckolib.constant.dataticket.DataTicket;
import com.geckolib.model.GeoModel;
import com.geckolib.renderer.base.GeoRenderState;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.resources.Identifier;

/**
 * GeckoLib 5 resolves model/texture from the render state (captured on the render thread), not
 * from the live animatable. This base class keeps the old "pick a resource per entity" style:
 * subclasses implement {@link #resolveModel}/{@link #resolveTexture} against the animatable, and
 * the choice is captured into the render state once per frame.
 */
@Environment(EnvType.CLIENT)
public abstract class CnbGeoModel<T extends GeoAnimatable> extends GeoModel<T> {
    private static final DataTicket<Identifier> MODEL_ID = DataTicket.create("cnb_model", Identifier.class);
    private static final DataTicket<Identifier> TEXTURE_ID = DataTicket.create("cnb_texture", Identifier.class);

    protected abstract Identifier resolveModel(T animatable);

    protected abstract Identifier resolveTexture(T animatable);

    @Override
    public void addAdditionalStateData(T animatable, Object relatedObject, GeoRenderState renderState) {
        renderState.addGeckolibData(MODEL_ID, this.resolveModel(animatable));
        renderState.addGeckolibData(TEXTURE_ID, this.resolveTexture(animatable));
    }

    @Override
    public Identifier getModelResource(GeoRenderState renderState) {
        return renderState.getGeckolibData(MODEL_ID);
    }

    @Override
    public Identifier getTextureResource(GeoRenderState renderState) {
        return renderState.getGeckolibData(TEXTURE_ID);
    }
}