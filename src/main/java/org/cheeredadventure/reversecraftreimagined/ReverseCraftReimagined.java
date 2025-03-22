package org.cheeredadventure.reversecraftreimagined;

import com.mojang.logging.LogUtils;
import java.util.Objects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.cheeredadventure.reversecraftreimagined.api.BlockInit;
import org.cheeredadventure.reversecraftreimagined.api.CreativeTabInit;
import org.cheeredadventure.reversecraftreimagined.api.ItemInit;
import org.cheeredadventure.reversecraftreimagined.api.PacketHandler;
import org.cheeredadventure.reversecraftreimagined.api.ReverseCraftPacket;
import org.cheeredadventure.reversecraftreimagined.api.ReverseCraftPacketHandler;
import org.cheeredadventure.reversecraftreimagined.api.ReverseWorkbenchMenuTypes;
import org.cheeredadventure.reversecraftreimagined.gui.ReverseWorkbenchBlockScreen;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ReverseCraftReimagined.MODID)
public class ReverseCraftReimagined {

  // Define mod id in a common place for everything to reference
  public static final String MODID = "reversecraftreimagined";
  private static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS;
  private static final RegistryObject<CreativeModeTab> EXAMPLE_TAB;

  static {
    CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);
    EXAMPLE_TAB = CREATIVE_MODE_TABS.register(
      "example_tab", () -> CreativeModeTab.builder()
        .title(Component.literal("ReverseCraft Reimagined MOD"))
        .withTabsBefore(CreativeModeTabs.COMBAT)
        .displayItems((parameters, output) -> {
        }).build());
  }
  // Directly reference a slf4j logger
  private static final Logger LOGGER = LogUtils.getLogger();

  public ReverseCraftReimagined(FMLJavaModLoadingContext context) {
    IEventBus modEventBus = context.getModEventBus();

    // Register the commonSetup method for modloading
    modEventBus.addListener(this::commonSetup);

    // Register the Deferred Register to the mod event bus so blocks get registered

    BlockInit.BLOCKS.register(modEventBus);
    // Register the Deferred Register to the mod event bus so items get registered
    ItemInit.ITEMS.register(modEventBus);
    // Register the Deferred Register to the mod event bus so tabs get registered
    CreativeTabInit.TABS.register(modEventBus);
    ReverseWorkbenchMenuTypes.MENU_TYPES.register(modEventBus);

    // Register ourselves for server and other game events we are interested in
    MinecraftForge.EVENT_BUS.register(this);

    // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
    context.registerConfig(ModConfig.Type.COMMON, Config.commonSpec);
  }

  private void commonSetup(final FMLCommonSetupEvent event) {
    event.enqueueWork(() -> {
      // Some common setup code
      LOGGER.info("HELLO FROM COMMON SETUP");

      if (Config.COMMON.getMisc().getLogDirtBlock()) {
        LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));
      }

      LOGGER.info("{}{}",
        Config.COMMON.getMisc().getMagicNumberIntroduction(),
        Config.COMMON.getMisc().getMagicNumber());

      Config.COMMON.getMisc().getItemStrings()
        .stream()
        .map(itemString -> ForgeRegistries.ITEMS.getValue(ResourceLocation.tryParse(itemString)))
        .filter(Objects::nonNull)
        .forEach((item) -> LOGGER.info("ITEM >> {}", item));

      PacketHandler.registerPacket(ReverseCraftPacket.class, new ReverseCraftPacketHandler());
    });
  }

  // You can use SubscribeEvent and let the Event Bus discover methods to call
  @SubscribeEvent
  public void onServerStarting(ServerStartingEvent event) {
    // Do something when the server starts
    LOGGER.info("HELLO from server starting");
  }

  // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
  @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
  public static class ClientModEvents {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
      event.enqueueWork(() -> {
        // Some client setup code
        LOGGER.info("HELLO FROM CLIENT SETUP");
        LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        MenuScreens.register(ReverseWorkbenchMenuTypes.getReverseWorkbenchMenu().get(),
          ReverseWorkbenchBlockScreen::new);
      });
    }
  }
}
