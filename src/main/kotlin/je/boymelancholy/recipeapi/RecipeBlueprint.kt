package je.boymelancholy.recipeapi

import org.bukkit.Material
import org.bukkit.enchantments.Enchantment

/**
 * 新規アイテムレシピのインターフェース
 * Interface of new item recipe
 *
 * @author boymelancholy
 */
interface RecipeBlueprint {

    /**
     * アイテムの名前
     * Item display name
     */
    fun getDisplayName(): String

    /**
     * アイテムの説明欄
     * Item lore
     */
    fun getLore(): List<String>

    /**
     * アイテムのエンチャント
     * Item enchantments
     *
     * @return Enchantment instance + level
     */
    fun getEnchantment(): MutableList<MutableMap<Enchantment, Int>>

    /**
     * レシピの設計
     * Recipe
     *
     * List<Material>
     *     1, 2, 3
     *     4, 5, 6
     *     7, 8, 9
     */
    fun getRecipe(): Collection<Material>

    /**
     * 完成品のMaterial
     * Result item as Material
     */
    fun getResult(): Material

    /**
     * 完成品のアイテム数
     * Result item count 1-64
     */
    fun getCount(): Int
}