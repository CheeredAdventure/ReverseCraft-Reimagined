package org.cheeredadventure.reversecraftreimagined.api.networking;

import com.mojang.logging.LogUtils;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;
import org.cheeredadventure.reversecraftreimagined.api.ReverseRecipeSearcher;
import org.cheeredadventure.reversecraftreimagined.blocks.ReverseWorkbenchBlockEntity;
import org.cheeredadventure.reversecraftreimagined.internal.functionality.ReverseRecipeSearcherImpl;
import org.slf4j.Logger;

public class ReverseCraftPacketHandler implements IPacketHandler<ReverseCraftPacket> {

  private static final Logger log = LogUtils.getLogger();
  private static final ReverseRecipeSearcher<CraftingRecipe> recipeSearcher = new ReverseRecipeSearcherImpl();

  @Override
  public void encode(ReverseCraftPacket msg, FriendlyByteBuf buffer) {
    msg.encode(buffer);
  }

  @Override
  public ReverseCraftPacket decode(FriendlyByteBuf buffer) {
    return ReverseCraftPacket.decode(buffer);
  }

  @Override
  public void handle(ReverseCraftPacket msg, Supplier<NetworkEvent.Context> context) {
    context.get().enqueueWork(() -> {
      ServerPlayer player = context.get().getSender();
      BlockPos blockPos = msg.getBlockPos();
      ItemStack targetItem = msg.getMayReverseCrafting();

      log.debug("Received reverse craft packet from player: {}", player);
      log.debug("itemStack: {}", targetItem);
      log.debug("blockPos: {}", blockPos);

      BlockEntity entity = player.level().getBlockEntity(blockPos);
      if (!(entity instanceof ReverseWorkbenchBlockEntity reverseWorkbenchBlockEntity)) {
        log.warn("our ReverseWorkbenchBlockEntity is missing!");
        return;
      }

      List<CraftingRecipe> recipes = recipeSearcher.searchRecipe(targetItem, blockPos, player);

      if (recipes.isEmpty()) {
        log.info("No recipe found for target item: {}", targetItem);
        return;
      }

      // TODO: implement a way to handle multiple recipes
      CraftingRecipe recipe = recipes.get(0);
      List<Ingredient> ingredients = recipe.getIngredients();
      int recipeWidth, recipeHeight;
      if (recipe instanceof ShapedRecipe shaped) {
        recipeWidth = shaped.getRecipeWidth();
        recipeHeight = shaped.getRecipeHeight();
      } else if (recipe instanceof ShapelessRecipe shapeless) {
        final int recipeMaxItemCount = shapeless.getIngredients().size();
        // Configure the number of material items, such as 3x1 + 2, 3x2 + 1, 3x3, as the width and height of the recipe grid.
        recipeWidth = Math.min(recipeMaxItemCount, 3);
        recipeHeight = recipeMaxItemCount > 3 ? recipeMaxItemCount / 3 : 1;
      } else {
        log.error("Unknown recipe type: {}", recipe.getClass().getName());
        return;
      }
      ReverseRecipePacket packet = new ReverseRecipePacket(ingredients, recipeWidth, recipeHeight);
      PacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), packet);

      log.info("Found {} recipes for target item: {}", recipes.size(), targetItem);
      recipes.forEach(r -> log.debug("Found ingredients: {}", r.getIngredients()));
    });
    context.get().setPacketHandled(true);
  }
}
