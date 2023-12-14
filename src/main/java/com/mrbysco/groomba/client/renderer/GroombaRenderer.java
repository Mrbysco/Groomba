package com.mrbysco.groomba.client.renderer;

import com.mrbysco.groomba.GroombaMod;
import com.mrbysco.groomba.client.ClientHandler;
import com.mrbysco.groomba.client.model.GroombaModel;
import com.mrbysco.groomba.entity.Groomba;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public class GroombaRenderer extends MobRenderer<Groomba, GroombaModel> {
	private static final ResourceLocation PADORU_TEXTURES = new ResourceLocation(GroombaMod.MOD_ID, "textures/entity/groomba.png");

	public GroombaRenderer(EntityRendererProvider.Context context) {
		super(context, new GroombaModel(context.bakeLayer(ClientHandler.GROOMBA)), 0.5F);
	}

	@Nullable
	@Override
	public ResourceLocation getTextureLocation(Groomba entity) {
		return PADORU_TEXTURES;
	}
}
