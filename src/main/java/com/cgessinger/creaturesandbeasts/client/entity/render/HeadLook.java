package com.cgessinger.creaturesandbeasts.client.entity.render;

import com.geckolib.constant.DataTickets;
import com.geckolib.renderer.base.BoneSnapshots;
import com.geckolib.renderer.base.GeoRenderState;
import com.geckolib.renderer.base.RenderPassInfo;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;

/** Head tracking for the {@code head_rotation} bone, replacing GeckoLib 4's setCustomAnimations hook. */
public final class HeadLook {
    private HeadLook() {}

    /**
     * @param yawOnZ the baby yeti's head bone is modelled lying down, so it yaws around Z instead of Y
     */
    public static <R extends GeoRenderState> void apply(RenderPassInfo<R> info, BoneSnapshots snapshots, boolean yawOnZ) {
        snapshots.get("head_rotation").ifPresent(bone -> {
            float pitch = info.getOrDefaultGeckolibData(DataTickets.ENTITY_PITCH, 0f);
            float yaw = info.getOrDefaultGeckolibData(DataTickets.ENTITY_YAW, 0f);
            // GeckoLib 5 mirrors both axes relative to 4.x (see DefaultAnimations#hardcodedHeadRotation).
            bone.setRotX(-pitch * Mth.DEG_TO_RAD);
            if (yawOnZ) {
                bone.setRotZ(-yaw * Mth.DEG_TO_RAD);
            } else {
                bone.setRotY(-yaw * Mth.DEG_TO_RAD);
            }
        });
    }
}
