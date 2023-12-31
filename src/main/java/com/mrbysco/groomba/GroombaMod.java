package com.mrbysco.groomba;

import com.mojang.logging.LogUtils;
import com.mrbysco.groomba.client.ClientHandler;
import com.mrbysco.groomba.registry.GroombaRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(GroombaMod.MOD_ID)
public class GroombaMod {
	public static final String MOD_ID = "groomba";
	private static final Logger LOGGER = LogUtils.getLogger();

	public static TagKey<Block> CUTTABLE = BlockTags.create(new ResourceLocation(MOD_ID, "cuttable"));

	public GroombaMod() {
		IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

		GroombaRegistry.ITEMS.register(eventBus);
		GroombaRegistry.ENTITY_TYPES.register(eventBus);
		GroombaRegistry.SOUND_EVENTS.register(eventBus);

		eventBus.addListener(GroombaRegistry::registerEntityAttributes);
		eventBus.addListener(this::addTabContents);

		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
			eventBus.addListener(ClientHandler::registerEntityRenders);
			eventBus.addListener(ClientHandler::registerLayerDefinitions);
		});
	}

	private void addTabContents(final BuildCreativeModeTabContentsEvent event) {
		if (event.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
			event.accept(GroombaRegistry.GROOMBA_ITEM);
		}
	}
}
