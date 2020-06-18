package net.glasslauncher.mixin;

import net.minecraft.entity.Living;
import net.minecraft.entity.WalkingBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Living.class)
public interface EntityLivingAccessor {
    @Accessor
    boolean isJumping();
    @Accessor(value = "field_1029")
    float getMoveForward();
    @Accessor(value = "field_1060")
    float getMoveStrafing();
}
