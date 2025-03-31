package org.cheeredadventure.reversecraftreimagined.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.logging.LogUtils;
import java.util.List;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.cheeredadventure.reversecraftreimagined.ReverseCraftReimagined;
import org.cheeredadventure.reversecraftreimagined.api.Helper.ComponentType;
import org.cheeredadventure.reversecraftreimagined.api.Helper.KeyString;
import org.cheeredadventure.reversecraftreimagined.api.networking.PacketHandler;
import org.cheeredadventure.reversecraftreimagined.api.networking.ReversingWorkPacket;
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

  public ReverseWorkbenchBlockScreen(ReverseWorkbenchBlockMenu menu, Inventory playerInventory,
    Component title) {
    super(menu, playerInventory, title);
  }

  @Override
  protected void init() {
    super.init();
    Button reverseButton = Button.builder(
        KeyString.getTranslatableKey(ComponentType.GUI, "reverse"),
        button -> {
          final ItemStack resultSlotItemStack = this.menu.getReverseWorkbenchBlockEntity()
            .getInventory()
            .getStackInSlot(9);
          if (resultSlotItemStack.isEmpty()) {
            return;
          }
          final List<ItemStack> ingredients = this.menu.slots.subList(36, 45)
            .stream()
            .map(Slot::getItem)
            .filter(itemStack -> !itemStack.isEmpty())
            .toList();
          final BlockPos blockPos = this.menu.getReverseWorkbenchBlockEntity().getBlockPos();
          ReversingWorkPacket packet = new ReversingWorkPacket(ingredients,
            resultSlotItemStack, blockPos);
          PacketHandler.INSTANCE.sendToServer(packet);
          List<Slot> slots = this.menu.slots.subList(36, 46);
          slots.forEach(slot -> slot.set(ItemStack.EMPTY));
          slots.forEach(Slot::setChanged);
        })
      .bounds(this.leftPos + 100, this.topPos + 60, 50, 15)
      .build();
    this.addRenderableWidget(reverseButton);
  }

  @Override
  protected void renderBg(GuiGraphics guiGraphics, float v, int i, int i1) {
    RenderSystem.setShader(GameRenderer::getPositionTexShader);
    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    RenderSystem.setShaderTexture(0, REVERSE_WORKBENCH_GUI_TEXTURE);
    int x = (this.width - this.imageWidth) / 2;
    int y = (this.height - this.imageHeight) / 2;

    guiGraphics.blit(REVERSE_WORKBENCH_GUI_TEXTURE, x, y, 0, 0, this.imageWidth, this.imageHeight);
  }

  @Override
  public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
    renderBackground(guiGraphics);
    super.render(guiGraphics, mouseX, mouseY, partialTicks);
    renderTooltip(guiGraphics, mouseX, mouseY);
  }
}
