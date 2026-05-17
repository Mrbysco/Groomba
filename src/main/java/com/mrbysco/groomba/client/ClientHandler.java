package com.mrbysco.groomba.client;

import com.mrbysco.groomba.GroombaMod;
import com.mrbysco.groomba.client.model.GroombaModel;
import com.mrbysco.groomba.client.renderer.GroombaRenderer;
import com.mrbysco.groomba.registry.GroombaRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(Dist.CLIENT)
public class ClientHandler {
	public static final ModelLayerLocation GROOMBA = new ModelLayerLocation(GroombaMod.modLoc("groomba"), "main");

	@SubscribeEvent
	public static void registerEntityRenders(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(GroombaRegistry.GROOMBA.get(), GroombaRenderer::new);
	}

	@SubscribeEvent
	public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(GROOMBA, GroombaModel::createBodyLayer);
	}
}
