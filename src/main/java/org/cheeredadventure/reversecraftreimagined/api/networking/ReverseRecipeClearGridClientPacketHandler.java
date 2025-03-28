package org.cheeredadventure.reversecraftreimagined.api.networking;

import com.mojang.logging.LogUtils;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent.Context;
import org.cheeredadventure.reversecraftreimagined.blocks.ReverseWorkbenchBlockEntity;
import org.cheeredadventure.reversecraftreimagined.gui.ReverseWorkbenchBlockMenu;
import org.slf4j.Logger;

public class ReverseRecipeClearGridClientPacketHandler implements
  IPacketHandler<ReverseRecipeClearGridClientPacket> {

  private static final Logger log = LogUtils.getLogger();

  @Override
  public void encode(ReverseRecipeClearGridClientPacket msg, FriendlyByteBuf buffer) {
    msg.encode(buffer);
  }

  @Override
  public ReverseRecipeClearGridClientPacket decode(FriendlyByteBuf buffer) {
    return ReverseRecipeClearGridClientPacket.decode(buffer);
  }

  @Override
  public void handle(ReverseRecipeClearGridClientPacket msg, Supplier<Context> ctx) {
    ctx.get().enqueueWork(() -> {
      ServerPlayer player = ctx.get().getSender();
      BlockPos blockPos = msg.getBlockPos();
      BlockEntity blockEntity = player.level().getBlockEntity(blockPos);
      if (!(blockEntity instanceof ReverseWorkbenchBlockEntity)) {
        log.warn("our ReverseWorkbenchBlockEntity is missing!");
        return;
      }

      DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> ReverseWorkbenchBlockMenu::clearDummyItems);
    });
    ctx.get().setPacketHandled(true);
  }
}
