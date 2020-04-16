// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 
// Source File Name:   AnimBase.java

package net.glasslauncher.shockahpi;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.src.TextureFX;

// Referenced classes of package net.minecraft.src:
//            TextureFX

public abstract class AnimBase extends TextureFX
{
    public abstract class Mode
    {

        public abstract void draw(int i, int j, Color color);

        public Mode()
        {
        }
    }


    public AnimBase(int spriteID, String spritePath)
    {
        super(spriteID);
        mdSet = new Mode() {

            public void draw(int x, int y, Color color)
            {
                setPixel(x, y, color);
            }

        }
;
        mdAdd = new Mode() {

            public void draw(int x, int y, Color color)
            {
                setPixel(x, y, AnimBase.add(new Color(frame[x][y]), color));
            }

        }
;
        mdSubtract = new Mode() {

            public void draw(int x, int y, Color color)
            {
                setPixel(x, y, AnimBase.subtract(new Color(frame[x][y]), color));
            }

        }
;
        mdBlend = new Mode() {

            public void draw(int x, int y, Color color)
            {
                setPixel(x, y, AnimBase.blend(new Color(frame[x][y]), color));
            }

        }
;
        size = (int)Math.sqrt(imageData.length / 4);
        fileBuf = new int[size][size];
        frame = new int[size][size];
        try
        {
            if(spritePath.isEmpty())
            {
                BufferedImage bufImage = ImageIO.read((Minecraft.class).getResource(tileImage != 0 ? "/gui/items.png" : "/terrain.png"));
                int xx = (spriteID % 16) * size;
                int yy = (int)Math.floor(spriteID / 16) * size;
                for(int y = 0; y < size; y++)
                {
                    for(int x = 0; x < size; x++)
                    {
                        fileBuf[x][y] = bufImage.getRGB(xx + x, yy + y);
                    }

                }

            } else
            {
                BufferedImage bufImage = ImageIO.read((Minecraft.class).getResource(spritePath));
                for(int y = 0; y < size; y++)
                {
                    for(int x = 0; x < size; x++)
                    {
                        fileBuf[x][y] = bufImage.getRGB(x, y);
                    }

                }

            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public void onTick()
    {
        getCleanFrame();
        animFrame();
        copyFrameToArray();
    }

    public abstract void animFrame();

    protected void getCleanFrame()
    {
        for(int y = 0; y < size; y++)
        {
            for(int x = 0; x < size; x++)
            {
                frame[x][y] = fileBuf[x][y];
            }

        }

    }

    protected void copyFrameToArray()
    {
        for(int y = 0; y < size; y++)
        {
            for(int x = 0; x < size; x++)
            {
                int index = getXYIndex(x, y);
                imageData[index * 4 + 0] = (byte)(frame[x][y] >> 16 & 0xff);
                imageData[index * 4 + 1] = (byte)(frame[x][y] >> 8 & 0xff);
                imageData[index * 4 + 2] = (byte)(frame[x][y] & 0xff);
                imageData[index * 4 + 3] = (byte)(frame[x][y] >> 24 & 0xff);
            }

        }

    }

    private void setPixel(int x, int y, Color color)
    {
        if(!inImage(x, y))
        {
            return;
        } else
        {
            frame[x][y] = color.getRGB();
            return;
        }
    }

    protected int getXYIndex(int x, int y)
    {
        return y * size + x;
    }

    protected boolean inImage(int x, int y)
    {
        return x >= 0 && y >= 0 && x < size && y < size;
    }

    protected void drawPoint(int x, int y, Color color)
    {
        drawPoint(x, y, color, mdSet);
    }

    protected void drawPoint(int x, int y, Color color, Mode mode)
    {
        mode.draw(x, y, color);
    }

    protected void drawRect(int x1, int y1, int x2, int y2, Color color)
    {
        drawRect(x1, y1, x2, y2, color, mdSet);
    }

    protected void drawRect(int x1, int y1, int x2, int y2, Color color, Mode mode)
    {
        int xS = Math.min(x1, x2);
        int yS = Math.min(y1, y2);
        int xE = Math.max(x1, x2);
        int yE = Math.max(y1, y2);
        for(int y = yS; y < yE; y++)
        {
            for(int x = xS; x < xE; x++)
            {
                drawPoint(x, y, color, mode);
            }

        }

    }

    protected void shiftFrame(int h, int v, boolean wrapH, boolean wrapV)
    {
        int line[] = new int[size];
        if(wrapH)
        {
            for(; h < 0; h += size) { }
            h %= size;
        }
        if(wrapV)
        {
            for(; v < 0; v += size) { }
            v %= size;
        }
        if(h != 0)
        {
            if(wrapH)
            {
                for(int y = 0; y < size; y++)
                {
                    for(int x = 0; x < size; x++)
                    {
                        line[x] = frame[x][y];
                    }

                    for(int x = 0; x < size; x++)
                    {
                        frame[x][y] = line[(x + h) % size];
                    }

                }

            } else
            {
                for(int y = 0; y < size; y++)
                {
                    for(int x = 0; x < size; x++)
                    {
                        line[x] = frame[x][y];
                        frame[x][y] = 0;
                    }

                    for(int x = 0; x < size; x++)
                    {
                        if(inImage(x + h, y))
                        {
                            frame[x + h][y] = line[x];
                        }
                    }

                }

            }
        }
        if(v != 0)
        {
            if(wrapV)
            {
                for(int x = 0; x < size; x++)
                {
                    for(int y = 0; y < size; y++)
                    {
                        line[y] = frame[x][y];
                    }

                    for(int y = 0; y < size; y++)
                    {
                        frame[x][y] = line[(y + v) % size];
                    }

                }

            } else
            {
                for(int x = 0; x < size; x++)
                {
                    for(int y = 0; y < size; y++)
                    {
                        line[y] = frame[x][y];
                        frame[x][y] = 0;
                    }

                    for(int y = 0; y < size; y++)
                    {
                        if(inImage(x, y + v))
                        {
                            frame[x][y + v] = line[y];
                        }
                    }

                }

            }
        }
    }

    protected void flipFrame(boolean h, boolean v)
    {
        if(h)
        {
            for(int x = 0; x < size / 2; x++)
            {
                for(int y = 0; y < size; y++)
                {
                    int swap = frame[x][y];
                    frame[x][y] = frame[size - 1 - x][y];
                    frame[size - 1 - x][y] = swap;
                }

            }

        }
        if(v)
        {
            for(int y = 0; y < size / 2; y++)
            {
                for(int x = 0; x < size; x++)
                {
                    int swap = frame[x][y];
                    frame[x][y] = frame[x][size - 1 - y];
                    frame[x][size - 1 - y] = swap;
                }

            }

        }
    }

    public static Color add(Color c1, Color c2)
    {
        float value = (float)c2.getAlpha() / 255F;
        int R = c1.getRed();
        R = (int)((float)R + (float)c2.getRed() * value);
        R = Math.min(R, 255);
        int G = c1.getGreen();
        G = (int)((float)G + (float)c2.getGreen() * value);
        G = Math.min(G, 255);
        int B = c1.getBlue();
        B = (int)((float)B + (float)c2.getBlue() * value);
        B = Math.min(B, 255);
        int A = c1.getAlpha();
        return new Color(R, G, B, A);
    }

    public static Color subtract(Color c1, Color c2)
    {
        float value = (float)c2.getAlpha() / 255F;
        int R = c1.getRed();
        R = (int)((float)R - (float)c2.getRed() * value);
        R = Math.max(R, 0);
        int G = c1.getGreen();
        G = (int)((float)G - (float)c2.getGreen() * value);
        G = Math.max(G, 0);
        int B = c1.getBlue();
        B = (int)((float)B - (float)c2.getBlue() * value);
        B = Math.max(B, 0);
        int A = c1.getAlpha();
        return new Color(R, G, B, A);
    }

    public static Color merge(Color c1, Color c2, float value)
    {
        value = Math.min(Math.max(value, 0.0F), 1.0F);
        float R = (float)c1.getRed() - ((float)c1.getRed() - (float)c2.getRed()) * value;
        float G = (float)c1.getGreen() - ((float)c1.getGreen() - (float)c2.getGreen()) * value;
        float B = (float)c1.getBlue() - ((float)c1.getBlue() - (float)c2.getBlue()) * value;
        float A = (float)c1.getAlpha() - ((float)c1.getAlpha() - (float)c2.getAlpha()) * value;
        return new Color(R / 255F, G / 255F, B / 255F, A / 255F);
    }

    public static Color blend(Color c1, Color c2)
    {
        float R = ((float)c1.getRed() / 255F) * ((float)c2.getRed() / 255F);
        float G = ((float)c1.getGreen() / 255F) * ((float)c2.getGreen() / 255F);
        float B = ((float)c1.getBlue() / 255F) * ((float)c2.getBlue() / 255F);
        float A = ((float)c1.getAlpha() / 255F) * ((float)c2.getAlpha() / 255F);
        return new Color(R, G, B, A);
    }

    protected int fileBuf[][];
    protected int frame[][];
    protected int size;
    public Mode mdSet;
    public Mode mdAdd;
    public Mode mdSubtract;
    public Mode mdBlend;

}
