package org.cheeredadventure.reversecraftreimagined.api.networking;

import com.mojang.logging.LogUtils;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent.Context;
import org.cheeredadventure.reversecraftreimagined.blocks.ReverseWorkbenchBlockEntity;
import org.slf4j.Logger;

public class ReversingWorkPacketHandler implements IPacketHandler<ReversingWorkPacket> {

  private static final Logger log = LogUtils.getLogger();

  @Override
  public void encode(ReversingWorkPacket msg, FriendlyByteBuf buffer) {
    msg.encode(buffer);
  }

  @Override
  public ReversingWorkPacket decode(FriendlyByteBuf buffer) {
    return ReversingWorkPacket.decode(buffer);
  }

  @Override
  public void handle(ReversingWorkPacket msg, Supplier<Context> ctx) {
    ctx.get().enqueueWork(() -> {
      ServerPlayer player = ctx.get().getSender();
      final List<ItemStack> ingredients = msg.getIngredients();
      final ItemStack stack = msg.getResultSlotItemStack();
      final BlockPos blockPos = msg.getBlockPos();

      BlockEntity entity = player.level().getBlockEntity(blockPos);
      if (!(entity instanceof ReverseWorkbenchBlockEntity)) {
        log.warn("our ReverseWorkbenchBlockEntity is missing!");
        return;
      }

      log.debug("Received reverse work packet from player: {}", player);

      stack.shrink(stack.getCount());
      // give the player the ingredients
      for (ItemStack ingredient : ingredients) {
        if (ingredient.isEmpty()) {
          continue;
        }
        ItemStack copy = ingredient.copy();
        copy.setCount(1);
        player.getInventory().add(copy);
      }
    });
    ctx.get().setPacketHandled(true);
  }
}
