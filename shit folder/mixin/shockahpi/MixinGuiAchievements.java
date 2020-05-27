package net.glasslauncher.mixin.shockahpi;

import net.glasslauncher.shockahpi.SAPI;
import net.minecraft.src.*;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Random;

@Mixin(GuiAchievements.class)
public class MixinGuiAchievements extends GuiScreen {
   private boolean draw = true;
   private static Method met1;
   private static Method met2;

   static {
      try {
         met1 = Class.forName("do").getMethod("a", new Class[]{String.class});
         met2 = Class.forName("do").getMethod("a", new Class[]{String.class, getArrayClass(Object.class)});
      } catch (Exception var1) {
         var1.printStackTrace();
      }
   }

   /**
    * @author calmilamsy
    * @reason Too lazy, just want to get working example.
    * //TODO: No overwrite.
    */
   @Overwrite
   public void initGui() {
      this.controlList.clear();

      try {
         this.controlList.add(new GuiSmallButton(1, this.width / 2 + 24, this.height / 2 + 74, 80, 20, (String)met1.invoke((Object)null, new Object[]{"gui.done"})));
      } catch (Exception var2) {
         var2.printStackTrace();
      }

      this.controlList.add(new GuiSmallButton(11, this.width / 2 - 113, this.height / 2 + 74, 20, 20, "<"));
      this.controlList.add(new GuiSmallButton(12, this.width / 2 - 93, this.height / 2 + 74, 20, 20, ">"));
   }

   /**
    * @author calmilamsy
    * @reason Too lazy, just want to get working example.
    * //TODO: No overwrite.
    */
   @Overwrite
   protected void actionPerformed(GuiButton button) {
      if(button.id == 1) {
         this.mc.displayGuiScreen(null);
         this.mc.setIngameFocus();
      } else if(button.id == 11) {
         SAPI.acPagePrev();
      } else if(button.id == 12) {
         SAPI.acPageNext();
      }

      super.actionPerformed(button);
   }

   @Inject(method = "func_27110_k", at = @At(value = "TAIL"))
   protected void doDrawTitle(CallbackInfo ci) {
      this.fontRenderer.drawString(SAPI.acGetCurrentPageTitle(), this.width / 2 - 69, this.height / 2 + 80, 0);
   }

   @Shadow @Final private static int field_27126_s;
   @Shadow @Final private static int field_27125_t;
   @Shadow @Final private static int field_27124_u;
   @Shadow @Final private static int field_27123_v;
   @Shadow protected double field_27116_m;
   @Shadow protected double field_27115_n;
   @Shadow protected double field_27114_o;
   @Shadow protected double field_27113_p;

   @Shadow private StatFileWriter field_27120_x;

   @Shadow protected int field_27121_a;
   @Shadow protected int field_27119_i;

