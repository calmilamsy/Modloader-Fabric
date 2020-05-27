package net.glasslauncher.shockahpi;

import net.minecraft.src.Block;
import net.minecraft.src.Material;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class ToolBase {
   public static final ToolBase Pickaxe;
   public static final ToolBase Shovel;
   public static final ToolBase Axe;
   public ArrayList<BlockHarvestPower> mineBlocks = new ArrayList();
   public ArrayList<Material> mineMaterials = new ArrayList();

   static {
      SAPI.showText();
      Pickaxe = new ToolBase();
      Shovel = new ToolBase();
      Axe = new ToolBase();
      List list = Arrays.asList(new Integer[]{Integer.valueOf(1), Integer.valueOf(4), Integer.valueOf(16), Integer.valueOf(21), Integer.valueOf(22), Integer.valueOf(23), Integer.valueOf(24), Integer.valueOf(43), Integer.valueOf(44), Integer.valueOf(45), Integer.valueOf(48), Integer.valueOf(52), Integer.valueOf(61), Integer.valueOf(62), Integer.valueOf(67), Integer.valueOf(77), Integer.valueOf(79), Integer.valueOf(87), Integer.valueOf(89), Integer.valueOf(93), Integer.valueOf(94)});
      Iterator var2 = list.iterator();

      Integer blockID;
      while(var2.hasNext()) {
         blockID = (Integer)var2.next();
         Pickaxe.mineBlocks.add(new BlockHarvestPower(blockID.intValue(), 20.0F));
      }

      list = Arrays.asList(new Integer[]{Integer.valueOf(15), Integer.valueOf(42), Integer.valueOf(71)});
      var2 = list.iterator();

      while(var2.hasNext()) {
         blockID = (Integer)var2.next();
         Pickaxe.mineBlocks.add(new BlockHarvestPower(blockID.intValue(), 40.0F));
      }

      list = Arrays.asList(new Integer[]{Integer.valueOf(14), Integer.valueOf(41), Integer.valueOf(56), Integer.valueOf(57), Integer.valueOf(73), Integer.valueOf(74)});
      var2 = list.iterator();

      while(var2.hasNext()) {
         blockID = (Integer)var2.next();
         Pickaxe.mineBlocks.add(new BlockHarvestPower(blockID.intValue(), 60.0F));
      }

      list = Arrays.asList(new Integer[]{Integer.valueOf(49)});
      var2 = list.iterator();

      while(var2.hasNext()) {
         blockID = (Integer)var2.next();
         Pickaxe.mineBlocks.add(new BlockHarvestPower(blockID.intValue(), 80.0F));
      }

      list = Arrays.asList(new Integer[]{Integer.valueOf(2), Integer.valueOf(3), Integer.valueOf(12), Integer.valueOf(13), Integer.valueOf(78), Integer.valueOf(80), Integer.valueOf(82)});
      var2 = list.iterator();

      while(var2.hasNext()) {
         blockID = (Integer)var2.next();
         Shovel.mineBlocks.add(new BlockHarvestPower(blockID.intValue(), 20.0F));
      }

      list = Arrays.asList(new Integer[]{Integer.valueOf(5), Integer.valueOf(17), Integer.valueOf(18), Integer.valueOf(25), Integer.valueOf(47), Integer.valueOf(53), Integer.valueOf(54), Integer.valueOf(58), Integer.valueOf(63), Integer.valueOf(64), Integer.valueOf(65), Integer.valueOf(66), Integer.valueOf(68), Integer.valueOf(69), Integer.valueOf(81), Integer.valueOf(84), Integer.valueOf(85)});
      var2 = list.iterator();

      while(var2.hasNext()) {
         blockID = (Integer)var2.next();
         Axe.mineBlocks.add(new BlockHarvestPower(blockID.intValue(), 20.0F));
      }

   }

   public boolean canHarvest(Block block, float currentPower) {
      Iterator var4 = this.mineMaterials.iterator();

      while(var4.hasNext()) {
         Material power = (Material)var4.next();
         if(power == block.blockMaterial) {
            return true;
         }
      }

      var4 = this.mineBlocks.iterator();

      BlockHarvestPower power1;
      do {
         if(!var4.hasNext()) {
            return false;
         }

         power1 = (BlockHarvestPower)var4.next();
      } while(block.blockID != power1.blockID && currentPower < power1.percentage);

      return true;
   }
}
