package com.cgessinger.creaturesandbeasts.client.entity.model;

import com.geckolib.animatable.GeoAnimatable;
import com.geckolib.constant.dataticket.DataTicket;
import com.geckolib.model.GeoModel;
import com.geckolib.renderer.base.GeoRenderState;
import net.minecraft.resources.Identifier;
import net.neoforged.api.distmarker.Dist;
import org.jspecify.annotations.Nullable;

/**
 * GeckoLib 5 resolves model/texture from the render state, which no longer carries the entity. This
 * base class evaluates the (entity-dependent) selection once while the state is captured and stashes
 * the result, so concrete models only describe how to choose a model and a texture for an entity.
 */
public abstract class CnbGeoModel<T extends GeoAnimatable> extends GeoModel<T> {
    private static final DataTicket<Identifier> MODEL = DataTicket.create("cnb_model", Identifier.class);
    private static final DataTicket<Identifier> TEXTURE = DataTicket.create("cnb_texture", Identifier.class);

    protected abstract Identifier selectModel(T animatable);

    protected abstract Identifier selectTexture(T animatable);

    @Override
    public void addAdditionalStateData(T animatable, @Nullable Object relatedObject, GeoRenderState renderState) {
        renderState.addGeckolibData(MODEL, selectModel(animatable));
        renderState.addGeckolibData(TEXTURE, selectTexture(animatable));
    }

    @Override
    public Identifier getModelResource(GeoRenderState renderState) {
        return renderState.getGeckolibData(MODEL);
    }

    @Override
    public Identifier getTextureResource(GeoRenderState renderState) {
        return renderState.getGeckolibData(TEXTURE);
    }
}
