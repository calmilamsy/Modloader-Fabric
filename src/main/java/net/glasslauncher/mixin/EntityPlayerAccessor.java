package net.glasslauncher.mixin;

import net.minecraft.entity.player.PlayerBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(PlayerBase.class)
public interface EntityPlayerAccessor {
    @Accessor
    boolean isSleeping();
}
