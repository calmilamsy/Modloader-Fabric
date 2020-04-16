//// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
//// Jad home page: http://www.kpdus.com/jad.html
//// Decompiler options: packimports(3) braces deadcode
//// Source File Name:   mx.java
//
//package net.glasslauncher.shockahpi.base;
//
//import net.glasslauncher.shockahpi.DimensionBase;
//import net.minecraft.src.*;
//import net.minecraft.src.SaveHandler;
//import net.minecraft.src.WorldProvider;
//
//import java.io.File;
//import java.util.List;
//
//// Referenced classes of package net.minecraft.src:
////            SaveHandler, DimensionBase, McRegionChunkLoader, WorldInfo,
////            WorldProvider, IChunkLoader
//
//public class SaveOldDir extends SaveHandler
//{
//
//    public SaveOldDir(File paramFile, String paramString, boolean paramBoolean)
//    {
//        super(paramFile, paramString, paramBoolean);
//    }
//
//    public IChunkLoader getChunkLoader(WorldProvider paramxa)
//    {
//        File localFile1 = getSaveDirectory();
//        DimensionBase localDimensionBase = DimensionBase.getDimByProvider(paramxa.getClass());
//        if(localDimensionBase.number != 0)
//        {
//            File localFile2 = new File(localFile1, (new StringBuilder("DIM")).append(localDimensionBase.number).toString());
//            localFile2.mkdirs();
//            return new McRegionChunkLoader(localFile2);
//        } else
//        {
//            return new McRegionChunkLoader(localFile1);
//        }
//    }
//
//    public void saveWorldInfoAndPlayer(WorldInfo paramei, List paramList)
//    {
//        paramei.setSaveVersion(19132);
//        super.saveWorldInfoAndPlayer(paramei, paramList);
//    }
//}
