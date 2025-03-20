package org.cheeredadventure.reversecraftreimagined.api;

import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.cheeredadventure.reversecraftreimagined.ReverseCraftReimagined;
import org.cheeredadventure.reversecraftreimagined.gui.ReverseWorkbenchBlockMenu;

public class ReverseWorkbenchMenuTypes {

  public static final DeferredRegister<MenuType<?>> MENU_TYPES;
  static final RegistryObject<MenuType<ReverseWorkbenchBlockMenu>> REVERSE_WORKBENCH_MENU;

  static {
    MENU_TYPES = DeferredRegister.create(ForgeRegistries.MENU_TYPES, ReverseCraftReimagined.MODID);
    REVERSE_WORKBENCH_MENU = MENU_TYPES.register("reverseworkbench_menu",
      () -> IForgeMenuType.create(ReverseWorkbenchBlockMenu::new));
  }

  public static RegistryObject<MenuType<ReverseWorkbenchBlockMenu>> getReverseWorkbenchMenu() {
    return REVERSE_WORKBENCH_MENU;
  }
}
