package org.SakyQ.horseCombatAgain

import org.SakyQ.horseCombatAgain.utils.MomentumBar
import org.bukkit.plugin.java.JavaPlugin

class HorseCombatAgain : JavaPlugin() {

    // Add this property to initialize MomentumBar
    lateinit var momentumBar: MomentumBar

    override fun onEnable() {
        // Initialize MomentumBar
        momentumBar = MomentumBar(this)

        // Load commands, managers, and listeners
        loadCommands()
        loadManagers()
        loadListeners()

        // Optional: Log enable message
        logger.info("HorseCombatAgain plugin has been enabled!")
    }

    override fun onDisable() {
        // Optional: Log disable message
        logger.info("HorseCombatAgain plugin is shutting down!")
    }

    // Load commands function
    private fun loadCommands() {
        val packageName = "org.SakyQ.horseCombatAgain.commands"
        val reflections = org.reflections.Reflections(packageName)
        val commandClasses = reflections.getSubTypesOf(org.bukkit.command.CommandExecutor::class.java)

        for (commandClass in commandClasses) {
            try {
                val commandInstance = commandClass.getDeclaredConstructor(HorseCombatAgain::class.java).newInstance(this)
                val commandName = commandClass.simpleName.replace("Command", "").lowercase()
                getCommand(commandName)?.setExecutor(commandInstance)
                logger.info("Registered command: $commandName")
            } catch (e: Exception) {
                logger.warning("Failed to register command: ${commandClass.simpleName}")
                e.printStackTrace()
            }
        }
    }

    // Load managers function
    private fun loadManagers() {
        // Optional: Add manager loading logic if needed
        logger.info("Managers loaded")
    }

    // Load listeners function
    private fun loadListeners() {
        val packageName = "org.SakyQ.horseCombatAgain.listeners"
        val reflections = org.reflections.Reflections(packageName)
        val listenerClasses = reflections.getSubTypesOf(org.bukkit.event.Listener::class.java)
        momentumBar = MomentumBar(this)
        momentumBar.startMomentumDecay() // Start momentum decay

        for (listenerClass in listenerClasses) {
            try {
                val listenerConstructor = listenerClass.getDeclaredConstructor(HorseCombatAgain::class.java)
                val listenerInstance = listenerConstructor.newInstance(this)
                server.pluginManager.registerEvents(listenerInstance, this)
                logger.info("Registered listener: ${listenerClass.simpleName}")
            } catch (e: Exception) {
                logger.warning("Failed to register listener: ${listenerClass.simpleName}")
                e.printStackTrace()
            }
        }
    }
}