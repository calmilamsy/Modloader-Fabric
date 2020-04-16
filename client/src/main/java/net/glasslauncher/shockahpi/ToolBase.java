// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 
// Source File Name:   ToolBase.java

package net.glasslauncher.shockahpi;

import net.minecraft.src.Block;
import net.minecraft.src.Material;

import java.util.*;

// Referenced classes of package net.minecraft.src:
//            SAPI, BlockHarvestPower, Block, Material

public class ToolBase
{

    public ToolBase()
    {
        mineBlocks = new ArrayList();
        mineMaterials = new ArrayList();
    }

    public boolean canHarvest(Block block, float currentPower)
    {
        for(Iterator iterator = mineMaterials.iterator(); iterator.hasNext();)
        {
            Material material = (Material)iterator.next();
            if(material == block.blockMaterial)
            {
                return true;
            }
        }

        for(Iterator iterator1 = mineBlocks.iterator(); iterator1.hasNext();)
        {
            BlockHarvestPower power = (BlockHarvestPower)iterator1.next();
            if(block.blockID == power.blockID || currentPower >= power.percentage)
            {
                return true;
            }
        }

        return false;
    }

    public static final ToolBase Pickaxe;
    public static final ToolBase Shovel;
    public static final ToolBase Axe;
    public ArrayList mineBlocks;
    public ArrayList mineMaterials;

    static 
    {
        // TODO SAPI.showText();
        Pickaxe = new ToolBase();
        Shovel = new ToolBase();
        Axe = new ToolBase();
        List list = Arrays.asList(new Integer[] {
            Integer.valueOf(1), Integer.valueOf(4), Integer.valueOf(16), Integer.valueOf(21), Integer.valueOf(22), Integer.valueOf(23), Integer.valueOf(24), Integer.valueOf(43), Integer.valueOf(44), Integer.valueOf(45), 
            Integer.valueOf(48), Integer.valueOf(52), Integer.valueOf(61), Integer.valueOf(62), Integer.valueOf(67), Integer.valueOf(77), Integer.valueOf(79), Integer.valueOf(87), Integer.valueOf(89), Integer.valueOf(93), 
            Integer.valueOf(94)
        });
        Integer blockID;
        for(Iterator iterator = list.iterator(); iterator.hasNext(); Pickaxe.mineBlocks.add(new BlockHarvestPower(blockID.intValue(), 20F)))
        {
            blockID = (Integer)iterator.next();
        }

        list = Arrays.asList(new Integer[] {
            Integer.valueOf(15), Integer.valueOf(42), Integer.valueOf(71)
        });

        for(Iterator iterator1 = list.iterator(); iterator1.hasNext(); Pickaxe.mineBlocks.add(new BlockHarvestPower(blockID.intValue(), 40F)))
        {
            blockID = (Integer)iterator1.next();
        }

        list = Arrays.asList(new Integer[] {
            Integer.valueOf(14), Integer.valueOf(41), Integer.valueOf(56), Integer.valueOf(57), Integer.valueOf(73), Integer.valueOf(74)
        });

        for(Iterator iterator2 = list.iterator(); iterator2.hasNext(); Pickaxe.mineBlocks.add(new BlockHarvestPower(blockID.intValue(), 60F)))
        {
            blockID = (Integer)iterator2.next();
        }

        list = Arrays.asList(new Integer[] {
            Integer.valueOf(49)
        });

        for(Iterator iterator3 = list.iterator(); iterator3.hasNext(); Pickaxe.mineBlocks.add(new BlockHarvestPower(blockID.intValue(), 80F)))
        {
            blockID = (Integer)iterator3.next();
        }

        list = Arrays.asList(new Integer[] {
            Integer.valueOf(2), Integer.valueOf(3), Integer.valueOf(12), Integer.valueOf(13), Integer.valueOf(78), Integer.valueOf(80), Integer.valueOf(82)
        });

        for(Iterator iterator4 = list.iterator(); iterator4.hasNext(); Shovel.mineBlocks.add(new BlockHarvestPower(blockID.intValue(), 20F)))
        {
            blockID = (Integer)iterator4.next();
        }

        list = Arrays.asList(new Integer[] {
            Integer.valueOf(5), Integer.valueOf(17), Integer.valueOf(18), Integer.valueOf(25), Integer.valueOf(47), Integer.valueOf(53), Integer.valueOf(54), Integer.valueOf(58), Integer.valueOf(63), Integer.valueOf(64), 
            Integer.valueOf(65), Integer.valueOf(66), Integer.valueOf(68), Integer.valueOf(69), Integer.valueOf(81), Integer.valueOf(84), Integer.valueOf(85)
        });

        for(Iterator iterator5 = list.iterator(); iterator5.hasNext(); Axe.mineBlocks.add(new BlockHarvestPower(blockID.intValue(), 20F)))
        {
            blockID = (Integer)iterator5.next();
        }

    }
}
