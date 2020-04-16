//// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
//// Jad home page: http://www.kpdus.com/jad.html
//// Decompiler options: packimports(3) braces deadcode
//// Source File Name:   os.java
//
//package net.glasslauncher.shockahpi.base;
//
//import net.glasslauncher.shockahpi.Loc;
//import net.glasslauncher.shockahpi.SAPI;
//import net.minecraft.client.Minecraft;
//import net.minecraft.src.Block;
//import net.minecraft.src.EntityPlayer;
//import net.minecraft.src.ItemStack;
//import net.minecraft.src.PlayerController;
//
//// Referenced classes of package net.minecraft.src:
////            PlayerController, EntityPlayer, World, EntityPlayerSP,
////            Block, ItemStack, Loc, SAPI,
////            StepSound, SoundManager, GuiIngame, RenderGlobal
//
//public class PlayerControllerSP extends PlayerController
//{
//
//    public PlayerControllerSP(Minecraft paramMinecraft)
//    {
//        super(paramMinecraft);
//        field_1074_c = -1;
//        field_1073_d = -1;
//        field_1072_e = -1;
//        curBlockDamage = 0.0F;
//        prevBlockDamage = 0.0F;
//        field_1069_h = 0.0F;
//        blockHitWait = 0;
//    }
//
//    public void flipPlayer(EntityPlayer paramgs)
//    {
//        paramgs.rotationYaw = -180F;
//    }
//
//    public boolean sendBlockRemoved(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
//    {
//        int j = mc.theWorld.getBlockId(paramInt1, paramInt2, paramInt3);
//        int k = mc.theWorld.getBlockMetadata(paramInt1, paramInt2, paramInt3);
//        boolean bool1 = super.sendBlockRemoved(paramInt1, paramInt2, paramInt3, paramInt4);
//        ItemStack localiz = mc.thePlayer.getCurrentEquippedItem();
//        boolean bool2 = mc.thePlayer.canHarvestBlock(Block.blocksList[j]);
//        if(localiz != null)
//        {
//            localiz.onDestroyBlock(j, paramInt1, paramInt2, paramInt3, mc.thePlayer);
//            if(localiz.stackSize == 0)
//            {
//                localiz.onItemDestroyedByUse(mc.thePlayer);
//                mc.thePlayer.destroyCurrentEquippedItem();
//            }
//        }
//        if(bool1 && bool2)
//        {
//            if(SAPI.interceptHarvest(mc.theWorld, mc.thePlayer, new Loc(paramInt1, paramInt2, paramInt3), j, k))
//            {
//                return bool1;
//            }
//            Block.blocksList[j].harvestBlock(mc.theWorld, mc.thePlayer, paramInt1, paramInt2, paramInt3, k);
//        }
//        return bool1;
//    }
//
//    public void clickBlock(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
//    {
//        mc.theWorld.onBlockHit(mc.thePlayer, paramInt1, paramInt2, paramInt3, paramInt4);
//        int j = mc.theWorld.getBlockId(paramInt1, paramInt2, paramInt3);
//        if(j > 0 && curBlockDamage == 0.0F)
//        {
//            Block.blocksList[j].onBlockClicked(mc.theWorld, paramInt1, paramInt2, paramInt3, mc.thePlayer);
//        }
//        if(j > 0 && Block.blocksList[j].blockStrength(mc.thePlayer) >= 1.0F)
//        {
//            sendBlockRemoved(paramInt1, paramInt2, paramInt3, paramInt4);
//        }
//    }
//
//    public void resetBlockRemoving()
//    {
//        curBlockDamage = 0.0F;
//        blockHitWait = 0;
//    }
//
//    public void sendBlockRemoving(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
//    {
//        if(blockHitWait > 0)
//        {
//            blockHitWait--;
//            return;
//        }
//        if(paramInt1 == field_1074_c && paramInt2 == field_1073_d && paramInt3 == field_1072_e)
//        {
//            int j = mc.theWorld.getBlockId(paramInt1, paramInt2, paramInt3);
//            if(j == 0)
//            {
//                return;
//            }
//            Block localuu = Block.blocksList[j];
//            curBlockDamage += localuu.blockStrength(mc.thePlayer);
//            if(field_1069_h % 4F == 0.0F && localuu != null)
//            {
//                mc.sndManager.playSound(localuu.stepSound.stepSoundDir2(), (float)paramInt1 + 0.5F, (float)paramInt2 + 0.5F, (float)paramInt3 + 0.5F, (localuu.stepSound.getVolume() + 1.0F) / 8F, localuu.stepSound.getPitch() * 0.5F);
//            }
//            field_1069_h++;
//            if(curBlockDamage >= 1.0F)
//            {
//                sendBlockRemoved(paramInt1, paramInt2, paramInt3, paramInt4);
//                curBlockDamage = 0.0F;
//                prevBlockDamage = 0.0F;
//                field_1069_h = 0.0F;
//                blockHitWait = 5;
//            }
//        } else
//        {
//            curBlockDamage = 0.0F;
//            prevBlockDamage = 0.0F;
//            field_1069_h = 0.0F;
//            field_1074_c = paramInt1;
//            field_1073_d = paramInt2;
//            field_1072_e = paramInt3;
//        }
//    }
//
//    public void setPartialTime(float paramFloat)
//    {
//        if(curBlockDamage <= 0.0F)
//        {
//            mc.ingameGUI.damageGuiPartialTime = 0.0F;
//            mc.renderGlobal.damagePartialTime = 0.0F;
//        } else
//        {
//            float f1 = prevBlockDamage + (curBlockDamage - prevBlockDamage) * paramFloat;
//            mc.ingameGUI.damageGuiPartialTime = f1;
//            mc.renderGlobal.damagePartialTime = f1;
//        }
//    }
//
//    public float getBlockReachDistance()
//    {
//        return SAPI.reachGet();
//    }
//
//    public void func_717_a(World paramfd)
//    {
//        super.func_717_a(paramfd);
//    }
//
//    public void updateController()
//    {
//        prevBlockDamage = curBlockDamage;
//        mc.sndManager.playRandomMusicIfReady();
//    }
//
//    private int field_1074_c;
//    private int field_1073_d;
//    private int field_1072_e;
//    private float curBlockDamage;
//    private float prevBlockDamage;
//    private float field_1069_h;
//    private int blockHitWait;
//}
