package net.glasslauncher.shockahpi;

import net.minecraft.src.Achievement;
import net.minecraft.src.Block;

import java.util.ArrayList;
import java.util.Random;

public class ACPage {
   private static int nextID = 1;
   public final int id;
   public final String title;
   ArrayList<Integer> list = new ArrayList();

   public ACPage() {
      this.id = 0;
      this.title = "Minecraft";
      SAPI.acPageAdd(this);
   }

   public ACPage(String title) {
      this.id = nextID++;
      this.title = title;
      SAPI.acPageAdd(this);
   }

   public void addAchievements(Achievement... achievements) {
      Achievement[] var5 = achievements;
      int var4 = achievements.length;

      for(int var3 = 0; var3 < var4; ++var3) {
         Achievement achievement = var5[var3];
         this.list.add(Integer.valueOf(achievement.statId));
      }

   }

   public int bgGetSprite(Random random, int x, int y) {
      int sprite = Block.sand.blockIndexInTexture;
      int rnd = random.nextInt(1 + y) + y / 2;
      if(rnd <= 37 && y != 35) {
         if(rnd == 22) {
            sprite = random.nextInt(2) == 0?Block.oreDiamond.blockIndexInTexture:Block.oreRedstone.blockIndexInTexture;
         } else if(rnd == 10) {
            sprite = Block.oreIron.blockIndexInTexture;
         } else if(rnd == 8) {
            sprite = Block.oreCoal.blockIndexInTexture;
         } else if(rnd > 4) {
            sprite = Block.stone.blockIndexInTexture;
         } else if(rnd > 0) {
            sprite = Block.dirt.blockIndexInTexture;
         }
      } else {
         sprite = Block.bedrock.blockIndexInTexture;
      }

      return sprite;
   }
}
