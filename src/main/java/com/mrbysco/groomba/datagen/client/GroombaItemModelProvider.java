package com.mrbysco.groomba.datagen.client;

import com.mrbysco.groomba.GroombaMod;
import com.mrbysco.groomba.registry.GroombaRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class GroombaItemModelProvider extends ItemModelProvider {
	public GroombaItemModelProvider(PackOutput packOutput, ExistingFileHelper helper) {
		super(packOutput, GroombaMod.MOD_ID, helper);
	}


	@Override
	protected void registerModels() {
		this.generatedItem(GroombaRegistry.GROOMBA_ITEM.getId());
	}

	private void generatedItem(ResourceLocation location) {
		singleTexture(location.getPath(), new ResourceLocation("item/generated"),
				"layer0", new ResourceLocation(GroombaMod.MOD_ID, "item/" + location.getPath()));
	}
}
