package net.glasslauncher.mixin.modloader;

import net.glasslauncher.modloader.ModLoader;
import net.minecraft.container.slot.FurnaceOutput;
import net.minecraft.container.slot.Slot;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(FurnaceOutput.class)
public abstract class MixinSlotFurnace {

    @Shadow
    private PlayerBase player;

    @Shadow
    public abstract void onCrafted(ItemInstance itemStack);

    @Redirect(method = "onCrafted", at = @At(target = "Lnet/minecraft/item/ItemInstance;onCrafted(Lnet/minecraft/level/Level;Lnet/minecraft/entity/player/PlayerBase;)V", value = "INVOKE"))
    public void onPickupFromSlot(ItemInstance itemInstance, Level arg, PlayerBase arg1) {
        onCrafted(itemInstance);
        ModLoader.takenFromFurnace(player, itemInstance);
    }
}
