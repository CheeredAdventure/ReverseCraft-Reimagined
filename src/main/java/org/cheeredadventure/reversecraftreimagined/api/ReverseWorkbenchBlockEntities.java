package org.cheeredadventure.reversecraftreimagined.api;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.cheeredadventure.reversecraftreimagined.ReverseCraftReimagined;
import org.cheeredadventure.reversecraftreimagined.blocks.ReverseWorkbenchBlockEntity;

public class ReverseWorkbenchBlockEntities {

  public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES;
  public static final RegistryObject<BlockEntityType<ReverseWorkbenchBlockEntity>> REVERSE_WORKBENCH_BLOCK_ENTITY;

  static {
    BLOCK_ENTITIES = DeferredRegister.create(
      ForgeRegistries.BLOCK_ENTITY_TYPES, ReverseCraftReimagined.MODID);
    REVERSE_WORKBENCH_BLOCK_ENTITY = BLOCK_ENTITIES.register(
      "reverseworkbench_block_entity",
      () -> BlockEntityType.Builder.of(ReverseWorkbenchBlockEntity::new,
        BlockInit.REVERSE_WORKBENCH.get()).build(null)
    );
  }
}
