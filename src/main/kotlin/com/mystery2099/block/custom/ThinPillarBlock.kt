package com.mystery2099.block.custom

import com.mystery2099.WoodenAccentsModItemGroups
import net.minecraft.block.Block
import net.minecraft.util.math.BlockPos
import net.minecraft.world.WorldAccess

class ThinPillarBlock(baseBlock: Block) : AbstractPillarBlock(baseBlock, size) {

    override fun WorldAccess.checkUp(pos: BlockPos): Boolean {
        val here = this.getBlockState(pos)
        val up = this.getUpState(pos)
        return up.block is ThinPillarBlock && !here.get(connectionLocked) && !up.get(connectionLocked)
    }

    override fun WorldAccess.checkDown(pos: BlockPos): Boolean {
        val here = this.getBlockState(pos)
        val down = this.getDownState(pos)
        return down.block is ThinPillarBlock && !here.get(connectionLocked) && !down.get(connectionLocked)
    }
    companion object {
        @JvmStatic
        val size = Size(
            createCuboidShape(4.0, 13.0, 4.0, 12.0, 16.0, 12.0),
            createCuboidShape(6.0, 1.0, 6.0, 10.0, 16.0, 10.0),
            createCuboidShape(4.0, 0.0, 4.0, 12.0, 3.0, 12.0)
        )
        @JvmStatic
        val instances = HashSet<ThinPillarBlock>()
    }
    init {
        instances.add(this)
        WoodenAccentsModItemGroups.outsideItems.add(this)

    }
}