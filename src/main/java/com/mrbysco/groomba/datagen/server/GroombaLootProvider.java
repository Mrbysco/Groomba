package com.mrbysco.groomba.datagen.server;

import com.mrbysco.groomba.registry.GroombaRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public class GroombaLootProvider extends LootTableProvider {
	public GroombaLootProvider(PackOutput packOutput) {
		super(packOutput, Set.of(), List.of(new SubProviderEntry(PaperEntityLoot::new, LootContextParamSets.ENTITY)));
	}

	private static class PaperEntityLoot extends EntityLootSubProvider {
		protected PaperEntityLoot() {
			super(FeatureFlags.REGISTRY.allFlags());
		}

		@Override
		public void generate() {
			this.add(GroombaRegistry.GROOMBA.get(), LootTable.lootTable()
					.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
							.add(LootItem.lootTableItem(GroombaRegistry.GROOMBA_ITEM.get()))
					)
			);
		}

		@Override
		protected Stream<EntityType<?>> getKnownEntityTypes() {
			return GroombaRegistry.ENTITY_TYPES.getEntries().stream().map(RegistryObject::get);
		}
	}

	@Override
	protected void validate(Map<ResourceLocation, LootTable> map, @Nonnull ValidationContext context) {

	}
}
