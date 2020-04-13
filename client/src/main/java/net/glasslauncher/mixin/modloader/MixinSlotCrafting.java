package net.glasslauncher.mixin.modloader;

import net.glasslauncher.modloader.ModLoader;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.SlotCrafting;
import net.minecraft.src.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(SlotCrafting.class)
public class MixinSlotCrafting {

    @Redirect(method = "onPickupFromSlot", at = @At(target = "Lnet/minecraft/src/ItemStack;onCrafting(Lnet/minecraft/src/World;Lnet/minecraft/src/EntityPlayer;)V", value = "INVOKE"))
    public void onCrafting(ItemStack itemStack, World world, EntityPlayer entityPlayer) {
        itemStack.onCrafting(world, entityPlayer);
        ModLoader.takenFromCrafting(entityPlayer, itemStack);
    }
}
