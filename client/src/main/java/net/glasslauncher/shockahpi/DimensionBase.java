//// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
//// Jad home page: http://www.kpdus.com/jad.html
//// Decompiler options: packimports(3) braces deadcode
//// Source File Name:   DimensionBase.java
//
//package net.glasslauncher.shockahpi;
//
//import java.lang.reflect.Method;
//import java.util.*;
//import net.minecraft.client.Minecraft;
//import net.minecraft.src.*;
//import net.minecraft.src.WorldProvider;
//
//// Referenced classes of package net.minecraft.src:
////            DimensionOverworld, DimensionNether, WorldProvider, Teleporter,
////            SAPI, World, EntityPlayerSP, EntityPlayer,
////            ChunkCoordinates, WorldGenDeadBush, ChunkProviderLoadOrGenerate, IChunkProvider,
////            PlayerController, MovementInputFromOptions, GuiGameOver, Loc
//
//public class DimensionBase
//{
//
//    public static DimensionBase getDimByNumber(int number)
//    {
//        for(int i = 0; i < list.size(); i++)
//        {
//            DimensionBase dim = (DimensionBase)list.get(i);
//            if(dim.number == number)
//            {
//                return dim;
//            }
//        }
//
//        return null;
//    }
//
//    public static DimensionBase getDimByProvider(Class worldProvider)
//    {
//        for(int i = 0; i < list.size(); i++)
//        {
//            DimensionBase dim = (DimensionBase)list.get(i);
//            if(dim.worldProvider.getName().equals(worldProvider.getName()))
//            {
//                return dim;
//            }
//        }
//
//        return null;
//    }
//
//    public net.minecraft.src.WorldProvider getWorldProvider()
//    {
//        try
//        {
//            return (WorldProvider)worldProvider.newInstance();
//        }
//        catch(InstantiationException instantiationexception) { }
//        catch(IllegalAccessException illegalaccessexception) { }
//        return null;
//    }
//
//    public Teleporter getTeleporter()
//    {
//        try
//        {
//            if(teleporter != null)
//            {
//                return (Teleporter)teleporter.newInstance();
//            }
//        }
//        catch(InstantiationException instantiationexception) { }
//        catch(IllegalAccessException illegalaccessexception) { }
//        return null;
//    }
//
//    public static void respawn(boolean paramBoolean, int paramInt)
//    {
//        Minecraft localMinecraft = SAPI.getMinecraftInstance();
//        if(!localMinecraft.theWorld.multiplayerWorld && !localMinecraft.theWorld.worldProvider.canRespawnHere())
//        {
//            usePortal(0, true);
//        }
//        ChunkCoordinates localbp1 = null;
//        ChunkCoordinates localbp2 = null;
//        int i = 1;
//        if(localMinecraft.thePlayer != null && !paramBoolean)
//        {
//            localbp1 = localMinecraft.thePlayer.getPlayerSpawnCoordinate();
//            if(localbp1 != null)
//            {
//                localbp2 = EntityPlayer.func_25060_a(localMinecraft.theWorld, localbp1);
//                if(localbp2 == null)
//                {
//                    localMinecraft.thePlayer.addChatMessage("tile.bed.notValid");
//                }
//            }
//        }
//        if(localbp2 == null)
//        {
//            localbp2 = localMinecraft.theWorld.getSpawnPoint();
//            i = 0;
//        }
//        IChunkProvider localcj = localMinecraft.theWorld.getIChunkProvider();
//        if(localcj instanceof WorldGenDeadBush)
//        {
//            ChunkProviderLoadOrGenerate localkt = (ChunkProviderLoadOrGenerate)localcj;
//            localkt.setCurrentChunkOver(localbp2.x >> 4, localbp2.z >> 4);
//        }
//        localMinecraft.theWorld.setSpawnLocation();
//        localMinecraft.theWorld.updateEntityList();
//        int j = 0;
//        if(localMinecraft.thePlayer != null)
//        {
//            j = localMinecraft.thePlayer.entityId;
//            localMinecraft.theWorld.setEntityDead(localMinecraft.thePlayer);
//        }
//        localMinecraft.renderViewEntity = null;
//        localMinecraft.thePlayer = (EntityPlayerSP)localMinecraft.playerController.createPlayer(localMinecraft.theWorld);
//        localMinecraft.thePlayer.dimension = paramInt;
//        localMinecraft.renderViewEntity = localMinecraft.thePlayer;
//        localMinecraft.thePlayer.preparePlayerToSpawn();
//        if(i != 0)
//        {
//            localMinecraft.thePlayer.setPlayerSpawnCoordinate(localbp1);
//            localMinecraft.thePlayer.setLocationAndAngles((float)localbp2.x + 0.5F, (float)localbp2.y + 0.1F, (float)localbp2.z + 0.5F, 0.0F, 0.0F);
//        }
//        localMinecraft.playerController.flipPlayer(localMinecraft.thePlayer);
//        localMinecraft.theWorld.spawnPlayerWithLoadedChunks(localMinecraft.thePlayer);
//        localMinecraft.thePlayer.movementInput = new MovementInputFromOptions(localMinecraft.gameSettings);
//        localMinecraft.thePlayer.entityId = j;
//        localMinecraft.thePlayer.func_6420_o();
//        localMinecraft.playerController.func_6473_b(localMinecraft.thePlayer);
//        try
//        {
//            Method localMethod = (Minecraft.class).getDeclaredMethod("d", new Class[] {
//                String.class
//            });
//            localMethod.setAccessible(true);
//            localMethod.invoke(localMinecraft, new Object[] {
//                "Respawning"
//            });
//        }
//        catch(Exception localException)
//        {
//            localException.printStackTrace();
//        }
//        if(localMinecraft.currentScreen instanceof GuiGameOver)
//        {
//            localMinecraft.displayGuiScreen(null);
//        }
//    }
//
//    public static void usePortal(int dimNumber)
//    {
//        usePortal(dimNumber, false);
//    }
//
//    private static void usePortal(int dimNumber, boolean resetOrder)
//    {
//        Minecraft game = SAPI.getMinecraftInstance();
//        int oldDimension = game.thePlayer.dimension;
//        int newDimension = dimNumber;
//        if(oldDimension == newDimension)
//        {
//            newDimension = 0;
//        }
//        game.theWorld.setEntityDead(game.thePlayer);
//        game.thePlayer.isDead = false;
//        Loc loc = new Loc(game.thePlayer.posX, game.thePlayer.posZ);
//        if(newDimension != 0)
//        {
//            order.push(Integer.valueOf(newDimension));
//        }
//        if(newDimension == 0 && !order.isEmpty())
//        {
//            newDimension = ((Integer)order.pop()).intValue();
//        }
//        if(oldDimension == newDimension)
//        {
//            newDimension = 0;
//        }
//        String str = "";
//        for(Iterator iterator = order.iterator(); iterator.hasNext();)
//        {
//            Integer dim = (Integer)iterator.next();
//            if(!str.isEmpty())
//            {
//                str = (new StringBuilder(String.valueOf(str))).append(",").toString();
//            }
//            str = (new StringBuilder(String.valueOf(str))).append(dim).toString();
//        }
//
//        World world = null;
//        DimensionBase dimOld = getDimByNumber(oldDimension);
//        DimensionBase dimNew = getDimByNumber(newDimension);
//        loc = dimOld.getDistanceScale(loc, true);
//        loc = dimNew.getDistanceScale(loc, false);
//        game.thePlayer.dimension = newDimension;
//        game.thePlayer.setLocationAndAngles(loc.x, game.thePlayer.posY, loc.z, game.thePlayer.rotationYaw, game.thePlayer.rotationPitch);
//        game.theWorld.updateEntityWithOptionalForce(game.thePlayer, false);
//        world = new World(game.theWorld, dimNew.getWorldProvider());
//        game.changeWorld(world, (new StringBuilder(String.valueOf(newDimension != 0 ? "Entering" : "Leaving"))).append(" the ").append(newDimension != 0 ? dimNew.name : dimOld.name).toString(), game.thePlayer);
//        game.thePlayer.worldObj = game.theWorld;
//        game.thePlayer.setLocationAndAngles(loc.x, game.thePlayer.posY, loc.z, game.thePlayer.rotationYaw, game.thePlayer.rotationPitch);
//        game.theWorld.updateEntityWithOptionalForce(game.thePlayer, false);
//        Teleporter teleporter = dimNew.getTeleporter();
//        if(teleporter == null)
//        {
//            teleporter = dimOld.getTeleporter();
//        }
//        teleporter.func_4107_a(game.theWorld, game.thePlayer);
//    }
//
//    public DimensionBase(int number, Class worldProvider, Class teleporter)
//    {
//        name = "Dimension";
//        soundTrigger = "portal.trigger";
//        soundTravel = "portal.travel";
//        this.number = number;
//        this.worldProvider = worldProvider;
//        this.teleporter = teleporter;
//        list.add(this);
//    }
//
//    public Loc getDistanceScale(Loc loc, boolean goingIn)
//    {
//        return loc;
//    }
//
//    public static ArrayList list = new ArrayList();
//    public static LinkedList order = new LinkedList();
//    public final int number;
//    public final Class worldProvider;
//    public final Class teleporter;
//    public String name;
//    public String soundTrigger;
//    public String soundTravel;
//
//    static
//    {
//        new DimensionOverworld();
//        new DimensionNether();
//    }
//}
