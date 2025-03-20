package org.cheeredadventure.reversecraftreimagined.api;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTab.TabVisibility;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.cheeredadventure.reversecraftreimagined.ReverseCraftReimagined;

@Mod.EventBusSubscriber(modid = ReverseCraftReimagined.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class CreativeTabInit {

  public static final DeferredRegister<CreativeModeTab> TABS;

  public static final List<Supplier<? extends ItemLike>> REVERSECRAFT_TAB_ITEMS;

  static final RegistryObject<CreativeModeTab> REVERSECRAFT_TAB;

  static {
    TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ReverseCraftReimagined.MODID);
    REVERSECRAFT_TAB_ITEMS = new ArrayList<>();
    REVERSECRAFT_TAB = TABS.register("reversecraft_tab", () -> CreativeModeTab.builder()
      .title(Component.literal("ReverseCraft Reimagined MOD"))
      .displayItems((parameters, output) -> {
        REVERSECRAFT_TAB_ITEMS.forEach(itemLike -> output.accept(itemLike.get()));
      }).build());
  }

  public static <T extends ItemLike> RegistryObject<T> addTabItem(RegistryObject<T> itemLike) {
    REVERSECRAFT_TAB_ITEMS.add(itemLike);
    return itemLike;
  }

  @SubscribeEvent
  public static void buildContents(BuildCreativeModeTabContentsEvent event) {
    if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
      event.getEntries().put(BlockInit.REVERSE_WORKBENCH.get().asItem().getDefaultInstance(),
        TabVisibility.PARENT_AND_SEARCH_TABS);
    }

    if (event.getTab() == REVERSECRAFT_TAB.get()) {
      event.accept(BlockInit.REVERSE_WORKBENCH.get().asItem());
    }
  }
}
