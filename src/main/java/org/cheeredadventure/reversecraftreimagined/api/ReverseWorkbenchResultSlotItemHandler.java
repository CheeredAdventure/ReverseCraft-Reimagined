package org.cheeredadventure.reversecraftreimagined.api;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.cheeredadventure.reversecraftreimagined.blocks.ReverseWorkbenchBlockEntity;
import org.jetbrains.annotations.NotNull;

public class ReverseWorkbenchResultSlotItemHandler extends SlotItemHandler {

  private final BlockPos blockPos;
  private final ReverseWorkbenchBlockEntity blockEntity;

  public ReverseWorkbenchResultSlotItemHandler(final IItemHandler itemHandler,
    BlockEntity blockEntity, List<Slot> craftGridSlots) {
    super(itemHandler, 9, 124, 35);
    this.blockEntity = (ReverseWorkbenchBlockEntity) blockEntity;
    this.blockPos = blockEntity.getBlockPos();
  }

  @Override
  public void onTake(@NotNull Player player, @NotNull ItemStack itemStack) {
    this.blockEntity.clearDummyItems();
    super.onTake(player, itemStack);
  }
}
