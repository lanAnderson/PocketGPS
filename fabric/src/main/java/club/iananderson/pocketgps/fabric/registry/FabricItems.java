package club.iananderson.pocketgps.fabric.registry;

import club.iananderson.pocketgps.PocketGps;
import club.iananderson.pocketgps.fabric.items.ChargeableGpsItem;
import club.iananderson.pocketgps.registry.ModItems;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;

public class FabricItems {
  public static final Item POCKET_GPS = registerItem("gps", new ChargeableGpsItem());

  static {
    ModItems.GPS = () -> POCKET_GPS;
  }

  private static Item registerItem(String name, Item item) {
    return Registry.register(BuiltInRegistries.ITEM, PocketGps.location(name), item);
  }

  public static void register() {
    PocketGps.LOG.info("Registering Mod Items for " + PocketGps.MOD_ID);
  }
}
