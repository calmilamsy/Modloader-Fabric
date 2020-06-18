package net.glasslauncher.mixin.audiomod;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.sound.SoundHelper;
import net.minecraft.util.SoundMap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import paulscode.sound.SoundSystemConfig;

import java.io.File;
import java.io.IOException;

@Mixin(SoundHelper.class)
public abstract class MixinAudioMod {
    @Shadow
    private SoundMap soundMapMusic;
    @Shadow
    private SoundMap soundMapSounds;
    @Shadow
    private SoundMap soundMapStreaming;

    private SoundMap soundMapCave;

    @Environment(EnvType.CLIENT)
    @Inject(method = "<init>", at=@At(value = "RETURN"))
    private void initStuff(CallbackInfo ci) {
        soundMapCave = new SoundMap();
    }

    @Environment(EnvType.CLIENT)
    private static void loadModAudio(String folder, SoundMap array) {
        File base = new File(Minecraft.getGameDirectory(), folder);
        try {
            walkFolder(base, base, array);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Environment(EnvType.CLIENT)
    private static void walkFolder(File root, File folder, SoundMap array) throws IOException {
        if (folder.exists() || folder.mkdirs()) {

            File[] files = folder.listFiles();
            if (files != null && files.length > 0)
                for (File file : files) {
                    if (!file.getName().startsWith(".")) {
                        if (file.isDirectory()) {
                            walkFolder(root, file, array);
                        } else if (file.isFile()) {

                            String path = file.getPath().substring(root.getPath().length() + 1).replace('\\', '/');

                            array.method_959(path, file);
                        }
                    }
                }
        }
    }

    @Environment(EnvType.CLIENT)
    @Inject(method = "acceptOptions", at = @At(value = "TAIL"))
    private void loadModAudio(GameOptions paramkv, CallbackInfo ci) {
        loadModAudio("resources/mod/sound", this.soundMapSounds);
        loadModAudio("resources/mod/streaming", this.soundMapStreaming);
        loadModAudio("resources/mod/music", this.soundMapMusic);
        loadModAudio("resources/mod/cavemusic", this.soundMapCave);
    }

    @Environment(EnvType.CLIENT)
    @Redirect(method = "setLibsAndCodecs", at = @At(value = "INVOKE", target = "Lpaulscode/sound/SoundSystemConfig;addLibrary(Ljava/lang/Class;)V", remap = false))
    private void trySetModCodecs(Class aClass) {
        SoundSystemConfig.addLibrary(aClass);
        try {
            if (Class.forName("paulscode.sound.codecs.CodecIBXM") != null) {
                SoundSystemConfig.setCodec("xm", Class.forName("paulscode.sound.codecs.CodecIBXM"));
                SoundSystemConfig.setCodec("s3m", Class.forName("paulscode.sound.codecs.CodecIBXM"));
                SoundSystemConfig.setCodec("mod", Class.forName("paulscode.sound.codecs.CodecIBXM"));
            }
        } catch (ClassNotFoundException classNotFoundException) {
            classNotFoundException.printStackTrace();
        }
    }
}
