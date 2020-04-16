// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 
// Source File Name:   AnimShift.java

package net.glasslauncher.shockahpi;


// Referenced classes of package net.minecraft.src:
//            AnimBase

public class AnimShift extends AnimBase
{

    public AnimShift(int spriteID, String spritePath, int h, int v)
    {
        super(spriteID, spritePath);
        this.h = h;
        this.v = v;
        getCleanFrame();
    }

    public void onTick()
    {
        animFrame();
        copyFrameToArray();
    }

    public void animFrame()
    {
        shiftFrame(h, v, true, true);
    }

    private final int h;
    private final int v;
}
