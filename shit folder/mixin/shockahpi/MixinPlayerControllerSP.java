package net.glasslauncher.mixin.shockahpi;

import net.glasslauncher.shockahpi.Loc;
import net.glasslauncher.shockahpi.SAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.src.Block;
import net.minecraft.src.ItemStack;
import net.minecraft.src.PlayerController;
import net.minecraft.src.PlayerControllerSP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(PlayerControllerSP.class)
public class MixinPlayerControllerSP extends PlayerController {

   public MixinPlayerControllerSP(Minecraft paramMinecraft) {
      super(paramMinecraft);
   }

   /**
    * @author calmilamsy
    * @reason Edited ifs that I cba to do just yet
    * //TODO: Not overwrite.
    */
   @Overwrite
   public boolean sendBlockRemoved(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
      int j = this.mc.theWorld.getBlockId(paramInt1, paramInt2, paramInt3);
      int k = this.mc.theWorld.getBlockMetadata(paramInt1, paramInt2, paramInt3);
      boolean bool1 = super.sendBlockRemoved(paramInt1, paramInt2, paramInt3, paramInt4);
      ItemStack localiz = this.mc.thePlayer.getCurrentEquippedItem();
      boolean bool2 = this.mc.thePlayer.canHarvestBlock(Block.blocksList[j]);
      if(localiz != null) {
         localiz.onDestroyBlock(j, paramInt1, paramInt2, paramInt3, this.mc.thePlayer);
         if (localiz.stackSize == 0) {
            localiz.func_1097_a(this.mc.thePlayer);
            this.mc.thePlayer.destroyCurrentEquippedItem();
         }
      }

      if(bool1 && bool2) {
         if(SAPI.interceptHarvest(this.mc.theWorld, this.mc.thePlayer, new Loc(paramInt1, paramInt2, paramInt3), j, k)) {
            return bool1;
         }

         Block.blocksList[j].harvestBlock(this.mc.theWorld, this.mc.thePlayer, paramInt1, paramInt2, paramInt3, k);
      }

      return bool1;
   }

   public float getBlockReachDistance() {
      return SAPI.reachGet();
   }
}
