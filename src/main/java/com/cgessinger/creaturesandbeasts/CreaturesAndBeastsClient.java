package com.cgessinger.creaturesandbeasts;

import com.cgessinger.creaturesandbeasts.client.CNBClient;
import com.cgessinger.creaturesandbeasts.client.entity.model.CactemSpearModel;
import com.cgessinger.creaturesandbeasts.client.entity.render.*;
import com.cgessinger.creaturesandbeasts.init.CNBEntityTypes;
import com.cgessinger.creaturesandbeasts.init.CNBParticleTypes;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
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
        // No-op here on Fabric — there is no equivalent of Forge's static
        // GeoArmorRenderer.registerFor(...). Renderer wiring lives on each
        // item via createRenderer / getRenderProvider returning a
        // RenderProvider that builds the GeoArmorRenderer lazily on the
        // client. See FlowerCrownItem and SporelingBackpackItem for the
        // implementations; GlowingFlowerCrownItem inherits the FlowerCrown
        // wiring through extension.

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
