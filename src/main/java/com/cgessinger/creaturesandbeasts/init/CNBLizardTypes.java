package com.cgessinger.creaturesandbeasts.init;

import com.cgessinger.creaturesandbeasts.util.LizardType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static com.cgessinger.creaturesandbeasts.CreaturesAndBeasts.MOD_ID;

public class CNBLizardTypes {

    private static final List<LizardType> LIZARD_TYPES = new ArrayList<>(5);

    public static final LizardType DESERT   = registerWithCNBDirectory("lizard_item_desert",   MOD_ID, "desert");
    public static final LizardType DESERT_2 = registerWithCNBDirectory("lizard_item_desert_2", MOD_ID, "desert_2");
    public static final LizardType JUNGLE   = registerWithCNBDirectory("lizard_item_jungle",   MOD_ID, "jungle");
    public static final LizardType JUNGLE_2 = registerWithCNBDirectory("lizard_item_jungle_2", MOD_ID, "jungle_2");
    public static final LizardType MUSHROOM = registerWithCNBDirectory("lizard_item_mushroom", MOD_ID, "mushroom");

    private static LizardType registerWithCNBDirectory(String itemName, String namespace, String name) {
        // Fabric: look up item from BuiltInRegistries at access time (items are already registered)
        return registerWithCNBDirectory(
                () -> BuiltInRegistries.ITEM.get(new ResourceLocation(namespace, itemName)),
                namespace, name);
    }

    private static LizardType registerWithCNBDirectory(@Nullable Supplier<Item> spawnItem,
                                                        String namespace, String name) {
        return register(new LizardType(
                spawnItem,
                new ResourceLocation(namespace, name),
                new ResourceLocation(MOD_ID, "textures/entity/lizard/lizard_"     + name + ".png"),
                new ResourceLocation(MOD_ID, "textures/entity/lizard/sad_lizard_" + name + ".png")));
    }

    private static LizardType register(LizardType lizardType) {
        LIZARD_TYPES.add(lizardType);
        return lizardType;
    }

    public static void registerAll() {
        for (LizardType lizardType : LIZARD_TYPES) {
            LizardType.registerLizardType(lizardType);
        }
    }
}
