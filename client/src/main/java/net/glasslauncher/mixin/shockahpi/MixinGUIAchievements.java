// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 
// Source File Name:   xm.java

/*package net.glasslauncher.mixin.shockahpi;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.*;

import net.glasslauncher.shockahpi.SAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.src.*;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Referenced classes of package net.minecraft.src:
//            GuiScreen, AchievementList, Achievement, GuiSmallButton, 
//            GuiButton, SAPI, GameSettings, KeyBinding, 
//            FontRenderer, MathHelper, RenderEngine, ACPage, 
//            StatFileWriter, RenderItem, RenderHelper, Tessellator

@Mixin(GuiAchievements.class)
public class MixinGUIAchievements extends GuiScreen {

    public MixinGUIAchievements(StatFileWriter xi1)
    {
        draw = true;
        achievementsPaneWidth = 256;
        achievementsPaneHeight = 202;
        mouseX = 0;
        mouseY = 0;
        isMouseButtonDown = 0;
        statFileWriter = xi1;
        char c1 = '\215';
        char c2 = '\215';
        xScrollO = xScrollP = xScrollTarget = AchievementList.openInventory.displayColumn * 24 - c1 / 2 - 12;
        yScrollO = yScrollP = yScrollTarget = AchievementList.openInventory.displayRow * 24 - c2 / 2;
    }

    @Redirect(method = "initGui", at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)V"))
    public void onInitGui(Object a)
    {
        try
        {
            controlList.add(a);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        controlList.add(new GuiSmallButton(11, width / 2 - 113, height / 2 + 74, 20, 20, "<"));
        controlList.add(new GuiSmallButton(12, width / 2 - 93, height / 2 + 74, 20, 20, ">"));
    }

    @Inject(method = "actionPerformed", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/GuiScreen;actionPerformed(Lnet/minecraft/GuiButton;)V"))
    protected void onActionPerformed(GuiButton button, CallbackInfo ci)
    {
        if(button.id == 11)
        {
            SAPI.acPagePrev();
        } else
        if(button.id == 12)
        {
            SAPI.acPageNext();
        }
        super.actionPerformed(button);
    }

    @Inject(method = "drawTitle", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/FontRenderer;drawString(Lnet/java/String;III)V"))
    protected void drawTitle(CallbackInfo ci)
    {
        int x = (width - achievementsPaneWidth) / 2;
        int y = (height - achievementsPaneHeight) / 2;
        fontRenderer.drawString("Achievements", x + 15, y + 5, 0x404040);
        fontRenderer.drawString(SAPI.acGetCurrentPageTitle(), width / 2 - 69, height / 2 + 80, 0);
    }

    /**
     * @author calmilamsy
     * @reason The changes are *massive*. There is no easy way to implement this without overwrite.
     *
    @Overwrite
    protected void drawAchievementScreen(int i1, int j1, float f)
    {
        int k1 = MathHelper.floor_double(xScrollO + (xScrollP - xScrollO) * (double)f);
        int l1 = MathHelper.floor_double(yScrollO + (yScrollP - yScrollO) * (double)f);
        if(k1 < X_MIN)
        {
            k1 = X_MIN;
        }
        if(l1 < Y_MIN)
        {
            l1 = Y_MIN;
        }
        if(k1 >= X_MAX)
        {
            k1 = X_MAX - 1;
        }
        if(l1 >= Y_MAX)
        {
            l1 = Y_MAX - 1;
        }
        int i2 = mc.renderEngine.getTexture("/terrain.png");
        int j2 = mc.renderEngine.getTexture("/achievement/bg.png");
        int k2 = (width - achievementsPaneWidth) / 2;
        int l2 = (height - achievementsPaneHeight) / 2;
        int i3 = k2 + 16;
        int j3 = l2 + 17;
        zLevel = 0.0F;
        GL11.glDepthFunc(518);
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0F, 0.0F, -200F);
        GL11.glEnable(3553 /*GL_TEXTURE_2D*);
        GL11.glDisable(2896 /*GL_LIGHTING*);
        GL11.glEnable(32826 /*GL_RESCALE_NORMAL_EXT*);
        GL11.glEnable(2903 /*GL_COLOR_MATERIAL*);
        mc.renderEngine.bindTexture(i2);
        int k3 = k1 + 288 >> 4;
        int i4 = l1 + 288 >> 4;
        int j4 = (k1 + 288) % 16;
        int i5 = (l1 + 288) % 16;
        Random random = new Random();
        for(int l8 = 0; l8 * 16 - i5 < 155; l8++)
        {
            float f5 = 0.6F - ((float)(i4 + l8) / 25F) * 0.3F;
            GL11.glColor4f(f5, f5, f5, 1.0F);
            for(int i9 = 0; i9 * 16 - j4 < 224; i9++)
            {
                random.setSeed(1234 + k3 + i9);
                random.nextInt();
                int k9 = SAPI.acGetCurrentPage().bgGetSprite(random, k3 + i9, i4 + l8);
                if(k9 != -1)
                {
                    drawTexturedModalRect((i3 + i9 * 16) - j4, (j3 + l8 * 16) - i5, k9 % 16 << 4, (k9 >> 4) << 4, 16, 16);
                }
            }

        }

        GL11.glEnable(2929 /*GL_DEPTH_TEST*);
        GL11.glDepthFunc(515);
        GL11.glDisable(3553 /*GL_TEXTURE_2D*);
        for(int l3 = 0; l3 < AchievementList.achievementList.size(); l3++)
        {
            Achievement ny2 = (Achievement)AchievementList.achievementList.get(l3);
            if(ny2.parentAchievement != null)
            {
                int k4 = (ny2.displayColumn * 24 - k1) + 11 + i3;
                int j5 = (ny2.displayRow * 24 - l1) + 11 + j3;
                int k5 = (ny2.parentAchievement.displayColumn * 24 - k1) + 11 + i3;
                int i6 = (ny2.parentAchievement.displayRow * 24 - l1) + 11 + j3;
                int l6 = 0;
                boolean flag = statFileWriter.hasAchievementUnlocked(ny2);
                boolean flag1 = statFileWriter.canUnlockAchievement(ny2);
                char c1 = Math.sin(((double)(System.currentTimeMillis() % 600L) / 600D) * 3.1415926535897931D * 2D) > 0.59999999999999998D ? '\377' : '\202';
                if(flag)
                {
                    l6 = 0xff707070;
                } else
                if(flag1)
                {
                    l6 = 65280 + (c1 << 24);
                } else
                {
                    l6 = 0xff000000;
                }
                draw = isVisibleLine(ny2);
                drawHorizontalLine(k4, k5, j5, l6);
                drawVerticalLine(k5, j5, i6, l6);
            }
        }

        Achievement ny1 = null;
        RenderItem bb1 = new RenderItem();
        GL11.glPushMatrix();
        GL11.glRotatef(180F, 1.0F, 0.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GL11.glPopMatrix();
        GL11.glDisable(2896 /*GL_LIGHTING*);
        GL11.glEnable(32826 /*GL_RESCALE_NORMAL_EXT*);
        GL11.glEnable(2903 /*GL_COLOR_MATERIAL*);
        for(int l4 = 0; l4 < AchievementList.achievementList.size(); l4++)
        {
            Achievement ny4 = (Achievement)AchievementList.achievementList.get(l4);
            if(isVisibleAchievement(ny4, 1))
            {
                int l5 = ny4.displayColumn * 24 - k1;
                int j6 = ny4.displayRow * 24 - l1;
                if(l5 >= -24 && j6 >= -24 && l5 <= 224 && j6 <= 155)
                {
                    if(statFileWriter.hasAchievementUnlocked(ny4))
                    {
                        float f1 = 1.0F;
                        GL11.glColor4f(f1, f1, f1, 1.0F);
                    } else
                    if(statFileWriter.canUnlockAchievement(ny4))
                    {
                        float f2 = Math.sin(((double)(System.currentTimeMillis() % 600L) / 600D) * 3.1415926535897931D * 2D) < 0.59999999999999998D ? 0.6F : 0.8F;
                        GL11.glColor4f(f2, f2, f2, 1.0F);
                    } else
                    {
                        float f3 = 0.3F;
                        GL11.glColor4f(f3, f3, f3, 1.0F);
                    }
                    mc.renderEngine.bindTexture(j2);
                    int i7 = i3 + l5;
                    int k7 = j3 + j6;
                    if(ny4.getSpecial())
                    {
                        drawTexturedModalRect(i7 - 2, k7 - 2, 26, 202, 26, 26);
                    } else
                    {
                        drawTexturedModalRect(i7 - 2, k7 - 2, 0, 202, 26, 26);
                    }
                    if(!statFileWriter.canUnlockAchievement(ny4))
                    {
                        float f4 = 0.1F;
                        GL11.glColor4f(f4, f4, f4, 1.0F);
                        bb1.field_27004_a = false;
                    }
                    GL11.glEnable(2896 /*GL_LIGHTING*);
                    GL11.glEnable(2884 /*GL_CULL_FACE*);
                    bb1.renderItemIntoGUI(mc.fontRenderer, mc.renderEngine, ny4.theItemStack, i7 + 3, k7 + 3);
                    GL11.glDisable(2896 /*GL_LIGHTING*);
                    if(!statFileWriter.canUnlockAchievement(ny4))
                    {
                        bb1.field_27004_a = true;
                    }
                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                    if(i1 >= i3 && j1 >= j3 && i1 < i3 + 224 && j1 < j3 + 155 && i1 >= i7 && i1 <= i7 + 22 && j1 >= k7 && j1 <= k7 + 22)
                    {
                        ny1 = ny4;
                    }
                }
            }
        }

        GL11.glDisable(2929 /*GL_DEPTH_TEST*);
        GL11.glEnable(3042 /*GL_BLEND*);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(j2);
        drawTexturedModalRect(k2, l2, 0, 0, achievementsPaneWidth, achievementsPaneHeight);
        GL11.glPopMatrix();
        zLevel = 0.0F;
        GL11.glDepthFunc(515);
        GL11.glDisable(2929 /*GL_DEPTH_TEST*);
        GL11.glEnable(3553 /*GL_TEXTURE_2D*);
        super.drawScreen(i1, j1, f);
        if(ny1 != null)
        {
            Achievement ny3 = ny1;
            String s1 = ny3.statName;
            String s2 = ny3.getDescription();
            int k6 = i1 + 12;
            int j7 = j1 - 4;
            if(statFileWriter.canUnlockAchievement(ny3))
            {
                int l7 = Math.max(fontRenderer.getStringWidth(s1), 120);
                int j8 = fontRenderer.splitStringWidth(s2, l7);
                if(statFileWriter.hasAchievementUnlocked(ny3))
                {
                    j8 += 12;
                }
                drawGradientRect(k6 - 3, j7 - 3, k6 + l7 + 3, j7 + j8 + 3 + 12, 0xc0000000, 0xc0000000);
                fontRenderer.drawSplitString(s2, k6, j7 + 12, l7, 0xffa0a0a0);
                if(statFileWriter.hasAchievementUnlocked(ny3))
                {
                    try
                    {
                        fontRenderer.drawStringWithShadow((String)met1.invoke(null, new Object[] {
                            "achievement.taken"
                        }), k6, j7 + j8 + 4, 0xff9090ff);
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            } else
            {
                try
                {
                    int i8 = Math.max(fontRenderer.getStringWidth(s1), 120);
                    String s3 = (String)met2.invoke(null, new Object[] {
                        "achievement.requires", new Object[] {
                            ny3.parentAchievement.statName
                        }
                    });
                    int k8 = fontRenderer.splitStringWidth(s3, i8);
                    drawGradientRect(k6 - 3, j7 - 3, k6 + i8 + 3, j7 + k8 + 12 + 3, 0xc0000000, 0xc0000000);
                    fontRenderer.drawSplitString(s3, k6, j7 + 12, i8, 0xff705050);
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
            fontRenderer.drawStringWithShadow(s1, k6, j7, statFileWriter.canUnlockAchievement(ny3) ? ny3.getSpecial() ? -128 : -1 : ny3.getSpecial() ? 0xff808040 : 0xff808080);
        }
        GL11.glEnable(2929 /*GL_DEPTH_TEST*);
        GL11.glEnable(2896 /*GL_LIGHTING*);
        RenderHelper.disableStandardItemLighting();
    }

    protected void drawRect(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
    {
        if(paramInt1 < paramInt3)
        {
            int i = paramInt1;
            paramInt1 = paramInt3;
            paramInt3 = i;
        }
        if(paramInt2 < paramInt4)
        {
            int i = paramInt2;
            paramInt2 = paramInt4;
            paramInt4 = i;
        }
        float f1 = (float)(paramInt5 >> 24 & 0xff) / 255F;
        float f2 = (float)(paramInt5 >> 16 & 0xff) / 255F;
        float f3 = (float)(paramInt5 >> 8 & 0xff) / 255F;
        float f4 = (float)(paramInt5 & 0xff) / 255F;
        Tessellator localns = Tessellator.instance;
        GL11.glEnable(3042 /*GL_BLEND*);
        GL11.glDisable(3553 /*GL_TEXTURE_2D*);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4f(f2, f3, f4, f1);
        if(draw)
        {
            localns.startDrawingQuads();
            localns.addVertex(paramInt1, paramInt4, 0.0D);
            localns.addVertex(paramInt3, paramInt4, 0.0D);
            localns.addVertex(paramInt3, paramInt2, 0.0D);
            localns.addVertex(paramInt1, paramInt2, 0.0D);
            localns.draw();
        }
        GL11.glEnable(3553 /*GL_TEXTURE_2D*);
        GL11.glDisable(3042 /*GL_BLEND*);
    }

    public boolean isVisibleAchievement(Achievement achievement, int deep)
    {
        if(checkHidden(achievement))
        {
            return false;
        }
        int tabID = SAPI.acGetPage(achievement).id;
        if(tabID == SAPI.acCurrentPage)
        {
            return true;
        }
        if(deep >= 1)
        {
            ArrayList list = new ArrayList(AchievementList.achievementList);
            for(int i = 0; i < list.size(); i++)
            {
                Achievement tmpAc = (Achievement)list.get(i);
                if(tmpAc.statId == achievement.statId)
                {
                    list.remove(i--);
                } else
                if(tmpAc.parentAchievement == null)
                {
                    list.remove(i--);
                } else
                if(tmpAc.parentAchievement.statId != achievement.statId)
                {
                    list.remove(i--);
                }
            }

            for(int i = 0; i < list.size(); i++)
            {
                Achievement tmpAc = (Achievement)list.get(i);
                if(isVisibleAchievement(tmpAc, deep - 1))
                {
                    return true;
                }
            }

        }
        return false;
    }

    public boolean isVisibleLine(Achievement achievement)
    {
        return achievement.parentAchievement != null && isVisibleAchievement(achievement, 1) && isVisibleAchievement(achievement.parentAchievement, 1);
    }

    public boolean checkHidden(Achievement achievement)
    {
        if(mc.statFileWriter.hasAchievementUnlocked(achievement))
        {
            return false;
        }
        if(SAPI.acIsHidden(achievement))
        {
            return true;
        }
        if(achievement.parentAchievement == null)
        {
            return false;
        } else
        {
            return checkHidden(achievement.parentAchievement);
        }
    }

    static Class getArrayClass(Class c)
    {
        try
        {
            Object e = Array.newInstance(c, 0);
            return e.getClass();
        }
        catch(Exception var2)
        {
            throw new IllegalArgumentException(var2);
        }
    }

    static Class _mthclass$(String s)
    {
        try
        {
            return Class.forName(s);
        }
        catch(ClassNotFoundException classnotfoundexception)
        {
            throw new NoClassDefFoundError(classnotfoundexception.getMessage());
        }
    }

    private static final int X_MIN;
    private static final int Y_MIN;
    private static final int X_MAX;
    private static final int Y_MAX;
    protected int achievementsPaneWidth;
    protected int achievementsPaneHeight;
    protected int mouseX;
    protected int mouseY;
    protected double xScrollO;
    protected double yScrollO;
    protected double xScrollP;
    protected double yScrollP;
    protected double xScrollTarget;
    protected double yScrollTarget;
    private int isMouseButtonDown;
    private StatFileWriter statFileWriter;
    private boolean draw;
    private static Method met1;
    private static Method met2;

    static 
    {
        try
        {
            met1 = Class.forName("do").getMethod("a", new Class[] {
                String.class
            });
            met2 = Class.forName("do").getMethod("a", new Class[] {
                String.class, getArrayClass(Object.class)
            });
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        X_MIN = AchievementList.minDisplayColumn * 24 - 112;
        Y_MIN = AchievementList.minDisplayRow * 24 - 112;
        X_MAX = AchievementList.maxDisplayColumn * 24 - 77;
        Y_MAX = AchievementList.maxDisplayRow * 24 - 77;
    }
}
*/