package club.iananderson.pocketgps.blocks;

import club.iananderson.pocketgps.blocks.entity.BaseChargingDockBlockEntity;
import club.iananderson.pocketgps.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BellAttachType;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class BaseChargingDockBlock extends BaseEntityBlock {
  public static final VoxelShape SHAPE = Block.box(1, 0, 4, 15, 6, 12);
  public static final DirectionProperty FACING;

  public BaseChargingDockBlock(Properties properties) {
    super(properties);
    this.registerDefaultState(((BlockState)this.stateDefinition.any()).setValue(FACING, Direction.NORTH));
  }

  public void setFacing(Direction facing, Level world, BlockPos pos) {
    world.setBlockAndUpdate(pos, (BlockState)world.getBlockState(pos).setValue(FACING, facing));

  }

  public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
    super.setPlacedBy(worldIn, pos, state, placer, stack);
    this.setFacing(placer.getDirection().getOpposite(), worldIn, pos);
  }

  @Override
  public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
    return SHAPE;
  }

  @Override
  public BlockState getStateForPlacement(BlockPlaceContext context) {
    return (BlockState) this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
  }

  @Override
  public BlockState rotate(BlockState state, Rotation rotation) {
    return (BlockState)state.setValue(FACING, rotation.rotate((Direction)state.getValue(FACING)));
  }

  @Override
  public BlockState mirror(BlockState state, Mirror mirror) {
    return state.rotate(mirror.getRotation((Direction)state.getValue(FACING)));
  }

  protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
    builder.add(FACING);
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

  static {
    FACING = HorizontalDirectionalBlock.FACING;
  }
}
