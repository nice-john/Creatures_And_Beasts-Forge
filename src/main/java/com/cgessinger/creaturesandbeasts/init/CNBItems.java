package com.cgessinger.creaturesandbeasts.init;

import com.cgessinger.creaturesandbeasts.CreaturesAndBeasts;
import com.cgessinger.creaturesandbeasts.items.*;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.Equippable;
import net.minecraft.world.level.material.Fluids;

import java.util.function.Function;

public class CNBItems {

    // Food
    public static final Item APPLE_SLICE = register("apple_slice", Item::new,
            new Item.Properties().food(new FoodProperties.Builder().nutrition(1).saturationModifier(0.3F).build()));

    public static final WaterlilyBlockItem PINK_WATERLILY = registerBlockItem("pink_waterlily",
            props -> new WaterlilyBlockItem(CNBBlocks.PINK_WATERLILY_BLOCK, props), waterlilyProperties());

    public static final WaterlilyBlockItem LIGHT_PINK_WATERLILY = registerBlockItem("light_pink_waterlily",
            props -> new WaterlilyBlockItem(CNBBlocks.LIGHT_PINK_WATERLILY_BLOCK, props), waterlilyProperties());

    public static final WaterlilyBlockItem YELLOW_WATERLILY = registerBlockItem("yellow_waterlily",
            props -> new WaterlilyBlockItem(CNBBlocks.YELLOW_WATERLILY_BLOCK, props), waterlilyProperties());

    // Bucketed Mobs
    public static final CNBEntityBucketItem CINDERSHELL_BUCKET = register("cindershell_bucket",
            props -> new CNBEntityBucketItem(() -> CNBEntityTypes.CINDERSHELL, Fluids.LAVA,
                    () -> SoundEvents.BUCKET_EMPTY_LAVA, props),
            new Item.Properties().stacksTo(1));

    // Misc. Items
    public static final Item ENTITY_NET = register("entity_net", Item::new, new Item.Properties().durability(64));
    public static final LizardEggItem LIZARD_EGG = registerBlockItem("lizard_egg",
            props -> new LizardEggItem(CNBBlocks.LIZARD_EGGS, props), new Item.Properties().stacksTo(16));
    public static final CNBFuelItem CINDERSHELL_SHELL_SHARD = register("cindershell_shell_shard",
            CNBFuelItem::new, CNBFuelItem.fuelProperties(6400));
    public static final Item YETI_ANTLER = register("yeti_antler", Item::new, new Item.Properties());
    public static final Item YETI_HIDE = register("yeti_hide", Item::new, new Item.Properties());

    public static final Item PINK_MINIPAD_FLOWER        = register("pink_minipad_flower",       Item::new, new Item.Properties());
    public static final Item LIGHT_PINK_MINIPAD_FLOWER  = register("light_pink_minipad_flower", Item::new, new Item.Properties());
    public static final Item YELLOW_MINIPAD_FLOWER      = register("yellow_minipad_flower",     Item::new, new Item.Properties());

    public static final Item PINK_MINIPAD_FLOWER_GLOW       = register("pink_minipad_flower_glow",       MinipadFlowerGlowItem::new, new Item.Properties());
    public static final Item LIGHT_PINK_MINIPAD_FLOWER_GLOW = register("light_pink_minipad_flower_glow", MinipadFlowerGlowItem::new, new Item.Properties());
    public static final Item YELLOW_MINIPAD_FLOWER_GLOW     = register("yellow_minipad_flower_glow",     MinipadFlowerGlowItem::new, new Item.Properties());

    public static final HealSpellBookItem HEAL_SPELL_BOOK_1 = register("heal_spell_book_1", HealSpellBookItem::new, new Item.Properties().stacksTo(1));
    public static final HealSpellBookItem HEAL_SPELL_BOOK_2 = register("heal_spell_book_2", HealSpellBookItem::new, new Item.Properties().stacksTo(1));
    public static final HealSpellBookItem HEAL_SPELL_BOOK_3 = register("heal_spell_book_3", HealSpellBookItem::new, new Item.Properties().stacksTo(1));

    // Armor
    public static final FlowerCrownItem FLOWER_CROWN = register("flower_crown", FlowerCrownItem::new,
            armorProperties(new Item.Properties(), CNBArmorMaterials.FLOWER_CROWN, ArmorType.HELMET));

    public static final GlowingFlowerCrownItem GLOWING_FLOWER_CROWN = register("glowing_flower_crown", GlowingFlowerCrownItem::new,
            armorProperties(new Item.Properties(), CNBArmorMaterials.GLOWING_FLOWER_CROWN, ArmorType.HELMET));

    public static final SporelingBackpackItem SPORELING_BACKPACK = register("sporeling_backpack", SporelingBackpackItem::new,
            armorProperties(new Item.Properties(), CNBArmorMaterials.SPORELING_BACKPACK, ArmorType.CHESTPLATE));

