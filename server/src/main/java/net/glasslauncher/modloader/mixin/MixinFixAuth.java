package net.glasslauncher.modloader.mixin;

import net.minecraft.src.NetLoginHandler;
import net.minecraft.src.Packet1Login;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;

@Mixin(targets = {"net.minecraft.src.ThreadLoginVerifier"})
public class MixinFixAuth {

    public MixinFixAuth(NetLoginHandler loginHandler, Packet1Login loginPacket) {
        this.loginPacket = loginPacket;
        this.loginHandler = loginHandler;
    }

    @Inject(method = "run", at = @At(value = "HEAD"), remap = false)
    public void fixAuth(CallbackInfo ci) {
        System.out.println("Test");
        try {
            String s = (String) NetLoginHandler.class.getMethod("getServerId").invoke(null, loginHandler);
            URL url = new URL((new StringBuilder()).append("http://www.minecraft.net/game/checkserver.jsp?user=").append(URLEncoder.encode(loginPacket.username, "UTF-8")).append("&serverId=").append(URLEncoder.encode(s, "UTF-8")).toString());
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(url.openStream()));
            String s1 = bufferedreader.readLine();
            bufferedreader.close();
            if(s1.equals("YES")) {
                NetLoginHandler.class.getMethod("setLoginPacket").invoke(null, loginHandler, loginPacket);
            }
            else {
                loginHandler.kickUser("Failed to verify username!");
            }
        }
        catch(Exception exception) {
            loginHandler.kickUser((new StringBuilder()).append("Failed to verify username! [internal error ").append(exception).append("]").toString());
            exception.printStackTrace();
        }
    }

    @Shadow(aliases = "loginPacket")
    Packet1Login loginPacket;
    @Shadow(aliases = "loginHandler")
    NetLoginHandler loginHandler;
}
