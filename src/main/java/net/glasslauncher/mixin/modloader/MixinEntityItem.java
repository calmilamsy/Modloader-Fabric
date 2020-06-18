package net.glasslauncher.mixin.modloader;

import net.glasslauncher.modloader.ModLoader;
import net.minecraft.entity.EntityBase;
import net.minecraft.entity.Item;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Item.class)
public abstract class MixinEntityItem {

    @Shadow
    public ItemInstance item;

    @Redirect(method = "onPlayerCollision", at = @At(target = "Lnet/minecraft/entity/player/PlayerBase;method_491(Lnet/minecraft/entity/EntityBase;I)V", value = "INVOKE"))
    public void onCollideWithPlayer(PlayerBase entityPlayer, EntityBase entity, int i) {
        ModLoader.onItemPickup(entityPlayer, item);
        entityPlayer.method_491(entity, i);
    }
}
