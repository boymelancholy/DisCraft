package je.boymelancholy.recipeapi

import org.bukkit.Bukkit
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.Recipe
import org.bukkit.inventory.ShapedRecipe


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
        ingredients = recipe?.ingredientMap?.map{ it.value } ?: mutableListOf()
        if (ingredients.isNotEmpty()) {
            val newIngredients = mutableListOf<ItemStack>()
            ingredients.forEach loop@ { item ->
                if (item !is ItemStack) return@loop
                if (!newIngredients.contains(item)) {
                    newIngredients.add(item)
                }
            }
            newIngredients.forEach { item ->
                if (ingredients.contains(item)) {
                    val count = ingredients.count { it?.type == item.type }
                    item.amount = count
                }
            }
            ingredients = newIngredients
        }
    }

    fun getIngredients(): Collection<ItemStack?> {
        return ingredients
    }

    fun getCost(): Int? {
        return recipe?.result?.amount
    }

    fun canDisCraft(): Boolean {
        if (recipes.isEmpty()) return false
        if (recipe?.result?.type != item.type) return false
        if (item.amount < recipe?.result?.amount!!) return false
        return true
    }

    companion object {
        fun get(item: ItemStack): RecipeAnalyzer {
            return RecipeAnalyzer(item)
        }
    }
}