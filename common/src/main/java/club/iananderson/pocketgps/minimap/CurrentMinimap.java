package club.iananderson.pocketgps.minimap;

import club.iananderson.pocketgps.PocketGps;
import club.iananderson.pocketgps.client.PocketGpsClient;
import club.iananderson.pocketgps.impl.xaero.minimap.MinimapEffect;
import club.iananderson.pocketgps.impl.xaero.worldmap.WorldMapEffect;
import club.iananderson.pocketgps.platform.Services;
import dev.ftb.mods.ftbchunks.client.FTBChunksClientConfig;
import java.util.ArrayList;
import java.util.List;
import journeymap.client.ui.UIManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Player;

public class CurrentMinimap {
  private static boolean minimapLoaded(Minimaps minimap) {
    String modID = minimap.getModID();
    return Services.PLATFORM.isModLoaded(modID);
  }

  public static List<Minimaps> loadedMinimaps() {
    List<Minimaps> values = new ArrayList<>(List.of(Minimaps.values()));
    List<Minimaps> loaded = new ArrayList<>();

    values.forEach(minimaps -> {
      if (minimapLoaded(minimaps)) {
        loaded.add(minimaps);
      }
    });
    return loaded;
  }

  public static boolean noMinimapLoaded() {
    return loadedMinimaps().isEmpty();
  }

  public static boolean xaeroLoaded() {
    return minimapLoaded(Minimaps.XAERO) || minimapLoaded(Minimaps.XAERO_FAIRPLAY);
  }

  public static boolean journeyMapLoaded() {
    return minimapLoaded(Minimaps.JOURNEYMAP);
  }

  public static boolean onlyFtbChunksLoaded() {
    return (minimapLoaded(Minimaps.FTB_CHUNKS) && loadedMinimaps().size() == 1);
  }

  public static void displayMinimap(Player player) {
    if (player == null || PocketGpsClient.isDrawingMap()) {
      return;
    }

    if (journeyMapLoaded() && player.level.isClientSide) {
      UIManager.INSTANCE.setMiniMapEnabled(true);
    }

    if (xaeroLoaded()) {
      if (player.hasEffect(MinimapEffect.NO_MINIMAP)) {
        player.removeEffect(MinimapEffect.NO_MINIMAP);
      }

      if (PocketGps.worldMapLoaded()) {
        if (player.hasEffect(WorldMapEffect.NO_WORLD_MAP)) {
          player.removeEffect(WorldMapEffect.NO_WORLD_MAP);
        }
      }
    }

    if (onlyFtbChunksLoaded() && player.level.isClientSide) {
      FTBChunksClientConfig.MINIMAP_ENABLED.set(true);
    }

    PocketGpsClient.setIsDrawingMap(true);
  }

  public static void removeMinimap(Player player) {
    if (player == null || !PocketGpsClient.isDrawingMap()) {
      return;
    }

    if (journeyMapLoaded() && player.level.isClientSide) {
      UIManager.INSTANCE.setMiniMapEnabled(false);
    }

    if (xaeroLoaded()) {
      if (!player.hasEffect(MinimapEffect.NO_MINIMAP)) {
        player.addEffect(MinimapEffect.noMiniMap);
      }

      if (PocketGps.worldMapLoaded()) {
        if (!player.hasEffect(WorldMapEffect.NO_WORLD_MAP)) {
          player.addEffect(WorldMapEffect.noWorldMap);
        }
      }
    }

    if (onlyFtbChunksLoaded() && player.level.isClientSide) {
      FTBChunksClientConfig.MINIMAP_ENABLED.set(false);
    }

    PocketGpsClient.setIsDrawingMap(false);
  }

  public enum Minimaps {
    XAERO("xaerominimap", new TranslatableComponent("minimap.pocketgps.xaero")),

    XAERO_FAIRPLAY("xaerominimapfair", new TranslatableComponent("minimap.pocketgps.xaero")),

    JOURNEYMAP("journeymap", new TranslatableComponent("minimap.pocketgps.journeymap")),

    FTB_CHUNKS("ftbchunks", new TranslatableComponent("minimap.pocketgps.ftb"));

    private final String modID;

    private final MutableComponent modName;

    Minimaps(String modID, MutableComponent modName) {
      this.modID = modID;
      this.modName = modName;
    }

    public String getModID() {
      return this.modID;
    }

    public MutableComponent getModName() {
      return this.modName;
    }
  }
}
