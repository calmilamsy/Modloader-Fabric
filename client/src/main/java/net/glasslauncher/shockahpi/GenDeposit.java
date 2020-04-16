// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 
// Source File Name:   GenDeposit.java

package net.glasslauncher.shockahpi;

import net.minecraft.src.World;

import java.util.*;

// Referenced classes of package net.minecraft.src:
//            Loc, World

public class GenDeposit
{

    public GenDeposit(World world, int blockID, Integer set1stOn[], Integer setOn[])
    {
        check = new ArrayList();
        cantSet = new ArrayList();
        sidesTmp = new ArrayList();
        this.world = world;
        this.blockID = blockID;
        this.set1stOn = Arrays.asList(set1stOn);
        this.setOn = Arrays.asList(setOn);
        rand = world.rand;
    }

    public void gen(int pX, int pY, int pZ, int maxAmount, int maxTries)
    {
        gen(new Loc(pX, pY, pZ), maxAmount, maxTries);
    }

    public void gen(Loc startLoc, int maxAmount, int maxTries)
    {
        if(!set1stOn.contains(Integer.valueOf(startLoc.getBlock(world))))
        {
            return;
        }
        check.add(startLoc);
        while(maxAmount > 0 && maxTries > 0 && !check.isEmpty()) 
        {
            maxTries--;
            int i = rand.nextInt(check.size());
            Loc curLoc = (Loc)check.get(i);
            check.remove(i);
            sidesTmp = new ArrayList(sides);
            if(setOn.contains(Integer.valueOf(curLoc.getBlock(world))))
            {
                curLoc.setBlock(world, blockID);
                cantSet.add(curLoc);
                check.addAll(Arrays.asList(curLoc.adjacent()));
                maxAmount--;
            }
            while(!sidesTmp.isEmpty()) 
            {
                i = rand.nextInt(sidesTmp.size());
                Loc side = (Loc)sidesTmp.get(i);
                sidesTmp.remove(i);
                if(!cantSet.contains(side));
            }
        }
    }

    public int getGoodY(int pX, int pY, int pZ)
    {
        int off = 0;
        if(set1stOn.contains(Integer.valueOf(world.getBlockId(pX, pY, pZ))))
        {
            return pY;
        }
        do
        {
            off++;
            if(pY + off <= 127 && set1stOn.contains(Integer.valueOf(world.getBlockId(pX, pY + off, pZ))))
            {
                return pY + off;
            }
            if(pY - off >= 0 && set1stOn.contains(Integer.valueOf(world.getBlockId(pX, pY - off, pZ))))
            {
                return pY - off;
            }
        } while(pY + off <= 127 || pY - off >= 0);
        return -1;
    }

    private final int blockID;
    private final List set1stOn;
    private final List setOn;
    private final List sides = Arrays.asList(Loc.vecAdjacent());
    private ArrayList check;
    private ArrayList cantSet;
    private ArrayList sidesTmp;
    private final World world;
    private final Random rand;
}
