package net.glasslauncher.mixin.modloader;

import net.glasslauncher.modloader.ModLoader;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntityItem.class)
public abstract class MixinEntityItem {

    @Redirect(method = "onCollideWithPlayer", at = @At(target = "Lnet/minecraft/src/EntityPlayer;onItemPickup(Lnet/minecraft/src/Entity;I)V", value = "INVOKE"))
    public void onCollideWithPlayer(EntityPlayer entityPlayer, Entity entity, int i) {
        ModLoader.onItemPickup(entityPlayer, item);
        entityPlayer.onItemPickup(entity, i);
    }

    @Shadow
    public ItemStack item;
}
