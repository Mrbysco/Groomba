package com.mrbysco.groomba.item;

import com.mrbysco.groomba.entity.Groomba;
import com.mrbysco.groomba.registry.GroombaRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.HitResult.Type;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.event.EventHooks;

public class GroombaItem extends Item {
	public GroombaItem(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		HitResult hitResult = getPlayerPOVHitResult(level, player, Fluid.NONE);
		InteractionResultHolder<ItemStack> ret = EventHooks.onBucketUse(player, level, stack, hitResult);
		if (ret != null) return ret;

		if (hitResult == null) {
			return new InteractionResultHolder<>(InteractionResult.PASS, stack);
		} else if (hitResult.getType() != Type.BLOCK) {
			return new InteractionResultHolder<>(InteractionResult.PASS, stack);
		} else {
			BlockHitResult blockTraceResult = (BlockHitResult) hitResult;
			BlockPos blockpos = blockTraceResult.getBlockPos();
			Groomba groomba = GroombaRegistry.GROOMBA.get().create(level);
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
			return new InteractionResultHolder<>(InteractionResult.SUCCESS, stack);
		}
	}
}