    // Tools
    public static final CinderSwordItem CINDER_SWORD   = register("cinder_sword",   props -> new CinderSwordItem(0, props), new Item.Properties().sword(CNBItemTiers.CINDER, 3, -2.4F));
    public static final CinderSwordItem CINDER_SWORD_1 = register("cinder_sword_1", props -> new CinderSwordItem(1, props), new Item.Properties().sword(CNBItemTiers.CINDER, 4, -2.4F));
    public static final CinderSwordItem CINDER_SWORD_2 = register("cinder_sword_2", props -> new CinderSwordItem(2, props), new Item.Properties().sword(CNBItemTiers.CINDER, 5, -2.4F));
    public static final CinderSwordItem CINDER_SWORD_3 = register("cinder_sword_3", props -> new CinderSwordItem(3, props), new Item.Properties().sword(CNBItemTiers.CINDER, 6, -2.4F));
    public static final CinderSwordItem CINDER_SWORD_4 = register("cinder_sword_4", props -> new CinderSwordItem(4, props), new Item.Properties().sword(CNBItemTiers.CINDER, 7, -2.4F));

    public static final SpearItem CACTEM_SPEAR = register("cactem_spear", SpearItem::new,
            SpearItem.spearProperties(new Item.Properties().durability(100)));

    // Spawn Eggs (26.x: the egg's entity type lives in the SPAWN_EGG-ish component set by Properties#spawnEgg)
    public static final SpawnEggItem GREBE_SPAWN_EGG       = register("little_grebe_spawn_egg", SpawnEggItem::new, new Item.Properties().spawnEgg(CNBEntityTypes.LITTLE_GREBE));
    public static final SpawnEggItem CINDERSHELL_SPAWN_EGG = register("cindershell_spawn_egg",  SpawnEggItem::new, new Item.Properties().spawnEgg(CNBEntityTypes.CINDERSHELL));
    public static final SpawnEggItem LILYTAD_SPAWN_EGG     = register("lilytad_spawn_egg",      SpawnEggItem::new, new Item.Properties().spawnEgg(CNBEntityTypes.LILYTAD));
    public static final SpawnEggItem YETI_SPAWN_EGG        = register("yeti_spawn_egg",         SpawnEggItem::new, new Item.Properties().spawnEgg(CNBEntityTypes.YETI));
    public static final SpawnEggItem MINIPAD_SPAWN_EGG     = register("minipad_spawn_egg",      SpawnEggItem::new, new Item.Properties().spawnEgg(CNBEntityTypes.MINIPAD));
    public static final SpawnEggItem LIZARD_SPAWN_EGG      = register("lizard_spawn_egg",       SpawnEggItem::new, new Item.Properties().spawnEgg(CNBEntityTypes.LIZARD));
    public static final SpawnEggItem END_WHALE_SPAWN_EGG   = register("end_whale_spawn_egg",    SpawnEggItem::new, new Item.Properties().spawnEgg(CNBEntityTypes.END_WHALE));
    public static final SpawnEggItem CACTEM_SPAWN_EGG      = register("cactem_spawn_egg",       SpawnEggItem::new, new Item.Properties().spawnEgg(CNBEntityTypes.CACTEM));

    // Lizard type spawn items
    public static final LizardItem LIZARD_ITEM_DESERT   = register("lizard_item_desert",   props -> new LizardItem(CNBLizardTypes.DESERT, props),   new Item.Properties().spawnEgg(CNBEntityTypes.LIZARD));
    public static final LizardItem LIZARD_ITEM_DESERT_2 = register("lizard_item_desert_2", props -> new LizardItem(CNBLizardTypes.DESERT_2, props), new Item.Properties().spawnEgg(CNBEntityTypes.LIZARD));
    public static final LizardItem LIZARD_ITEM_JUNGLE   = register("lizard_item_jungle",   props -> new LizardItem(CNBLizardTypes.JUNGLE, props),   new Item.Properties().spawnEgg(CNBEntityTypes.LIZARD));
    public static final LizardItem LIZARD_ITEM_JUNGLE_2 = register("lizard_item_jungle_2", props -> new LizardItem(CNBLizardTypes.JUNGLE_2, props), new Item.Properties().spawnEgg(CNBEntityTypes.LIZARD));
    public static final LizardItem LIZARD_ITEM_MUSHROOM = register("lizard_item_mushroom", props -> new LizardItem(CNBLizardTypes.MUSHROOM, props), new Item.Properties().spawnEgg(CNBEntityTypes.LIZARD));

