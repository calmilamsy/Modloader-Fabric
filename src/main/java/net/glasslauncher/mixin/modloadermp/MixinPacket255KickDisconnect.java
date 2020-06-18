package net.glasslauncher.mixin.modloadermp;

import java.io.DataInputStream;

import net.minecraft.packet.Id255Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Id255Packet.class)
public class MixinPacket255KickDisconnect {

   @ModifyConstant(method = "read")
   private int readPacketDataContant(int i) {
      return 1000;
   }
}
