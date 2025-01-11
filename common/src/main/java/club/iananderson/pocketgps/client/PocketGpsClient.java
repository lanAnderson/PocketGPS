package club.iananderson.pocketgps.client;

import club.iananderson.pocketgps.PocketGps;
import club.iananderson.pocketgps.impl.accessories.AccessoriesCompat;
import club.iananderson.pocketgps.impl.curios.CuriosCompat;
import club.iananderson.pocketgps.impl.trinkets.TrinketsCompat;
import club.iananderson.pocketgps.minimap.CurrentMinimap;
import club.iananderson.pocketgps.util.ItemUtil;
import club.iananderson.pocketgps.util.NBTUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class PocketGpsClient {
  private static ItemStack currentActiveGps = ItemStack.EMPTY;
  private static boolean isDrawingMap = false;

  @NotNull
  private static ItemStack getGpsFromInventory(Inventory inventory) {
    int max = inventory.getContainerSize();
    for (int i = 0; i < max; ++i) {
      ItemStack itemstack = inventory.getItem(i);
      if (itemstack.is(PocketGps.GPS.get())) {
        return itemstack;
      }
    }
    return ItemStack.EMPTY;
  }

  @NotNull
  public static ItemStack getGpsFromPlayer(Player player) {
    Inventory inv = player.getInventory();
    // first scan hand
    ItemStack mainHandItem = player.getMainHandItem();
    ItemStack offhandItem = player.getOffhandItem();
    ItemStack inventoryItem = getGpsFromInventory(inv);

    if (mainHandItem.is(PocketGps.GPS.get())) {
      return mainHandItem;
    }
    // then offhand
    if (offhandItem.is(PocketGps.GPS.get())) {
      return offhandItem;
    }

    //then curios
    if (PocketGps.curiosLoaded()) {
      ItemStack curioItem = CuriosCompat.getGpsInCurio(player);
      if (!curioItem.isEmpty()) {
        return curioItem;
      }
    }

    if (PocketGps.trinketsLoaded()) {
      ItemStack trinketItem = TrinketsCompat.getGpsInTrinket(player);
      if (!trinketItem.isEmpty()) {
        return trinketItem;
      }
    }

    if (PocketGps.accessoriesLoaded()) {
      ItemStack accessoryItem = AccessoriesCompat.getGpsInAccessory(player);
      if (!accessoryItem.isEmpty()) {
        return accessoryItem;
      }
    }
    return inventoryItem;
  }

  public static void cachePlayerState(Player player) {
    if (player != Minecraft.getInstance().player) {
      return;
    }
    ItemStack gps = getGpsFromPlayer(player);
    currentActiveGps = gps;
    if (gps.isEmpty()) {
      setIsDrawingMap(false);
      CurrentMinimap.displayMinimap(player);
    }

    if (!gps.isEmpty()) {
      boolean hasPower = NBTUtil.getInt(gps, PocketGps.ENERGY_TAG) > 0;
      boolean gpsOn = ItemUtil.isGpsOn(gps);

      if (PocketGps.gpsNeedPower()) {
        setIsDrawingMap(hasPower && gpsOn);
        CurrentMinimap.displayMinimap(player);
      } else {
        setIsDrawingMap(gpsOn);
        CurrentMinimap.displayMinimap(player);
      }
    }
  }

  public static ItemStack getCurrentActiveGps() {
    return currentActiveGps;
  }

  public static void setIsDrawingMap(boolean state) {
    isDrawingMap = state;
  }

  public static boolean isDrawingMap() {
    return isDrawingMap;
  }
}