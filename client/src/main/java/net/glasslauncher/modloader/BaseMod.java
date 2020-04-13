// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 
// Source File Name:   BaseMod.java

package net.glasslauncher.modloader;

import net.minecraft.client.Minecraft;
import net.minecraft.src.*;

import java.util.Map;
import java.util.Random;

// Referenced classes of package net.minecraft.src:
//            World, ItemStack, KeyBinding, GuiScreen, 
//            RenderBlocks, Block, IBlockAccess, EntityPlayer

public abstract class BaseMod {

    public BaseMod() {
    }

    public int addFuel(int id) {
        return 0;
    }

    public void addRenderer(Map map) {
    }

    public boolean dispenseEntity(World world, double x, double d, double d1,
                                  int i, int j, ItemStack itemstack) {
        return false;
    }

    public void generateNether(World world1, Random random1, int i, int j) {
    }

    public void generateSurface(World world1, Random random1, int i, int j) {
    }

    public void keyboardEvent(KeyBinding keybinding) {
    }

    public void modsLoaded() {
    }

    public boolean onTickInGame(Minecraft game) {
        return false;
    }

    public boolean onTickInGUI(Minecraft game, GuiScreen gui) {
        return false;
    }

    public void registerAnimation(Minecraft minecraft) {
    }

    public void renderInvBlock(RenderBlocks renderblocks, Block block1, int i, int j) {
    }

    public boolean renderWorldBlock(RenderBlocks renderer, IBlockAccess world, int x, int i, int j, Block block1, int k) {
        return false;
    }

    public void takenFromCrafting(EntityPlayer entityplayer, ItemStack itemstack) {
    }

    public void takenFromFurnace(EntityPlayer entityplayer, ItemStack itemstack) {
    }

    public void onItemPickup(EntityPlayer entityplayer, ItemStack itemstack) {
    }

    public String toString() {
        return getClass().getName() + " " + version();
    }

    public abstract String version();
}
