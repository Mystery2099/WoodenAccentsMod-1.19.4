package com.mystery2099.wooden_accents_mod.block.custom

import com.github.mystery2099.voxelshapeutils.VoxelShapeUtils
import com.github.mystery2099.voxelshapeutils.combination.VoxelShapeCombining
import com.github.mystery2099.voxelshapeutils.rotation.Rotation.flip
import com.github.mystery2099.voxelshapeutils.rotation.Rotation.rotateLeft
import com.github.mystery2099.voxelshapeutils.rotation.Rotation.rotateRight
import com.mystery2099.wooden_accents_mod.block.ModBlocks.textureId
import com.mystery2099.wooden_accents_mod.block.custom.enums.DeskShape
import com.mystery2099.wooden_accents_mod.data.ModBlockTags
import com.mystery2099.wooden_accents_mod.data.ModModels
import com.mystery2099.wooden_accents_mod.data.generation.RecipeDataGen.Companion.customGroup
import com.mystery2099.wooden_accents_mod.data.generation.RecipeDataGen.Companion.requires
import com.mystery2099.wooden_accents_mod.data.generation.interfaces.CustomBlockStateProvider
import com.mystery2099.wooden_accents_mod.data.generation.interfaces.CustomItemGroupProvider
import com.mystery2099.wooden_accents_mod.data.generation.interfaces.CustomRecipeProvider
import com.mystery2099.wooden_accents_mod.data.generation.interfaces.CustomTagProvider
import com.mystery2099.wooden_accents_mod.item_group.CustomItemGroup
import com.mystery2099.wooden_accents_mod.item_group.ModItemGroups
import com.mystery2099.wooden_accents_mod.state.property.ModProperties
import com.mystery2099.wooden_accents_mod.util.BlockStateUtil.isIn
import com.mystery2099.wooden_accents_mod.util.BlockStateUtil.withProperties
import com.mystery2099.wooden_accents_mod.util.BlockStateVariantUtil.asBlockStateVariant
import com.mystery2099.wooden_accents_mod.util.BlockStateVariantUtil.withYRotationOf
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.ShapeContext
import net.minecraft.data.client.*
import net.minecraft.data.server.recipe.RecipeJsonProvider
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder
import net.minecraft.item.ItemPlacementContext
import net.minecraft.recipe.book.RecipeCategory
import net.minecraft.registry.tag.TagKey
import net.minecraft.state.StateManager
import net.minecraft.state.property.DirectionProperty
import net.minecraft.state.property.Properties
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.shape.VoxelShape
import net.minecraft.util.shape.VoxelShapes
import net.minecraft.world.BlockView
import net.minecraft.world.WorldAccess
import java.util.function.Consumer

/**
 * Desk block
 *
 * @property baseBlock
 * @property topBlock
 * @constructor
 *
 * @param settings
 */
