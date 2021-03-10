package je.boymelancholy.discraft.item

import je.boymelancholy.recipeapi.RecipeBlueprint
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment

class KnockbackStick : RecipeBlueprint {

    /**
     * Item display name
     */
    override fun getDisplayName(): String {
        return this::class.java.simpleName
    }

    /**
     * Item lore
     */
    override fun getLore(): List<String> {
        return listOf(
            "Let's blow off guys!!"
        )
    }

    /**
     * Item enchantments
     * @return Enchantment instance + level
     */
    override fun getEnchantment(): MutableList<MutableMap<Enchantment, Int>> {
        return mutableListOf(
            mutableMapOf(Enchantment.KNOCKBACK to 4)
        )
    }

    /**
     * Recipe
     * List<Material>
     *     1, 2, 3
     *     4, 5, 6
     *     7, 8, 9
     */
    override fun getRecipe(): Collection<Material> {
        return listOf(
            Material.SLIME_BALL, Material.STICK, Material.AIR,
            Material.AIR, Material.AIR, Material.AIR,
            Material.AIR, Material.AIR, Material.AIR
        )
    }

    /**
     * Result item as Material
     */
    override fun getResult(): Material {
        return Material.STICK
    }

    /**
     * Result item count 1-64
     */
    override fun getCount(): Int {
        return 1
    }

}