    public static final SporelingSpawnEggItem SPORELING_OVERWORLD_EGG = register("sporeling_overworld_egg",
            props -> new SporelingSpawnEggItem("Overworld", props), new Item.Properties().spawnEgg(CNBEntityTypes.SPORELING));
    public static final SporelingSpawnEggItem SPORELING_NETHER_EGG = register("sporeling_nether_egg",
            props -> new SporelingSpawnEggItem("Nether", props), new Item.Properties().spawnEgg(CNBEntityTypes.SPORELING));

    // Block Items
    public static final CinderFurnaceItem CINDERSHELL_FURNACE = registerBlockItem("cinder_furnace",
            props -> new CinderFurnaceItem(CNBBlocks.CINDER_FURNACE, props), new Item.Properties());

    // ─────────────────────────────────────────────────────────────────────────

    private static Item.Properties waterlilyProperties() {
        Consumable heal = Consumable.builder()
                .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.INSTANT_HEALTH, 1), 1.0F))
                .build();
        return new Item.Properties().food(
                new FoodProperties.Builder().nutrition(4).saturationModifier(0.5F).alwaysEdible().build(), heal);
    }

    /**
     * Armor-slot properties without {@code Item.Properties#humanoidArmor}: that helper derives
     * durability from the material, but the crowns/backpack were unbreakable (no durability) in
     * 1.21.1, so we only attach attributes, the equippable slot/asset and the repair tag.
     */
    private static Item.Properties armorProperties(Item.Properties props, ArmorMaterial material, ArmorType type) {
        return props.attributes(material.createAttributes(type))
                .component(DataComponents.EQUIPPABLE, Equippable.builder(type.getSlot())
                        .setEquipSound(material.equipSound())
                        .setAsset(material.assetId())
                        .build())
                .repairable(material.repairIngredient());
    }

    private static <T extends Item> T register(String name, Function<Item.Properties, T> factory, Item.Properties props) {
        ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, name));
        return Registry.register(BuiltInRegistries.ITEM, key, factory.apply(props.setId(key)));
    }

    private static <T extends Item> T registerBlockItem(String name, Function<Item.Properties, T> factory, Item.Properties props) {
        return register(name, factory, props.useBlockDescriptionPrefix());
    }

    public static void register() {
        CreaturesAndBeasts.LOGGER.debug("Registered CNB items");
    }

    public static void addItemsToCreativeTab(CreativeModeTab.Output output) {
        output.accept(APPLE_SLICE);
        output.accept(PINK_WATERLILY);
        output.accept(LIGHT_PINK_WATERLILY);
        output.accept(YELLOW_WATERLILY);
        output.accept(CINDERSHELL_BUCKET);
        output.accept(ENTITY_NET);
        output.accept(LIZARD_EGG);
        output.accept(CINDERSHELL_SHELL_SHARD);
        output.accept(YETI_ANTLER);
        output.accept(YETI_HIDE);
        output.accept(PINK_MINIPAD_FLOWER);
        output.accept(LIGHT_PINK_MINIPAD_FLOWER);
        output.accept(YELLOW_MINIPAD_FLOWER);
        output.accept(PINK_MINIPAD_FLOWER_GLOW);
        output.accept(LIGHT_PINK_MINIPAD_FLOWER_GLOW);
        output.accept(YELLOW_MINIPAD_FLOWER_GLOW);
        output.accept(HEAL_SPELL_BOOK_1);
        output.accept(HEAL_SPELL_BOOK_2);
        output.accept(HEAL_SPELL_BOOK_3);
        output.accept(FLOWER_CROWN);
        output.accept(GLOWING_FLOWER_CROWN);
        output.accept(SPORELING_BACKPACK);
        output.accept(CINDER_SWORD);
        output.accept(CINDER_SWORD_1);
        output.accept(CINDER_SWORD_2);
        output.accept(CINDER_SWORD_3);
        output.accept(CINDER_SWORD_4);
        output.accept(CACTEM_SPEAR);
        output.accept(GREBE_SPAWN_EGG);
        output.accept(CINDERSHELL_SPAWN_EGG);
        output.accept(LILYTAD_SPAWN_EGG);
        output.accept(YETI_SPAWN_EGG);
        output.accept(MINIPAD_SPAWN_EGG);
        output.accept(LIZARD_SPAWN_EGG);
        output.accept(END_WHALE_SPAWN_EGG);
        output.accept(CACTEM_SPAWN_EGG);
        output.accept(LIZARD_ITEM_DESERT);
        output.accept(LIZARD_ITEM_DESERT_2);
        output.accept(LIZARD_ITEM_JUNGLE);
        output.accept(LIZARD_ITEM_JUNGLE_2);
        output.accept(LIZARD_ITEM_MUSHROOM);
        output.accept(SPORELING_OVERWORLD_EGG);
        output.accept(SPORELING_NETHER_EGG);
        output.accept(CINDERSHELL_FURNACE);
    }
}
