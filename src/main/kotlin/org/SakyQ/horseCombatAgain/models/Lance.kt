package org.SakyQ.horseCombatAgain.models

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

object Lance {
    private const val LANCE_CUSTOM_MODEL_DATA = 12345

    fun getLance(player: Player): ItemStack? {
        val item = player.inventory.itemInMainHand
        return if (isLance(item)) item else null
    }

    fun isLance(item: ItemStack): Boolean {
        return item.type == Material.STICK &&
                item.hasItemMeta() &&
                item.itemMeta?.customModelData == LANCE_CUSTOM_MODEL_DATA
    }

    fun createLance(): ItemStack {
        return ItemStack(Material.STICK).apply {
            itemMeta = Bukkit.getItemFactory().getItemMeta(Material.STICK)?.also { meta ->
                meta.setDisplayName("§6Combat Lance")
                meta.lore = listOf("§7A specialized lance for mounted combat")
                meta.setCustomModelData(LANCE_CUSTOM_MODEL_DATA)
            }
        }
    }
}