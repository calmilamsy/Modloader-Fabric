package net.glasslauncher.mixin.fixes;

import net.minecraft.network.ClientPlayNetworkHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ClientPlayNetworkHandler.class)
public class MixinFixAuth {

    @ModifyConstant(method = "handleHandshake", constant = @Constant(stringValue = "http://www.minecraft.net/game/joinserver.jsp?user="))
    private String fixAuth(String url) {
        return "http://session.minecraft.net/game/joinserver.jsp?user=";
    }
}
