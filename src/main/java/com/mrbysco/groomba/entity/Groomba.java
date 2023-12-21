package com.mrbysco.groomba.entity;

import com.mrbysco.groomba.GroombaMod;
import com.mrbysco.groomba.entity.goal.RemoveTaggedGoal;
import com.mrbysco.groomba.registry.GroombaRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.players.OldUsersConverter;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

public class Groomba extends PathfinderMob {
	protected static final EntityDataAccessor<Optional<UUID>> OWNER_UNIQUE_ID = SynchedEntityData.defineId(Groomba.class, EntityDataSerializers.OPTIONAL_UUID);

	public Groomba(EntityType<? extends PathfinderMob> type, Level level) {
		super(type, level);
	}

	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(OWNER_UNIQUE_ID, Optional.empty());
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(2, new Groomba.AttackGrassGoal(this, 1.0D, 8));
		this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 0.8D, 1.0000001E-5F));
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 8.0D)
				.add(Attributes.FOLLOW_RANGE, 35.0D).add(Attributes.MOVEMENT_SPEED, (double) 0.25F);
	}

	@Nullable
	public UUID getOwnerId() {
		return this.entityData.get(OWNER_UNIQUE_ID).orElse((UUID) null);
	}

	public void setOwnerId(@Nullable UUID uuid) {
		this.entityData.set(OWNER_UNIQUE_ID, Optional.ofNullable(uuid));
	}

	@Nullable
	public LivingEntity getOwner() {
		try {
			UUID uuid = this.getOwnerId();
			return uuid == null ? null : this.level().getPlayerByUUID(uuid);
		} catch (IllegalArgumentException illegalargumentexception) {
			return null;
		}
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);

		if (this.getOwnerId() != null) {
			compound.putUUID("Owner", this.getOwnerId());
		}
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);

		UUID uuid;
		if (compound.hasUUID("Owner")) {
			uuid = compound.getUUID("Owner");
		} else {
			String s = compound.getString("Owner");
			uuid = OldUsersConverter.convertMobOwnerIfNecessary(this.getServer(), s);
		}

		if (uuid != null) {
			this.setOwnerId(uuid);
		}
	}

	@Override
	public Iterable<ItemStack> getArmorSlots() {
		return new ArrayList<>();
	}

	@Override
	public ItemStack getItemBySlot(EquipmentSlot slot) {
		return new ItemStack(Items.SHEARS);
	}

	@Override
	public void setItemSlot(EquipmentSlot slot, ItemStack stack) {

	}

	@Override
	public HumanoidArm getMainArm() {
		return HumanoidArm.RIGHT;
	}

	class AttackGrassGoal extends RemoveTaggedGoal {
		AttackGrassGoal(PathfinderMob mob, double speedModifier, int searchRange) {
			super(GroombaMod.CUTTABLE, mob, speedModifier, searchRange);
		}

		@Override
		public void playBreakSound(Level level, BlockPos pos) {
			level.playSound((Player) null, pos, GroombaRegistry.GROOMBA_CUTTING.get(), SoundSource.NEUTRAL,
					1.0F, 0.9F + Groomba.this.random.nextFloat() * 0.2F);
		}

		public double acceptedDistance() {
			return 0.75D;
		}
	}
}
