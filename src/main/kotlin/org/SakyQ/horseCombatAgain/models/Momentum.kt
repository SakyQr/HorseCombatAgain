package org.SakyQ.horseCombatAgain.models

import org.bukkit.entity.Horse
import kotlin.math.min

data class Momentum(var value: Int) {
    companion object {
        fun calculate(horse: Horse): Momentum {
            val speed = horse.velocity.length()
            val maxSpeed = getMaxHorseSpeed(horse)

            // More nuanced momentum calculation
            val momentumValue = when {
                speed >= maxSpeed * 0.9 -> 100 // Full speed
                speed >= maxSpeed * 0.7 -> 75  // High speed
                speed >= maxSpeed * 0.5 -> 50  // Medium speed
                speed >= maxSpeed * 0.3 -> 25  // Low speed
                else -> 0 // Stationary
            }

            return Momentum(momentumValue)
        }

        private fun getMaxHorseSpeed(horse: Horse): Double {
            return when (horse.variant) {
                Horse.Variant.HORSE -> 10.0
                Horse.Variant.DONKEY -> 8.0
                Horse.Variant.MULE -> 9.0
                else -> 10.0
            }
        }
    }
}