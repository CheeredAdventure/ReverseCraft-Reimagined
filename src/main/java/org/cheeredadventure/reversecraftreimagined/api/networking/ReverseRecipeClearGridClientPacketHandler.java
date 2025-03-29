package org.cheeredadventure.reversecraftreimagined.api.networking;

import com.mojang.logging.LogUtils;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent.Context;
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

  private static void unsafeOperationOnClientSide() {
    ReverseWorkbenchBlockMenu.clearDummyItems();
  }

  @Override
  public void handle(ReverseRecipeClearGridClientPacket msg, Supplier<Context> ctx) {
    ctx.get().enqueueWork(() -> {
      DistExecutor.unsafeRunWhenOn(Dist.CLIENT,
        () -> ReverseRecipeClearGridClientPacketHandler::unsafeOperationOnClientSide);
    });
    ctx.get().setPacketHandled(true);
  }
}
