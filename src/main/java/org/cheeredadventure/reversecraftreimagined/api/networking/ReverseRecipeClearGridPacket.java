package org.cheeredadventure.reversecraftreimagined.api.networking;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;

@RequiredArgsConstructor
@Getter
public class ReverseRecipeClearGridPacket {

  private final BlockPos blockPos;

  public static ReverseRecipeClearGridPacket decode(FriendlyByteBuf buffer) {
    return new ReverseRecipeClearGridPacket(buffer.readBlockPos());
  }

  public void encode(FriendlyByteBuf buffer) {
    buffer.writeBlockPos(blockPos);
  }
}
