//// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
//// Jad home page: http://www.kpdus.com/jad.html
//// Decompiler options: packimports(3) braces deadcode
//// Source File Name:   SAPI.java
//
//package net.glasslauncher.shockahpi;
//
//import java.lang.reflect.Field;
//import java.util.*;
//
//import net.glasslauncher.shockahpi.base.World;
//import net.minecraft.client.Minecraft;
//import net.minecraft.src.*;
//
//// Referenced classes of package net.minecraft.src:
////            IInterceptHarvest, World, ItemStack, EntityItem,
////            Loc, IInterceptBlockSet, EntityPlayerSP, InventoryPlayer,
////            IReach, DungeonLoot, Item, Achievement,
////            ACPage, PlayerBaseSAPI, PlayerAPI, EntityPlayer
//
//public class SAPI
//{
//
//    public SAPI()
//    {
//    }
//
//    public static void showText()
//    {
//        if(!usingText)
//        {
//            System.out.println("Using ShockAhPI r5.1");
//            usingText = true;
//        }
//    }
//
//    public static Minecraft getMinecraftInstance()
//    {
//        if(instance == null)
//        {
//            try
//            {
//                ThreadGroup threadgroup = Thread.currentThread().getThreadGroup();
//                int i = threadgroup.activeCount();
//                Thread athread[] = new Thread[i];
//                threadgroup.enumerate(athread);
//                int j = 0;
//                do
//                {
//                    if(j >= athread.length)
//                    {
//                        break;
//                    }
//                    if(athread[j].getName().equals("Minecraft main thread"))
//                    {
//                        Field field = (Thread.class).getDeclaredField("target");
//                        field.setAccessible(true);
//                        instance = (Minecraft)field.get(athread[j]);
//                        break;
//                    }
//                    j++;
//                } while(true);
//            }
//            catch(Exception exception)
//            {
//                exception.printStackTrace();
//            }
//        }
//        return instance;
//    }
//
//    public static void interceptAdd(IInterceptHarvest iinterceptharvest)
//    {
//        harvestIntercepts.add(iinterceptharvest);
//    }
//
//    public static boolean interceptHarvest(net.glasslauncher.shockahpi.base.World world, EntityPlayer entityplayer, Loc loc, int i, int j)
//    {
//        for(Iterator iterator = harvestIntercepts.iterator(); iterator.hasNext();)
//        {
//            IInterceptHarvest iinterceptharvest = (IInterceptHarvest)iterator.next();
//            if(iinterceptharvest.canIntercept(world, entityplayer, loc, i, j))
//            {
//                iinterceptharvest.intercept(world, entityplayer, loc, i, j);
//                return true;
//            }
//        }
//
//        return false;
//    }
//
//    public static void drop(net.glasslauncher.shockahpi.base.World world, Loc loc, ItemStack itemstack)
//    {
//        if(world.multiplayerWorld)
//        {
//            return;
//        }
//        for(int i = 0; i < itemstack.stackSize; i++)
//        {
//            float f = 0.7F;
//            double d = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
//            double d1 = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
//            double d2 = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
//            EntityItem entityitem = new EntityItem(world, (double)loc.x() + d, (double)loc.y() + d1, (double)loc.z() + d2, new ItemStack(itemstack.itemID, 1, itemstack.getItemDamage()));
//            entityitem.delayBeforeCanPickup = 10;
//            world.entityJoinedWorld(entityitem);
//        }
//
//    }
//
//    public static void interceptAdd(IInterceptBlockSet iinterceptblockset)
//    {
//        setIntercepts.add(iinterceptblockset);
//    }
//
//    public static int interceptBlockSet(World world, Loc loc, int i)
//    {
//        for(Iterator iterator = setIntercepts.iterator(); iterator.hasNext();)
//        {
//            IInterceptBlockSet iinterceptblockset = (IInterceptBlockSet)iterator.next();
//            if(iinterceptblockset.canIntercept(world, loc, i))
//            {
//                return iinterceptblockset.intercept(world, loc, i);
//            }
//        }
//
//        return i;
//    }
//
//    public static void reachAdd(IReach ireach)
//    {
//        reaches.add(ireach);
//    }
//
//    public static float reachGet()
//    {
//        ItemStack itemstack = getMinecraftInstance().thePlayer.inventory.getCurrentItem();
//        for(Iterator iterator = reaches.iterator(); iterator.hasNext();)
//        {
//            IReach ireach = (IReach)iterator.next();
//            if(ireach.reachItemMatches(itemstack))
//            {
//                return ireach.getReach(itemstack);
//            }
//        }
//
//        return 4F;
//    }
//
//    public static void dungeonAddMob(String s)
//    {
//        dungeonAddMob(s, 10);
//    }
//
//    public static void dungeonAddMob(String s, int i)
//    {
//        for(int j = 0; j < i; j++)
//        {
//            dngMobs.add(s);
//        }
//
//    }
//
//    public static void dungeonRemoveMob(String s)
//    {
//        for(int i = 0; i < dngMobs.size(); i++)
//        {
//            if(((String)dngMobs.get(i)).equals(s))
//            {
//                dngMobs.remove(i);
//                i--;
//            }
//        }
//
//    }
//
//    public static void dungeonRemoveAllMobs()
//    {
//        dngAddedMobs = true;
//        dngMobs.clear();
//    }
//
//    static void dungeonAddDefaultMobs()
//    {
//        for(int i = 0; i < 10; i++)
//        {
//            dngMobs.add("Skeleton");
//        }
//
//        for(int j = 0; j < 20; j++)
//        {
//            dngMobs.add("Zombie");
//        }
//
//        for(int k = 0; k < 10; k++)
//        {
//            dngMobs.add("Spider");
//        }
//
//    }
//
//    public static String dungeonGetRandomMob()
//    {
//        if(!dngAddedMobs)
//        {
//            dungeonAddDefaultMobs();
//            dngAddedMobs = true;
//        }
//        if(dngMobs.isEmpty())
//        {
//            return "Pig";
//        } else
//        {
//            return (String)dngMobs.get((new Random()).nextInt(dngMobs.size()));
//        }
//    }
//
//    public static void dungeonAddItem(DungeonLoot dungeonloot)
//    {
//        dungeonAddItem(dungeonloot, 100);
//    }
//
//    public static void dungeonAddItem(DungeonLoot dungeonloot, int i)
//    {
//        for(int j = 0; j < i; j++)
//        {
//            dngItems.add(dungeonloot);
//        }
//
//    }
//
//    public static void dungeonAddGuaranteedItem(DungeonLoot dungeonloot)
//    {
//        dngGuaranteed.add(dungeonloot);
//    }
//
//    public static int dungeonGetAmountOfGuaranteed()
//    {
//        return dngGuaranteed.size();
//    }
//
//    public static DungeonLoot dungeonGetGuaranteed(int i)
//    {
//        return (DungeonLoot)dngGuaranteed.get(i);
//    }
//
//    public static void dungeonRemoveItem(int i)
//    {
//        for(int j = 0; j < dngItems.size(); j++)
//        {
//            if(((DungeonLoot)dngItems.get(j)).loot.itemID == i)
//            {
//                dngItems.remove(j);
//                j--;
//            }
//        }
//
//        for(int k = 0; k < dngGuaranteed.size(); k++)
//        {
//            if(((DungeonLoot)dngGuaranteed.get(k)).loot.itemID == i)
//            {
//                dngGuaranteed.remove(k);
//                k--;
//            }
//        }
//
//    }
//
//    public static void dungeonRemoveAllItems()
//    {
//        dngAddedItems = true;
//        dngItems.clear();
//        dngGuaranteed.clear();
//    }
//
//    static void dungeonAddDefaultItems()
//    {
//        for(int i = 0; i < 100; i++)
//        {
//            dngItems.add(new DungeonLoot(new ItemStack(Item.saddle)));
//        }
//
//        for(int j = 0; j < 100; j++)
//        {
//            dngItems.add(new DungeonLoot(new ItemStack(Item.ingotIron), 1, 4));
//        }
//
//        for(int k = 0; k < 100; k++)
//        {
//            dngItems.add(new DungeonLoot(new ItemStack(Item.bread)));
//        }
//
//        for(int l = 0; l < 100; l++)
//        {
//            dngItems.add(new DungeonLoot(new ItemStack(Item.wheat), 1, 4));
//        }
//
//        for(int i1 = 0; i1 < 100; i1++)
//        {
//            dngItems.add(new DungeonLoot(new ItemStack(Item.gunpowder), 1, 4));
//        }
//
//        for(int j1 = 0; j1 < 100; j1++)
//        {
//            dngItems.add(new DungeonLoot(new ItemStack(Item.silk), 1, 4));
//        }
//
//        for(int k1 = 0; k1 < 100; k1++)
//        {
//            dngItems.add(new DungeonLoot(new ItemStack(Item.bucketEmpty)));
//        }
//
//        dngItems.add(new DungeonLoot(new ItemStack(Item.appleGold)));
//        for(int l1 = 0; l1 < 50; l1++)
//        {
//            dngItems.add(new DungeonLoot(new ItemStack(Item.redstone), 1, 4));
//        }
//
//        for(int i2 = 0; i2 < 5; i2++)
//        {
//            dngItems.add(new DungeonLoot(new ItemStack(Item.record13)));
//        }
//
//        for(int j2 = 0; j2 < 5; j2++)
//        {
//            dngItems.add(new DungeonLoot(new ItemStack(Item.recordCat)));
//        }
//
//    }
//
//    public static ItemStack dungeonGetRandomItem()
//    {
//        if(!dngAddedItems)
//        {
//            dungeonAddDefaultItems();
//            dngAddedItems = true;
//        }
//        if(dngItems.isEmpty())
//        {
//            return null;
//        } else
//        {
//            return ((DungeonLoot)dngItems.get((new Random()).nextInt(dngItems.size()))).getStack();
//        }
//    }
//
//    public static void acPageAdd(ACPage acpage)
//    {
//        acPages.add(acpage);
//    }
//
//    public static void acHide(Achievement aachievement[])
//    {
//        Achievement aachievement1[];
//        int j = (aachievement1 = aachievement).length;
//        for(int i = 0; i < j; i++)
//        {
//            Achievement achievement = aachievement1[i];
//            acHidden.add(Integer.valueOf(achievement.statId));
//        }
//
//    }
//
//    public static boolean acIsHidden(Achievement achievement)
//    {
//        return acHidden.contains(Integer.valueOf(achievement.statId));
//    }
//
//    public static ACPage acGetPage(Achievement achievement)
//    {
//        if(achievement == null)
//        {
//            return null;
//        }
//        for(Iterator iterator = acPages.iterator(); iterator.hasNext();)
//        {
//            ACPage acpage = (ACPage)iterator.next();
//            if(acpage.list.contains(Integer.valueOf(achievement.statId)))
//            {
//                return acpage;
//            }
//        }
//
//        return acDefaultPage;
//    }
//
//    public static ACPage acGetCurrentPage()
//    {
//        return (ACPage)acPages.get(acCurrentPage);
//    }
//
//    public static String acGetCurrentPageTitle()
//    {
//        return acGetCurrentPage().title;
//    }
//
//    public static void acPageNext()
//    {
//        acCurrentPage++;
//        if(acCurrentPage > acPages.size() - 1)
//        {
//            acCurrentPage = 0;
//        }
//    }
//
//    public static void acPagePrev()
//    {
//        acCurrentPage--;
//        if(acCurrentPage < 0)
//        {
//            acCurrentPage = acPages.size() - 1;
//        }
//    }
//
//    private static Minecraft instance;
//    public static boolean usingText = false;
//    private static ArrayList harvestIntercepts = new ArrayList();
//    private static ArrayList setIntercepts = new ArrayList();
//    private static ArrayList reaches = new ArrayList();
//    private static ArrayList dngMobs = new ArrayList();
//    private static ArrayList dngItems = new ArrayList();
//    private static ArrayList dngGuaranteed = new ArrayList();
//    private static boolean dngAddedMobs = false;
//    private static boolean dngAddedItems = false;
//    public static int acCurrentPage = 0;
//    private static ArrayList acHidden = new ArrayList();
//    private static ArrayList acPages = new ArrayList();
//    public static final ACPage acDefaultPage = new ACPage();
//
//    static
//    {
//        PlayerAPI.RegisterPlayerBase(net.minecraft.src.PlayerBaseSAPI.class);
//        showText();
//    }
//}
