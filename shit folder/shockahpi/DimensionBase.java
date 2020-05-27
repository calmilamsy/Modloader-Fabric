package net.glasslauncher.shockahpi;

import net.minecraft.client.Minecraft;
import net.minecraft.src.*;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class DimensionBase {
   public static ArrayList<DimensionBase> list = new ArrayList();
   public static LinkedList<Integer> order = new LinkedList();
   public final int number;
   public final Class<? extends WorldProvider> worldProvider;
   public final Class<? extends Teleporter> teleporter;
   public String name = "Dimension";
   public String soundTrigger = "portal.trigger";
   public String soundTravel = "portal.travel";

   static {
      new DimensionOverworld();
      new DimensionNether();
   }

   public static DimensionBase getDimByNumber(int number) {
      for(int i = 0; i < list.size(); ++i) {
         DimensionBase dim = (DimensionBase)list.get(i);
         if(dim.number == number) {
            return dim;
         }
      }

      return null;
   }

   public static DimensionBase getDimByProvider(Class<? extends WorldProvider> worldProvider) {
      for(int i = 0; i < list.size(); ++i) {
         DimensionBase dim = (DimensionBase)list.get(i);
         if(dim.worldProvider.getName().equals(worldProvider.getName())) {
            return dim;
         }
      }

      return null;
   }

   public WorldProvider getWorldProvider() {
      try {
         return (WorldProvider)this.worldProvider.newInstance();
      } catch (InstantiationException var2) {
         ;
      } catch (IllegalAccessException var3) {
         ;
      }

      return null;
   }

   public Teleporter getTeleporter() {
      try {
         if(this.teleporter != null) {
            return (Teleporter)this.teleporter.newInstance();
         }
      } catch (InstantiationException var2) {
         ;
      } catch (IllegalAccessException var3) {
         ;
      }

      return null;
   }

   public static void respawn(boolean paramBoolean, int paramInt) {
      Minecraft localMinecraft = SAPI.getMinecraftInstance();
      if(!localMinecraft.theWorld.multiplayerWorld && !localMinecraft.theWorld.worldProvider.canRespawnHere()) {
         usePortal(0, true);
      }

      ChunkCoordinates localbp1 = null;
      ChunkCoordinates localbp2 = null;
      boolean i = true;
      if(localMinecraft.thePlayer != null && !paramBoolean) {
         localbp1 = localMinecraft.thePlayer.getPlayerSpawnCoordinate();
         if(localbp1 != null) {
            localbp2 = EntityPlayer.func_25060_a(localMinecraft.theWorld, localbp1);
            if(localbp2 == null) {
               localMinecraft.thePlayer.addChatMessage("tile.bed.notValid");
            }
         }
      }

      if(localbp2 == null) {
         localbp2 = localMinecraft.theWorld.getSpawnPoint();
         i = false;
      }

      IChunkProvider localcj = localMinecraft.theWorld.getIChunkProvider();
      if(localcj instanceof WorldGenDeadBush) {
         ChunkProviderLoadOrGenerate j = (ChunkProviderLoadOrGenerate)localcj;
         j.setCurrentChunkOver(localbp2.x >> 4, localbp2.z >> 4);
      }

      localMinecraft.theWorld.setSpawnLocation();
      localMinecraft.theWorld.updateEntityList();
      int j1 = 0;
      if(localMinecraft.thePlayer != null) {
         j1 = localMinecraft.thePlayer.entityId;
         localMinecraft.theWorld.setEntityDead(localMinecraft.thePlayer);
      }

      localMinecraft.renderViewEntity = null;
      localMinecraft.thePlayer = (EntityPlayerSP)localMinecraft.playerController.createPlayer(localMinecraft.theWorld);
      localMinecraft.thePlayer.dimension = paramInt;
      localMinecraft.renderViewEntity = localMinecraft.thePlayer;
      localMinecraft.thePlayer.preparePlayerToSpawn();
      if(i) {
         localMinecraft.thePlayer.setPlayerSpawnCoordinate(localbp1);
         localMinecraft.thePlayer.setLocationAndAngles((double)((float)localbp2.x + 0.5F), (double)((float)localbp2.y + 0.1F), (double)((float)localbp2.z + 0.5F), 0.0F, 0.0F);
      }

      localMinecraft.playerController.flipPlayer(localMinecraft.thePlayer);
      localMinecraft.theWorld.spawnPlayerWithLoadedChunks(localMinecraft.thePlayer);
      localMinecraft.thePlayer.movementInput = new MovementInputFromOptions(localMinecraft.gameSettings);
      localMinecraft.thePlayer.entityId = j1;
      localMinecraft.thePlayer.func_6420_o();
      localMinecraft.playerController.func_6473_b(localMinecraft.thePlayer);

      try {
         Method localException = Minecraft.class.getDeclaredMethod("d", new Class[]{String.class});
         localException.setAccessible(true);
         localException.invoke(localMinecraft, new Object[]{"Respawning"});
      } catch (Exception var9) {
         var9.printStackTrace();
      }

      if(localMinecraft.currentScreen instanceof GuiGameOver) {
         localMinecraft.displayGuiScreen((GuiScreen)null);
      }

   }

   public static void usePortal(int dimNumber) {
      usePortal(dimNumber, false);
   }

   private static void usePortal(int dimNumber, boolean resetOrder) {
      Minecraft game = SAPI.getMinecraftInstance();
      int oldDimension = game.thePlayer.dimension;
      int newDimension = dimNumber;
      if(oldDimension == dimNumber) {
         newDimension = 0;
      }

      game.theWorld.setEntityDead(game.thePlayer);
      game.thePlayer.isDead = false;
      Loc loc = new Loc(game.thePlayer.posX, game.thePlayer.posZ);
      if(newDimension != 0) {
         order.push(Integer.valueOf(newDimension));
      }

      if(newDimension == 0 && !order.isEmpty()) {
         newDimension = ((Integer)order.pop()).intValue();
      }

      if(oldDimension == newDimension) {
         newDimension = 0;
      }

      String str = "";

      Integer world;
      for(Iterator dimOld = order.iterator(); dimOld.hasNext(); str = str + world) {
         world = (Integer)dimOld.next();
         if(!str.isEmpty()) {
            str = str + ",";
         }
      }

      world = null;
      DimensionBase dimOld1 = getDimByNumber(oldDimension);
      DimensionBase dimNew = getDimByNumber(newDimension);
      loc = dimOld1.getDistanceScale(loc, true);
      loc = dimNew.getDistanceScale(loc, false);
      game.thePlayer.dimension = newDimension;
      game.thePlayer.setLocationAndAngles(loc.x, game.thePlayer.posY, loc.z, game.thePlayer.rotationYaw, game.thePlayer.rotationPitch);
      game.theWorld.updateEntityWithOptionalForce(game.thePlayer, false);
      World world1 = new World(game.theWorld, dimNew.getWorldProvider());
      game.changeWorld(world1, (newDimension == 0?"Leaving":"Entering") + " the " + (newDimension == 0?dimOld1.name:dimNew.name), game.thePlayer);
      game.thePlayer.worldObj = game.theWorld;
      game.thePlayer.setLocationAndAngles(loc.x, game.thePlayer.posY, loc.z, game.thePlayer.rotationYaw, game.thePlayer.rotationPitch);
      game.theWorld.updateEntityWithOptionalForce(game.thePlayer, false);
      Teleporter teleporter = dimNew.getTeleporter();
      if(teleporter == null) {
         teleporter = dimOld1.getTeleporter();
      }

      teleporter.func_4107_a(game.theWorld, game.thePlayer);
   }

   public DimensionBase(int number, Class<? extends WorldProvider> worldProvider, Class<? extends Teleporter> teleporter) {
      this.number = number;
      this.worldProvider = worldProvider;
      this.teleporter = teleporter;
      list.add(this);
   }

   public Loc getDistanceScale(Loc loc, boolean goingIn) {
      return loc;
   }
}
