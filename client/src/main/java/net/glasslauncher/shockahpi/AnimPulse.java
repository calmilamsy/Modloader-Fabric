// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 
// Source File Name:   AnimPulse.java

package net.glasslauncher.shockahpi;

import java.awt.Color;

// Referenced classes of package net.minecraft.src:
//            AnimBase

public class AnimPulse extends AnimBase
{

    public AnimPulse(int spriteID, String spritePath, int animMax, Color c1, Color c2)
    {
        super(spriteID, spritePath);
        animState = 0;
        animAdd = 1;
        this.animMax = animMax;
        this.c1 = c1;
        this.c2 = c2;
    }

    public void animFrame()
    {
        animState += animAdd;
        if(animState == animMax || animState == 0)
        {
            animAdd *= -1;
        }
        drawRect(0, 0, size, size, merge(c1, c2, (float)animState / (float)animMax), mdBlend);
    }

    private int animState;
    private int animAdd;
    private final int animMax;
    private final Color c1;
    private final Color c2;
}
