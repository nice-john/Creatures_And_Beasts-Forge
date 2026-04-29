package com.cgessinger.creaturesandbeasts;

import com.cgessinger.creaturesandbeasts.client.CNBClient;
import com.cgessinger.creaturesandbeasts.client.entity.model.CactemSpearModel;
import com.cgessinger.creaturesandbeasts.client.entity.render.*;
import com.cgessinger.creaturesandbeasts.client.armor.render.FlowerCrownRenderer;
import com.cgessinger.creaturesandbeasts.client.armor.render.SporelingBackpackRenderer;
import com.cgessinger.creaturesandbeasts.init.CNBEntityTypes;
import com.cgessinger.creaturesandbeasts.init.CNBParticleTypes;
import com.cgessinger.creaturesandbeasts.items.FlowerCrownItem;
import com.cgessinger.creaturesandbeasts.items.GlowingFlowerCrownItem;
import com.cgessinger.creaturesandbeasts.items.SporelingBackpackItem;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import com.cgessinger.creaturesandbeasts.client.particle.CactemHealParticle;
import com.cgessinger.creaturesandbeasts.client.particle.MinipadFlowerParticle;

@Environment(EnvType.CLIENT)
public class CreaturesAndBeastsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        // ── Entity renderers ─────────────────────────────────────────────────
        EntityRendererRegistry.register(CNBEntityTypes.LITTLE_GREBE, LittleGrebeRenderer::new);
        EntityRendererRegistry.register(CNBEntityTypes.LIZARD,       LizardRenderer::new);
        EntityRendererRegistry.register(CNBEntityTypes.CINDERSHELL,  CindershellRenderer::new);
        EntityRendererRegistry.register(CNBEntityTypes.LILYTAD,      LilytadRenderer::new);
        EntityRendererRegistry.register(CNBEntityTypes.SPORELING,    SporelingRenderer::new);
        EntityRendererRegistry.register(CNBEntityTypes.YETI,         YetiRenderer::new);
        EntityRendererRegistry.register(CNBEntityTypes.MINIPAD,      MinipadRenderer::new);
        EntityRendererRegistry.register(CNBEntityTypes.END_WHALE,    EndWhaleRenderer::new);
        EntityRendererRegistry.register(CNBEntityTypes.CACTEM,       CactemRenderer::new);
        EntityRendererRegistry.register(CNBEntityTypes.LIZARD_EGG,
                ctx -> new ThrownItemRenderer<>(ctx, 1.0F, true));
        EntityRendererRegistry.register(CNBEntityTypes.THROWN_CACTEM_SPEAR,
                ThrownCactemSpearRenderer::new);

        // ── Model layers ─────────────────────────────────────────────────────
        EntityModelLayerRegistry.registerModelLayer(CactemSpearModel.LAYER_LOCATION,
                CactemSpearModel::createLayer);

        // ── GeckoLib armor renderers ─────────────────────────────────────────
        GeoArmorRenderer.registerFor(FlowerCrownItem.class,         FlowerCrownRenderer::new);
        GeoArmorRenderer.registerFor(GlowingFlowerCrownItem.class,  FlowerCrownRenderer::new);
        GeoArmorRenderer.registerFor(SporelingBackpackItem.class,   SporelingBackpackRenderer::new);

        // ── Particle factories ───────────────────────────────────────────────
        ParticleFactoryRegistry.getInstance().register(
                CNBParticleTypes.PINK_MINIPAD_FLOWER,       MinipadFlowerParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(
                CNBParticleTypes.LIGHT_PINK_MINIPAD_FLOWER, MinipadFlowerParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(
                CNBParticleTypes.YELLOW_MINIPAD_FLOWER,     MinipadFlowerParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(
                CNBParticleTypes.CACTEM_HEAL_PARTICLE,      CactemHealParticle.Factory::new);

        // ── Misc client setup ────────────────────────────────────────────────
        CNBClient.init();
    }
}
