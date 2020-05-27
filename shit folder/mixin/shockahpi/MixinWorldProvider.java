package net.glasslauncher.mixin.shockahpi;

import net.glasslauncher.shockahpi.DimensionBase;
import net.minecraft.src.WorldProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(WorldProvider.class)
public abstract class MixinWorldProvider {

   /**
    * @author calmilamsy
    * @reason Replaced functionality.
    */
   @Overwrite
   public static WorldProvider getProviderForDimension(int paramInt) {
      DimensionBase localDimensionBase = DimensionBase.getDimByNumber(paramInt);
      return localDimensionBase != null?localDimensionBase.getWorldProvider():null;
   }
}
