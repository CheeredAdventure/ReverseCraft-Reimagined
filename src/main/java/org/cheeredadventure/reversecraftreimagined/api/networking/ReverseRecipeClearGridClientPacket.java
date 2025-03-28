package org.cheeredadventure.reversecraftreimagined.api.networking;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;

@RequiredArgsConstructor
@Getter
public class ReverseRecipeClearGridClientPacket {

  private final BlockPos blockPos;

  public static ReverseRecipeClearGridClientPacket decode(FriendlyByteBuf buffer) {
    BlockPos blockPos = buffer.readBlockPos();
    return new ReverseRecipeClearGridClientPacket(blockPos);
  }

  public void encode(FriendlyByteBuf buffer) {
    buffer.writeBlockPos(blockPos);
  }
}
