package com.mystery2099.block.custom.enums

import net.minecraft.util.StringIdentifiable


enum class CoffeeTableType(string: String) : StringIdentifiable {
    SHORT("short"),
    TALL("tall");

    private val string: String
    init {
        this.string = string
    }
    override fun asString(): String {
        return string
    }
}

