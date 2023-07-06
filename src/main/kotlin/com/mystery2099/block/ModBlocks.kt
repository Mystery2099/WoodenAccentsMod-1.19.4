package com.mystery2099.block

import com.mystery2099.WoodenAccentsMod
import com.mystery2099.WoodenAccentsMod.toId
import com.mystery2099.block.custom.*
import com.mystery2099.datagen.BlockTagDataGen
import com.mystery2099.datagen.EnglishLangDataGen
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.item.BlockItem
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier

object ModBlocks {

    /*---------------Outside Stuff----------------*/

    //Thin plank pillars
    @JvmStatic
    val thinOakPillar = ThinPillarBlock(Blocks.OAK_PLANKS).register("thin_oak_pillar")
    @JvmStatic
    val thinSprucePillar = ThinPillarBlock(Blocks.SPRUCE_PLANKS).register("thin_spruce_pillar")
    @JvmStatic
    val thinBirchPillar = ThinPillarBlock(Blocks.BIRCH_PLANKS).register("thin_birch_pillar")
    @JvmStatic
    val thinJunglePillar = ThinPillarBlock(Blocks.JUNGLE_PLANKS).register("thin_jungle_pillar")
    @JvmStatic
    val thinAcaciaPillar = ThinPillarBlock(Blocks.ACACIA_PLANKS).register("thin_acacia_pillar")
    @JvmStatic
    val thinDarkOakPillar = ThinPillarBlock(Blocks.DARK_OAK_PLANKS).register("thin_dark_oak_pillar")
    @JvmStatic
    val thinMangrovePillar = ThinPillarBlock(Blocks.MANGROVE_PLANKS).register("thin_mangrove_pillar")
    @JvmStatic
    val thinCherryPillar = ThinPillarBlock(Blocks.CHERRY_PLANKS).register("thin_cherry_pillar")
    @JvmStatic
    val thinBambooPillar = ThinPillarBlock(Blocks.BAMBOO_PLANKS).register("thin_bamboo_pillar")
    @JvmStatic
    val thinBambooMosaicPillar = ThinPillarBlock(Blocks.BAMBOO_MOSAIC).register("thin_bamboo_mosaic_pillar")
    @JvmStatic
    val thinCrimsonPillar = ThinPillarBlock(Blocks.CRIMSON_PLANKS).register("thin_crimson_pillar")
    @JvmStatic
    val thinWarpedPillar = ThinPillarBlock(Blocks.WARPED_PLANKS).register("thin_warped_pillar")

    //Thick plank pillars
    @JvmStatic
    val thickOakPillar = ThickPillarBlock(Blocks.OAK_PLANKS).register("thick_oak_pillar")
    @JvmStatic
    val thickSprucePillar = ThickPillarBlock(Blocks.SPRUCE_PLANKS).register("thick_spruce_pillar")
    @JvmStatic
    val thickBirchPillar = ThickPillarBlock(Blocks.BIRCH_PLANKS).register("thick_birch_pillar")
    @JvmStatic
    val thickJunglePillar = ThickPillarBlock(Blocks.JUNGLE_PLANKS).register("thick_jungle_pillar")
    @JvmStatic
    val thickAcaciaPillar = ThickPillarBlock(Blocks.ACACIA_PLANKS).register("thick_acacia_pillar")
    @JvmStatic
    val thickDarkOakPillar = ThickPillarBlock(Blocks.DARK_OAK_PLANKS).register("thick_dark_oak_pillar")
    @JvmStatic
    val thickMangrovePillar = ThickPillarBlock(Blocks.MANGROVE_PLANKS).register("thick_mangrove_pillar")
    @JvmStatic
    val thickCherryPillar = ThickPillarBlock(Blocks.CHERRY_PLANKS).register("thick_cherry_pillar")
    @JvmStatic
    val thickBambooPillar = ThickPillarBlock(Blocks.BAMBOO_PLANKS).register("thick_bamboo_pillar")
    @JvmStatic
    val thickBambooMosaicPillar = ThickPillarBlock(Blocks.BAMBOO_MOSAIC).register("thick_bamboo_mosaic_pillar")
    @JvmStatic
    val thickCrimsonPillar = ThickPillarBlock(Blocks.CRIMSON_PLANKS).register("thick_crimson_pillar")
    @JvmStatic
    val thickWarpedPillar = ThickPillarBlock(Blocks.WARPED_PLANKS).register("thick_warped_pillar")

