package org.cheeredadventure.reversecraftreimagined.blocks;

import java.util.List;
import lombok.Getter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.cheeredadventure.reversecraftreimagined.api.Helper;
import org.cheeredadventure.reversecraftreimagined.api.Helper.ComponentType;
import org.cheeredadventure.reversecraftreimagined.api.ReverseWorkbenchBlockEntities;
import org.cheeredadventure.reversecraftreimagined.gui.ReverseWorkbenchBlockMenu;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Getter
public class ReverseWorkbenchBlockEntity extends BlockEntity implements MenuProvider {

  // 9 slots for crafting grid, 1 slot for result
  private final ItemStackHandler inventory = new ItemStackHandler(9 + 1);
  private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
  private ItemStack[] gridItems = new ItemStack[9];


  public ReverseWorkbenchBlockEntity(BlockPos pos, BlockState state) {
    super(ReverseWorkbenchBlockEntities.REVERSE_WORKBENCH_BLOCK_ENTITY.get(), pos, state);
    for (int i = 0; i < 9; i++) {
      this.gridItems[i] = ItemStack.EMPTY;
    }
  }

  @Override
  public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap,
    @Nullable Direction side) {
    if (cap == ForgeCapabilities.ITEM_HANDLER) {
      return this.lazyItemHandler.cast();
    }

    return super.getCapability(cap, side);
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

  public <T extends Recipe<?>> void setDummyItems(T recipe, List<Slot> craftGridSlots) {
    if (recipe instanceof ShapedRecipe shaped) {
      final int width = shaped.getWidth();
      final int height = shaped.getHeight();
      final List<Ingredient> ingredients = shaped.getIngredients();
      for (int row = 0; row < height; row++) {
        for (int column = 0; column < width; column++) {
          final int index = row * width + column;
          if (index >= ingredients.size()) {
            continue;
          }
          final Ingredient ingredient = ingredients.get(index);
          if (ingredient.isEmpty()) {
            continue;
          }
          final ItemStack item = ingredient.getItems()[0].copy();
          craftGridSlots.get(index).set(item);
        }
      }
    } else if (recipe instanceof ShapelessRecipe shapeless) {
      final List<Ingredient> ingredients = shapeless.getIngredients();
      for (int i = 0; i < ingredients.size(); i++) {
        final Ingredient ingredient = ingredients.get(i);
        if (ingredient.isEmpty()) {
          continue;
        }
        final ItemStack item = ingredient.getItems()[0].copy();
        craftGridSlots.get(i).set(item);
      }
    } else {
      throw new IllegalArgumentException("Unsupported recipe type: " + recipe.getType());
    }
    this.setChanged();
  }

  public void setGridItems(ItemStack[] gridItems) {
    this.gridItems = gridItems;
    this.setChanged();
  }

  @Override
  public @NotNull Component getDisplayName() {
    return Helper.KeyString.getTranslatableKey(ComponentType.CONTAINER, "reverseworkbenchcrafting");
  }

  @Override
  public @Nullable AbstractContainerMenu createMenu(int i, @NotNull Inventory inventory,
    @NotNull Player player) {
    return new ReverseWorkbenchBlockMenu(i, inventory, this);
  }

  @Override
  protected void saveAdditional(CompoundTag tag) {
    tag.put("inventory", this.inventory.serializeNBT());
    for (int i = 0; i < 9; i++) {
      tag.put("gridItem" + i, this.gridItems[i].save(new CompoundTag()));
    }
    super.saveAdditional(tag);
  }

  @Override
  public void load(@NotNull CompoundTag tag) {
    super.load(tag);
    this.inventory.deserializeNBT(tag.getCompound("inventory"));
    for (int i = 0; i < 9; i++) {
      this.gridItems[i] = ItemStack.of(tag.getCompound("gridItem" + i));
    }
  }

  @Override
  public @NotNull CompoundTag getUpdateTag() {
    CompoundTag tag = new CompoundTag();
    saveAdditional(tag);
    return tag;
  }

  @Override
  public @Nullable ClientboundBlockEntityDataPacket getUpdatePacket() {
    return ClientboundBlockEntityDataPacket.create(this);
  }

  @Override
  public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
    load(pkt.getTag());
  }

  // TODO: implement crafting logic
}
