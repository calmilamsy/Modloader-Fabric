//// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
//// Jad home page: http://www.kpdus.com/jad.html
//// Decompiler options: packimports(3) braces deadcode
//// Source File Name:   PlayerBaseSAPI.java
//
//package net.glasslauncher.shockahpi;
//
//import java.util.Random;
//
//import net.glasslauncher.playerapi.PlayerBase;
//import net.minecraft.client.Minecraft;
//import net.minecraft.src.AchievementList;
//import net.minecraft.src.EntityPlayerSP;
//
//// Referenced classes of package net.minecraft.src:
////            PlayerBase, EntityPlayerSP, AchievementList, StatFileWriter,
////            GuiAchievement, DimensionBase, World, SoundManager,
////            MovementInput, AxisAlignedBB
//
//public class PlayerBaseSAPI extends PlayerBase
//{
//
//    public PlayerBaseSAPI(EntityPlayerSP p)
//    {
//        super(p);
//    }
//
//    public boolean onLivingUpdate()
//    {
//        Minecraft mc = player.mc;
//        if(!mc.statFileWriter.hasAchievementUnlocked(AchievementList.openInventory))
//        {
//            mc.guiAchievement.queueAchievementInformation(AchievementList.openInventory);
//        }
//        player.prevTimeInPortal = player.timeInPortal;
//        if(portal != 0)
//        {
//            DimensionBase dimensionbase = DimensionBase.getDimByNumber(portal);
//            if(player.inPortal)
//            {
//                if(!player.worldObj.multiplayerWorld && player.ridingEntity != null)
//                {
//                    player.mountEntity(null);
//                }
//                if(mc.currentScreen != null)
//                {
//                    mc.displayGuiScreen(null);
//                }
//                if(player.timeInPortal == 0.0F)
//                {
//                    mc.sndManager.playSoundFX(dimensionbase.soundTrigger, 1.0F, player.rand.nextFloat() * 0.4F + 0.8F);
//                }
//                player.timeInPortal += 0.0125F;
//                if(player.timeInPortal >= 1.0F)
//                {
//                    player.timeInPortal = 1.0F;
//                    if(!player.worldObj.multiplayerWorld)
//                    {
//                        player.timeUntilPortal = 10;
//                        mc.sndManager.playSoundFX(dimensionbase.soundTravel, 1.0F, player.rand.nextFloat() * 0.4F + 0.8F);
//                        DimensionBase.usePortal(portal);
//                    }
//                }
//                player.inPortal = false;
//            } else
//            {
//                if(player.timeInPortal > 0.0F)
//                {
//                    player.timeInPortal -= 0.05F;
//                }
//                if(player.timeInPortal < 0.0F)
//                {
//                    player.timeInPortal = 0.0F;
//                }
//            }
//        }
//        if(player.timeUntilPortal > 0)
//        {
//            player.timeUntilPortal--;
//        }
//        player.movementInput.updatePlayerMoveState(player);
//        if(player.movementInput.sneak && player.ySize < 0.2F)
//        {
//            player.ySize = 0.2F;
//        }
//        player.pushOutOfBlocks(player.posX - (double)player.width * 0.34999999999999998D, player.boundingBox.minY + 0.5D, player.posZ + (double)player.width * 0.34999999999999998D);
//        player.pushOutOfBlocks(player.posX - (double)player.width * 0.34999999999999998D, player.boundingBox.minY + 0.5D, player.posZ - (double)player.width * 0.34999999999999998D);
//        player.pushOutOfBlocks(player.posX + (double)player.width * 0.34999999999999998D, player.boundingBox.minY + 0.5D, player.posZ - (double)player.width * 0.34999999999999998D);
//        player.pushOutOfBlocks(player.posX + (double)player.width * 0.34999999999999998D, player.boundingBox.minY + 0.5D, player.posZ + (double)player.width * 0.34999999999999998D);
//        player.superOnLivingUpdate();
//        return true;
//    }
//
//    public boolean respawn()
//    {
//        DimensionBase.respawn(false, 0);
//        return true;
//    }
//
//    public int portal;
//}
