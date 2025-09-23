package com.mrbysco.groomba.datagen.server;

import com.mrbysco.groomba.registry.GroombaRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.tags.ItemTags;
import net.neoforged.neoforge.common.Tags;

import java.util.concurrent.CompletableFuture;

public class GroombaRecipeProvider extends RecipeProvider {
	public GroombaRecipeProvider(HolderLookup.Provider provider, RecipeOutput recipeOutput) {
		super(provider, recipeOutput);
	}

	@Override
	protected void buildRecipes() {
		shaped(RecipeCategory.MISC, GroombaRegistry.GROOMBA_ITEM.get())
				.pattern("III")
				.pattern("WCW")
				.pattern("RSR")
				.define('I', Tags.Items.INGOTS_IRON)
				.define('W', ItemTags.PLANKS)
				.define('C', Tags.Items.COBBLESTONES)
				.define('R', Tags.Items.DUSTS_REDSTONE)
				.define('S', Tags.Items.TOOLS_SHEAR)
				.unlockedBy("has_iron_ingot", has(Tags.Items.INGOTS_IRON))
				.unlockedBy("has_planks", has(ItemTags.PLANKS))
				.unlockedBy("has_cobble", has(Tags.Items.COBBLESTONES))
				.unlockedBy("has_redstone", has(Tags.Items.DUSTS_REDSTONE))
				.unlockedBy("has_shears", has(Tags.Items.TOOLS_SHEAR))
				.save(output);
	}

	public static class Runner extends RecipeProvider.Runner {
		public Runner(PackOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
			super(output, completableFuture);
		}

		@Override
		protected RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput recipeOutput) {
			return new GroombaRecipeProvider(provider, recipeOutput);
		}

		@Override
		public String getName() {
			return "Groomba Recipes";
		}
	}
}
