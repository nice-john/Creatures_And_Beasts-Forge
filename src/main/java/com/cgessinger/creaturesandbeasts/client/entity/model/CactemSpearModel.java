package com.cgessinger.creaturesandbeasts.client.entity.model;

import com.cgessinger.creaturesandbeasts.CreaturesAndBeasts;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Unit;
import net.neoforged.api.distmarker.Dist;
public class CactemSpearModel extends Model<Unit> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Identifier.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "thrown_cactem_spear"), "main");

    public CactemSpearModel(ModelPart root) {
        super(root, id -> RenderTypes.entityCutout(id));
    }

    public static LayerDefinition createLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        partdefinition.addOrReplaceChild("spear", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 18.0F, new CubeDeformation(0.0F))
                .texOffs(0, 12).addBox(-0.5F, -2.0F, -6.0F, 0.0F, 3.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-2.0F, -0.5F, -6.0F, 3.0F, 0.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(0, 4).addBox(-1.5F, -1.5F, 0.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.ZERO);
        return LayerDefinition.create(meshdefinition, 64, 64);
    }
}