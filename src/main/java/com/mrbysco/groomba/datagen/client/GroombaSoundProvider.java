package com.mrbysco.groomba.datagen.client;

import com.mrbysco.groomba.GroombaMod;
import com.mrbysco.groomba.registry.GroombaRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SoundDefinitionsProvider;

public class GroombaSoundProvider extends SoundDefinitionsProvider {

	public GroombaSoundProvider(PackOutput packOutput, ExistingFileHelper helper) {
		super(packOutput, GroombaMod.MOD_ID, helper);
	}

	@Override
	public void registerSounds() {
		this.add(GroombaRegistry.GROOMBA_CUTTING, definition()
				.subtitle(modSubtitle(GroombaRegistry.GROOMBA_CUTTING.getId()))
				.with(sound(modLoc("cutting"))));
	}

	private String modSubtitle(ResourceLocation id) {
		return GroombaMod.MOD_ID + ".subtitle." + id.getPath();
	}

	private ResourceLocation modLoc(String name) {
		return new ResourceLocation(GroombaMod.MOD_ID, name);
	}
}
