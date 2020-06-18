package net.glasslauncher.mixin.modloader;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.glasslauncher.modloader.ModLoader;
import net.minecraft.level.Level;
import net.minecraft.level.chunk.ChunkCache;
import net.minecraft.level.source.LevelSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ChunkCache.class)
public class MixinChunkProvider {

    @Shadow private LevelSource field_1512;

    @Shadow private Level field_1515;

    @Environment(EnvType.CLIENT)
    @Redirect(method = "decorate", at = @At(value = "INVOKE", target = "Lnet/minecraft/level/source/LevelSource;decorate(Lnet/minecraft/level/source/LevelSource;II)V"))
            public void populate(LevelSource levelSource, LevelSource levelSource2, int chunkX, int chunkZ) {
        field_1512.decorate(levelSource, chunkX, chunkZ);
        ModLoader.populateChunk(field_1512, chunkX, chunkZ, field_1515);
    }
}
