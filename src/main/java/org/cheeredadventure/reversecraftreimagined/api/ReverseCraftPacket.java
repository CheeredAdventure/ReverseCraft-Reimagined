package org.cheeredadventure.reversecraftreimagined.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;

@RequiredArgsConstructor
@Getter
public class ReverseCraftPacket {

  private final ItemStack mayReverseCrafting;
  private final BlockPos blockPos;

  public static ReverseCraftPacket decode(FriendlyByteBuf buffer) {
    return new ReverseCraftPacket(buffer.readItem(), buffer.readBlockPos());
  }

  public void encode(FriendlyByteBuf buffer) {
    buffer.writeItem(mayReverseCrafting);
    buffer.writeBlockPos(blockPos);
  }

}
