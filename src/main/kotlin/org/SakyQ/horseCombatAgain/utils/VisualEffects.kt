package org.SakyQ.horseCombatAgain.utils

import org.bukkit.Color
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.Sound

object VisualEffects {
    fun playBloodSplatterEffect(location: Location) {
        val world = location.world ?: return

        // Intense blood splatter
        val bloodColor = Particle.DustOptions(Color.fromRGB(139, 0, 0), 2.0f)
        world.spawnParticle(Particle.DUST, location, 50, 0.5, 0.5, 0.5, bloodColor)

        // Additional impact particles
        world.spawnParticle(Particle.EXPLOSION, location, 3)
    }

    fun playImpactEffects(location: Location, momentum: Int) {
        val world = location.world ?: return

        if (momentum == 100) {
            // Devastating hit effects
            world.playSound(location, Sound.ENTITY_SLIME_SQUISH, 1.0f, 0.5f)
            world.spawnParticle(Particle.EXPLOSION, location, 2)
            playBloodSplatterEffect(location)
        }
    }
}