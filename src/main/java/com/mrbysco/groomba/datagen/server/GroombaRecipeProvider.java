package com.mrbysco.groomba.datagen.server;

import com.mrbysco.groomba.registry.GroombaRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

public class GroombaRecipeProvider extends RecipeProvider {
	public GroombaRecipeProvider(PackOutput packOutput) {
		super(packOutput);
	}

	@Override
	protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, GroombaRegistry.GROOMBA_ITEM.get())
				.pattern("III")
				.pattern("WCW")
				.pattern("RSR")
				.define('I', Tags.Items.INGOTS_IRON)
				.define('W', ItemTags.PLANKS)
				.define('C', Tags.Items.COBBLESTONE)
				.define('R', Tags.Items.DUSTS_REDSTONE)
				.define('S', Tags.Items.SHEARS)
				.unlockedBy("has_iron_ingot", has(Tags.Items.INGOTS_IRON))
				.unlockedBy("has_planks", has(ItemTags.PLANKS))
				.unlockedBy("has_cobble", has(Tags.Items.COBBLESTONE))
				.unlockedBy("has_redstone", has(Tags.Items.DUSTS_REDSTONE))
				.unlockedBy("has_shears", has(Tags.Items.SHEARS))
				.save(consumer);
	}
}
