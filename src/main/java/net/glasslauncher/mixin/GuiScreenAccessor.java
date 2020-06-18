package net.glasslauncher.mixin;

import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.InGame;
import net.minecraft.client.gui.screen.ScreenBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(ScreenBase.class)
public interface GuiScreenAccessor {
    @Accessor(value = "buttons")
    List getControlList();
}
