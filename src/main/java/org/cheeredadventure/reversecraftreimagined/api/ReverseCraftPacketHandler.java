package org.cheeredadventure.reversecraftreimagined.api;

import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

public class ReverseCraftPacketHandler implements IPacketHandler<ReverseCraftPacket> {

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
      // TODO: Handle reverse crafting
    });
    context.get().setPacketHandled(true);
  }
}
