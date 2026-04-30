package com.cgessinger.creaturesandbeasts.init;

import com.cgessinger.creaturesandbeasts.CreaturesAndBeasts;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * 1.21 reworked {@link ArmorMaterial} from an interface (one-method-per-attribute) into a
 * registered record with a defense map, layer list, and Holder-based equip sound.
 */
public class CNBArmorMaterials {

    public static final Holder<ArmorMaterial> FLOWER_CROWN = register("flower_crown",
            defenseFor(1, 2, 3, 1),
            5,                              // enchantment value
            0.0F,                           // toughness
            0.0F,                           // knockback resistance
            () -> Ingredient.EMPTY);        // repair (none)

    public static final Holder<ArmorMaterial> SPORELING_BACKPACK = register("sporeling_backpack",
            defenseFor(0, 0, 1, 0),
            2,
            0.0F,
            0.0F,
            () -> Ingredient.of(Items.LEATHER));

    private static Map<ArmorItem.Type, Integer> defenseFor(int boots, int legs, int chest, int head) {
        Map<ArmorItem.Type, Integer> map = new EnumMap<>(ArmorItem.Type.class);
        map.put(ArmorItem.Type.BOOTS, boots);
        map.put(ArmorItem.Type.LEGGINGS, legs);
        map.put(ArmorItem.Type.CHESTPLATE, chest);
        map.put(ArmorItem.Type.HELMET, head);
        // 1.21 also has BODY (e.g. wolf armor) – use head value as a sane fallback for items
        // that don't actually equip in that slot.
        map.put(ArmorItem.Type.BODY, head);
        return map;
    }

    private static Holder<ArmorMaterial> register(String name,
                                                  Map<ArmorItem.Type, Integer> defense,
                                                  int enchantmentValue,
                                                  float toughness,
                                                  float knockbackResistance,
                                                  java.util.function.Supplier<Ingredient> repair) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, name);
        ArmorMaterial.Layer layer = new ArmorMaterial.Layer(id, "", false);
        ArmorMaterial material = new ArmorMaterial(
                defense,
                enchantmentValue,
                Holder.direct(SoundEvents.ARMOR_EQUIP_LEATHER.value()),
                repair,
                List.of(layer),
                toughness,
                knockbackResistance);
        return Registry.registerForHolder(BuiltInRegistries.ARMOR_MATERIAL, id, material);
    }

    public static void register() {
        // Static field initialisation registers everything when this class is loaded.
        CreaturesAndBeasts.LOGGER.debug("Registered CNB armor materials");
    }
}
