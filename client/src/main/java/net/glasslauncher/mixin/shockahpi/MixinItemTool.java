// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 
// Source File Name:   dq.java

/*package net.glasslauncher.mixin.shockahpi;

import net.glasslauncher.shockahpi.BlockHarvestPower;
import net.glasslauncher.shockahpi.Tool;
import net.glasslauncher.shockahpi.ToolBase;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Iterator;

// Referenced classes of package net.minecraft.src:
//            Tool, EnumToolMaterial, BlockHarvestPower, Block, 
//            ItemPickaxe, ToolBase, ItemAxe, ItemSpade, 
//            Material

@Mixin(ItemTool.class)
public class MixinItemTool
{

    @Inject(method = "<init>", @At(value = "INVOKE"))
    private void init(int itemID, int damage, EnumToolMaterial material, Block blocks[], CallbackInfo ci)
    {
        super(false, null, itemID, material.getMaxUses(), damage + material.getDamageVsEntity(), getToolPower(material), material.getEfficiencyOnProperMaterial());
        toolMaterial = material;
        toolBase = getToolBase();
        Block ablock[];
        int j = (ablock = blocks).length;
        for(int i = 0; i < j; i++)
        {
            Block block = ablock[i];
            mineBlocks.add(new BlockHarvestPower(block.blockID, 0.0F));
        }

    }

    public MixinItemTool(boolean usingSAPI, ToolBase toolBase, int itemID, int uses, float baseDamage, float basePower, float toolSpeed) {
        super(usingSAPI, toolBase, itemID, uses, baseDamage, basePower, toolSpeed);
    }

    public ToolBase getToolBase()
    {
        if(this instanceof ItemPickaxe)
        {
            return ToolBase.Pickaxe;
        }
        if(this instanceof ItemAxe)
        {
            return ToolBase.Axe;
        }
        if(this instanceof ItemSpade)
        {
            return ToolBase.Shovel;
        } else
        {
            return null;
        }
    }

    public static float getToolPower(EnumToolMaterial material)
    {
        if(material == EnumToolMaterial.EMERALD)
        {
            return 80F;
        }
        if(material == EnumToolMaterial.IRON)
        {
            return 60F;
        }
        return material != EnumToolMaterial.STONE ? 20F : 40F;
    }

    public boolean canHarvest(Block block)
    {
        if(!usingSAPI && !isBlockOnList(block.blockID))
        {
            if(this instanceof ItemPickaxe)
            {
                if(shiftedIndex <= 369)
                {
                    return false;
                }
                if(block.blockMaterial == Material.rock || block.blockMaterial == Material.ice)
                {
                    return true;
                }
                if(block.blockMaterial == Material.iron && basePower >= 40F)
                {
                    return true;
                }
            } else
            if(this instanceof ItemAxe)
            {
                if(shiftedIndex <= 369)
                {
                    return false;
                }
                if(block.blockMaterial == Material.wood || block.blockMaterial == Material.leaves || block.blockMaterial == Material.plants || block.blockMaterial == Material.cactus || block.blockMaterial == Material.pumpkin)
                {
                    return true;
                }
            } else
            if(this instanceof ItemSpade)
            {
                if(shiftedIndex <= 369)
                {
                    return false;
                }
                if(block.blockMaterial == Material.grassMaterial || block.blockMaterial == Material.ground || block.blockMaterial == Material.sand || block.blockMaterial == Material.snow || block.blockMaterial == Material.builtSnow || block.blockMaterial == Material.clay)
                {
                    return true;
                }
            }
        }
        return super.canHarvest(block);
    }

    private boolean isBlockOnList(int blockID)
    {
        for(Iterator iterator = mineBlocks.iterator(); iterator.hasNext();)
        {
            BlockHarvestPower power = (BlockHarvestPower)iterator.next();
            if(power.blockID == blockID)
            {
                return true;
            }
        }

        for(Iterator iterator1 = toolBase.mineBlocks.iterator(); iterator1.hasNext();)
        {
            BlockHarvestPower power = (BlockHarvestPower)iterator1.next();
            if(power.blockID == blockID)
            {
                return true;
            }
        }

        return false;
    }

    public EnumToolMaterial toolMaterial;
}
*/