package org.SakyQ.horseCombatAgain.commands

import org.SakyQ.horseCombatAgain.HorseCombatAgain
import org.SakyQ.horseCombatAgain.models.Lance
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin

class GiveLanceCommand(private val plugin: HorseCombatAgain) : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender is Player) {
            val lance = ItemStack(Material.STICK).apply {
                itemMeta = itemMeta?.also { meta ->
                    meta.setDisplayName("§6Combat Lance")
                    meta.lore = listOf("§7A specialized lance for mounted combat")
                    meta.setCustomModelData(12345)
                }
            }
            sender.inventory.addItem(lance)
            sender.sendMessage("§aYou have been given a Combat Lance!")
            return true
        } else {
            sender.sendMessage("§cThis command can only be executed by a player.")
            return false
        }
    }
}