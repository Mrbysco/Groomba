package com.mrbysco.groomba.datagen.client;

import com.mrbysco.groomba.GroombaMod;
import com.mrbysco.groomba.registry.GroombaRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;

public class GroombaSoundProvider extends SoundDefinitionsProvider {

	public GroombaSoundProvider(PackOutput packOutput) {
		super(packOutput, GroombaMod.MOD_ID);
	}

	@Override
	public void registerSounds() {
		this.add(GroombaRegistry.GROOMBA_CUTTING, definition()
				.subtitle(modSubtitle(GroombaRegistry.GROOMBA_CUTTING.getId()))
				.with(sound(modLoc("cutting"))));
	}

	private String modSubtitle(Identifier id) {
		return GroombaMod.MOD_ID + ".subtitle." + id.getPath();
	}

	private Identifier modLoc(String name) {
		return GroombaMod.modLoc(name);
	}
}
