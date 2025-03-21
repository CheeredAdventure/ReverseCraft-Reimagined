package org.cheeredadventure.reversecraftreimagined.api;

import com.mojang.logging.LogUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import org.slf4j.Logger;

public class RecipeSearcherImpl implements RecipeSearcher {

  private static final Logger log = LogUtils.getLogger();
  private final MinecraftServer server;
  private Map<ItemStack, List<Recipe<?>>> outputRecipeMap;
  private Map<ItemStack, List<Recipe<?>>> ingredientRecipeMap;

  public RecipeSearcherImpl(MinecraftServer server) {
    this.server = server;
    this.outputRecipeMap = new ConcurrentHashMap<>();
    this.ingredientRecipeMap = new ConcurrentHashMap<>();
  }

  @Override
  public void buildIndex() {
    RecipeManager recipeManager = this.server.getRecipeManager();
    Collection<Recipe<?>> recipes = recipeManager.getRecipes();

    for (Recipe<?> recipe : recipes) {
      ItemStack output = recipe.getResultItem(this.server.registryAccess());
      outputRecipeMap.computeIfAbsent(output, k -> new ArrayList<>()).add(recipe);

      recipe.getIngredients().forEach(ingredient -> {
        for (ItemStack itemStack : ingredient.getItems()) {
          ingredientRecipeMap.computeIfAbsent(itemStack, k -> new ArrayList<>()).add(recipe);
        }
      });
    }
  }

  @Override
  public List<Recipe<?>> findRecipesByOutput(ItemStack itemStack) {
    // countermeasures against unlimited proliferation of recipes using damaged items
    if (itemStack.isDamaged()) {
      return new ArrayList<>();
    }
    List<Recipe<?>> result = new ArrayList<>();
    outputRecipeMap.forEach((key, value) -> {
      if (key.is(itemStack.getItem())) {
        result.addAll(value);
      }
    });
    return result;
  }

  @Override
  public Map<ItemStack, List<Recipe<?>>> findRecipesByIngredient(ItemStack ingredientItemStack) {
    // countermeasures against unlimited proliferation of recipes using damaged items
    if (ingredientItemStack.isDamaged()) {
      return new ConcurrentHashMap<>();
    }
    Map<ItemStack, List<Recipe<?>>> result = new ConcurrentHashMap<>();
    ingredientRecipeMap.forEach((key, value) -> {
      if (key.is(ingredientItemStack.getItem())) {
        result.put(key, value);
      }
    });
    return result;
  }
}
