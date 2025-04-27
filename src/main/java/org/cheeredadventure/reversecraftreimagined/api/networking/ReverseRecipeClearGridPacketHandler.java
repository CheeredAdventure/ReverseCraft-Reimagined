package org.cheeredadventure.reversecraftreimagined.api.networking;

import com.mojang.logging.LogUtils;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent.Context;
import org.cheeredadventure.reversecraftreimagined.blocks.ReverseWorkbenchBlockEntity;
import org.slf4j.Logger;

public class ReverseRecipeClearGridPacketHandler implements
  IPacketHandler<ReverseRecipeClearGridPacket> {

  private static final Logger log = LogUtils.getLogger();

  @Override
  public void encode(ReverseRecipeClearGridPacket msg, FriendlyByteBuf buffer) {
    msg.encode(buffer);
  }

  @Override
  public ReverseRecipeClearGridPacket decode(FriendlyByteBuf buffer) {
    return ReverseRecipeClearGridPacket.decode(buffer);
  }

  @Override
  public void handle(ReverseRecipeClearGridPacket msg, Supplier<Context> ctx) {
    ctx.get().enqueueWork(() -> {
      ServerPlayer player = ctx.get().getSender();
      BlockPos blockPos = msg.getBlockPos();
      BlockEntity entity = player.level().getBlockEntity(blockPos);
      if (entity instanceof ReverseWorkbenchBlockEntity blockEntity) {
        blockEntity.clearGridInventory();
        blockEntity.setChanged();
      } else {
        log.error("Our BlockEntity is missing!");
      }
    });
    ctx.get().setPacketHandled(true);
  }
}
