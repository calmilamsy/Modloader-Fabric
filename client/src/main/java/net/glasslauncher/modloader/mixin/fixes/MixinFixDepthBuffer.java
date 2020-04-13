package net.glasslauncher.modloader.mixin.fixes;

import net.minecraft.client.Minecraft;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.PixelFormat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Minecraft.class)
public class MixinFixDepthBuffer {

    @Redirect(method = "startGame", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/Display;create()V", remap = false))
    private void fixDepthBuffer() throws LWJGLException {

        PixelFormat pixelformat = new PixelFormat();
        pixelformat = pixelformat.withDepthBits(24);
        Display.create(pixelformat);
    }
}
