// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 
// Source File Name:   BlockHarvestPower.java

package net.glasslauncher.shockahpi;


public class BlockHarvestPower
{

    public BlockHarvestPower(int blockID, float percentage)
    {
        this.blockID = blockID;
        this.percentage = percentage;
    }

    public boolean equals(Object other)
    {
        if(other == null)
        {
            return false;
        }
        if(other instanceof BlockHarvestPower)
        {
            return blockID == ((BlockHarvestPower)other).blockID;
        }
        if(other instanceof Integer)
        {
            return blockID == ((Integer)other).intValue();
        } else
        {
            return false;
        }
    }

    public final int blockID;
    public final float percentage;
}
