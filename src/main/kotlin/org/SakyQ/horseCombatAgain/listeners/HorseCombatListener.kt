package org.SakyQ.horseCombatAgain.listeners

import org.SakyQ.horseCombatAgain.HorseCombatAgain
import org.bukkit.Material
import org.bukkit.entity.Horse
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.inventory.ItemStack

class HorseCombatListener(private val plugin: HorseCombatAgain) : Listener {

    private val momentum = mutableMapOf<Player, Int>()
    private val MAX_MOMENTUM = 100
    private val CUSTOM_SPEAR_NAME = "§6Lance"
    private val CUSTOM_SPEAR_LORE = "§7A lance for horse combat."
    private val FULL_MOMENTUM_DAMAGE = 1000.0

    @EventHandler
    fun onPlayerMove(event: PlayerMoveEvent) {
        val player = event.player
        val itemInHand = player.inventory.itemInMainHand
        val currentHorse = player.vehicle as? Horse


        if (currentHorse != null && itemInHand.type == Material.STICK && isCustomSpear(itemInHand)) {
            val currentMomentum = momentum.getOrDefault(player, 0)
            momentum[player] = (currentMomentum + 1).coerceAtMost(MAX_MOMENTUM)
            updateMomentumBar(player)
        }
    }

    @EventHandler
    fun onPlayerAttack(event: EntityDamageByEntityEvent) {
        val attacker = event.damager as? Player
        val target = event.entity as? Player ?: return
        val itemInHand = attacker?.inventory?.itemInMainHand


        if (attacker != null && itemInHand?.type == Material.STICK && isCustomSpear(itemInHand)) {
            val currentMomentum = momentum.getOrDefault(attacker, 0)
            if (currentMomentum == MAX_MOMENTUM) {

                event.damage = FULL_MOMENTUM_DAMAGE
                momentum[attacker] = 0
            }
        }
    }

    private fun updateMomentumBar(player: Player) {
        val currentMomentum = momentum.getOrDefault(player, 0)
        player.sendActionBar("Momentum: $currentMomentum/$MAX_MOMENTUM")
    }


    private fun isCustomSpear(item: ItemStack): Boolean {
        val meta = item.itemMeta
        return meta != null && meta.hasDisplayName() && meta.displayName == CUSTOM_SPEAR_NAME &&
                meta.hasLore() && meta.lore?.contains(CUSTOM_SPEAR_LORE) == true
    }
}
