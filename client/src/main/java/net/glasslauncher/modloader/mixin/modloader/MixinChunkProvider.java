package net.glasslauncher.modloader.mixin.modloader;

import net.glasslauncher.modloader.ModLoader;
import net.minecraft.src.ChunkProvider;
import net.minecraft.src.IChunkProvider;
import net.minecraft.src.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ChunkProvider.class)
public class MixinChunkProvider {

    @Redirect(method = "populate", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/IChunkProvider;populate(Lnet/minecraft/src/IChunkProvider;II)V"))
    public void populate(IChunkProvider ths, IChunkProvider iChunkProvider, int i, int i1) {
        chunkProvider.populate(iChunkProvider, i, i1);
        ModLoader.populateChunk(chunkProvider, i, i1, worldObj);
    }

    @Shadow
    private IChunkProvider chunkProvider;
    @Shadow
    private World worldObj;
}
