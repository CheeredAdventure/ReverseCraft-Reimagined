package org.cheeredadventure.reversecraftreimagined.api;

import net.minecraft.network.chat.Component;
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
}
