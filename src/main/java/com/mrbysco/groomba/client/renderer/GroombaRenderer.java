package com.mrbysco.groomba.client.renderer;

import com.mrbysco.groomba.GroombaMod;
import com.mrbysco.groomba.client.ClientHandler;
import com.mrbysco.groomba.client.model.GroombaModel;
import com.mrbysco.groomba.client.state.GroombaRenderState;
import com.mrbysco.groomba.entity.Groomba;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class GroombaRenderer extends MobRenderer<Groomba, GroombaRenderState, GroombaModel> {
	private static final ResourceLocation PADORU_TEXTURES = ResourceLocation.fromNamespaceAndPath(GroombaMod.MOD_ID, "textures/entity/groomba.png");

	public GroombaRenderer(EntityRendererProvider.Context context) {
		super(context, new GroombaModel(context.bakeLayer(ClientHandler.GROOMBA)), 0.5F);
	}

	@Override
	public GroombaRenderState createRenderState() {
		return new GroombaRenderState();
	}

	@Override
	public void extractRenderState(Groomba groomba, GroombaRenderState renderState, float partialTick) {
		super.extractRenderState(groomba, renderState, partialTick);
		renderState.deltaMovement = groomba.getDeltaMovement();
	}

	@Override
	public ResourceLocation getTextureLocation(GroombaRenderState renderState) {
		return PADORU_TEXTURES;
	}
}
