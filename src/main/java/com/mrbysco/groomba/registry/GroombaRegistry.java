package com.mrbysco.groomba.registry;

import com.mrbysco.groomba.GroombaMod;
import com.mrbysco.groomba.entity.Groomba;
import com.mrbysco.groomba.item.GroombaItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class GroombaRegistry {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, GroombaMod.MOD_ID);
	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, GroombaMod.MOD_ID);
	public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(Registries.SOUND_EVENT, GroombaMod.MOD_ID);

	public static final RegistryObject<EntityType<Groomba>> GROOMBA = ENTITY_TYPES.register("groomba", () ->
			EntityType.Builder.<Groomba>of(Groomba::new, MobCategory.CREATURE)
					.sized(0.99F, 0.99F).clientTrackingRange(10).build("groomba"));

	public static final RegistryObject<SoundEvent> GROOMBA_CUTTING = SOUND_EVENTS.register("groomba.cutting", () ->
			SoundEvent.createVariableRangeEvent(new ResourceLocation(GroombaMod.MOD_ID, "groomba.cutting")));

	public static final RegistryObject<SoundEvent> GROOMBA_CUTTING2 = SOUND_EVENTS.register("groomba.cutting2", () ->
			SoundEvent.createVariableRangeEvent(new ResourceLocation(GroombaMod.MOD_ID, "groomba.cutting2")));

	public static final RegistryObject<Item> GROOMBA_ITEM = ITEMS.register("groomba", () -> new GroombaItem(new Item.Properties()));

	public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
		event.put(GROOMBA.get(), Groomba.createAttributes().build());
	}
}
