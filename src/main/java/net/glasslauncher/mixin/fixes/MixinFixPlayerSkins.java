package net.glasslauncher.mixin.fixes;

import net.glasslauncher.cachemanager.web.HttpSkinHandler;
import net.minecraft.entity.player.ClientPlayer;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayer.class)
public class MixinFixPlayerSkins extends PlayerBase {

    public MixinFixPlayerSkins(Level world) {
        super(world);
    }

    @Override
    public void method_494() {}

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
}
