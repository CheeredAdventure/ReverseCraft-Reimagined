package org.cheeredadventure.reversecraftreimagined.api;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface ReverseRecipeSearcher<T> {

  List<T> searchRecipe(final ItemStack resultItem, final BlockPos reverseWorkbenchBlockPos,
    final Player invokedPlayer);
}
