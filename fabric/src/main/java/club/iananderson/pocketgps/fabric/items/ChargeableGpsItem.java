package club.iananderson.pocketgps.fabric.items;

import club.iananderson.pocketgps.items.GpsItem;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import team.reborn.energy.api.base.SimpleEnergyItem;

public class ChargeableGpsItem extends GpsItem implements SimpleEnergyItem {
  private int energyCapacity;
  private int maxInput;
  private int maxOutput;
  private int cost;


  public ChargeableGpsItem(Properties properties, int energyCapacity, int maxInput, int maxOutput, int cost) {
    super(properties);
    this.energyCapacity = energyCapacity;
    this.maxInput = maxInput;
    this.maxOutput = maxOutput;
    this.cost = cost;
  }

  @Override
  public long getEnergyCapacity(ItemStack stack) {
    return energyCapacity;
  }

  @Override
  public long getEnergyMaxInput(ItemStack stack) {
    return maxInput;
  }

  @Override
  public long getEnergyMaxOutput(ItemStack stack) {
    return maxOutput;
  }

  public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
    if(entity instanceof Player player){
      if (player.walkAnimation.isMoving()){
        this.tryUseEnergy(stack, this.cost);

        if(this.getStoredEnergy(stack)< this.cost){
          setStoredEnergy(stack,0);
        }
      }
    }
  }

  public boolean allowNbtUpdateAnimation(Player player, InteractionHand hand, ItemStack oldStack, ItemStack newStack){
    return false;
  }

    @Override
  public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
    super.appendHoverText(stack, worldIn, tooltip, flagIn);

    tooltip.add(Component.literal(""));
    tooltip.addAll(energyComponents(stack));
  }

  public List<Component> energyComponents(ItemStack stack) {
    List<Component> energyTooltips = new ArrayList<>();
    DecimalFormat commaFormat = new DecimalFormat("#,###");
    DecimalFormat kFormat = new DecimalFormat("###.0k");
    DecimalFormat percentFormat = new DecimalFormat("###");

    float storedEnergy = this.getStoredEnergy(stack);
    float maxEnergy = this.getEnergyCapacity(stack);
    float percentage = ((storedEnergy / maxEnergy) * 100);

    String simpleStoredEnergy;
    String expandedStoredEnergy = commaFormat.format(storedEnergy);
    String simpleMaxEnergy = kFormat.format(maxEnergy / 1000);
    String expandedMaxEnergy = commaFormat.format(maxEnergy);
    String percentageText = percentFormat.format(percentage);

    if (storedEnergy < 1000) {
      simpleStoredEnergy = commaFormat.format(storedEnergy);
    } else {
      simpleStoredEnergy = kFormat.format((storedEnergy) / 1000);
    }

    if (!Screen.hasShiftDown()) {
      energyTooltips.add(
          Component.translatable("item.pocketgps.gps.tooltip.energy.stored", simpleStoredEnergy, simpleMaxEnergy, "E")
              .withStyle(ChatFormatting.GOLD));
    } else {
      energyTooltips.add(
          Component.translatable("item.pocketgps.gps.tooltip.energy.stored", expandedStoredEnergy, expandedMaxEnergy,
                                 "E").withStyle(ChatFormatting.GOLD));
      energyTooltips.add(Component.translatable("item.pocketgps.gps.tooltip.energy.percent", percentageText)
                             .withStyle(ChatFormatting.DARK_GRAY));
    }

    return energyTooltips;
  }

  public int getBarWidth(ItemStack stack) {
    return Math.round((float) this.getStoredEnergy(stack) * 100.0F / (float) this.getEnergyCapacity(stack) * 13.0F)
        / 100;
  }

  public boolean isBarVisible(ItemStack stack) {
    return true;
  }

  public int getBarColor(ItemStack stack) {
    return 16744454;
  }
}
