package com.mystery2099.data

import com.mystery2099.WoodenAccentsMod.toId
import net.minecraft.data.client.Model
import net.minecraft.data.client.TextureKey
import java.util.*

object ModModels {
    /*---------------Texture Keys----------------*/
    @JvmStatic
    val legs: TextureKey = TextureKey.of("legs")


    /*---------------Outside Stuff----------------*/

    //Thin Pillar
    @JvmStatic
    val thinPillarInventory = block("thin_pillar", "_inventory", TextureKey.ALL)
    @JvmStatic
    val thinPillarTop = block("thin_pillar_top", "_top", TextureKey.ALL)
    @JvmStatic
    val thinPillarBottom = block("thin_pillar_base", "_base", TextureKey.ALL)
    @JvmStatic
    val thinPillarCenter = block("thin_pillar_center", "_center", TextureKey.ALL)
    //Thick Pillar
    @JvmStatic
    val thickPillarInventory = block("thick_pillar", "_inventory", TextureKey.ALL)
    @JvmStatic
    val thickPillarTop = block("thick_pillar_top", "_top", TextureKey.ALL)
    @JvmStatic
    val thickPillarBottom = block("thick_pillar_base", "_base", TextureKey.ALL)
    @JvmStatic
    val thickPillarCenter = block("thick_pillar_center", "_center", TextureKey.ALL)

    /*---------------Living Room Stuff----------------*/
    //Coffee Table Models
    @JvmStatic
    val coffeeTableInventory: Model = block("coffee_table", "_inventory", TextureKey.TOP, legs)
    @JvmStatic
    val coffeeTableTopTall: Model = block("coffee_table_top_tall",
        "_top_tall", TextureKey.TOP)
    @JvmStatic
    val coffeeTableLegTall: Model = block(
        "coffee_table_leg_tall",
        "_leg_tall",
        legs
    )
    @JvmStatic
    val coffeeTableTopShort: Model =
        block("coffee_table_top_short", "_top_short", TextureKey.TOP)
    @JvmStatic
    val coffeeTableLegShort: Model = block(
        "coffee_table_leg_short",
        "_leg_short",
        legs
    )
    /*---------------Storage Stuff----------------*/

    /*---------------Kitchen Stuff----------------*/

    /*---------------Bedroom Stuff----------------*/


    @Suppress("unused")
    private fun make(vararg requiredTextureKeys: TextureKey): Model {
        return Model(Optional.empty(), Optional.empty(), *requiredTextureKeys)
    }

    private fun block(parent: String, vararg requiredTextureKeys: TextureKey): Model {
        return Model(
            Optional.of("block/$parent".toId()),
            Optional.empty(),
            *requiredTextureKeys
        )
    }

    @Suppress("unused")
    private fun item(parent: String, vararg requiredTextureKeys: TextureKey): Model {
        return Model(
            Optional.of("item/$parent".toId()),
            Optional.empty(),
            *requiredTextureKeys
        )
    }

    private fun block(parent: String, variant: String, vararg requiredTextureKeys: TextureKey): Model {
        return Model(
            Optional.of("block/$parent".toId()),
            Optional.of(variant),
            *requiredTextureKeys
        )
    }
}