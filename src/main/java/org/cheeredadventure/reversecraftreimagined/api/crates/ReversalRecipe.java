package org.cheeredadventure.reversecraftreimagined.api.crates;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import org.cheeredadventure.reversecraftreimagined.api.Helper.Registries;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ReversalRecipe {

  private final InventoryItemRecord[][] ingredients;
  private final InventoryItemRecord result;
  private final boolean isShaped;

  public static class Builder {

    private InventoryItemRecord[][] ingredients;
    private InventoryItemRecord result;
    private boolean isShaped = true;
    private List<String> patterns;
    private Map<Character, InventoryItemRecord> patternChars;

    public Builder setIngredients(InventoryItemRecord[][] ingredients) {
      this.ingredients = ingredients;
      return this;
    }

    public Builder setResult(InventoryItemRecord result) {
      this.result = result;
      return this;
    }

    public Builder pattern(String pattern) {
      if (this.patterns == null) {
        this.patterns = new ArrayList<>();
      }
      if (this.patterns.size() >= 3) {
        throw new IllegalStateException("Cannot add more than 3 patterns");
      }
      this.patterns.add(pattern);
      return this;
    }

    public Builder register(char character, @Nonnull ItemLike itemLike) {
      if (this.patternChars == null) {
        this.patternChars = new ConcurrentHashMap<>();
      }
      if (this.patternChars.size() >= 9) {
        throw new IllegalStateException("Cannot link more than 9 characters");
      }
      if (this.patternChars.containsKey(character)) {
        return this;
      }
      final String itemResourceId = Registries.getItemResourceLocation(itemLike)
        .map(ResourceLocation::toString).orElse("");
      this.patternChars.put(character, new InventoryItemRecord(itemResourceId));
      return this;
    }

    public ReversalRecipe build() {
      if (this.ingredients == null) {
        convertPatternsToIngredients();
      }
      if (this.patterns == null && this.patternChars == null) {
        throw new IllegalStateException("No patterns or ingredients set");
      }
      return new ReversalRecipe(ingredients, result, isShaped);
    }

    private void convertPatternsToIngredients() {
      if (this.patterns == null || this.patternChars == null) {
        return;
      }
      this.ingredients = new InventoryItemRecord[3][3];
      for (int i = 0; i < 3; i++) {
        String pattern = i < this.patterns.size() ? this.patterns.get(i) : "";
        for (int j = 0; j < 3; j++) {
          char character = j < pattern.length() ? pattern.charAt(j) : ' ';
          InventoryItemRecord itemRecord = this.patternChars.get(character);
          this.ingredients[i][j] = Objects.requireNonNullElseGet(
            itemRecord, InventoryItemRecord::empty);
        }
      }
    }

    public Builder isShaped(boolean shaped) {
      this.isShaped = shaped;
      return this;
    }
  }
}
