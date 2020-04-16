//// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
//// Jad home page: http://www.kpdus.com/jad.html
//// Decompiler options: packimports(3) braces deadcode
//// Source File Name:   fd.java
//
//package net.glasslauncher.shockahpi.base;
//
//import net.glasslauncher.shockahpi.DimensionBase;
//import net.glasslauncher.shockahpi.Loc;
//import net.glasslauncher.shockahpi.SAPI;
//import net.minecraft.src.*;
//import net.minecraft.src.WorldProvider;
//
//import java.util.*;
//
//// Referenced classes of package net.minecraft.src:
////            IBlockAccess, WorldProvider, WorldInfo, MapStorage,
////            ISaveHandler, DimensionBase, ChunkProvider, EntityPlayer,
////            NBTTagCompound, ChunkProviderLoadOrGenerate, MathHelper, IChunkProvider,
////            IProgressUpdate, Chunk, Loc, SAPI,
////            Material, Block, IWorldAccess, EnumSkyBlock,
////            Vec3D, Entity, AxisAlignedBB, WorldChunkManager,
////            BiomeGenBase, NextTickListEntry, TileEntity, BlockFire,
////            BlockFluid, Explosion, MetadataChunkBlock, SpawnerAnimals,
////            ChunkCoordIntPair, EntityLightningBolt, ChunkCache, Pathfinder,
////            ChunkCoordinates, MovingObjectPosition, PathEntity, MapDataBase
//
//public class World
//    implements IBlockAccess
//{
//
//    public WorldChunkManager getWorldChunkManager()
//    {
//        return worldProvider.worldChunkMgr;
//    }
//
//    public World(ISaveHandler wt1, String s1, net.minecraft.src.WorldProvider xa1, long l1)
//    {
//        scheduledUpdatesAreImmediate = false;
//        lightingToUpdate = new ArrayList();
//        loadedEntityList = new ArrayList();
//        unloadedEntityList = new ArrayList();
//        scheduledTickTreeSet = new TreeSet();
//        scheduledTickSet = new HashSet();
//        loadedTileEntityList = new ArrayList();
//        field_30900_E = new ArrayList();
//        playerEntities = new ArrayList();
//        weatherEffects = new ArrayList();
//        field_1019_F = 0xffffffL;
//        skylightSubtracted = 0;
//        field_9437_g = (new Random()).nextInt();
//        field_27168_F = 0;
//        field_27172_i = 0;
//        editingBlocks = false;
//        lockTimestamp = System.currentTimeMillis();
//        autosavePeriod = 40;
//        rand = new Random();
//        isNewWorld = false;
//        worldAccesses = new ArrayList();
//        collidingBoundingBoxes = new ArrayList();
//        lightingUpdatesCounter = 0;
//        spawnHostileMobs = true;
//        spawnPeacefulMobs = true;
//        positionsToUpdate = new HashSet();
//        soundCounter = rand.nextInt(12000);
//        field_1012_M = new ArrayList();
//        multiplayerWorld = false;
//        saveHandler = wt1;
//        worldInfo = new WorldInfo(l1, s1);
//        worldProvider = xa1;
//        field_28108_z = new MapStorage(wt1);
//        xa1.registerWorld(this);
//        chunkProvider = getChunkProvider();
//        calculateInitialSkylight();
//        func_27163_E();
//    }
//
//    public World(World fd1, net.minecraft.src.WorldProvider xa1)
//    {
//        scheduledUpdatesAreImmediate = false;
//        lightingToUpdate = new ArrayList();
//        loadedEntityList = new ArrayList();
//        unloadedEntityList = new ArrayList();
//        scheduledTickTreeSet = new TreeSet();
//        scheduledTickSet = new HashSet();
//        loadedTileEntityList = new ArrayList();
//        field_30900_E = new ArrayList();
//        playerEntities = new ArrayList();
//        weatherEffects = new ArrayList();
//        field_1019_F = 0xffffffL;
//        skylightSubtracted = 0;
//        field_9437_g = (new Random()).nextInt();
//        field_27168_F = 0;
//        field_27172_i = 0;
//        editingBlocks = false;
//        lockTimestamp = System.currentTimeMillis();
//        autosavePeriod = 40;
//        rand = new Random();
//        isNewWorld = false;
//        worldAccesses = new ArrayList();
//        collidingBoundingBoxes = new ArrayList();
//        lightingUpdatesCounter = 0;
//        spawnHostileMobs = true;
//        spawnPeacefulMobs = true;
//        positionsToUpdate = new HashSet();
//        soundCounter = rand.nextInt(12000);
//        field_1012_M = new ArrayList();
//        multiplayerWorld = false;
//        lockTimestamp = fd1.lockTimestamp;
//        saveHandler = fd1.saveHandler;
//        worldInfo = new WorldInfo(fd1.worldInfo);
//        field_28108_z = new MapStorage(saveHandler);
//        worldProvider = xa1;
//        xa1.registerWorld(this);
//        chunkProvider = getChunkProvider();
//        calculateInitialSkylight();
//        func_27163_E();
//    }
//
//    public World(ISaveHandler wt1, String s1, long l1)
//    {
//        this(wt1, s1, l1, ((net.minecraft.src.WorldProvider) (null)));
//    }
//
//    public World(ISaveHandler wt1, String s1, long l1, net.minecraft.src.WorldProvider xa1)
//    {
//        scheduledUpdatesAreImmediate = false;
//        lightingToUpdate = new ArrayList();
//        loadedEntityList = new ArrayList();
//        unloadedEntityList = new ArrayList();
//        scheduledTickTreeSet = new TreeSet();
//        scheduledTickSet = new HashSet();
//        loadedTileEntityList = new ArrayList();
//        field_30900_E = new ArrayList();
//        playerEntities = new ArrayList();
//        weatherEffects = new ArrayList();
//        field_1019_F = 0xffffffL;
//        skylightSubtracted = 0;
//        field_9437_g = (new Random()).nextInt();
//        field_27168_F = 0;
//        field_27172_i = 0;
//        editingBlocks = false;
//        lockTimestamp = System.currentTimeMillis();
//        autosavePeriod = 40;
//        rand = new Random();
//        isNewWorld = false;
//        worldAccesses = new ArrayList();
//        collidingBoundingBoxes = new ArrayList();
//        lightingUpdatesCounter = 0;
//        spawnHostileMobs = true;
//        spawnPeacefulMobs = true;
//        positionsToUpdate = new HashSet();
//        soundCounter = rand.nextInt(12000);
//        field_1012_M = new ArrayList();
//        multiplayerWorld = false;
//        saveHandler = wt1;
//        field_28108_z = new MapStorage(wt1);
//        worldInfo = wt1.loadWorldInfo();
//        isNewWorld = worldInfo == null;
//        if(xa1 != null)
//        {
//            worldProvider = xa1;
//        } else
//        {
//            int i1 = 0;
//            if(worldInfo != null)
//            {
//                i1 = worldInfo.getDimension();
//            }
//            DimensionBase localDimensionBase = DimensionBase.getDimByNumber(i1);
//            worldProvider = localDimensionBase.getWorldProvider();
//        }
//        boolean flag = false;
//        if(worldInfo == null)
//        {
//            worldInfo = new WorldInfo(l1, s1);
//            flag = true;
//        } else
//        {
//            worldInfo.setWorldName(s1);
//        }
//        worldProvider.registerWorld(this);
//        chunkProvider = getChunkProvider();
//        if(flag)
//        {
//            getInitialSpawnLocation();
//        }
//        calculateInitialSkylight();
//        func_27163_E();
//    }
//
//    protected IChunkProvider getChunkProvider()
//    {
//        IChunkLoader bf = saveHandler.getChunkLoader(worldProvider);
//        return new ChunkProvider(this, bf, worldProvider.getChunkProvider());
//    }
//
//    protected void getInitialSpawnLocation()
//    {
//        findingSpawnPoint = true;
//        int i1 = 0;
//        byte byte0 = 64;
//        int j1;
//        for(j1 = 0; !worldProvider.canCoordinateBeSpawn(i1, j1); j1 += rand.nextInt(64) - rand.nextInt(64))
//        {
//            i1 += rand.nextInt(64) - rand.nextInt(64);
//        }
//
//        worldInfo.setSpawn(i1, byte0, j1);
//        findingSpawnPoint = false;
//    }
//
//    public void setSpawnLocation()
//    {
//        if(worldInfo.getSpawnY() <= 0)
//        {
//            worldInfo.setSpawnY(64);
//        }
//        int i1 = worldInfo.getSpawnX();
//        int j1;
//        for(j1 = worldInfo.getSpawnZ(); getFirstUncoveredBlock(i1, j1) == 0; j1 += rand.nextInt(8) - rand.nextInt(8))
//        {
//            i1 += rand.nextInt(8) - rand.nextInt(8);
//        }
//
//        worldInfo.setSpawnX(i1);
//        worldInfo.setSpawnZ(j1);
//    }
//
//    public int getFirstUncoveredBlock(int i1, int j1)
//    {
//        int k1;
//        for(k1 = 63; !isAirBlock(i1, k1 + 1, j1); k1++) { }
//        return getBlockId(i1, k1, j1);
//    }
//
//    public void emptyMethod1()
//    {
//    }
//
//    public void spawnPlayerWithLoadedChunks(EntityPlayer gs1)
//    {
//        try
//        {
//            NBTTagCompound nu = worldInfo.getPlayerNBTTagCompound();
//            if(nu != null)
//            {
//                gs1.readFromNBT(nu);
//                worldInfo.setPlayerNBTTagCompound(null);
//            }
//            if(chunkProvider instanceof ChunkProviderLoadOrGenerate)
//            {
//                ChunkProviderLoadOrGenerate kx1 = (ChunkProviderLoadOrGenerate)chunkProvider;
//                int i1 = MathHelper.floor_float((int)gs1.posX) >> 4;
//                int j1 = MathHelper.floor_float((int)gs1.posZ) >> 4;
//                kx1.setCurrentChunkOver(i1, j1);
//            }
//            entityJoinedWorld(gs1);
//        }
//        catch(Exception exception)
//        {
//            exception.printStackTrace();
//        }
//    }
//
//    public void saveWorld(boolean flag, IProgressUpdate yb1)
//    {
//        if(!chunkProvider.canSave())
//        {
//            return;
//        }
//        if(yb1 != null)
//        {
//            yb1.displayProgressMessage("Saving level");
//        }
//        saveLevel();
//        if(yb1 != null)
//        {
//            yb1.displayLoadingString("Saving chunks");
//        }
//        chunkProvider.saveChunks(flag, yb1);
//    }
//
//    private void saveLevel()
//    {
//        checkSessionLock();
//        saveHandler.saveWorldInfoAndPlayer(worldInfo, playerEntities);
//        field_28108_z.saveAllData();
//    }
//
//    public boolean quickSaveWorld(int i1)
//    {
//        if(!chunkProvider.canSave())
//        {
//            return true;
//        }
//        if(i1 == 0)
//        {
//            saveLevel();
//        }
//        return chunkProvider.saveChunks(false, null);
//    }
//
//    public int getBlockId(int i1, int j1, int k1)
//    {
//        if(i1 < 0xfe17b800 || k1 < 0xfe17b800 || i1 >= 0x1e84800 || k1 > 0x1e84800)
//        {
//            return 0;
//        }
//        if(j1 < 0)
//        {
//            return 0;
//        }
//        if(j1 >= 128)
//        {
//            return 0;
//        } else
//        {
//            return getChunkFromChunkCoords(i1 >> 4, k1 >> 4).getBlockID(i1 & 0xf, j1, k1 & 0xf);
//        }
//    }
//
//    public boolean isAirBlock(int i1, int j1, int k1)
//    {
//        return getBlockId(i1, j1, k1) == 0;
//    }
//
//    public boolean blockExists(int i1, int j1, int k1)
//    {
//        if(j1 < 0 || j1 >= 128)
//        {
//            return false;
//        } else
//        {
//            return chunkExists(i1 >> 4, k1 >> 4);
//        }
//    }
//
//    public boolean doChunksNearChunkExist(int i1, int j1, int k1, int l1)
//    {
//        return checkChunksExist(i1 - l1, j1 - l1, k1 - l1, i1 + l1, j1 + l1, k1 + l1);
//    }
//
//    public boolean checkChunksExist(int i1, int j1, int k1, int l1, int i2, int j2)
//    {
//        if(i2 < 0 || j1 >= 128)
//        {
//            return false;
//        }
//        i1 >>= 4;
//        j1 >>= 4;
//        k1 >>= 4;
//        l1 >>= 4;
//        i2 >>= 4;
//        j2 >>= 4;
//        for(int k2 = i1; k2 <= l1; k2++)
//        {
//            for(int l2 = k1; l2 <= j2; l2++)
//            {
//                if(!chunkExists(k2, l2))
//                {
//                    return false;
//                }
//            }
//
//        }
//
//        return true;
//    }
//
//    private boolean chunkExists(int i1, int j1)
//    {
//        return chunkProvider.chunkExists(i1, j1);
//    }
//
//    public Chunk getChunkFromBlockCoords(int i1, int j1)
//    {
//        return getChunkFromChunkCoords(i1 >> 4, j1 >> 4);
//    }
//
//    public Chunk getChunkFromChunkCoords(int i1, int j1)
//    {
//        return chunkProvider.provideChunk(i1, j1);
//    }
//
//    public boolean setBlockAndMetadata(int i1, int j1, int k1, int l1, int i2)
//    {
//        if(i1 < 0xfe17b800 || k1 < 0xfe17b800 || i1 >= 0x1e84800 || k1 > 0x1e84800)
//        {
//            return false;
//        }
//        if(j1 < 0)
//        {
//            return false;
//        }
//        if(j1 >= 128)
//        {
//            return false;
//        } else
//        {
//            Chunk lm1 = getChunkFromChunkCoords(i1 >> 4, k1 >> 4);
//            l1 = SAPI.interceptBlockSet(this, new Loc(i1, j1, k1), l1);
//            return lm1.setBlockIDWithMetadata(i1 & 0xf, j1, k1 & 0xf, l1, i2);
//        }
//    }
//
//    public boolean setBlock(int i1, int j1, int k1, int l1)
//    {
//        if(i1 < 0xfe17b800 || k1 < 0xfe17b800 || i1 >= 0x1e84800 || k1 > 0x1e84800)
//        {
//            return false;
//        }
//        if(j1 < 0)
//        {
//            return false;
//        }
//        if(j1 >= 128)
//        {
//            return false;
//        } else
//        {
//            Chunk lm1 = getChunkFromChunkCoords(i1 >> 4, k1 >> 4);
//            l1 = SAPI.interceptBlockSet(this, new Loc(i1, j1, k1), l1);
//            return lm1.setBlockID(i1 & 0xf, j1, k1 & 0xf, l1);
//        }
//    }
//
//    public Material getBlockMaterial(int i1, int j1, int k1)
//    {
//        int l1 = getBlockId(i1, j1, k1);
//        if(l1 == 0)
//        {
//            return Material.air;
//        } else
//        {
//            return Block.blocksList[l1].blockMaterial;
//        }
//    }
//
//    public int getBlockMetadata(int i1, int j1, int k1)
//    {
//        if(i1 < 0xfe17b800 || k1 < 0xfe17b800 || i1 >= 0x1e84800 || k1 > 0x1e84800)
//        {
//            return 0;
//        }
//        if(j1 < 0)
//        {
//            return 0;
//        }
//        if(j1 >= 128)
//        {
//            return 0;
//        } else
//        {
//            Chunk lm1 = getChunkFromChunkCoords(i1 >> 4, k1 >> 4);
//            i1 &= 0xf;
//            k1 &= 0xf;
//            return lm1.getBlockMetadata(i1, j1, k1);
//        }
//    }
//
//    public void setBlockMetadataWithNotify(int i1, int j1, int k1, int l1)
//    {
//        if(setBlockMetadata(i1, j1, k1, l1))
//        {
//            int i2 = getBlockId(i1, j1, k1);
//            if(Block.setNeighborNotifyOnMetadataChange[i2 & 0xff])
//            {
//                notifyBlockChange(i1, j1, k1, i2);
//            } else
//            {
//                notifyBlocksOfNeighborChange(i1, j1, k1, i2);
//            }
//        }
//    }
//
//    public boolean setBlockMetadata(int i1, int j1, int k1, int l1)
//    {
//        if(i1 < 0xfe17b800 || k1 < 0xfe17b800 || i1 >= 0x1e84800 || k1 > 0x1e84800)
//        {
//            return false;
//        }
//        if(j1 < 0)
//        {
//            return false;
//        }
//        if(j1 >= 128)
//        {
//            return false;
//        } else
//        {
//            Chunk lm1 = getChunkFromChunkCoords(i1 >> 4, k1 >> 4);
//            i1 &= 0xf;
//            k1 &= 0xf;
//            lm1.setBlockMetadata(i1, j1, k1, l1);
//            return true;
//        }
//    }
//
//    public boolean setBlockWithNotify(int i1, int j1, int k1, int l1)
//    {
//        if(setBlock(i1, j1, k1, l1))
//        {
//            notifyBlockChange(i1, j1, k1, l1);
//            return true;
//        } else
//        {
//            return false;
//        }
//    }
//
//    public boolean setBlockAndMetadataWithNotify(int i1, int j1, int k1, int l1, int i2)
//    {
//        if(setBlockAndMetadata(i1, j1, k1, l1, i2))
//        {
//            notifyBlockChange(i1, j1, k1, l1);
//            return true;
//        } else
//        {
//            return false;
//        }
//    }
//
//    public void markBlockNeedsUpdate(int i1, int j1, int k1)
//    {
//        for(int l1 = 0; l1 < worldAccesses.size(); l1++)
//        {
//            ((IWorldAccess)worldAccesses.get(l1)).markBlockAndNeighborsNeedsUpdate(i1, j1, k1);
//        }
//
//    }
//
//    protected void notifyBlockChange(int i1, int j1, int k1, int l1)
//    {
//        markBlockNeedsUpdate(i1, j1, k1);
//        notifyBlocksOfNeighborChange(i1, j1, k1, l1);
//    }
//
//    public void markBlocksDirtyVertical(int i1, int j1, int k1, int l1)
//    {
//        if(k1 > l1)
//        {
//            int i2 = l1;
//            l1 = k1;
//            k1 = i2;
//        }
//        markBlocksDirty(i1, k1, j1, i1, l1, j1);
//    }
//
//    public void markBlockAsNeedsUpdate(int i1, int j1, int k1)
//    {
//        for(int l1 = 0; l1 < worldAccesses.size(); l1++)
//        {
//            ((IWorldAccess)worldAccesses.get(l1)).markBlockRangeNeedsUpdate(i1, j1, k1, i1, j1, k1);
//        }
//
//    }
//
//    public void markBlocksDirty(int i1, int j1, int k1, int l1, int i2, int j2)
//    {
//        for(int k2 = 0; k2 < worldAccesses.size(); k2++)
//        {
//            ((IWorldAccess)worldAccesses.get(k2)).markBlockRangeNeedsUpdate(i1, j1, k1, l1, i2, j2);
//        }
//
//    }
//
//    public void notifyBlocksOfNeighborChange(int i1, int j1, int k1, int l1)
//    {
//        notifyBlockOfNeighborChange(i1 - 1, j1, k1, l1);
//        notifyBlockOfNeighborChange(i1 + 1, j1, k1, l1);
//        notifyBlockOfNeighborChange(i1, j1 - 1, k1, l1);
//        notifyBlockOfNeighborChange(i1, j1 + 1, k1, l1);
//        notifyBlockOfNeighborChange(i1, j1, k1 - 1, l1);
//        notifyBlockOfNeighborChange(i1, j1, k1 + 1, l1);
//    }
//
//    private void notifyBlockOfNeighborChange(int i1, int j1, int k1, int l1)
//    {
//        if(editingBlocks || multiplayerWorld)
//        {
//            return;
//        }
//        Block uu1 = Block.blocksList[getBlockId(i1, j1, k1)];
//        if(uu1 != null)
//        {
//            uu1.onNeighborBlockChange(this, i1, j1, k1, l1);
//        }
//    }
//
//    public boolean canBlockSeeTheSky(int i1, int j1, int k1)
//    {
//        return getChunkFromChunkCoords(i1 >> 4, k1 >> 4).canBlockSeeTheSky(i1 & 0xf, j1, k1 & 0xf);
//    }
//
//    public int getFullBlockLightValue(int i1, int j1, int k1)
//    {
//        if(j1 < 0)
//        {
//            return 0;
//        }
//        if(j1 >= 128)
//        {
//            j1 = 127;
//        }
//        return getChunkFromChunkCoords(i1 >> 4, k1 >> 4).getBlockLightValue(i1 & 0xf, j1, k1 & 0xf, 0);
//    }
//
//    public int getBlockLightValue(int i1, int j1, int k1)
//    {
//        return getBlockLightValue_do(i1, j1, k1, true);
//    }
//
//    public int getBlockLightValue_do(int i1, int j1, int k1, boolean flag)
//    {
//        if(i1 < 0xfe17b800 || k1 < 0xfe17b800 || i1 >= 0x1e84800 || k1 > 0x1e84800)
//        {
//            return 15;
//        }
//        if(flag)
//        {
//            int l1 = getBlockId(i1, j1, k1);
//            if(l1 == Block.stairSingle.blockID || l1 == Block.tilledField.blockID || l1 == Block.stairCompactCobblestone.blockID || l1 == Block.stairCompactPlanks.blockID)
//            {
//                int i2 = getBlockLightValue_do(i1, j1 + 1, k1, false);
//                int j2 = getBlockLightValue_do(i1 + 1, j1, k1, false);
//                int k2 = getBlockLightValue_do(i1 - 1, j1, k1, false);
//                int l2 = getBlockLightValue_do(i1, j1, k1 + 1, false);
//                int i3 = getBlockLightValue_do(i1, j1, k1 - 1, false);
//                if(j2 > i2)
//                {
//                    i2 = j2;
//                }
//                if(k2 > i2)
//                {
//                    i2 = k2;
//                }
//                if(l2 > i2)
//                {
//                    i2 = l2;
//                }
//                if(i3 > i2)
//                {
//                    i2 = i3;
//                }
//                return i2;
//            }
//        }
//        if(j1 < 0)
//        {
//            return 0;
//        }
//        if(j1 >= 128)
//        {
//            j1 = 127;
//        }
//        Chunk lm1 = getChunkFromChunkCoords(i1 >> 4, k1 >> 4);
//        i1 &= 0xf;
//        k1 &= 0xf;
//        return lm1.getBlockLightValue(i1, j1, k1, skylightSubtracted);
//    }
//
//    public boolean canExistingBlockSeeTheSky(int i1, int j1, int k1)
//    {
//        if(i1 < 0xfe17b800 || k1 < 0xfe17b800 || i1 >= 0x1e84800 || k1 > 0x1e84800)
//        {
//            return false;
//        }
//        if(j1 < 0)
//        {
//            return false;
//        }
//        if(j1 >= 128)
//        {
//            return true;
//        }
//        if(!chunkExists(i1 >> 4, k1 >> 4))
//        {
//            return false;
//        } else
//        {
//            Chunk lm1 = getChunkFromChunkCoords(i1 >> 4, k1 >> 4);
//            i1 &= 0xf;
//            k1 &= 0xf;
//            return lm1.canBlockSeeTheSky(i1, j1, k1);
//        }
//    }
//
//    public int getHeightValue(int i1, int j1)
//    {
//        if(i1 < 0xfe17b800 || j1 < 0xfe17b800 || i1 >= 0x1e84800 || j1 > 0x1e84800)
//        {
//            return 0;
//        }
//        if(!chunkExists(i1 >> 4, j1 >> 4))
//        {
//            return 0;
//        } else
//        {
//            Chunk lm1 = getChunkFromChunkCoords(i1 >> 4, j1 >> 4);
//            return lm1.getHeightValue(i1 & 0xf, j1 & 0xf);
//        }
//    }
//
//    public void neighborLightPropagationChanged(EnumSkyBlock eb1, int i1, int j1, int k1, int l1)
//    {
//        if(worldProvider.hasNoSky && eb1 == EnumSkyBlock.Sky)
//        {
//            return;
//        }
//        if(!blockExists(i1, j1, k1))
//        {
//            return;
//        }
//        if(eb1 == EnumSkyBlock.Sky)
//        {
//            if(canExistingBlockSeeTheSky(i1, j1, k1))
//            {
//                l1 = 15;
//            }
//        } else
//        if(eb1 == EnumSkyBlock.Block)
//        {
//            int i2 = getBlockId(i1, j1, k1);
//            if(Block.lightValue[i2] > l1)
//            {
//                l1 = Block.lightValue[i2];
//            }
//        }
//        if(getSavedLightValue(eb1, i1, j1, k1) != l1)
//        {
//            scheduleLightingUpdate(eb1, i1, j1, k1, i1, j1, k1);
//        }
//    }
//
//    public int getSavedLightValue(EnumSkyBlock eb1, int i1, int j1, int k1)
//    {
//        if(j1 < 0)
//        {
//            j1 = 0;
//        }
//        if(j1 >= 128)
//        {
//            j1 = 127;
//        }
//        if(j1 < 0 || j1 >= 128 || i1 < 0xfe17b800 || k1 < 0xfe17b800 || i1 >= 0x1e84800 || k1 > 0x1e84800)
//        {
//            return eb1.defaultLightValue;
//        }
//        int l1 = i1 >> 4;
//        int i2 = k1 >> 4;
//        if(!chunkExists(l1, i2))
//        {
//            return 0;
//        } else
//        {
//            Chunk lm1 = getChunkFromChunkCoords(l1, i2);
//            return lm1.getSavedLightValue(eb1, i1 & 0xf, j1, k1 & 0xf);
//        }
//    }
//
//    public void setLightValue(EnumSkyBlock eb1, int i1, int j1, int k1, int l1)
//    {
//        if(i1 < 0xfe17b800 || k1 < 0xfe17b800 || i1 >= 0x1e84800 || k1 > 0x1e84800)
//        {
//            return;
//        }
//        if(j1 < 0)
//        {
//            return;
//        }
//        if(j1 >= 128)
//        {
//            return;
//        }
//        if(!chunkExists(i1 >> 4, k1 >> 4))
//        {
//            return;
//        }
//        Chunk lm1 = getChunkFromChunkCoords(i1 >> 4, k1 >> 4);
//        lm1.setLightValue(eb1, i1 & 0xf, j1, k1 & 0xf, l1);
//        for(int i2 = 0; i2 < worldAccesses.size(); i2++)
//        {
//            ((IWorldAccess)worldAccesses.get(i2)).markBlockAndNeighborsNeedsUpdate(i1, j1, k1);
//        }
//
//    }
//
//    public float getBrightness(int i1, int j1, int k1, int l1)
//    {
//        int i2 = getBlockLightValue(i1, j1, k1);
//        if(i2 < l1)
//        {
//            i2 = l1;
//        }
//        return worldProvider.lightBrightnessTable[i2];
//    }
//
//    public float getLightBrightness(int i1, int j1, int k1)
//    {
//        return worldProvider.lightBrightnessTable[getBlockLightValue(i1, j1, k1)];
//    }
//
//    public boolean isDaytime()
//    {
//        return skylightSubtracted < 4;
//    }
//
//    public MovingObjectPosition rayTraceBlocks(Vec3D bt1, Vec3D bt2)
//    {
//        return rayTraceBlocks_do_do(bt1, bt2, false, false);
//    }
//
//    public MovingObjectPosition rayTraceBlocks_do(Vec3D bt1, Vec3D bt2, boolean flag)
//    {
//        return rayTraceBlocks_do_do(bt1, bt2, flag, false);
//    }
//
//    public MovingObjectPosition rayTraceBlocks_do_do(Vec3D bt1, Vec3D bt2, boolean flag, boolean flag1)
//    {
//        if(Double.isNaN(bt1.xCoord) || Double.isNaN(bt1.yCoord) || Double.isNaN(bt1.zCoord))
//        {
//            return null;
//        }
//        if(Double.isNaN(bt2.xCoord) || Double.isNaN(bt2.yCoord) || Double.isNaN(bt2.zCoord))
//        {
//            return null;
//        }
//        int i1 = MathHelper.floor_double(bt2.xCoord);
//        int j1 = MathHelper.floor_double(bt2.yCoord);
//        int k1 = MathHelper.floor_double(bt2.zCoord);
//        int l1 = MathHelper.floor_double(bt1.xCoord);
//        int i2 = MathHelper.floor_double(bt1.yCoord);
//        int j2 = MathHelper.floor_double(bt1.zCoord);
//        int k2 = getBlockId(l1, i2, j2);
//        int i3 = getBlockMetadata(l1, i2, j2);
//        Block uu1 = Block.blocksList[k2];
//        if((!flag1 || uu1 == null || uu1.getCollisionBoundingBoxFromPool(this, l1, i2, j2) != null) && k2 > 0 && uu1.canCollideCheck(i3, flag))
//        {
//            MovingObjectPosition vf = uu1.collisionRayTrace(this, l1, i2, j2, bt1, bt2);
//            if(vf != null)
//            {
//                return vf;
//            }
//        }
//        for(int l2 = 200; l2-- >= 0;)
//        {
//            if(Double.isNaN(bt1.xCoord) || Double.isNaN(bt1.yCoord) || Double.isNaN(bt1.zCoord))
//            {
//                return null;
//            }
//            if(l1 == i1 && i2 == j1 && j2 == k1)
//            {
//                return null;
//            }
//            boolean flag2 = true;
//            boolean flag3 = true;
//            boolean flag4 = true;
//            double d1 = 999D;
//            double d2 = 999D;
//            double d3 = 999D;
//            if(i1 > l1)
//            {
//                d1 = (double)l1 + 1.0D;
//            } else
//            if(i1 < l1)
//            {
//                d1 = (double)l1 + 0.0D;
//            } else
//            {
//                flag2 = false;
//            }
//            if(j1 > i2)
//            {
//                d2 = (double)i2 + 1.0D;
//            } else
//            if(j1 < i2)
//            {
//                d2 = (double)i2 + 0.0D;
//            } else
//            {
//                flag3 = false;
//            }
//            if(k1 > j2)
//            {
//                d3 = (double)j2 + 1.0D;
//            } else
//            if(k1 < j2)
//            {
//                d3 = (double)j2 + 0.0D;
//            } else
//            {
//                flag4 = false;
//            }
//            double d4 = 999D;
//            double d5 = 999D;
//            double d6 = 999D;
//            double d7 = bt2.xCoord - bt1.xCoord;
//            double d8 = bt2.yCoord - bt1.yCoord;
//            double d9 = bt2.zCoord - bt1.zCoord;
//            if(flag2)
//            {
//                d4 = (d1 - bt1.xCoord) / d7;
//            }
//            if(flag3)
//            {
//                d5 = (d2 - bt1.yCoord) / d8;
//            }
//            if(flag4)
//            {
//                d6 = (d3 - bt1.zCoord) / d9;
//            }
//            byte byte0 = 0;
//            if(d4 < d5 && d4 < d6)
//            {
//                if(i1 > l1)
//                {
//                    byte0 = 4;
//                } else
//                {
//                    byte0 = 5;
//                }
//                bt1.xCoord = d1;
//                bt1.yCoord += d8 * d4;
//                bt1.zCoord += d9 * d4;
//            } else
//            if(d5 < d6)
//            {
//                if(j1 > i2)
//                {
//                    byte0 = 0;
//                } else
//                {
//                    byte0 = 1;
//                }
//                bt1.xCoord += d7 * d5;
//                bt1.yCoord = d2;
//                bt1.zCoord += d9 * d5;
//            } else
//            {
//                if(k1 > j2)
//                {
//                    byte0 = 2;
//                } else
//                {
//                    byte0 = 3;
//                }
//                bt1.xCoord += d7 * d6;
//                bt1.yCoord += d8 * d6;
//                bt1.zCoord = d3;
//            }
//            Vec3D bt3 = Vec3D.createVector(bt1.xCoord, bt1.yCoord, bt1.zCoord);
//            l1 = (int)(bt3.xCoord = MathHelper.floor_double(bt1.xCoord));
//            if(byte0 == 5)
//            {
//                l1--;
//                bt3.xCoord++;
//            }
//            i2 = (int)(bt3.yCoord = MathHelper.floor_double(bt1.yCoord));
//            if(byte0 == 1)
//            {
//                i2--;
//                bt3.yCoord++;
//            }
//            j2 = (int)(bt3.zCoord = MathHelper.floor_double(bt1.zCoord));
//            if(byte0 == 3)
//            {
//                j2--;
//                bt3.zCoord++;
//            }
//            int j3 = getBlockId(l1, i2, j2);
//            int k3 = getBlockMetadata(l1, i2, j2);
//            Block uu2 = Block.blocksList[j3];
//            if((!flag1 || uu2 == null || uu2.getCollisionBoundingBoxFromPool(this, l1, i2, j2) != null) && j3 > 0 && uu2.canCollideCheck(k3, flag))
//            {
//                MovingObjectPosition vf1 = uu2.collisionRayTrace(this, l1, i2, j2, bt1, bt2);
//                if(vf1 != null)
//                {
//                    return vf1;
//                }
//            }
//        }
//
//        return null;
//    }
//
//    public void playSoundAtEntity(Entity sn1, String s1, float f1, float f2)
//    {
//        for(int i1 = 0; i1 < worldAccesses.size(); i1++)
//        {
//            ((IWorldAccess)worldAccesses.get(i1)).playSound(s1, sn1.posX, sn1.posY - (double)sn1.yOffset, sn1.posZ, f1, f2);
//        }
//
//    }
//
//    public void playSoundEffect(double d1, double d2, double d3, String s1,
//            float f1, float f2)
//    {
//        for(int i1 = 0; i1 < worldAccesses.size(); i1++)
//        {
//            ((IWorldAccess)worldAccesses.get(i1)).playSound(s1, d1, d2, d3, f1, f2);
//        }
//
//    }
//
//    public void playRecord(String s1, int i1, int j1, int k1)
//    {
//        for(int l1 = 0; l1 < worldAccesses.size(); l1++)
//        {
//            ((IWorldAccess)worldAccesses.get(l1)).playRecord(s1, i1, j1, k1);
//        }
//
//    }
//
//    public void spawnParticle(String s1, double d1, double d2, double d3,
//            double d4, double d5, double d6)
//    {
//        for(int i1 = 0; i1 < worldAccesses.size(); i1++)
//        {
//            ((IWorldAccess)worldAccesses.get(i1)).spawnParticle(s1, d1, d2, d3, d4, d5, d6);
//        }
//
//    }
//
//    public boolean addWeatherEffect(Entity sn1)
//    {
//        weatherEffects.add(sn1);
//        return true;
//    }
//
//    public boolean entityJoinedWorld(Entity sn1)
//    {
//        int i1 = MathHelper.floor_double(sn1.posX / 16D);
//        int j1 = MathHelper.floor_double(sn1.posZ / 16D);
//        boolean flag = false;
//        if(sn1 instanceof EntityPlayer)
//        {
//            flag = true;
//        }
//        if(flag || chunkExists(i1, j1))
//        {
//            if(sn1 instanceof EntityPlayer)
//            {
//                EntityPlayer gs1 = (EntityPlayer)sn1;
//                playerEntities.add(gs1);
//                updateAllPlayersSleepingFlag();
//            }
//            getChunkFromChunkCoords(i1, j1).addEntity(sn1);
//            loadedEntityList.add(sn1);
//            obtainEntitySkin(sn1);
//            return true;
//        } else
//        {
//            return false;
//        }
//    }
//
//    protected void obtainEntitySkin(Entity sn1)
//    {
//        for(int i1 = 0; i1 < worldAccesses.size(); i1++)
//        {
//            ((IWorldAccess)worldAccesses.get(i1)).obtainEntitySkin(sn1);
//        }
//
//    }
//
//    protected void releaseEntitySkin(Entity sn1)
//    {
//        for(int i1 = 0; i1 < worldAccesses.size(); i1++)
//        {
//            ((IWorldAccess)worldAccesses.get(i1)).releaseEntitySkin(sn1);
//        }
//
//    }
//
//    public void setEntityDead(Entity sn1)
//    {
//        if(sn1.riddenByEntity != null)
//        {
//            sn1.riddenByEntity.mountEntity(null);
//        }
//        if(sn1.ridingEntity != null)
//        {
//            sn1.mountEntity(null);
//        }
//        sn1.setEntityDead();
//        if(sn1 instanceof EntityPlayer)
//        {
//            playerEntities.remove((EntityPlayer)sn1);
//            updateAllPlayersSleepingFlag();
//        }
//    }
//
//    public void addWorldAccess(IWorldAccess pm1)
//    {
//        worldAccesses.add(pm1);
//    }
//
//    public void removeWorldAccess(IWorldAccess pm1)
//    {
//        worldAccesses.remove(pm1);
//    }
//
//    public List getCollidingBoundingBoxes(Entity sn1, AxisAlignedBB eq1)
//    {
//        collidingBoundingBoxes.clear();
//        int i1 = MathHelper.floor_double(eq1.minX);
//        int j1 = MathHelper.floor_double(eq1.maxX + 1.0D);
//        int k1 = MathHelper.floor_double(eq1.minY);
//        int l1 = MathHelper.floor_double(eq1.maxY + 1.0D);
//        int i2 = MathHelper.floor_double(eq1.minZ);
//        int j2 = MathHelper.floor_double(eq1.maxZ + 1.0D);
//        for(int k2 = i1; k2 < j1; k2++)
//        {
//            for(int l2 = i2; l2 < j2; l2++)
//            {
//                if(blockExists(k2, 64, l2))
//                {
//                    for(int i3 = k1 - 1; i3 < l1; i3++)
//                    {
//                        Block uu1 = Block.blocksList[getBlockId(k2, i3, l2)];
//                        if(uu1 != null)
//                        {
//                            uu1.getCollidingBoundingBoxes(this, k2, i3, l2, eq1, collidingBoundingBoxes);
//                        }
//                    }
//
//                }
//            }
//
//        }
//
//        double d1 = 0.25D;
//        List list = getEntitiesWithinAABBExcludingEntity(sn1, eq1.expand(d1, d1, d1));
//        for(int j3 = 0; j3 < list.size(); j3++)
//        {
//            AxisAlignedBB eq2 = ((Entity)list.get(j3)).getBoundingBox();
//            if(eq2 != null && eq2.intersectsWith(eq1))
//            {
//                collidingBoundingBoxes.add(eq2);
//            }
//            eq2 = sn1.getCollisionBox((Entity)list.get(j3));
//            if(eq2 != null && eq2.intersectsWith(eq1))
//            {
//                collidingBoundingBoxes.add(eq2);
//            }
//        }
//
//        return collidingBoundingBoxes;
//    }
//
//    public int calculateSkylightSubtracted(float f1)
//    {
//        float f2 = getCelestialAngle(f1);
//        float f3 = 1.0F - (MathHelper.cos(f2 * 3.141593F * 2.0F) * 2.0F + 0.5F);
//        if(f3 < 0.0F)
//        {
//            f3 = 0.0F;
//        }
//        if(f3 > 1.0F)
//        {
//            f3 = 1.0F;
//        }
//        f3 = 1.0F - f3;
//        f3 = (float)((double)f3 * (1.0D - (double)(getRainStrength(f1) * 5F) / 16D));
//        f3 = (float)((double)f3 * (1.0D - (double)(getWeightedThunderStrength(f1) * 5F) / 16D));
//        f3 = 1.0F - f3;
//        return (int)(f3 * 11F);
//    }
//
//    public Vec3D getSkyColor(Entity sn1, float f1)
//    {
//        float f2 = getCelestialAngle(f1);
//        float f3 = MathHelper.cos(f2 * 3.141593F * 2.0F) * 2.0F + 0.5F;
//        if(f3 < 0.0F)
//        {
//            f3 = 0.0F;
//        }
//        if(f3 > 1.0F)
//        {
//            f3 = 1.0F;
//        }
//        int i1 = MathHelper.floor_double(sn1.posX);
//        int j1 = MathHelper.floor_double(sn1.posZ);
//        float f4 = (float)getWorldChunkManager().getTemperature(i1, j1);
//        int k1 = getWorldChunkManager().getBiomeGenAt(i1, j1).getSkyColorByTemp(f4);
//        float f5 = (float)(k1 >> 16 & 0xff) / 255F;
//        float f6 = (float)(k1 >> 8 & 0xff) / 255F;
//        float f7 = (float)(k1 & 0xff) / 255F;
//        f5 *= f3;
//        f6 *= f3;
//        f7 *= f3;
//        float f8 = getRainStrength(f1);
//        if(f8 > 0.0F)
//        {
//            float f9 = (f5 * 0.3F + f6 * 0.59F + f7 * 0.11F) * 0.6F;
//            float f11 = 1.0F - f8 * 0.75F;
//            f5 = f5 * f11 + f9 * (1.0F - f11);
//            f6 = f6 * f11 + f9 * (1.0F - f11);
//            f7 = f7 * f11 + f9 * (1.0F - f11);
//        }
//        float f10 = getWeightedThunderStrength(f1);
//        if(f10 > 0.0F)
//        {
//            float f12 = (f5 * 0.3F + f6 * 0.59F + f7 * 0.11F) * 0.2F;
//            float f14 = 1.0F - f10 * 0.75F;
//            f5 = f5 * f14 + f12 * (1.0F - f14);
//            f6 = f6 * f14 + f12 * (1.0F - f14);
//            f7 = f7 * f14 + f12 * (1.0F - f14);
//        }
//        if(field_27172_i > 0)
//        {
//            float f13 = (float)field_27172_i - f1;
//            if(f13 > 1.0F)
//            {
//                f13 = 1.0F;
//            }
//            f13 *= 0.45F;
//            f5 = f5 * (1.0F - f13) + 0.8F * f13;
//            f6 = f6 * (1.0F - f13) + 0.8F * f13;
//            f7 = f7 * (1.0F - f13) + 1.0F * f13;
//        }
//        return Vec3D.createVector(f5, f6, f7);
//    }
//
//    public float getCelestialAngle(float f1)
//    {
//        return worldProvider.calculateCelestialAngle(worldInfo.getWorldTime(), f1);
//    }
//
//    public Vec3D drawClouds(float f1)
//    {
//        float f2 = getCelestialAngle(f1);
//        float f3 = MathHelper.cos(f2 * 3.141593F * 2.0F) * 2.0F + 0.5F;
//        if(f3 < 0.0F)
//        {
//            f3 = 0.0F;
//        }
//        if(f3 > 1.0F)
//        {
//            f3 = 1.0F;
//        }
//        float f4 = (float)(field_1019_F >> 16 & 255L) / 255F;
//        float f5 = (float)(field_1019_F >> 8 & 255L) / 255F;
//        float f6 = (float)(field_1019_F & 255L) / 255F;
//        float f7 = getRainStrength(f1);
//        if(f7 > 0.0F)
//        {
//            float f8 = (f4 * 0.3F + f5 * 0.59F + f6 * 0.11F) * 0.6F;
//            float f10 = 1.0F - f7 * 0.95F;
//            f4 = f4 * f10 + f8 * (1.0F - f10);
//            f5 = f5 * f10 + f8 * (1.0F - f10);
//            f6 = f6 * f10 + f8 * (1.0F - f10);
//        }
//        f4 *= f3 * 0.9F + 0.1F;
//        f5 *= f3 * 0.9F + 0.1F;
//        f6 *= f3 * 0.85F + 0.15F;
//        float f9 = getWeightedThunderStrength(f1);
//        if(f9 > 0.0F)
//        {
//            float f11 = (f4 * 0.3F + f5 * 0.59F + f6 * 0.11F) * 0.2F;
//            float f12 = 1.0F - f9 * 0.95F;
//            f4 = f4 * f12 + f11 * (1.0F - f12);
//            f5 = f5 * f12 + f11 * (1.0F - f12);
//            f6 = f6 * f12 + f11 * (1.0F - f12);
//        }
//        return Vec3D.createVector(f4, f5, f6);
//    }
//
//    public Vec3D getFogColor(float f1)
//    {
//        float f2 = getCelestialAngle(f1);
//        return worldProvider.func_4096_a(f2, f1);
//    }
//
//    public int findTopSolidBlock(int i1, int j1)
//    {
//        Chunk lm1 = getChunkFromBlockCoords(i1, j1);
//        int k1 = 127;
//        i1 &= 0xf;
//        j1 &= 0xf;
//        while(k1 > 0)
//        {
//            int l1 = lm1.getBlockID(i1, k1, j1);
//            Material ln1 = l1 == 0 ? Material.air : Block.blocksList[l1].blockMaterial;
//            if(!ln1.getIsSolid() && !ln1.getIsLiquid())
//            {
//                k1--;
//            } else
//            {
//                return k1 + 1;
//            }
//        }
//        return -1;
//    }
//
//    public float getStarBrightness(float f1)
//    {
//        float f2 = getCelestialAngle(f1);
//        float f3 = 1.0F - (MathHelper.cos(f2 * 3.141593F * 2.0F) * 2.0F + 0.75F);
//        if(f3 < 0.0F)
//        {
//            f3 = 0.0F;
//        }
//        if(f3 > 1.0F)
//        {
//            f3 = 1.0F;
//        }
//        return f3 * f3 * 0.5F;
//    }
//
//    public void scheduleBlockUpdate(int i1, int j1, int k1, int l1, int i2)
//    {
//        NextTickListEntry qy1 = new NextTickListEntry(i1, j1, k1, l1);
//        byte byte0 = 8;
//        if(scheduledUpdatesAreImmediate)
//        {
//            if(checkChunksExist(qy1.xCoord - byte0, qy1.yCoord - byte0, qy1.zCoord - byte0, qy1.xCoord + byte0, qy1.yCoord + byte0, qy1.zCoord + byte0))
//            {
//                int j2 = getBlockId(qy1.xCoord, qy1.yCoord, qy1.zCoord);
//                if(j2 == qy1.blockID && j2 > 0)
//                {
//                    Block.blocksList[j2].updateTick(this, qy1.xCoord, qy1.yCoord, qy1.zCoord, rand);
//                }
//            }
//            return;
//        }
//        if(checkChunksExist(i1 - byte0, j1 - byte0, k1 - byte0, i1 + byte0, j1 + byte0, k1 + byte0))
//        {
//            if(l1 > 0)
//            {
//                qy1.setScheduledTime((long)i2 + worldInfo.getWorldTime());
//            }
//            if(!scheduledTickSet.contains(qy1))
//            {
//                scheduledTickSet.add(qy1);
//                scheduledTickTreeSet.add(qy1);
//            }
//        }
//    }
//
//    public void updateEntities()
//    {
//        for(int i1 = 0; i1 < weatherEffects.size(); i1++)
//        {
//            Entity sn1 = (Entity)weatherEffects.get(i1);
//            sn1.onUpdate();
//            if(sn1.isDead)
//            {
//                weatherEffects.remove(i1--);
//            }
//        }
//
//        loadedEntityList.removeAll(unloadedEntityList);
//        for(int j1 = 0; j1 < unloadedEntityList.size(); j1++)
//        {
//            Entity sn2 = (Entity)unloadedEntityList.get(j1);
//            int i2 = sn2.chunkCoordX;
//            int k2 = sn2.chunkCoordZ;
//            if(sn2.addedToChunk && chunkExists(i2, k2))
//            {
//                getChunkFromChunkCoords(i2, k2).removeEntity(sn2);
//            }
//        }
//
//        for(int k1 = 0; k1 < unloadedEntityList.size(); k1++)
//        {
//            releaseEntitySkin((Entity)unloadedEntityList.get(k1));
//        }
//
//        unloadedEntityList.clear();
//        for(int l1 = 0; l1 < loadedEntityList.size(); l1++)
//        {
//            Entity sn3 = (Entity)loadedEntityList.get(l1);
//            if(sn3.ridingEntity != null)
//            {
//                if(!sn3.ridingEntity.isDead && sn3.ridingEntity.riddenByEntity == sn3)
//                {
//                    continue;
//                }
//                sn3.ridingEntity.riddenByEntity = null;
//                sn3.ridingEntity = null;
//            }
//            if(!sn3.isDead)
//            {
//                updateEntity(sn3);
//            }
//            if(sn3.isDead)
//            {
//                int j2 = sn3.chunkCoordX;
//                int l2 = sn3.chunkCoordZ;
//                if(sn3.addedToChunk && chunkExists(j2, l2))
//                {
//                    getChunkFromChunkCoords(j2, l2).removeEntity(sn3);
//                }
//                loadedEntityList.remove(l1--);
//                releaseEntitySkin(sn3);
//            }
//        }
//
//        field_31055_L = true;
//        Iterator iterator = loadedTileEntityList.iterator();
//        do
//        {
//            if(!iterator.hasNext())
//            {
//                break;
//            }
//            TileEntity ow1 = (TileEntity)iterator.next();
//            if(!ow1.isInvalid())
//            {
//                ow1.updateEntity();
//            }
//            if(ow1.isInvalid())
//            {
//                iterator.remove();
//                Chunk lm1 = getChunkFromChunkCoords(ow1.xCoord >> 4, ow1.zCoord >> 4);
//                if(lm1 != null)
//                {
//                    lm1.removeChunkBlockTileEntity(ow1.xCoord & 0xf, ow1.yCoord, ow1.zCoord & 0xf);
//                }
//            }
//        } while(true);
//        field_31055_L = false;
//        if(!field_30900_E.isEmpty())
//        {
//            Iterator iterator1 = field_30900_E.iterator();
//            do
//            {
//                if(!iterator1.hasNext())
//                {
//                    break;
//                }
//                TileEntity ow2 = (TileEntity)iterator1.next();
//                if(!ow2.isInvalid())
//                {
//                    if(!loadedTileEntityList.contains(ow2))
//                    {
//                        loadedTileEntityList.add(ow2);
//                    }
//                    Chunk lm2 = getChunkFromChunkCoords(ow2.xCoord >> 4, ow2.zCoord >> 4);
//                    if(lm2 != null)
//                    {
//                        lm2.setChunkBlockTileEntity(ow2.xCoord & 0xf, ow2.yCoord, ow2.zCoord & 0xf, ow2);
//                    }
//                    markBlockNeedsUpdate(ow2.xCoord, ow2.yCoord, ow2.zCoord);
//                }
//            } while(true);
//            field_30900_E.clear();
//        }
//    }
//
//    public void addTileEntity(Collection collection)
//    {
//        if(field_31055_L)
//        {
//            field_30900_E.addAll(collection);
//        } else
//        {
//            loadedTileEntityList.addAll(collection);
//        }
//    }
//
//    public void updateEntity(Entity sn1)
//    {
//        updateEntityWithOptionalForce(sn1, true);
//    }
//
//    public void updateEntityWithOptionalForce(Entity sn1, boolean flag)
//    {
//        int i1 = MathHelper.floor_double(sn1.posX);
//        int j1 = MathHelper.floor_double(sn1.posZ);
//        byte byte0 = 32;
//        if(flag && !checkChunksExist(i1 - byte0, 0, j1 - byte0, i1 + byte0, 128, j1 + byte0))
//        {
//            return;
//        }
//        sn1.lastTickPosX = sn1.posX;
//        sn1.lastTickPosY = sn1.posY;
//        sn1.lastTickPosZ = sn1.posZ;
//        sn1.prevRotationYaw = sn1.rotationYaw;
//        sn1.prevRotationPitch = sn1.rotationPitch;
//        if(flag && sn1.addedToChunk)
//        {
//            if(sn1.ridingEntity != null)
//            {
//                sn1.updateRidden();
//            } else
//            {
//                sn1.onUpdate();
//            }
//        }
//        if(Double.isNaN(sn1.posX) || Double.isInfinite(sn1.posX))
//        {
//            sn1.posX = sn1.lastTickPosX;
//        }
//        if(Double.isNaN(sn1.posY) || Double.isInfinite(sn1.posY))
//        {
//            sn1.posY = sn1.lastTickPosY;
//        }
//        if(Double.isNaN(sn1.posZ) || Double.isInfinite(sn1.posZ))
//        {
//            sn1.posZ = sn1.lastTickPosZ;
//        }
//        if(Double.isNaN(sn1.rotationPitch) || Double.isInfinite(sn1.rotationPitch))
//        {
//            sn1.rotationPitch = sn1.prevRotationPitch;
//        }
//        if(Double.isNaN(sn1.rotationYaw) || Double.isInfinite(sn1.rotationYaw))
//        {
//            sn1.rotationYaw = sn1.prevRotationYaw;
//        }
//        int k1 = MathHelper.floor_double(sn1.posX / 16D);
//        int l1 = MathHelper.floor_double(sn1.posY / 16D);
//        int i2 = MathHelper.floor_double(sn1.posZ / 16D);
//        if(!sn1.addedToChunk || sn1.chunkCoordX != k1 || sn1.chunkCoordY != l1 || sn1.chunkCoordZ != i2)
//        {
//            if(sn1.addedToChunk && chunkExists(sn1.chunkCoordX, sn1.chunkCoordZ))
//            {
//                getChunkFromChunkCoords(sn1.chunkCoordX, sn1.chunkCoordZ).removeEntityAtIndex(sn1, sn1.chunkCoordY);
//            }
//            if(chunkExists(k1, i2))
//            {
//                sn1.addedToChunk = true;
//                getChunkFromChunkCoords(k1, i2).addEntity(sn1);
//            } else
//            {
//                sn1.addedToChunk = false;
//            }
//        }
//        if(flag && sn1.addedToChunk && sn1.riddenByEntity != null)
//        {
//            if(sn1.riddenByEntity.isDead || sn1.riddenByEntity.ridingEntity != sn1)
//            {
//                sn1.riddenByEntity.ridingEntity = null;
//                sn1.riddenByEntity = null;
//            } else
//            {
//                updateEntity(sn1.riddenByEntity);
//            }
//        }
//    }
//
//    public boolean checkIfAABBIsClear(AxisAlignedBB eq1)
//    {
//        List list = getEntitiesWithinAABBExcludingEntity(null, eq1);
//        for(int i1 = 0; i1 < list.size(); i1++)
//        {
//            Entity sn1 = (Entity)list.get(i1);
//            if(!sn1.isDead && sn1.preventEntitySpawning)
//            {
//                return false;
//            }
//        }
//
//        return true;
//    }
//
//    public boolean getIsAnyLiquid(AxisAlignedBB eq1)
//    {
//        int i1 = MathHelper.floor_double(eq1.minX);
//        int j1 = MathHelper.floor_double(eq1.maxX + 1.0D);
//        int k1 = MathHelper.floor_double(eq1.minY);
//        int l1 = MathHelper.floor_double(eq1.maxY + 1.0D);
//        int i2 = MathHelper.floor_double(eq1.minZ);
//        int j2 = MathHelper.floor_double(eq1.maxZ + 1.0D);
//        if(eq1.minX < 0.0D)
//        {
//            i1--;
//        }
//        if(eq1.minY < 0.0D)
//        {
//            k1--;
//        }
//        if(eq1.minZ < 0.0D)
//        {
//            i2--;
//        }
//        for(int k2 = i1; k2 < j1; k2++)
//        {
//            for(int l2 = k1; l2 < l1; l2++)
//            {
//                for(int i3 = i2; i3 < j2; i3++)
//                {
//                    Block uu1 = Block.blocksList[getBlockId(k2, l2, i3)];
//                    if(uu1 != null && uu1.blockMaterial.getIsLiquid())
//                    {
//                        return true;
//                    }
//                }
//
//            }
//
//        }
//
//        return false;
//    }
//
//    public boolean isBoundingBoxBurning(AxisAlignedBB eq1)
//    {
//        int i1 = MathHelper.floor_double(eq1.minX);
//        int j1 = MathHelper.floor_double(eq1.maxX + 1.0D);
//        int k1 = MathHelper.floor_double(eq1.minY);
//        int l1 = MathHelper.floor_double(eq1.maxY + 1.0D);
//        int i2 = MathHelper.floor_double(eq1.minZ);
//        int j2 = MathHelper.floor_double(eq1.maxZ + 1.0D);
//        if(checkChunksExist(i1, k1, i2, j1, l1, j2))
//        {
//            for(int k2 = i1; k2 < j1; k2++)
//            {
//                for(int l2 = k1; l2 < l1; l2++)
//                {
//                    for(int i3 = i2; i3 < j2; i3++)
//                    {
//                        int j3 = getBlockId(k2, l2, i3);
//                        if(j3 == Block.fire.blockID || j3 == Block.lavaMoving.blockID || j3 == Block.lavaStill.blockID)
//                        {
//                            return true;
//                        }
//                    }
//
//                }
//
//            }
//
//        }
//        return false;
//    }
//
//    public boolean handleMaterialAcceleration(AxisAlignedBB eq1, Material ln1, Entity sn1)
//    {
//        int i1 = MathHelper.floor_double(eq1.minX);
//        int j1 = MathHelper.floor_double(eq1.maxX + 1.0D);
//        int k1 = MathHelper.floor_double(eq1.minY);
//        int l1 = MathHelper.floor_double(eq1.maxY + 1.0D);
//        int i2 = MathHelper.floor_double(eq1.minZ);
//        int j2 = MathHelper.floor_double(eq1.maxZ + 1.0D);
//        if(!checkChunksExist(i1, k1, i2, j1, l1, j2))
//        {
//            return false;
//        }
//        boolean flag = false;
//        Vec3D bt1 = Vec3D.createVector(0.0D, 0.0D, 0.0D);
//        for(int k2 = i1; k2 < j1; k2++)
//        {
//            for(int l2 = k1; l2 < l1; l2++)
//            {
//                for(int i3 = i2; i3 < j2; i3++)
//                {
//                    Block uu1 = Block.blocksList[getBlockId(k2, l2, i3)];
//                    if(uu1 != null && uu1.blockMaterial == ln1)
//                    {
//                        double d2 = (float)(l2 + 1) - BlockFluid.getPercentAir(getBlockMetadata(k2, l2, i3));
//                        if((double)l1 >= d2)
//                        {
//                            flag = true;
//                            uu1.velocityToAddToEntity(this, k2, l2, i3, sn1, bt1);
//                        }
//                    }
//                }
//
//            }
//
//        }
//
//        if(bt1.lengthVector() > 0.0D)
//        {
//            bt1 = bt1.normalize();
//            double d1 = 0.014D;
//            sn1.motionX += bt1.xCoord * d1;
//            sn1.motionY += bt1.yCoord * d1;
//            sn1.motionZ += bt1.zCoord * d1;
//        }
//        return flag;
//    }
//
//    public boolean isMaterialInBB(AxisAlignedBB eq1, Material ln1)
//    {
//        int i1 = MathHelper.floor_double(eq1.minX);
//        int j1 = MathHelper.floor_double(eq1.maxX + 1.0D);
//        int k1 = MathHelper.floor_double(eq1.minY);
//        int l1 = MathHelper.floor_double(eq1.maxY + 1.0D);
//        int i2 = MathHelper.floor_double(eq1.minZ);
//        int j2 = MathHelper.floor_double(eq1.maxZ + 1.0D);
//        for(int k2 = i1; k2 < j1; k2++)
//        {
//            for(int l2 = k1; l2 < l1; l2++)
//            {
//                for(int i3 = i2; i3 < j2; i3++)
//                {
//                    Block uu1 = Block.blocksList[getBlockId(k2, l2, i3)];
//                    if(uu1 != null && uu1.blockMaterial == ln1)
//                    {
//                        return true;
//                    }
//                }
//
//            }
//
//        }
//
//        return false;
//    }
//
//    public boolean isAABBInMaterial(AxisAlignedBB eq1, Material ln1)
//    {
//        int i1 = MathHelper.floor_double(eq1.minX);
//        int j1 = MathHelper.floor_double(eq1.maxX + 1.0D);
//        int k1 = MathHelper.floor_double(eq1.minY);
//        int l1 = MathHelper.floor_double(eq1.maxY + 1.0D);
//        int i2 = MathHelper.floor_double(eq1.minZ);
//        int j2 = MathHelper.floor_double(eq1.maxZ + 1.0D);
//        for(int k2 = i1; k2 < j1; k2++)
//        {
//            for(int l2 = k1; l2 < l1; l2++)
//            {
//                for(int i3 = i2; i3 < j2; i3++)
//                {
//                    Block uu1 = Block.blocksList[getBlockId(k2, l2, i3)];
//                    if(uu1 != null && uu1.blockMaterial == ln1)
//                    {
//                        int j3 = getBlockMetadata(k2, l2, i3);
//                        double d1 = l2 + 1;
//                        if(j3 < 8)
//                        {
//                            d1 = (double)(l2 + 1) - (double)j3 / 8D;
//                        }
//                        if(d1 >= eq1.minY)
//                        {
//                            return true;
//                        }
//                    }
//                }
//
//            }
//
//        }
//
//        return false;
//    }
//
//    public Explosion createExplosion(Entity sn1, double d1, double d2, double d3,
//            float f1)
//    {
//        return newExplosion(sn1, d1, d2, d3, f1, false);
//    }
//
//    public Explosion newExplosion(Entity sn1, double d1, double d2, double d3,
//            float f1, boolean flag)
//    {
//        Explosion qx1 = new Explosion(this, sn1, d1, d2, d3, f1);
//        qx1.isFlaming = flag;
//        qx1.doExplosionA();
//        qx1.doExplosionB(true);
//        return qx1;
//    }
//
//    public float getBlockDensity(Vec3D bt1, AxisAlignedBB eq1)
//    {
//        double d1 = 1.0D / ((eq1.maxX - eq1.minX) * 2D + 1.0D);
//        double d2 = 1.0D / ((eq1.maxY - eq1.minY) * 2D + 1.0D);
//        double d3 = 1.0D / ((eq1.maxZ - eq1.minZ) * 2D + 1.0D);
//        int i1 = 0;
//        int j1 = 0;
//        for(float f1 = 0.0F; f1 <= 1.0F; f1 = (float)((double)f1 + d1))
//        {
//            for(float f2 = 0.0F; f2 <= 1.0F; f2 = (float)((double)f2 + d2))
//            {
//                for(float f3 = 0.0F; f3 <= 1.0F; f3 = (float)((double)f3 + d3))
//                {
//                    double d4 = eq1.minX + (eq1.maxX - eq1.minX) * (double)f1;
//                    double d5 = eq1.minY + (eq1.maxY - eq1.minY) * (double)f2;
//                    double d6 = eq1.minZ + (eq1.maxZ - eq1.minZ) * (double)f3;
//                    if(rayTraceBlocks(Vec3D.createVector(d4, d5, d6), bt1) == null)
//                    {
//                        i1++;
//                    }
//                    j1++;
//                }
//
//            }
//
//        }
//
//        return (float)i1 / (float)j1;
//    }
//
//    public void onBlockHit(EntityPlayer gs1, int i1, int j1, int k1, int l1)
//    {
//        if(l1 == 0)
//        {
//            j1--;
//        }
//        if(l1 == 1)
//        {
//            j1++;
//        }
//        if(l1 == 2)
//        {
//            k1--;
//        }
//        if(l1 == 3)
//        {
//            k1++;
//        }
//        if(l1 == 4)
//        {
//            i1--;
//        }
//        if(l1 == 5)
//        {
//            i1++;
//        }
//        if(getBlockId(i1, j1, k1) == Block.fire.blockID)
//        {
//            playAuxSFXAtEntity(gs1, 1004, i1, j1, k1, 0);
//            setBlockWithNotify(i1, j1, k1, 0);
//        }
//    }
//
//    public Entity func_4085_a(Class class1)
//    {
//        return null;
//    }
//
//    public String func_687_d()
//    {
//        return (new StringBuilder()).append("All: ").append(loadedEntityList.size()).toString();
//    }
//
//    public String func_21119_g()
//    {
//        return chunkProvider.makeString();
//    }
//
//    public TileEntity getBlockTileEntity(int i1, int j1, int k1)
//    {
//        Chunk lm1 = getChunkFromChunkCoords(i1 >> 4, k1 >> 4);
//        if(lm1 != null)
//        {
//            return lm1.getChunkBlockTileEntity(i1 & 0xf, j1, k1 & 0xf);
//        } else
//        {
//            return null;
//        }
//    }
//
//    public void setBlockTileEntity(int i1, int j1, int k1, TileEntity ow1)
//    {
//        if(!ow1.isInvalid())
//        {
//            if(field_31055_L)
//            {
//                ow1.xCoord = i1;
//                ow1.yCoord = j1;
//                ow1.zCoord = k1;
//                field_30900_E.add(ow1);
//            } else
//            {
//                loadedTileEntityList.add(ow1);
//                Chunk lm1 = getChunkFromChunkCoords(i1 >> 4, k1 >> 4);
//                if(lm1 != null)
//                {
//                    lm1.setChunkBlockTileEntity(i1 & 0xf, j1, k1 & 0xf, ow1);
//                }
//            }
//        }
//    }
//
//    public void removeBlockTileEntity(int i1, int j1, int k1)
//    {
//        TileEntity ow1 = getBlockTileEntity(i1, j1, k1);
//        if(ow1 != null && field_31055_L)
//        {
//            ow1.invalidate();
//        } else
//        {
//            if(ow1 != null)
//            {
//                loadedTileEntityList.remove(ow1);
//            }
//            Chunk lm1 = getChunkFromChunkCoords(i1 >> 4, k1 >> 4);
//            if(lm1 != null)
//            {
//                lm1.removeChunkBlockTileEntity(i1 & 0xf, j1, k1 & 0xf);
//            }
//        }
//    }
//
//    public boolean isBlockOpaqueCube(int i1, int j1, int k1)
//    {
//        Block uu1 = Block.blocksList[getBlockId(i1, j1, k1)];
//        if(uu1 == null)
//        {
//            return false;
//        } else
//        {
//            return uu1.isOpaqueCube();
//        }
//    }
//
//    public boolean isBlockNormalCube(int i1, int j1, int k1)
//    {
//        Block uu1 = Block.blocksList[getBlockId(i1, j1, k1)];
//        if(uu1 == null)
//        {
//            return false;
//        }
//        return uu1.blockMaterial.getIsTranslucent() && uu1.renderAsNormalBlock();
//    }
//
//    public void saveWorldIndirectly(IProgressUpdate yb1)
//    {
//        saveWorld(true, yb1);
//    }
//
//    public boolean updatingLighting()
//    {
//        if(lightingUpdatesCounter >= 50)
//        {
//            return false;
//        }
//        lightingUpdatesCounter++;
//        try
//        {
//            int i1 = 500;
//            for(; lightingToUpdate.size() > 0; ((MetadataChunkBlock)lightingToUpdate.remove(lightingToUpdate.size() - 1)).func_4127_a(this))
//            {
//                if(--i1 <= 0)
//                {
//                    boolean flag = true;
//                    boolean flag2 = flag;
//                    return flag2;
//                }
//            }
//
//            boolean flag1 = false;
//            boolean flag3 = flag1;
//            return flag3;
//        }
//        finally
//        {
//            lightingUpdatesCounter--;
//        }
//    }
//
//    public void scheduleLightingUpdate(EnumSkyBlock eb1, int i1, int j1, int k1, int l1, int i2, int j2)
//    {
//        scheduleLightingUpdate_do(eb1, i1, j1, k1, l1, i2, j2, true);
//    }
//
//    public void scheduleLightingUpdate_do(EnumSkyBlock eb1, int i1, int j1, int k1, int l1, int i2, int j2,
//            boolean flag)
//    {
//        if(worldProvider.hasNoSky && eb1 == EnumSkyBlock.Sky)
//        {
//            return;
//        }
//        lightingUpdatesScheduled++;
//        try
//        {
//            if(lightingUpdatesScheduled == 50)
//            {
//                return;
//            }
//            int k2 = (l1 + i1) / 2;
//            int l2 = (j2 + k1) / 2;
//            if(!blockExists(k2, 64, l2))
//            {
//                return;
//            }
//            if(getChunkFromBlockCoords(k2, l2).isEmpty())
//            {
//                return;
//            }
//            int i3 = lightingToUpdate.size();
//            if(flag)
//            {
//                int j3 = 5;
//                if(j3 > i3)
//                {
//                    j3 = i3;
//                }
//                for(int l3 = 0; l3 < j3; l3++)
//                {
//                    MetadataChunkBlock st1 = (MetadataChunkBlock)lightingToUpdate.get(lightingToUpdate.size() - l3 - 1);
//                    if(st1.field_1299_a == eb1 && st1.func_866_a(i1, j1, k1, l1, i2, j2))
//                    {
//                        return;
//                    }
//                }
//
//            }
//            lightingToUpdate.add(new MetadataChunkBlock(eb1, i1, j1, k1, l1, i2, j2));
//            int k3 = 0xf4240;
//            if(lightingToUpdate.size() > 0xf4240)
//            {
//                System.out.println((new StringBuilder()).append("More than ").append(k3).append(" updates, aborting lighting updates").toString());
//                lightingToUpdate.clear();
//            }
//        }
//        finally
//        {
//            lightingUpdatesScheduled--;
//        }
//        lightingUpdatesScheduled--;
//    }
//
//    public void calculateInitialSkylight()
//    {
//        int i1 = calculateSkylightSubtracted(1.0F);
//        if(i1 != skylightSubtracted)
//        {
//            skylightSubtracted = i1;
//        }
//    }
//
//    public void setAllowedMobSpawns(boolean flag, boolean flag1)
//    {
//        spawnHostileMobs = flag;
//        spawnPeacefulMobs = flag1;
//    }
//
//    public void tick()
//    {
//        updateWeather();
//        if(isAllPlayersFullyAsleep())
//        {
//            boolean flag = false;
//            if(spawnHostileMobs && difficultySetting >= 1)
//            {
//                flag = SpawnerAnimals.performSleepSpawning(this, playerEntities);
//            }
//            if(!flag)
//            {
//                long l1 = worldInfo.getWorldTime() + 24000L;
//                worldInfo.setWorldTime(l1 - l1 % 24000L);
//                wakeUpAllPlayers();
//            }
//        }
//        SpawnerAnimals.performSpawning(this, spawnHostileMobs, spawnPeacefulMobs);
//        chunkProvider.unload100OldestChunks();
//        int i1 = calculateSkylightSubtracted(1.0F);
//        if(i1 != skylightSubtracted)
//        {
//            skylightSubtracted = i1;
//            for(int j1 = 0; j1 < worldAccesses.size(); j1++)
//            {
//                ((IWorldAccess)worldAccesses.get(j1)).updateAllRenderers();
//            }
//
//        }
//        long l2 = worldInfo.getWorldTime() + 1L;
//        if(l2 % (long)autosavePeriod == 0L)
//        {
//            saveWorld(false, null);
//        }
//        worldInfo.setWorldTime(l2);
//        TickUpdates(false);
//        updateBlocksAndPlayCaveSounds();
//    }
//
//    private void func_27163_E()
//    {
//        if(worldInfo.getRaining())
//        {
//            rainingStrength = 1.0F;
//            if(worldInfo.getThundering())
//            {
//                thunderingStrength = 1.0F;
//            }
//        }
//    }
//
//    protected void updateWeather()
//    {
//        if(worldProvider.hasNoSky)
//        {
//            return;
//        }
//        if(field_27168_F > 0)
//        {
//            field_27168_F--;
//        }
//        int i1 = worldInfo.getThunderTime();
//        if(i1 <= 0)
//        {
//            if(worldInfo.getThundering())
//            {
//                worldInfo.setThunderTime(rand.nextInt(12000) + 3600);
//            } else
//            {
//                worldInfo.setThunderTime(rand.nextInt(0x29040) + 12000);
//            }
//        } else
//        {
//            i1--;
//            worldInfo.setThunderTime(i1);
//            if(i1 <= 0)
//            {
//                worldInfo.setThundering(!worldInfo.getThundering());
//            }
//        }
//        int j1 = worldInfo.getRainTime();
//        if(j1 <= 0)
//        {
//            if(worldInfo.getRaining())
//            {
//                worldInfo.setRainTime(rand.nextInt(12000) + 12000);
//            } else
//            {
//                worldInfo.setRainTime(rand.nextInt(0x29040) + 12000);
//            }
//        } else
//        {
//            j1--;
//            worldInfo.setRainTime(j1);
//            if(j1 <= 0)
//            {
//                worldInfo.setRaining(!worldInfo.getRaining());
//            }
//        }
//        prevRainingStrength = rainingStrength;
//        if(worldInfo.getRaining())
//        {
//            rainingStrength += 0.01D;
//        } else
//        {
//            rainingStrength -= 0.01D;
//        }
//        if(rainingStrength < 0.0F)
//        {
//            rainingStrength = 0.0F;
//        }
//        if(rainingStrength > 1.0F)
//        {
//            rainingStrength = 1.0F;
//        }
//        prevThunderingStrength = thunderingStrength;
//        if(worldInfo.getThundering())
//        {
//            thunderingStrength += 0.01D;
//        } else
//        {
//            thunderingStrength -= 0.01D;
//        }
//        if(thunderingStrength < 0.0F)
//        {
//            thunderingStrength = 0.0F;
//        }
//        if(thunderingStrength > 1.0F)
//        {
//            thunderingStrength = 1.0F;
//        }
//    }
//
//    private void stopPrecipitation()
//    {
//        worldInfo.setRainTime(0);
//        worldInfo.setRaining(false);
//        worldInfo.setThunderTime(0);
//        worldInfo.setThundering(false);
//    }
//
//    protected void updateBlocksAndPlayCaveSounds()
//    {
//        positionsToUpdate.clear();
//        for(int i1 = 0; i1 < playerEntities.size(); i1++)
//        {
//            EntityPlayer gs1 = (EntityPlayer)playerEntities.get(i1);
//            int j1 = MathHelper.floor_double(gs1.posX / 16D);
//            int l1 = MathHelper.floor_double(gs1.posZ / 16D);
//            byte byte0 = 9;
//            for(int j2 = -byte0; j2 <= byte0; j2++)
//            {
//                for(int k3 = -byte0; k3 <= byte0; k3++)
//                {
//                    positionsToUpdate.add(new ChunkCoordIntPair(j2 + j1, k3 + l1));
//                }
//
//            }
//
//        }
//
//        if(soundCounter > 0)
//        {
//            soundCounter--;
//        }
//        for(Iterator iterator = positionsToUpdate.iterator(); iterator.hasNext();)
//        {
//            ChunkCoordIntPair yy1 = (ChunkCoordIntPair)iterator.next();
//            int k1 = yy1.chunkXPos * 16;
//            int i2 = yy1.chunkZPos * 16;
//            Chunk lm1 = getChunkFromChunkCoords(yy1.chunkXPos, yy1.chunkZPos);
//            if(soundCounter == 0)
//            {
//                field_9437_g = field_9437_g * 3 + 0x3c6ef35f;
//                int k2 = field_9437_g >> 2;
//                int l3 = k2 & 0xf;
//                int l4 = k2 >> 8 & 0xf;
//                int l5 = k2 >> 16 & 0x7f;
//                int l6 = lm1.getBlockID(l3, l5, l4);
//                l3 += k1;
//                l4 += i2;
//                if(l6 == 0 && getFullBlockLightValue(l3, l5, l4) <= rand.nextInt(8) && getSavedLightValue(EnumSkyBlock.Sky, l3, l5, l4) <= 0)
//                {
//                    EntityPlayer gs2 = getClosestPlayer((double)l3 + 0.5D, (double)l5 + 0.5D, (double)l4 + 0.5D, 8D);
//                    if(gs2 != null && gs2.getDistanceSq((double)l3 + 0.5D, (double)l5 + 0.5D, (double)l4 + 0.5D) > 4D)
//                    {
//                        playSoundEffect((double)l3 + 0.5D, (double)l5 + 0.5D, (double)l4 + 0.5D, "ambient.cave.cave", 0.7F, 0.8F + rand.nextFloat() * 0.2F);
//                        soundCounter = rand.nextInt(12000) + 6000;
//                    }
//                }
//            }
//            if(rand.nextInt(0x186a0) == 0 && isRaining() && getIsThundering())
//            {
//                field_9437_g = field_9437_g * 3 + 0x3c6ef35f;
//                int l2 = field_9437_g >> 2;
//                int i4 = k1 + (l2 & 0xf);
//                int i5 = i2 + (l2 >> 8 & 0xf);
//                int i6 = findTopSolidBlock(i4, i5);
//                if(canBlockBeRainedOn(i4, i6, i5))
//                {
//                    addWeatherEffect(new EntityLightningBolt(this, i4, i6, i5));
//                    field_27168_F = 2;
//                }
//            }
//            if(rand.nextInt(16) == 0)
//            {
//                field_9437_g = field_9437_g * 3 + 0x3c6ef35f;
//                int i3 = field_9437_g >> 2;
//                int j4 = i3 & 0xf;
//                int j5 = i3 >> 8 & 0xf;
//                int j6 = findTopSolidBlock(j4 + k1, j5 + i2);
//                if(getWorldChunkManager().getBiomeGenAt(j4 + k1, j5 + i2).getEnableSnow() && j6 >= 0 && j6 < 128 && lm1.getSavedLightValue(EnumSkyBlock.Block, j4, j6, j5) < 10)
//                {
//                    int i7 = lm1.getBlockID(j4, j6 - 1, j5);
//                    int k7 = lm1.getBlockID(j4, j6, j5);
//                    if(isRaining() && k7 == 0 && Block.snow.canPlaceBlockAt(this, j4 + k1, j6, j5 + i2) && i7 != 0 && i7 != Block.ice.blockID && Block.blocksList[i7].blockMaterial.getIsSolid())
//                    {
//                        setBlockWithNotify(j4 + k1, j6, j5 + i2, Block.snow.blockID);
//                    }
//                    if(i7 == Block.waterStill.blockID && lm1.getBlockMetadata(j4, j6 - 1, j5) == 0)
//                    {
//                        setBlockWithNotify(j4 + k1, j6 - 1, j5 + i2, Block.ice.blockID);
//                    }
//                }
//            }
//            for(int j3 = 0; j3 < 80; j3++)
//            {
//                field_9437_g = field_9437_g * 3 + 0x3c6ef35f;
//                int k4 = field_9437_g >> 2;
//                int k5 = k4 & 0xf;
//                int k6 = k4 >> 8 & 0xf;
//                int j7 = k4 >> 16 & 0x7f;
//                int l7 = lm1.blocks[k5 << 11 | k6 << 7 | j7] & 0xff;
//                if(Block.tickOnLoad[l7])
//                {
//                    Block.blocksList[l7].updateTick(this, k5 + k1, j7, k6 + i2, rand);
//                }
//            }
//
//        }
//
//    }
//
//    public boolean TickUpdates(boolean flag)
//    {
//        int i1 = scheduledTickTreeSet.size();
//        if(i1 != scheduledTickSet.size())
//        {
//            throw new IllegalStateException("TickNextTick list out of synch");
//        }
//        if(i1 > 1000)
//        {
//            i1 = 1000;
//        }
//        for(int j1 = 0; j1 < i1; j1++)
//        {
//            NextTickListEntry qy1 = (NextTickListEntry)scheduledTickTreeSet.first();
//            if(!flag && qy1.scheduledTime > worldInfo.getWorldTime())
//            {
//                break;
//            }
//            scheduledTickTreeSet.remove(qy1);
//            scheduledTickSet.remove(qy1);
//            byte byte0 = 8;
//            if(checkChunksExist(qy1.xCoord - byte0, qy1.yCoord - byte0, qy1.zCoord - byte0, qy1.xCoord + byte0, qy1.yCoord + byte0, qy1.zCoord + byte0))
//            {
//                int k1 = getBlockId(qy1.xCoord, qy1.yCoord, qy1.zCoord);
//                if(k1 == qy1.blockID && k1 > 0)
//                {
//                    Block.blocksList[k1].updateTick(this, qy1.xCoord, qy1.yCoord, qy1.zCoord, rand);
//                }
//            }
//        }
//
//        return scheduledTickTreeSet.size() != 0;
//    }
//
//    public void randomDisplayUpdates(int i1, int j1, int k1)
//    {
//        byte byte0 = 16;
//        Random random = new Random();
//        for(int l1 = 0; l1 < 1000; l1++)
//        {
//            int i2 = (i1 + rand.nextInt(byte0)) - rand.nextInt(byte0);
//            int j2 = (j1 + rand.nextInt(byte0)) - rand.nextInt(byte0);
//            int k2 = (k1 + rand.nextInt(byte0)) - rand.nextInt(byte0);
//            int l2 = getBlockId(i2, j2, k2);
//            if(l2 > 0)
//            {
//                Block.blocksList[l2].randomDisplayTick(this, i2, j2, k2, random);
//            }
//        }
//
//    }
//
//    public List getEntitiesWithinAABBExcludingEntity(Entity sn1, AxisAlignedBB eq1)
//    {
//        field_1012_M.clear();
//        int i1 = MathHelper.floor_double((eq1.minX - 2D) / 16D);
//        int j1 = MathHelper.floor_double((eq1.maxX + 2D) / 16D);
//        int k1 = MathHelper.floor_double((eq1.minZ - 2D) / 16D);
//        int l1 = MathHelper.floor_double((eq1.maxZ + 2D) / 16D);
//        for(int i2 = i1; i2 <= j1; i2++)
//        {
//            for(int j2 = k1; j2 <= l1; j2++)
//            {
//                if(chunkExists(i2, j2))
//                {
//                    getChunkFromChunkCoords(i2, j2).getEntitiesWithinAABBForEntity(sn1, eq1, field_1012_M);
//                }
//            }
//
//        }
//
//        return field_1012_M;
//    }
//
//    public List getEntitiesWithinAABB(Class class1, AxisAlignedBB eq1)
//    {
//        int i1 = MathHelper.floor_double((eq1.minX - 2D) / 16D);
//        int j1 = MathHelper.floor_double((eq1.maxX + 2D) / 16D);
//        int k1 = MathHelper.floor_double((eq1.minZ - 2D) / 16D);
//        int l1 = MathHelper.floor_double((eq1.maxZ + 2D) / 16D);
//        ArrayList arraylist = new ArrayList();
//        for(int i2 = i1; i2 <= j1; i2++)
//        {
//            for(int j2 = k1; j2 <= l1; j2++)
//            {
//                if(chunkExists(i2, j2))
//                {
//                    getChunkFromChunkCoords(i2, j2).getEntitiesOfTypeWithinAAAB(class1, eq1, arraylist);
//                }
//            }
//
//        }
//
//        return arraylist;
//    }
//
//    public List getLoadedEntityList()
//    {
//        return loadedEntityList;
//    }
//
//    public void func_698_b(int i1, int j1, int k1, TileEntity ow1)
//    {
//        if(blockExists(i1, j1, k1))
//        {
//            getChunkFromBlockCoords(i1, k1).setChunkModified();
//        }
//        for(int l1 = 0; l1 < worldAccesses.size(); l1++)
//        {
//            ((IWorldAccess)worldAccesses.get(l1)).doNothingWithTileEntity(i1, j1, k1, ow1);
//        }
//
//    }
//
//    public int countEntities(Class class1)
//    {
//        int i1 = 0;
//        for(int j1 = 0; j1 < loadedEntityList.size(); j1++)
//        {
//            Entity sn1 = (Entity)loadedEntityList.get(j1);
//            if(class1.isAssignableFrom(sn1.getClass()))
//            {
//                i1++;
//            }
//        }
//
//        return i1;
//    }
//
//    public void addLoadedEntities(List list)
//    {
//        loadedEntityList.addAll(list);
//        for(int i1 = 0; i1 < list.size(); i1++)
//        {
//            obtainEntitySkin((Entity)list.get(i1));
//        }
//
//    }
//
//    public void unloadEntities(List list)
//    {
//        unloadedEntityList.addAll(list);
//    }
//
//    public void func_656_j()
//    {
//        while(chunkProvider.unload100OldestChunks()) ;
//    }
//
//    public boolean canBlockBePlacedAt(int i1, int j1, int k1, int l1, boolean flag, int i2)
//    {
//        int j2 = getBlockId(j1, k1, l1);
//        Block uu1 = Block.blocksList[j2];
//        Block uu2 = Block.blocksList[i1];
//        AxisAlignedBB eq1 = uu2.getCollisionBoundingBoxFromPool(this, j1, k1, l1);
//        if(flag)
//        {
//            eq1 = null;
//        }
//        if(eq1 != null && !checkIfAABBIsClear(eq1))
//        {
//            return false;
//        }
//        if(uu1 == Block.waterMoving || uu1 == Block.waterStill || uu1 == Block.lavaMoving || uu1 == Block.lavaStill || uu1 == Block.fire || uu1 == Block.snow)
//        {
//            uu1 = null;
//        }
//        return i1 > 0 && uu1 == null && uu2.canPlaceBlockOnSide(this, j1, k1, l1, i2);
//    }
//
//    public PathEntity getPathToEntity(Entity sn1, Entity sn2, float f1)
//    {
//        int i1 = MathHelper.floor_double(sn1.posX);
//        int j1 = MathHelper.floor_double(sn1.posY);
//        int k1 = MathHelper.floor_double(sn1.posZ);
//        int l1 = (int)(f1 + 16F);
//        int i2 = i1 - l1;
//        int j2 = j1 - l1;
//        int k2 = k1 - l1;
//        int l2 = i1 + l1;
//        int i3 = j1 + l1;
//        int j3 = k1 + l1;
//        ChunkCache ew1 = new ChunkCache(this, i2, j2, k2, l2, i3, j3);
//        return (new Pathfinder(ew1)).createEntityPathTo(sn1, sn2, f1);
//    }
//
//    public PathEntity getEntityPathToXYZ(Entity sn1, int i1, int j1, int k1, float f1)
//    {
//        int l1 = MathHelper.floor_double(sn1.posX);
//        int i2 = MathHelper.floor_double(sn1.posY);
//        int j2 = MathHelper.floor_double(sn1.posZ);
//        int k2 = (int)(f1 + 8F);
//        int l2 = l1 - k2;
//        int i3 = i2 - k2;
//        int j3 = j2 - k2;
//        int k3 = l1 + k2;
//        int l3 = i2 + k2;
//        int i4 = j2 + k2;
//        ChunkCache ew1 = new ChunkCache(this, l2, i3, j3, k3, l3, i4);
//        return (new Pathfinder(ew1)).createEntityPathTo(sn1, i1, j1, k1, f1);
//    }
//
//    public boolean isBlockProvidingPowerTo(int i1, int j1, int k1, int l1)
//    {
//        int i2 = getBlockId(i1, j1, k1);
//        if(i2 == 0)
//        {
//            return false;
//        } else
//        {
//            return Block.blocksList[i2].isIndirectlyPoweringTo(this, i1, j1, k1, l1);
//        }
//    }
//
//    public boolean isBlockGettingPowered(int i1, int j1, int k1)
//    {
//        if(isBlockProvidingPowerTo(i1, j1 - 1, k1, 0))
//        {
//            return true;
//        }
//        if(isBlockProvidingPowerTo(i1, j1 + 1, k1, 1))
//        {
//            return true;
//        }
//        if(isBlockProvidingPowerTo(i1, j1, k1 - 1, 2))
//        {
//            return true;
//        }
//        if(isBlockProvidingPowerTo(i1, j1, k1 + 1, 3))
//        {
//            return true;
//        }
//        if(isBlockProvidingPowerTo(i1 - 1, j1, k1, 4))
//        {
//            return true;
//        } else
//        {
//            return isBlockProvidingPowerTo(i1 + 1, j1, k1, 5);
//        }
//    }
//
//    public boolean isBlockIndirectlyProvidingPowerTo(int i1, int j1, int k1, int l1)
//    {
//        if(isBlockNormalCube(i1, j1, k1))
//        {
//            return isBlockGettingPowered(i1, j1, k1);
//        }
//        int i2 = getBlockId(i1, j1, k1);
//        if(i2 == 0)
//        {
//            return false;
//        } else
//        {
//            return Block.blocksList[i2].isPoweringTo(this, i1, j1, k1, l1);
//        }
//    }
//
//    public boolean isBlockIndirectlyGettingPowered(int i1, int j1, int k1)
//    {
//        if(isBlockIndirectlyProvidingPowerTo(i1, j1 - 1, k1, 0))
//        {
//            return true;
//        }
//        if(isBlockIndirectlyProvidingPowerTo(i1, j1 + 1, k1, 1))
//        {
//            return true;
//        }
//        if(isBlockIndirectlyProvidingPowerTo(i1, j1, k1 - 1, 2))
//        {
//            return true;
//        }
//        if(isBlockIndirectlyProvidingPowerTo(i1, j1, k1 + 1, 3))
//        {
//            return true;
//        }
//        if(isBlockIndirectlyProvidingPowerTo(i1 - 1, j1, k1, 4))
//        {
//            return true;
//        } else
//        {
//            return isBlockIndirectlyProvidingPowerTo(i1 + 1, j1, k1, 5);
//        }
//    }
//
//    public EntityPlayer getClosestPlayerToEntity(Entity sn1, double d1)
//    {
//        return getClosestPlayer(sn1.posX, sn1.posY, sn1.posZ, d1);
//    }
//
//    public EntityPlayer getClosestPlayer(double d1, double d2, double d3, double d4)
//    {
//        double d5 = -1D;
//        EntityPlayer gs1 = null;
//        for(int i1 = 0; i1 < playerEntities.size(); i1++)
//        {
//            EntityPlayer gs2 = (EntityPlayer)playerEntities.get(i1);
//            double d6 = gs2.getDistanceSq(d1, d2, d3);
//            if((d4 < 0.0D || d6 < d4 * d4) && (d5 == -1D || d6 < d5))
//            {
//                d5 = d6;
//                gs1 = gs2;
//            }
//        }
//
//        return gs1;
//    }
//
//    public EntityPlayer getPlayerEntityByName(String s1)
//    {
//        for(int i1 = 0; i1 < playerEntities.size(); i1++)
//        {
//            if(s1.equals(((EntityPlayer)playerEntities.get(i1)).username))
//            {
//                return (EntityPlayer)playerEntities.get(i1);
//            }
//        }
//
//        return null;
//    }
//
//    public void setChunkData(int i1, int j1, int k1, int l1, int i2, int j2, byte abyte0[])
//    {
//        int k2 = i1 >> 4;
//        int l2 = k1 >> 4;
//        int i3 = (i1 + l1) - 1 >> 4;
//        int j3 = (k1 + j2) - 1 >> 4;
//        int k3 = 0;
//        int l3 = j1;
//        int i4 = j1 + i2;
//        if(l3 < 0)
//        {
//            l3 = 0;
//        }
//        if(i4 > 128)
//        {
//            i4 = 128;
//        }
//        for(int j4 = k2; j4 <= i3; j4++)
//        {
//            int k4 = i1 - j4 * 16;
//            int l4 = (i1 + l1) - j4 * 16;
//            if(k4 < 0)
//            {
//                k4 = 0;
//            }
//            if(l4 > 16)
//            {
//                l4 = 16;
//            }
//            for(int i5 = l2; i5 <= j3; i5++)
//            {
//                int j5 = k1 - i5 * 16;
//                int k5 = (k1 + j2) - i5 * 16;
//                if(j5 < 0)
//                {
//                    j5 = 0;
//                }
//                if(k5 > 16)
//                {
//                    k5 = 16;
//                }
//                k3 = getChunkFromChunkCoords(j4, i5).setChunkData(abyte0, k4, l3, j5, l4, i4, k5, k3);
//                markBlocksDirty(j4 * 16 + k4, l3, i5 * 16 + j5, j4 * 16 + l4, i4, i5 * 16 + k5);
//            }
//
//        }
//
//    }
//
//    public void sendQuittingDisconnectingPacket()
//    {
//    }
//
//    public void checkSessionLock()
//    {
//        saveHandler.checkSessionLock();
//    }
//
//    public void setWorldTime(long l1)
//    {
//        worldInfo.setWorldTime(l1);
//    }
//
//    public long getRandomSeed()
//    {
//        return worldInfo.getRandomSeed();
//    }
//
//    public long getWorldTime()
//    {
//        return worldInfo.getWorldTime();
//    }
//
//    public ChunkCoordinates getSpawnPoint()
//    {
//        return new ChunkCoordinates(worldInfo.getSpawnX(), worldInfo.getSpawnY(), worldInfo.getSpawnZ());
//    }
//
//    public void setSpawnPoint(ChunkCoordinates br1)
//    {
//        worldInfo.setSpawn(br1.x, br1.y, br1.z);
//    }
//
//    public void joinEntityInSurroundings(Entity sn1)
//    {
//        int i1 = MathHelper.floor_double(sn1.posX / 16D);
//        int j1 = MathHelper.floor_double(sn1.posZ / 16D);
//        byte byte0 = 2;
//        for(int k1 = i1 - byte0; k1 <= i1 + byte0; k1++)
//        {
//            for(int l1 = j1 - byte0; l1 <= j1 + byte0; l1++)
//            {
//                getChunkFromChunkCoords(k1, l1);
//            }
//
//        }
//
//        if(!loadedEntityList.contains(sn1))
//        {
//            loadedEntityList.add(sn1);
//        }
//    }
//
//    public boolean func_6466_a(EntityPlayer gs1, int i1, int j1, int i)
//    {
//        return true;
//    }
//
//    public void setEntityState(Entity entity, byte byte1)
//    {
//    }
//
//    public void updateEntityList()
//    {
//        loadedEntityList.removeAll(unloadedEntityList);
//        for(int i1 = 0; i1 < unloadedEntityList.size(); i1++)
//        {
//            Entity sn1 = (Entity)unloadedEntityList.get(i1);
//            int l1 = sn1.chunkCoordX;
//            int j2 = sn1.chunkCoordZ;
//            if(sn1.addedToChunk && chunkExists(l1, j2))
//            {
//                getChunkFromChunkCoords(l1, j2).removeEntity(sn1);
//            }
//        }
//
//        for(int j1 = 0; j1 < unloadedEntityList.size(); j1++)
//        {
//            releaseEntitySkin((Entity)unloadedEntityList.get(j1));
//        }
//
//        unloadedEntityList.clear();
//        for(int k1 = 0; k1 < loadedEntityList.size(); k1++)
//        {
//            Entity sn2 = (Entity)loadedEntityList.get(k1);
//            if(sn2.ridingEntity != null)
//            {
//                if(!sn2.ridingEntity.isDead && sn2.ridingEntity.riddenByEntity == sn2)
//                {
//                    continue;
//                }
//                sn2.ridingEntity.riddenByEntity = null;
//                sn2.ridingEntity = null;
//            }
//            if(sn2.isDead)
//            {
//                int i2 = sn2.chunkCoordX;
//                int k2 = sn2.chunkCoordZ;
//                if(sn2.addedToChunk && chunkExists(i2, k2))
//                {
//                    getChunkFromChunkCoords(i2, k2).removeEntity(sn2);
//                }
//                loadedEntityList.remove(k1--);
//                releaseEntitySkin(sn2);
//            }
//        }
//
//    }
//
//    public IChunkProvider getIChunkProvider()
//    {
//        return chunkProvider;
//    }
//
//    public void playNoteAt(int i1, int j1, int k1, int l1, int i2)
//    {
//        int j2 = getBlockId(i1, j1, k1);
//        if(j2 > 0)
//        {
//            Block.blocksList[j2].playBlock(this, i1, j1, k1, l1, i2);
//        }
//    }
//
//    public WorldInfo getWorldInfo()
//    {
//        return worldInfo;
//    }
//
//    public void updateAllPlayersSleepingFlag()
//    {
//        allPlayersSleeping = !playerEntities.isEmpty();
//        Iterator iterator = playerEntities.iterator();
//        do
//        {
//            if(!iterator.hasNext())
//            {
//                break;
//            }
//            EntityPlayer gs1 = (EntityPlayer)iterator.next();
//            if(gs1.isPlayerSleeping())
//            {
//                continue;
//            }
//            allPlayersSleeping = false;
//            break;
//        } while(true);
//    }
//
//    protected void wakeUpAllPlayers()
//    {
//        allPlayersSleeping = false;
//        Iterator iterator = playerEntities.iterator();
//        do
//        {
//            if(!iterator.hasNext())
//            {
//                break;
//            }
//            EntityPlayer gs1 = (EntityPlayer)iterator.next();
//            if(gs1.isPlayerSleeping())
//            {
//                gs1.wakeUpPlayer(false, false, true);
//            }
//        } while(true);
//        stopPrecipitation();
//    }
//
//    public boolean isAllPlayersFullyAsleep()
//    {
//        if(allPlayersSleeping && !multiplayerWorld)
//        {
//            for(Iterator iterator = playerEntities.iterator(); iterator.hasNext();)
//            {
//                EntityPlayer gs1 = (EntityPlayer)iterator.next();
//                if(!gs1.isPlayerFullyAsleep())
//                {
//                    return false;
//                }
//            }
//
//            return true;
//        } else
//        {
//            return false;
//        }
//    }
//
//    public float getWeightedThunderStrength(float f1)
//    {
//        return (prevThunderingStrength + (thunderingStrength - prevThunderingStrength) * f1) * getRainStrength(f1);
//    }
//
//    public float getRainStrength(float f1)
//    {
//        return prevRainingStrength + (rainingStrength - prevRainingStrength) * f1;
//    }
//
//    public void setRainStrength(float f1)
//    {
//        prevRainingStrength = f1;
//        rainingStrength = f1;
//    }
//
//    public boolean getIsThundering()
//    {
//        return (double)getWeightedThunderStrength(1.0F) > 0.90000000000000002D;
//    }
//
//    public boolean isRaining()
//    {
//        return (double)getRainStrength(1.0F) > 0.20000000000000001D;
//    }
//
//    public boolean canBlockBeRainedOn(int i1, int j1, int k1)
//    {
//        if(!isRaining())
//        {
//            return false;
//        }
//        if(!canBlockSeeTheSky(i1, j1, k1))
//        {
//            return false;
//        }
//        if(findTopSolidBlock(i1, k1) > j1)
//        {
//            return false;
//        }
//        BiomeGenBase kd1 = getWorldChunkManager().getBiomeGenAt(i1, k1);
//        if(kd1.getEnableSnow())
//        {
//            return false;
//        } else
//        {
//            return kd1.canSpawnLightningBolt();
//        }
//    }
//
//    public void setItemData(String s1, MapDataBase hm)
//    {
//        field_28108_z.setData(s1, hm);
//    }
//
//    public MapDataBase loadItemData(Class class1, String s1)
//    {
//        return field_28108_z.loadData(class1, s1);
//    }
//
//    public int getUniqueDataId(String s1)
//    {
//        return field_28108_z.getUniqueDataId(s1);
//    }
//
//    public void playAuxSFX(int i1, int j1, int k1, int l1, int i2)
//    {
//        playAuxSFXAtEntity(null, i1, j1, k1, l1, i2);
//    }
//
//    public void playAuxSFXAtEntity(EntityPlayer gs1, int i1, int j1, int k1, int l1, int i2)
//    {
//        for(int j2 = 0; j2 < worldAccesses.size(); j2++)
//        {
//            ((IWorldAccess)worldAccesses.get(j2)).playAuxSFX(gs1, i1, j1, k1, l1, i2);
//        }
//
//    }
//
//    public boolean scheduledUpdatesAreImmediate;
//    private List lightingToUpdate;
//    public List loadedEntityList;
//    private List unloadedEntityList;
//    private TreeSet scheduledTickTreeSet;
//    private Set scheduledTickSet;
//    public List loadedTileEntityList;
//    private List field_30900_E;
//    public List playerEntities;
//    public List weatherEffects;
//    private long field_1019_F;
//    public int skylightSubtracted;
//    protected int field_9437_g;
//    protected final int field_9436_h = 0x3c6ef35f;
//    protected float prevRainingStrength;
//    protected float rainingStrength;
//    protected float prevThunderingStrength;
//    protected float thunderingStrength;
//    protected int field_27168_F;
//    public int field_27172_i;
//    public boolean editingBlocks;
//    private long lockTimestamp;
//    protected int autosavePeriod;
//    public int difficultySetting;
//    public Random rand;
//    public boolean isNewWorld;
//    public final WorldProvider worldProvider;
//    protected List worldAccesses;
//    protected IChunkProvider chunkProvider;
//    protected final ISaveHandler saveHandler;
//    protected WorldInfo worldInfo;
//    public boolean findingSpawnPoint;
//    private boolean allPlayersSleeping;
//    public MapStorage field_28108_z;
//    private ArrayList collidingBoundingBoxes;
//    private boolean field_31055_L;
//    private int lightingUpdatesCounter;
//    private boolean spawnHostileMobs;
//    private boolean spawnPeacefulMobs;
//    static int lightingUpdatesScheduled = 0;
//    private Set positionsToUpdate;
//    private int soundCounter;
//    private List field_1012_M;
//    public boolean multiplayerWorld;
//
//}
