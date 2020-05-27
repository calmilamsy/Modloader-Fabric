package net.glasslauncher.mixin.shockahpi;

import net.glasslauncher.shockahpi.EntityPlayerSPProxy;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BlockPortal.class)
public class MixinBlockPortal extends BlockBreakable {

   public MixinBlockPortal(int i, int i1, Material material, boolean b) {
      super(i, i1, material, b);
   }

   @Redirect(method = "onEntityCollidedWithBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/Entity;setInPortal()V"))
   public void redirectOnEntityCollidedWithBlock(Entity entity) {
      if(entity instanceof EntityPlayerSP) {
         EntityPlayerSPProxy entityplayersp = (EntityPlayerSPProxy)entity;
         entityplayersp.superOnLivingUpdate();
      }

   }

   public int getDimNumber() {
      return -1;
   }
}
