package com.mystery2099.wooden_accents_mod.block.custom

import com.mystery2099.wooden_accents_mod.block.custom.enums.ConnectingLadderShape
import com.mystery2099.wooden_accents_mod.data.ModBlockTags
import com.mystery2099.wooden_accents_mod.data.ModBlockTags.isIn
import com.mystery2099.wooden_accents_mod.data.ModModels
import com.mystery2099.wooden_accents_mod.state.property.ModProperties
import com.mystery2099.wooden_accents_mod.util.BlockStateVariantUtil.asBlockStateVariant
import com.mystery2099.wooden_accents_mod.util.BlockStateVariantUtil.withYRotationOf
import com.mystery2099.wooden_accents_mod.util.CompositeVoxelShape
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.block.ShapeContext
import net.minecraft.data.client.*
import net.minecraft.data.server.recipe.RecipeJsonProvider
import net.minecraft.registry.tag.TagKey
import net.minecraft.resource.featuretoggle.FeatureFlags
import net.minecraft.state.StateManager
import net.minecraft.state.property.Properties
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.shape.VoxelShape
import net.minecraft.world.BlockView
import net.minecraft.world.WorldAccess
import java.util.function.Consumer

class ConnectingLadderBlock(val baseBlock: Block) : AbstractCustomLadderBlock(FabricBlockSettings.of(baseBlock.defaultState.material, baseBlock.defaultMapColor).apply {
    hardness(Blocks.LADDER.hardness)
    resistance(Blocks.LADDER.blastResistance)
    sounds(baseBlock.getSoundGroup(baseBlock.defaultState))

    if (baseBlock.requiredFeatures.contains(FeatureFlags.UPDATE_1_20)) {
        requires(FeatureFlags.UPDATE_1_20)
    }
}) {
    override val tag: TagKey<Block> = ModBlockTags.connectingLadders

    init {
        defaultState = defaultState.withShape(left = false, right = false)
    }

    @Deprecated("Deprecated in Java")
    override fun getStateForNeighborUpdate(
        state: BlockState,
        direction: Direction?,
        neighborState: BlockState?,
        world: WorldAccess,
        pos: BlockPos,
        neighborPos: BlockPos?
    ): BlockState {
        val shouldConnectLeft: Boolean = world.getBlockState(pos.offset(state[FACING].rotateYClockwise())).isConnectingLadder()
        val shouldConnectRight: Boolean = world.getBlockState(pos.offset(state[FACING].rotateYCounterclockwise())).isConnectingLadder()
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos)
            .withShape(shouldConnectLeft, shouldConnectRight)
    }
    private fun BlockState.isConnectingLadder(): Boolean = this isIn tag
    private fun BlockState.withShape(left: Boolean, right: Boolean): BlockState = this.withIfExists(shape, run {
        when {
            left && right -> ConnectingLadderShape.CENTER
            left -> ConnectingLadderShape.RIGHT
            right -> ConnectingLadderShape.LEFT
            else -> ConnectingLadderShape.SINGLE
        }
    })

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) {
        super.appendProperties(builder)
        builder.add(shape)
    }
    @Deprecated("Deprecated in Java")
    override fun getOutlineShape(
        state: BlockState,
        world: BlockView?,
        pos: BlockPos?,
        context: ShapeContext?
    ): VoxelShape = shapeMap[state[shape]]?.get(state[FACING])?.get() ?: super.getOutlineShape(state, world, pos, context)

    override fun offerRecipeTo(exporter: Consumer<RecipeJsonProvider>) {
        super.offerRecipe(exporter, baseBlock, 8, "connecting_ladder")
    }

    override fun generateBlockStateModels(generator: BlockStateModelGenerator) {
        val textureMap = TextureMap.all(this.baseBlock)
        val singleModel = ModModels.connectingLadder.upload(this, textureMap, generator.modelCollector)
        val leftModel = ModModels.connectingLadderLeft.upload(this, textureMap, generator.modelCollector)
        val centerModel = ModModels.connectingLadderCenter.upload(this, textureMap, generator.modelCollector)
        val rightModel = ModModels.connectingLadderRight.upload(this, textureMap, generator.modelCollector)
        generator.blockStateCollector.accept(
            VariantsBlockStateSupplier.create(this).coordinate(
                BlockStateVariantMap.create(Properties.HORIZONTAL_FACING, ModProperties.connectingLadderShape).apply {
                    val northSingleVariant = singleModel.asBlockStateVariant()
                    val northLeftVariant = leftModel.asBlockStateVariant()
                    val northCenterVariant = centerModel.asBlockStateVariant()
                    val northRightVariant = rightModel.asBlockStateVariant()

                    register(Direction.NORTH, ConnectingLadderShape.SINGLE, northSingleVariant)
                    register(Direction.NORTH, ConnectingLadderShape.LEFT, northLeftVariant)
                    register(Direction.NORTH, ConnectingLadderShape.CENTER, northCenterVariant)
                    register(Direction.NORTH, ConnectingLadderShape.RIGHT, northRightVariant)

                    register(Direction.EAST, ConnectingLadderShape.SINGLE, northSingleVariant.withYRotationOf(
                        VariantSettings.Rotation.R90))
                    register(Direction.EAST, ConnectingLadderShape.LEFT, northLeftVariant.withYRotationOf(
                        VariantSettings.Rotation.R90))
                    register(Direction.EAST, ConnectingLadderShape.CENTER, northCenterVariant.withYRotationOf(
                        VariantSettings.Rotation.R90))
                    register(Direction.EAST, ConnectingLadderShape.RIGHT, northRightVariant.withYRotationOf(
                        VariantSettings.Rotation.R90))

                    register(Direction.SOUTH, ConnectingLadderShape.SINGLE, northSingleVariant.withYRotationOf(
                        VariantSettings.Rotation.R180))
                    register(Direction.SOUTH, ConnectingLadderShape.LEFT, northLeftVariant.withYRotationOf(
                        VariantSettings.Rotation.R180))
                    register(Direction.SOUTH, ConnectingLadderShape.CENTER, northCenterVariant.withYRotationOf(
                        VariantSettings.Rotation.R180))
                    register(Direction.SOUTH, ConnectingLadderShape.RIGHT, northRightVariant.withYRotationOf(
                        VariantSettings.Rotation.R180))

                    register(Direction.WEST, ConnectingLadderShape.SINGLE, northSingleVariant.withYRotationOf(
                        VariantSettings.Rotation.R270))
                    register(Direction.WEST, ConnectingLadderShape.LEFT, northLeftVariant.withYRotationOf(
                        VariantSettings.Rotation.R270))
                    register(Direction.WEST, ConnectingLadderShape.CENTER, northCenterVariant.withYRotationOf(
                        VariantSettings.Rotation.R270))
                    register(Direction.WEST, ConnectingLadderShape.RIGHT, northRightVariant.withYRotationOf(
                        VariantSettings.Rotation.R270))

                }
            )
        )
    }

    companion object {
        val shape = ModProperties.connectingLadderShape

        private val singleShape = CompositeVoxelShape.of(
            CompositeVoxelShape.createCuboidShape(2, 0, 15, 4, 16, 16),
            CompositeVoxelShape.createCuboidShape(12, 0, 15, 14, 16, 16),
            CompositeVoxelShape.createCuboidShape(2, 1, 14.5, 14, 15, 15)
        )

        private val leftShape = CompositeVoxelShape.of(
            CompositeVoxelShape.createCuboidShape(12, 0, 15, 14, 16, 16),
            CompositeVoxelShape.createCuboidShape(0, 1, 14.5, 14, 15, 15)
        )

        private val rightShape = CompositeVoxelShape.of(
            CompositeVoxelShape.createCuboidShape(2, 0, 15, 4, 16, 16),
            CompositeVoxelShape.createCuboidShape(2, 1, 14.5, 16, 15, 15)
        )

        private val singleShapeMap = mapOf(
            Direction.NORTH to singleShape,
            Direction.EAST to singleShape.rotatedLeft(),
            Direction.SOUTH to singleShape.flipped(),
            Direction.WEST to singleShape.rotatedRight()
        )
        private val centerShapeMap = mutableMapOf(
            Direction.NORTH to CompositeVoxelShape.of(CompositeVoxelShape.createCuboidShape(0, 1, 14.5, 16, 15, 15))
        ).also {
            it[Direction.EAST] = it[Direction.NORTH]?.rotatedLeft()!!
            it[Direction.SOUTH] = it[Direction.NORTH]?.flipped()!!
            it[Direction.WEST] = it[Direction.NORTH]?.rotatedRight()!!
        }.toMap()

        private val leftShapeMap = mapOf(
            Direction.NORTH to leftShape,
            Direction.EAST to leftShape.rotatedLeft(),
            Direction.SOUTH to leftShape.flipped(),
            Direction.WEST to leftShape.rotatedRight()
        )

        private val rightShapeMap = mapOf(
            Direction.NORTH to rightShape,
            Direction.EAST to rightShape.rotatedLeft(),
            Direction.SOUTH to rightShape.flipped(),
            Direction.WEST to rightShape.rotatedRight()
        )
        private val shapeMap = mapOf(
            ConnectingLadderShape.SINGLE to singleShapeMap,
            ConnectingLadderShape.CENTER to centerShapeMap,
            ConnectingLadderShape.LEFT to leftShapeMap,
            ConnectingLadderShape.RIGHT to rightShapeMap
        )

    }
}