package club.iananderson.pocketgps.forge.registry;

import net.minecraftforge.eventbus.api.IEventBus;

public class ForgeRegistration {
  public static void register(IEventBus modEventBus) {
    ForgeCreativeModeTabs.register(modEventBus);
    ForgeItems.register(modEventBus);
    ForgeBlocks.register(modEventBus);
    ForgeBlockEntities.register(modEventBus);
  }
}
