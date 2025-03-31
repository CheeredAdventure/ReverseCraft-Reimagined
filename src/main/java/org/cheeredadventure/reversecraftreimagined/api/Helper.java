package org.cheeredadventure.reversecraftreimagined.api;

import java.util.Optional;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import org.cheeredadventure.reversecraftreimagined.ReverseCraftReimagined;

public class Helper {

  public enum ComponentType {
    CONTAINER("container"),
    BLOCK("block"),
    ITEM("item"),
    ENTITY("entity"),
    GUI("gui"),
    SCREEN("screen");

    final String key;

    ComponentType(String key) {
      this.key = key;
    }

    public String getAsKey() {
      return this.key;
    }
  }

  public static class KeyString {

    public static Component getTranslatableKey(ComponentType type, String unique) {
      return Component.translatable(
        type.getAsKey() + "." + ReverseCraftReimagined.MODID + "." + unique);
    }

    public static String getTranslatableKeyAsString(ComponentType type, String unique) {
      return type.getAsKey() + "." + ReverseCraftReimagined.MODID + "." + unique;
    }
  }

  public static class Registries {

    public static String getRegistryName(String modid, String name) {
      return "%s:%s".formatted(modid, name);
    }

    public static String getRegistryName(String name) {
      return getRegistryName(ReverseCraftReimagined.MODID, name);
    }

    public static String getVanillaRegistryName(String name) {
      return getRegistryName("minecraft", name);
    }

    public static Optional<Block> getBlockResource(String name) {
      ResourceLocation r = ResourceLocation.tryParse(name);
      return Qualities.wrapWithOptional(ForgeRegistries.BLOCKS.getValue(r));
    }

    public static Optional<Item> getItemResource(String name) {
      ResourceLocation r = ResourceLocation.tryParse(name);
      return Qualities.wrapWithOptional(ForgeRegistries.ITEMS.getValue(r));
    }

    public static Optional<ResourceLocation> getItemResourceLocation(ItemLike itemLike) {
      Item item = itemLike.asItem();
      ResourceLocation location = ForgeRegistries.ITEMS.getKey(item);
      return Qualities.wrapWithOptional(location);
    }

    public static Optional<ItemStack> convertItemLike(@Nonnull ItemLike itemLike) {
      return Qualities.wrapWithOptional(itemLike.asItem().getDefaultInstance());
    }

    public static Optional<? extends ItemLike> convertItemStack(@Nonnull ItemStack stack) {
      return Qualities.wrapWithOptional(stack.getItem());
    }
  }

  public static class Qualities {

    public static <T> Optional<T> wrapWithOptional(@Nullable T value) {
      return Optional.ofNullable(value);
    }
  }
}
