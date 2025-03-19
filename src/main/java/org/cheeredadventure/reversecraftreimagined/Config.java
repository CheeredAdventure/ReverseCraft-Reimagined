package org.cheeredadventure.reversecraftreimagined;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.tuple.Pair;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Forge's config APIs
public class Config {

  public static final Config.Common COMMON;
  static final ForgeConfigSpec commonSpec;

  static {
    final Pair<Common, ForgeConfigSpec> commonSpecPair = new ForgeConfigSpec.Builder().configure(
      Common::new);
    commonSpec = commonSpecPair.getRight();
    COMMON = commonSpecPair.getLeft();
  }

  public static void saveClientConfig() {
    //TODO:  clientSpec.save();
  }

  @Getter
  public static class Common {

    private final Miscs misc;

    private Common(ForgeConfigSpec.Builder builder) {
      builder.push("common");
      {
        this.misc = new Miscs(builder);
      }
      builder.pop();
    }
  }

  public static class Miscs {

    private final ForgeConfigSpec.ConfigValue<String> magicNumberIntroduction;
    private final ForgeConfigSpec.BooleanValue logDirtBlock;
    private final ForgeConfigSpec.IntValue magicNumber;
    // a list of strings that are treated as resource locations for items
    private final ForgeConfigSpec.ConfigValue<List<? extends String>> itemStrings;

    private Miscs(ForgeConfigSpec.Builder builder) {
      builder.comment("Other misceallous configurations").push("misc");
      {
        this.magicNumberIntroduction = builder
          .comment("What you want the introduction message to be for the magic number")
          .define("magicNumberIntroduction", "The magic number is... ");
        this.logDirtBlock = builder
          .comment("Whether to log the dirt block on common setup")
          .define("logDirtBlock", true);
        this.magicNumber = builder
          .comment("A magic number")
          .defineInRange("magicNumber", 42, 0, Integer.MAX_VALUE);
        this.itemStrings = builder
          .comment("A list of items to log on common setup.")
          .defineListAllowEmpty("items", List.of("minecraft:iron_ingot"), Miscs::validateItemName);
      }
      builder.pop();
    }

    private static boolean validateItemName(final Object obj) {
      return obj instanceof final String itemName && ForgeRegistries.ITEMS.containsKey(
        ResourceLocation.tryParse(itemName));
    }

    public String getMagicNumberIntroduction() {
      return this.magicNumberIntroduction.get();
    }

    public boolean getLogDirtBlock() {
      return this.logDirtBlock.get();
    }

    public int getMagicNumber() {
      return this.magicNumber.get();
    }

    public Set<String> getItemStrings() {
      return this.itemStrings.get()
        .parallelStream()
        .collect(Collectors.toSet());
    }
  }
}
