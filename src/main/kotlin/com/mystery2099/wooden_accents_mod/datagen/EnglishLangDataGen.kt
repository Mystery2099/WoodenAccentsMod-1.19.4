package com.mystery2099.wooden_accents_mod.datagen

import com.mystery2099.wooden_accents_mod.block.ModBlocks
import com.mystery2099.wooden_accents_mod.block.ModBlocks.id
import com.mystery2099.wooden_accents_mod.item_group.CreativeTab
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider
import net.minecraft.block.Block
import net.minecraft.item.ItemGroup

class EnglishLangDataGen(dataOutput: FabricDataOutput) : FabricLanguageProvider(dataOutput) {
    override fun generateTranslations(translationBuilder: TranslationBuilder) {
        with(translationBuilder) {
            ModBlocks.blocks.forEach{it.genLangName(this)}
            CreativeTab.instances.forEach{it.itemGroup.genLangName(this)}
        }

    }

    private fun ItemGroup.genLangName(translationBuilder: TranslationBuilder) {
        translationBuilder.add(this, "Wooden Accents: ${this.id.path.toName()}")
    }
    private fun Block.genLangName(translationBuilder: TranslationBuilder) {
        translationBuilder.add(this, id.path.toName())
    }
    private fun String?.toName(): String = if (isNullOrEmpty()) ""
    else split("_").joinToString(" ") { it.replaceFirstChar(Char::uppercase) }

}