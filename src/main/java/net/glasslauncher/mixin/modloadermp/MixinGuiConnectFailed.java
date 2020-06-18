package net.glasslauncher.mixin.modloadermp;


import net.minecraft.client.gui.screen.Disconnecting;
import net.minecraft.client.gui.screen.ScreenBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Disconnecting.class)
public class MixinGuiConnectFailed extends ScreenBase {

   @Shadow private String line1;
   @Shadow private String line2;

   /**
    * @author calmilamsy
    * @reason Entire method is changed.
    */
   @Overwrite
   public void render(int i, int j, float f) {
      this.renderBackground();
      this.drawTextWithShadowCentred(this.textManager, this.line1, this.width / 2, this.height / 2 - 50, 16777215);
      String[] as = this.line2.split("\n");

      for(int k = 0; k < as.length; ++k) {
         this.drawTextWithShadowCentred(this.textManager, as[k], this.width / 2, this.height / 2 - 10 + k * 10, 16777215);
      }

      super.render(i, j, f);
   }
}
