package org.cheeredadventure.reversecraftreimagined.internal;

import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.cheeredadventure.reversecraftreimagined.ReverseCraftReimagined;
import org.cheeredadventure.reversecraftreimagined.api.BlockInit;

public class ReverseWorkbenchBlockStateProvider extends BlockStateProvider {

  public ReverseWorkbenchBlockStateProvider(PackOutput output, ExistingFileHelper fileHelper) {
    super(output, ReverseCraftReimagined.MODID, fileHelper);
  }

  @Override
  protected void registerStatesAndModels() {
    this.simpleBlockWithItem(
      BlockInit.getREVERSE_WORKBENCH().get(),
      this.models()
        .cube(
          "reverseworkbench",
          mcLoc("block/oak_planks"),
          mcLoc("block/crafting_table_top"),
          mcLoc("block/crafting_table_side"),
          mcLoc("block/crafting_table_front"),
          mcLoc("block/crafting_table_front"),
          mcLoc("block/crafting_table_side")));
  }
}
