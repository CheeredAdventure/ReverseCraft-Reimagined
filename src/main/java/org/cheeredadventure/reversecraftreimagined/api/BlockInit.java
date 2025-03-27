package org.cheeredadventure.reversecraftreimagined.api;

import lombok.Getter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.cheeredadventure.reversecraftreimagined.ReverseCraftReimagined;
import org.cheeredadventure.reversecraftreimagined.blocks.ReverseWorkbench;

public class BlockInit {

  public static final DeferredRegister<Block> BLOCKS;
  @Getter
  static final RegistryObject<Block> REVERSE_WORKBENCH;

  static {
    BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ReverseCraftReimagined.MODID);

    REVERSE_WORKBENCH = BLOCKS.register("reverseworkbench",
      () -> new ReverseWorkbench(BlockBehaviour.Properties.copy(Blocks.CRAFTING_TABLE)
        .sound(SoundType.WOOD)
        .mapColor(MapColor.WOOD)
        .strength(2.0F)
        .pushReaction(PushReaction.IGNORE)
      ));
  }
}
