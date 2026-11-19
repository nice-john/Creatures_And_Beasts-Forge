package com.cgessinger.creaturesandbeasts.init;

import com.cgessinger.creaturesandbeasts.CreaturesAndBeasts;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;

import java.util.EnumMap;
import java.util.Map;

/**
 * 26.x ArmorMaterial is a plain (unregistered) record that references an equipment asset
 * ({@code assets/cnb/equipment/<name>.json}). Durability 0 is intentional: these items are
 * built without {@code humanoidArmor(...)} (see {@link CNBItems#armorProperties}) so they stay
 * unbreakable like the 1.21.1 versions.
 */
public final class CNBArmorMaterials {

    public static final ArmorMaterial FLOWER_CROWN = material("flower_crown",
            defenseFor(1, 2, 3, 1),
            5,
            CNBTags.Items.REPAIRS_FLOWER_CROWN);

    public static final ArmorMaterial GLOWING_FLOWER_CROWN = material("flower_crown",
            defenseFor(1, 2, 3, 1),
            5,
            CNBTags.Items.REPAIRS_GLOWING_FLOWER_CROWN);

    public static final ArmorMaterial SPORELING_BACKPACK = material("sporeling_backpack",
            defenseFor(0, 0, 1, 0),
            2,
            ItemTags.REPAIRS_LEATHER_ARMOR);

    private static Map<ArmorType, Integer> defenseFor(int boots, int legs, int chest, int head) {
        Map<ArmorType, Integer> map = new EnumMap<>(ArmorType.class);
        map.put(ArmorType.BOOTS, boots);
        map.put(ArmorType.LEGGINGS, legs);
        map.put(ArmorType.CHESTPLATE, chest);
        map.put(ArmorType.HELMET, head);
        map.put(ArmorType.BODY, chest);
        return map;
    }

    private static ArmorMaterial material(String assetName, Map<ArmorType, Integer> defense, int enchantmentValue,
                                          TagKey<Item> repair) {
        ResourceKey<EquipmentAsset> asset = ResourceKey.create(EquipmentAssets.ROOT_ID,
                Identifier.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, assetName));
        return new ArmorMaterial(0, defense, enchantmentValue, SoundEvents.ARMOR_EQUIP_LEATHER, 0.0F, 0.0F, repair, asset);
    }

    public static void register() {
        CreaturesAndBeasts.LOGGER.debug("Loaded CNB armor materials");
    }

    private CNBArmorMaterials() {}
}
