package org.cheeredadventure.reversecraftreimagined.api.networking;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;

@RequiredArgsConstructor
@Getter
public class ReversingWorkPacket {

  private final List<ItemStack> ingredients;
  private final ItemStack resultSlotItemStack;
  private final BlockPos blockPos;

  public static ReversingWorkPacket decode(FriendlyByteBuf buffer) {
    final List<ItemStack> ingredients = buffer.readList(FriendlyByteBuf::readItem);
    final ItemStack resultSlotItemStack = buffer.readItem();
    final BlockPos blockPos = buffer.readBlockPos();
    return new ReversingWorkPacket(ingredients, resultSlotItemStack, blockPos);
  }

  public void encode(FriendlyByteBuf buffer) {
    buffer.writeCollection(this.ingredients, FriendlyByteBuf::writeItem);
    buffer.writeItem(this.resultSlotItemStack);
    buffer.writeBlockPos(this.blockPos);
  }
}
