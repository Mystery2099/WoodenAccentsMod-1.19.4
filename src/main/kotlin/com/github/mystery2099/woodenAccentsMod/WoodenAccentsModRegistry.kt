package com.github.mystery2099.woodenAccentsMod

/**
 * Wooden accents mod registry
 *
 * @constructor Create empty Wooden accents mod registry
 */
interface WoodenAccentsModRegistry {
    /**
     * Register:
     * Logs the name of the class it's called on and stats that it's for this mod
     */
    fun register() {
        WoodenAccentsMod.LOGGER.info("Registering ${this::class.simpleName} for mod: ${WoodenAccentsMod.MOD_ID}")
    }
}