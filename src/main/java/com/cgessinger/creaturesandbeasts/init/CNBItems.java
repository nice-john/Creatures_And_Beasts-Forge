package com.cgessinger.creaturesandbeasts.init;

import com.cgessinger.creaturesandbeasts.CreaturesAndBeasts;
import com.cgessinger.creaturesandbeasts.items.*;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.material.Fluids;

public class CNBItems {

    // Food
    public static final Item APPLE_SLICE = register("apple_slice",
            new Item(new Item.Properties().food(
                    new FoodProperties.Builder().nutrition(1).saturationModifier(0.3F).build())));

    public static final WaterlilyBlockItem PINK_WATERLILY = register("pink_waterlily",
            new WaterlilyBlockItem(CNBBlocks.PINK_WATERLILY_BLOCK,
                    new Item.Properties().food(new FoodProperties.Builder()
                            .nutrition(4).saturationModifier(0.5F).alwaysEdible()
                            .effect(new MobEffectInstance(MobEffects.HEAL, 1), 1.0F).build())));

    public static final WaterlilyBlockItem LIGHT_PINK_WATERLILY = register("light_pink_waterlily",
            new WaterlilyBlockItem(CNBBlocks.LIGHT_PINK_WATERLILY_BLOCK,
                    new Item.Properties().food(new FoodProperties.Builder()
                            .nutrition(4).saturationModifier(0.5F).alwaysEdible()
                            .effect(new MobEffectInstance(MobEffects.HEAL, 1), 1.0F).build())));

    public static final WaterlilyBlockItem YELLOW_WATERLILY = register("yellow_waterlily",
            new WaterlilyBlockItem(CNBBlocks.YELLOW_WATERLILY_BLOCK,
                    new Item.Properties().food(new FoodProperties.Builder()
                            .nutrition(4).saturationModifier(0.5F).alwaysEdible()
                            .effect(new MobEffectInstance(MobEffects.HEAL, 1), 1.0F).build())));

    // Bucketed Mobs
    public static final CNBEntityBucketItem CINDERSHELL_BUCKET = register("cindershell_bucket",
            new CNBEntityBucketItem(() -> CNBEntityTypes.CINDERSHELL, Fluids.LAVA,
                    () -> SoundEvents.BUCKET_EMPTY_LAVA, new Item.Properties().stacksTo(1)));

    // Misc. Items
    public static final Item ENTITY_NET = register("entity_net",
            new Item(new Item.Properties().durability(64)));
    public static final LizardEggItem LIZARD_EGG = register("lizard_egg",
            new LizardEggItem(CNBBlocks.LIZARD_EGGS));
    public static final CNBFuelItem CINDERSHELL_SHELL_SHARD = register("cindershell_shell_shard",
            new CNBFuelItem(6400));
    public static final Item YETI_ANTLER = register("yeti_antler",
            new Item(new Item.Properties()));
    public static final Item YETI_HIDE = register("yeti_hide",
            new Item(new Item.Properties()));

    public static final Item PINK_MINIPAD_FLOWER        = register("pink_minipad_flower",       new Item(new Item.Properties()));
    public static final Item LIGHT_PINK_MINIPAD_FLOWER  = register("light_pink_minipad_flower", new Item(new Item.Properties()));
    public static final Item YELLOW_MINIPAD_FLOWER      = register("yellow_minipad_flower",     new Item(new Item.Properties()));

    public static final Item PINK_MINIPAD_FLOWER_GLOW       = register("pink_minipad_flower_glow",       new MinipadFlowerGlowItem(new Item.Properties()));
    public static final Item LIGHT_PINK_MINIPAD_FLOWER_GLOW = register("light_pink_minipad_flower_glow", new MinipadFlowerGlowItem(new Item.Properties()));
    public static final Item YELLOW_MINIPAD_FLOWER_GLOW     = register("yellow_minipad_flower_glow",     new MinipadFlowerGlowItem(new Item.Properties()));

