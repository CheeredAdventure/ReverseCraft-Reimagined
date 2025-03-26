package org.cheeredadventure.reversecraftreimagined.internal;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.cheeredadventure.reversecraftreimagined.ReverseCraftReimagined;

@Mod.EventBusSubscriber(modid = ReverseCraftReimagined.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

  @SubscribeEvent
  public static void gatherData(GatherDataEvent event) {
    DataGenerator generator = event.getGenerator();

    generator.addProvider(event.includeServer(),
      new ReverseCraftRecipeProvider(generator.getPackOutput()));
    generator.addProvider(event.includeClient(),
      new ReverseCraftLangProvider.ReverseCraftLangUS(generator.getPackOutput()));
    generator.addProvider(event.includeClient(),
      new ReverseCraftLangProvider.ReverseCraftLangJP(generator.getPackOutput()));
  }
}
