package org.cheeredadventure.reversecraftreimagined.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CraftingTableBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.cheeredadventure.reversecraftreimagined.gui.ReverseWorkbenchBlockMenu;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ReverseWorkbench extends CraftingTableBlock implements EntityBlock {

  private static final Component CONTAINER_TITLE = Component.translatable(
    "container.reverseworkbenchcrafting");

  public ReverseWorkbench(Properties properties) {
    super(properties);
  }

  @Override
  @NotNull
  public InteractionResult use(@NotNull BlockState blockState, @NotNull Level level,
    @NotNull BlockPos blockPos, @NotNull Player player, @NotNull InteractionHand hand,
    @NotNull BlockHitResult hitResult) {
    if (!level.isClientSide()) {
      NetworkHooks.openScreen((ServerPlayer) player, new SimpleMenuProvider(
        (id, playerInventory, playerEntity) -> new ReverseWorkbenchBlockMenu(id, playerInventory,
          ContainerLevelAccess.create(level, blockPos)), CONTAINER_TITLE), blockPos);
    }
    return InteractionResult.SUCCESS;
  }

  @Override
  @NotNull
  public MenuProvider getMenuProvider(@NotNull BlockState blockState, @NotNull Level level,
    @NotNull BlockPos blockPos) {
    return new SimpleMenuProvider(
      (id, playerInventory, player) -> new ReverseWorkbenchBlockMenu(id, playerInventory,
        ContainerLevelAccess.create(level, blockPos)), CONTAINER_TITLE);
  }

  @Nullable
  @Override
  public BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
    return new ReverseWorkbenchBlockEntity(blockPos, blockState);
  }
}
