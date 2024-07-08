package com.mrbysco.groomba.datagen.server;

import com.mrbysco.groomba.registry.GroombaRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.WritableRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public class GroombaLootProvider extends LootTableProvider {
	public GroombaLootProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
		super(packOutput, Set.of(), List.of(new SubProviderEntry(GroombaEntityLoot::new, LootContextParamSets.ENTITY)), lookupProvider);
	}

	private static class GroombaEntityLoot extends EntityLootSubProvider {
		protected GroombaEntityLoot(HolderLookup.Provider provider) {
			super(FeatureFlags.REGISTRY.allFlags(), provider);
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
			return GroombaRegistry.ENTITY_TYPES.getEntries().stream().map(DeferredHolder::get);
		}
	}

	@Override
	protected void validate(WritableRegistry<LootTable> writableregistry, ValidationContext validationcontext, ProblemReporter.Collector problemreporter$collector) {
		super.validate(writableregistry, validationcontext, problemreporter$collector);
	}
}