   /**
    * @author calmilamsy
    * @reason Too lazy, just want to get working example.
    * //TODO: No overwrite.
    */
   @Overwrite
   protected void func_27109_b(int i1, int j1, float f) {
      int k1 = MathHelper.floor_double(this.field_27116_m + (this.field_27114_o - this.field_27116_m) * (double)f);
      int l1 = MathHelper.floor_double(this.field_27115_n + (this.field_27113_p - this.field_27115_n) * (double)f);
      if(k1 < field_27126_s) {
         k1 = field_27126_s;
      }

      if(l1 < field_27125_t) {
         l1 = field_27125_t;
      }

      if(k1 >= field_27124_u) {
         k1 = field_27124_u - 1;
      }

      if(l1 >= field_27123_v) {
         l1 = field_27123_v - 1;
      }

      int i2 = this.mc.renderEngine.getTexture("/terrain.png");
      int j2 = this.mc.renderEngine.getTexture("/achievement/bg.png");
      int k2 = (this.width - this.field_27121_a) / 2;
      int l2 = (this.height - this.field_27119_i) / 2;
      int i3 = k2 + 16;
      int j3 = l2 + 17;
      this.zLevel = 0.0F;
      GL11.glDepthFunc(518);
      GL11.glPushMatrix();
      GL11.glTranslatef(0.0F, 0.0F, -200.0F);
      GL11.glEnable(3553 /*GL_TEXTURE_2D*/);
      GL11.glDisable(2896 /*GL_LIGHTING*/);
      GL11.glEnable('\u803a');
      GL11.glEnable(2903 /*GL_COLOR_MATERIAL*/);
      this.mc.renderEngine.bindTexture(i2);
      int k3 = k1 + 288 >> 4;
      int i4 = l1 + 288 >> 4;
      int j4 = (k1 + 288) % 16;
      int i5 = (l1 + 288) % 16;
      Random random = new Random();

      int ny1;
      int ny3;
      int s1;
      for(ny1 = 0; ny1 * 16 - i5 < 155; ++ny1) {
         float bb1 = 0.6F - (float)(i4 + ny1) / 25.0F * 0.3F;
         GL11.glColor4f(bb1, bb1, bb1, 1.0F);

         for(ny3 = 0; ny3 * 16 - j4 < 224; ++ny3) {
            random.setSeed((long)(1234 + k3 + ny3));
            random.nextInt();
            s1 = SAPI.acGetCurrentPage().bgGetSprite(random, k3 + ny3, i4 + ny1);
            if(s1 != -1) {
               this.drawTexturedModalRect(i3 + ny3 * 16 - j4, j3 + ny1 * 16 - i5, s1 % 16 << 4, s1 >> 4 << 4, 16, 16);
            }
         }
      }

      GL11.glEnable(2929 /*GL_DEPTH_TEST*/);
      GL11.glDepthFunc(515);
      GL11.glDisable(3553 /*GL_TEXTURE_2D*/);

      int s2;
      int k6;
      int k8;
      int var36;
      for(ny1 = 0; ny1 < AchievementList.achievementList.size(); ++ny1) {
         Achievement var30 = (Achievement)AchievementList.achievementList.get(ny1);
         if(var30.parentAchievement != null) {
            ny3 = var30.displayColumn * 24 - k1 + 11 + i3;
            s1 = var30.displayRow * 24 - l1 + 11 + j3;
            s2 = var30.parentAchievement.displayColumn * 24 - k1 + 11 + i3;
            k6 = var30.parentAchievement.displayRow * 24 - l1 + 11 + j3;
            boolean j7 = false;
            boolean e = this.field_27120_x.hasAchievementUnlocked(var30);
            boolean s3 = this.field_27120_x.func_27181_b(var30);
            k8 = Math.sin((double)(System.currentTimeMillis() % 600L) / 600.0D * 3.141592653589793D * 2.0D) <= 0.6D?130:255;
            if(e) {
               var36 = -9408400;
            } else if(s3) {
               var36 = '\uff00' + (k8 << 24);
            } else {
               var36 = -16777216;
            }

            this.draw = this.isVisibleLine(var30);
            this.func_27100_a(ny3, s2, s1, var36);
            this.func_27099_b(s2, s1, k6, var36);
         }
      }

      Achievement var29 = null;
      RenderItem var31 = new RenderItem();
      GL11.glPushMatrix();
      GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
      RenderHelper.enableStandardItemLighting();
      GL11.glPopMatrix();
      GL11.glDisable(2896 /*GL_LIGHTING*/);
      GL11.glEnable('\u803a');
      GL11.glEnable(2903 /*GL_COLOR_MATERIAL*/);

      int var37;
      for(ny3 = 0; ny3 < AchievementList.achievementList.size(); ++ny3) {
         Achievement var32 = (Achievement)AchievementList.achievementList.get(ny3);
         if(this.isVisibleAchievement(var32, 1)) {
            s2 = var32.displayColumn * 24 - k1;
            k6 = var32.displayRow * 24 - l1;
            if(s2 >= -24 && k6 >= -24 && s2 <= 224 && k6 <= 155) {
               float var38;
               if(this.field_27120_x.hasAchievementUnlocked(var32)) {
                  var38 = 1.0F;
                  GL11.glColor4f(var38, var38, var38, 1.0F);
               } else if(this.field_27120_x.func_27181_b(var32)) {
                  var38 = Math.sin((double)(System.currentTimeMillis() % 600L) / 600.0D * 3.141592653589793D * 2.0D) >= 0.6D?0.8F:0.6F;
                  GL11.glColor4f(var38, var38, var38, 1.0F);
               } else {
                  var38 = 0.3F;
                  GL11.glColor4f(var38, var38, var38, 1.0F);
               }

               this.mc.renderEngine.bindTexture(j2);
               var36 = i3 + s2;
               var37 = j3 + k6;
               if(var32.getSpecial()) {
                  this.drawTexturedModalRect(var36 - 2, var37 - 2, 26, 202, 26, 26);
               } else {
                  this.drawTexturedModalRect(var36 - 2, var37 - 2, 0, 202, 26, 26);
               }

               if(!this.field_27120_x.func_27181_b(var32)) {
                  float var39 = 0.1F;
                  GL11.glColor4f(var39, var39, var39, 1.0F);
                  var31.field_27004_a = false;
               }

               GL11.glEnable(2896 /*GL_LIGHTING*/);
               GL11.glEnable(2884 /*GL_CULL_FACE*/);
               var31.renderItemIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, var32.theItemStack, var36 + 3, var37 + 3);
               GL11.glDisable(2896 /*GL_LIGHTING*/);
               if(!this.field_27120_x.func_27181_b(var32)) {
                  var31.field_27004_a = true;
               }

               GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
               if(i1 >= i3 && j1 >= j3 && i1 < i3 + 224 && j1 < j3 + 155 && i1 >= var36 && i1 <= var36 + 22 && j1 >= var37 && j1 <= var37 + 22) {
                  var29 = var32;
               }
            }
         }
      }

