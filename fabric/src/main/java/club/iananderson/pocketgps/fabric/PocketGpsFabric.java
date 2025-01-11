package club.iananderson.pocketgps.fabric;

import club.iananderson.pocketgps.PocketGps;
import club.iananderson.pocketgps.fabric.registry.FabricRegistration;
import net.fabricmc.api.ModInitializer;

public final class PocketGpsFabric implements ModInitializer {
  //Todo ---- Find out why it wont receive power on servers only

  @Override
  public void onInitialize() {
    PocketGps.init();
    FabricRegistration.register();
  }
}
