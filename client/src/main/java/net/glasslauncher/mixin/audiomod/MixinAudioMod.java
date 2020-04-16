package net.glasslauncher.mixin.audiomod;

import net.minecraft.client.Minecraft;
import net.minecraft.src.GameSettings;
import net.minecraft.src.SoundManager;
import net.minecraft.src.SoundPool;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import paulscode.sound.SoundSystemConfig;

import java.io.File;
import java.io.IOException;

@Mixin(SoundManager.class)
public abstract class MixinAudioMod {
    @Shadow
    private SoundPool soundPoolMusic;
    @Shadow
    private SoundPool soundPoolSounds;
    @Shadow
    private SoundPool soundPoolStreaming;

    private SoundPool soundPoolCave = new SoundPool();

    private static void loadModAudio(String folder, SoundPool array) {
        File base = new File(Minecraft.getMinecraftDir(), folder);
        try {
            walkFolder(base, base, array);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void walkFolder(File root, File folder, SoundPool array) throws IOException {
        if (folder.exists() || folder.mkdirs()) {

            File[] files = folder.listFiles();
            if (files != null && files.length > 0)
                for (File file : files) {
                    if (!file.getName().startsWith(".")) {
                        if (file.isDirectory()) {
                            walkFolder(root, file, array);
                        } else if (file.isFile()) {

                            String path = file.getPath().substring(root.getPath().length() + 1).replace('\\', '/');

                            array.addSound(path, file);
                        }
                    }
                }
        }
    }

    @Inject(method = "loadSoundSettings", at = @At(value = "TAIL"))
    private void loadModAudio(GameSettings paramkv, CallbackInfo ci) {
        loadModAudio("resources/mod/sound", this.soundPoolSounds);
        loadModAudio("resources/mod/streaming", this.soundPoolStreaming);
        loadModAudio("resources/mod/music", this.soundPoolMusic);
        loadModAudio("resources/mod/cavemusic", this.soundPoolCave);
    }

    @Redirect(method = "tryToSetLibraryAndCodecs", at = @At(value = "INVOKE", target = "Lpaulscode/sound/SoundSystemConfig;addLibrary(Ljava/lang/Class;)V", remap = false))
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
