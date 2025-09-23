package com.mrbysco.groomba.item;

import com.mrbysco.groomba.entity.Groomba;
import com.mrbysco.groomba.registry.GroombaRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.HitResult.Type;
import net.neoforged.neoforge.common.util.FakePlayer;

public class GroombaItem extends Item {
	public GroombaItem(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResult use(Level level, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		HitResult hitResult = getPlayerPOVHitResult(level, player, Fluid.NONE);
		if (hitResult == null) {
			return InteractionResult.PASS;
		} else if (hitResult.getType() != Type.BLOCK) {
			return InteractionResult.PASS;
		} else {
			BlockHitResult blockTraceResult = (BlockHitResult) hitResult;
			BlockPos blockpos = blockTraceResult.getBlockPos();
			Groomba groomba = GroombaRegistry.GROOMBA.get().create(level, EntitySpawnReason.SPAWN_ITEM_USE);
			if (groomba != null) {
				groomba.teleportTo(blockpos.getX() + 0.5D, blockpos.getY() + 1, blockpos.getZ() + 0.5D);
				if (!(player instanceof FakePlayer)) {
					groomba.setOwnerId(player.getUUID());
				}
				level.addFreshEntity(groomba);
			}

			if (!player.isCreative()) {
				stack.shrink(1);
			}
			return InteractionResult.SUCCESS;
		}
	}
}
