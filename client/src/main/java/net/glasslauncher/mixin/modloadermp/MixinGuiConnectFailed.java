package net.glasslauncher.mixin.modloadermp;

import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(GuiConnectFailed.class)
public class MixinGuiConnectFailed extends GuiScreen {

   @Shadow private String errorMessage;
   @Shadow private String errorDetail;

   /**
    * @author calmilamsy
    * @reason Entire method is changed.
    */
   @Overwrite
   public void drawScreen(int i, int j, float f) {
      this.drawDefaultBackground();
      this.drawCenteredString(this.fontRenderer, this.errorMessage, this.width / 2, this.height / 2 - 50, 16777215);
      String[] as = this.errorDetail.split("\n");

      for(int k = 0; k < as.length; ++k) {
         this.drawCenteredString(this.fontRenderer, as[k], this.width / 2, this.height / 2 - 10 + k * 10, 16777215);
      }

      super.drawScreen(i, j, f);
   }
}
