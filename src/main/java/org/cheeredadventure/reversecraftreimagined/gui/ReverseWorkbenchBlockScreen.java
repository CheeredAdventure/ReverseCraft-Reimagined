package org.cheeredadventure.reversecraftreimagined.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.cheeredadventure.reversecraftreimagined.ReverseCraftReimagined;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

public class ReverseWorkbenchBlockScreen extends
  AbstractContainerScreen<ReverseWorkbenchBlockMenu> {

  private static final ResourceLocation REVERSE_WORKBENCH_GUI_TEXTURE;
  private static final Logger log = LogUtils.getLogger();

  static {
    REVERSE_WORKBENCH_GUI_TEXTURE = ResourceLocation.fromNamespaceAndPath(
      ReverseCraftReimagined.MODID,
      "textures/gui/reverseworkbench.png");
  }

  public ReverseWorkbenchBlockScreen(ReverseWorkbenchBlockMenu menu, Inventory inventory,
    Component title) {
    super(menu, inventory, title);
    this.imageWidth = 176;
    this.imageHeight = 207;

  }

  @Override
  protected void init() {
    super.init();
    this.leftPos = (this.width - this.imageWidth) / 2;
    this.topPos = (this.height - this.imageHeight) / 2;
  }

  @Override
  protected void renderBg(@NotNull GuiGraphics guiGraphics, float partialTicks, int x, int y) {
    RenderSystem.setShaderTexture(0, REVERSE_WORKBENCH_GUI_TEXTURE);
    guiGraphics.blit(REVERSE_WORKBENCH_GUI_TEXTURE, this.leftPos, this.topPos, 0, 0,
      this.imageWidth, this.imageHeight);
  }

  @Override
  public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
    this.renderBackground(guiGraphics);
    super.render(guiGraphics, mouseX, mouseY, partialTicks);
    this.renderTooltip(guiGraphics, mouseX, mouseY);
  }
}
