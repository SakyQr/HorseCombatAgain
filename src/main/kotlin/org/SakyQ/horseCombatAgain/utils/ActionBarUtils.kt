package org.SakyQ.horseCombatAgain.utils

import net.md_5.bungee.api.ChatMessageType
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.entity.Player

object ActionBarUtils {
    fun updateActionBar(player: Player, momentum: Int) {
        val bar = buildString {
            val filledBars = momentum / 10
            for (i in 1..10) append(if (i <= filledBars) "§c|" else "§7|")
        }
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent("Momentum: $bar §7$momentum%"))
    }
}
