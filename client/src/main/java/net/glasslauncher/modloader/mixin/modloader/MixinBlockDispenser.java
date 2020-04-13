package net.glasslauncher.modloader.mixin.modloader;

import net.glasslauncher.modloader.ModLoader;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.Random;

@Mixin(BlockDispenser.class)
public class MixinBlockDispenser {


    /**
     * @author calmilamsy
     * @reason Modified if statement.
     */
    @Overwrite
    private void dispenseItem(World paramfd, int paramInt1, int paramInt2, int paramInt3, Random paramRandom) {
        int i = paramfd.getBlockMetadata(paramInt1, paramInt2, paramInt3);
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
        TileEntityDispenser localaz = (TileEntityDispenser) paramfd.getBlockTileEntity(paramInt1, paramInt2, paramInt3);
        ItemStack localiz = localaz.getRandomStackFromInventory();
        double d1 = (double) paramInt1 + (double) j * 0.59999999999999998D + 0.5D;
        double d2 = (double) paramInt2 + 0.5D;
        double d3 = (double) paramInt3 + (double) k * 0.59999999999999998D + 0.5D;
        if (localiz == null) {
            paramfd.playAuxSFX(1001, paramInt1, paramInt2, paramInt3, 0);
        } else {
            boolean handled = ModLoader.dispenseEntity(paramfd, d1, d2, d3, j, k, localiz);
            if (!handled) {
                if (localiz.itemID == Item.arrow.shiftedIndex) {
                    EntityArrow localObject = new EntityArrow(paramfd, d1, d2, d3);
                    localObject.setArrowHeading(j, 0.10000000149011611D, k, 1.1F, 6F);
                    localObject.doesArrowBelongToPlayer = true;
                    paramfd.entityJoinedWorld(localObject);
                    paramfd.playAuxSFX(1002, paramInt1, paramInt2, paramInt3, 0);
                } else if (localiz.itemID == Item.egg.shiftedIndex) {
                    EntityEgg localObject = new EntityEgg(paramfd, d1, d2, d3);
                    localObject.setEggHeading(j, 0.10000000149011611D, k, 1.1F, 6F);
                    paramfd.entityJoinedWorld(localObject);
                    paramfd.playAuxSFX(1002, paramInt1, paramInt2, paramInt3, 0);
                } else if (localiz.itemID == Item.snowball.shiftedIndex) {
                    EntitySnowball localObject = new EntitySnowball(paramfd, d1, d2, d3);
                    localObject.setSnowballHeading(j, 0.10000000149011611D, k, 1.1F, 6F);
                    paramfd.entityJoinedWorld(localObject);
                    paramfd.playAuxSFX(1002, paramInt1, paramInt2, paramInt3, 0);
                } else {
                    Entity localObject = new EntityItem(paramfd, d1, d2 - 0.29999999999999999D, d3, localiz);
                    double d4 = paramRandom.nextDouble() * 0.10000000000000001D + 0.20000000000000001D;
                    localObject.motionX = (double) j * d4;
                    localObject.motionY = 0.20000000298023221D;
                    localObject.motionZ = (double) k * d4;
                    localObject.motionX += paramRandom.nextGaussian() * 0.0074999998323619366D * 6D;
                    localObject.motionY += paramRandom.nextGaussian() * 0.0074999998323619366D * 6D;
                    localObject.motionZ += paramRandom.nextGaussian() * 0.0074999998323619366D * 6D;
                    paramfd.entityJoinedWorld(localObject);
                    paramfd.playAuxSFX(1000, paramInt1, paramInt2, paramInt3, 0);
                }
            }
            paramfd.playAuxSFX(2000, paramInt1, paramInt2, paramInt3, j + 1 + (k + 1) * 3);
        }
    }
}
