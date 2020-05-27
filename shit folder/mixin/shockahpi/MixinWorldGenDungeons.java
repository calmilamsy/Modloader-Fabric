package net.glasslauncher.mixin.shockahpi;

import net.glasslauncher.shockahpi.SAPI;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.Random;

@Mixin(WorldGenDungeons.class)
public class MixinWorldGenDungeons extends WorldGenerator {


   /**
    * @author calmilamsy
    * @reason Too lazy, just want to get working example.
    * //TODO: No overwrite.
    */
   @Overwrite
   public boolean generate(World paramfd, Random paramRandom, int paramInt1, int paramInt2, int paramInt3) {
      byte i = 3;
      int j = paramRandom.nextInt(2) + 2;
      int k = paramRandom.nextInt(2) + 2;
      int m = 0;

      int i1;
      int i2;
      int localcy;
      for(localcy = paramInt1 - j - 1; localcy <= paramInt1 + j + 1; ++localcy) {
         for(i1 = paramInt2 - 1; i1 <= paramInt2 + i + 1; ++i1) {
            for(i2 = paramInt3 - k - 1; i2 <= paramInt3 + k + 1; ++i2) {
               Material i3 = paramfd.getBlockMaterial(localcy, i1, i2);
               if(i1 == paramInt2 - 1 && !i3.isSolid()) {
                  return false;
               }

               if(i1 == paramInt2 + i + 1 && !i3.isSolid()) {
                  return false;
               }

               if((localcy == paramInt1 - j - 1 || localcy == paramInt1 + j + 1 || i2 == paramInt3 - k - 1 || i2 == paramInt3 + k + 1) && i1 == paramInt2 && paramfd.isAirBlock(localcy, i1, i2) && paramfd.isAirBlock(localcy, i1 + 1, i2)) {
                  ++m;
               }
            }
         }
      }

      if(m >= 1 && m <= 5) {
         for(localcy = paramInt1 - j - 1; localcy <= paramInt1 + j + 1; ++localcy) {
            for(i1 = paramInt2 + i; i1 >= paramInt2 - 1; --i1) {
               for(i2 = paramInt3 - k - 1; i2 <= paramInt3 + k + 1; ++i2) {
                  if(localcy != paramInt1 - j - 1 && i1 != paramInt2 - 1 && i2 != paramInt3 - k - 1 && localcy != paramInt1 + j + 1 && i1 != paramInt2 + i + 1 && i2 != paramInt3 + k + 1) {
                     paramfd.setBlockWithNotify(localcy, i1, i2, 0);
                  } else if(i1 >= 0 && !paramfd.getBlockMaterial(localcy, i1 - 1, i2).isSolid()) {
                     paramfd.setBlockWithNotify(localcy, i1, i2, 0);
                  } else if(paramfd.getBlockMaterial(localcy, i1, i2).isSolid()) {
                     if(i1 == paramInt2 - 1 && paramRandom.nextInt(4) != 0) {
                        paramfd.setBlockWithNotify(localcy, i1, i2, Block.cobblestoneMossy.blockID);
                     } else {
                        paramfd.setBlockWithNotify(localcy, i1, i2, Block.cobblestone.blockID);
                     }
                  }
               }
            }
         }

         localcy = 0;

         while(localcy < 2) {
            i1 = 0;

            while(true) {
               if(i1 < 3) {
                  label122: {
                     i2 = paramInt1 + paramRandom.nextInt(j * 2 + 1) - j;
                     int i4 = paramInt3 + paramRandom.nextInt(k * 2 + 1) - k;
                     if(paramfd.isAirBlock(i2, paramInt2, i4)) {
                        int i5 = 0;
                        if(paramfd.getBlockMaterial(i2 - 1, paramInt2, i4).isSolid()) {
                           ++i5;
                        }

                        if(paramfd.getBlockMaterial(i2 + 1, paramInt2, i4).isSolid()) {
                           ++i5;
                        }

                        if(paramfd.getBlockMaterial(i2, paramInt2, i4 - 1).isSolid()) {
                           ++i5;
                        }

                        if(paramfd.getBlockMaterial(i2, paramInt2, i4 + 1).isSolid()) {
                           ++i5;
                        }

                        if(i5 == 1) {
                           paramfd.setBlockWithNotify(i2, paramInt2, i4, Block.chest.blockID);
                           TileEntityChest localjs = (TileEntityChest)paramfd.getBlockTileEntity(i2, paramInt2, i4);

                           int i6;
                           ItemStack stack;
                           for(i6 = 0; i6 < 8; ++i6) {
                              stack = this.pickCheckLootItem(paramRandom);
                              if(stack != null) {
                                 localjs.setInventorySlotContents(paramRandom.nextInt(localjs.getSizeInventory()), stack);
                              }
                           }

                           i6 = 0;

                           while(true) {
                              if(i6 >= Math.min(19, SAPI.dungeonGetAmountOfGuaranteed())) {
                                 break label122;
                              }

                              stack = SAPI.dungeonGetGuaranteed(i6).getStack();
                              if(stack != null) {
                                 localjs.setInventorySlotContents(paramRandom.nextInt(localjs.getSizeInventory()), stack);
                              }

                              ++i6;
                           }
                        }
                     }

                     ++i1;
                     continue;
                  }
               }

               ++localcy;
               break;
            }
         }

         paramfd.setBlockWithNotify(paramInt1, paramInt2, paramInt3, Block.mobSpawner.blockID);
         TileEntityMobSpawner var19 = (TileEntityMobSpawner)paramfd.getBlockTileEntity(paramInt1, paramInt2, paramInt3);
         var19.setMobID(this.pickMobSpawner(paramRandom));
         return true;
      } else {
         return false;
      }
   }


   /**
    * @author calmilamsy
    * @reason Replaced functionality.
    */
   @Overwrite
   private ItemStack pickCheckLootItem(Random paramRandom) {
      return SAPI.dungeonGetRandomItem();
   }


   /**
    * @author calmilamsy
    * @reason Replaced functionality.
    */
   @Overwrite
   private String pickMobSpawner(Random paramRandom) {
      return SAPI.dungeonGetRandomMob();
   }
}
