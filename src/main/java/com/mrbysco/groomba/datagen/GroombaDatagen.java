package com.mrbysco.groomba.datagen;

import com.mrbysco.groomba.datagen.client.GroombaLanguageProvider;
import com.mrbysco.groomba.datagen.client.GroombaModelProvider;
import com.mrbysco.groomba.datagen.client.GroombaSoundProvider;
import com.mrbysco.groomba.datagen.server.GroombaBlockTagProvider;
import com.mrbysco.groomba.datagen.server.GroombaLootProvider;
import com.mrbysco.groomba.datagen.server.GroombaRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber
public class GroombaDatagen {
	@SubscribeEvent
	public static void gatherData(GatherDataEvent.Client event) {
		DataGenerator generator = event.getGenerator();
		PackOutput packOutput = generator.getPackOutput();
		CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

		generator.addProvider(true, new GroombaRecipeProvider.Runner(packOutput, lookupProvider));
		generator.addProvider(true, new GroombaLootProvider(packOutput, lookupProvider));
		generator.addProvider(true, new GroombaBlockTagProvider(packOutput, lookupProvider));

		generator.addProvider(true, new GroombaLanguageProvider(packOutput));
		generator.addProvider(true, new GroombaSoundProvider(packOutput));
		generator.addProvider(true, new GroombaModelProvider(packOutput));

	}
}
