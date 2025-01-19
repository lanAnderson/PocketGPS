package club.iananderson.pocketgps.blocks;

import club.iananderson.pocketgps.blocks.entity.BaseChargingDockBlockEntity;
import club.iananderson.pocketgps.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class BaseChargingDockBlock extends BaseEntityBlock {
  public static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 16, 16);

  public BaseChargingDockBlock(Properties properties) {
    super(properties);
  }

  @Override
  public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
    return SHAPE;
  }

  @Override
  public RenderShape getRenderShape(BlockState state) {
    return RenderShape.MODEL;
  }

  @Nullable
  @Override
  public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
    return new BaseChargingDockBlockEntity(pos, state);
  }

  @Nullable
  @Override
  public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state,
      BlockEntityType<T> blockEntityType) {
    if (level.isClientSide()) {
      return null;
    }

    return createTickerHelper(blockEntityType, ModBlockEntities.CHARGING_DOCK_BE.get(),
                              (level1, pos, state1, blockEntity) -> blockEntity.tick(level1, pos, state1));
  }
}
