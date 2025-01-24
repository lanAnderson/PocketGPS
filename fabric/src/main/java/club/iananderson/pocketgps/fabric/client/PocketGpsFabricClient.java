package club.iananderson.pocketgps.fabric.client;

import club.iananderson.pocketgps.PocketGps;
import club.iananderson.pocketgps.client.PocketGpsClient;
import club.iananderson.pocketgps.config.PocketGpsConfig;
import club.iananderson.pocketgps.items.properties.GpsItemProperties;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientEntityEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraftforge.api.ModLoadingContext;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fml.config.ModConfig.Type;

public final class PocketGpsFabricClient implements ClientModInitializer {
  @Override
  public void onInitializeClient() {
    ModLoadingContext.registerConfig(PocketGps.MOD_ID, Type.COMMON, PocketGpsConfig.GENERAL_SPEC,
                                     "pocketgps-common.toml");

    PocketGps.clientInit();
    ItemProperties.register(PocketGps.GPS.get(), PocketGps.TOGGLE_GPS, new GpsItemProperties());

    ClientTickEvents.END_CLIENT_TICK.register(client -> {
      if (client.player != null) {
        PocketGpsClient.cachePlayerState(client.player);
      }
    });

    ClientEntityEvents.ENTITY_LOAD.register((entity, world) -> {
      if (entity instanceof Player player) {
        PocketGpsClient.setInitializedMapState(false);
        PocketGpsClient.setIsDrawingMap(false);
        PocketGpsClient.cachePlayerState(player);
      }
    });
  }
}