      GL11.glDisable(2929 /*GL_DEPTH_TEST*/);
      GL11.glEnable(3042 /*GL_BLEND*/);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.mc.renderEngine.bindTexture(j2);
      this.drawTexturedModalRect(k2, l2, 0, 0, this.field_27121_a, this.field_27119_i);
      GL11.glPopMatrix();
      this.zLevel = 0.0F;
      GL11.glDepthFunc(515);
      GL11.glDisable(2929 /*GL_DEPTH_TEST*/);
      GL11.glEnable(3553 /*GL_TEXTURE_2D*/);
      super.drawScreen(i1, j1, f);
      if(var29 != null) {
         Achievement var33 = var29;
         String var34 = var29.statName;
         String var35 = var29.getDescription();
         k6 = i1 + 12;
         var36 = j1 - 4;
         if(this.field_27120_x.func_27181_b(var29)) {
            var37 = Math.max(this.fontRenderer.getStringWidth(var34), 120);
            int var40 = this.fontRenderer.func_27277_a(var35, var37);
            if(this.field_27120_x.hasAchievementUnlocked(var29)) {
               var40 += 12;
            }

            this.drawGradientRect(k6 - 3, var36 - 3, k6 + var37 + 3, var36 + var40 + 3 + 12, -1073741824, -1073741824);
            this.fontRenderer.func_27278_a(var35, k6, var36 + 12, var37, -6250336);
            if(this.field_27120_x.hasAchievementUnlocked(var29)) {
               try {
                  this.fontRenderer.drawStringWithShadow((String)met1.invoke((Object)null, new Object[]{"achievement.taken"}), k6, var36 + var40 + 4, -7302913);
               } catch (Exception var28) {
                  var28.printStackTrace();
               }
            }
         } else {
            try {
               var37 = Math.max(this.fontRenderer.getStringWidth(var34), 120);
               String var41 = (String)met2.invoke((Object)null, new Object[]{"achievement.requires", new Object[]{var33.parentAchievement.statName}});
               k8 = this.fontRenderer.func_27277_a(var41, var37);
               this.drawGradientRect(k6 - 3, var36 - 3, k6 + var37 + 3, var36 + k8 + 12 + 3, -1073741824, -1073741824);
               this.fontRenderer.func_27278_a(var41, k6, var36 + 12, var37, -9416624);
            } catch (Exception var27) {
               var27.printStackTrace();
            }
         }

         this.fontRenderer.drawStringWithShadow(var34, k6, var36, this.field_27120_x.func_27181_b(var29)?(var29.getSpecial()?-128:-1):(var29.getSpecial()?-8355776:-8355712));
      }

