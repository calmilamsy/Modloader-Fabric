// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 
// Source File Name:   IReach.java

package net.glasslauncher.shockahpi;


// Referenced classes of package net.minecraft.src:
//            ItemStack

import net.minecraft.src.ItemStack;

public interface IReach
{

    public abstract boolean reachItemMatches(ItemStack itemstack);

    public abstract float getReach(ItemStack itemstack);
}
