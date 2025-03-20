package org.cheeredadventure.reversecraftreimagined.api;

import com.mojang.logging.LogUtils;
import java.util.Collection;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.crafting.Recipe;
import org.slf4j.Logger;

public class RecipeLoader {

  private static final Logger log = LogUtils.getLogger();
  private final MinecraftServer server;

  public RecipeLoader(MinecraftServer server) {
    this.server = server;
  }

  public Collection<Recipe<?>> loadRecipes() {
    log.info("Loading recipes...");
    return server.getRecipeManager().getRecipes();
  }
}
