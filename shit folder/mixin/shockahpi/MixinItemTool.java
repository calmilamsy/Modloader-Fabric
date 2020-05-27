package net.glasslauncher.mixin.shockahpi;

import java.util.Iterator;

import net.glasslauncher.shockahpi.BlockHarvestPower;
import net.glasslauncher.shockahpi.Tool;
import net.glasslauncher.shockahpi.ToolBase;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ItemTool.class)
public class MixinItemTool {
   public EnumToolMaterial toolMaterial;

   protected MixinItemTool(int itemID, int damage, EnumToolMaterial material, Block[] blocks) {
      super(false, null, itemID, material.getMaxUses(), (float)(damage + material.getDamageVsEntity()), getToolPower(material), material.getEfficiencyOnProperMaterial());
      this.toolMaterial = material;
      this.toolBase = this.getToolBase();
      Block[] var8 = blocks;
      int var7 = blocks.length;

      for(int var6 = 0; var6 < var7; ++var6) {
         Block block = var8[var6];
         this.mineBlocks.add(new BlockHarvestPower(block.blockID, 0.0F));
      }

   }

   public ToolBase getToolBase() {
      return this instanceof ItemPickaxe?ToolBase.Pickaxe:(this instanceof ItemAxe?ToolBase.Axe:(this instanceof ItemSpade?ToolBase.Shovel:null));
   }

   public static float getToolPower(EnumToolMaterial material) {
      return material == EnumToolMaterial.EMERALD?80.0F:(material == EnumToolMaterial.IRON?60.0F:(material == EnumToolMaterial.STONE?40.0F:20.0F));
   }

   public boolean canHarvest(Block block) {
      if(!this.usingSAPI && !this.isBlockOnList(block.blockID)) {
         if(this instanceof ItemPickaxe) {
            if(this.shiftedIndex <= 369) {
               return false;
            }

            if(block.blockMaterial == Material.rock || block.blockMaterial == Material.ice) {
               return true;
            }

            if(block.blockMaterial == Material.iron && this.basePower >= 40.0F) {
               return true;
            }
         } else if(this instanceof ItemAxe) {
            if(this.shiftedIndex <= 369) {
               return false;
            }

            if(block.blockMaterial == Material.wood || block.blockMaterial == Material.leaves || block.blockMaterial == Material.plants || block.blockMaterial == Material.cactus || block.blockMaterial == Material.pumpkin) {
               return true;
            }
         } else if(this instanceof ItemSpade) {
            if(this.shiftedIndex <= 369) {
               return false;
            }

            if(block.blockMaterial == Material.grassMaterial || block.blockMaterial == Material.ground || block.blockMaterial == Material.sand || block.blockMaterial == Material.snow || block.blockMaterial == Material.builtSnow || block.blockMaterial == Material.clay) {
               return true;
            }
         }
      }

      return super.canHarvest(block);
   }

   private boolean isBlockOnList(int blockID) {
      Iterator var3 = this.mineBlocks.iterator();

      BlockHarvestPower power;
      while(var3.hasNext()) {
         power = (BlockHarvestPower)var3.next();
         if(power.blockID == blockID) {
            return true;
         }
      }

      var3 = this.toolBase.mineBlocks.iterator();

      while(var3.hasNext()) {
         power = (BlockHarvestPower)var3.next();
         if(power.blockID == blockID) {
            return true;
         }
      }

      return false;
   }
}