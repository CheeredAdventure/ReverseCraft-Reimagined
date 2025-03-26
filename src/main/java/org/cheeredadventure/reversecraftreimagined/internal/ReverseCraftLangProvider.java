package org.cheeredadventure.reversecraftreimagined.internal;

import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;
import org.cheeredadventure.reversecraftreimagined.ReverseCraftReimagined;
import org.cheeredadventure.reversecraftreimagined.api.BlockInit;
import org.cheeredadventure.reversecraftreimagined.api.Helper;
import org.cheeredadventure.reversecraftreimagined.api.Helper.ComponentType;

public abstract class ReverseCraftLangProvider extends LanguageProvider {

  public ReverseCraftLangProvider(PackOutput output, String locale) {
    super(output, ReverseCraftReimagined.MODID, locale);
  }

  public static class ReverseCraftLangUS extends ReverseCraftLangProvider {

    public ReverseCraftLangUS(PackOutput output) {
      super(output, "en_us");
    }

    @Override
    protected void addTranslations() {
      this.add(BlockInit.getREVERSE_WORKBENCH().get(), "Reverse Workbench");
      this.add(Helper.KeyString.getTranslatableKeyAsString(ComponentType.CONTAINER,
        "reverseworkbenchcrafting"), "Reverse Crafting");
    }
  }

  public static class ReverseCraftLangJP extends ReverseCraftLangProvider {

    public ReverseCraftLangJP(PackOutput output) {
      super(output, "ja_jp");
    }

    @Override
    protected void addTranslations() {
      this.add(BlockInit.getREVERSE_WORKBENCH().get(), "リバースワークベンチ");
      this.add(Helper.KeyString.getTranslatableKeyAsString(ComponentType.CONTAINER,
        "reverseworkbenchcrafting"), "リバースクラフト");
    }
  }
}
