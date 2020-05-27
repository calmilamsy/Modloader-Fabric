package net.glasslauncher.shockahpi;

import net.minecraft.src.Block;
import net.minecraft.src.ItemStack;

import java.util.Random;

public class DungeonLoot {
   public final ItemStack loot;
   public final int min;
   public final int max;

   public DungeonLoot(ItemStack stack) {
      this.loot = new ItemStack(stack.itemID, 1, stack.getItemDamage());
      this.min = this.max = stack.stackSize;
   }

   public DungeonLoot(ItemStack stack, int min, int max) {
      this.loot = new ItemStack(stack.itemID, 1, stack.getItemDamage());
      this.min = min;
      this.max = max;
   }

   public ItemStack getStack() {
      int damage = 0;
      if(this.loot.itemID <= 255) {
         if(Block.blocksList[this.loot.itemID].getRenderColor(1) != 1) {
            damage = this.loot.getItemDamage();
         } else if(!this.loot.getItem().isFull3D()) {
            damage = this.loot.getItemDamage();
         }
      }

      return new ItemStack(this.loot.itemID, this.min + (new Random()).nextInt(this.max - this.min + 1), damage);
   }
}
