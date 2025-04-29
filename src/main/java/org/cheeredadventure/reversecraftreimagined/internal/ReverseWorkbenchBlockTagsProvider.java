package org.cheeredadventure.reversecraftreimagined.internal;

import java.util.concurrent.CompletableFuture;
import javax.annotation.Nonnull;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.cheeredadventure.reversecraftreimagined.ReverseCraftReimagined;
import org.cheeredadventure.reversecraftreimagined.api.BlockInit;
import org.jetbrains.annotations.Nullable;

public class ReverseWorkbenchBlockTagsProvider extends BlockTagsProvider {

  public ReverseWorkbenchBlockTagsProvider(PackOutput output,
    CompletableFuture<Provider> lookupProvider,
    @Nullable ExistingFileHelper existingFileHelper) {
    super(output, lookupProvider, ReverseCraftReimagined.MODID, existingFileHelper);
  }

  @Override
  protected void addTags(@Nonnull Provider pProvider) {
    this.tag(BlockTags.MINEABLE_WITH_AXE)
      .add(BlockInit.getREVERSE_WORKBENCH().get());
  }
}
