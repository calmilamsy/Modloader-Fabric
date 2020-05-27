package net.glasslauncher.shockahpi;

import net.minecraft.src.*;

import java.util.ArrayList;
import java.util.Iterator;

public class Tool extends Item {
   public final boolean usingSAPI;
   public ToolBase toolBase;
   public final float baseDamage;
   public final float basePower;
   public final float defaultSpeed;
   public final float toolSpeed;
   public ArrayList<BlockHarvestPower> mineBlocks;
   public ArrayList<Material> mineMaterials;

   static {
      SAPI.showText();
   }

   public Tool(boolean usingSAPI, ToolBase toolBase, int itemID, int uses, float baseDamage, float basePower, float toolSpeed) {
      this(usingSAPI, toolBase, itemID, uses, baseDamage, basePower, toolSpeed, 1.0F);
   }

   public Tool(boolean usingSAPI, ToolBase toolBase, int itemID, int uses, float baseDamage, float basePower, float toolSpeed, float defaultSpeed) {
      super(itemID);
      this.mineBlocks = new ArrayList();
      this.mineMaterials = new ArrayList();
      this.setMaxDamage(uses);
      this.maxStackSize = 1;
      this.usingSAPI = usingSAPI;
      this.toolBase = toolBase;
      this.baseDamage = baseDamage;
      this.basePower = basePower;
      this.toolSpeed = toolSpeed;
      this.defaultSpeed = defaultSpeed;
   }

   public boolean isFull3D() {
      return true;
   }

   public boolean hitEntity(ItemStack stack, EntityLiving living, EntityLiving living2) {
      stack.damageItem(2, living2);
      return true;
   }

   public boolean onBlockDestroyed(ItemStack stack, int blockID, int x, int y, int z, EntityLiving living) {
      stack.damageItem(1, living);
      System.out.println("Boiiii");
      return true;
   }

   public int getDamageVsEntity(Entity entity) {
      return (int)Math.floor((double)this.baseDamage);
   }

   public float getPower() {
      return this.basePower;
   }

   public float getStrVsBlock(ItemStack stack, Block block) {
      return this.canHarvest(block)?this.toolSpeed:this.defaultSpeed;
   }

   public boolean canHarvest(Block block) {
      if(this.toolBase != null && this.toolBase.canHarvest(block, this.getPower())) {
         return true;
      } else {
         Iterator var3 = this.mineMaterials.iterator();

         while(var3.hasNext()) {
            Material power = (Material)var3.next();
            if(power == block.blockMaterial) {
               return true;
            }
         }

         var3 = this.mineBlocks.iterator();

         BlockHarvestPower power1;
         do {
            if(!var3.hasNext()) {
               return false;
            }

            power1 = (BlockHarvestPower)var3.next();
         } while(block.blockID != power1.blockID && this.getPower() < power1.percentage);

         return true;
      }
   }
}
