package org.cheeredadventure.reversecraftreimagined.api;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;

public interface RecipeSearcher {

  /**
   * builds an index of recipes for searching
   */
  void buildIndex();

  /**
   * finds recipes that have the given itemStack as an output
   *
   * @param itemStack the itemStack to search for
   * @return a {@link List} of {@link Recipe}s that have the given itemStack as an output
   */
  List<Recipe<?>> findRecipesByOutput(ItemStack itemStack);

  CompletableFuture<Optional<RecipeSearcher>> readIndexFromJson(Path path);

  CompletableFuture<Void> writeIndexToJson(Path path);
}