      GL11.glEnable(2929 /*GL_DEPTH_TEST*/);
      GL11.glEnable(2896 /*GL_LIGHTING*/);
      RenderHelper.disableStandardItemLighting();
   }

   protected void drawRect(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
      int i;
      if(paramInt1 < paramInt3) {
         i = paramInt1;
         paramInt1 = paramInt3;
         paramInt3 = i;
      }

      if(paramInt2 < paramInt4) {
         i = paramInt2;
         paramInt2 = paramInt4;
         paramInt4 = i;
      }

      float f1 = (float)(paramInt5 >> 24 & 255) / 255.0F;
      float f2 = (float)(paramInt5 >> 16 & 255) / 255.0F;
      float f3 = (float)(paramInt5 >> 8 & 255) / 255.0F;
      float f4 = (float)(paramInt5 & 255) / 255.0F;
      Tessellator localns = Tessellator.instance;
      GL11.glEnable(3042 /*GL_BLEND*/);
      GL11.glDisable(3553 /*GL_TEXTURE_2D*/);
      GL11.glBlendFunc(770, 771);
      GL11.glColor4f(f2, f3, f4, f1);
      if(this.draw) {
         localns.startDrawingQuads();
         localns.addVertex((double)paramInt1, (double)paramInt4, 0.0D);
         localns.addVertex((double)paramInt3, (double)paramInt4, 0.0D);
         localns.addVertex((double)paramInt3, (double)paramInt2, 0.0D);
         localns.addVertex((double)paramInt1, (double)paramInt2, 0.0D);
         localns.draw();
      }

      GL11.glEnable(3553 /*GL_TEXTURE_2D*/);
      GL11.glDisable(3042 /*GL_BLEND*/);
   }

   public boolean isVisibleAchievement(Achievement achievement, int deep) {
      if(this.checkHidden(achievement)) {
         return false;
      } else {
         int tabID = SAPI.acGetPage(achievement).id;
         if(tabID == SAPI.acCurrentPage) {
            return true;
         } else {
            if(deep >= 1) {
               ArrayList list = new ArrayList(AchievementList.achievementList);

               int i;
               Achievement tmpAc;
               for(i = 0; i < list.size(); ++i) {
                  tmpAc = (Achievement)list.get(i);
                  if(tmpAc.statId == achievement.statId) {
                     list.remove(i--);
                  } else if(tmpAc.parentAchievement == null) {
                     list.remove(i--);
                  } else if(tmpAc.parentAchievement.statId != achievement.statId) {
                     list.remove(i--);
                  }
               }

               for(i = 0; i < list.size(); ++i) {
                  tmpAc = (Achievement)list.get(i);
                  if(this.isVisibleAchievement(tmpAc, deep - 1)) {
                     return true;
                  }
               }
            }

            return false;
         }
      }
   }

   public boolean isVisibleLine(Achievement achievement) {
      return achievement.parentAchievement != null && this.isVisibleAchievement(achievement, 1) && this.isVisibleAchievement(achievement.parentAchievement, 1);
   }

   public boolean checkHidden(Achievement achievement) {
      return this.mc.statFileWriter.hasAchievementUnlocked(achievement)?false:(SAPI.acIsHidden(achievement)?true:(achievement.parentAchievement == null?false:this.checkHidden(achievement.parentAchievement)));
   }

   private static Class getArrayClass(Class c) {
      try {
         Object var2 = Array.newInstance(c, 0);
         return var2.getClass();
      } catch (Exception var21) {
         throw new IllegalArgumentException(var21);
      }
   }
}
