package com.mystery2099.wooden_accents_mod.state.property

import com.mystery2099.wooden_accents_mod.block.custom.enums.CoffeeTableType
import net.minecraft.state.property.EnumProperty

object ModProperties {
    @JvmStatic
    val coffeeTableType: EnumProperty<CoffeeTableType> = EnumProperty.of("type", CoffeeTableType::class.java)
}