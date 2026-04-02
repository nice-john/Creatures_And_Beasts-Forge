package com.cgessinger.creaturesandbeasts.items;

import com.cgessinger.creaturesandbeasts.CreaturesAndBeasts;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class WaterlilyBlockItem extends BlockItem {
    public WaterlilyBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    protected boolean canSurvive(BlockPlaceContext context, BlockState state) {
        // Ensure the block can only be placed in water
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        return level.getFluidState(pos).is(FluidTags.WATER);
    }
    @Override
    protected boolean canPlace(BlockPlaceContext context, BlockState state) {
        // Ensure the block can only be placed in water
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        return level.getFluidState(pos).is(FluidTags.WATER) && state.canSurvive(level, pos);
    }

}

