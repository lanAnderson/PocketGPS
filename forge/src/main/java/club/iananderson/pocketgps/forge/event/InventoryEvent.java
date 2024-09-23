package club.iananderson.pocketgps.forge.event;

import club.iananderson.pocketgps.PocketGps;
import club.iananderson.pocketgps.forge.registry.ForgeRegistration;
import club.iananderson.pocketgps.minimap.CurrentMinimap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.util.ICuriosHelper;

@Mod.EventBusSubscriber(modid = PocketGps.MOD_ID, bus = Bus.FORGE, value = Dist.CLIENT)
public class InventoryEvent {
  private static boolean findCurio(Player player, Item item) {
    if (player == null) {
      return false;
    }

    int slot = 0;

    if (PocketGps.curiosLoaded()) {
      ICuriosHelper curiosInventory = CuriosApi.getCuriosHelper();
      if (curiosInventory.findFirstCurio(player, item).isPresent()) {
        slot += 1;
      }
    }
    return slot > 0;
  }

  @SubscribeEvent
  public static void onPlayerTickEvent(PlayerTickEvent event) {
    if (event.side == LogicalSide.CLIENT) {
      Player player = event.player;

      boolean hasGpsInv = CurrentMinimap.hasGps(player, ForgeRegistration.POCKET_GPS.get());
      boolean hasGpsCurio = findCurio(player, ForgeRegistration.POCKET_GPS.get());

      CurrentMinimap.displayMinimap(player,hasGpsInv || hasGpsCurio);
    }
  }
}
