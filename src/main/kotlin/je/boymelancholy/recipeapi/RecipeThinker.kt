package je.boymelancholy.recipeapi

import je.boymelancholy.discraft.DisCraft
import org.bukkit.Material
import org.bukkit.inventory.ShapedRecipe
import org.bukkit.Bukkit
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack

/**
 * レシピの操作を主にするクラス
 * Class for mainly manipulates recipes.
 *
 * @author boymelancholy
 */
class RecipeThinker {
    companion object {

        private val chars: List<Char> = ('A'..'I').toList()

        /**
         * レシピの登録
         * Register new recipe
         */
        fun register(recipes: Collection<RecipeBlueprint>) {
            recipes.forEach {
                if (it.getCount() in 1..64) {
                    val data = getRecipeData(it)
                    val itemsToAdd = data.itemMap

                    val item = ItemStack(it.getResult())
                    val key = NamespacedKey(DisCraft.instance, it.getDisplayName())
                    val meta = item.itemMeta
                    meta.displayName = it.getDisplayName()
                    meta.lore = it.getLore()
                    item.itemMeta = meta
                    if (it.getEnchantment().isNotEmpty()) {
                        it.getEnchantment().forEach { enchantments ->
                            enchantments.forEach { (t, u) ->
                                item.addUnsafeEnchantment(t, u)
                            }
                        }
                    }
                    val recipe = ShapedRecipe(key, item)
                    recipe.shape(
                        data.upperShape,
                        data.middleShape,
                        data.lowerShape
                    )
                    itemsToAdd.forEach { (char, material) ->
                        recipe.setIngredient(char, material)
                    }
                    Bukkit.addRecipe(recipe)
                }
            }
        }

        /**
         * めにめにぷろせす
         * Lots of process.
         */
        private fun getRecipeData(recipeBlueprint: RecipeBlueprint): RecipeData {
            val upper = mutableListOf<Char>()
            val middle = mutableListOf<Char>()
            val lower = mutableListOf<Char>()
            val itemMap: MutableMap<Char, Material> = mutableMapOf()
            val recipe = recipeBlueprint.getRecipe() as List
            val ingredients = listOf<Material>()
            var j = 0
            for (i in recipe.indices) {
                val target = recipe[i]
                if (!ingredients.contains(target) && target != Material.AIR) {
                    j++
                }
                val literal = if (target != Material.AIR) chars[j] else ' '
                when (true) {
                    i in 0..2 -> upper.add(literal)
                    i in 3..5 -> middle.add(literal)
                    i in 6..8 -> lower.add(literal)
                }
                itemMap[literal] = target
            }
            return RecipeData(
                itemMap,
                upper.joinToString(""),
                middle.joinToString(""),
                lower.joinToString("")
            )
        }
    }

    /**
     * レシピ追加時に使用しやすいデータ型に落とし込む
     * Putting it into a data type that is easy to use when adding recipes
     */
    data class RecipeData(
        val itemMap: MutableMap<Char, Material>,
        val upperShape: String,
        val middleShape: String,
        val lowerShape: String
    )
}