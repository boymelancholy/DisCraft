package je.boymelancholy.recipeapi

import org.bukkit.Bukkit
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.Recipe
import org.bukkit.inventory.ShapedRecipe

/**
 * レシピの解析をするクラス
 * Class for analyzing recipes.
 *
 * @author boymelancholy
 */
class RecipeAnalyzer(private val item: ItemStack) {

    private var recipe: ShapedRecipe? = null
    private var recipes: Collection<Recipe?> = mutableListOf()
    private var ingredients: Collection<ItemStack?> = mutableListOf()

    init {
        recipes = Bukkit.getRecipesFor(item)
        recipe = if (recipes.isEmpty()) {
            null
        } else {
            recipes.first() as ShapedRecipe
        }
        ingredients = recipe?.ingredientMap?.summarize()!!
    }

    /**
     * 素材のリストを取得
     * Get a list of ingredients
     */
    fun getIngredients(): Collection<ItemStack?> {
        return ingredients
    }

    /**
     * リバートクラフトに必要な要素の数
     * Number of elements required for revert crafting.
     */
    fun getCost(): Int? {
        return recipe?.result?.amount
    }

    /**
     * リバートクラフトが可能かどうかの判断
     * Determining if revert crafting is possible
     */
    fun canDisCraft(): Boolean {
        if (recipes.isEmpty()) return false
        if (recipe?.result?.type != item.type) return false
        if (item.amount < recipe?.result?.amount!!) return false
        return true
    }

    /**
     * ShapeListの状態から、同じアイテムの素材をまとめる
     * Combine the material parts of the same item from the state of ShapeList.
     * e.g.
     *   before = {a:{ItemStack(IRON_INGOT, 1)}, b:{ItemStack(IRON_INGOT, 1)}, c:{ItemStack(STICK, 1)}}
     *   after = {ItemStack(IRON_INGOT, 2), ItemStack(STICK, 1)}
     */
    private fun Map<Char, ItemStack>.summarize(): Collection<ItemStack?> {
        val materials = this.map{ it.value }
        if (materials.isNotEmpty()) {
            val newIngredients = mutableListOf<ItemStack>()
            this.forEach loop@ { item ->
                if (item !is ItemStack) return@loop
                if (!newIngredients.contains(item)) {
                    newIngredients.add(item)
                }
            }
            newIngredients.forEach { item ->
                if (materials.contains(item)) {
                    val count = materials.count { it.type == item.type }
                    item.amount = count
                }
            }
            return newIngredients
        }
        return materials
    }

    companion object {
        fun get(item: ItemStack): RecipeAnalyzer {
            return RecipeAnalyzer(item)
        }
    }
}