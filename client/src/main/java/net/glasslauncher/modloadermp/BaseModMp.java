package net.glasslauncher.modloadermp;

import net.glasslauncher.modloader.BaseMod;
import net.minecraft.src.GuiScreen;

public abstract class BaseModMp extends BaseMod {
   public final int getId() {
      return this.toString().hashCode();
   }

   public void ModsLoaded() {
      ModLoaderMp.Init();
   }

   public void HandlePacket(Packet230ModLoader packet230modloader) {
   }

   public void HandleTileEntityPacket(int i, int j, int k, int l, int[] ai, float[] af, String[] as) {
   }

   public GuiScreen HandleGUI(int i) {
      return null;
   }
}
