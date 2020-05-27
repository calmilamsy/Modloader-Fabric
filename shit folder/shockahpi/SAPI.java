package net.glasslauncher.shockahpi;

import net.glasslauncher.playerapi.PlayerAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.src.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class SAPI {
   private static Minecraft instance;
   public static boolean usingText;
   private static ArrayList harvestIntercepts;
   private static ArrayList setIntercepts;
   private static ArrayList reaches;
   private static ArrayList dngMobs;
   private static ArrayList dngItems;
   private static ArrayList dngGuaranteed;
   private static boolean dngAddedMobs;
   private static boolean dngAddedItems;
   public static int acCurrentPage;
   private static ArrayList acHidden;
   private static ArrayList acPages;
   public static final ACPage acDefaultPage;

   public static void showText() {
      if(!usingText) {
         System.out.println("Using ShockAhPI r5.1");
         usingText = true;
      }

   }

   public static Minecraft getMinecraftInstance() {
      if(instance == null) {
         try {
            ThreadGroup exception = Thread.currentThread().getThreadGroup();
            int i = exception.activeCount();
            Thread[] athread = new Thread[i];
            exception.enumerate(athread);

            for(int j = 0; j < athread.length; ++j) {
               if(athread[j].getName().equals("Minecraft main thread")) {
                  Field field = Thread.class.getDeclaredField("target");
                  field.setAccessible(true);
                  instance = (Minecraft)field.get(athread[j]);
                  break;
               }
            }
         } catch (Exception var5) {
            var5.printStackTrace();
         }
      }

      return instance;
   }

   public static void interceptAdd(IInterceptHarvest iinterceptharvest) {
      harvestIntercepts.add(iinterceptharvest);
   }

   public static boolean interceptHarvest(World world, EntityPlayer entityplayer, Loc loc, int i, int j) {
      Iterator iterator = harvestIntercepts.iterator();

      IInterceptHarvest iinterceptharvest;
      do {
         if(!iterator.hasNext()) {
            return false;
         }

         iinterceptharvest = (IInterceptHarvest)iterator.next();
      } while(!iinterceptharvest.canIntercept(world, entityplayer, loc, i, j));

      iinterceptharvest.intercept(world, entityplayer, loc, i, j);
      return true;
   }

   public static void drop(World world, Loc loc, ItemStack itemstack) {
      if(!world.multiplayerWorld) {
         for(int i = 0; i < itemstack.stackSize; ++i) {
            float f = 0.7F;
            double d = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
            double d1 = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
            double d2 = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
            EntityItem entityitem = new EntityItem(world, (double)loc.x() + d, (double)loc.y() + d1, (double)loc.z() + d2, new ItemStack(itemstack.itemID, 1, itemstack.getItemDamage()));
            entityitem.delayBeforeCanPickup = 10;
            world.entityJoinedWorld(entityitem);
         }

      }
   }

   public static void interceptAdd(IInterceptBlockSet iinterceptblockset) {
      setIntercepts.add(iinterceptblockset);
   }

   public static int interceptBlockSet(World world, Loc loc, int i) {
      Iterator iterator = setIntercepts.iterator();

      IInterceptBlockSet iinterceptblockset;
      do {
         if(!iterator.hasNext()) {
            return i;
         }

         iinterceptblockset = (IInterceptBlockSet)iterator.next();
      } while(!iinterceptblockset.canIntercept(world, loc, i));

      return iinterceptblockset.intercept(world, loc, i);
   }

   public static void reachAdd(IReach ireach) {
      reaches.add(ireach);
   }

   public static float reachGet() {
      ItemStack itemstack = getMinecraftInstance().thePlayer.inventory.getCurrentItem();
      Iterator iterator = reaches.iterator();

      IReach ireach;
      do {
         if(!iterator.hasNext()) {
            return 4.0F;
         }

         ireach = (IReach)iterator.next();
      } while(!ireach.reachItemMatches(itemstack));

      return ireach.getReach(itemstack);
   }

   public static void dungeonAddMob(String s) {
      dungeonAddMob(s, 10);
   }

   public static void dungeonAddMob(String s, int i) {
      for(int j = 0; j < i; ++j) {
         dngMobs.add(s);
      }

   }

   public static void dungeonRemoveMob(String s) {
      for(int i = 0; i < dngMobs.size(); ++i) {
         if(((String)dngMobs.get(i)).equals(s)) {
            dngMobs.remove(i);
            --i;
         }
      }

   }

   public static void dungeonRemoveAllMobs() {
      dngAddedMobs = true;
      dngMobs.clear();
   }

   static void dungeonAddDefaultMobs() {
      int k;
      for(k = 0; k < 10; ++k) {
         dngMobs.add("Skeleton");
      }

      for(k = 0; k < 20; ++k) {
         dngMobs.add("Zombie");
      }

      for(k = 0; k < 10; ++k) {
         dngMobs.add("Spider");
      }

   }

   public static String dungeonGetRandomMob() {
      if(!dngAddedMobs) {
         dungeonAddDefaultMobs();
         dngAddedMobs = true;
      }

      return dngMobs.isEmpty()?"Pig":(String)dngMobs.get((new Random()).nextInt(dngMobs.size()));
   }

   public static void dungeonAddItem(DungeonLoot dungeonloot) {
      dungeonAddItem(dungeonloot, 100);
   }

   public static void dungeonAddItem(DungeonLoot dungeonloot, int i) {
      for(int j = 0; j < i; ++j) {
         dngItems.add(dungeonloot);
      }

   }

   public static void dungeonAddGuaranteedItem(DungeonLoot dungeonloot) {
      dngGuaranteed.add(dungeonloot);
   }

   public static int dungeonGetAmountOfGuaranteed() {
      return dngGuaranteed.size();
   }

   public static DungeonLoot dungeonGetGuaranteed(int i) {
      return (DungeonLoot)dngGuaranteed.get(i);
   }

   public static void dungeonRemoveItem(int i) {
      int k;
      for(k = 0; k < dngItems.size(); ++k) {
         if(((DungeonLoot)dngItems.get(k)).loot.itemID == i) {
            dngItems.remove(k);
            --k;
         }
      }

      for(k = 0; k < dngGuaranteed.size(); ++k) {
         if(((DungeonLoot)dngGuaranteed.get(k)).loot.itemID == i) {
            dngGuaranteed.remove(k);
            --k;
         }
      }

   }

   public static void dungeonRemoveAllItems() {
      dngAddedItems = true;
      dngItems.clear();
      dngGuaranteed.clear();
   }

   static void dungeonAddDefaultItems() {
      int j2;
      for(j2 = 0; j2 < 100; ++j2) {
         dngItems.add(new DungeonLoot(new ItemStack(Item.saddle)));
      }

      for(j2 = 0; j2 < 100; ++j2) {
         dngItems.add(new DungeonLoot(new ItemStack(Item.ingotIron), 1, 4));
      }

      for(j2 = 0; j2 < 100; ++j2) {
         dngItems.add(new DungeonLoot(new ItemStack(Item.bread)));
      }

      for(j2 = 0; j2 < 100; ++j2) {
         dngItems.add(new DungeonLoot(new ItemStack(Item.wheat), 1, 4));
      }

      for(j2 = 0; j2 < 100; ++j2) {
         dngItems.add(new DungeonLoot(new ItemStack(Item.gunpowder), 1, 4));
      }

      for(j2 = 0; j2 < 100; ++j2) {
         dngItems.add(new DungeonLoot(new ItemStack(Item.silk), 1, 4));
      }

      for(j2 = 0; j2 < 100; ++j2) {
         dngItems.add(new DungeonLoot(new ItemStack(Item.bucketEmpty)));
      }

      dngItems.add(new DungeonLoot(new ItemStack(Item.appleGold)));

      for(j2 = 0; j2 < 50; ++j2) {
         dngItems.add(new DungeonLoot(new ItemStack(Item.redstone), 1, 4));
      }

      for(j2 = 0; j2 < 5; ++j2) {
         dngItems.add(new DungeonLoot(new ItemStack(Item.record13)));
      }

      for(j2 = 0; j2 < 5; ++j2) {
         dngItems.add(new DungeonLoot(new ItemStack(Item.recordCat)));
      }

   }

   public static ItemStack dungeonGetRandomItem() {
      if(!dngAddedItems) {
         dungeonAddDefaultItems();
         dngAddedItems = true;
      }

      return dngItems.isEmpty()?null:((DungeonLoot)dngItems.get((new Random()).nextInt(dngItems.size()))).getStack();
   }

   public static void acPageAdd(ACPage acpage) {
      acPages.add(acpage);
   }

   public static void acHide(Achievement[] aachievement) {
      Achievement[] aachievement1 = aachievement;
      int j = aachievement.length;

      for(int i = 0; i < j; ++i) {
         Achievement achievement = aachievement1[i];
         acHidden.add(Integer.valueOf(achievement.statId));
      }

   }

   public static boolean acIsHidden(Achievement achievement) {
      return acHidden.contains(Integer.valueOf(achievement.statId));
   }

   public static ACPage acGetPage(Achievement achievement) {
      if(achievement == null) {
         return null;
      } else {
         Iterator iterator = acPages.iterator();

         ACPage acpage;
         do {
            if(!iterator.hasNext()) {
               return acDefaultPage;
            }

            acpage = (ACPage)iterator.next();
         } while(!acpage.list.contains(Integer.valueOf(achievement.statId)));

         return acpage;
      }
   }

   public static ACPage acGetCurrentPage() {
      return (ACPage)acPages.get(acCurrentPage);
   }

   public static String acGetCurrentPageTitle() {
      return acGetCurrentPage().title;
   }

   public static void acPageNext() {
      ++acCurrentPage;
      if(acCurrentPage > acPages.size() - 1) {
         acCurrentPage = 0;
      }

   }

   public static void acPagePrev() {
      --acCurrentPage;
      if(acCurrentPage < 0) {
         acCurrentPage = acPages.size() - 1;
      }

   }

   static {
      PlayerAPI.RegisterPlayerBase(PlayerBaseSAPI.class);
      usingText = false;
      harvestIntercepts = new ArrayList();
      setIntercepts = new ArrayList();
      reaches = new ArrayList();
      dngMobs = new ArrayList();
      dngItems = new ArrayList();
      dngGuaranteed = new ArrayList();
      dngAddedMobs = false;
      dngAddedItems = false;
      acCurrentPage = 0;
      acHidden = new ArrayList();
      acPages = new ArrayList();
      acDefaultPage = new ACPage();
      showText();
   }
}
