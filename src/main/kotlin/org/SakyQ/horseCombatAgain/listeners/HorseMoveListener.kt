package org.SakyQ.horseCombatAgain.listeners

import org.SakyQ.horseCombatAgain.HorseCombatAgain
import org.SakyQ.horseCombatAgain.models.Momentum
import org.bukkit.Bukkit
import org.bukkit.entity.Horse
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.event.vehicle.VehicleExitEvent
import org.bukkit.event.vehicle.VehicleMoveEvent
import org.bukkit.util.Vector
import kotlin.math.abs

class HorseMoveListener(private val plugin: HorseCombatAgain) : Listener {

    @EventHandler
    fun onPlayerMove(event: PlayerMoveEvent) {
        val player = event.player
        val vehicle = player.vehicle


        if (vehicle is Horse) {

            val yawChange = Math.abs(event.from.yaw - event.to.yaw)


            val currentMomentum = plugin.momentumBar.playerMomentum[player]?.value ?: 0


            val newMomentum = if (isMovingForward(event)) {
                (currentMomentum + 1).coerceAtMost(100)
            } else {
                currentMomentum
            }


            val finalMomentum = when {
                yawChange >= 90 -> 0
                yawChange > 45 -> (newMomentum - 10).coerceAtLeast(0)
                yawChange > 30 -> (newMomentum / 2).coerceAtLeast(0)
                yawChange > 15 -> (newMomentum * 0.75).toInt().coerceAtLeast(0)
                else -> newMomentum
            }


            plugin.momentumBar.playerMomentum[player] = Momentum(finalMomentum)

            plugin.momentumBar.updateMomentum(player)
        }
    }


    private fun isMovingForward(event: PlayerMoveEvent): Boolean {
        val from = event.from
        val to = event.to


        if (from.distance(to) < 0.1) return false


        val forwardVector = Vector(
            Math.cos(Math.toRadians(from.yaw.toDouble() + 90.0)),
            0.0,
            Math.sin(Math.toRadians(from.yaw.toDouble() + 90.0))
        ).normalize()


        val movementVector = to.toVector().subtract(from.toVector()).normalize()


        val angle = Math.toDegrees(forwardVector.angle(movementVector).toDouble())


        return angle < 45.0
    }
}