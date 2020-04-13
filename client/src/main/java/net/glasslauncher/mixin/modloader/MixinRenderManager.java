package net.glasslauncher.mixin.modloader;

import net.glasslauncher.modloader.ModLoader;
import net.minecraft.src.Render;
import net.minecraft.src.RenderManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Iterator;
import java.util.Map;

@Mixin(RenderManager.class)
public class MixinRenderManager {

    @Shadow
    private Map entityRenderMap;

    @Inject(method = "<init>()V", at = @At(value = "TAIL"))
    public void init(CallbackInfo ci) {

        Iterator iterator = entityRenderMap.values().iterator();

        ModLoader.addAllRenderers(entityRenderMap);
        while (iterator.hasNext()) {
            Render var2 = (Render) iterator.next();
            var2.setRenderManager((RenderManager) (Object) this);
        }
    }
}
