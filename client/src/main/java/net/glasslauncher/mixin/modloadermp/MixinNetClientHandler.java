package net.glasslauncher.mixin.modloadermp;

import java.lang.reflect.Field;

import net.glasslauncher.modloader.ModLoader;
import net.glasslauncher.modloadermp.ModLoaderMp;
import net.glasslauncher.modloadermp.NetClientHandlerEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(NetClientHandler.class)
public abstract class MixinNetClientHandler extends NetHandler {

   @Override
   public boolean isServerHandler() {
      return false;
   }

   @Shadow private WorldClient worldClient;

   @Shadow protected abstract Entity getEntityByID(int i);

   /**
    * @author calmilamsy
    * @reason PITA
    */
   @Overwrite
   public void handleVehicleSpawn(Packet23VehicleSpawn packet23vehiclespawn) {
      double d = (double)packet23vehiclespawn.xPosition / 32.0D;
      double d1 = (double)packet23vehiclespawn.yPosition / 32.0D;
      double d2 = (double)packet23vehiclespawn.zPosition / 32.0D;
      Object obj = null;
      if(packet23vehiclespawn.type == 10) {
         obj = new EntityMinecart(this.worldClient, d, d1, d2, 0);
      }

      if(packet23vehiclespawn.type == 11) {
         obj = new EntityMinecart(this.worldClient, d, d1, d2, 1);
      }

      if(packet23vehiclespawn.type == 12) {
         obj = new EntityMinecart(this.worldClient, d, d1, d2, 2);
      }

      if(packet23vehiclespawn.type == 90) {
         obj = new EntityFish(this.worldClient, d, d1, d2);
      }

      if(packet23vehiclespawn.type == 60) {
         obj = new EntityArrow(this.worldClient, d, d1, d2);
      }

      if(packet23vehiclespawn.type == 61) {
         obj = new EntitySnowball(this.worldClient, d, d1, d2);
      }

      if(packet23vehiclespawn.type == 63) {
         obj = new EntityFireball(this.worldClient, d, d1, d2, (double)packet23vehiclespawn.field_28047_e / 8000.0D, (double)packet23vehiclespawn.field_28046_f / 8000.0D, (double)packet23vehiclespawn.field_28045_g / 8000.0D);
         packet23vehiclespawn.field_28044_i = 0;
      }

      if(packet23vehiclespawn.type == 62) {
         obj = new EntityEgg(this.worldClient, d, d1, d2);
      }

      if(packet23vehiclespawn.type == 1) {
         obj = new EntityBoat(this.worldClient, d, d1, d2);
      }

      if(packet23vehiclespawn.type == 50) {
         obj = new EntityTNTPrimed(this.worldClient, d, d1, d2);
      }

      if(packet23vehiclespawn.type == 70) {
         obj = new EntityFallingSand(this.worldClient, d, d1, d2, Block.sand.blockID);
      }

      if(packet23vehiclespawn.type == 71) {
         obj = new EntityFallingSand(this.worldClient, d, d1, d2, Block.gravel.blockID);
      }

      //TODO: edit
      NetClientHandlerEntity netclienthandlerentity = ModLoaderMp.HandleNetClientHandlerEntities(packet23vehiclespawn.type);
      if(netclienthandlerentity != null) {
         try {
            obj = netclienthandlerentity.entityClass.getConstructor(new Class[]{World.class, Double.TYPE, Double.TYPE, Double.TYPE}).newInstance(new Object[]{this.worldClient, Double.valueOf(d), Double.valueOf(d1), Double.valueOf(d2)});
            if(netclienthandlerentity.entityHasOwner) {
               Field entity = netclienthandlerentity.entityClass.getField("owner");
               if(!Entity.class.isAssignableFrom(entity.getType())) {
                  throw new Exception(String.format("Entity\'s owner field must be of type Entity, but it is of type %s.", new Object[]{entity.getType()}));
               }

               Entity entity1 = this.getEntityByID(packet23vehiclespawn.field_28044_i);
               if(entity1 == null) {
                  ModLoaderMp.Log("Received spawn packet for entity with owner, but owner was not found.");
               } else {
                  if(!entity.getType().isAssignableFrom(entity1.getClass())) {
                     throw new Exception(String.format("Tried to assign an entity of type %s to entity owner, which is of type %s.", new Object[]{entity1.getClass(), entity.getType()}));
                  }

                  entity.set(obj, entity1);
               }
            }
         } catch (Exception var12) {
            ModLoader.getLogger().throwing("NetClientHandler", "handleVehicleSpawn", var12);
            ModLoader.throwException(String.format("Error initializing entity of type %s.", new Object[]{Integer.valueOf(packet23vehiclespawn.type)}), var12);
            return;
         }
      }

      if(obj != null) {
         ((Entity)obj).serverPosX = packet23vehiclespawn.xPosition;
         ((Entity)obj).serverPosY = packet23vehiclespawn.yPosition;
         ((Entity)obj).serverPosZ = packet23vehiclespawn.zPosition;
         ((Entity)obj).rotationYaw = 0.0F;
         ((Entity)obj).rotationPitch = 0.0F;
         ((Entity)obj).entityId = packet23vehiclespawn.entityId;
         this.worldClient.func_712_a(packet23vehiclespawn.entityId, (Entity)obj);
         if(packet23vehiclespawn.field_28044_i > 0) {
            if(packet23vehiclespawn.type == 60) {
               Entity entity1 = this.getEntityByID(packet23vehiclespawn.field_28044_i);
               if(entity1 instanceof EntityLiving) {
                  ((EntityArrow)obj).owner = (EntityLiving)entity1;
               }
            }

            ((Entity)obj).setVelocity((double)packet23vehiclespawn.field_28047_e / 8000.0D, (double)packet23vehiclespawn.field_28046_f / 8000.0D, (double)packet23vehiclespawn.field_28045_g / 8000.0D);
         }
      }

   }


   @Shadow private Minecraft mc;

   /**
    * @author calmilamsy
    * @reason Modified if statement.
    */
   @Overwrite
   public void func_20087_a(Packet100OpenWindow packet100openwindow) {
      if(packet100openwindow.inventoryType == 0) {
         InventoryBasic entityplayersp = new InventoryBasic(packet100openwindow.windowTitle, packet100openwindow.slotsCount);
         this.mc.thePlayer.displayGUIChest(entityplayersp);
         this.mc.thePlayer.craftingInventory.windowId = packet100openwindow.windowId;
      } else if(packet100openwindow.inventoryType == 2) {
         TileEntityFurnace entityplayersp1 = new TileEntityFurnace();
         this.mc.thePlayer.displayGUIFurnace(entityplayersp1);
         this.mc.thePlayer.craftingInventory.windowId = packet100openwindow.windowId;
      } else if(packet100openwindow.inventoryType == 3) {
         TileEntityDispenser entityplayersp2 = new TileEntityDispenser();
         this.mc.thePlayer.displayGUIDispenser(entityplayersp2);
         this.mc.thePlayer.craftingInventory.windowId = packet100openwindow.windowId;
      } else if(packet100openwindow.inventoryType == 1) {
         EntityPlayerSP entityplayersp3 = this.mc.thePlayer;
         this.mc.thePlayer.displayWorkbenchGUI(MathHelper.floor_double(entityplayersp3.posX), MathHelper.floor_double(entityplayersp3.posY), MathHelper.floor_double(entityplayersp3.posZ));
         this.mc.thePlayer.craftingInventory.windowId = packet100openwindow.windowId;
      } else {
         ModLoaderMp.HandleGUI(packet100openwindow);
      }

   }
}
