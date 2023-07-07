package com.mystery2099.data

import com.mystery2099.WoodenAccentsMod
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.registry.RegistryKeys
import net.minecraft.registry.tag.TagKey
import net.minecraft.util.Identifier

object ModBlockTags {
    internal var blockTagWithMatchingItemTag: MutableMap<TagKey<Block>, TagKey<Item>> = HashMap()

    val TEST_TAG: TagKey<Block> = "test_tag".toBlockTag().withMatchingItemTag()

    /*---------------Outside Tags----------------*/
    val pillars = "pillars".toBlockTag().withMatchingItemTag()
    val thinPillars = "thin_pillars".toBlockTag().withMatchingItemTag()
    val thickPillars = "thick_pillars".toBlockTag().withMatchingItemTag()
    /*---------------End Outside Tags----------------*/


    private fun String.toBlockTag(namespace: String): TagKey<Block> {
        return TagKey.of(RegistryKeys.BLOCK, Identifier(namespace, this))
    }
    private fun String.toBlockTag(): TagKey<Block> {
        return this.toBlockTag(WoodenAccentsMod.modid)
    }
    private fun TagKey<Block>.withMatchingItemTag(): TagKey<Block> {
        blockTagWithMatchingItemTag[this] = TagKey.of(RegistryKeys.ITEM, this.id)
        return this
    }

}