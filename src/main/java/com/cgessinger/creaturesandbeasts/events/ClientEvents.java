package com.cgessinger.creaturesandbeasts.events;

import com.cgessinger.creaturesandbeasts.CreaturesAndBeasts;
import com.cgessinger.creaturesandbeasts.client.armor.render.FlowerCrownRenderer;
import com.cgessinger.creaturesandbeasts.client.armor.render.SporelingBackpackRenderer;
import com.cgessinger.creaturesandbeasts.client.entity.model.CactemSpearModel;
import com.cgessinger.creaturesandbeasts.client.entity.render.CactemRenderer;
import com.cgessinger.creaturesandbeasts.client.entity.render.CindershellRenderer;
import com.cgessinger.creaturesandbeasts.client.entity.render.EndWhaleRenderer;
import com.cgessinger.creaturesandbeasts.client.entity.render.LilytadRenderer;
import com.cgessinger.creaturesandbeasts.client.entity.render.LittleGrebeRenderer;
import com.cgessinger.creaturesandbeasts.client.entity.render.LizardRenderer;
import com.cgessinger.creaturesandbeasts.client.entity.render.MinipadRenderer;
import com.cgessinger.creaturesandbeasts.client.entity.render.SporelingRenderer;
import com.cgessinger.creaturesandbeasts.client.entity.render.ThrownCactemSpearRenderer;
import com.cgessinger.creaturesandbeasts.client.entity.render.YetiRenderer;
import com.cgessinger.creaturesandbeasts.init.CNBEntityTypes;
import com.cgessinger.creaturesandbeasts.init.CNBItems;
import com.cgessinger.creaturesandbeasts.items.FlowerCrownItem;
import com.cgessinger.creaturesandbeasts.items.GlowingFlowerCrownItem;
import com.cgessinger.creaturesandbeasts.items.SporelingBackpackItem;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.neoforged.api.distmarker.Dist;
import com.cgessinger.creaturesandbeasts.client.gui.screens.inventory.CinderFurnaceScreen;
import com.cgessinger.creaturesandbeasts.init.CNBContainerTypes;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import software.bernie.geckolib.renderer.GeoArmorRenderer;


@EventBusSubscriber(modid = CreaturesAndBeasts.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(CNBEntityTypes.LITTLE_GREBE.get(), LittleGrebeRenderer::new);
        event.registerEntityRenderer(CNBEntityTypes.LIZARD.get(), LizardRenderer::new);
        event.registerEntityRenderer(CNBEntityTypes.CINDERSHELL.get(), CindershellRenderer::new);
        event.registerEntityRenderer(CNBEntityTypes.LILYTAD.get(), LilytadRenderer::new);
        event.registerEntityRenderer(CNBEntityTypes.SPORELING.get(), SporelingRenderer::new);
        event.registerEntityRenderer(CNBEntityTypes.YETI.get(), YetiRenderer::new);
        event.registerEntityRenderer(CNBEntityTypes.MINIPAD.get(), MinipadRenderer::new);
        event.registerEntityRenderer(CNBEntityTypes.END_WHALE.get(), EndWhaleRenderer::new);
        event.registerEntityRenderer(CNBEntityTypes.CACTEM.get(), CactemRenderer::new);
        event.registerEntityRenderer(CNBEntityTypes.CHESHIRE_TIGER.get(), com.cgessinger.creaturesandbeasts.client.entity.render.CheshireTigerRenderer::new);
        event.registerEntityRenderer(CNBEntityTypes.LIZARD_EGG.get(), manager -> new ThrownItemRenderer<>(manager, 1.0F, true));
        event.registerEntityRenderer(CNBEntityTypes.THROWN_CACTEM_SPEAR.get(), ThrownCactemSpearRenderer::new);
    }

    @SubscribeEvent
    public static void onRegisterLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(CactemSpearModel.LAYER_LOCATION, CactemSpearModel::createLayer);
    }

    @SubscribeEvent
    public static void onRegisterMenuScreens(RegisterMenuScreensEvent event) {
        event.register(CNBContainerTypes.CINDER_FURNACE_CONTAINER.get(), CinderFurnaceScreen::new);
    }

    /**
     * Disable vanilla SpawnEggItem two-color tint on the spawn eggs that have
     * custom full-color sprites. Vanilla's bulk ItemColor handler multiplies
     * layer 0 by the egg's backgroundColor (and layer 1 by highlightColor), so
     * a textured layer0 with item/generated parent gets washed-out by the
     * primary color even though it's a solid sprite. Registering a per-item
     * passthrough (0xFFFFFFFF = white = no tint) over the top wins because
     * ItemColors is a last-write-wins map keyed by Item.
     */
    @SubscribeEvent
    public static void onRegisterColorHandlers(RegisterColorHandlersEvent.Item event) {
        event.register((stack, layer) -> 0xFFFFFFFF,
                CNBItems.CACTEM_SPAWN_EGG.get(),
                CNBItems.CINDERSHELL_SPAWN_EGG.get(),
                CNBItems.END_WHALE_SPAWN_EGG.get(),
                CNBItems.LILYTAD_SPAWN_EGG.get(),
                CNBItems.MINIPAD_SPAWN_EGG.get(),
                CNBItems.YETI_SPAWN_EGG.get(),
                CNBItems.SPORELING_NETHER_EGG.get(),
                CNBItems.SPORELING_OVERWORLD_EGG.get());
    }

    /*@SubscribeEvent
    public static void registerArmorRenderers(EntityRenderersEvent.AddLayers event) {
        GeoArmorRenderer.registerFor(FlowerCrownItem.class, () -> new FlowerCrownRenderer());
        GeoArmorRenderer.registerFor(GlowingFlowerCrownItem.class, () -> new FlowerCrownRenderer());
        GeoArmorRenderer.registerFor(SporelingBackpackItem.class, () -> new SporelingBackpackRenderer());
    }*/
}
