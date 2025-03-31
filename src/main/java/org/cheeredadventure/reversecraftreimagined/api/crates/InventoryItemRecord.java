package org.cheeredadventure.reversecraftreimagined.api.crates;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import org.cheeredadventure.reversecraftreimagined.api.Helper.Registries;

public record InventoryItemRecord(String itemResourceId, int itemCount) {

  public InventoryItemRecord(String itemResourceId) {
    this(itemResourceId, 1);
  }

  public InventoryItemRecord(ItemLike itemLike, int itemCount) {
    this(Registries.getItemResourceLocation(itemLike).map(ResourceLocation::toString).orElse(""),
      itemCount);
  }
}
