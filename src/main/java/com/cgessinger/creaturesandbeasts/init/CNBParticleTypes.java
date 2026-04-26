package com.cgessinger.creaturesandbeasts.init;

import com.cgessinger.creaturesandbeasts.CreaturesAndBeasts;
import com.cgessinger.creaturesandbeasts.client.particle.CactemHealParticle;
import com.cgessinger.creaturesandbeasts.client.particle.MinipadFlowerParticle;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

@EventBusSubscriber(modid = CreaturesAndBeasts.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class CNBParticleTypes {

    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(Registries.PARTICLE_TYPE, CreaturesAndBeasts.MOD_ID);

    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> PINK_MINIPAD_FLOWER =
            PARTICLE_TYPES.register("pink_minipad_flower", () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> LIGHT_PINK_MINIPAD_FLOWER =
            PARTICLE_TYPES.register("light_pink_minipad_flower", () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> YELLOW_MINIPAD_FLOWER =
            PARTICLE_TYPES.register("yellow_minipad_flower", () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> CACTEM_HEAL_PARTICLE =
            PARTICLE_TYPES.register("heal", () -> new SimpleParticleType(false));

    @SubscribeEvent
    public static void registerParticleFactories(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(PINK_MINIPAD_FLOWER.get(), MinipadFlowerParticle.Factory::new);
        event.registerSpriteSet(LIGHT_PINK_MINIPAD_FLOWER.get(), MinipadFlowerParticle.Factory::new);
        event.registerSpriteSet(YELLOW_MINIPAD_FLOWER.get(), MinipadFlowerParticle.Factory::new);
        event.registerSpriteSet(CACTEM_HEAL_PARTICLE.get(), CactemHealParticle.Factory::new);
    }
}
