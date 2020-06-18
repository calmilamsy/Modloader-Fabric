package net.glasslauncher.mixin.modloader;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.glasslauncher.modloader.ModLoader;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Iterator;
import java.util.Map;

@Mixin(EntityRenderDispatcher.class)
public class MixinRenderManager {

    @Shadow private Map renderers;

    @Environment(EnvType.CLIENT)
    @Inject(method = "<init>()V", at = @At(value = "TAIL"))
    public void init(CallbackInfo ci) {
        ModLoader.addAllRenderers(renderers);

        Iterator iterator = renderers.values().iterator();

        while (iterator.hasNext()) {
            EntityRenderer var2 = (EntityRenderer) iterator.next();
            var2.setDispatcher((EntityRenderDispatcher) (Object) this);
        }
    }
}
