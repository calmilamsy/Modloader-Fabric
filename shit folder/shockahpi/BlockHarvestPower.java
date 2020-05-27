package net.glasslauncher.shockahpi;

public class BlockHarvestPower {
   public final int blockID;
   public final float percentage;

   public BlockHarvestPower(int blockID, float percentage) {
      this.blockID = blockID;
      this.percentage = percentage;
   }

   public boolean equals(Object other) {
      return other == null?false:(other instanceof BlockHarvestPower?this.blockID == ((BlockHarvestPower)other).blockID:(other instanceof Integer?this.blockID == ((Integer)other).intValue():false));
   }
}
