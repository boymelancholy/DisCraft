package je.boymelancholy.recipeapi

import org.bukkit.Material
import org.bukkit.enchantments.Enchantment

interface RecipeBlueprint {

    /**
     * Item display name
     */
    fun getDisplayName(): String

    /**
     * Item lore
     */
    fun getLore(): List<String>

    /**
     * Item enchantments
     * @return Enchantment instance + level
     */
    fun getEnchantment(): MutableList<MutableMap<Enchantment, Int>>

    /**
     * Recipe
     * List<Material>
     *     1, 2, 3
     *     4, 5, 6
     *     7, 8, 9
     */
    fun getRecipe(): Collection<Material>

    /**
     * Result item as Material
     */
    fun getResult(): Material

    /**
     * Result item count 1-64
     */
    fun getCount(): Int
}