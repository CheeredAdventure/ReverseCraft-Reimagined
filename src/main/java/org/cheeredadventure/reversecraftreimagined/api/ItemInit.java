package org.cheeredadventure.reversecraftreimagined.api;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.cheeredadventure.reversecraftreimagined.ReverseCraftReimagined;

public class ItemInit {

  public static final DeferredRegister<Item> ITEMS;
  static final RegistryObject<Item> BLOCK_REVERSE_WORKBENCH_ITEM;

  static {
    ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ReverseCraftReimagined.MODID);
    BLOCK_REVERSE_WORKBENCH_ITEM = ITEMS.register("reverseworkbench",
      () -> new BlockItem(BlockInit.REVERSE_WORKBENCH.get(),
        new Item.Properties()));
  }
}