    public static final HealSpellBookItem HEAL_SPELL_BOOK_1 = register("heal_spell_book_1", new HealSpellBookItem(new Item.Properties().stacksTo(1)));
    public static final HealSpellBookItem HEAL_SPELL_BOOK_2 = register("heal_spell_book_2", new HealSpellBookItem(new Item.Properties().stacksTo(1)));
    public static final HealSpellBookItem HEAL_SPELL_BOOK_3 = register("heal_spell_book_3", new HealSpellBookItem(new Item.Properties().stacksTo(1)));

    // Armor
    public static final FlowerCrownItem FLOWER_CROWN = register("flower_crown",
            new FlowerCrownItem(CNBArmorMaterials.FLOWER_CROWN,
                    Ingredient.of(PINK_MINIPAD_FLOWER, LIGHT_PINK_MINIPAD_FLOWER, YELLOW_MINIPAD_FLOWER),
                    ArmorItem.Type.HELMET, new Item.Properties()));

    public static final GlowingFlowerCrownItem GLOWING_FLOWER_CROWN = register("glowing_flower_crown",
            new GlowingFlowerCrownItem(CNBArmorMaterials.FLOWER_CROWN,
                    Ingredient.of(PINK_MINIPAD_FLOWER_GLOW, LIGHT_PINK_MINIPAD_FLOWER_GLOW, YELLOW_MINIPAD_FLOWER_GLOW),
                    ArmorItem.Type.HELMET, new Item.Properties()));

    public static final SporelingBackpackItem SPORELING_BACKPACK = register("sporeling_backpack",
            new SporelingBackpackItem(CNBArmorMaterials.SPORELING_BACKPACK,
                    ArmorItem.Type.CHESTPLATE, new Item.Properties()));

    // Tools
    public static final CinderSwordItem CINDER_SWORD   = register("cinder_sword",   new CinderSwordItem(CNBItemTiers.CINDER, 0, 3, -2.4F, new Item.Properties()));
    public static final CinderSwordItem CINDER_SWORD_1 = register("cinder_sword_1", new CinderSwordItem(CNBItemTiers.CINDER, 1, 4, -2.4F, new Item.Properties()));
    public static final CinderSwordItem CINDER_SWORD_2 = register("cinder_sword_2", new CinderSwordItem(CNBItemTiers.CINDER, 2, 5, -2.4F, new Item.Properties()));
    public static final CinderSwordItem CINDER_SWORD_3 = register("cinder_sword_3", new CinderSwordItem(CNBItemTiers.CINDER, 3, 6, -2.4F, new Item.Properties()));
    public static final CinderSwordItem CINDER_SWORD_4 = register("cinder_sword_4", new CinderSwordItem(CNBItemTiers.CINDER, 4, 7, -2.4F, new Item.Properties()));

    public static final SpearItem CACTEM_SPEAR = register("cactem_spear",
            new SpearItem(new Item.Properties().durability(100)));

