package com.mystery2099.wooden_accents_mod.data

import com.mystery2099.wooden_accents_mod.WoodenAccentsMod
import com.mystery2099.wooden_accents_mod.WoodenAccentsMod.toIdentifier
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.registry.RegistryKeys
import net.minecraft.registry.tag.TagKey

object ModItemTags {

    val chests = "chests" asItemTagOf "c"
    val uncrateable = "uncrateable".toItemTag()

    private infix fun String.asItemTagOf(namespace: String): TagKey<Item> = TagKey.of(RegistryKeys.ITEM, this.toIdentifier(namespace))
    private fun String.toItemTag(): TagKey<Item> = asItemTagOf(WoodenAccentsMod.MOD_ID)
    infix fun ItemStack.isIn(tag: TagKey<Item>) = isIn(tag)
    infix operator fun TagKey<Item>.contains(stack: ItemStack) = stack.isIn(this)

}