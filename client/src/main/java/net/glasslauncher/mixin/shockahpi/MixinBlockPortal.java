// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 
// Source File Name:   BlockPortal.java

/*package net.glasslauncher.mixin.shockahpi;

import net.glasslauncher.playerapi.PlayerAPI;
import net.glasslauncher.shockahpi.PlayerBaseSAPI;
import net.glasslauncher.shockahpi.base.World;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Random;

// Referenced classes of package net.minecraft.src:
//            BlockBreakable, Material, IBlockAccess, World, 
//            Block, BlockFire, Entity, EntityPlayerSP, 
//            PlayerBaseSAPI, PlayerAPI, AxisAlignedBB

@Mixin(net.minecraft.src.BlockPortal.class)
public class MixinBlockPortal
{

    @Redirect(method = "onEntityCollidedWithBlock", at = @At(value = "INVOKE", target = "Lnet.minecraft.src.Entity;setInPortal()V"))
    public void addPlayerAPI(World world, int i, int j, int k, Entity entity)
    {
        if(entity instanceof EntityPlayerSP)
        {
            EntityPlayerSP entityplayersp = (EntityPlayerSP)entity;
            ((PlayerBaseSAPI) PlayerAPI.getPlayerBase(entityplayersp, PlayerBaseSAPI.class)).portal = getDimNumber();
        }
        entity.setInPortal();
    }

    public int getDimNumber()
    {
        return -1;
    }
}
*/