package org.cheeredadventure.reversecraftreimagined.blocks;

import lombok.Getter;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;
import org.cheeredadventure.reversecraftreimagined.api.ReverseWorkbenchBlockEntities;
import org.jetbrains.annotations.NotNull;

@Getter
public class ReverseWorkbenchBlockEntity extends BlockEntity {

  // 9 slots for crafting grid, 1 slot for result
  private final ItemStackHandler inventory = new ItemStackHandler(9 + 1);

  public ReverseWorkbenchBlockEntity(BlockPos pos, BlockState state) {
    super(ReverseWorkbenchBlockEntities.REVERSE_WORKBENCH_BLOCK_ENTITY.get(), pos, state);
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

  public void drops() {
    SimpleContainer inventory = new SimpleContainer(this.inventory.getSlots());
    for (int i = 0; i < this.inventory.getSlots(); i++) {
      inventory.setItem(i, this.inventory.getStackInSlot(i));
    }
  }

}
