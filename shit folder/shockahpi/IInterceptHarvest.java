package net.glasslauncher.shockahpi;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.World;

public interface IInterceptHarvest {
   boolean canIntercept(World var1, EntityPlayer var2, Loc var3, int var4, int var5);

   void intercept(World var1, EntityPlayer var2, Loc var3, int var4, int var5);
}
