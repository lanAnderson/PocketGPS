package club.iananderson.pocketgps.forge.registry;

import club.iananderson.pocketgps.PocketGps;
import club.iananderson.pocketgps.forge.items.ChargeableGpsItem;
import club.iananderson.pocketgps.registry.ModItems;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ForgeItems {
  public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, PocketGps.MOD_ID);

  public static final RegistryObject<Item> POCKET_GPS = ITEMS.register("gps", ChargeableGpsItem::new);

  static {
    ModItems.GPS = POCKET_GPS;
  }

  public static void register(IEventBus eventBus) {
    PocketGps.LOG.info("Registering Mod Items for " + PocketGps.MOD_ID);
    ITEMS.register(eventBus);
  }
}
