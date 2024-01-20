package com.mrbysco.groomba.datagen.client;

import com.mrbysco.groomba.GroombaMod;
import com.mrbysco.groomba.registry.GroombaRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.registries.DeferredHolder;

public class GroombaLanguageProvider extends LanguageProvider {
	public GroombaLanguageProvider(PackOutput packOutput) {
		super(packOutput, GroombaMod.MOD_ID, "en_us");
	}

	@Override
	protected void addTranslations() {
		addItem(GroombaRegistry.GROOMBA_ITEM, "Groomba");
		addEntityType(GroombaRegistry.GROOMBA, "Groomba");

		addSubtitle(GroombaRegistry.GROOMBA_CUTTING, "Groomba cutting grass");
		addSubtitle(GroombaRegistry.GROOMBA_CUTTING2, "Groomba cutting grass");
	}

	/**
	 * Add a subtitle to a sound event
	 *
	 * @param sound The sound event registry object
	 * @param text  The subtitle text
	 */
	private void addSubtitle(DeferredHolder<SoundEvent, SoundEvent> sound, String text) {
		this.addSubtitle(sound.get(), text);
	}

	/**
	 * Add a subtitle to a sound event
	 *
	 * @param sound The sound event
	 * @param text  The subtitle text
	 */
	private void addSubtitle(SoundEvent sound, String text) {
		String path = GroombaMod.MOD_ID + ".subtitle." + sound.getLocation().getPath();
		this.add(path, text);
	}
}
