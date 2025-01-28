package org.SakyQ.horseCombatAgain.listeners

import org.SakyQ.horseCombatAgain.HorseCombatAgain
import org.bukkit.Material
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.entity.Player
import org.bukkit.entity.Horse
import org.bukkit.event.EventHandler

class EnvironmentalEffectsListener(private val plugin: HorseCombatAgain) : Listener {

    @EventHandler
    fun onPlayerMove(event: PlayerMoveEvent) {
        val player = event.player
        val horse = player.vehicle as? Horse ?: return
        val terrainType = event.to.block.type
        applyEnvironmentalEffects(horse, terrainType)
    }

    private fun applyEnvironmentalEffects(horse: Horse, terrainType: Material) {
        when (terrainType) {
            Material.SAND -> horse.velocity.multiply(0.8)
            Material.ICE -> horse.velocity.multiply(1.2)
            else -> {}
        }
    }
}
