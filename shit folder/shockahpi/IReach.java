package net.glasslauncher.shockahpi;

import net.minecraft.src.ItemStack;

public interface IReach {
   boolean reachItemMatches(ItemStack var1);

   float getReach(ItemStack var1);
}
