package net.glasslauncher.modloader.mixin.modloader;

import net.glasslauncher.modloader.ModLoader;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(TileEntityFurnace.class)
public class MixinTileEntityFurnace {

    /**
     * @author calmilamsy
     * @reason Editing if statement.
     */
    @Overwrite
    private int getItemBurnTime(ItemStack paramiz) {
        if (paramiz == null) {
            return 0;
        }
        int j = paramiz.getItem().shiftedIndex;
        if (j < 256 && Block.blocksList[j].blockMaterial == Material.wood) {
            return 300;
        }
        if (j == Item.stick.shiftedIndex) {
            return 100;
        }
        if (j == Item.coal.shiftedIndex) {
            return 1600;
        }
        if (j == Item.bucketLava.shiftedIndex) {
            return 20000;
        }
        if (j == Block.sapling.blockID) {
            return 100;
        } else {
            return ModLoader.addAllFuel(j);
        }
    }
}
