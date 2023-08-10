package com.mystery2099.wooden_accents_mod.util

import com.mystery2099.wooden_accents_mod.util.VoxelShapeHelper.rotateElements
import com.mystery2099.wooden_accents_mod.util.VoxelShapeHelper.unifyElements
import net.minecraft.block.Block
import net.minecraft.util.shape.VoxelShape

class CompositeVoxelShape(vararg voxelShapes: VoxelShape) {
    private var _voxelShapes: MutableList<VoxelShape>
    val voxelShapes: List<VoxelShape>
        get() = _voxelShapes
    inline val voxelShape: VoxelShape
        get() = voxelShapes.unifyElements()

    init {
        _voxelShapes = voxelShapes.toMutableList()
    }

    constructor(voxelShapes: Collection<VoxelShape>) : this(*voxelShapes.toTypedArray())

    infix operator fun get(index: Int): VoxelShape = _voxelShapes[index]
    fun get() = _voxelShapes.unifyElements()
    operator fun invoke(index: Int) = get(index)

    operator fun set(index: Int, voxelShape: VoxelShape) {
        _voxelShapes[index] = voxelShape
    }

    fun mergeWith(other: CompositeVoxelShape) = this.also { this._voxelShapes += other.voxelShapes }
    fun mergeWith(vararg others: CompositeVoxelShape): CompositeVoxelShape = this.also {
        it._voxelShapes += others.reduce { a, b -> a.mergeWith(b) }.voxelShapes
    }

    infix fun add(voxelShape: VoxelShape): CompositeVoxelShape = this.also {
        _voxelShapes += voxelShape
    }

    infix fun addAll(voxelShapes: Collection<VoxelShape>): CompositeVoxelShape = this.also {
        _voxelShapes += voxelShapes
    }

    fun addAll(vararg voxelShapes: VoxelShape): CompositeVoxelShape = this.also {
        _voxelShapes += voxelShapes
    }

    operator fun plus(voxelShape: VoxelShape): CompositeVoxelShape = copy().add(voxelShape)

    operator fun plus(voxelShapes: Collection<VoxelShape>): CompositeVoxelShape = this.copy().addAll(voxelShapes)

    operator fun plus(voxelShapes: Array<VoxelShape>): CompositeVoxelShape = this.copy().addAll(*voxelShapes)

    operator fun plusAssign(other: CompositeVoxelShape) {
        this.mergeWith(other)
    }

    operator fun plusAssign(voxelShape: VoxelShape) {
        add(voxelShape)
    }

    operator fun plusAssign(voxelShapes: Collection<VoxelShape>) {
        addAll(voxelShapes)
    }

    operator fun plusAssign(voxelShapes: Array<VoxelShape>) {
        addAll(*voxelShapes)
    }

    infix fun VoxelShape.shallBeAddedIf(boolean: Boolean) {
        if (boolean) add(this)
    }

    fun remove(voxelShape: VoxelShape) = this.also { _voxelShapes -= voxelShape }
    fun removeAll(voxelShapes: Collection<VoxelShape>) = this.also {
        _voxelShapes -= voxelShapes.toSet()
    }

    fun removeAll(voxelShapes: Array<VoxelShape>) = this.also {
        _voxelShapes -= voxelShapes.toSet()
    }

    operator fun minus(voxelShape: VoxelShape): CompositeVoxelShape = copy().remove(voxelShape)

    operator fun minus(voxelShapes: Collection<VoxelShape>): CompositeVoxelShape = this.copy().removeAll(voxelShapes)

    operator fun minus(voxelShapes: Array<VoxelShape>): CompositeVoxelShape = this.copy().removeAll(voxelShapes)

    operator fun minusAssign(voxelShape: VoxelShape) {
        remove(voxelShape)
    }

    operator fun minusAssign(voxelShapes: Collection<VoxelShape>) {
        removeAll(voxelShapes)
    }

    operator fun minusAssign(voxelShapes: Array<VoxelShape>) {
        removeAll(voxelShapes)
    }

    fun rotateLeft() = this.also {
        _voxelShapes = _voxelShapes.rotateElements(VoxelShapeTransformation.ROTATE_LEFT).toMutableList()
    }

    fun rotatedLeft(): CompositeVoxelShape = this.copy().also { it.rotateLeft() }
    fun rotateRight() = this.also {
        _voxelShapes = _voxelShapes.rotateElements(VoxelShapeTransformation.ROTATE_RIGHT).toMutableList()
    }

    fun rotatedRight(): CompositeVoxelShape = this.copy().also { it.rotateRight() }
    fun flip() = this.also {
        _voxelShapes = _voxelShapes.rotateElements(VoxelShapeTransformation.FLIP).toMutableList()
    }

    fun flipped(): CompositeVoxelShape = this.copy().also { it.flip() }

    fun copy() = CompositeVoxelShape(this.voxelShapes)

    companion object {
        fun of(vararg voxelShapes: VoxelShape) = CompositeVoxelShape(*voxelShapes)
        infix fun of(voxelShapes: Collection<VoxelShape>) = CompositeVoxelShape(voxelShapes)
        fun createCuboidShape(
            minX: Double,
            minY: Double,
            minZ: Double,
            maxX: Double,
            maxY: Double,
            maxZ: Double
        ) = Block.createCuboidShape(minX, minY, minZ, maxX, maxY, maxZ)

        fun createCuboidShape(
            minX: Int,
            minY: Int,
            minZ: Int,
            maxX: Int,
            maxY: Int,
            maxZ: Int
        ) = Block.createCuboidShape(
            minX.toDouble(),
            minY.toDouble(),
            minZ.toDouble(),
            maxX.toDouble(),
            maxY.toDouble(),
            maxZ.toDouble()
        )


        fun copy(compositeVoxelShape: CompositeVoxelShape) = compositeVoxelShape.copy()
    }
}