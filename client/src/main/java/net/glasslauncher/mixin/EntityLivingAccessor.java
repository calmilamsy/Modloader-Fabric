package net.glasslauncher.mixin;

import net.minecraft.src.EntityLiving;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(EntityLiving.class)
public interface EntityLivingAccessor {
    @Accessor
    boolean isIsJumping();
    @Accessor
    float getMoveForward();
    @Accessor
    float getMoveStrafing();
}