    /*---------------Living Room Stuff----------------*/
    //Tables
    @JvmStatic
    val oakTable = TableBlock(Blocks.OAK_PLANKS, Blocks.OAK_LOG).register("oak_table")
    @JvmStatic
    val spruceTable = TableBlock(Blocks.SPRUCE_PLANKS, Blocks.SPRUCE_LOG).register("spruce_table")
    @JvmStatic
    val birchTable = TableBlock(Blocks.BIRCH_PLANKS, Blocks.BIRCH_LOG).register("birch_table")
    @JvmStatic
    val jungleTable = TableBlock(Blocks.JUNGLE_PLANKS, Blocks.JUNGLE_LOG).register("jungle_table")
    @JvmStatic
    val acaciaTable = TableBlock(Blocks.ACACIA_PLANKS, Blocks.ACACIA_LOG).register("acacia_table")
    @JvmStatic
    val darkOakTable = TableBlock(Blocks.DARK_OAK_PLANKS, Blocks.DARK_OAK_LOG).register("dark_oak_table")
    @JvmStatic
    val mangroveTable = TableBlock(Blocks.MANGROVE_PLANKS, Blocks.MANGROVE_LOG).register("mangrove_table")
    @JvmStatic
    val cherryTable = TableBlock(Blocks.CHERRY_PLANKS, Blocks.CHERRY_LOG).register("cherry_table")
    @JvmStatic
    val bambooTable = TableBlock(Blocks.BAMBOO_PLANKS, Blocks.BAMBOO_BLOCK).register("bamboo_table")
    @JvmStatic
    val bambooMosaicTable = TableBlock(Blocks.BAMBOO_MOSAIC, Blocks.BAMBOO_BLOCK).register("bamboo_mosaic_table")
    @JvmStatic
    val crimsonTable = TableBlock(Blocks.CRIMSON_PLANKS, Blocks.CRIMSON_STEM).register("crimson_table")
    @JvmStatic
    val warpedTable = TableBlock(Blocks.WARPED_PLANKS, Blocks.WARPED_STEM).register("warped_table")

    //Coffee tables
    @JvmStatic
    val oakCoffeeTable = CoffeeTableBlock(Blocks.OAK_PLANKS, Blocks.OAK_LOG).register("oak_coffee_table")
    @JvmStatic
    val spruceCoffeeTable = CoffeeTableBlock(Blocks.SPRUCE_PLANKS, Blocks.SPRUCE_LOG).register("spruce_coffee_table")
    @JvmStatic
    val birchCoffeeTable = CoffeeTableBlock(Blocks.BIRCH_PLANKS, Blocks.BIRCH_LOG).register("birch_coffee_table")
    @JvmStatic
    val jungleCoffeeTable = CoffeeTableBlock(Blocks.JUNGLE_PLANKS, Blocks.JUNGLE_LOG).register("jungle_coffee_table")
    @JvmStatic
    val acaciaCoffeeTable = CoffeeTableBlock(Blocks.ACACIA_PLANKS, Blocks.ACACIA_LOG).register("acacia_coffee_table")
    @JvmStatic
    val darkOakCoffeeTable = CoffeeTableBlock(Blocks.DARK_OAK_PLANKS, Blocks.DARK_OAK_LOG).register("dark_oak_coffee_table")
    @JvmStatic
    val mangroveCoffeeTable = CoffeeTableBlock(Blocks.MANGROVE_PLANKS, Blocks.MANGROVE_LOG).register("mangrove_coffee_table")
    @JvmStatic
    val cherryCoffeeTable = CoffeeTableBlock(Blocks.CHERRY_PLANKS, Blocks.CHERRY_LOG).register("cherry_coffee_table")
    @JvmStatic
    val bambooCoffeeTable = CoffeeTableBlock(Blocks.BAMBOO_PLANKS, Blocks.BAMBOO_BLOCK).register("bamboo_coffee_table")
    @JvmStatic
    val bambooMosaicCoffeeTable = CoffeeTableBlock(Blocks.BAMBOO_MOSAIC, Blocks.BAMBOO_BLOCK).register("bamboo_mosaic_coffee_table")
    @JvmStatic
    val crimsonCoffeeTable = CoffeeTableBlock(Blocks.CRIMSON_PLANKS, Blocks.CRIMSON_STEM).register("crimson_coffee_table")
    @JvmStatic
    val warpedCoffeeTable = CoffeeTableBlock(Blocks.WARPED_PLANKS, Blocks.CRIMSON_STEM).register("warped_coffee_table")

