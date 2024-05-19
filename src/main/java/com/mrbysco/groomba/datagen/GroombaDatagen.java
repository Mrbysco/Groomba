package com.mrbysco.groomba.datagen;

import com.mrbysco.groomba.datagen.client.GroombaItemModelProvider;
import com.mrbysco.groomba.datagen.client.GroombaLanguageProvider;
import com.mrbysco.groomba.datagen.client.GroombaSoundProvider;
import com.mrbysco.groomba.datagen.server.GroombaBlockTagProvider;
import com.mrbysco.groomba.datagen.server.GroombaLootProvider;
import com.mrbysco.groomba.datagen.server.GroombaRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class GroombaDatagen {
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		PackOutput packOutput = generator.getPackOutput();
		ExistingFileHelper helper = event.getExistingFileHelper();
		CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

		if (event.includeServer()) {
			generator.addProvider(true, new GroombaRecipeProvider(packOutput, lookupProvider));
			generator.addProvider(true, new GroombaLootProvider(packOutput, lookupProvider));
			generator.addProvider(true, new GroombaBlockTagProvider(packOutput, lookupProvider, helper));
		}
		if (event.includeClient()) {
			generator.addProvider(true, new GroombaLanguageProvider(packOutput));
			generator.addProvider(true, new GroombaSoundProvider(packOutput, helper));
			generator.addProvider(true, new GroombaItemModelProvider(packOutput, helper));
		}
	}
}
