package org.SakyQ.horseCombatAgain.utils

import org.SakyQ.horseCombatAgain.models.Lance
import org.bukkit.inventory.ItemStack

object DamageUtils {
    fun calculateDamage(momentum: Int, lance: ItemStack): Double {
        val baseDamage = 10.0
        val criticalMultiplier = if (momentum == 100) 2.0 else 1.0
        return baseDamage * criticalMultiplier
    }
}
