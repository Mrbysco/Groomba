package com.mrbysco.groomba.entity.goal;

import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class RemoveTaggedGoal extends MoveInsideBlockGoal {
	private final TagKey<Block> blockTagToRemove;
	private final Mob removerMob;
	private int ticksSinceReachedGoal;
	private static final int WAIT_AFTER_BLOCK_FOUND = 20;

	public RemoveTaggedGoal(TagKey<Block> tagKey, PathfinderMob pathfinderMob, double speedModifier, int searchRange) {
		super(pathfinderMob, speedModifier, 24, searchRange);
		this.blockTagToRemove = tagKey;
		this.removerMob = pathfinderMob;
	}

	public boolean canUse() {
		if (!net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.removerMob.level(), this.removerMob)) {
			return false;
		} else if (this.nextStartTick > 0) {
			--this.nextStartTick;
			return false;
		} else if (this.findNearestBlock()) {
			this.nextStartTick = reducedTickDelay(WAIT_AFTER_BLOCK_FOUND);
			return true;
		} else {
			this.nextStartTick = this.nextStartTick(this.mob);
			return false;
		}
	}

	public void stop() {
		super.stop();
		this.removerMob.fallDistance = 1.0F;
	}

	public void start() {
		super.start();
		this.ticksSinceReachedGoal = 0;
	}

	public void playDestroyProgressSound(LevelAccessor levelAccessor, BlockPos pos) {
	}

	public void playBreakSound(Level level, BlockPos pos) {
	}

	public void tick() {
		super.tick();
		Level level = this.removerMob.level();
		BlockPos pos = this.removerMob.blockPosition();
		BlockPos blockPos = this.getPosWithBlock(pos, level);
		RandomSource randomsource = this.removerMob.getRandom();
		if (this.isReachedTarget() && blockPos != null) {
			if (this.ticksSinceReachedGoal > 0) {
				Vec3 vec3 = this.removerMob.getDeltaMovement();
				this.removerMob.setDeltaMovement(vec3.x, 0.15D, vec3.z);
				if (!level.isClientSide) {
					double d0 = 0.08D;
					((ServerLevel) level).sendParticles(new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(Items.BAMBOO)),
							(double) blockPos.getX() + 0.5D, (double) blockPos.getY() + 0.7D, (double) blockPos.getZ() + 0.5D, 3,
							((double) randomsource.nextFloat() - 0.5D) * d0,
							((double) randomsource.nextFloat() - 0.5D) * d0,
							((double) randomsource.nextFloat() - 0.5D) * d0, (double) 0.15F);
				}
			}

			if (this.ticksSinceReachedGoal % 2 == 0) {
				Vec3 deltaMovement = this.removerMob.getDeltaMovement();
				this.removerMob.setDeltaMovement(deltaMovement.x, -0.3D, deltaMovement.z);
				if (this.ticksSinceReachedGoal % 6 == 0) {
					this.playDestroyProgressSound(level, this.blockPos);
				}
			}

			if (this.ticksSinceReachedGoal > 60) {
				level.removeBlock(blockPos, false);
				if (!level.isClientSide) {
					for (int i = 0; i < 20; ++i) {
						double d3 = randomsource.nextGaussian() * 0.02D;
						double d1 = randomsource.nextGaussian() * 0.02D;
						double d2 = randomsource.nextGaussian() * 0.02D;
						((ServerLevel) level).sendParticles(ParticleTypes.POOF,
								(double) blockPos.getX() + 0.5D, (double) blockPos.getY(), (double) blockPos.getZ() + 0.5D,
								1, d3, d1, d2, (double) 0.15F);
					}

					this.playBreakSound(level, blockPos);
				}
			}

			++this.ticksSinceReachedGoal;
		}

	}

	@Nullable
	private BlockPos getPosWithBlock(BlockPos pos, BlockGetter blockGetter) {
		if (blockGetter.getBlockState(pos).is(this.blockTagToRemove)) {
			return pos;
		} else {
			BlockPos[] ablockpos = new BlockPos[]{pos.below(), pos.west(), pos.east(), pos.north(), pos.south(), pos.below().below()};

			for (BlockPos blockpos : ablockpos) {
				if (blockGetter.getBlockState(blockpos).is(this.blockTagToRemove)) {
					return blockpos;
				}
			}

			return null;
		}
	}

	protected boolean isValidTarget(LevelReader levelReader, BlockPos pos) {
		ChunkAccess chunkaccess = levelReader.getChunk(SectionPos.blockToSectionCoord(pos.getX()),
				SectionPos.blockToSectionCoord(pos.getZ()), ChunkStatus.FULL, false);
		if (chunkaccess == null) {
			return false;
		} else {
			if (!chunkaccess.getBlockState(pos).canEntityDestroy(levelReader, pos, this.removerMob)) return false;
			return chunkaccess.getBlockState(pos).is(this.blockTagToRemove) &&
					chunkaccess.getBlockState(pos.above()).isAir() && chunkaccess.getBlockState(pos.above(2)).isAir();
		}
	}
}