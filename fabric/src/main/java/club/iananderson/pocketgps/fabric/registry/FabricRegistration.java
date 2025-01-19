package club.iananderson.pocketgps.fabric.registry;

public class FabricRegistration {
  public static void register() {
    FabricItems.register();
    FabricBlocks.register();
    FabricBlockEntities.register();
    FabricCreativeModeTabs.register();
  }
}
