package com.mrbysco.groomba.registry;

import com.mrbysco.groomba.GroombaMod;
import com.mrbysco.groomba.entity.Groomba;
import com.mrbysco.groomba.item.GroombaItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class GroombaRegistry {
	public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(GroombaMod.MOD_ID);
	public static final DeferredRegister.Entities ENTITIES = DeferredRegister.createEntities(GroombaMod.MOD_ID);
	public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, GroombaMod.MOD_ID);

	public static final Supplier<EntityType<Groomba>> GROOMBA = ENTITIES.registerEntityType("groomba",
			Groomba::new,
			MobCategory.CREATURE,
			builder -> builder
					.sized(0.95F, 0.95F)
					.clientTrackingRange(10)
	);

	public static final DeferredHolder<SoundEvent, SoundEvent> GROOMBA_CUTTING = SOUND_EVENTS.register("groomba.cutting", () ->
			SoundEvent.createVariableRangeEvent(GroombaMod.modLoc("groomba.cutting")));

	public static final DeferredHolder<SoundEvent, SoundEvent> GROOMBA_CUTTING2 = SOUND_EVENTS.register("groomba.cutting2", () ->
			SoundEvent.createVariableRangeEvent(GroombaMod.modLoc("groomba.cutting2")));

	public static final DeferredItem<GroombaItem> GROOMBA_ITEM = ITEMS.registerItem("groomba", GroombaItem::new);

	public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
		event.put(GROOMBA.get(), Groomba.createAttributes().build());
	}
}
