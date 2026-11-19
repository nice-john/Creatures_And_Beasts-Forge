package com.cgessinger.creaturesandbeasts.init;

import com.cgessinger.creaturesandbeasts.CreaturesAndBeasts;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;

import java.util.EnumMap;
import java.util.Map;

public class CNBArmorMaterials {
    // Durability 0 keeps both pieces undamageable, matching the 1.21.1 items (no durability set).
    public static final ArmorMaterial FLOWER_CROWN = new ArmorMaterial(
            0,
            mapOf(1, 2, 3, 1),
            5,
            SoundEvents.ARMOR_EQUIP_LEATHER,
            0.0F,
            0.0F,
            CNBTags.Items.REPAIRS_FLOWER_CROWN,
            assetId("flower_crown")
    );

    public static final ArmorMaterial SPORELING_BACKPACK = new ArmorMaterial(
            0,
            mapOf(0, 0, 1, 0),
            2,
            SoundEvents.ARMOR_EQUIP_LEATHER,
            0.0F,
            0.0F,
            CNBTags.Items.REPAIRS_SPORELING_BACKPACK,
            assetId("sporeling_backpack")
    );

    private static Map<ArmorType, Integer> mapOf(int feet, int legs, int chest, int head) {
        EnumMap<ArmorType, Integer> map = new EnumMap<>(ArmorType.class);
        map.put(ArmorType.BOOTS, feet);
        map.put(ArmorType.LEGGINGS, legs);
        map.put(ArmorType.CHESTPLATE, chest);
        map.put(ArmorType.HELMET, head);
        map.put(ArmorType.BODY, chest);
        return map;
    }

    private static ResourceKey<EquipmentAsset> assetId(String name) {
        return ResourceKey.create(EquipmentAssets.ROOT_ID, Identifier.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, name));
    }
}
