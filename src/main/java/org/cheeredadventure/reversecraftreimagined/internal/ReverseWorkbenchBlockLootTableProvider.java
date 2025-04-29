package org.cheeredadventure.reversecraftreimagined.internal;

import java.util.Collections;
import java.util.List;
import javax.annotation.Nonnull;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.registries.RegistryObject;
import org.cheeredadventure.reversecraftreimagined.api.BlockInit;

public class ReverseWorkbenchBlockLootTableProvider extends LootTableProvider {

  public ReverseWorkbenchBlockLootTableProvider(PackOutput pOutput) {
    super(pOutput, Collections.emptySet(), List.of(
      new SubProviderEntry(ReverseWorkbenchBlockLootTableSubProvider::new,
        LootContextParamSets.BLOCK)));
  }

  private static class ReverseWorkbenchBlockLootTableSubProvider extends BlockLootSubProvider {

    protected ReverseWorkbenchBlockLootTableSubProvider() {
      super(Collections.emptySet(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
      this.add(BlockInit.getREVERSE_WORKBENCH().get(), this::createNameableBlockEntityTable);
    }

    @Override
    @Nonnull
    protected Iterable<Block> getKnownBlocks() {
      return BlockInit.BLOCKS.getEntries()
        .stream()
        .flatMap(RegistryObject::stream)
        ::iterator;
    }
  }
}
