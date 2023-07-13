package com.mystery2099.block_entity.custom

import com.mystery2099.block.custom.KitchenCabinetBlock
import com.mystery2099.block_entity.ModBlockEntities
import net.minecraft.block.BarrelBlock
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.block.entity.LootableContainerBlockEntity
import net.minecraft.block.entity.ViewerCountManager
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.Inventories
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.screen.GenericContainerScreenHandler
import net.minecraft.screen.ScreenHandler
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvent
import net.minecraft.sound.SoundEvents
import net.minecraft.text.Text
import net.minecraft.util.collection.DefaultedList
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class KitchenCabinetBlockEntity(pos: BlockPos, state: BlockState) : LootableContainerBlockEntity(ModBlockEntities.kitchenCabinet, pos, state) {
    private var inventory: DefaultedList<ItemStack> = DefaultedList.ofSize(27, ItemStack.EMPTY)
    val stateManager: ViewerCountManager = object : ViewerCountManager() {
        override fun onContainerOpen(world: World, pos: BlockPos, state: BlockState) {
            playSound(state, SoundEvents.BLOCK_BARREL_OPEN)
            setOpen(state, true)
        }

        override fun onContainerClose(world: World, pos: BlockPos, state: BlockState) {
            playSound(state, SoundEvents.BLOCK_BARREL_CLOSE)
            setOpen(state, false)
        }

        override fun onViewerCountUpdate(
            world: World,
            pos: BlockPos,
            state: BlockState,
            oldViewerCount: Int,
            newViewerCount: Int
        ) {
        }

        override fun isPlayerViewing(player: PlayerEntity): Boolean {
            if (player.currentScreenHandler is GenericContainerScreenHandler) {
                val inventory = (player.currentScreenHandler as GenericContainerScreenHandler).inventory
                return inventory === this@KitchenCabinetBlockEntity
            }
            return false
        }
    }

    override fun writeNbt(nbt: NbtCompound?) {
        super.writeNbt(nbt)
        if (!this.serializeLootTable(nbt)) {
            Inventories.writeNbt(nbt, inventory)
        }
    }

    override fun readNbt(nbt: NbtCompound?) {
        super.readNbt(nbt)
        inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY)
        if (!this.deserializeLootTable(nbt)) {
            Inventories.readNbt(nbt, inventory)
        }
    }

    override fun size(): Int {
        return 27
    }

    override fun getInvStackList(): DefaultedList<ItemStack> {
        return inventory
    }

    override fun setInvStackList(list: DefaultedList<ItemStack>) {
        inventory = list
    }

    override fun getContainerName(): Text {
        return Text.translatable(cachedState.block.translationKey)
    }

    override fun createScreenHandler(syncId: Int, playerInventory: PlayerInventory?): ScreenHandler? {
        return GenericContainerScreenHandler.createGeneric9x3(syncId, playerInventory, this)
    }

    override fun onOpen(player: PlayerEntity) {
        if (!this.removed && !player.isSpectator) {
            this.stateManager.openContainer(player, this.getWorld(), this.getPos(), this.cachedState)
        }
    }

    override fun onClose(player: PlayerEntity) {
        if (!this.removed && !player.isSpectator) {
            this.stateManager.closeContainer(player, this.getWorld(), this.getPos(), this.cachedState)
        }
    }

    fun tick() {
        if (!this.removed) {
            this.stateManager.updateViewerCount(this.getWorld(), this.getPos(), this.cachedState)
        }
    }

    fun setOpen(state: BlockState, open: Boolean) {
        this.world?.setBlockState(this.getPos(), state.with(KitchenCabinetBlock.open, open), Block.NOTIFY_ALL)
    }

    fun playSound(state: BlockState, soundEvent: SoundEvent?) {
        val vec3i = state.get(BarrelBlock.FACING).vector
        val d: Double = this.pos.x.toDouble() + 0.5 + vec3i.x.toDouble() / 2.0
        val e: Double = this.pos.y.toDouble() + 0.5 + vec3i.y.toDouble() / 2.0
        val f: Double = this.pos.z.toDouble() + 0.5 + vec3i.z.toDouble() / 2.0
        this.world?.playSound(
            null,
            d,
            e,
            f,
            soundEvent,
            SoundCategory.BLOCKS,
            0.5f,
            this.world!!.random.nextFloat() * 0.1f + 0.9f
        )
    }
}

