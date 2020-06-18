package net.glasslauncher.mixin.modloader;

import net.glasslauncher.modloader.ModLoader;
import net.minecraft.block.Dispenser;
import net.minecraft.entity.EntityBase;
import net.minecraft.entity.Item;
import net.minecraft.entity.projectile.Arrow;
import net.minecraft.entity.projectile.ThrownEgg;
import net.minecraft.entity.projectile.ThrownSnowball;
import net.minecraft.item.ItemBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.Level;
import net.minecraft.tileentity.TileEntityDispenser;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.Random;

@Mixin(Dispenser.class)
public class MixinBlockDispenser {


    /**
     * @author calmilamsy
     * @reason Modified if statement.
     */
    @Overwrite
    private void method_1774(Level paramfd, int paramInt1, int paramInt2, int paramInt3, Random paramRandom) {
        int i = paramfd.getTileMeta(paramInt1, paramInt2, paramInt3);
        int j = 0;
        int k = 0;
        if (i == 3) {
            k = 1;
        } else if (i == 2) {
            k = -1;
        } else if (i == 5) {
            j = 1;
        } else {
            j = -1;
        }
        TileEntityDispenser localaz = (TileEntityDispenser) paramfd.getTileEntity(paramInt1, paramInt2, paramInt3);
        ItemInstance localiz = localaz.getItemToDispense();
        double d1 = (double) paramInt1 + (double) j * 0.59999999999999998D + 0.5D;
        double d2 = (double) paramInt2 + 0.5D;
        double d3 = (double) paramInt3 + (double) k * 0.59999999999999998D + 0.5D;
        if (localiz == null) {
            paramfd.playLevelEvent(1001, paramInt1, paramInt2, paramInt3, 0);
        } else {
            boolean handled = ModLoader.dispenseEntity(paramfd, d1, d2, d3, j, k, localiz);
            if (!handled) {
                if (localiz.itemId == ItemBase.arrow.id) {
                    Arrow localObject = new Arrow(paramfd, d1, d2, d3);
                    localObject.setPositionAndAngles(j, 0.10000000149011611D, k, 1.1F, 6F);
                    localObject.player = true;
                    paramfd.spawnEntity(localObject);
                    paramfd.playLevelEvent(1002, paramInt1, paramInt2, paramInt3, 0);
                } else if (localiz.itemId == ItemBase.egg.id) {
                    ThrownEgg localObject = new ThrownEgg(paramfd, d1, d2, d3);
                    localObject.setPositionAndAngles(j, 0.10000000149011611D, k, 1.1F, 6F);
                    paramfd.spawnEntity(localObject);
                    paramfd.playLevelEvent(1002, paramInt1, paramInt2, paramInt3, 0);
                } else if (localiz.itemId == ItemBase.snowball.id) {
                    ThrownSnowball localObject = new ThrownSnowball(paramfd, d1, d2, d3);
                    localObject.setPositionAndAngles(j, 0.10000000149011611D, k, 1.1F, 6F);
                    paramfd.spawnEntity(localObject);
                    paramfd.playLevelEvent(1002, paramInt1, paramInt2, paramInt3, 0);
                } else {
                    EntityBase localObject = new Item(paramfd, d1, d2 - 0.29999999999999999D, d3, localiz);
                    double d4 = paramRandom.nextDouble() * 0.10000000000000001D + 0.20000000000000001D;
                    localObject.velocityX = (double) j * d4;
                    localObject.velocityY = 0.20000000298023221D;
                    localObject.velocityZ = (double) k * d4;
                    localObject.velocityX += paramRandom.nextGaussian() * 0.0074999998323619366D * 6D;
                    localObject.velocityY += paramRandom.nextGaussian() * 0.0074999998323619366D * 6D;
                    localObject.velocityZ += paramRandom.nextGaussian() * 0.0074999998323619366D * 6D;
                    paramfd.spawnEntity(localObject);
                    paramfd.playLevelEvent(1000, paramInt1, paramInt2, paramInt3, 0);
                }
            }
            paramfd.playLevelEvent(2000, paramInt1, paramInt2, paramInt3, j + 1 + (k + 1) * 3);
        }
    }
}
