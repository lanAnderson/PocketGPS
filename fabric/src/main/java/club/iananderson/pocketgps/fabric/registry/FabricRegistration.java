package club.iananderson.pocketgps.fabric.registry;

import club.iananderson.pocketgps.PocketGps;
import club.iananderson.pocketgps.fabric.items.ChargeableGpsItem;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class FabricRegistration {
  public static final CreativeModeTab.Builder TABS = FabricItemGroup.builder();

  public static final ChargeableGpsItem POCKET_GPS = new ChargeableGpsItem();

  public static CreativeModeTab TAB = TABS.icon(() -> new ItemStack(POCKET_GPS))
      .title(Component.translatable("tab.pocketgps"))
      .icon(() -> new ItemStack(POCKET_GPS)).displayItems((featureFlags, output) -> {
        output.accept(POCKET_GPS);
      })
      .build();

  public static void itemInit() {
    Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(PocketGps.MOD_ID, "gps"), POCKET_GPS);
    Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, new ResourceLocation(PocketGps.MOD_ID, "tab"), TAB);
  }
}
