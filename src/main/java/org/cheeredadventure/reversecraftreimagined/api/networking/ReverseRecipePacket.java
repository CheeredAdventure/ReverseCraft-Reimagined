package org.cheeredadventure.reversecraftreimagined.api.networking;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;

@RequiredArgsConstructor
@Getter
public class ReverseRecipePacket {

  private final List<ItemStack> ingredients;
  private final int recipeWidth;
  private final int recipeHeight;

  public static ReverseRecipePacket decode(FriendlyByteBuf buffer) {
    return new ReverseRecipePacket(buffer.readCollection(ArrayList::new, FriendlyByteBuf::readItem),
      buffer.readInt(), buffer.readInt());
  }

  public void encode(FriendlyByteBuf buffer) {
    buffer.writeCollection(ingredients, FriendlyByteBuf::writeItem);
    buffer.writeInt(recipeWidth);
    buffer.writeInt(recipeHeight);
  }
}
