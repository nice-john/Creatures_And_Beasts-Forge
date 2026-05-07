package com.cgessinger.creaturesandbeasts;

import com.cgessinger.creaturesandbeasts.client.CNBClient;
import com.cgessinger.creaturesandbeasts.config.CNBConfig;
import com.cgessinger.creaturesandbeasts.events.CNBEvents;
import com.cgessinger.creaturesandbeasts.init.CNBArmorMaterials;
import com.cgessinger.creaturesandbeasts.init.CNBBiomeModifiers;
import com.cgessinger.creaturesandbeasts.init.CNBBlocks;
import com.cgessinger.creaturesandbeasts.init.CNBContainerTypes;
import com.cgessinger.creaturesandbeasts.init.CNBCreativeTabs;
import com.cgessinger.creaturesandbeasts.init.CNBEntityTypes;
import com.cgessinger.creaturesandbeasts.init.CNBItems;
import com.cgessinger.creaturesandbeasts.init.CNBLilytadTypes;
import com.cgessinger.creaturesandbeasts.init.CNBLizardTypes;
import com.cgessinger.creaturesandbeasts.init.CNBLootModifiers;
import com.cgessinger.creaturesandbeasts.init.CNBMinipadTypes;
import com.cgessinger.creaturesandbeasts.init.CNBPaintingTypes;
import com.cgessinger.creaturesandbeasts.init.CNBParticleTypes;
import com.cgessinger.creaturesandbeasts.init.CNBSoundEvents;
import com.cgessinger.creaturesandbeasts.init.CNBSporelingTypes;
import com.cgessinger.creaturesandbeasts.world.gen.ModEntitySpawns;
import com.electronwill.nightconfig.core.io.ParsingException;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.common.NeoForge;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.infernalstudios.config.Config;

import java.io.IOException;

@Mod(CreaturesAndBeasts.MOD_ID)
public class CreaturesAndBeasts {
    public static final String MOD_ID = "cnb";
    public static final Logger LOGGER = LogManager.getLogger();

    public CreaturesAndBeasts(IEventBus eventBus, ModContainer modContainer) {
        eventBus.addListener(this::commonSetup);
        eventBus.addListener(this::clientSetup);
        eventBus.addListener(this::onEntityAttributeCreation);

        CNBParticleTypes.PARTICLE_TYPES.register(eventBus);
        CNBBlocks.BLOCKS.register(eventBus);
        CNBItems.ITEMS.register(eventBus);
        com.cgessinger.creaturesandbeasts.init.CNBDataComponents.COMPONENTS.register(eventBus);
        CNBContainerTypes.CONTAINER_TYPES.register(eventBus);
        CNBPaintingTypes.PAINTINGS.register(eventBus);
        CNBSoundEvents.SOUND_EVENTS.register(eventBus);
        CNBEntityTypes.ENTITY_TYPES.register(eventBus);
        CNBLootModifiers.LOOT_MODIFIERS.register(eventBus);
        CNBBiomeModifiers.BIOME_MODIFIERS.register(eventBus);
        CNBArmorMaterials.ARMOR_MATERIALS.register(eventBus);
        CNBCreativeTabs.CREATIVE_TABS.register(eventBus);

        CNBSporelingTypes.registerAll();
        CNBLizardTypes.registerAll();
        CNBLilytadTypes.registerAll();
        CNBMinipadTypes.registerAll();

        NeoForge.EVENT_BUS.register(new CNBEvents());

        try {
            CNBConfig.CONFIG = Config
                    .builder(FMLPaths.CONFIGDIR.get().resolve("creaturesandbeasts-common.toml"))
                    .loadClass(CNBConfig.class)
                    .build();
        } catch (IllegalStateException | IllegalArgumentException | IOException | ParsingException e) {
            throw new RuntimeException(
                    "Failed to load Creatures and Beasts config" +
                            (e instanceof ParsingException ? ", try fixing/deleting your config file" : ""), e);
        }

        CNBConfig.CONFIG.onReload(stage -> {
            if (stage == Config.ReloadStage.PRE) {
                CreaturesAndBeasts.LOGGER.debug("Reloading Creatures and Beasts config");
            }
        });
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            ModEntitySpawns.entitySpawnPlacementRegistry();

            FlowerPotBlock flowerPot = (FlowerPotBlock) Blocks.FLOWER_POT;
            flowerPot.addPlant(CNBBlocks.PINK_WATERLILY_BLOCK.getId(), CNBBlocks.POTTED_PINK_WATERLILY);
            flowerPot.addPlant(CNBBlocks.LIGHT_PINK_WATERLILY_BLOCK.getId(), CNBBlocks.POTTED_LIGHT_PINK_WATERLILY);
            flowerPot.addPlant(CNBBlocks.YELLOW_WATERLILY_BLOCK.getId(), CNBBlocks.POTTED_YELLOW_WATERLILY);
        });
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            CNBClient.init();
            ItemProperties.register(
                    CNBItems.CACTEM_SPEAR.get(),
                    ResourceLocation.fromNamespaceAndPath(MOD_ID, "throwing"),
                    (stack, level, entity, seed) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F);
        });
    }

    private void onEntityAttributeCreation(net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent event) {
        event.put(CNBEntityTypes.CINDERSHELL.get(), com.cgessinger.creaturesandbeasts.entities.CindershellEntity.createAttributes().build());
        event.put(CNBEntityTypes.SPORELING.get(), com.cgessinger.creaturesandbeasts.entities.SporelingEntity.createAttributes().build());
        event.put(CNBEntityTypes.LITTLE_GREBE.get(), com.cgessinger.creaturesandbeasts.entities.LittleGrebeEntity.createAttributes().build());
        event.put(CNBEntityTypes.LILYTAD.get(), com.cgessinger.creaturesandbeasts.entities.LilytadEntity.createAttributes().build());
        event.put(CNBEntityTypes.LIZARD.get(), com.cgessinger.creaturesandbeasts.entities.LizardEntity.createAttributes().build());
        event.put(CNBEntityTypes.YETI.get(), com.cgessinger.creaturesandbeasts.entities.YetiEntity.createAttributes().build());
        event.put(CNBEntityTypes.MINIPAD.get(), com.cgessinger.creaturesandbeasts.entities.MinipadEntity.createAttributes().build());
        event.put(CNBEntityTypes.END_WHALE.get(), com.cgessinger.creaturesandbeasts.entities.EndWhaleEntity.createAttributes().build());
        event.put(CNBEntityTypes.CACTEM.get(), com.cgessinger.creaturesandbeasts.entities.CactemEntity.createAttributes().build());
        event.put(CNBEntityTypes.CHESHIRE_TIGER.get(), com.cgessinger.creaturesandbeasts.entities.CheshireTigerEntity.createAttributes().build());
    }
}
