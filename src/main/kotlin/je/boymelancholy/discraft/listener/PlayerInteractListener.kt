package je.boymelancholy.discraft.listener

import je.boymelancholy.discraft.DisCraft
import je.boymelancholy.discraft.Session
import je.boymelancholy.recipeapi.RecipeAnalyzer
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent

class PlayerInteractListener : Listener {

    @EventHandler
    fun onClick(event: PlayerInteractEvent) {
        val player = event.player
        if (!player.isSneaking) return
        val block = event.clickedBlock ?: return
        if (block.type != Material.WORKBENCH) return
        if (event.action != Action.LEFT_CLICK_BLOCK) return
        val item = player.inventory.itemInMainHand ?: return

        if (Session.isValid(player)) return
        Session.validate(player)

        val analyzer = RecipeAnalyzer.get(item)
        if (!analyzer.canDisCraft()) {
            player.sendMessage("素材に変換できません")
            Session.invalidate(player)
            return
        }
        val count = analyzer.getCost()!!
        val reduce = item.clone()
        reduce.amount = count
        player.inventory.removeItem(reduce)
        analyzer.getIngredients().forEach {
            if (it != null) {
                if (it.durability > 15) it.durability = 0
                player.inventory.addItem(it)
            }
        }
        Bukkit.getScheduler().runTaskLater(
            DisCraft.instance, fun() {
                Session.invalidate(player)
            }, 10
        )
    }
}