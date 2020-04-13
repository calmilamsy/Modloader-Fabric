package net.glasslauncher.modloader.mixin;

import net.minecraft.src.CraftingManager;
import net.minecraft.src.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(CraftingManager.class)
public interface CraftingManagerAccessor {

    @Invoker
    void callAddShapelessRecipe(ItemStack var1, Object... var2);

    @Invoker
    void callAddRecipe(ItemStack var1, Object... var2);

}
