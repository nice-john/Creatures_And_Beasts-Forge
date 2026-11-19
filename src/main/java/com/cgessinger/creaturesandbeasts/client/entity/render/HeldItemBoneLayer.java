package com.cgessinger.creaturesandbeasts.client.entity.render;

import com.geckolib.animatable.GeoAnimatable;
import com.geckolib.cache.model.GeoBone;
import com.geckolib.constant.dataticket.DataTicket;
import com.geckolib.renderer.base.GeoRenderState;
import com.geckolib.renderer.base.GeoRenderer;
import com.geckolib.renderer.layer.builtin.BlockAndItemGeoLayer;
import com.geckolib.util.RenderUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.util.Mth;
import org.joml.Quaternionf;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.function.Function;

/** Renders a single item on a named bone, with an entity-dependent pose (translate / rotate / scale). */
public class HeldItemBoneLayer<T extends LivingEntity & GeoAnimatable, R extends GeoRenderState> extends BlockAndItemGeoLayer<T, Void, R> {
    /** Pose applied around the item in the bone's local space: translate, then rotate, then scale. */
    public record Pose(float scale, float tx, float ty, float tz, Quaternionf rotation) {
        public static Pose at(float tx, float ty, float tz) {
            return new Pose(1.0F, tx, ty, tz, new Quaternionf());
        }

        public Pose scaled(float s) {
            return new Pose(s, this.tx, this.ty, this.tz, this.rotation);
        }

        public Pose rotX(float degrees) {
            return new Pose(this.scale, this.tx, this.ty, this.tz, new Quaternionf(this.rotation).rotateX(degrees * Mth.DEG_TO_RAD));
        }

        public Pose rotY(float degrees) {
            return new Pose(this.scale, this.tx, this.ty, this.tz, new Quaternionf(this.rotation).rotateY(degrees * Mth.DEG_TO_RAD));
        }

        public Pose rotZ(float degrees) {
            return new Pose(this.scale, this.tx, this.ty, this.tz, new Quaternionf(this.rotation).rotateZ(degrees * Mth.DEG_TO_RAD));
        }
    }

    private final DataTicket<Pose> poseTicket;
    private final String boneName;
    private final ItemDisplayContext displayContext;
    private final Function<T, ItemStack> stack;
    private final Function<T, Pose> pose;

    public HeldItemBoneLayer(EntityRendererProvider.Context context, GeoRenderer<T, Void, R> renderer, String boneName,
                             ItemDisplayContext displayContext, Function<T, ItemStack> stack, Function<T, Pose> pose) {
        super(context, renderer);
        this.boneName = boneName;
        this.displayContext = displayContext;
        this.stack = stack;
        this.pose = pose;
        this.poseTicket = DataTicket.create("cnb_held_pose_" + boneName, Pose.class);
    }

    @Override
    protected List<RenderData> getRelevantBones(T animatable, @Nullable Void relatedObject, R renderState, float partialTick) {
        ItemStack held = this.stack.apply(animatable);
        if (held.isEmpty()) {
            return List.of();
        }
        ItemStackRenderState itemState = RenderUtil.createRenderStateForItem(held, this.itemModelResolver, this.displayContext, animatable);
        return List.of(RenderData.item(this.boneName, this.displayContext, itemState));
    }

    @Override
    public void addRenderData(T animatable, @Nullable Void relatedObject, R renderState, float partialTick) {
        List<RenderData> contents = getRelevantBones(animatable, relatedObject, renderState, partialTick);
        if (!contents.isEmpty()) {
            renderState.addGeckolibData(BlockAndItemGeoLayer.CONTENTS, contents);
            renderState.addGeckolibData(this.poseTicket, this.pose.apply(animatable));
        }
    }

    @Override
    protected void submitItemStackRender(PoseStack poseStack, GeoBone bone, ItemStackRenderState stackState, ItemDisplayContext displayContext,
                                         R renderState, SubmitNodeCollector renderTasks, int packedLight) {
        Pose p = renderState.getOrDefaultGeckolibData(this.poseTicket, Pose.at(0, 0, 0));
        poseStack.pushPose();
        poseStack.translate(p.tx(), p.ty(), p.tz());
        poseStack.rotate(p.rotation());
        if (p.scale() != 1.0F) poseStack.scale(p.scale(), p.scale(), p.scale());
        super.submitItemStackRender(poseStack, bone, stackState, displayContext, renderState, renderTasks, packedLight);
        poseStack.popPose();
    }
}
