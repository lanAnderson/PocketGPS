package club.iananderson.pocketgps.fabric.registry;

import club.iananderson.pocketgps.PocketGps;
import club.iananderson.pocketgps.blocks.BaseChargingDockBlock;
import club.iananderson.pocketgps.registry.ModBlocks;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class FabricBlocks {
  public static final Block CHARGING_DOCK = registerBlock("charging_dock", new BaseChargingDockBlock(
      BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));

  static {
    ModBlocks.CHARGING_DOCK = () -> CHARGING_DOCK;
  }

  private static Block registerBlock(String name, Block block) {
    registerBlockItem(name, block);
    return Registry.register(BuiltInRegistries.BLOCK, PocketGps.location(name), block);
  }

  private static Item registerBlockItem(String name, Block block) {
    return Registry.register(BuiltInRegistries.ITEM, PocketGps.location(name),
                             new BlockItem(block, new FabricItemSettings()));
  }

  public static void register() {
    PocketGps.LOG.info("Registering ModBlocks for " + PocketGps.MOD_ID);
  }
}
