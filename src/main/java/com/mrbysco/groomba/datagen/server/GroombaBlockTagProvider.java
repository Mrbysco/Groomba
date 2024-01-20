package com.mrbysco.groomba.datagen.server;

import com.mrbysco.groomba.GroombaMod;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class GroombaBlockTagProvider extends BlockTagsProvider {
	public GroombaBlockTagProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider,
								   ExistingFileHelper existingFileHelper) {
		super(packOutput, lookupProvider, GroombaMod.MOD_ID, existingFileHelper);
	}

	@Override
	public void addTags(HolderLookup.Provider lookupProvider) {
		this.tag(GroombaMod.CUTTABLE).add(Blocks.SHORT_GRASS, Blocks.TALL_GRASS, Blocks.FERN, Blocks.DEAD_BUSH);
	}
}
