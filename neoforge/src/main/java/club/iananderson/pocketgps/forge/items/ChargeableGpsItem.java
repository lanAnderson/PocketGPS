package club.iananderson.pocketgps.forge.items;

import club.iananderson.pocketgps.energy.ItemEnergyStorage;
import club.iananderson.pocketgps.forge.energy.EnergyStorageImpl;
import club.iananderson.pocketgps.items.BaseChargeableGps;
import javax.annotation.Nonnull;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.Nullable;

public class ChargeableGpsItem extends BaseChargeableGps implements ItemEnergyStorage {
  public ChargeableGpsItem() {
    super();
  }

  @Override
  public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag nbt) {
    ItemEnergyStorage container = this;
    return new ICapabilityProvider() {
      @Nonnull
      @Override
      public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ENERGY) {
          return LazyOptional.of(() -> new EnergyStorageImpl(stack, container)).cast();
        }
        return LazyOptional.empty();
      }
    };
  }
}
