package net.glasslauncher.mixin.shockahpi;

import net.glasslauncher.shockahpi.Loc;
import net.glasslauncher.shockahpi.SAPI;
import net.minecraft.src.Chunk;
import net.minecraft.src.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(World.class)
public abstract class MixinWorld {

   /**
    * @author calmilamsy
    * @reason Too lazy, just want to get working example.
    * //TODO: No overwrite.
    */
   @Overwrite
   public boolean setBlockAndMetadata(int i1, int j1, int k1, int l1, int i2) {
      if(i1 >= -32000000 && k1 >= -32000000 && i1 < 32000000 && k1 <= 32000000) {
         if(j1 < 0) {
            return false;
         } else if(j1 >= 128) {
            return false;
         } else {
            Chunk lm1 = this.getChunkFromChunkCoords(i1 >> 4, k1 >> 4);
            l1 = SAPI.interceptBlockSet((World) (Object) this, new Loc(i1, j1, k1), l1);
            return lm1.setBlockIDWithMetadata(i1 & 15, j1, k1 & 15, l1, i2);
         }
      } else {
         return false;
      }
   }

   /**
    * @author calmilamsy
    * @reason Too lazy, just want to get working example.
    * //TODO: No overwrite.
    */
   @Overwrite
   public boolean setBlock(int i1, int j1, int k1, int l1) {
      if(i1 >= -32000000 && k1 >= -32000000 && i1 < 32000000 && k1 <= 32000000) {
         if(j1 < 0) {
            return false;
         } else if(j1 >= 128) {
            return false;
         } else {
            Chunk lm1 = this.getChunkFromChunkCoords(i1 >> 4, k1 >> 4);
            l1 = SAPI.interceptBlockSet((World) (Object) this, new Loc(i1, j1, k1), l1);
            return lm1.setBlockID(i1 & 15, j1, k1 & 15, l1);
         }
      } else {
         return false;
      }
   }

   @Shadow public abstract Chunk getChunkFromChunkCoords(int i, int i1);
}
