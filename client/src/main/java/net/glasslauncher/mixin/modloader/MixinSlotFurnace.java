package net.glasslauncher.mixin.modloader;

import net.glasslauncher.modloader.ModLoader;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Slot;
import net.minecraft.src.SlotFurnace;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(SlotFurnace.class)
public abstract class MixinSlotFurnace {

    @Shadow
    private EntityPlayer thePlayer;

    @Shadow
    public abstract void onPickupFromSlot(ItemStack itemStack);

    @Redirect(method = "onPickupFromSlot", at = @At(target = "Lnet/minecraft/src/Slot;onPickupFromSlot(Lnet/minecraft/src/ItemStack;)V", value = "INVOKE"))
    public void onPickupFromSlot(Slot slot, ItemStack itemStack) {
        ModLoader.takenFromFurnace(thePlayer, itemStack);
        onPickupFromSlot(itemStack);
    }
}
