package net.glasslauncher.mixin.modloader;

import net.glasslauncher.modloader.ModLoader;
import net.minecraft.container.slot.CraftingResult;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(CraftingResult.class)
public class MixinSlotCrafting {

    @Redirect(method = "onCrafted", at = @At(target = "Lnet/minecraft/item/ItemInstance;onCrafted(Lnet/minecraft/level/Level;Lnet/minecraft/entity/player/PlayerBase;)V", value = "INVOKE"))
    public void onCrafting(ItemInstance itemStack, Level world, PlayerBase entityPlayer) {
        itemStack.onCrafted(world, entityPlayer);
        ModLoader.takenFromCrafting(entityPlayer, itemStack);
    }
}