class DeskBlock(settings: Settings, val baseBlock: Block, private val topBlock: Block) :
    AbstractWaterloggableBlock(settings), CustomItemGroupProvider, CustomRecipeProvider, CustomBlockStateProvider,
    CustomTagProvider<Block> {


    override val itemGroup: CustomItemGroup = ModItemGroups.decorations
    override val tag: TagKey<Block> = ModBlockTags.desks
    private val BlockState.isDesk: Boolean
        get() = this isIn tag
    private val BlockState.isDeskDrawer: Boolean
        get() = this isIn ModBlockTags.deskDrawers

    init {
        defaultState = defaultState.with(facing, Direction.NORTH).withShape(
            left = false,
            right = false,
            forward = false
        )
    }

    @Deprecated("Deprecated in Java")
    override fun getOutlineShape(
        state: BlockState,
        world: BlockView?,
        pos: BlockPos?,
        context: ShapeContext?
    ): VoxelShape =
        shapeMap[state[shape]]?.get(state[facing]) ?: VoxelShapes.fullCube()

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) {
        super.appendProperties(builder)
        builder.add(facing, shape)
    }

    override fun getPlacementState(ctx: ItemPlacementContext): BlockState {
        return super.getPlacementState(ctx).with(facing, ctx.horizontalPlayerFacing.opposite)
    }

    @Deprecated("Deprecated in Java")
    override fun getStateForNeighborUpdate(
        state: BlockState,
        direction: Direction?,
        neighborState: BlockState?,
        world: WorldAccess,
        pos: BlockPos?,
        neighborPos: BlockPos?
    ): BlockState {
        val left = world.getBlockState(pos?.offset(state[facing].rotateYClockwise())).isDesk
        val right = world.getBlockState(pos?.offset(state[facing].rotateYCounterclockwise())).isDesk
        val forward = world.getBlockState(pos?.offset(state[facing])).let {
            (it.isDesk || it.isDeskDrawer) && if (left) it[facing] == state[facing].rotateYClockwise()
            else if (right) it[facing] == state[facing].rotateYCounterclockwise() else true
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos)
            .withShape(left, right, forward)
    }

    private fun BlockState.withShape(left: Boolean, right: Boolean, forward: Boolean = false): BlockState {
        return this.withProperties {
            shape setTo when {
                left && right -> DeskShape.CENTER
                left && forward -> DeskShape.RIGHT_CORNER
                right && forward -> DeskShape.LEFT_CORNER
                left -> DeskShape.RIGHT
                right -> DeskShape.LEFT
                else -> DeskShape.SINGLE
            }
        }
    }

    override fun generateBlockStateModels(generator: BlockStateModelGenerator) {
        val block = this
        TextureMap().apply {
            put(TextureKey.TOP, block.topBlock.textureId)
            put(TextureKey.SIDE, block.baseBlock.textureId)
        }.let { map ->
            generator.blockStateCollector.accept(
                VariantsBlockStateSupplier.create(block)
                    .coordinate(
                        variantMap(
                            singleModel = ModModels.desk.upload(block, map, generator.modelCollector),
                            leftModel = ModModels.deskLeft.upload(block, map, generator.modelCollector),
                            centerModel = ModModels.deskCenter.upload(block, map, generator.modelCollector),
                            rightModel = ModModels.deskRight.upload(block, map, generator.modelCollector),
                            leftCornerModel = ModModels.deskLeftCorner.upload(block, map, generator.modelCollector)
                        )
                    )
            )
        }
    }

    private fun variantMap(
        singleModel: Identifier,
        leftModel: Identifier,
        centerModel: Identifier,
        rightModel: Identifier,
        leftCornerModel: Identifier
    ) = BlockStateVariantMap.create(Properties.HORIZONTAL_FACING, ModProperties.deskShape).apply {
        val northSingle = singleModel.asBlockStateVariant()
        val northLeft = leftModel.asBlockStateVariant()
        val northCenter = centerModel.asBlockStateVariant()
        val northRight = rightModel.asBlockStateVariant()
        val northLeftCorner = leftCornerModel.asBlockStateVariant()

        mapOf(
            Direction.NORTH to mapOf(
                DeskShape.SINGLE to northSingle,
                DeskShape.LEFT to northLeft,
                DeskShape.CENTER to northCenter,
                DeskShape.RIGHT to northRight,
                DeskShape.LEFT_CORNER to northLeftCorner,
                DeskShape.RIGHT_CORNER to northLeftCorner.withYRotationOf(VariantSettings.Rotation.R90)
            ),
            Direction.EAST to mapOf(
                DeskShape.SINGLE to northSingle.withYRotationOf(VariantSettings.Rotation.R90),
                DeskShape.LEFT to northLeft.withYRotationOf(VariantSettings.Rotation.R90),
                DeskShape.CENTER to northCenter.withYRotationOf(VariantSettings.Rotation.R90),
                DeskShape.RIGHT to northRight.withYRotationOf(VariantSettings.Rotation.R90),
                DeskShape.LEFT_CORNER to northLeftCorner.withYRotationOf(VariantSettings.Rotation.R90),
                DeskShape.RIGHT_CORNER to northLeftCorner.withYRotationOf(VariantSettings.Rotation.R180)
            ),
            Direction.SOUTH to mapOf(
                DeskShape.SINGLE to northSingle.withYRotationOf(VariantSettings.Rotation.R180),
                DeskShape.LEFT to northLeft.withYRotationOf(VariantSettings.Rotation.R180),
                DeskShape.CENTER to northCenter.withYRotationOf(VariantSettings.Rotation.R180),
                DeskShape.RIGHT to northRight.withYRotationOf(VariantSettings.Rotation.R180),
                DeskShape.LEFT_CORNER to northLeftCorner.withYRotationOf(VariantSettings.Rotation.R180),
                DeskShape.RIGHT_CORNER to northLeftCorner.withYRotationOf(VariantSettings.Rotation.R270)
            ),
            Direction.WEST to mapOf(
                DeskShape.SINGLE to northSingle.withYRotationOf(VariantSettings.Rotation.R270),
                DeskShape.LEFT to northLeft.withYRotationOf(VariantSettings.Rotation.R270),
                DeskShape.CENTER to northCenter.withYRotationOf(VariantSettings.Rotation.R270),
                DeskShape.RIGHT to northRight.withYRotationOf(VariantSettings.Rotation.R270),
                DeskShape.LEFT_CORNER to northLeftCorner.withYRotationOf(VariantSettings.Rotation.R270),
                DeskShape.RIGHT_CORNER to northLeftCorner
            )
        ).forEach { i -> i.value.forEach { j -> register(i.key, j.key, j.value) } }
    }

    override fun offerRecipeTo(exporter: Consumer<RecipeJsonProvider>) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, this, 4).apply {
            input('|', baseBlock)
            input('_', topBlock)
            pattern("___")
            pattern("| |")
            pattern("| |")
            customGroup(this@DeskBlock, "desks")
            requires(baseBlock)
            offerTo(exporter)
        }
    }

    companion object {
        val shape = ModProperties.deskShape
        val facing: DirectionProperty = Properties.HORIZONTAL_FACING

        //shapes
        private val northSingleShape = VoxelShapeCombining.union(
            VoxelShapeUtils.createCuboidShape(1, 15, 0, 15, 16, 16),
            VoxelShapeUtils.createCuboidShape(1, 8, 15, 15, 15, 16),
            VoxelShapeUtils.createCuboidShape(15, 0, 0, 16, 16, 2),
            VoxelShapeUtils.createCuboidShape(15, 14, 2, 16, 16, 14),
            VoxelShapeUtils.createCuboidShape(15, 0, 14, 16, 16, 16),
            VoxelShapeUtils.createCuboidShape(0, 0, 0, 1, 16, 2),
            VoxelShapeUtils.createCuboidShape(0, 14, 2, 1, 16, 14),
            VoxelShapeUtils.createCuboidShape(0, 0, 14, 1, 16, 16)
        )

        val northLeftShape = VoxelShapeCombining.union(
            VoxelShapeUtils.createCuboidShape(0, 15, 0, 15, 16, 16),
            VoxelShapeUtils.createCuboidShape(0, 8, 15, 15, 15, 16),
            VoxelShapeUtils.createCuboidShape(15, 0, 0, 16, 16, 2),
            VoxelShapeUtils.createCuboidShape(15, 14, 2, 16, 16, 14),
            VoxelShapeUtils.createCuboidShape(15, 0, 14, 16, 16, 16)
        )

        val northCenterShape = VoxelShapeCombining.union(
            VoxelShapeUtils.createCuboidShape(0, 15, 0, 16, 16, 16),
            VoxelShapeUtils.createCuboidShape(0, 8, 15, 16, 15, 16)
        )

        val northRightShape = VoxelShapeCombining.union(
            VoxelShapeUtils.createCuboidShape(1, 15, 0, 16, 16, 16),
            VoxelShapeUtils.createCuboidShape(1, 8, 15, 16, 15, 16),
            VoxelShapeUtils.createCuboidShape(0, 0, 0, 1, 16, 2),
            VoxelShapeUtils.createCuboidShape(0, 14, 2, 1, 16, 14),
            VoxelShapeUtils.createCuboidShape(0, 0, 14, 1, 16, 16)
        )

        private val northLeftCornerShape = VoxelShapeCombining.union(
            VoxelShapeUtils.createCuboidShape(15, 0, 14, 16, 15, 16),
            VoxelShapeUtils.createCuboidShape(14, 0, 15, 15, 15, 16),
            VoxelShapeUtils.createCuboidShape(0, 8, 15, 14, 15, 16),
            VoxelShapeUtils.createCuboidShape(15, 8, 0, 16, 15, 14),
            VoxelShapeUtils.createCuboidShape(0, 15, 0, 16, 16, 16)
        )
        private val northRightCornerShape = northLeftCornerShape.rotateLeft()

        private val singleShapeMap = mapOf(
            Direction.NORTH to northSingleShape,
            Direction.EAST to northSingleShape.rotateLeft(),
            Direction.SOUTH to northSingleShape.flip(),
            Direction.WEST to northSingleShape.rotateRight()
        )
        private val centerShapeMap =
            mapOf(
                Direction.NORTH to northCenterShape,
                Direction.EAST to northCenterShape.rotateLeft(),
                Direction.SOUTH to northCenterShape.flip(),
                Direction.WEST to northCenterShape.rotateRight()
            )
        private val leftShapeMap = mapOf(
            Direction.NORTH to northLeftShape,
            Direction.EAST to northLeftShape.rotateLeft(),
            Direction.SOUTH to northLeftShape.flip(),
            Direction.WEST to northLeftShape.rotateRight()
        )
        private val rightShapeMap = mapOf(
            Direction.NORTH to northRightShape,
            Direction.EAST to northRightShape.rotateLeft(),
            Direction.SOUTH to northRightShape.flip(),
            Direction.WEST to northRightShape.rotateRight()
        )
        private val leftCornerShapeMap = mapOf(
            Direction.NORTH to northLeftCornerShape,
            Direction.EAST to northLeftCornerShape.rotateLeft(),
            Direction.SOUTH to northLeftCornerShape.flip(),
            Direction.WEST to northLeftCornerShape.rotateRight()
        )
        private val rightCornerShapeMap = mapOf(
            Direction.NORTH to northRightCornerShape,
            Direction.EAST to northRightCornerShape.rotateLeft(),
            Direction.SOUTH to northRightCornerShape.flip(),
            Direction.WEST to northRightCornerShape.rotateRight()
        )
        private val shapeMap = mapOf(
            DeskShape.SINGLE to singleShapeMap,
            DeskShape.CENTER to centerShapeMap,
            DeskShape.LEFT to leftShapeMap,
            DeskShape.RIGHT to rightShapeMap,
            DeskShape.LEFT_CORNER to leftCornerShapeMap,
            DeskShape.RIGHT_CORNER to rightCornerShapeMap
        )
    }
}