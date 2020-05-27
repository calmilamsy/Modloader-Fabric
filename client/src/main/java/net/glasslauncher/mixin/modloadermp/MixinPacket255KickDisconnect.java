package net.glasslauncher.mixin.modloadermp;

import java.io.DataInputStream;

import net.minecraft.src.Packet;
import net.minecraft.src.Packet255KickDisconnect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Packet255KickDisconnect.class)
public class MixinPacket255KickDisconnect {

   @ModifyConstant(method = "readPacketData")
   private int readPacketDataContant(int i) {
      return 1000;
   }
}
