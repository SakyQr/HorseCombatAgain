package org.SakyQ.horseCombatAgain.models

import org.bukkit.entity.Horse

data class HorseStats(var stamina: Int, var level: Int) {

    companion object {
        private val horseStats = mutableMapOf<Horse, HorseStats>()

        fun getStats(horse: Horse): HorseStats {
            return horseStats.getOrPut(horse) { HorseStats(100, 1) }
        }
    }
}
