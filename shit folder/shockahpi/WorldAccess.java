package net.glasslauncher.shockahpi;

import net.minecraft.src.ISaveHandler;
import net.minecraft.src.World;
import net.minecraft.src.WorldProvider;

public class WorldAccess extends World {
    public WorldAccess(ISaveHandler iSaveHandler, String s, WorldProvider worldProvider, long l) {
        super(iSaveHandler, s, worldProvider, l);
    }

    public WorldAccess(World world, WorldProvider worldProvider) {
        super(world, worldProvider);
    }

    public WorldAccess(ISaveHandler iSaveHandler, String s, long l) {
        super(iSaveHandler, s, l);
    }

    public WorldAccess(ISaveHandler iSaveHandler, String s, long l, WorldProvider worldProvider) {
        super(iSaveHandler, s, l, worldProvider);
    }

    public void doNotifiyBlockChange(int x, int y, int z, int block) {
        super.notifyBlockChange(x, y, z, block);
    }
}
