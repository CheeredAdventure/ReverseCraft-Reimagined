package org.cheeredadventure.reversecraftreimagined.internal;

import com.mojang.logging.LogUtils;
import java.util.function.Consumer;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import org.cheeredadventure.reversecraftreimagined.ReverseCraftReimagined;
import org.cheeredadventure.reversecraftreimagined.api.BlockInit;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

public class ReverseCraftRecipeProvider extends RecipeProvider {

  private static final Logger log = LogUtils.getLogger();

  public ReverseCraftRecipeProvider(PackOutput packOutput) {
    super(packOutput);
  }

  @Override
  protected void buildRecipes(@NotNull Consumer<FinishedRecipe> consumer) {
    ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, BlockInit.getREVERSE_WORKBENCH().get())
      .pattern(" R ")
      .pattern(" C ")
      .pattern("   ")
      .define('R', Items.REDSTONE)
      .define('C', Items.CRAFTING_TABLE)
      .unlockedBy("has_redstone", has(Items.REDSTONE))
      .save(consumer,
        ResourceLocation.fromNamespaceAndPath(ReverseCraftReimagined.MODID, "reverseworkbench"));
    log.info("ReverseCraftReimagined: Reverse Workbench recipe generated.");
  }
}
