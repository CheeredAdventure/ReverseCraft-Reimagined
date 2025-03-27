package org.cheeredadventure.reversecraftreimagined.api;

import com.mojang.logging.LogUtils;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraftforge.network.NetworkEvent;
import org.cheeredadventure.reversecraftreimagined.internal.functionality.ReverseRecipeSearcherImpl;
import org.slf4j.Logger;

public class ReverseCraftPacketHandler implements IPacketHandler<ReverseCraftPacket> {

  private static final Logger log = LogUtils.getLogger();

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
      log.debug("Received reverse craft packet from player {}", player);
      log.debug("itemStack: {}", msg.getMayReverseCrafting());
      log.debug("blockPos: {}", msg.getBlockPos());
      // TODO: Handle reverse crafting
      ReverseRecipeSearcher<CraftingRecipe> recipeSearcher = new ReverseRecipeSearcherImpl();
      List<CraftingRecipe> recipes = recipeSearcher.searchRecipe(msg.getMayReverseCrafting(),
        msg.getBlockPos(), player);
      log.info("Found {} recipes for item: {}", recipes.size(), msg.getMayReverseCrafting());
      recipes.forEach(recipe -> log.debug("Found: ingredients: {}", recipe.getIngredients()));
    });
    context.get().setPacketHandled(true);
  }
}
