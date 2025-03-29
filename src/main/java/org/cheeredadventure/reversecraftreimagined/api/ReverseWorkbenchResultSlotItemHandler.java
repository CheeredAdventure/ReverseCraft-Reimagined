package org.cheeredadventure.reversecraftreimagined.api;

import com.mojang.logging.LogUtils;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.cheeredadventure.reversecraftreimagined.api.networking.PacketHandler;
import org.cheeredadventure.reversecraftreimagined.api.networking.ReverseCraftPacket;
import org.cheeredadventure.reversecraftreimagined.api.networking.ReverseRecipeClearGridPacket;
import org.cheeredadventure.reversecraftreimagined.blocks.ReverseWorkbenchBlockEntity;
import org.slf4j.Logger;

public class ReverseWorkbenchResultSlotItemHandler extends SlotItemHandler {

  private final BlockPos blockPos;
  private static final Logger log = LogUtils.getLogger();
  private final ReverseWorkbenchBlockEntity blockEntity;
  private final List<Slot> craftGridSlots;

  public ReverseWorkbenchResultSlotItemHandler(final IItemHandler itemHandler,
    BlockEntity blockEntity, List<Slot> craftGridSlots) {
    super(itemHandler, 9, 124, 35);
    this.blockEntity = (ReverseWorkbenchBlockEntity) blockEntity;
    this.blockPos = blockEntity.getBlockPos();
    this.craftGridSlots = craftGridSlots;
  }

  @Override
  public void setChanged() {
    final ItemStack itemStack = this.getItem();
    if (itemStack.isEmpty()) {
      ReverseRecipeClearGridPacket packet = new ReverseRecipeClearGridPacket(this.blockPos);
      PacketHandler.INSTANCE.sendToServer(packet);
      super.setChanged();
      return;
    }
    ReverseCraftPacket packet = new ReverseCraftPacket(itemStack, this.blockPos);
    PacketHandler.INSTANCE.sendToServer(packet);
    super.setChanged();
  }
}
