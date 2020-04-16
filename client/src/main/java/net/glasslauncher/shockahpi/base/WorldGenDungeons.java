//// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
//// Jad home page: http://www.kpdus.com/jad.html
//// Decompiler options: packimports(3) braces deadcode
//// Source File Name:   er.java
//
//package net.glasslauncher.shockahpi.base;
//
//import net.glasslauncher.shockahpi.SAPI;
//import net.minecraft.src.*;
//import net.minecraft.src.World;
//
//import java.util.Random;
//
//// Referenced classes of package net.minecraft.src:
////            WorldGenerator, World, Material, Block,
////            TileEntityChest, ItemStack, SAPI, DungeonLoot,
////            TileEntityMobSpawner
//
//public class WorldGenDungeons extends WorldGenerator
//{
//
//    public WorldGenDungeons()
//    {
//    }
//
//    public boolean generate(World paramfd, Random paramRandom, int paramInt1, int paramInt2, int paramInt3)
//    {
//        int i = 3;
//        int j = paramRandom.nextInt(2) + 2;
//        int k = paramRandom.nextInt(2) + 2;
//        int m = 0;
//        for(int n = paramInt1 - j - 1; n <= paramInt1 + j + 1; n++)
//        {
//            for(int i1 = paramInt2 - 1; i1 <= paramInt2 + i + 1; i1++)
//            {
//                for(int i2 = paramInt3 - k - 1; i2 <= paramInt3 + k + 1; i2++)
//                {
//                    Material localln = paramfd.getBlockMaterial(n, i1, i2);
//                    if(i1 == paramInt2 - 1 && !localln.isSolid())
//                    {
//                        return false;
//                    }
//                    if(i1 == paramInt2 + i + 1 && !localln.isSolid())
//                    {
//                        return false;
//                    }
//                    if((n == paramInt1 - j - 1 || n == paramInt1 + j + 1 || i2 == paramInt3 - k - 1 || i2 == paramInt3 + k + 1) && i1 == paramInt2 && paramfd.isAirBlock(n, i1, i2) && paramfd.isAirBlock(n, i1 + 1, i2))
//                    {
//                        m++;
//                    }
//                }
//
//            }
//
//        }
//
//        if(m < 1 || m > 5)
//        {
//            return false;
//        }
//        for(int n = paramInt1 - j - 1; n <= paramInt1 + j + 1; n++)
//        {
//            for(int i1 = paramInt2 + i; i1 >= paramInt2 - 1; i1--)
//            {
//                for(int i2 = paramInt3 - k - 1; i2 <= paramInt3 + k + 1; i2++)
//                {
//                    if(n == paramInt1 - j - 1 || i1 == paramInt2 - 1 || i2 == paramInt3 - k - 1 || n == paramInt1 + j + 1 || i1 == paramInt2 + i + 1 || i2 == paramInt3 + k + 1)
//                    {
//                        if(i1 >= 0 && !paramfd.getBlockMaterial(n, i1 - 1, i2).isSolid())
//                        {
//                            paramfd.setBlockWithNotify(n, i1, i2, 0);
//                        } else
//                        if(paramfd.getBlockMaterial(n, i1, i2).isSolid())
//                        {
//                            if(i1 == paramInt2 - 1 && paramRandom.nextInt(4) != 0)
//                            {
//                                paramfd.setBlockWithNotify(n, i1, i2, Block.cobblestoneMossy.blockID);
//                            } else
//                            {
//                                paramfd.setBlockWithNotify(n, i1, i2, Block.cobblestone.blockID);
//                            }
//                        }
//                    } else
//                    {
//                        paramfd.setBlockWithNotify(n, i1, i2, 0);
//                    }
//                }
//
//            }
//
//        }
//
//        for(int n = 0; n < 2; n++)
//        {
//            for(int i1 = 0; i1 < 3; i1++)
//            {
//                int i2 = (paramInt1 + paramRandom.nextInt(j * 2 + 1)) - j;
//                int i3 = paramInt2;
//                int i4 = (paramInt3 + paramRandom.nextInt(k * 2 + 1)) - k;
//                if(!paramfd.isAirBlock(i2, i3, i4))
//                {
//                    continue;
//                }
//                int i5 = 0;
//                if(paramfd.getBlockMaterial(i2 - 1, i3, i4).isSolid())
//                {
//                    i5++;
//                }
//                if(paramfd.getBlockMaterial(i2 + 1, i3, i4).isSolid())
//                {
//                    i5++;
//                }
//                if(paramfd.getBlockMaterial(i2, i3, i4 - 1).isSolid())
//                {
//                    i5++;
//                }
//                if(paramfd.getBlockMaterial(i2, i3, i4 + 1).isSolid())
//                {
//                    i5++;
//                }
//                if(i5 != 1)
//                {
//                    continue;
//                }
//                paramfd.setBlockWithNotify(i2, i3, i4, Block.chest.blockID);
//                TileEntityChest localjs = (TileEntityChest)paramfd.getBlockTileEntity(i2, i3, i4);
//                for(int i6 = 0; i6 < 8; i6++)
//                {
//                    ItemStack localiz = pickCheckLootItem(paramRandom);
//                    if(localiz != null)
//                    {
//                        localjs.setInventorySlotContents(paramRandom.nextInt(localjs.getSizeInventory()), localiz);
//                    }
//                }
//
//                for(int i6 = 0; i6 < Math.min(19, SAPI.dungeonGetAmountOfGuaranteed()); i6++)
//                {
//                    ItemStack stack = SAPI.dungeonGetGuaranteed(i6).getStack();
//                    if(stack != null)
//                    {
//                        localjs.setInventorySlotContents(paramRandom.nextInt(localjs.getSizeInventory()), stack);
//                    }
//                }
//
//                break;
//            }
//
//        }
//
//        paramfd.setBlockWithNotify(paramInt1, paramInt2, paramInt3, Block.mobSpawner.blockID);
//        TileEntityMobSpawner localcy = (TileEntityMobSpawner)paramfd.getBlockTileEntity(paramInt1, paramInt2, paramInt3);
//        localcy.setMobID(pickMobSpawner(paramRandom));
//        return true;
//    }
//
//    private ItemStack pickCheckLootItem(Random paramRandom)
//    {
//        return SAPI.dungeonGetRandomItem();
//    }
//
//    private String pickMobSpawner(Random paramRandom)
//    {
//        return SAPI.dungeonGetRandomMob();
//    }
//}
