package com.cgessinger.creaturesandbeasts.init;

import com.cgessinger.creaturesandbeasts.CreaturesAndBeasts;
import com.cgessinger.creaturesandbeasts.blocks.CinderFurnaceBlock;
import com.cgessinger.creaturesandbeasts.blocks.LizardEggBlock;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Function;

public class CNBBlocks {

    // Pink Waterlily
    public static final FlowerBlock PINK_WATERLILY_BLOCK = register("pink_waterlily_block",
            props -> new FlowerBlock(MobEffects.INSTANT_HEALTH, 5, props),
            BlockBehaviour.Properties.of().noCollision().instabreak().sound(SoundType.GRASS));
    public static final FlowerPotBlock POTTED_PINK_WATERLILY = register("potted_pink_waterlily",
            props -> new FlowerPotBlock(PINK_WATERLILY_BLOCK, props),
            BlockBehaviour.Properties.of().instabreak().noOcclusion());

    // Light Pink Waterlily
    public static final FlowerBlock LIGHT_PINK_WATERLILY_BLOCK = register("light_pink_waterlily_block",
            props -> new FlowerBlock(MobEffects.INSTANT_HEALTH, 5, props),
            BlockBehaviour.Properties.of().noCollision().instabreak().sound(SoundType.GRASS));
    public static final FlowerPotBlock POTTED_LIGHT_PINK_WATERLILY = register("potted_light_pink_waterlily",
            props -> new FlowerPotBlock(LIGHT_PINK_WATERLILY_BLOCK, props),
            BlockBehaviour.Properties.of().instabreak().noOcclusion());

    // Yellow Waterlily
    public static final FlowerBlock YELLOW_WATERLILY_BLOCK = register("yellow_waterlily_block",
            props -> new FlowerBlock(MobEffects.INSTANT_HEALTH, 5, props),
            BlockBehaviour.Properties.of().noCollision().instabreak().sound(SoundType.GRASS));
    public static final FlowerPotBlock POTTED_YELLOW_WATERLILY = register("potted_yellow_waterlily",
            props -> new FlowerPotBlock(YELLOW_WATERLILY_BLOCK, props),
            BlockBehaviour.Properties.of().instabreak().noOcclusion());

    // Cinder Furnace
    public static final Block CINDER_FURNACE = register("cinder_furnace", CinderFurnaceBlock::new,
            BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(3.5F));

    // Lizard Eggs
    public static final Block LIZARD_EGGS = register("lizard_egg_block", LizardEggBlock::new,
            LizardEggBlock.eggProperties());

    // ─────────────────────────────────────────────────────────────────────────

    private static <T extends Block> T register(String name, Function<BlockBehaviour.Properties, T> factory,
                                                BlockBehaviour.Properties props) {
        ResourceKey<Block> key = ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, name));
        return Registry.register(BuiltInRegistries.BLOCK, key, factory.apply(props.setId(key)));
    }

    public static void register() {
        // Static initialisation of all fields happens when this class is loaded.
        CreaturesAndBeasts.LOGGER.debug("Registered CNB blocks");
    }
}
