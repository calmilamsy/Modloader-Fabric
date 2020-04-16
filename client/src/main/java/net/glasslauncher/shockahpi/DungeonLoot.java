//// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
//// Jad home page: http://www.kpdus.com/jad.html
//// Decompiler options: packimports(3) braces deadcode
//// Source File Name:   DungeonLoot.java
//
//package net.glasslauncher.shockahpi;
//
//import net.minecraft.src.Block;
//import net.minecraft.src.ItemStack;
//
//import java.util.Random;
//
//// Referenced classes of package net.minecraft.src:
////            ItemStack, Block, Item
//
//public class DungeonLoot
//{
//
//    public DungeonLoot(ItemStack stack)
//    {
//        loot = new ItemStack(stack.itemID, 1, stack.getItemDamage());
//        min = max = stack.stackSize;
//    }
//
//    public DungeonLoot(ItemStack stack, int min, int max)
//    {
//        loot = new ItemStack(stack.itemID, 1, stack.getItemDamage());
//        this.min = min;
//        this.max = max;
//    }
//
//    public ItemStack getStack()
//    {
//        int damage = 0;
//        if(loot.itemID <= 255)
//        {
//            if(Block.blocksList[loot.itemID].getRenderColor(1) != 1)
//            {
//                damage = loot.getItemDamage();
//            } else
//            if(!loot.getItem().bFull3D)
//            {
//                damage = loot.getItemDamage();
//            }
//        }
//        return new ItemStack(loot.itemID, min + (new Random()).nextInt((max - min) + 1), damage);
//    }
//
//    public final ItemStack loot;
//    public final int min;
//    public final int max;
//}
