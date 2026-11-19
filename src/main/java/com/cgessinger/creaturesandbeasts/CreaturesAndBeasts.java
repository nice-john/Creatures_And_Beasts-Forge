package com.cgessinger.creaturesandbeasts;

import com.cgessinger.creaturesandbeasts.client.CNBClient;
import com.cgessinger.creaturesandbeasts.config.CNBConfig;
import com.cgessinger.creaturesandbeasts.events.CNBEvents;
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
import com.cgessinger.creaturesandbeasts.init.CNBParticleTypes;
import com.cgessinger.creaturesandbeasts.init.CNBSoundEvents;
import com.cgessinger.creaturesandbeasts.init.CNBSporelingTypes;
import com.cgessinger.creaturesandbeasts.world.gen.ModEntitySpawns;
import com.electronwill.nightconfig.core.io.ParsingException;
import net.minecraft.resources.Identifier;
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
        CNBSoundEvents.SOUND_EVENTS.register(eventBus);
        CNBEntityTypes.ENTITY_TYPES.register(eventBus);
        CNBLootModifiers.LOOT_MODIFIERS.register(eventBus);
        CNBBiomeModifiers.BIOME_MODIFIERS.register(eventBus);
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

        });
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            CNBClient.init();
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
    }
}
