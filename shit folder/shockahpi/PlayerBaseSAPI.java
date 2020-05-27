package net.glasslauncher.shockahpi;

import net.glasslauncher.modloader.ModLoader;
import net.glasslauncher.playerapi.PlayerBase;
import net.minecraft.client.Minecraft;
import net.minecraft.src.AchievementList;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayerSP;
import net.minecraft.src.GuiScreen;

public class PlayerBaseSAPI extends PlayerBase {
   public int portal;

   public PlayerBaseSAPI(EntityPlayerSP p) {
      super(p);
   }

   public boolean onLivingUpdate() {
      Minecraft mc = ModLoader.getMinecraftInstance();
      if(!mc.statFileWriter.hasAchievementUnlocked(AchievementList.openInventory)) {
         mc.guiAchievement.queueAchievementInformation(AchievementList.openInventory);
      }

      this.player.prevTimeInPortal = this.player.timeInPortal;
      if(this.portal != 0) {
         DimensionBase dimensionbase = DimensionBase.getDimByNumber(this.portal);
         if(((EntityPlayerSPProxy) this.player).isInPortal()) {
            if(!this.player.worldObj.multiplayerWorld && this.player.ridingEntity != null) {
               this.player.mountEntity((Entity)null);
            }

            if(mc.currentScreen != null) {
               mc.displayGuiScreen((GuiScreen)null);
            }

            if(this.player.timeInPortal == 0.0F) {
               mc.sndManager.playSoundFX(dimensionbase.soundTrigger, 1.0F, mc.theWorld.rand.nextFloat() * 0.4F + 0.8F);
            }

            this.player.timeInPortal += 0.0125F;
            if(this.player.timeInPortal >= 1.0F) {
               this.player.timeInPortal = 1.0F;
               if(!this.player.worldObj.multiplayerWorld) {
                  this.player.timeUntilPortal = 10;
                  mc.sndManager.playSoundFX(dimensionbase.soundTravel, 1.0F, mc.theWorld.rand.nextFloat() * 0.4F + 0.8F);
                  DimensionBase.usePortal(this.portal);
               }
            }

            ((EntityPlayerSPProxy) this.player).setInPortal(false);
         } else {
            if(this.player.timeInPortal > 0.0F) {
               this.player.timeInPortal -= 0.05F;
            }

            if(this.player.timeInPortal < 0.0F) {
               this.player.timeInPortal = 0.0F;
            }
         }
      }

      if(this.player.timeUntilPortal > 0) {
         --this.player.timeUntilPortal;
      }

      this.player.movementInput.updatePlayerMoveState(this.player);
      if(this.player.movementInput.sneak && this.player.ySize < 0.2F) {
         this.player.ySize = 0.2F;
      }

      ((EntityPlayerSPProxy) this.player).doPushOutOfBlocks(this.player.posX - (double)this.player.width * 0.35D, this.player.boundingBox.minY + 0.5D, this.player.posZ + (double)this.player.width * 0.35D);
      ((EntityPlayerSPProxy) this.player).doPushOutOfBlocks(this.player.posX - (double)this.player.width * 0.35D, this.player.boundingBox.minY + 0.5D, this.player.posZ - (double)this.player.width * 0.35D);
      ((EntityPlayerSPProxy) this.player).doPushOutOfBlocks(this.player.posX + (double)this.player.width * 0.35D, this.player.boundingBox.minY + 0.5D, this.player.posZ - (double)this.player.width * 0.35D);
      ((EntityPlayerSPProxy) this.player).doPushOutOfBlocks(this.player.posX + (double)this.player.width * 0.35D, this.player.boundingBox.minY + 0.5D, this.player.posZ + (double)this.player.width * 0.35D);
      ((EntityPlayerSPProxy) this.player).superOnLivingUpdate();
      return true;
   }

   public boolean respawn() {
      DimensionBase.respawn(false, 0);
      return true;
   }
}
