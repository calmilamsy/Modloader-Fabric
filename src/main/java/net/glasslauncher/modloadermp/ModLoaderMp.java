package net.glasslauncher.modloadermp;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.glasslauncher.modloader.BaseMod;
import net.glasslauncher.modloader.ModLoader;
import net.minecraft.client.gui.screen.ScreenBase;
import net.minecraft.client.level.ClientLevel;
import net.minecraft.entity.EntityBase;
import net.minecraft.level.Level;
import net.minecraft.network.PacketHandler;
import net.minecraft.packet.AbstractPacket;
import net.minecraft.packet.Id10Packet;
import net.minecraft.packet.play.OpenScreenS2C;

public class ModLoaderMp {
   public static final String NAME = "ModLoaderMP";
   public static final String VERSION = "Beta 1.7.3 unofficial";
   private static boolean hasInit = false;
   private static boolean packet230Received = false;
   private static Map netClientHandlerEntityMap = new HashMap();
   private static Map guiModMap = new HashMap();

   public static void staticInit() {
      if(!hasInit) {
         init();
      }

   }

   @Environment(EnvType.CLIENT)
   public static void HandleAllPackets(Packet230ModLoader packet230modloader) {
      if(!hasInit) {
         init();
      }

      packet230Received = true;
      if(packet230modloader.modId == "ModLoaderMP".hashCode()) {
         switch(packet230modloader.packetType) {
         case 0:
            handleModCheck(packet230modloader);
            break;
         case 1:
            handleTileEntityPacket(packet230modloader);
         }
      } else if(packet230modloader.modId == "Spawn".hashCode()) {
         NetClientHandlerEntity i = HandleNetClientHandlerEntities(packet230modloader.packetType);
         if(i != null && ISpawnable.class.isAssignableFrom(i.entityClass)) {
            try {
               EntityBase basemod = (EntityBase) i.entityClass.getConstructor(new Class[]{Level.class}).newInstance(new Object[]{ModLoader.getMinecraftInstance().level});
               ((ISpawnable)basemod).spawn(packet230modloader);
               ((ClientLevel) ModLoader.getMinecraftInstance().level).method_1495(basemod.entityId, basemod);
            } catch (Exception var4) {
               ModLoader.getLogger().throwing("ModLoader", "handleCustomSpawn", var4);
               ModLoader.throwException(String.format("Error initializing entity of type %s.", new Object[]{Integer.valueOf(packet230modloader.packetType)}), var4);
               return;
            }
         }
      } else {
         for(int var5 = 0; var5 < ModLoader.getLoadedMods().size(); ++var5) {
            BaseMod var6 = (BaseMod)ModLoader.getLoadedMods().get(var5);
            if(var6 instanceof BaseModMp) {
               BaseModMp basemodmp = (BaseModMp)var6;
               if(basemodmp.getId() == packet230modloader.modId) {
                  basemodmp.HandlePacket(packet230modloader);
                  break;
               }
            }
         }
      }

   }

   public static NetClientHandlerEntity HandleNetClientHandlerEntities(int i) {
      if(!hasInit) {
         init();
      }

      return netClientHandlerEntityMap.containsKey(Integer.valueOf(i))?(NetClientHandlerEntity)netClientHandlerEntityMap.get(Integer.valueOf(i)):null;
   }

   @Environment(EnvType.CLIENT)
   public static void SendPacket(BaseModMp basemodmp, Packet230ModLoader packet230modloader) {
      if(!hasInit) {
         init();
      }

      if(basemodmp == null) {
         IllegalArgumentException illegalargumentexception = new IllegalArgumentException("baseModMp cannot be null.");
         ModLoader.getLogger().throwing("ModLoaderMp", "SendPacket", illegalargumentexception);
         ModLoader.throwException("baseModMp cannot be null.", illegalargumentexception);
      } else {
         packet230modloader.modId = basemodmp.getId();
         sendPacket(packet230modloader);
      }

   }

   public static void RegisterGUI(BaseModMp basemodmp, int i) {
      if(!hasInit) {
         init();
      }

      if(guiModMap.containsKey(Integer.valueOf(i))) {
         Log("RegisterGUI error: inventoryType already registered.");
      } else {
         guiModMap.put(Integer.valueOf(i), basemodmp);
      }

   }

   @Environment(EnvType.CLIENT)
   public static void HandleGUI(OpenScreenS2C packet100openwindow) {
      if(!hasInit) {
         init();
      }

      BaseModMp basemodmp = (BaseModMp)guiModMap.get(packet100openwindow.containerId);
      ScreenBase guiscreen = basemodmp.HandleGUI(packet100openwindow.containerId);
      if(guiscreen != null) {
         ModLoader.openGUI(ModLoader.getMinecraftInstance().player, guiscreen);
         ModLoader.getMinecraftInstance().player.container.currentContainerId = packet100openwindow.screenId;
      }

   }

   public static void RegisterNetClientHandlerEntity(Class class1, int i) {
      RegisterNetClientHandlerEntity(class1, false, i);
   }

