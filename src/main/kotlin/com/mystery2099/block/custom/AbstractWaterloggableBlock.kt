package com.mystery2099.block.custom

import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.Waterloggable
import net.minecraft.fluid.FluidState
import net.minecraft.fluid.Fluids
import net.minecraft.item.ItemPlacementContext
import net.minecraft.state.StateManager
import net.minecraft.state.property.BooleanProperty
import net.minecraft.state.property.Properties
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.WorldAccess

abstract class AbstractWaterloggableBlock(settings: Settings) : Block(settings), Waterloggable {
    init {
        defaultState = defaultState.with(waterlogged, false)
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) {
        super.appendProperties(builder)
        builder.add(waterlogged)
    }

    override fun getPlacementState(ctx: ItemPlacementContext): BlockState? {
        val blockPos = ctx.blockPos
        val fluidState = ctx.world.getFluidState(blockPos)
        return super.getPlacementState(ctx)?.with(waterlogged, fluidState.fluid === Fluids.WATER)
    }

    @Deprecated("Deprecated in Java")
    override fun getStateForNeighborUpdate(
        state: BlockState,
        direction: Direction?,
        neighborState: BlockState?,
        world: WorldAccess,
        pos: BlockPos?,
        neighborPos: BlockPos?
    ): BlockState? {
        if (state.get(waterlogged)) world.scheduleFluidTick(
            pos,
            Fluids.WATER,
            Fluids.WATER.getTickRate(world)
        )
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos)
    }

    @Deprecated("Deprecated in Java")
    override fun getFluidState(state: BlockState): FluidState {
        return if (state.get(waterlogged)) {
            Fluids.WATER.getStill(false)
        } else {
            super.getFluidState(state)
        }
    }

    companion object {
        val waterlogged: BooleanProperty = Properties.WATERLOGGED
    }
}