package club.iananderson.pocketgps.fabric.registry;

import club.iananderson.pocketgps.PocketGps;
import club.iananderson.pocketgps.registry.CommonRegistration;
import club.iananderson.pocketgps.registry.ModBlocks;
import club.iananderson.pocketgps.registry.ModItems;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;

public class FabricCreativeModeTabs {
  public static final CreativeModeTab.Builder CREATIVE_MODE_TAB = FabricItemGroup.builder();
  private static final ResourceKey<CreativeModeTab> ITEM_GROUP;
  public static CreativeModeTab TAB = CREATIVE_MODE_TAB.title(Component.translatable("tab.pocketgps"))
      .icon(() -> CommonRegistration.addIcon(ModItems.GPS.get())).displayItems((par, out) -> entries(out))
      .build();

  static {
    ITEM_GROUP = ResourceKey.create(Registries.CREATIVE_MODE_TAB, new ResourceLocation(PocketGps.MOD_ID, "item_group"));
  }

  private static void entries(CreativeModeTab.Output entries) {
    entries.acceptAll(CommonRegistration.addPoweredItem(ModItems.GPS.get(), true));
    entries.accept(ModBlocks.CHARGING_DOCK.get());
  }

  public static void register() {
    Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, PocketGps.location("tab"), TAB);
    ItemGroupEvents.modifyEntriesEvent(ITEM_GROUP)
        .register(FabricCreativeModeTabs::entries);
  }
}
