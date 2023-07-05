package com.mystery2099.block.custom

import com.mystery2099.WoodenAccentsModItemGroups
import com.mystery2099.datagen.BlockLootTableDataGen.Companion.dropsSelf
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.*
import net.minecraft.block.enums.StairShape
import net.minecraft.item.ItemPlacementContext
import net.minecraft.state.StateManager
import net.minecraft.state.property.DirectionProperty
import net.minecraft.state.property.EnumProperty
import net.minecraft.state.property.Properties
import net.minecraft.util.BlockMirror
import net.minecraft.util.BlockRotation
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.shape.VoxelShape
import net.minecraft.util.shape.VoxelShapes
import net.minecraft.world.BlockView
import net.minecraft.world.WorldAccess
import java.util.*

open class KitchenCounterBlock(val baseBlock: Block, val topBlock: Block) :
    AbstractWaterloggableBlock(FabricBlockSettings.copyOf(baseBlock)) {

    init {
        defaultState =
            stateManager.defaultState.with(
                facing,
                Direction.NORTH
            ).with(shape, StairShape.STRAIGHT)
        this.dropsSelf()
        WoodenAccentsModItemGroups.kitchenItems += this
        instances += this
    }

    @Deprecated("Deprecated in Java")
    override fun getOutlineShape(
        state: BlockState,
        world: BlockView,
        pos: BlockPos,
        context: ShapeContext
    ): VoxelShape {
        //southShapes
        val southInnerLeftShape: VoxelShape =
            VoxelShapes.union(southShape, createCuboidShape(2.0, 0.0, 0.0, 16.0, 14.0, 2.0))
        val southInnerRightShape: VoxelShape =
            VoxelShapes.union(southShape, createCuboidShape(0.0, 0.0, 0.0, 14.0, 14.0, 2.0))
        val southOuterLeftShape: VoxelShape = createCuboidShape(2.0, 0.0, 2.0, 16.0, 14.0, 16.0)
        val southOuterRightShape: VoxelShape = createCuboidShape(0.0, 0.0, 2.0, 14.0, 14.0, 16.0)

        //northShapes
        val northInnerLeftShape: VoxelShape =
            VoxelShapes.union(northShape, createCuboidShape(0.0, 0.0, 14.0, 14.0, 14.0, 16.0))
        val northInnerRightShape: VoxelShape =
            VoxelShapes.union(northShape, createCuboidShape(2.0, 0.0, 14.0, 16.0, 14.0, 16.0))
        val northOuterLeftShape: VoxelShape = createCuboidShape(0.0, 0.0, 0.0, 14.0, 14.0, 14.0)
        val northOuterRightShape: VoxelShape = createCuboidShape(2.0, 0.0, 0.0, 16.0, 14.0, 14.0)
        val stairShape: StairShape = state.get(shape)
        return VoxelShapes.union(
            topShape, when (state.get(facing)) {
                Direction.NORTH -> when (stairShape) {
                    StairShape.STRAIGHT -> northShape
                    StairShape.INNER_LEFT -> northInnerLeftShape
                    StairShape.INNER_RIGHT -> northInnerRightShape
                    StairShape.OUTER_LEFT -> northOuterLeftShape
                    StairShape.OUTER_RIGHT -> northOuterRightShape
                }

                Direction.SOUTH -> when (stairShape) {
                    StairShape.STRAIGHT -> southShape
                    StairShape.INNER_LEFT -> southInnerLeftShape
                    StairShape.INNER_RIGHT -> southInnerRightShape
                    StairShape.OUTER_LEFT -> southOuterLeftShape
                    StairShape.OUTER_RIGHT -> southOuterRightShape
                }

                Direction.EAST -> when (stairShape) {
                    StairShape.STRAIGHT -> eastShape
                    StairShape.INNER_LEFT -> northInnerRightShape
                    StairShape.INNER_RIGHT -> southInnerLeftShape
                    StairShape.OUTER_LEFT -> northOuterRightShape
                    StairShape.OUTER_RIGHT -> southOuterLeftShape
                }

                Direction.WEST -> when (stairShape) {
                    StairShape.STRAIGHT -> westShape
                    StairShape.INNER_LEFT -> southInnerRightShape
                    StairShape.INNER_RIGHT -> northInnerLeftShape
                    StairShape.OUTER_LEFT -> southOuterRightShape
                    StairShape.OUTER_RIGHT -> northOuterLeftShape
                }

                else -> VoxelShapes.fullCube()
            }
        )
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) {
        super.appendProperties(builder)
        builder.add(facing, shape)
    }

    override fun getPlacementState(ctx: ItemPlacementContext): BlockState? {
        val blockPos: BlockPos = ctx.blockPos
        val superState: BlockState? = super.getPlacementState(ctx)
        return superState?.with(facing, ctx.horizontalPlayerFacing)
            ?.with(shape, getCounterShape(superState, ctx.world, blockPos))
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
        val stateForNeighborUpdate: BlockState? =
            super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos)

        return if (direction?.axis?.isHorizontal == true) {
            stateForNeighborUpdate?.with(shape, pos?.let { getCounterShape(state, world, it) })
        } else stateForNeighborUpdate
    }


    @Deprecated("Deprecated in Java", ReplaceWith(
        "state.with<Direction, Direction>(facing, rotation.rotate(state.get<Direction>(facing)))",
        "net.minecraft.util.math.Direction",
        "net.minecraft.util.math.Direction",
        "com.mystery2099.block.custom.KitchenCounterBlock.Companion.facing",
        "net.minecraft.util.math.Direction",
        "com.mystery2099.block.custom.KitchenCounterBlock.Companion.facing"
    )
    )
    override fun rotate(state: BlockState, rotation: BlockRotation): BlockState {
        return state.with(facing, rotation.rotate(state.get(facing)))
    }

    @Deprecated("Deprecated in Java")
    override fun mirror(state: BlockState, mirror: BlockMirror?): BlockState {
        state.get(facing)
        val stairShape = state.get(shape)
        when (mirror) {
            BlockMirror.LEFT_RIGHT -> {
                return when (stairShape) {
                    StairShape.INNER_LEFT -> {
                        state.rotate(BlockRotation.CLOCKWISE_180).with(shape, StairShape.INNER_RIGHT)
                    }

                    StairShape.INNER_RIGHT -> {
                        state.rotate(BlockRotation.CLOCKWISE_180).with(shape, StairShape.INNER_LEFT)
                    }

                    StairShape.OUTER_LEFT -> {
                        state.rotate(BlockRotation.CLOCKWISE_180).with(shape, StairShape.OUTER_RIGHT)
                    }

                    StairShape.OUTER_RIGHT -> {
                        state.rotate(BlockRotation.CLOCKWISE_180).with(shape, StairShape.OUTER_LEFT)
                    }
                    else -> {
                        state.rotate(BlockRotation.CLOCKWISE_180)
                    }
                }
            }

            BlockMirror.FRONT_BACK -> {
                return when (stairShape) {
                    StairShape.INNER_LEFT -> {
                        state.rotate(BlockRotation.CLOCKWISE_180).with(shape, StairShape.INNER_LEFT)
                    }

                    StairShape.INNER_RIGHT -> {
                        state.rotate(BlockRotation.CLOCKWISE_180).with(shape, StairShape.INNER_RIGHT)
                    }

                    StairShape.OUTER_LEFT -> {
                        state.rotate(BlockRotation.CLOCKWISE_180).with(shape, StairShape.OUTER_RIGHT)
                    }

                    StairShape.OUTER_RIGHT -> {
                        state.rotate(BlockRotation.CLOCKWISE_180).with(shape, StairShape.OUTER_LEFT)
                    }

                    StairShape.STRAIGHT -> {
                        state.rotate(BlockRotation.CLOCKWISE_180)
                    }
                    else -> {
                        return super.mirror(state, mirror)
                    }
                }
            }
            else -> {return super.mirror(state, mirror)}
        }
    }

    companion object {
        val instances: MutableSet<KitchenCounterBlock> = HashSet()
        val shape: EnumProperty<StairShape> = Properties.STAIR_SHAPE
        val facing: DirectionProperty = HorizontalFacingBlock.FACING
        protected val topShape: VoxelShape = createCuboidShape(0.0, 14.0, 0.0, 16.0, 16.0, 16.0)
        protected val southShape: VoxelShape = createCuboidShape(0.0, 0.0, 2.0, 16.0, 14.0, 16.0)
        protected val eastShape: VoxelShape = createCuboidShape(2.0, 0.0, 0.0, 16.0, 14.0, 16.0)
        private const val shapeOffset = -(2.0 / 16)
        protected val northShape: VoxelShape = southShape.offset(0.0, 0.0, shapeOffset)
        protected val westShape: VoxelShape = eastShape.offset(shapeOffset, 0.0, 0.0)

        private fun getCounterShape(state: BlockState, world: BlockView, pos: BlockPos): StairShape? {
            var direction3: Direction = state.get(facing)
            var direction2: Direction = state.get(facing)
            val direction = state.get(facing)
            val blockState = world.getBlockState(pos.offset(direction))
            if (canConnectTo(blockState) && blockState.get(facing).let {
                direction2 = it
                    it.axis !== state.get(facing).axis &&
                                isDifferentOrientation(state, world, pos, direction2.opposite)
            }
            ) {
                return if (direction2 == direction.rotateYCounterclockwise()) StairShape.OUTER_LEFT
                else StairShape.OUTER_RIGHT
            }
            val blockState2 = world.getBlockState(pos.offset(direction.opposite))
            return if (canConnectTo(blockState2) && blockState2.get(facing).let {
                direction3 = it
                    it.axis !== state.get(facing).axis &&isDifferentOrientation(state, world, pos, direction3)
            }
            ) {
                if (direction3 == direction.rotateYCounterclockwise()) StairShape.INNER_LEFT else StairShape.INNER_RIGHT
            } else StairShape.STRAIGHT
        }

        private fun canConnectTo(blockState: BlockState): Boolean {
            return blockState.block is KitchenCounterBlock // || blockState.getBlock() is KitchenCabinetBlock
        }

        private fun isDifferentOrientation(
            state: BlockState,
            world: BlockView,
            pos: BlockPos?,
            dir: Direction
        ): Boolean {
            val blockState: BlockState = world.getBlockState(pos?.offset(dir))
            return !canConnectTo(blockState) || blockState.get(facing) != state.get(facing)
        }
    }
}
