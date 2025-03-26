package org.cheeredadventure.reversecraftreimagined.internal;

import com.mojang.logging.LogUtils;
import java.util.function.Consumer;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.cheeredadventure.reversecraftreimagined.ReverseCraftReimagined;
import org.cheeredadventure.reversecraftreimagined.api.BlockInit;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

@Mod.EventBusSubscriber(modid = ReverseCraftReimagined.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

  private static final Logger log = LogUtils.getLogger();

  @SubscribeEvent
  public static void gatherData(GatherDataEvent event) {
    DataGenerator generator = event.getGenerator();

    generator.addProvider(event.includeServer(), new RecipeProvider(generator.getPackOutput()) {
      @Override
      protected void buildRecipes(@NotNull Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, BlockInit.getREVERSE_WORKBENCH().get())
          .pattern(" R ")
          .pattern(" C ")
          .pattern("   ")
          .define('R', Items.REDSTONE)
          .define('C', Items.CRAFTING_TABLE)
          .unlockedBy("has_redstone", has(Items.REDSTONE))
          .save(consumer, ResourceLocation.fromNamespaceAndPath(ReverseCraftReimagined.MODID,
            "reverseworkbench"));
        log.info("Generated recipe for Reverse Workbench");
      }
    });

    generator.addProvider(event.includeServer(),
      new ReverseCraftLangProvider.ReverseCraftLangUS(generator.getPackOutput()));
    generator.addProvider(event.includeClient(),
      new ReverseCraftLangProvider.ReverseCraftLangJP(generator.getPackOutput()));
  }
}
