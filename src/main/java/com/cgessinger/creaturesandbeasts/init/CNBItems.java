package com.cgessinger.creaturesandbeasts.init;

import com.cgessinger.creaturesandbeasts.CreaturesAndBeasts;
import com.cgessinger.creaturesandbeasts.items.CinderFurnaceItem;
import com.cgessinger.creaturesandbeasts.items.CinderSwordItem;
import com.cgessinger.creaturesandbeasts.items.FlowerCrownItem;
import com.cgessinger.creaturesandbeasts.items.GlowingFlowerCrownItem;
import com.cgessinger.creaturesandbeasts.items.HealSpellBookItem;
import com.cgessinger.creaturesandbeasts.items.LizardEggItem;
import com.cgessinger.creaturesandbeasts.items.LizardItem;
import com.cgessinger.creaturesandbeasts.items.MinipadFlowerGlowItem;
import com.cgessinger.creaturesandbeasts.items.SpearItem;
import com.cgessinger.creaturesandbeasts.items.SporelingBackpackItem;
import com.cgessinger.creaturesandbeasts.items.SporelingSpawnEggItem;
import com.cgessinger.creaturesandbeasts.items.WaterlilyBlockItem;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.Consumables;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CNBItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(CreaturesAndBeasts.MOD_ID);

    private static FoodProperties waterlilyFood() {
        return new FoodProperties.Builder().nutrition(4).saturationModifier(0.5F).alwaysEdible().build();
    }

    private static Consumable waterlilyConsumable() {
        return Consumables.defaultFood()
                .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.INSTANT_HEALTH, 1), 1.0F))
                .build();
    }

    // Food
    public static final DeferredItem<Item> APPLE_SLICE = ITEMS.registerItem("apple_slice", Item::new,
            p -> p.food(new FoodProperties.Builder().nutrition(1).saturationModifier(0.3F).build()));

    public static final DeferredItem<WaterlilyBlockItem> PINK_WATERLILY = ITEMS.registerItem("pink_waterlily",
            props -> new WaterlilyBlockItem(CNBBlocks.PINK_WATERLILY_BLOCK.get(), props),
            p -> p.food(waterlilyFood(), waterlilyConsumable()));

    public static final DeferredItem<WaterlilyBlockItem> LIGHT_PINK_WATERLILY = ITEMS.registerItem("light_pink_waterlily",
            props -> new WaterlilyBlockItem(CNBBlocks.LIGHT_PINK_WATERLILY_BLOCK.get(), props),
            p -> p.food(waterlilyFood(), waterlilyConsumable()));

    public static final DeferredItem<WaterlilyBlockItem> YELLOW_WATERLILY = ITEMS.registerItem("yellow_waterlily",
            props -> new WaterlilyBlockItem(CNBBlocks.YELLOW_WATERLILY_BLOCK.get(), props),
            p -> p.food(waterlilyFood(), waterlilyConsumable()));

    // Bucketed Mobs
    public static final DeferredItem<MobBucketItem> CINDERSHELL_BUCKET = ITEMS.registerItem("cindershell_bucket",
            props -> new MobBucketItem(CNBEntityTypes.CINDERSHELL.get(), Fluids.LAVA, SoundEvents.BUCKET_EMPTY_LAVA, props),
            p -> p.stacksTo(1));

    // Misc. Items
    public static final DeferredItem<Item> ENTITY_NET = ITEMS.registerItem("entity_net", Item::new, p -> p.durability(64));
    public static final DeferredItem<LizardEggItem> LIZARD_EGG = ITEMS.registerItem("lizard_egg",
            props -> new LizardEggItem(CNBBlocks.LIZARD_EGGS.get(), props));
    // Burn time (6400) lives in data/neoforge/data_maps/item/furnace_fuels.json
    public static final DeferredItem<Item> CINDERSHELL_SHELL_SHARD = ITEMS.registerSimpleItem("cindershell_shell_shard");
    public static final DeferredItem<Item> YETI_ANTLER = ITEMS.registerSimpleItem("yeti_antler");
    public static final DeferredItem<Item> YETI_HIDE = ITEMS.registerSimpleItem("yeti_hide");

    public static final DeferredItem<Item> PINK_MINIPAD_FLOWER = ITEMS.registerSimpleItem("pink_minipad_flower");
    public static final DeferredItem<Item> LIGHT_PINK_MINIPAD_FLOWER = ITEMS.registerSimpleItem("light_pink_minipad_flower");
    public static final DeferredItem<Item> YELLOW_MINIPAD_FLOWER = ITEMS.registerSimpleItem("yellow_minipad_flower");

    public static final DeferredItem<Item> PINK_MINIPAD_FLOWER_GLOW = ITEMS.<Item>registerItem("pink_minipad_flower_glow", MinipadFlowerGlowItem::new);
    public static final DeferredItem<Item> LIGHT_PINK_MINIPAD_FLOWER_GLOW = ITEMS.<Item>registerItem("light_pink_minipad_flower_glow", MinipadFlowerGlowItem::new);
    public static final DeferredItem<Item> YELLOW_MINIPAD_FLOWER_GLOW = ITEMS.<Item>registerItem("yellow_minipad_flower_glow", MinipadFlowerGlowItem::new);

    public static final DeferredItem<HealSpellBookItem> HEAL_SPELL_BOOK_1 = ITEMS.registerItem("heal_spell_book_1", HealSpellBookItem::new, p -> p.stacksTo(1));
    public static final DeferredItem<HealSpellBookItem> HEAL_SPELL_BOOK_2 = ITEMS.registerItem("heal_spell_book_2", HealSpellBookItem::new, p -> p.stacksTo(1));
    public static final DeferredItem<HealSpellBookItem> HEAL_SPELL_BOOK_3 = ITEMS.registerItem("heal_spell_book_3", HealSpellBookItem::new, p -> p.stacksTo(1));

    // Armor
    public static final DeferredItem<FlowerCrownItem> FLOWER_CROWN = ITEMS.registerItem("flower_crown",
            props -> new FlowerCrownItem(CNBArmorMaterials.FLOWER_CROWN, props));

    public static final DeferredItem<GlowingFlowerCrownItem> GLOWING_FLOWER_CROWN = ITEMS.registerItem("glowing_flower_crown",
            props -> new GlowingFlowerCrownItem(CNBArmorMaterials.FLOWER_CROWN, props));

    public static final DeferredItem<SporelingBackpackItem> SPORELING_BACKPACK = ITEMS.registerItem("sporeling_backpack",
            props -> new SporelingBackpackItem(CNBArmorMaterials.SPORELING_BACKPACK, props));

    // Tools
    public static final DeferredItem<CinderSwordItem> CINDER_SWORD = ITEMS.registerItem("cinder_sword",
            props -> new CinderSwordItem(CNBItemTiers.CINDER, 0, 3, -2.4F, props));
    public static final DeferredItem<CinderSwordItem> CINDER_SWORD_1 = ITEMS.registerItem("cinder_sword_1",
            props -> new CinderSwordItem(CNBItemTiers.CINDER, 1, 4, -2.4F, props));
    public static final DeferredItem<CinderSwordItem> CINDER_SWORD_2 = ITEMS.registerItem("cinder_sword_2",
            props -> new CinderSwordItem(CNBItemTiers.CINDER, 2, 5, -2.4F, props));
    public static final DeferredItem<CinderSwordItem> CINDER_SWORD_3 = ITEMS.registerItem("cinder_sword_3",
            props -> new CinderSwordItem(CNBItemTiers.CINDER, 3, 6, -2.4F, props));
    public static final DeferredItem<CinderSwordItem> CINDER_SWORD_4 = ITEMS.registerItem("cinder_sword_4",
            props -> new CinderSwordItem(CNBItemTiers.CINDER, 4, 7, -2.4F, props));

    public static final DeferredItem<SpearItem> CACTEM_SPEAR = ITEMS.registerItem("cactem_spear", SpearItem::new, p -> p.durability(100));

    // Spawn Eggs (the egg sprites are custom, untinted models)
    public static final DeferredItem<SpawnEggItem> GREBE_SPAWN_EGG = ITEMS.registerItem("little_grebe_spawn_egg",
            SpawnEggItem::new, p -> p.spawnEgg(CNBEntityTypes.LITTLE_GREBE.get()));
    public static final DeferredItem<SpawnEggItem> CINDERSHELL_SPAWN_EGG = ITEMS.registerItem("cindershell_spawn_egg",
            SpawnEggItem::new, p -> p.spawnEgg(CNBEntityTypes.CINDERSHELL.get()));
    public static final DeferredItem<SpawnEggItem> LILYTAD_SPAWN_EGG = ITEMS.registerItem("lilytad_spawn_egg",
            SpawnEggItem::new, p -> p.spawnEgg(CNBEntityTypes.LILYTAD.get()));
    public static final DeferredItem<SpawnEggItem> YETI_SPAWN_EGG = ITEMS.registerItem("yeti_spawn_egg",
            SpawnEggItem::new, p -> p.spawnEgg(CNBEntityTypes.YETI.get()));
    public static final DeferredItem<SpawnEggItem> MINIPAD_SPAWN_EGG = ITEMS.registerItem("minipad_spawn_egg",
            SpawnEggItem::new, p -> p.spawnEgg(CNBEntityTypes.MINIPAD.get()));
    public static final DeferredItem<SpawnEggItem> LIZARD_SPAWN_EGG = ITEMS.registerItem("lizard_spawn_egg",
            SpawnEggItem::new, p -> p.spawnEgg(CNBEntityTypes.LIZARD.get()));
    public static final DeferredItem<SpawnEggItem> END_WHALE_SPAWN_EGG = ITEMS.registerItem("end_whale_spawn_egg",
            SpawnEggItem::new, p -> p.spawnEgg(CNBEntityTypes.END_WHALE.get()));
    public static final DeferredItem<SpawnEggItem> CACTEM_SPAWN_EGG = ITEMS.registerItem("cactem_spawn_egg",
            SpawnEggItem::new, p -> p.spawnEgg(CNBEntityTypes.CACTEM.get()));

    public static final DeferredItem<LizardItem> LIZARD_ITEM_DESERT = ITEMS.registerItem("lizard_item_desert",
            props -> new LizardItem(props, CNBLizardTypes.DESERT), p -> p.spawnEgg(CNBEntityTypes.LIZARD.get()));
    public static final DeferredItem<LizardItem> LIZARD_ITEM_DESERT_2 = ITEMS.registerItem("lizard_item_desert_2",
            props -> new LizardItem(props, CNBLizardTypes.DESERT_2), p -> p.spawnEgg(CNBEntityTypes.LIZARD.get()));
    public static final DeferredItem<LizardItem> LIZARD_ITEM_JUNGLE = ITEMS.registerItem("lizard_item_jungle",
            props -> new LizardItem(props, CNBLizardTypes.JUNGLE), p -> p.spawnEgg(CNBEntityTypes.LIZARD.get()));
    public static final DeferredItem<LizardItem> LIZARD_ITEM_JUNGLE_2 = ITEMS.registerItem("lizard_item_jungle_2",
            props -> new LizardItem(props, CNBLizardTypes.JUNGLE_2), p -> p.spawnEgg(CNBEntityTypes.LIZARD.get()));
    public static final DeferredItem<LizardItem> LIZARD_ITEM_MUSHROOM = ITEMS.registerItem("lizard_item_mushroom",
            props -> new LizardItem(props, CNBLizardTypes.MUSHROOM), p -> p.spawnEgg(CNBEntityTypes.LIZARD.get()));

    public static final DeferredItem<SporelingSpawnEggItem> SPORELING_OVERWORLD_EGG = ITEMS.registerItem("sporeling_overworld_egg",
            props -> new SporelingSpawnEggItem(props, "Overworld"), p -> p.spawnEgg(CNBEntityTypes.SPORELING.get()));
    public static final DeferredItem<SporelingSpawnEggItem> SPORELING_NETHER_EGG = ITEMS.registerItem("sporeling_nether_egg",
            props -> new SporelingSpawnEggItem(props, "Nether"), p -> p.spawnEgg(CNBEntityTypes.SPORELING.get()));

    // Block Items
    public static final DeferredItem<CinderFurnaceItem> CINDERSHELL_FURNACE = ITEMS.registerItem("cinder_furnace",
            props -> new CinderFurnaceItem(CNBBlocks.CINDER_FURNACE.get(), props), p -> p.useBlockDescriptionPrefix());

    public static void addItemsToCreativeTab(CreativeModeTab.Output output) {
        output.accept(APPLE_SLICE.get());
        output.accept(PINK_WATERLILY.get());
        output.accept(LIGHT_PINK_WATERLILY.get());
        output.accept(YELLOW_WATERLILY.get());
        output.accept(CINDERSHELL_BUCKET.get());
        output.accept(ENTITY_NET.get());
        output.accept(LIZARD_EGG.get());
        output.accept(CINDERSHELL_SHELL_SHARD.get());
        output.accept(YETI_ANTLER.get());
        output.accept(YETI_HIDE.get());
        output.accept(PINK_MINIPAD_FLOWER.get());
        output.accept(LIGHT_PINK_MINIPAD_FLOWER.get());
        output.accept(YELLOW_MINIPAD_FLOWER.get());
        output.accept(PINK_MINIPAD_FLOWER_GLOW.get());
        output.accept(LIGHT_PINK_MINIPAD_FLOWER_GLOW.get());
        output.accept(YELLOW_MINIPAD_FLOWER_GLOW.get());
        output.accept(HEAL_SPELL_BOOK_1.get());
        output.accept(HEAL_SPELL_BOOK_2.get());
        output.accept(HEAL_SPELL_BOOK_3.get());
        output.accept(FLOWER_CROWN.get());
        output.accept(GLOWING_FLOWER_CROWN.get());
        output.accept(SPORELING_BACKPACK.get());
        output.accept(CINDER_SWORD.get());
        output.accept(CINDER_SWORD_1.get());
        output.accept(CINDER_SWORD_2.get());
        output.accept(CINDER_SWORD_3.get());
        output.accept(CINDER_SWORD_4.get());
        output.accept(CACTEM_SPEAR.get());
        output.accept(GREBE_SPAWN_EGG.get());
        output.accept(CINDERSHELL_SPAWN_EGG.get());
        output.accept(LILYTAD_SPAWN_EGG.get());
        output.accept(YETI_SPAWN_EGG.get());
        output.accept(MINIPAD_SPAWN_EGG.get());
        output.accept(LIZARD_SPAWN_EGG.get());
        output.accept(END_WHALE_SPAWN_EGG.get());
        output.accept(CACTEM_SPAWN_EGG.get());
        output.accept(LIZARD_ITEM_DESERT.get());
        output.accept(LIZARD_ITEM_DESERT_2.get());
        output.accept(LIZARD_ITEM_JUNGLE.get());
        output.accept(LIZARD_ITEM_JUNGLE_2.get());
        output.accept(LIZARD_ITEM_MUSHROOM.get());
        output.accept(SPORELING_OVERWORLD_EGG.get());
        output.accept(SPORELING_NETHER_EGG.get());
        output.accept(CINDERSHELL_FURNACE.get());
    }
}
