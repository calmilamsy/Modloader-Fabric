package net.glasslauncher.mixin.modloadermp;

import java.lang.reflect.Field;
import java.util.logging.Level;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.glasslauncher.modloader.ModLoader;
import net.glasslauncher.modloadermp.ModLoaderMp;
import net.glasslauncher.modloadermp.NetClientHandlerEntity;
import net.minecraft.block.BlockBase;
import net.minecraft.client.ClientInventory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.level.ClientLevel;
import net.minecraft.entity.*;
import net.minecraft.entity.player.AbstractClientPlayer;
import net.minecraft.entity.projectile.Arrow;
import net.minecraft.entity.projectile.Snowball;
import net.minecraft.entity.projectile.ThrownEgg;
import net.minecraft.entity.projectile.ThrownSnowball;
import net.minecraft.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketHandler;
import net.minecraft.packet.play.EntitySpawnS2C;
import net.minecraft.packet.play.OpenScreenS2C;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.maths.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class MixinNetClientHandler extends PacketHandler {

   @Shadow private ClientLevel level;

   @Shadow protected abstract EntityBase method_1645(int i);

   /**
    * @author calmilamsy
    * @reason PITA
    */
   @Environment(EnvType.CLIENT)
   @Overwrite
   public void handleEntitySpawn(EntitySpawnS2C packet23vehiclespawn) {
      double d = (double)packet23vehiclespawn.x / 32.0D;
      double d1 = (double)packet23vehiclespawn.y / 32.0D;
      double d2 = (double)packet23vehiclespawn.z / 32.0D;
      Object obj = null;
      if(packet23vehiclespawn.id == 10) {
         obj = new Minecart(this.level, d, d1, d2, 0);
      }

      if(packet23vehiclespawn.id == 11) {
         obj = new Minecart(this.level, d, d1, d2, 1);
      }

      if(packet23vehiclespawn.id == 12) {
         obj = new Minecart(this.level, d, d1, d2, 2);
      }

      if(packet23vehiclespawn.id == 90) {
         obj = new FishHook(this.level, d, d1, d2);
      }

      if(packet23vehiclespawn.id == 60) {
         obj = new Arrow(this.level, d, d1, d2);
      }

      if(packet23vehiclespawn.id == 61) {
         obj = new ThrownSnowball(this.level, d, d1, d2);
      }

      if(packet23vehiclespawn.id == 63) {
         obj = new Snowball(this.level, d, d1, d2, (double)packet23vehiclespawn.field_1667 / 8000.0D, (double)packet23vehiclespawn.field_1668 / 8000.0D, (double)packet23vehiclespawn.field_1669 / 8000.0D);
         packet23vehiclespawn.field_1671 = 0;
      }

      if(packet23vehiclespawn.id == 62) {
         obj = new ThrownEgg(this.level, d, d1, d2);
      }

      if(packet23vehiclespawn.id == 1) {
         obj = new Boat(this.level, d, d1, d2);
      }

      if(packet23vehiclespawn.id == 50) {
         obj = new PrimedTnt(this.level, d, d1, d2);
      }

      if(packet23vehiclespawn.id == 70) {
         obj = new FallingBlock(this.level, d, d1, d2, BlockBase.SAND.id);
      }

      if(packet23vehiclespawn.id == 71) {
         obj = new FallingBlock(this.level, d, d1, d2, BlockBase.GRAVEL.id);
      }

      //TODO: edit
      NetClientHandlerEntity netclienthandlerentity = ModLoaderMp.HandleNetClientHandlerEntities(packet23vehiclespawn.id);
      if(netclienthandlerentity != null) {
         try {
            obj = netclienthandlerentity.entityClass.getConstructor(new Class[]{Level.class, Double.TYPE, Double.TYPE, Double.TYPE}).newInstance(new Object[]{this.level, Double.valueOf(d), Double.valueOf(d1), Double.valueOf(d2)});
            if(netclienthandlerentity.entityHasOwner) {
               Field entity = netclienthandlerentity.entityClass.getField("owner");
               if(!EntityBase.class.isAssignableFrom(entity.getType())) {
                  throw new Exception(String.format("EntityBase\'s owner field must be of id EntityBase, but it is of id %s.", new Object[]{entity.getType()}));
               }

               EntityBase entity1 = this.method_1645(packet23vehiclespawn.field_1671);
               if(entity1 == null) {
                  ModLoaderMp.Log("Received spawn packet for entity with owner, but owner was not found.");
               } else {
                  if(!entity.getType().isAssignableFrom(entity1.getClass())) {
                     throw new Exception(String.format("Tried to assign an entity of id %s to entity owner, which is of id %s.", new Object[]{entity1.getClass(), entity.getType()}));
                  }

                  entity.set(obj, entity1);
               }
            }
         } catch (Exception var12) {
            ModLoader.getLogger().throwing("NetClientHandler", "handleVehicleSpawn", var12);
            ModLoader.throwException(String.format("Error initializing entity of id %s.", new Object[]{Integer.valueOf(packet23vehiclespawn.id)}), var12);
            return;
         }
      }

      if(obj != null) {
         ((EntityBase)obj).x = packet23vehiclespawn.x;
         ((EntityBase)obj).y = packet23vehiclespawn.y;
         ((EntityBase)obj).z = packet23vehiclespawn.z;
         ((EntityBase)obj).yaw = 0.0F;
         ((EntityBase)obj).pitch = 0.0F;
         ((EntityBase)obj).entityId = packet23vehiclespawn.field_1663;
         this.level.method_1495(packet23vehiclespawn.field_1663, (EntityBase)obj);
         if(packet23vehiclespawn.field_1671 > 0) {
            if(packet23vehiclespawn.id == 60) {
               EntityBase entity1 = this.method_1645(packet23vehiclespawn.field_1671);
               if(entity1 instanceof Living) {
                  ((Arrow)obj).owner = (Living) entity1;
               }
            }

            ((EntityBase)obj).setVelocity((double)packet23vehiclespawn.field_1667 / 8000.0D, (double)packet23vehiclespawn.field_1668 / 8000.0D, (double)packet23vehiclespawn.field_1669 / 8000.0D);
         }
      }

   }


   @Shadow private Minecraft minecraft;

   /**
    * @author calmilamsy
    * @reason Modified if statement.
    */
   @Environment(EnvType.CLIENT)
   @Overwrite
   public void handleScreenOpen(OpenScreenS2C packet100openwindow) {
      if(packet100openwindow.containerId == 0) {
         ClientInventory entityplayersp = new ClientInventory(packet100openwindow.field_743, packet100openwindow.field_744);
         this.minecraft.player.openChestScreen(entityplayersp);
         this.minecraft.player.container.currentContainerId = packet100openwindow.screenId;
      } else if(packet100openwindow.containerId == 2) {
         TileEntityFurnace entityplayersp1 = new TileEntityFurnace();
         this.minecraft.player.openFurnaceScreen(entityplayersp1);
         this.minecraft.player.container.currentContainerId = packet100openwindow.screenId;
      } else if(packet100openwindow.containerId == 3) {
         TileEntityDispenser entityplayersp2 = new TileEntityDispenser();
         this.minecraft.player.openDispenserScreen(entityplayersp2);
         this.minecraft.player.container.currentContainerId = packet100openwindow.screenId;
      } else if(packet100openwindow.containerId == 1) {
         AbstractClientPlayer entityplayersp3 = this.minecraft.player;
         this.minecraft.player.openCraftingScreen(MathHelper.floor(entityplayersp3.x), MathHelper.floor(entityplayersp3.y), MathHelper.floor(entityplayersp3.z));
         this.minecraft.player.container.currentContainerId = packet100openwindow.screenId;
      } else {
         ModLoaderMp.HandleGUI(packet100openwindow);
      }

   }
}
