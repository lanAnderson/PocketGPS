package club.iananderson.pocketgps.blocks.entity;

import club.iananderson.pocketgps.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class BaseChargingDockBlockEntity extends BlockEntity implements MenuProvider {
  protected final ContainerData data;

  public BaseChargingDockBlockEntity(BlockPos pos, BlockState blockState) {
    super(ModBlockEntities.CHARGING_DOCK_BE.get(), pos, blockState);
    this.data = new ContainerData() {
      @Override
      public int get(int index) {
        return 0;

//        return switch (index) {
//          case 0 -> BaseChargingDockBlockEntity.this.progress;
//          case 1 -> BaseChargingDockBlockEntity.this.maxProgress;
//          default -> 0;
//        };
      }

      @Override
      public void set(int index, int value) {
//        switch (index) {
//          case 0 -> BaseChargingDockBlockEntity.this.progress = value;
//          case 1 -> BaseChargingDockBlockEntity.this.maxProgress = value;
//        }
      }

      @Override
      public int getCount() {
        return 0;

//        return 2
      }
    };
  }

  @Override
  public Component getDisplayName() {
    return Component.translatable("block.pocketgps.charging_dock");
  }

  @Nullable
  @Override
  public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
    return null;
    //return new ChagingDockMenu(containerId, playerInventory, this, this.data);
  }

  public void tick(Level level, BlockPos pos, BlockState state) {

  }
}
