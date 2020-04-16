//// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
//// Jad home page: http://www.kpdus.com/jad.html
//// Decompiler options: packimports(3) braces deadcode
//// Source File Name:   fm.java
//
//package net.glasslauncher.shockahpi.base;
//
//import net.glasslauncher.shockahpi.DimensionBase;
//import net.minecraft.src.*;
//import net.minecraft.src.WorldProvider;
//
//import java.io.*;
//import java.util.List;
//import java.util.logging.Logger;
//
//// Referenced classes of package net.minecraft.src:
////            ISaveHandler, MinecraftException, DimensionBase, ChunkLoader,
////            CompressedStreamTools, NBTTagCompound, WorldInfo, WorldProvider,
////            IChunkLoader
//
//public class SaveHandler
//    implements ISaveHandler
//{
//
//    public SaveHandler(File paramFile, String paramString, boolean paramBoolean)
//    {
//        saveDirectory = new File(paramFile, paramString);
//        saveDirectory.mkdirs();
//        playersDirectory = new File(saveDirectory, "players");
//        field_28114_d = new File(saveDirectory, "data");
//        field_28114_d.mkdirs();
//        if(paramBoolean)
//        {
//            playersDirectory.mkdirs();
//        }
//        func_22154_d();
//    }
//
//    private void func_22154_d()
//    {
//        try {
//        File localFile = new File(saveDirectory, "session.lock");
//        DataOutputStream localDataOutputStream = new DataOutputStream(new FileOutputStream(localFile));
//        try {
//          localDataOutputStream.writeLong(now);
//        } finally {
//          localDataOutputStream.close();
//        }
//      } catch (IOException localIOException) {
//        localIOException.printStackTrace();
//        throw new RuntimeException("Failed to check session lock, aborting");
//      }
//    }
//
//    protected File getSaveDirectory()
//    {
//        return saveDirectory;
//    }
//
//    public void checkSessionLock()
//    {
//        try {
//            File localFile = new File(saveDirectory, "session.lock");
//            DataInputStream localDataInputStream = new DataInputStream(new FileInputStream(localFile));
//            try {
//              if (localDataInputStream.readLong() != now) {
//                throw new MinecraftException("The save is being accessed from another location, aborting");
//              }
//            } finally {
//              localDataInputStream.close();
//            }
//          } catch (IOException localIOException) {
//            throw new MinecraftException("Failed to check session lock, aborting");
//          }
//    }
//
//    public IChunkLoader getChunkLoader(WorldProvider paramxa)
//    {
//        DimensionBase localDimensionBase = DimensionBase.getDimByProvider(paramxa.getClass());
//        if(localDimensionBase.number != 0)
//        {
//            File localFile = new File(saveDirectory, (new StringBuilder("DIM")).append(localDimensionBase.number).toString());
//            localFile.mkdirs();
//            return new ChunkLoader(localFile, true);
//        } else
//        {
//            return new ChunkLoader(saveDirectory, true);
//        }
//    }
//
//    public WorldInfo loadWorldInfo()
//    {
//        File localFile = new File(saveDirectory, "level.dat");
//        if(localFile.exists())
//        {
//            try
//            {
//                NBTTagCompound localnu1 = CompressedStreamTools.loadGzippedCompoundFromOutputStream(new FileInputStream(localFile));
//                NBTTagCompound localnu3 = localnu1.getCompoundTag("Data");
//                return new WorldInfo(localnu3);
//            }
//            catch(Exception localException1)
//            {
//                localException1.printStackTrace();
//            }
//        }
//        localFile = new File(saveDirectory, "level.dat_old");
//        if(localFile.exists())
//        {
//            try
//            {
//                NBTTagCompound localnu2 = CompressedStreamTools.loadGzippedCompoundFromOutputStream(new FileInputStream(localFile));
//                NBTTagCompound localnu3 = localnu2.getCompoundTag("Data");
//                return new WorldInfo(localnu3);
//            }
//            catch(Exception localException2)
//            {
//                localException2.printStackTrace();
//            }
//        }
//        return null;
//    }
//
//    public void saveWorldInfoAndPlayer(WorldInfo paramei, List paramList)
//    {
//        NBTTagCompound localnu1 = paramei.getNBTTagCompoundWithPlayer(paramList);
//        NBTTagCompound localnu2 = new NBTTagCompound();
//        localnu2.setCompoundTag("Data", localnu1);
//        try
//        {
//            File localFile1 = new File(saveDirectory, "level.dat_new");
//            File localFile2 = new File(saveDirectory, "level.dat_old");
//            File localFile3 = new File(saveDirectory, "level.dat");
//            CompressedStreamTools.writeGzippedCompoundToOutputStream(localnu2, new FileOutputStream(localFile1));
//            if(localFile2.exists())
//            {
//                localFile2.delete();
//            }
//            localFile3.renameTo(localFile2);
//            if(localFile3.exists())
//            {
//                localFile3.delete();
//            }
//            localFile1.renameTo(localFile3);
//            if(localFile1.exists())
//            {
//                localFile1.delete();
//            }
//        }
//        catch(Exception localException)
//        {
//            localException.printStackTrace();
//        }
//    }
//
//    public void saveWorldInfo(WorldInfo paramei)
//    {
//        NBTTagCompound localnu1 = paramei.getNBTTagCompound();
//        NBTTagCompound localnu2 = new NBTTagCompound();
//        localnu2.setCompoundTag("Data", localnu1);
//        try
//        {
//            File localFile1 = new File(saveDirectory, "level.dat_new");
//            File localFile2 = new File(saveDirectory, "level.dat_old");
//            File localFile3 = new File(saveDirectory, "level.dat");
//            CompressedStreamTools.writeGzippedCompoundToOutputStream(localnu2, new FileOutputStream(localFile1));
//            if(localFile2.exists())
//            {
//                localFile2.delete();
//            }
//            localFile3.renameTo(localFile2);
//            if(localFile3.exists())
//            {
//                localFile3.delete();
//            }
//            localFile1.renameTo(localFile3);
//            if(localFile1.exists())
//            {
//                localFile1.delete();
//            }
//        }
//        catch(Exception localException)
//        {
//            localException.printStackTrace();
//        }
//    }
//
//    public File getMapFile(String paramString)
//    {
//        return new File(field_28114_d, (new StringBuilder(String.valueOf(paramString))).append(".dat").toString());
//    }
//
//    private static final Logger logger = Logger.getLogger("Minecraft");
//    private final File saveDirectory;
//    private final File playersDirectory;
//    private final File field_28114_d;
//    private final long now = System.currentTimeMillis();
//
//}
