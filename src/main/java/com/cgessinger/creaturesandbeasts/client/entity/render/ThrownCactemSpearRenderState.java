package com.cgessinger.creaturesandbeasts.client.entity.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.state.EntityRenderState;

@Environment(EnvType.CLIENT)
public class ThrownCactemSpearRenderState extends EntityRenderState {
    public float xRot;
    public float yRot;
    public boolean isFoil;
}