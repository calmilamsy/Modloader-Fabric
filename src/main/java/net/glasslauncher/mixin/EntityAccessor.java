package net.glasslauncher.mixin;

import net.minecraft.entity.EntityBase;
import org.checkerframework.checker.units.qual.A;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(EntityBase.class)
public interface EntityAccessor {
    @Accessor
    float getFallDistance();
}
