package com.mystery2099.wooden_accents_mod.data

import com.mystery2099.wooden_accents_mod.WoodenAccentsMod
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.item.Item
import net.minecraft.registry.RegistryKeys
import net.minecraft.registry.tag.TagKey
import net.minecraft.util.Identifier

object ModBlockTags {
    internal val blockTagWithMatchingItemTag: MutableMap<TagKey<Block>, TagKey<Item>> = HashMap()


    /*---------------Outside Tags----------------*/
    //Pillars
    val pillars = "pillars".toBlockTag().withMatchingItemTag()
    val thinPillars = "thin_pillars".toBlockTag().withMatchingItemTag()
    val thickPillars = "thick_pillars".toBlockTag().withMatchingItemTag()
    val thinPillarsConnectable = "thin_pillars_connectable".toBlockTag()
    val thickPillarsConnectable = "thick_pillars_connectable".toBlockTag()
    //Walls
    val woodenWalls = "wooden_walls".toBlockTag().withMatchingItemTag()
    //Ladders
    val plankLadders = "plank_ladders".toBlockTag().withMatchingItemTag()
    val connectingLadders = "connecting_ladders".toBlockTag().withMatchingItemTag()

    //Fences
    val modernFences = "modern_fences".toBlockTag().withMatchingItemTag()
    val modernFenceGates = "modern_fence_gates".toBlockTag().withMatchingItemTag()

    val supportBeams = "support_beams".toBlockTag().withMatchingItemTag()
    val crates = "crates".toBlockTag().withMatchingItemTag()
    /*---------------End Outside Tags----------------*/

    /*---------------Living Room Tags----------------*/
    val tables = "tables".toBlockTag().withMatchingItemTag()
    val coffeeTables = "coffee_tables".toBlockTag().withMatchingItemTag()
    val thinBookshelves = "thin_bookshelves".toBlockTag().withMatchingItemTag()
    val plankCarpets = "plank_carpets".toBlockTag().withMatchingItemTag()

    /*---------------End Living Room Tags----------------*/

    /*---------------Kitchen Tags----------------*/
    @JvmStatic
    val kitchenCounters = "kitchen_counters".toBlockTag().withMatchingItemTag()
    val kitchenCabinets = "kitchen_cabinets".toBlockTag().withMatchingItemTag()
    /*---------------End Kitchen Tags----------------*/

    /*---------------Storage Tags----------------*/
    /*---------------End Storage Tags----------------*/

    private fun String.toBlockTag(namespace: String = WoodenAccentsMod.MOD_ID): TagKey<Block> {
        return TagKey.of(RegistryKeys.BLOCK, Identifier(namespace, this))
    }
    private fun TagKey<Block>.withMatchingItemTag() = this.also {
        blockTagWithMatchingItemTag[this] = TagKey.of(RegistryKeys.ITEM, this.id)
    }
    infix fun BlockState.isIn(tag: TagKey<Block>) = isIn(tag)
    infix operator fun TagKey<Block>.contains(block: BlockState) = block.isIn(this)

}