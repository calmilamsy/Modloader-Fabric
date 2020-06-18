// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 
// Source File Name:   BaseMod.java

package net.glasslauncher.modloader;

import net.minecraft.block.BlockBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.ScreenBase;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.render.TileRenderer;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.Level;
import net.minecraft.level.TileView;

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

    public boolean dispenseEntity(Level world, double x, double d, double d1,
                                  int i, int j, ItemInstance itemstack) {
        return false;
    }

    public void generateNether(Level world1, Random random1, int i, int j) {
    }

    public void generateSurface(Level world1, Random random1, int i, int j) {
    }

    public void keyboardEvent(KeyBinding keybinding) {
    }

    public void modsLoaded() {
    }

    public boolean onTickInGame(Minecraft game) {
        return false;
    }

    public boolean onTickInGUI(Minecraft game, ScreenBase gui) {
        return false;
    }

    public void registerAnimation(Minecraft minecraft) {
    }

    public void renderInvBlock(TileRenderer renderblocks, BlockBase block1, int i, int j) {
    }

    public boolean renderWorldBlock(TileRenderer renderer, TileView world, int x, int i, int j, BlockBase block1, int k) {
        return false;
    }

    public void takenFromCrafting(PlayerBase entityplayer, ItemInstance itemstack) {
    }

    public void takenFromFurnace(PlayerBase entityplayer, ItemInstance itemstack) {
    }

    public void onItemPickup(PlayerBase entityplayer, ItemInstance itemstack) {
    }

    public String toString() {
        return getClass().getName() + " " + version();
    }

    public abstract String version();
}
