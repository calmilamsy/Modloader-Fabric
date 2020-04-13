package net.glasslauncher.playerapi;

import net.minecraft.src.*;

import java.util.ArrayList;
import java.util.List;

// Referenced classes of package net.minecraft.src:
//            EntityPlayerSP, PlayerBase, EnumStatus, NBTTagCompound,
//            Entity, Block, Material, ItemStack,
//            TileEntitySign, IInventory, TileEntityFurnace, TileEntityDispenser

public class PlayerAPI
{

    public PlayerAPI()
    {
    }

    public static void RegisterPlayerBase(Class pb)
    {
        playerBaseClasses.add(pb);
    }

    public static PlayerBase getPlayerBase(EntityPlayerSP player, Class pb)
    {
        for(int i = 0; i < ((EntityPlayerSPAccessor) player).getPlayerBases().size(); i++)
        {
            if(pb.isInstance(((EntityPlayerSPAccessor) player).getPlayerBases().get(i)))
            {
                return ((EntityPlayerSPAccessor) player).getPlayerBases().get(i);
            }
        }

        return null;
    }

    public static List playerInit(EntityPlayerSP player)
    {
        List playerBases = new ArrayList();
        for(int i = 0; i < playerBaseClasses.size(); i++)
        {
            try
            {
                playerBases.add(((Class)playerBaseClasses.get(i)).getDeclaredConstructor(new Class[] {
                        EntityPlayerSP.class
                }).newInstance(player));
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }

        return playerBases;
    }

    public static boolean onLivingUpdate(EntityPlayerSP player)
    {
        boolean override = false;
        for(int i = 0; i < ((EntityPlayerSPAccessor) player).getPlayerBases().size(); i++)
        {
            if(((EntityPlayerSPAccessor) player).getPlayerBases().get(i).onLivingUpdate())
            {
                override = true;
            }
        }

        return override;
    }

    public static boolean respawn(EntityPlayerSP player)
    {
        boolean override = false;
        for(int i = 0; i < ((EntityPlayerSPAccessor) player).getPlayerBases().size(); i++)
        {
            if(((EntityPlayerSPAccessor) player).getPlayerBases().get(i).respawn())
            {
                override = true;
            }
        }

        return override;
    }

    public static boolean moveFlying(EntityPlayerSP player, float x, float y, float z)
    {
        boolean override = false;
        for(int i = 0; i < ((EntityPlayerSPAccessor) player).getPlayerBases().size(); i++)
        {
            if(((EntityPlayerSPAccessor) player).getPlayerBases().get(i).moveFlying(x, y, z))
            {
                override = true;
            }
        }

        return override;
    }

    public static boolean updatePlayerActionState(EntityPlayerSP player)
    {
        boolean override = false;
        for(int i = 0; i < ((EntityPlayerSPAccessor) player).getPlayerBases().size(); i++)
        {
            if(((EntityPlayerSPAccessor) player).getPlayerBases().get(i).updatePlayerActionState())
            {
                override = true;
            }
        }

        return override;
    }

    public static boolean handleKeyPress(EntityPlayerSP player, int j, boolean flag)
    {
        boolean override = false;
        for(int i = 0; i < ((EntityPlayerSPAccessor) player).getPlayerBases().size(); i++)
        {
            if(((EntityPlayerSPAccessor) player).getPlayerBases().get(i).handleKeyPress(j, flag))
            {
                override = true;
            }
        }

        return override;
    }

    public static boolean writeEntityToNBT(EntityPlayerSP player, NBTTagCompound tag)
    {
        boolean override = false;
        for(int i = 0; i < ((EntityPlayerSPAccessor) player).getPlayerBases().size(); i++)
        {
            if(((EntityPlayerSPAccessor) player).getPlayerBases().get(i).writeEntityToNBT(tag))
            {
                override = true;
            }
        }

        return override;
    }

    public static boolean readEntityFromNBT(EntityPlayerSP player, NBTTagCompound tag)
    {
        boolean override = false;
        for(int i = 0; i < ((EntityPlayerSPAccessor) player).getPlayerBases().size(); i++)
        {
            if(((EntityPlayerSPAccessor) player).getPlayerBases().get(i).readEntityFromNBT(tag))
            {
                override = true;
            }
        }

        return override;
    }

    public static boolean onExitGUI(EntityPlayerSP player)
    {
        boolean override = false;
        for(int i = 0; i < ((EntityPlayerSPAccessor) player).getPlayerBases().size(); i++)
        {
            if(((EntityPlayerSPAccessor) player).getPlayerBases().get(i).onExitGUI())
            {
                override = true;
            }
        }

        return override;
    }

    public static boolean setEntityDead(EntityPlayerSP player)
    {
        boolean override = false;
        for(int i = 0; i < ((EntityPlayerSPAccessor) player).getPlayerBases().size(); i++)
        {
            if(((EntityPlayerSPAccessor) player).getPlayerBases().get(i).setEntityDead())
            {
                override = true;
            }
        }

        return override;
    }

    public static boolean onDeath(EntityPlayerSP player, Entity killer)
    {
        boolean override = false;
        for(int i = 0; i < ((EntityPlayerSPAccessor) player).getPlayerBases().size(); i++)
        {
            if(((EntityPlayerSPAccessor) player).getPlayerBases().get(i).onDeath(killer))
            {
                override = true;
            }
        }

        return override;
    }

    public static boolean attackEntityFrom(EntityPlayerSP player, Entity attacker, int damage)
    {
        boolean override = false;
        for(int i = 0; i < ((EntityPlayerSPAccessor) player).getPlayerBases().size(); i++)
        {
            if(((EntityPlayerSPAccessor) player).getPlayerBases().get(i).attackEntityFrom(attacker, damage))
            {
                override = true;
            }
        }

        return override;
    }

    public static double getDistanceSq(EntityPlayerSP player, double d, double d1, double d2, double answer)
    {
        for(int i = 0; i < ((EntityPlayerSPAccessor) player).getPlayerBases().size(); i++)
        {
            answer = ((EntityPlayerSPAccessor) player).getPlayerBases().get(i).getDistanceSq(d, d1, d2, answer);
        }

        return answer;
    }

    public static boolean isInWater(EntityPlayerSP player, boolean inWater)
    {
        for(int i = 0; i < ((EntityPlayerSPAccessor) player).getPlayerBases().size(); i++)
        {
            inWater = ((EntityPlayerSPAccessor) player).getPlayerBases().get(i).isInWater(inWater);
        }

        return inWater;
    }

    public static boolean canTriggerWalking(EntityPlayerSP player, boolean canTrigger)
    {
        for(int i = 0; i < ((EntityPlayerSPAccessor) player).getPlayerBases().size(); i++)
        {
            canTrigger = ((EntityPlayerSPAccessor) player).getPlayerBases().get(i).canTriggerWalking(canTrigger);
        }

        return canTrigger;
    }

    public static boolean heal(EntityPlayerSP player, int j)
    {
        boolean override = false;
        for(int i = 0; i < ((EntityPlayerSPAccessor) player).getPlayerBases().size(); i++)
        {
            if(((EntityPlayerSPAccessor) player).getPlayerBases().get(i).heal(j))
            {
                override = true;
            }
        }

        return override;
    }

    public static int getPlayerArmorValue(EntityPlayerSP player, int armor)
    {
        for(int i = 0; i < ((EntityPlayerSPAccessor) player).getPlayerBases().size(); i++)
        {
            armor = ((EntityPlayerSPAccessor) player).getPlayerBases().get(i).getPlayerArmorValue(armor);
        }

        return armor;
    }

    public static float getCurrentPlayerStrVsBlock(EntityPlayerSP player, Block block, float f)
    {
        for(int i = 0; i < ((EntityPlayerSPAccessor) player).getPlayerBases().size(); i++)
        {
            f = ((EntityPlayerSPAccessor) player).getPlayerBases().get(i).getCurrentPlayerStrVsBlock(block, f);
        }

        return f;
    }

    public static boolean moveEntity(EntityPlayerSP player, double d, double d1, double d2)
    {
        boolean override = false;
        for(int i = 0; i < ((EntityPlayerSPAccessor) player).getPlayerBases().size(); i++)
        {
            if(((EntityPlayerSPAccessor) player).getPlayerBases().get(i).moveEntity(d, d1, d2))
            {
                override = true;
            }
        }

        return override;
    }

    public static EnumStatus sleepInBedAt(EntityPlayerSP player, int x, int y, int z)
    {
        EnumStatus status = null;
        for(int i = 0; i < ((EntityPlayerSPAccessor) player).getPlayerBases().size(); i++)
        {
            status = ((EntityPlayerSPAccessor) player).getPlayerBases().get(i).sleepInBedAt(x, y, z, status);
        }

        return status;
    }

    public static float getEntityBrightness(EntityPlayerSP player, float f, float brightness)
    {
        for(int i = 0; i < ((EntityPlayerSPAccessor) player).getPlayerBases().size(); i++)
        {
            f = ((EntityPlayerSPAccessor) player).getPlayerBases().get(i).getEntityBrightness(f, brightness);
        }

        return f;
    }

    public static boolean pushOutOfBlocks(EntityPlayerSP player, double d, double d1, double d2)
    {
        boolean override = false;
        for(int i = 0; i < ((EntityPlayerSPAccessor) player).getPlayerBases().size(); i++)
        {
            if(((EntityPlayerSPAccessor) player).getPlayerBases().get(i).pushOutOfBlocks(d, d1, d2))
            {
                override = true;
            }
        }

        return override;
    }

    public static boolean onUpdate(EntityPlayerSP player)
    {
        boolean override = false;
        for(int i = 0; i < ((EntityPlayerSPAccessor) player).getPlayerBases().size(); i++)
        {
            if(((EntityPlayerSPAccessor) player).getPlayerBases().get(i).onUpdate())
            {
                override = true;
            }
        }

        return override;
    }

    public static void afterUpdate(EntityPlayerSP player)
    {
        for(int i = 0; i < ((EntityPlayerSPAccessor) player).getPlayerBases().size(); i++)
        {
            ((EntityPlayerSPAccessor) player).getPlayerBases().get(i).afterUpdate();
        }

    }

    public static boolean moveEntityWithHeading(EntityPlayerSP player, float f, float f1)
    {
        boolean override = false;
        for(int i = 0; i < ((EntityPlayerSPAccessor) player).getPlayerBases().size(); i++)
        {
            if(((EntityPlayerSPAccessor) player).getPlayerBases().get(i).moveEntityWithHeading(f, f1))
            {
                override = true;
            }
        }

        return override;
    }

    public static boolean isOnLadder(EntityPlayerSP player, boolean onLadder)
    {
        for(int i = 0; i < ((EntityPlayerSPAccessor) player).getPlayerBases().size(); i++)
        {
            onLadder = ((EntityPlayerSPAccessor) player).getPlayerBases().get(i).isOnLadder(onLadder);
        }

        return onLadder;
    }

    public static boolean isInsideOfMaterial(EntityPlayerSP player, Material material, boolean inMaterial)
    {
        for(int i = 0; i < ((EntityPlayerSPAccessor) player).getPlayerBases().size(); i++)
        {
            inMaterial = ((EntityPlayerSPAccessor) player).getPlayerBases().get(i).isInsideOfMaterial(material, inMaterial);
        }

        return inMaterial;
    }

    public static boolean isSneaking(EntityPlayerSP player, boolean sneaking)
    {
        for(int i = 0; i < ((EntityPlayerSPAccessor) player).getPlayerBases().size(); i++)
        {
            sneaking = ((EntityPlayerSPAccessor) player).getPlayerBases().get(i).isSneaking(sneaking);
        }

        return sneaking;
    }

    public static boolean dropCurrentItem(EntityPlayerSP player)
    {
        boolean override = false;
        for(int i = 0; i < ((EntityPlayerSPAccessor) player).getPlayerBases().size(); i++)
        {
            if(((EntityPlayerSPAccessor) player).getPlayerBases().get(i).dropCurrentItem())
            {
                override = true;
            }
        }

        return override;
    }

    public static boolean dropPlayerItem(EntityPlayerSP player, ItemStack itemstack)
    {
        boolean override = false;
        for(int i = 0; i < ((EntityPlayerSPAccessor) player).getPlayerBases().size(); i++)
        {
            if(((EntityPlayerSPAccessor) player).getPlayerBases().get(i).dropPlayerItem(itemstack))
            {
                override = true;
            }
        }

        return override;
    }

    public static boolean displayGUIEditSign(EntityPlayerSP player, TileEntitySign sign)
    {
        boolean override = false;
        for(int i = 0; i < ((EntityPlayerSPAccessor) player).getPlayerBases().size(); i++)
        {
            if(((EntityPlayerSPAccessor) player).getPlayerBases().get(i).displayGUIEditSign(sign))
            {
                override = true;
            }
        }

        return override;
    }

    public static boolean displayGUIChest(EntityPlayerSP player, IInventory inventory)
    {
        boolean override = false;
        for(int i = 0; i < ((EntityPlayerSPAccessor) player).getPlayerBases().size(); i++)
        {
            if(((EntityPlayerSPAccessor) player).getPlayerBases().get(i).displayGUIChest(inventory))
            {
                override = true;
            }
        }

        return override;
    }

    public static boolean displayWorkbenchGUI(EntityPlayerSP player, int i, int j, int k)
    {
        boolean override = false;
        for(int n = 0; n < ((EntityPlayerSPAccessor) player).getPlayerBases().size(); n++)
        {
            if(((EntityPlayerSPAccessor) player).getPlayerBases().get(n).displayWorkbenchGUI(i, j, k))
            {
                override = true;
            }
        }

        return override;
    }

    public static boolean displayGUIFurnace(EntityPlayerSP player, TileEntityFurnace furnace)
    {
        boolean override = false;
        for(int i = 0; i < ((EntityPlayerSPAccessor) player).getPlayerBases().size(); i++)
        {
            if(((EntityPlayerSPAccessor) player).getPlayerBases().get(i).displayGUIFurnace(furnace))
            {
                override = true;
            }
        }

        return override;
    }

    public static boolean displayGUIDispenser(EntityPlayerSP player, TileEntityDispenser dispenser)
    {
        boolean override = false;
        for(int i = 0; i < ((EntityPlayerSPAccessor) player).getPlayerBases().size(); i++)
        {
            if(((EntityPlayerSPAccessor) player).getPlayerBases().get(i).displayGUIDispenser(dispenser))
            {
                override = true;
            }
        }

        return override;
    }

    public static boolean sendChatMessage(EntityPlayerSP entityplayersp, String s)
    {
        boolean flag = false;
        for(int i = 0; i < ((EntityPlayerSPAccessor) entityplayersp).getPlayerBases().size(); i++)
        {
            if(((EntityPlayerSPAccessor) entityplayersp).getPlayerBases().get(i).sendChatMessage(s))
            {
                flag = true;
            }
        }

        return flag;
    }

    public static String getHurtSound(EntityPlayerSP entityplayersp)
    {
        String result = null;
        for(int i = 0; i < ((EntityPlayerSPAccessor) entityplayersp).getPlayerBases().size(); i++)
        {
            String baseResult = ((EntityPlayerSPAccessor) entityplayersp).getPlayerBases().get(i).getHurtSound(result);
            if(baseResult != null)
            {
                result = baseResult;
            }
        }

        return result;
    }

    public static Boolean canHarvestBlock(EntityPlayerSP entityplayersp, Block block)
    {
        Boolean result = null;
        for(int i = 0; i < ((EntityPlayerSPAccessor) entityplayersp).getPlayerBases().size(); i++)
        {
            Boolean baseResult = ((EntityPlayerSPAccessor) entityplayersp).getPlayerBases().get(i).canHarvestBlock(block, result);
            if(baseResult != null)
            {
                result = baseResult;
            }
        }

        return result;
    }

    public static boolean fall(EntityPlayerSP entityplayersp, float f)
    {
        boolean flag = false;
        for(int i = 0; i < ((EntityPlayerSPAccessor) entityplayersp).getPlayerBases().size(); i++)
        {
            if(((EntityPlayerSPAccessor) entityplayersp).getPlayerBases().get(i).fall(f))
            {
                flag = true;
            }
        }

        return flag;
    }

    public static boolean jump(EntityPlayerSP entityplayersp)
    {
        boolean flag = false;
        for(int i = 0; i < ((EntityPlayerSPAccessor) entityplayersp).getPlayerBases().size(); i++)
        {
            if(((EntityPlayerSPAccessor) entityplayersp).getPlayerBases().get(i).jump())
            {
                flag = true;
            }
        }

        return flag;
    }

    public static boolean damageEntity(EntityPlayerSP entityplayersp, int i1)
    {
        boolean flag = false;
        for(int i = 0; i < ((EntityPlayerSPAccessor) entityplayersp).getPlayerBases().size(); i++)
        {
            if(((EntityPlayerSPAccessor) entityplayersp).getPlayerBases().get(i).damageEntity(i1))
            {
                flag = true;
            }
        }

        return flag;
    }

    public static Double getDistanceSqToEntity(EntityPlayerSP entityplayersp, Entity entity)
    {
        Double result = null;
        for(int i = 0; i < ((EntityPlayerSPAccessor) entityplayersp).getPlayerBases().size(); i++)
        {
            Double baseResult = ((EntityPlayerSPAccessor) entityplayersp).getPlayerBases().get(i).getDistanceSqToEntity(entity, result);
            if(baseResult != null)
            {
                result = baseResult;
            }
        }

        return result;
    }

    public static boolean attackTargetEntityWithCurrentItem(EntityPlayerSP entityplayersp, Entity entity)
    {
        boolean flag = false;
        for(int i = 0; i < ((EntityPlayerSPAccessor) entityplayersp).getPlayerBases().size(); i++)
        {
            if(((EntityPlayerSPAccessor) entityplayersp).getPlayerBases().get(i).attackTargetEntityWithCurrentItem(entity))
            {
                flag = true;
            }
        }

        return flag;
    }

    public static Boolean handleWaterMovement(EntityPlayerSP entityplayersp)
    {
        Boolean result = null;
        for(int i = 0; i < ((EntityPlayerSPAccessor) entityplayersp).getPlayerBases().size(); i++)
        {
            Boolean baseResult = ((EntityPlayerSPAccessor) entityplayersp).getPlayerBases().get(i).handleWaterMovement(result);
            if(baseResult != null)
            {
                result = baseResult;
            }
        }

        return result;
    }

    public static Boolean handleLavaMovement(EntityPlayerSP entityplayersp)
    {
        Boolean result = null;
        for(int i = 0; i < ((EntityPlayerSPAccessor) entityplayersp).getPlayerBases().size(); i++)
        {
            Boolean baseResult = ((EntityPlayerSPAccessor) entityplayersp).getPlayerBases().get(i).handleLavaMovement(result);
            if(baseResult != null)
            {
                result = baseResult;
            }
        }

        return result;
    }

    public static boolean dropPlayerItemWithRandomChoice(EntityPlayerSP entityplayersp, ItemStack itemstack, boolean flag1)
    {
        boolean flag = false;
        for(int i = 0; i < ((EntityPlayerSPAccessor) entityplayersp).getPlayerBases().size(); i++)
        {
            if(((EntityPlayerSPAccessor) entityplayersp).getPlayerBases().get(i).dropPlayerItemWithRandomChoice(itemstack, flag1))
            {
                flag = true;
            }
        }

        return flag;
    }

    public static void beforeUpdate(EntityPlayerSP entityplayersp)
    {
        for(int i = 0; i < ((EntityPlayerSPAccessor) entityplayersp).getPlayerBases().size(); i++)
        {
            ((EntityPlayerSPAccessor) entityplayersp).getPlayerBases().get(i).beforeUpdate();
        }

    }

    public static void beforeMoveEntity(EntityPlayerSP entityplayersp, double d, double d1, double d2)
    {
        for(int i = 0; i < ((EntityPlayerSPAccessor) entityplayersp).getPlayerBases().size(); i++)
        {
            ((EntityPlayerSPAccessor) entityplayersp).getPlayerBases().get(i).beforeMoveEntity(d, d1, d2);
        }

    }

    public static void afterMoveEntity(EntityPlayerSP entityplayersp, double d, double d1, double d2)
    {
        for(int i = 0; i < ((EntityPlayerSPAccessor) entityplayersp).getPlayerBases().size(); i++)
        {
            ((EntityPlayerSPAccessor) entityplayersp).getPlayerBases().get(i).afterMoveEntity(d, d1, d2);
        }

    }

    public static void beforeSleepInBedAt(EntityPlayerSP entityplayersp, int i1, int j, int k)
    {
        for(int i = 0; i < ((EntityPlayerSPAccessor) entityplayersp).getPlayerBases().size(); i++)
        {
            ((EntityPlayerSPAccessor) entityplayersp).getPlayerBases().get(i).beforeSleepInBedAt(i1, j, k);
        }

    }

    public static List playerBaseClasses = new ArrayList();

}
