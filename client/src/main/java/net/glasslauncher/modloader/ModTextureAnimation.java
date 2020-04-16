// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 
// Source File Name:   ModTextureAnimation.java

package net.glasslauncher.modloader;

import net.minecraft.src.TextureFX;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.awt.image.BufferedImage;

// Referenced classes of package net.minecraft.src:
//            TextureFX, ModLoader

public class ModTextureAnimation extends TextureFX {

    private final int tickRate;
    private final byte[][] images;
    private int index;
    private int ticks;
    public ModTextureAnimation(int slot, int dst, BufferedImage source, int rate) {
        this(slot, 1, dst, source, rate);
    }
    public ModTextureAnimation(int slot, int size, int dst, BufferedImage source, int rate) {
        super(slot);
        index = 0;
        ticks = 0;
        tileSize = size;
        tileImage = dst;
        tickRate = rate;
        ticks = rate;
        bindImage(ModLoader.getMinecraftInstance().renderEngine);
        int targetWidth = GL11.glGetTexLevelParameteri(3553 /*GL_TEXTURE_2D*/, 0, 4096 /*GL_TEXTURE_WIDTH*/) / 16;
        int targetHeight = GL11.glGetTexLevelParameteri(3553 /*GL_TEXTURE_2D*/, 0, 4097 /*GL_TEXTURE_HEIGHT*/) / 16;
        int width = source.getWidth();
        int height = source.getHeight();
        int images = (int) Math.floor(height / width);
        if (images <= 0) {
            throw new IllegalArgumentException("source has no complete images");
        }
        this.images = new byte[images][];
        if (width != targetWidth) {
            BufferedImage img = new BufferedImage(targetWidth, targetHeight * images, 6);
            Graphics2D gfx = img.createGraphics();
            gfx.drawImage(source, 0, 0, targetWidth, targetHeight * images, 0, 0, width, height, null);
            gfx.dispose();
            source = img;
        }
        for (int i = 0; i < images; i++) {
            int[] temp = new int[targetWidth * targetHeight];
            source.getRGB(0, targetHeight * i, targetWidth, targetHeight, temp, 0, targetWidth);
            this.images[i] = new byte[targetWidth * targetHeight * 4];
            for (int j = 0; j < temp.length; j++) {
                int a = temp[j] >> 24 & 0xff;
                int r = temp[j] >> 16 & 0xff;
                int g = temp[j] >> 8 & 0xff;
                int b = temp[j] >> 0 & 0xff;
                this.images[i][j * 4 + 0] = (byte) r;
                this.images[i][j * 4 + 1] = (byte) g;
                this.images[i][j * 4 + 2] = (byte) b;
                this.images[i][j * 4 + 3] = (byte) a;
            }

        }

    }

    public void onTick() {
        if (ticks >= tickRate) {
            index++;
            if (index >= images.length) {
                index = 0;
            }
            imageData = images[index];
            ticks = 0;
        }
        ticks++;
    }
}
