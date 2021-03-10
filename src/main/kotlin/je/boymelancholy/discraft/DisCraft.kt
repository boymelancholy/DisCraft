package je.boymelancholy.discraft

import je.boymelancholy.discraft.item.KnockbackStick
import je.boymelancholy.discraft.listener.PlayerInteractListener
import je.boymelancholy.recipeapi.RecipeThinker
import org.bukkit.plugin.java.JavaPlugin


class DisCraft : JavaPlugin() {

    init { instance = this }

    override fun onEnable() {
        val recipes = listOf(
            KnockbackStick()
        )
        RecipeThinker.register(recipes)

        server.pluginManager.registerEvents(
            PlayerInteractListener(), this
        )
    }

    companion object {
        lateinit var instance: DisCraft
    }
}