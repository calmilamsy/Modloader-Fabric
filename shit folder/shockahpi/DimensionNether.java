package net.glasslauncher.shockahpi;

import net.minecraft.src.Teleporter;
import net.minecraft.src.WorldProviderHell;

public class DimensionNether extends DimensionBase {
   public DimensionNether() {
      super(-1, WorldProviderHell.class, Teleporter.class);
      this.name = "Nether";
   }

   public Loc getDistanceScale(Loc paramLoc, boolean paramBoolean) {
      double d = paramBoolean?8.0D:0.125D;
      return paramLoc.multiply(d, 1.0D, d);
   }
}
