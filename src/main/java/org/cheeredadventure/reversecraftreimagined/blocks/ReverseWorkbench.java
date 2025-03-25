package org.cheeredadventure.reversecraftreimagined.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CraftingTableBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ReverseWorkbench extends CraftingTableBlock implements EntityBlock {

  private static final Component CONTAINER_TITLE = Component.translatable(
    "container.reverseworkbenchcrafting");

  public ReverseWorkbench(Properties properties) {
    super(properties);
  }

  @Override
  public void onRemove(BlockState blockState, Level level, BlockPos blockPos,
    BlockState newBlockState,
    boolean isMoving) {
    if (blockState.getBlock() != newBlockState.getBlock()) {
      BlockEntity blockEntity = level.getBlockEntity(blockPos);
      if (blockEntity instanceof ReverseWorkbenchBlockEntity reverseWorkbenchBlockEntity) {
        reverseWorkbenchBlockEntity.drops();
      }
    }
  }

  @Override
  @NotNull
  public InteractionResult use(@NotNull BlockState blockState, @NotNull Level level,
    @NotNull BlockPos blockPos, @NotNull Player player, @NotNull InteractionHand hand,
    @NotNull BlockHitResult hitResult) {
    if (!level.isClientSide()) {
      BlockEntity entity = level.getBlockEntity(blockPos);
      if (entity instanceof ReverseWorkbenchBlockEntity reverseWorkbenchBlockEntity) {
        NetworkHooks.openScreen((ServerPlayer) player, reverseWorkbenchBlockEntity, blockPos);
      } else {
        throw new IllegalStateException("Missing block entity for ReverseWorkbench");
      }
    }
    return InteractionResult.sidedSuccess(level.isClientSide());
  }

  @Nullable
  @Override
  public BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
    return new ReverseWorkbenchBlockEntity(blockPos, blockState);
  }
}
