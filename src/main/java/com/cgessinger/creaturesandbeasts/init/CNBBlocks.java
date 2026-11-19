package com.cgessinger.creaturesandbeasts.init;

import com.cgessinger.creaturesandbeasts.CreaturesAndBeasts;
import com.cgessinger.creaturesandbeasts.blocks.CinderFurnaceBlock;
import com.cgessinger.creaturesandbeasts.blocks.LizardEggBlock;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CNBBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(CreaturesAndBeasts.MOD_ID);

    // Pink Waterlily
    public static final DeferredBlock<FlowerBlock> PINK_WATERLILY_BLOCK = BLOCKS.registerBlock("pink_waterlily_block",
            props -> new FlowerBlock(MobEffects.INSTANT_HEALTH, 0.25F, props),
            () -> BlockBehaviour.Properties.of().noCollision().instabreak().sound(SoundType.GRASS));

    public static final DeferredBlock<FlowerPotBlock> POTTED_PINK_WATERLILY = BLOCKS.registerBlock("potted_pink_waterlily",
            props -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, PINK_WATERLILY_BLOCK, props),
            () -> BlockBehaviour.Properties.of().instabreak().noOcclusion());

    // Light Pink Waterlily
    public static final DeferredBlock<FlowerBlock> LIGHT_PINK_WATERLILY_BLOCK = BLOCKS.registerBlock("light_pink_waterlily_block",
            props -> new FlowerBlock(MobEffects.INSTANT_HEALTH, 0.25F, props),
            () -> BlockBehaviour.Properties.of().noCollision().instabreak().sound(SoundType.GRASS));

    public static final DeferredBlock<FlowerPotBlock> POTTED_LIGHT_PINK_WATERLILY = BLOCKS.registerBlock("potted_light_pink_waterlily",
            props -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, LIGHT_PINK_WATERLILY_BLOCK, props),
            () -> BlockBehaviour.Properties.of().instabreak().noOcclusion());

    // Yellow Waterlily
    public static final DeferredBlock<FlowerBlock> YELLOW_WATERLILY_BLOCK = BLOCKS.registerBlock("yellow_waterlily_block",
            props -> new FlowerBlock(MobEffects.INSTANT_HEALTH, 0.25F, props),
            () -> BlockBehaviour.Properties.of().noCollision().instabreak().sound(SoundType.GRASS));

    public static final DeferredBlock<FlowerPotBlock> POTTED_YELLOW_WATERLILY = BLOCKS.registerBlock("potted_yellow_waterlily",
            props -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, YELLOW_WATERLILY_BLOCK, props),
            () -> BlockBehaviour.Properties.of().instabreak().noOcclusion());

    // Cinder Furnace
    public static final DeferredBlock<Block> CINDER_FURNACE = BLOCKS.registerBlock("cinder_furnace",
            CinderFurnaceBlock::new,
            () -> BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(3.5F));

    // Lizard Eggs
    public static final DeferredBlock<Block> LIZARD_EGGS = BLOCKS.registerBlock("lizard_egg_block", LizardEggBlock::new);
}
