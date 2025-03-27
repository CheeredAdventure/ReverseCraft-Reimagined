package org.cheeredadventure.reversecraftreimagined.internal.functionality;

import com.mojang.logging.LogUtils;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import lombok.NoArgsConstructor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.cheeredadventure.reversecraftreimagined.api.ReverseRecipeSearcher;
import org.slf4j.Logger;

@NoArgsConstructor
public class ReverseRecipeSearcherImpl implements ReverseRecipeSearcher<CraftingRecipe> {

  private static final Logger log = LogUtils.getLogger();

  public List<CraftingRecipe> searchRecipe(final ItemStack resultItem,
    final BlockPos reverseWorkbenchBlockPos, final Player invokedPlayer) {
    if (resultItem.is(Items.AIR)) {
      return Collections.emptyList();
    }

    try (Level level = invokedPlayer.level()) {
      RecipeManager recipeManager = level.getRecipeManager();
      List<CraftingRecipe> recipes = recipeManager.getAllRecipesFor(RecipeType.CRAFTING);
      return recipes.stream()
        .filter(recipe -> recipe.getResultItem(level.registryAccess()).is(resultItem.getItem()))
        .toList();
    } catch (IOException e) {
      log.error("Error while searching recipes for reversal of item: {}", resultItem, e);
    }
    return Collections.emptyList();
  }
}
