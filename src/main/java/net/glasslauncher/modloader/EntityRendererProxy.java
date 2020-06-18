// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 
// Source File Name:   EntityRendererProxy.java

package net.glasslauncher.modloader;

import net.minecraft.client.Minecraft;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.EntityRegistry;
import net.minecraft.sortme.GameRenderer;

// Referenced classes of package net.minecraft.src:
//            EntityRenderer, ModLoader

public class EntityRendererProxy extends GameRenderer {

    private Minecraft game;

    public EntityRendererProxy(Minecraft minecraft) {
        super(minecraft);
        game = minecraft;
    }

    public void method_1844(float f1) {
        super.method_1844(f1);
        ModLoader.onTick(game);
    }
}
