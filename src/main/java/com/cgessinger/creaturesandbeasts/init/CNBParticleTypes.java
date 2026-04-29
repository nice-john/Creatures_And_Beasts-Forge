package com.cgessinger.creaturesandbeasts.init;

import com.cgessinger.creaturesandbeasts.CreaturesAndBeasts;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

public class CNBParticleTypes {

    public static final SimpleParticleType PINK_MINIPAD_FLOWER       = register("pink_minipad_flower");
    public static final SimpleParticleType LIGHT_PINK_MINIPAD_FLOWER = register("light_pink_minipad_flower");
    public static final SimpleParticleType YELLOW_MINIPAD_FLOWER     = register("yellow_minipad_flower");
    public static final SimpleParticleType CACTEM_HEAL_PARTICLE      = register("heal");

    // ─────────────────────────────────────────────────────────────────────────

    private static SimpleParticleType register(String name) {
        return Registry.register(BuiltInRegistries.PARTICLE_TYPE,
                new ResourceLocation(CreaturesAndBeasts.MOD_ID, name),
                new SimpleParticleType(false));
    }

    public static void register() {
        // Triggers static initialisation of all fields above.
        CreaturesAndBeasts.LOGGER.debug("Registered CNB particle types");
    }
}
