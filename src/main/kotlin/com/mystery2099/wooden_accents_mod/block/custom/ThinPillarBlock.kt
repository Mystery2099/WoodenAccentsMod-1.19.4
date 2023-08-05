package com.mystery2099.wooden_accents_mod.block.custom

import com.mystery2099.wooden_accents_mod.data.ModBlockTags
import net.minecraft.block.Block
import net.minecraft.registry.tag.BlockTags
import net.minecraft.util.math.BlockPos
import net.minecraft.world.WorldAccess

class ThinPillarBlock(baseBlock: Block) : AbstractPillarBlock(baseBlock, shape) {

    override infix fun WorldAccess.checkUp(pos: BlockPos): Boolean = this.getUpState(pos).run {
        isIn(ModBlockTags.thinPillars) || isIn(BlockTags.FENCES)
    }

    override infix fun WorldAccess.checkDown(pos: BlockPos): Boolean = this.getDownState(pos).run {
        isIn(ModBlockTags.thinPillars) || isIn(BlockTags.FENCES)
    }
    companion object {
        @JvmStatic
        val shape = Shape(
            createCuboidShape(4.0, 13.0, 4.0, 12.0, 16.0, 12.0),
            createCuboidShape(6.0, 0.0, 6.0, 10.0, 16.0, 10.0),
            createCuboidShape(4.0, 0.0, 4.0, 12.0, 3.0, 12.0)
        )
    }
}