package com.cgessinger.creaturesandbeasts.init;

import com.cgessinger.creaturesandbeasts.CreaturesAndBeasts;
import com.cgessinger.creaturesandbeasts.blocks.CinderFurnaceBlock;
import com.cgessinger.creaturesandbeasts.blocks.LizardEggBlock;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CNBBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(CreaturesAndBeasts.MOD_ID);

    // Pink Waterlily
    public static final DeferredBlock<FlowerBlock> PINK_WATERLILY_BLOCK = BLOCKS.register("pink_waterlily_block",
            () -> new FlowerBlock(MobEffects.HEAL, 0.25F, BlockBehaviour.Properties.of().noCollission().instabreak().sound(SoundType.GRASS)));

    public static final DeferredBlock<FlowerPotBlock> POTTED_PINK_WATERLILY = BLOCKS.register("potted_pink_waterlily",
            () -> new FlowerPotBlock(PINK_WATERLILY_BLOCK.get(), BlockBehaviour.Properties.of().instabreak().noOcclusion()));

    // Light Pink Waterlily
    public static final DeferredBlock<FlowerBlock> LIGHT_PINK_WATERLILY_BLOCK = BLOCKS.register("light_pink_waterlily_block",
            () -> new FlowerBlock(MobEffects.HEAL, 0.25F, BlockBehaviour.Properties.of().noCollission().instabreak().sound(SoundType.GRASS)));

    public static final DeferredBlock<FlowerPotBlock> POTTED_LIGHT_PINK_WATERLILY = BLOCKS.register("potted_light_pink_waterlily",
            () -> new FlowerPotBlock(LIGHT_PINK_WATERLILY_BLOCK.get(), BlockBehaviour.Properties.of().instabreak().noOcclusion()));

    // Yellow Waterlily
    public static final DeferredBlock<FlowerBlock> YELLOW_WATERLILY_BLOCK = BLOCKS.register("yellow_waterlily_block",
            () -> new FlowerBlock(MobEffects.HEAL, 0.25F, BlockBehaviour.Properties.of().noCollission().instabreak().sound(SoundType.GRASS)));

    public static final DeferredBlock<FlowerPotBlock> POTTED_YELLOW_WATERLILY = BLOCKS.register("potted_yellow_waterlily",
            () -> new FlowerPotBlock(YELLOW_WATERLILY_BLOCK.get(), BlockBehaviour.Properties.of().instabreak().noOcclusion()));

    // Cinder Furnace
    public static final DeferredBlock<Block> CINDER_FURNACE = BLOCKS.register("cinder_furnace",
            () -> new CinderFurnaceBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(3.5F)));

    // Lizard Eggs
    public static final DeferredBlock<Block> LIZARD_EGGS = BLOCKS.register("lizard_egg_block", LizardEggBlock::new);
}
