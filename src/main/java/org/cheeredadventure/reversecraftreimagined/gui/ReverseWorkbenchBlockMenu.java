package org.cheeredadventure.reversecraftreimagined.gui;

import com.mojang.logging.LogUtils;
import lombok.Getter;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.cheeredadventure.reversecraftreimagined.api.ReverseWorkbenchMenuTypes;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

public class ReverseWorkbenchBlockMenu extends AbstractContainerMenu {

  private static final Logger log = LogUtils.getLogger();
  private static final int INVENTORY_START = 10;
  private static final int INVENTORY_END = 37;
  private static final int HOTBAR_END = 46;
  private final ContainerLevelAccess access;
  private final SimpleContainer craftingGrid;
  @Getter
  private final SimpleContainer resultContainer;

  public ReverseWorkbenchBlockMenu(int id, Inventory playerInventory, FriendlyByteBuf extraData) {
    this(id, playerInventory,
      extraData != null ? ContainerLevelAccess.create(playerInventory.player.level(),
        extraData.readBlockPos()) : ContainerLevelAccess.NULL);
  }

  public ReverseWorkbenchBlockMenu(int id, Inventory playerInventory, ContainerLevelAccess access) {
    super(ReverseWorkbenchMenuTypes.getReverseWorkbenchMenu().get(), id);
    this.access = access;

    // crafting grid
    craftingGrid = new SimpleContainer(9);
    for (int row = 0; row < 3; ++row) {
      for (int col = 0; col < 3; ++col) {
        this.addSlot(new Slot(this.craftingGrid, col + row * 3, 30 + col * 18, 17 + row * 18));
      }
    }

    // result item slot
    resultContainer = new SimpleContainer(1);
    this.addSlot(new Slot(this.resultContainer, 0, 124, 35) {
      @Override
      public boolean mayPlace(ItemStack itemStack) {
        return true;
      }
    });

// player inventory
    for (int row = 0; row < 3; ++row) {
      for (int col = 0; col < 9; ++col) {
        this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
      }
    }

    // hotbar
    for (int i = 0; i < 9; ++i) {
      this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
    }
  }

  @Override
  public boolean stillValid(@NotNull Player player) {
    return this.access.evaluate((level, blockPos) -> true, true);
  }

  @Override
  @NotNull
  public ItemStack quickMoveStack(@NotNull Player player, int i) {
    ItemStack newStack = ItemStack.EMPTY;
    final Slot slot = this.slots.get(i);

    if (slot.hasItem()) {
      ItemStack originalStack = slot.getItem();
      newStack = originalStack.copy();

      if (i < INVENTORY_START) {
        if (!this.moveItemStackTo(originalStack, INVENTORY_START, this.slots.size(), true)) {
          return ItemStack.EMPTY;
        }
      } else if (!this.moveItemStackTo(originalStack, 0, INVENTORY_START, false)) {
        return ItemStack.EMPTY;
      }

      if (originalStack.isEmpty()) {
        slot.set(ItemStack.EMPTY);
      } else {
        slot.setChanged();
      }
    }
    return newStack;
  }
}
