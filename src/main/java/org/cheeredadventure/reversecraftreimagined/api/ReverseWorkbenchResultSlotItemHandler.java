package org.cheeredadventure.reversecraftreimagined.api;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ReverseWorkbenchResultSlotItemHandler extends SlotItemHandler {

  public ReverseWorkbenchResultSlotItemHandler(final IItemHandler itemHandler) {
    super(itemHandler, 9, 124, 35);
  }

  @Override
  public void setChanged() {
    final ItemStack itemStack = this.getItem();
    final BlockPos blockPos = this.
  }
}
