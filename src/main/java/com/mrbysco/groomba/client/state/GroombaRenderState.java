package com.mrbysco.groomba.client.state;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.phys.Vec3;

public class GroombaRenderState extends LivingEntityRenderState {
	public Vec3 deltaMovement = Vec3.ZERO;
}
