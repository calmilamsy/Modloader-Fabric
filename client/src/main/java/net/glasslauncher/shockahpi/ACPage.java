//// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
//// Jad home page: http://www.kpdus.com/jad.html
//// Decompiler options: packimports(3) braces deadcode
//// Source File Name:   ACPage.java
//
//package net.glasslauncher.shockahpi;
//
//import net.minecraft.src.Achievement;
//import net.minecraft.src.Block;
//
//import java.util.ArrayList;
//import java.util.Random;
//
//// Referenced classes of package net.minecraft.src:
////            SAPI, Achievement, Block
//
//public class ACPage
//{
//
//    public ACPage()
//    {
//        list = new ArrayList();
//        id = 0;
//        title = "Minecraft";
//        SAPI.acPageAdd(this);
//    }
//
//    public ACPage(String title)
//    {
//        list = new ArrayList();
//        id = nextID++;
//        this.title = title;
//        SAPI.acPageAdd(this);
//    }
//
//    public void addAchievements(Achievement achievements[])
//    {
//        Achievement aachievement[];
//        int j = (aachievement = achievements).length;
//        for(int i = 0; i < j; i++)
//        {
//            Achievement achievement = aachievement[i];
//            list.add(Integer.valueOf(achievement.statId));
//        }
//
//    }
//
//    public int bgGetSprite(Random random, int x, int y)
//    {
//        int sprite = Block.sand.blockIndexInTexture;
//        int rnd = random.nextInt(1 + y) + y / 2;
//        if(rnd > 37 || y == 35)
//        {
//            sprite = Block.bedrock.blockIndexInTexture;
//        } else
//        if(rnd == 22)
//        {
//            sprite = random.nextInt(2) != 0 ? Block.oreRedstone.blockIndexInTexture : Block.oreDiamond.blockIndexInTexture;
//        } else
//        if(rnd == 10)
//        {
//            sprite = Block.oreIron.blockIndexInTexture;
//        } else
//        if(rnd == 8)
//        {
//            sprite = Block.oreCoal.blockIndexInTexture;
//        } else
//        if(rnd > 4)
//        {
//            sprite = Block.stone.blockIndexInTexture;
//        } else
//        if(rnd > 0)
//        {
//            sprite = Block.dirt.blockIndexInTexture;
//        }
//        return sprite;
//    }
//
//    private static int nextID = 1;
//    public final int id;
//    public final String title;
//    ArrayList list;
//
//}
