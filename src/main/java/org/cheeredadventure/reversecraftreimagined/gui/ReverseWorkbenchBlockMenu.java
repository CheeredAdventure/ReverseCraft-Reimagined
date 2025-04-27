package org.cheeredadventure.reversecraftreimagined.gui;

import com.mojang.logging.LogUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.cheeredadventure.reversecraftreimagined.api.BlockInit;
import org.cheeredadventure.reversecraftreimagined.api.ReverseWorkbenchMenuTypes;
import org.cheeredadventure.reversecraftreimagined.api.ReverseWorkbenchResultSlotItemHandler;
import org.cheeredadventure.reversecraftreimagined.blocks.ReverseWorkbenchBlockEntity;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

public class ReverseWorkbenchBlockMenu extends AbstractContainerMenu {

  private static final Logger log = LogUtils.getLogger();
  private static final int INVENTORY_START = 10;
  private static final int CRAFTING_GRID_SIZE = 9;
  @Getter
  private final ReverseWorkbenchBlockEntity reverseWorkbenchBlockEntity;
  static final int VANILLA_FIRST_SLOT_INDEX = 0;
  static final int REVERSE_WORKBENCH_SLOT_COUNT = 10;
  // CREDIT GOES TO: diesieben07 | https://github.com/diesieben07/SevenCommons
  // must assign a slot number to each of the slots used by the GUI.
  // For this container, we can see both the tile inventory's slots as well as the player inventory slots and the hotbar.
  // Each time we add a Slot to the container, it automatically increases the slotIndex, which means
  //  0 - 8 = hotbar slots (which will map to the InventoryPlayer slot numbers 0 - 8)
  //  9 - 35 = player inventory slots (which map to the InventoryPlayer slot numbers 9 - 35)
  //  36 - 44 = TileInventory slots, which map to our TileEntity slot numbers 0 - 8)
  private static final int HOTBAR_SLOT_COUNT = 9;
  private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
  private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
  private static final int PLAYER_INVENTORY_SLOT_COUNT =
    PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
  private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
  static final int REVERSE_WORKBENCH_INVENTORY_FIRST_SLOT_INDEX =
    VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;
  private final Level level;

  public ReverseWorkbenchBlockMenu(int id, Inventory playerInventory, FriendlyByteBuf extraData) {
    this(id, playerInventory,
      playerInventory.player.level().getBlockEntity(extraData.readBlockPos()));
  }

  public ReverseWorkbenchBlockMenu(int id, Inventory playerInventory, BlockEntity blockEntity) {
    super(ReverseWorkbenchMenuTypes.getReverseWorkbenchMenu().get(), id);
    checkContainerSize(playerInventory, 10);
    this.reverseWorkbenchBlockEntity = (ReverseWorkbenchBlockEntity) blockEntity;
    this.level = playerInventory.player.level();

    addPlayerInventory(playerInventory);
    addPlayerHotbar(playerInventory);

    this.reverseWorkbenchBlockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER)
      .ifPresent(IItemhandler -> {
      for (int row = 0; row < 3; row++) {
        for (int column = 0; column < 3; column++) {
          addSlot(
            new SlotItemHandler(IItemhandler, row * 3 + column, 30 + column * 18, 17 + row * 18));
        }
      }
        this.addSlot(
          new ReverseWorkbenchResultSlotItemHandler(
            IItemhandler,
            this.reverseWorkbenchBlockEntity,
            this.slots.subList(36, 45)));
    });
  }

  @Override
  public void removed(Player pPlayer) {
    super.removed(pPlayer);

    if (pPlayer.level().isClientSide()) {
      return;
    }

    LazyOptional<IItemHandler> lazyOptional = this.reverseWorkbenchBlockEntity
      .getCapability(ForgeCapabilities.ITEM_HANDLER);
    lazyOptional.ifPresent(handler -> {
      boolean changed = false;
      boolean isResultSlotEmpty = handler.getStackInSlot(9).isEmpty();
      for (int i = 0; i < 9; i++) {
        ItemStack stack = handler.getStackInSlot(i);
        if (isResultSlotEmpty) {
          pPlayer.addItem(stack);
        }
        if (!stack.isEmpty() && handler instanceof ItemStackHandler itemStackHandler) {
          itemStackHandler.setStackInSlot(i, ItemStack.EMPTY);
          changed = true;
        } else if (!stack.isEmpty()) {
          log.warn("Unexpected item stack in slot {}: {}", i, stack);
        }
      }
      ItemStack resultStack = handler.getStackInSlot(9);
      pPlayer.addItem(resultStack);
      this.reverseWorkbenchBlockEntity.clearGridInventory();
      if (!resultStack.isEmpty() && handler instanceof ItemStackHandler itemStackHandler) {
        itemStackHandler.setStackInSlot(9, ItemStack.EMPTY);
        changed = true;
      } else {
        log.warn("Unexpected item stack in result slot: {}", resultStack);
      }
      if (changed) {
        this.reverseWorkbenchBlockEntity.setChanged();
      }
    });
  }

  @Override
  public @NotNull ItemStack quickMoveStack(@NotNull Player player, int i) {
    ItemStack itemstack = ItemStack.EMPTY;
    Slot slot = this.slots.get(i);
    if (slot.hasItem()) {
      ItemStack itemstack1 = slot.getItem();
      itemstack = itemstack1.copy();
      if (i < VANILLA_SLOT_COUNT) {
        if (!this.moveItemStackTo(itemstack1, REVERSE_WORKBENCH_INVENTORY_FIRST_SLOT_INDEX,
          REVERSE_WORKBENCH_INVENTORY_FIRST_SLOT_INDEX + REVERSE_WORKBENCH_SLOT_COUNT, false)) {
          return ItemStack.EMPTY;
        }
      } else if (i < REVERSE_WORKBENCH_INVENTORY_FIRST_SLOT_INDEX + REVERSE_WORKBENCH_SLOT_COUNT) {
        if (!this.moveItemStackTo(itemstack1, 0, VANILLA_SLOT_COUNT, false)) {
          return ItemStack.EMPTY;
        }
      }
      if (itemstack1.isEmpty()) {
        slot.set(ItemStack.EMPTY);
      } else {
        slot.setChanged();
      }
      if (itemstack1.getCount() == itemstack.getCount()) {
        return ItemStack.EMPTY;
      }
      slot.onTake(player, itemstack1);
    }
    return itemstack;
  }

  @Override
  public boolean stillValid(@NotNull Player player) {
    return stillValid(
      ContainerLevelAccess.create(this.level, this.reverseWorkbenchBlockEntity.getBlockPos()),
      player,
      BlockInit.getREVERSE_WORKBENCH().get());
  }

  private void addPlayerInventory(Inventory inventory) {
    for (int row = 0; row < 3; ++row) {
      for (int col = 0; col < 9; ++col) {
        this.addSlot(new Slot(inventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
      }
    }
  }

  private void addPlayerHotbar(Inventory inventory) {
    for (int row = 0; row < 9; ++row) {
      this.addSlot(new Slot(inventory, row, 8 + row * 18, 142));
    }
  }

  @RequiredArgsConstructor
  private static class DummyInventory extends SimpleContainer {

    private final ItemStack[] items;

    @Override
    public ItemStack getItem(int index) {
      return items[index];
    }
  }
}
