package club.iananderson.pocketgps.forge.registry;

import club.iananderson.pocketgps.PocketGps;
import club.iananderson.pocketgps.blocks.entity.BaseChargingDockBlockEntity;
import club.iananderson.pocketgps.registry.ModBlockEntities;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ForgeBlockEntities {
  public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(
      ForgeRegistries.BLOCK_ENTITY_TYPES, PocketGps.MOD_ID);

  public static final RegistryObject<BlockEntityType<BaseChargingDockBlockEntity>> CHARGING_DOCK_BE = BLOCK_ENTITIES.register(
      "charging_dock_be",
      () -> BlockEntityType.Builder.of(BaseChargingDockBlockEntity::new, ForgeBlocks.CHARGING_DOCK.get())
          .build(null));

  static {
    ModBlockEntities.CHARGING_DOCK_BE = CHARGING_DOCK_BE;
  }

  public static void register(IEventBus eventBus) {
    PocketGps.LOG.info("Registering Block Entities for " + PocketGps.MOD_ID);
    BLOCK_ENTITIES.register(eventBus);
  }
}
