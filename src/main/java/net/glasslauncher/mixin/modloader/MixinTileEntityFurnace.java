package net.glasslauncher.mixin.modloader;

import net.glasslauncher.modloader.ModLoader;
import net.minecraft.block.BlockBase;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.tileentity.TileEntityFurnace;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(TileEntityFurnace.class)
public class MixinTileEntityFurnace {

    /**
     * @author calmilamsy
     * @reason Editing if statement.
     */
    @Overwrite
    private int getFuelTime(ItemInstance paramiz) {
        if (paramiz == null) {
            return 0;
        }
        int j = paramiz.getType().id;
        if (j < 256 && BlockBase.BY_ID[j].material == Material.WOOD) {
            return 300;
        }
        if (j == ItemBase.stick.id) {
            return 100;
        }
        if (j == ItemBase.coal.id) {
            return 1600;
        }
        if (j == ItemBase.bucketLava.id) {
            return 20000;
        }
        if (j == BlockBase.SAPLING.id) {
            return 100;
        } else {
            return ModLoader.addAllFuel(j);
        }
    }
}
