package com.cgessinger.creaturesandbeasts.init;

import com.cgessinger.creaturesandbeasts.CreaturesAndBeasts;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.List;

public class CNBArmorMaterials {
    public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS =
            DeferredRegister.create(Registries.ARMOR_MATERIAL, CreaturesAndBeasts.MOD_ID);

    public static final Holder<ArmorMaterial> FLOWER_CROWN = register(
            "flower_crown",
            mapOf(1, 2, 3, 1),
            5,
            0.0F,
            0.0F,
            () -> Ingredient.EMPTY
    );

    public static final Holder<ArmorMaterial> SPORELING_BACKPACK = register(
            "sporeling_backpack",
            mapOf(0, 0, 1, 0),
            2,
            0.0F,
            0.0F,
            () -> Ingredient.of(Items.LEATHER)
    );

    private static EnumMap<ArmorItem.Type, Integer> mapOf(int feet, int legs, int chest, int head) {
        EnumMap<ArmorItem.Type, Integer> map = new EnumMap<>(ArmorItem.Type.class);
        map.put(ArmorItem.Type.BOOTS, feet);
        map.put(ArmorItem.Type.LEGGINGS, legs);
        map.put(ArmorItem.Type.CHESTPLATE, chest);
        map.put(ArmorItem.Type.HELMET, head);
        map.put(ArmorItem.Type.BODY, chest);
        return map;
    }

    private static DeferredHolder<ArmorMaterial, ArmorMaterial> register(
            String name,
            EnumMap<ArmorItem.Type, Integer> defense,
            int enchantability,
            float toughness,
            float knockbackResistance,
            java.util.function.Supplier<Ingredient> repairIngredient
    ) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, name);
        return ARMOR_MATERIALS.register(name, () -> new ArmorMaterial(
                defense,
                enchantability,
                Holder.direct(SoundEvents.ARMOR_EQUIP_LEATHER.value()),
                repairIngredient,
                List.of(new ArmorMaterial.Layer(id)),
                toughness,
                knockbackResistance
        ));
    }
}
