// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 
// Source File Name:   Tool.java

package net.glasslauncher.shockahpi;

import net.minecraft.src.*;

import java.util.ArrayList;
import java.util.Iterator;

// Referenced classes of package net.minecraft.src:
//            Item, SAPI, ItemStack, ToolBase, 
//            Block, Material, BlockHarvestPower, EntityLiving, 
//            Entity

public class Tool extends Item
{

    public Tool(boolean usingSAPI, ToolBase toolBase, int itemID, int uses, float baseDamage, float basePower, float toolSpeed)
    {
        this(usingSAPI, toolBase, itemID, uses, baseDamage, basePower, toolSpeed, 1.0F);
    }

    public Tool(boolean usingSAPI, ToolBase toolBase, int itemID, int uses, float baseDamage, float basePower, float toolSpeed, 
            float defaultSpeed)
    {
        super(itemID);
        mineBlocks = new ArrayList();
        mineMaterials = new ArrayList();
        setMaxDamage(uses);
        maxStackSize = 1;
        this.usingSAPI = usingSAPI;
        this.toolBase = toolBase;
        this.baseDamage = baseDamage;
        this.basePower = basePower;
        this.toolSpeed = toolSpeed;
        this.defaultSpeed = defaultSpeed;
    }

    public boolean isFull3D()
    {
        return true;
    }

    public boolean hitEntity(ItemStack stack, EntityLiving living, EntityLiving living2)
    {
        stack.damageItem(2, living2);
        return true;
    }

    public boolean onBlockDestroyed(ItemStack stack, int blockID, int x, int y, int z, EntityLiving living)
    {
        stack.damageItem(1, living);
        return true;
    }

    public int getDamageVsEntity(Entity entity)
    {
        return (int)Math.floor(baseDamage);
    }

    public float getPower()
    {
        return basePower;
    }

    public float getStrVsBlock(ItemStack stack, Block block)
    {
        return canHarvest(block) ? toolSpeed : defaultSpeed;
    }

    public boolean canHarvest(Block block)
    {
        if(toolBase != null && toolBase.canHarvest(block, getPower()))
        {
            return true;
        }
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
            if(block.blockID == power.blockID || getPower() >= power.percentage)
            {
                return true;
            }
        }

        return false;
    }

    public final boolean usingSAPI;
    public ToolBase toolBase;
    public final float baseDamage;
    public final float basePower;
    public final float defaultSpeed;
    public final float toolSpeed;
    public ArrayList mineBlocks;
    public ArrayList mineMaterials;

    static 
    {

        //TODO SAPI.showText();
    }
}
