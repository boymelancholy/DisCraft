package je.boymelancholy.discraft

import org.bukkit.entity.Player
import java.util.UUID

class Session {
    companion object {
        private val validators: MutableMap<UUID, Player> = mutableMapOf()

        fun validate(player: Player) {
            if (isValid(player)) return
            validators[player.uniqueId] = player
        }

        fun invalidate(player: Player) {
            if (!isValid(player)) return
            validators.remove(player.uniqueId)
        }

        fun isValid(player: Player): Boolean {
            return validators.contains(player.uniqueId)
        }
    }
}