package net.glasslauncher.modloader.mixin;

import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftServer.class)
public abstract class MixinMinecraftServer {
	@Shadow public abstract void log(String s);

	@Inject(method = "startServer", at = @At("HEAD"))
	private void onRun(CallbackInfoReturnable<Boolean> cir) {
		this.log("Hello from a mixin!");
	}
}
