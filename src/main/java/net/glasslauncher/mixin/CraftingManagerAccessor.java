package net.glasslauncher.mixin;

import net.minecraft.item.ItemInstance;
import net.minecraft.recipe.RecipeRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(RecipeRegistry.class)
public interface CraftingManagerAccessor {

    @Invoker
    void callAddShapelessRecipe(ItemInstance var1, Object... var2);

    @Invoker
    void callAddShapedRecipe(ItemInstance var1, Object... var2);
}
