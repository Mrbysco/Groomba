package com.mrbysco.groomba.client;

import com.mrbysco.groomba.GroombaMod;
import com.mrbysco.groomba.client.model.GroombaModel;
import com.mrbysco.groomba.client.renderer.GroombaRenderer;
import com.mrbysco.groomba.registry.GroombaRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

public class ClientHandler {
	public static final ModelLayerLocation GROOMBA = new ModelLayerLocation(new ResourceLocation(GroombaMod.MOD_ID, "groomba"), "main");

	public static void registerEntityRenders(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(GroombaRegistry.GROOMBA.get(), GroombaRenderer::new);
	}

	public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(GROOMBA, GroombaModel::createBodyLayer);
	}
}
