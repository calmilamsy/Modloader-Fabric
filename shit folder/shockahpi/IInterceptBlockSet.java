package net.glasslauncher.shockahpi;

import net.minecraft.src.World;

public interface IInterceptBlockSet {
   boolean canIntercept(World var1, Loc var2, int var3);

   int intercept(World var1, Loc var2, int var3);
}
