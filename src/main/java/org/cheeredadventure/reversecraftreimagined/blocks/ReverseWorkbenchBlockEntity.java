package org.cheeredadventure.reversecraftreimagined.blocks;

import lombok.Getter;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.cheeredadventure.reversecraftreimagined.api.ReverseWorkbenchBlockEntities;
import org.cheeredadventure.reversecraftreimagined.gui.ReverseWorkbenchBlockMenu;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Getter
public class ReverseWorkbenchBlockEntity extends BlockEntity implements MenuProvider {

  // 9 slots for crafting grid, 1 slot for result
  private final ItemStackHandler inventory = new ItemStackHandler(9 + 1);
  private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

  public ReverseWorkbenchBlockEntity(BlockPos pos, BlockState state) {
    super(ReverseWorkbenchBlockEntities.REVERSE_WORKBENCH_BLOCK_ENTITY.get(), pos, state);
  }

  @Override
  @NotNull
  public Component getDisplayName() {
    return Component.translatable("container.reverseworkbenchcrafting");
  }

  @Override
  public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
    return new ReverseWorkbenchBlockMenu(i, inventory, this, this.data);
  }

  @Override
  protected void saveAdditional(@NotNull CompoundTag tag) {
    super.saveAdditional(tag);
    tag.put("inventory", this.inventory.serializeNBT());
  }

  @Override
  public void load(@NotNull CompoundTag tag) {
    super.load(tag);
    this.inventory.deserializeNBT(tag.getCompound("inventory"));
  }

  @Override
  public void onLoad() {
    super.onLoad();
    this.lazyItemHandler = LazyOptional.of(() -> this.inventory);
  }

  @Override
  public void invalidateCaps() {
    super.invalidateCaps();
    this.lazyItemHandler.invalidate();
  }

  public void drops() {
    SimpleContainer inventory = new SimpleContainer(this.inventory.getSlots());
    for (int i = 0; i < this.inventory.getSlots(); i++) {
      inventory.setItem(i, this.inventory.getStackInSlot(i));
    }
    Containers.dropContents(this.level, this.worldPosition, inventory);
  }

}
