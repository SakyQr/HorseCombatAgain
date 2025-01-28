package org.SakyQ.horseCombatAgain.utils

import org.SakyQ.horseCombatAgain.HorseCombatAgain
import org.SakyQ.horseCombatAgain.models.Momentum
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import net.md_5.bungee.api.ChatMessageType
import net.md_5.bungee.api.chat.TextComponent

class MomentumBar(private val plugin: HorseCombatAgain) {
    val playerMomentum = mutableMapOf<Player, Momentum>()

    fun updateMomentum(player: Player) {
        // Check if player is riding a horse
        if (player.vehicle == null) {
            // Clear momentum if not on a horse
            resetMomentum(player)
            return
        }

        val currentMomentum = playerMomentum[player]?.value ?: 0


        val filledBlocks = (currentMomentum / 10).toInt()
        val emptyBlocks = 10 - filledBlocks

        val momentumBar = "§a" + "▮".repeat(filledBlocks) + "§7" + "▮".repeat(emptyBlocks)


        player.spigot().sendMessage(
            ChatMessageType.ACTION_BAR,
            TextComponent("§6Momentum: $momentumBar §e($currentMomentum/100)")
        )
    }


    fun decreaseMomentum(player: Player) {
        // Check if player is not on a horse
        if (player.vehicle == null) {
            resetMomentum(player)
            return
        }

        val currentMomentum = playerMomentum[player]?.value ?: 0
        val newMomentum = (currentMomentum - 1).coerceAtLeast(0)

        playerMomentum[player] = Momentum(newMomentum)
        updateMomentum(player)
    }


    fun startMomentumDecay() {
        Bukkit.getScheduler().runTaskTimer(plugin, Runnable {
            for (player in playerMomentum.keys.toList()) {
                decreaseMomentum(player)
            }
        }, 0L, 20L) // Every second
    }

    fun resetMomentum(player: Player) {
        playerMomentum.remove(player)

        player.spigot().sendMessage(
            ChatMessageType.ACTION_BAR,
            TextComponent(" ")
        )
    }
}