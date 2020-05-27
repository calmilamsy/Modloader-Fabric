package net.glasslauncher.shockahpi;

import net.minecraft.client.Minecraft;
import net.minecraft.src.EntityPlayerSP;
import net.minecraft.src.Session;
import net.minecraft.src.World;

public class EntityPlayerSPProxy extends EntityPlayerSP {
    public EntityPlayerSPProxy(Minecraft minecraft, World world, Session session, int i) {
        super(minecraft, world, session, i);
    }

    public boolean isInPortal() {
        return inPortal;
    }

    public void setInPortal(boolean b) {
        inPortal = b;
    }

    public void doPushOutOfBlocks(double d1, double d2, double d3) {
        pushOutOfBlocks(d1, d2, d3);
    }

    public void superOnLivingUpdate() {
        super.onLivingUpdate();
    }
}
