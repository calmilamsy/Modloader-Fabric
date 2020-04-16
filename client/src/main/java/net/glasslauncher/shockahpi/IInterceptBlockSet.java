// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 
// Source File Name:   IInterceptBlockSet.java

package net.glasslauncher.shockahpi;


// Referenced classes of package net.minecraft.src:
//            World, Loc

import net.minecraft.src.World;

public interface IInterceptBlockSet
{

    public abstract boolean canIntercept(World world, Loc loc, int i);

    public abstract int intercept(World world, Loc loc, int i);
}
