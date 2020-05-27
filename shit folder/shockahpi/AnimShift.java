package net.glasslauncher.shockahpi;

public class AnimShift extends AnimBase {
   private final int h;
   private final int v;

   public AnimShift(int spriteID, String spritePath, int h, int v) {
      super(spriteID, spritePath);
      this.h = h;
      this.v = v;
      this.getCleanFrame();
   }

   public void onTick() {
      this.animFrame();
      this.copyFrameToArray();
   }

   public void animFrame() {
      this.shiftFrame(this.h, this.v, true, true);
   }
}
