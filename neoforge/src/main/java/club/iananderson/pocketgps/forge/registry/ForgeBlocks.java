package club.iananderson.pocketgps.forge.registry;

import club.iananderson.pocketgps.PocketGps;
import club.iananderson.pocketgps.blocks.BaseChargingDockBlock;
import club.iananderson.pocketgps.registry.ModBlocks;
import java.util.function.Supplier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ForgeBlocks {
  public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,
                                                                               PocketGps.MOD_ID);

  public static final RegistryObject<Block> CHARGING_DOCK = registerBlock("charging_dock",
                                                                          () -> new BaseChargingDockBlock(
                                                                              BlockBehaviour.Properties.copy(
                                                                                  Blocks.IRON_BLOCK).noOcclusion()));

  static {
    ModBlocks.CHARGING_DOCK = CHARGING_DOCK;
  }

  private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
    RegistryObject<T> toReturn = BLOCKS.register(name, block);
    registerBlockItem(name, toReturn);
    return toReturn;
  }

  private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
    return ForgeItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
  }

  public static void register(IEventBus eventBus) {
    PocketGps.LOG.info("Registering ModBlocks for " + PocketGps.MOD_ID);
    BLOCKS.register(eventBus);
  }
}
