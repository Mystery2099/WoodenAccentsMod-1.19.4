package com.mystery2099.wooden_accents_mod.block.custom

import com.mystery2099.wooden_accents_mod.WoodenAccentsMod.asBlockModelId
import com.mystery2099.wooden_accents_mod.block.ModBlocks.getItemModelId
import com.mystery2099.wooden_accents_mod.block.ModBlocks.woodType
import com.mystery2099.wooden_accents_mod.data.ModBlockTags
import com.mystery2099.wooden_accents_mod.data.ModModels
import com.mystery2099.wooden_accents_mod.util.VoxelShapeHelper
import net.minecraft.block.Block
import net.minecraft.data.client.BlockStateModelGenerator
import net.minecraft.data.client.TextureMap
import net.minecraft.data.server.recipe.RecipeJsonProvider
import net.minecraft.item.Items
import net.minecraft.registry.tag.TagKey
import net.minecraft.util.Identifier
import java.util.function.Consumer

class ThinPillarBlock(baseBlock: Block) : AbstractPillarBlock(baseBlock, shape) {
    override val connectableBlockTag: TagKey<Block> = ModBlockTags.thinPillarsConnectable
    override val tag: TagKey<Block> = ModBlockTags.thinPillars

    override fun offerRecipeTo(exporter: Consumer<RecipeJsonProvider>) {
        this.offerRecipe(
            exporter = exporter,
            outputNum = 5,
            primaryInput = this.baseBlock,
            secondaryInput = Items.STICK
        )
    }

    override fun generateBlockStateModels(generator: BlockStateModelGenerator) {
        val map = TextureMap.all(this.baseBlock)
        generator.blockStateCollector.accept(this.genBlockStateModelSupplier(
            centerModel = Identifier("${this.woodType.name.lowercase()}_fence_post").asBlockModelId(),
            bottomModel = ModModels.thinPillarBottom.upload(this, map, generator.modelCollector)
        ))
        ModModels.thinPillarInventory.upload(this.getItemModelId(), map, generator.modelCollector)
    }

    companion object {
        val shape = Shape(
            topShape = VoxelShapeHelper.createCuboidShape(4, 13, 4, 12, 16, 12),
            centerShape = VoxelShapeHelper.createCuboidShape(6, 0, 6, 10, 16, 10),
            baseShape = VoxelShapeHelper.createCuboidShape(4, 0, 4, 12, 3, 12)
        )
    }
}