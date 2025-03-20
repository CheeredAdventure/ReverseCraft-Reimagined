package org.cheeredadventure.reversecraftreimagined.api;

import com.mojang.logging.LogUtils;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
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
    });
    context.get().setPacketHandled(true);
  }
}
