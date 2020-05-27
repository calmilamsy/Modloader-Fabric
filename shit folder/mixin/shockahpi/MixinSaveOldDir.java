package net.glasslauncher.mixin.shockahpi;

import net.glasslauncher.shockahpi.DimensionBase;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.io.File;

@Mixin(SaveOldDir.class)
public class MixinSaveOldDir extends SaveHandler {

   public MixinSaveOldDir(File file, String s, boolean b) {
      super(file, s, b);
   }

   /**
    * @author calmilamsy
    * @reason Too lazy, just want to get working example.
    * //TODO: No overwrite.
    */
   @Overwrite
   public IChunkLoader getChunkLoader(WorldProvider paramxa) {
      File localFile1 = this.getSaveDirectory();
      DimensionBase localDimensionBase = DimensionBase.getDimByProvider(paramxa.getClass());
      if(localDimensionBase.number != 0) {
         File localFile2 = new File(localFile1, "DIM" + localDimensionBase.number);
         localFile2.mkdirs();
         return new McRegionChunkLoader(localFile2);
      } else {
         return new McRegionChunkLoader(localFile1);
      }
   }

}
