package org.cheeredadventure.reversecraftreimagined.api.networking;

import com.mojang.logging.LogUtils;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent.Context;
import org.cheeredadventure.reversecraftreimagined.gui.ReverseWorkbenchBlockMenu;
import org.slf4j.Logger;

public class ReverseRecipePacketHandler implements IPacketHandler<ReverseRecipePacket> {

  private static final Logger log = LogUtils.getLogger();

  private static void unsafeOperationOnClientSide(ReverseRecipePacket msg, Supplier<Context> ctx) {
    ReverseWorkbenchBlockMenu.displayDummyItems(msg.getIngredients(), msg.getRecipeWidth(),
      msg.getRecipeHeight());
  }

  @Override
  public void encode(ReverseRecipePacket msg, FriendlyByteBuf buffer) {
    msg.encode(buffer);
  }

  @Override
  public ReverseRecipePacket decode(FriendlyByteBuf buffer) {
    return ReverseRecipePacket.decode(buffer);
  }

  @Override
  public void handle(ReverseRecipePacket msg, Supplier<Context> ctx) {
    ctx.get().enqueueWork(() -> {
      DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> unsafeOperationOnClientSide(msg, ctx));
    });
    ctx.get().setPacketHandled(true);
  }
}
