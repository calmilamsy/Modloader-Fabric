package net.glasslauncher.shockahpi;

import net.minecraft.src.WorldProviderSurface;

public class DimensionOverworld extends DimensionBase {
   public DimensionOverworld() {
      super(0, WorldProviderSurface.class, (Class)null);
      this.name = "Overworld";
   }
}