   public static void RegisterNetClientHandlerEntity(Class class1, boolean flag, int i) {
      if(!hasInit) {
         init();
      }

      if(i > 255) {
         Log("RegisterNetClientHandlerEntity error: entityId cannot be greater than 255.");
      } else if(netClientHandlerEntityMap.containsKey(Integer.valueOf(i))) {
         Log("RegisterNetClientHandlerEntity error: entityId already registered.");
      } else {
         if(i > 127) {
            i -= 256;
         }

         netClientHandlerEntityMap.put(Integer.valueOf(i), new NetClientHandlerEntity(class1, flag));
      }

   }

   @Environment(EnvType.CLIENT)
   public static void SendKey(BaseModMp basemodmp, int i) {
      if(!hasInit) {
         init();
      }

      if(basemodmp == null) {
         IllegalArgumentException packet230modloader = new IllegalArgumentException("baseModMp cannot be null.");
         ModLoader.getLogger().throwing("ModLoaderMp", "SendKey", packet230modloader);
         ModLoader.throwException("baseModMp cannot be null.", packet230modloader);
      } else {
         Packet230ModLoader packet230modloader1 = new Packet230ModLoader();
         packet230modloader1.modId = "ModLoaderMP".hashCode();
         packet230modloader1.packetType = 1;
         packet230modloader1.dataInt = new int[]{basemodmp.getId(), i};
         sendPacket(packet230modloader1);
      }

   }

   public static void Log(String s) {
      System.out.println(s);
      ModLoader.getLogger().fine(s);
   }

   private static void init() {
      hasInit = true;

      try {
         Method securityexception;
         try {
            securityexception = AbstractPacket.class.getDeclaredMethod("a", new Class[]{Integer.TYPE, Boolean.TYPE, Boolean.TYPE, Class.class});
         } catch (NoSuchMethodException var2) {
            securityexception = AbstractPacket.class.getDeclaredMethod("register", int.class, boolean.class, boolean.class, Class.class);
         }

         securityexception.setAccessible(true);
         securityexception.invoke(null, 230, Boolean.TRUE, Boolean.TRUE, Packet230ModLoader.class);
      } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException var3) {
         ModLoader.getLogger().throwing("ModLoaderMp", "init", var3);
         ModLoader.throwException("An impossible error has occurred!", var3);
      }

      Log("ModLoaderMP Beta 1.7.3 unofficial Initialized");
   }

   @Environment(EnvType.CLIENT)
   private static void handleModCheck(Packet230ModLoader packet230modloader) {
      Packet230ModLoader packet230modloader1 = new Packet230ModLoader();
      packet230modloader1.modId = "ModLoaderMP".hashCode();
      packet230modloader1.packetType = 0;
      packet230modloader1.dataString = new String[ModLoader.getLoadedMods().size()];

      for(int i = 0; i < ModLoader.getLoadedMods().size(); ++i) {
         packet230modloader1.dataString[i] = ((BaseMod)ModLoader.getLoadedMods().get(i)).toString();
      }

      sendPacket(packet230modloader1);
   }

   private static void handleTileEntityPacket(Packet230ModLoader packet230modloader) {
      if(packet230modloader.dataInt != null && packet230modloader.dataInt.length >= 5) {
         int i = packet230modloader.dataInt[0];
         int j = packet230modloader.dataInt[1];
         int k = packet230modloader.dataInt[2];
         int l = packet230modloader.dataInt[3];
         int i1 = packet230modloader.dataInt[4];
         int[] ai = new int[packet230modloader.dataInt.length - 5];
         System.arraycopy(packet230modloader.dataInt, 5, ai, 0, packet230modloader.dataInt.length - 5);
         float[] af = packet230modloader.dataFloat;
         String[] as = packet230modloader.dataString;

         for(int j1 = 0; j1 < ModLoader.getLoadedMods().size(); ++j1) {
            BaseMod basemod = (BaseMod)ModLoader.getLoadedMods().get(j1);
            if(basemod instanceof BaseModMp) {
               BaseModMp basemodmp = (BaseModMp)basemod;
               if(basemodmp.getId() == i) {
                  basemodmp.HandleTileEntityPacket(j, k, l, i1, ai, af, as);
                  break;
               }
            }
         }
      } else {
         Log("Bad TileEntityPacket received.");
      }

   }

   @Environment(EnvType.CLIENT)
   private static void sendPacket(Packet230ModLoader packet230modloader) {
      if(packet230Received && ModLoader.getMinecraftInstance().level != null && ModLoader.getMinecraftInstance().level.isClient) {
         ModLoader.getMinecraftInstance().getNetworkHandler().sendPacket(packet230modloader);
      }

   }

   public static BaseModMp GetModInstance(Class class1) {
      for(int i = 0; i < ModLoader.getLoadedMods().size(); ++i) {
         BaseMod basemod = (BaseMod)ModLoader.getLoadedMods().get(i);
         if(basemod instanceof BaseModMp) {
            BaseModMp basemodmp = (BaseModMp)basemod;
            if(class1.isInstance(basemodmp)) {
               return (BaseModMp)ModLoader.getLoadedMods().get(i);
            }
         }
      }

      return null;
   }
}
