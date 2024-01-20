package com.mrbysco.groomba.registry;

import com.mrbysco.groomba.GroombaMod;
import com.mrbysco.groomba.entity.Groomba;
import com.mrbysco.groomba.item.GroombaItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class GroombaRegistry {
	public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(GroombaMod.MOD_ID);
	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, GroombaMod.MOD_ID);
	public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, GroombaMod.MOD_ID);

	public static final Supplier<EntityType<Groomba>> GROOMBA = ENTITY_TYPES.register("groomba", () ->
			EntityType.Builder.<Groomba>of(Groomba::new, MobCategory.CREATURE)
					.sized(0.99F, 0.99F).clientTrackingRange(10).build("groomba"));

	public static final DeferredHolder<SoundEvent, SoundEvent> GROOMBA_CUTTING = SOUND_EVENTS.register("groomba.cutting", () ->
			SoundEvent.createVariableRangeEvent(new ResourceLocation(GroombaMod.MOD_ID, "groomba.cutting")));

	public static final DeferredHolder<SoundEvent, SoundEvent> GROOMBA_CUTTING2 = SOUND_EVENTS.register("groomba.cutting2", () ->
			SoundEvent.createVariableRangeEvent(new ResourceLocation(GroombaMod.MOD_ID, "groomba.cutting2")));

	public static final DeferredItem<GroombaItem> GROOMBA_ITEM = ITEMS.register("groomba", () -> new GroombaItem(new Item.Properties()));

	public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
		event.put(GROOMBA.get(), Groomba.createAttributes().build());
	}
}