    /*---------------Storage Stuff----------------*/

    /*---------------Kitchen Stuff----------------*/
    @JvmStatic
    val oakKitchenCounter = KitchenCounterBlock(Blocks.OAK_PLANKS, Blocks.OAK_LOG).register("oak_kitchen_counter")
    @JvmStatic
    val spruceKitchenCounter = KitchenCounterBlock(Blocks.SPRUCE_PLANKS, Blocks.SPRUCE_LOG).register("spruce_kitchen_counter")
    @JvmStatic
    val birchKitchenCounter = KitchenCounterBlock(Blocks.BIRCH_PLANKS, Blocks.BIRCH_LOG).register("birch_kitchen_counter")
    @JvmStatic
    val jungleKitchenCounter = KitchenCounterBlock(Blocks.JUNGLE_PLANKS, Blocks.JUNGLE_LOG).register("jungle_kitchen_counter")
    @JvmStatic
    val acaciaKitchenCounter = KitchenCounterBlock(Blocks.ACACIA_PLANKS, Blocks.ACACIA_LOG).register("acacia_kitchen_counter")
    @JvmStatic
    val darkOakKitchenCounter = KitchenCounterBlock(Blocks.DARK_OAK_PLANKS, Blocks.DARK_OAK_LOG).register("dark_oak_kitchen_counter")
    @JvmStatic
    val mangroveKitchenCounter = KitchenCounterBlock(Blocks.MANGROVE_PLANKS, Blocks.MANGROVE_LOG).register("mangrove_kitchen_counter")
    @JvmStatic
    val cherryKitchenCounter = KitchenCounterBlock(Blocks.CHERRY_PLANKS, Blocks.CHERRY_LOG).register("cherry_kitchen_counter")
    @JvmStatic
    val bambooKitchenCounter = KitchenCounterBlock(Blocks.BAMBOO_PLANKS, Blocks.BAMBOO_BLOCK).register("bamboo_kitchen_counter")
    @JvmStatic
    val bambooMosaicKitchenCounter = KitchenCounterBlock(Blocks.BAMBOO_MOSAIC, Blocks.BAMBOO_BLOCK).register("bamboo_mosaic_kitchen_counter")
    @JvmStatic
    val crimsonKitchenCounter = KitchenCounterBlock(Blocks.CRIMSON_PLANKS, Blocks.CRIMSON_STEM).register("crimson_kitchen_counter")
    @JvmStatic
    val warpedKitchenCounter = KitchenCounterBlock(Blocks.WARPED_PLANKS, Blocks.CRIMSON_STEM).register("warped_kitchen_counter")

    /*---------------Bedroom Stuff----------------*/









    private fun Block.register(id: String): Block {
        return this.register(id.toId())
    }
    private fun Block.register(identifier: Identifier): Block {
        return Registry.register(Registries.BLOCK, identifier, this).also {
            BlockTagDataGen.axeMineable.add(this)
            EnglishLangDataGen.basicNamedBlocks.add(this)
            Registry.register(Registries.ITEM, identifier, BlockItem(this, FabricItemSettings()))
        }
    }

    fun register() {
        WoodenAccentsMod.logger.info("Registering ModBlocks for mod: ${WoodenAccentsMod.modid}")
    }

}