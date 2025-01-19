package club.iananderson.pocketgps.fabric.registry;

import club.iananderson.pocketgps.PocketGps;
import club.iananderson.pocketgps.blocks.entity.BaseChargingDockBlockEntity;
import club.iananderson.pocketgps.registry.ModBlockEntities;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class FabricBlockEntities {
  public static final BlockEntityType<BaseChargingDockBlockEntity> CHARGING_DOCK_BE = Registry.register(
      BuiltInRegistries.BLOCK_ENTITY_TYPE, PocketGps.location("charging_dock_be"),
      FabricBlockEntityTypeBuilder.create(BaseChargingDockBlockEntity::new, FabricBlocks.CHARGING_DOCK)
          .build());

  static {
    ModBlockEntities.CHARGING_DOCK_BE = () -> CHARGING_DOCK_BE;
  }

  public static void register() {
    PocketGps.LOG.info("Registering Block Entities for " + PocketGps.MOD_ID);
  }
}
