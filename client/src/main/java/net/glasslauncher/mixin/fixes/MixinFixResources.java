package net.glasslauncher.mixin.fixes;

import net.minecraft.src.ThreadDownloadResources;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ThreadDownloadResources.class)
public class MixinFixResources {

    @ModifyConstant(method = "run", remap = false, constant = @Constant(stringValue = "http://s3.amazonaws.com/MinecraftResources/"))
    private String fixResourcesURL(String url) {
        try {
            return "https://resourceproxy.pymcl.net/MinecraftResources/";
        } catch (Exception e) {
            e.printStackTrace();
            return url;
        }
    }
}