    // Spawn Eggs (vanilla SpawnEggItem – lazy entity type lookup not needed on Fabric)
    public static final SpawnEggItem GREBE_SPAWN_EGG       = register("little_grebe_spawn_egg",  new SpawnEggItem(CNBEntityTypes.LITTLE_GREBE, 0x00FFFFFF, 0x00FFFFFF, new Item.Properties()));
    public static final SpawnEggItem CINDERSHELL_SPAWN_EGG = register("cindershell_spawn_egg",    new SpawnEggItem(CNBEntityTypes.CINDERSHELL, 0x0D0403, 0xC64500, new Item.Properties()));
    public static final SpawnEggItem LILYTAD_SPAWN_EGG     = register("lilytad_spawn_egg",        new SpawnEggItem(CNBEntityTypes.LILYTAD, 0x37702E, 0x102417, new Item.Properties()));
    public static final SpawnEggItem YETI_SPAWN_EGG        = register("yeti_spawn_egg",           new SpawnEggItem(CNBEntityTypes.YETI, 0xD7E1E7, 0x887E96, new Item.Properties()));
    public static final SpawnEggItem MINIPAD_SPAWN_EGG     = register("minipad_spawn_egg",        new SpawnEggItem(CNBEntityTypes.MINIPAD, 0x3EA62E, 0x194F28, new Item.Properties()));
    public static final SpawnEggItem LIZARD_SPAWN_EGG      = register("lizard_spawn_egg",         new SpawnEggItem(CNBEntityTypes.LIZARD, 0x00FFFFFF, 0x00FFFFFF, new Item.Properties()));
    public static final SpawnEggItem END_WHALE_SPAWN_EGG   = register("end_whale_spawn_egg",      new SpawnEggItem(CNBEntityTypes.END_WHALE, 0x5609AD, 0xD4AD5F, new Item.Properties()));
    public static final SpawnEggItem CACTEM_SPAWN_EGG      = register("cactem_spawn_egg",         new SpawnEggItem(CNBEntityTypes.CACTEM, 0x1A6E23, 0xDCEBAB, new Item.Properties()));

    // Lizard type spawn items
    public static final LizardItem LIZARD_ITEM_DESERT   = register("lizard_item_desert",   new LizardItem(CNBEntityTypes.LIZARD, 0x00FFFFFF, 0x00FFFFFF, new Item.Properties(), CNBLizardTypes.DESERT));
    public static final LizardItem LIZARD_ITEM_DESERT_2 = register("lizard_item_desert_2", new LizardItem(CNBEntityTypes.LIZARD, 0x00FFFFFF, 0x00FFFFFF, new Item.Properties(), CNBLizardTypes.DESERT_2));
    public static final LizardItem LIZARD_ITEM_JUNGLE   = register("lizard_item_jungle",   new LizardItem(CNBEntityTypes.LIZARD, 0x00FFFFFF, 0x00FFFFFF, new Item.Properties(), CNBLizardTypes.JUNGLE));
    public static final LizardItem LIZARD_ITEM_JUNGLE_2 = register("lizard_item_jungle_2", new LizardItem(CNBEntityTypes.LIZARD, 0x00FFFFFF, 0x00FFFFFF, new Item.Properties(), CNBLizardTypes.JUNGLE_2));
    public static final LizardItem LIZARD_ITEM_MUSHROOM = register("lizard_item_mushroom", new LizardItem(CNBEntityTypes.LIZARD, 0x00FFFFFF, 0x00FFFFFF, new Item.Properties(), CNBLizardTypes.MUSHROOM));

    public static final SporelingSpawnEggItem SPORELING_OVERWORLD_EGG = register("sporeling_overworld_egg",
            new SporelingSpawnEggItem(CNBEntityTypes.SPORELING, 0xDE0942, 0xFFEBC4, new Item.Properties(), "Overworld"));
    public static final SporelingSpawnEggItem SPORELING_NETHER_EGG = register("sporeling_nether_egg",
            new SporelingSpawnEggItem(CNBEntityTypes.SPORELING, 0xBF2828, 0xFF9245, new Item.Properties(), "Nether"));

    // Block Items
    public static final CinderFurnaceItem CINDERSHELL_FURNACE = register("cinder_furnace",
            new CinderFurnaceItem(CNBBlocks.CINDER_FURNACE, new Item.Properties()));

    // ─────────────────────────────────────────────────────────────────────────

    @SuppressWarnings("unchecked")
    private static <T extends Item> T register(String name, T item) {
        return (T) Registry.register(BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, name), item);
    }

    public static void register() {
        CreaturesAndBeasts.LOGGER.debug("Registered CNB items");
    }

    /** Furnace fuel registration. Called from the main initializer. */
    public static void registerFuels() {
        net.fabricmc.fabric.api.registry.FuelRegistry.INSTANCE.add(
                CINDERSHELL_SHELL_SHARD, CINDERSHELL_SHELL_SHARD.getBurnTime());
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
