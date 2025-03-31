package org.cheeredadventure.reversecraftreimagined.api.networking;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.crafting.Ingredient;

@RequiredArgsConstructor
@Getter
public class ReverseRecipePacket {

  private final List<Ingredient> ingredients;
  private final int recipeWidth;
  private final int recipeHeight;

  public static ReverseRecipePacket decode(FriendlyByteBuf buffer) {
    final int ingredientCount = buffer.readInt();
    List<Ingredient> ingredients = new ArrayList<>();
    for (int i = 0; i < ingredientCount; i++) {
      ingredients.add(Ingredient.fromNetwork(buffer));
    }
    return new ReverseRecipePacket(
      ingredients,
      buffer.readInt(), buffer.readInt());
  }

  public void encode(FriendlyByteBuf buffer) {
    buffer.writeInt(this.ingredients.size());
    this.ingredients.forEach(i -> i.toNetwork(buffer));
    buffer.writeInt(recipeWidth);
    buffer.writeInt(recipeHeight);
  }
}
