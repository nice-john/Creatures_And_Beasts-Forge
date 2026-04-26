package com.cgessinger.creaturesandbeasts.world.gen;

import com.cgessinger.creaturesandbeasts.CreaturesAndBeasts;
import com.cgessinger.creaturesandbeasts.entities.CindershellEntity;
import com.cgessinger.creaturesandbeasts.entities.EndWhaleEntity;
import com.cgessinger.creaturesandbeasts.entities.LilytadEntity;
import com.cgessinger.creaturesandbeasts.entities.LittleGrebeEntity;
import com.cgessinger.creaturesandbeasts.entities.LizardEntity;
import com.cgessinger.creaturesandbeasts.entities.MinipadEntity;
import com.cgessinger.creaturesandbeasts.entities.SporelingEntity;
import com.cgessinger.creaturesandbeasts.entities.YetiEntity;
import com.cgessinger.creaturesandbeasts.init.CNBEntityTypes;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;

@EventBusSubscriber(modid = CreaturesAndBeasts.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModEntitySpawns {

    @SubscribeEvent
    public static void registerSpawnPlacements(RegisterSpawnPlacementsEvent event) {
        event.register(CNBEntityTypes.LITTLE_GREBE.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, LittleGrebeEntity::checkGrebeSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(CNBEntityTypes.LIZARD.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, LizardEntity::checkLizardSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(CNBEntityTypes.CINDERSHELL.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, CindershellEntity::checkCindershellSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(CNBEntityTypes.SPORELING.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SporelingEntity::checkSporelingSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(CNBEntityTypes.LILYTAD.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, LilytadEntity::checkLilytadSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(CNBEntityTypes.YETI.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, YetiEntity::checkMobSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(CNBEntityTypes.MINIPAD.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, MinipadEntity::checkMinipadSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(CNBEntityTypes.END_WHALE.get(), SpawnPlacementTypes.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EndWhaleEntity::checkEndWhaleSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
    }

    public static void entitySpawnPlacementRegistry() {
        // No-op stub kept so CreaturesAndBeasts#commonSetup still compiles.
        // Spawn placements are now registered via the RegisterSpawnPlacementsEvent above.
    }
}
