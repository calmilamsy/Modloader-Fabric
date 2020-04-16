package net.glasslauncher.mixin;

import net.minecraft.src.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(GuiScreen.class)
public interface GuiScreenAccessor {
    @Accessor
    List getControlList();
}
