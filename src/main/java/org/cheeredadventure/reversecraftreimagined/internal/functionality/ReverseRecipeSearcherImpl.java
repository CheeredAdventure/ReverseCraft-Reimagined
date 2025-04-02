package org.cheeredadventure.reversecraftreimagined.internal.functionality;

import com.mojang.logging.LogUtils;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import lombok.NoArgsConstructor;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
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
    MinecraftServer server = Objects.requireNonNull(invokedPlayer.getServer());

    RecipeManager recipeManager = server.getRecipeManager();
    List<CraftingRecipe> recipes = recipeManager.getAllRecipesFor(RecipeType.CRAFTING);
    return recipes.stream()
      .filter(recipe -> recipe.getResultItem(server.registryAccess())
        .is(resultItem.getItem()))
      .filter(recipe -> recipe.getResultItem(server.registryAccess())
        .getCount() == resultItem.getCount())
      .toList();
  }
}
