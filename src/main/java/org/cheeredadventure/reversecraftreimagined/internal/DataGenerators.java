package org.cheeredadventure.reversecraftreimagined.internal;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.cheeredadventure.reversecraftreimagined.ReverseCraftReimagined;

@Mod.EventBusSubscriber(modid = ReverseCraftReimagined.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

  @SubscribeEvent
  public static void gatherData(GatherDataEvent event) {
    DataGenerator generator = event.getGenerator();
    PackOutput packOutput = generator.getPackOutput();

    generator.addProvider(event.includeServer(), new ReverseCraftRecipeProvider(packOutput));
    generator.addProvider(event.includeClient(),
      new ReverseCraftLangProvider.ReverseCraftLangUS(packOutput));
    generator.addProvider(event.includeClient(),
      new ReverseCraftLangProvider.ReverseCraftLangJP(packOutput));

    ExistingFileHelper fileHelper = event.getExistingFileHelper();
    generator.addProvider(event.includeClient(),
      new ReverseWorkbenchBlockStateProvider(packOutput, fileHelper));
    generator.addProvider(event.includeServer(), new ReverseWorkbenchBlockTagsProvider(packOutput,
      event.getLookupProvider(), fileHelper
    ));
    generator.addProvider(event.includeServer(), new ReverseWorkbenchBlockLootTableProvider(
      packOutput
    ));
  }
}
