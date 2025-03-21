package org.cheeredadventure.reversecraftreimagined.api;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.mojang.logging.LogUtils;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import org.slf4j.Logger;

public class RecipeSearcherImpl implements RecipeSearcher {

  private static final Logger log = LogUtils.getLogger();
  private final MinecraftServer server;
  private final Gson gson;
  private Map<ItemStack, List<Recipe<?>>> outputRecipeMap;

  public RecipeSearcherImpl(MinecraftServer server) {
    this.server = server;
    this.outputRecipeMap = new ConcurrentHashMap<>();
    this.gson = new GsonBuilder()
      .registerTypeAdapterFactory(new OptionalTypeAdapterFactory())
      .create();
  }

  @Override
  public void buildIndex() {
    RecipeManager recipeManager = this.server.getRecipeManager();
    Collection<Recipe<?>> recipes = recipeManager.getRecipes();

    for (Recipe<?> recipe : recipes) {
      ItemStack output = recipe.getResultItem(this.server.registryAccess());
      outputRecipeMap.computeIfAbsent(output, k -> new ArrayList<>()).add(recipe);
    }
  }

  @Override
  public List<Recipe<?>> findRecipesByOutput(ItemStack itemStack) {
    if (itemStack.isDamaged()) {
      return new ArrayList<>();
    }
    return outputRecipeMap.entrySet().stream()
      .filter(entry -> entry.getKey().is(itemStack.getItem()))
      .flatMap(entry -> entry.getValue().stream())
      .collect(Collectors.toList());
  }

  @Override
  public CompletableFuture<Void> writeIndexToJson(Path path) {
    return CompletableFuture.runAsync(() -> {
      try {
        String json = gson.toJson(outputRecipeMap, outputRecipeMap.getClass());
        Files.write(path, json.getBytes());
      } catch (IOException e) {
        log.error("Failed to write index to json", e);
      } catch (JsonIOException e) {
        log.error("Json related exception has occurred.", e);
      }
    });
  }

  @Override
  public CompletableFuture<Optional<RecipeSearcher>> readIndexFromJson(Path path) {
    return CompletableFuture.supplyAsync(() -> {
      try {
        String json = new String(Files.readAllBytes(path));
        Type type = new TypeToken<Map<ItemStack, List<Recipe<?>>>>() {
        }.getType();
        outputRecipeMap = gson.fromJson(json, type);
        return Optional.of(this);
      } catch (IOException e) {
        log.error("Failed to read index from json", e);
        return Optional.empty();
      }
    });
  }
}
