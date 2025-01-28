package org.SakyQ.horseCombatAgain.listeners

import org.SakyQ.horseCombatAgain.HorseCombatAgain
import org.SakyQ.horseCombatAgain.models.Lance
import org.SakyQ.horseCombatAgain.utils.VisualEffects
import org.bukkit.Bukkit
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.entity.Entity
import org.bukkit.entity.Horse
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.vehicle.VehicleEntityCollisionEvent
import kotlin.math.abs

class LanceHitListener(private val plugin: HorseCombatAgain) : Listener {

    @EventHandler
    fun onEntityCollision(event: VehicleEntityCollisionEvent) {
        val horse = event.vehicle as? Horse ?: return
        val rider = horse.passengers.firstOrNull() as? Player ?: return
        val lance = Lance.getLance(rider) ?: return
        val target = event.entity as? LivingEntity ?: return

        val momentum = plugin.momentumBar.playerMomentum[rider]?.value ?: 0
        val ramDamage = calculateRamDamage(momentum)

        target.damage(ramDamage, rider)

        target.world.playSound(target.location, Sound.ENTITY_PLAYER_HURT, 1.0f, 1.0f)
        target.world.spawnParticle(Particle.DAMAGE_INDICATOR, target.location, 10)

        plugin.momentumBar.resetMomentum(rider)
    }

    @EventHandler
    fun onLanceHit(event: EntityDamageByEntityEvent) {
        val attacker = event.damager as? Player ?: return
        val target = event.entity
        val lance = Lance.getLance(attacker) ?: return
        val horse = attacker.vehicle

        if (horse is Horse) {
            val momentum = plugin.momentumBar.playerMomentum[attacker]?.value ?: 0
            val chargeDamage = calculateChargeDamage(momentum, attacker, target)

            event.damage = chargeDamage


            if (momentum > 50) {
                attacker.world.playSound(attacker.location, Sound.ENTITY_GENERIC_EXPLODE, 1.0f, 0.5f)
                target.world.spawnParticle(Particle.DAMAGE_INDICATOR, target.location, 20)
            }

            plugin.momentumBar.resetMomentum(attacker)
        } else {

            event.damage = calculateGroundAttackDamage()
        }
    }

    private fun calculateChargeDamage(momentum: Int, attacker: Player, target: Entity): Double {

        val baseDamage = momentum / 10.0 // 0-10 damage based on momentum

        val fullMomentumBonus = if (momentum == 100) 5.0 else 0.0


        val behindBonus = if (isChargingFromBehind(attacker, target)) 2.0 else 0.0

        return baseDamage + fullMomentumBonus + behindBonus
    }

    private fun calculateGroundAttackDamage(): Double {

        return 2.0
    }

    private fun calculateRamDamage(momentum: Int): Double {

        return momentum / 10.0
    }

    private fun isChargingFromBehind(attacker: Player, target: Entity): Boolean {
        if (target !is LivingEntity) return false

        val attackerYaw = attacker.location.yaw
        val targetYaw = target.location.yaw
        val angleDifference = abs(attackerYaw - targetYaw)

        return angleDifference > 135 && angleDifference < 225
    }
}