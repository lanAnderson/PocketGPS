package club.iananderson.pocketgps.forge.registry;

import club.iananderson.pocketgps.PocketGps;
import club.iananderson.pocketgps.registry.CommonRegistration;
import club.iananderson.pocketgps.registry.ModBlocks;
import club.iananderson.pocketgps.registry.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ForgeCreativeModeTabs {
  public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB = DeferredRegister.create(
      Registries.CREATIVE_MODE_TAB, PocketGps.MOD_ID);

  public static RegistryObject<CreativeModeTab> TAB = CREATIVE_MODE_TAB.register("tab", () -> CreativeModeTab.builder()
      .title(Component.translatable("tab.pocketgps"))
      .icon(() -> CommonRegistration.addIcon(ModItems.GPS.get()))
      .displayItems((par, out) -> entries(out))
      .build());

  private static void entries(CreativeModeTab.Output entries) {
    entries.acceptAll(CommonRegistration.addPoweredItem(ModItems.GPS.get(), true));
    entries.accept(ModBlocks.CHARGING_DOCK.get());
  }

  public static void register(IEventBus eventBus) {
    CREATIVE_MODE_TAB.register(eventBus);
  }
}
