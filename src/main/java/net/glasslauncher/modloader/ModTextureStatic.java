// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 
// Source File Name:   ModTextureStatic.java

package net.glasslauncher.modloader;

import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.render.TextureBinder;
import net.minecraft.client.render.TileRenderer;
import net.minecraft.entity.ParticleBase;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.awt.image.BufferedImage;

// Referenced classes of package net.minecraft.src:
//            TextureFX, ModLoader

public class ModTextureStatic extends TextureBinder {

    private boolean oldanaglyph;
    private int[] pixels;

    public ModTextureStatic(int slot, int dst, BufferedImage source) {
        this(slot, 1, dst, source);
    }

    public ModTextureStatic(int slot, int size, int dst, BufferedImage source) {
        super(slot);
        pixels = null;
        textureSize = size;
        renderMode = dst;
        bindTexture(ModLoader.getMinecraftInstance().textureManager);
        int targetWidth = GL11.glGetTexLevelParameteri(3553 /*GL_TEXTURE_2D*/, 0, 4096 /*GL_TEXTURE_WIDTH*/) / 16;
        int targetHeight = GL11.glGetTexLevelParameteri(3553 /*GL_TEXTURE_2D*/, 0, 4097 /*GL_TEXTURE_HEIGHT*/) / 16;
        int width = source.getWidth();
        int height = source.getHeight();
        pixels = new int[targetWidth * targetHeight];
        grid = new byte[targetWidth * targetHeight * 4];
        if (width != height || width != targetWidth) {
            BufferedImage img = new BufferedImage(targetWidth, targetHeight, 6);
            Graphics2D gfx = img.createGraphics();
            gfx.drawImage(source, 0, 0, targetWidth, targetHeight, 0, 0, width, height, null);
            img.getRGB(0, 0, targetWidth, targetHeight, pixels, 0, targetWidth);
            gfx.dispose();
        } else {
            source.getRGB(0, 0, width, height, pixels, 0, width);
        }
        update();
    }

    public static BufferedImage scale2x(BufferedImage in) {
        int width = in.getWidth();
        int height = in.getHeight();
        BufferedImage out = new BufferedImage(width * 2, height * 2, 2);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int E = in.getRGB(x, y);
                int B;
                if (y == 0) {
                    B = E;
                } else {
                    B = in.getRGB(x, y - 1);
                }
                int D;
                if (x == 0) {
                    D = E;
                } else {
                    D = in.getRGB(x - 1, y);
                }
                int F;
                if (x >= width - 1) {
                    F = E;
                } else {
                    F = in.getRGB(x + 1, y);
                }
                int H;
                if (y >= height - 1) {
                    H = E;
                } else {
                    H = in.getRGB(x, y + 1);
                }
                int E0;
                int E1;
                int E2;
                int E3;
                if (B != H && D != F) {
                    E0 = D != B ? E : D;
                    E1 = B != F ? E : F;
                    E2 = D != H ? E : D;
                    E3 = H != F ? E : F;
                } else {
                    E0 = E;
                    E1 = E;
                    E2 = E;
                    E3 = E;
                }
                out.setRGB(x * 2, y * 2, E0);
                out.setRGB(x * 2 + 1, y * 2, E1);
                out.setRGB(x * 2, y * 2 + 1, E2);
                out.setRGB(x * 2 + 1, y * 2 + 1, E3);
            }

        }

        return out;
    }

    public void update() {
        for (int i = 0; i < pixels.length; i++) {
            int a = pixels[i] >> 24 & 0xff;
            int r = pixels[i] >> 16 & 0xff;
            int g = pixels[i] >> 8 & 0xff;
            int b = pixels[i] >> 0 & 0xff;
            if (render3d) {
                int grey = (r + g + b) / 3;
                r = g = b = grey;
            }
            pixels[i * 4] = (byte) r;
            pixels[i * 4 + 1] = (byte) g;
            pixels[i * 4 + 2] = (byte) b;
            pixels[i * 4 + 3] = (byte) a;
        }

        oldanaglyph = render3d;
    }

    public void setup() {
        if (oldanaglyph != render3d) {
            update();
        }
    }
}
