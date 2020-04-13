package net.glasslauncher.modloader.mixin.fixes;

import net.glasslauncher.modloader.cachemanager.web.HttpSkinHandler;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerSP;
import net.minecraft.src.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityPlayerSP.class)
public class MixinFixPlayerSkins extends EntityPlayer {

    @Inject(method = "<init>", at = @At(value = "RETURN"))
    public void fixURLS(CallbackInfo callbackInfo) {
        try {
            if ((cloakUrl != null && cloakUrl.contains("s3.amazonaws.com/MinecraftCloaks/")) || skinUrl.contains("s3.amazonaws.com/MinecraftSkins/")) {
                String[] imageUrls = HttpSkinHandler.getImages(skinUrl.replaceAll("https?://s3\\.amazonaws\\.com/MinecraftSkins/", "").replace(".png", ""));
                skinUrl = (!imageUrls[0].equals("")) ? imageUrls[0] : skinUrl;
                cloakUrl = (!imageUrls[1].equals("")) ? imageUrls[1] : cloakUrl;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public MixinFixPlayerSkins(World world) {
        super(world);
    }

    @Override
    public void func_6420_o() {
    }
}
