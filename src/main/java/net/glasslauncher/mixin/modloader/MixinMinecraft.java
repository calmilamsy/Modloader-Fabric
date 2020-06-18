package net.glasslauncher.mixin.modloader;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.glasslauncher.modloader.ModLoader;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft {

    @Shadow protected abstract void printOpenGLError(String errorName);

    @Environment(EnvType.CLIENT)
    @Redirect(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;printOpenGLError(Ljava/lang/String;)V"))
    public void startGame(Minecraft minecraft, String text) {
        if (!ModLoader.hasInit) {
            ModLoader.init();
        }
        printOpenGLError(text);
    }

    /*
    @Inject(method = "main", at = @At(value = "HEAD"))
    private static void loadMyShit(String[] strings, CallbackInfo ci) {
        ClassTinkerers.addReplacement("net/minecraft/src/ItemTool", (classNode) -> {
            try {
                String parentEntityRenderer = Tool.class.getName().replace(".", "/");
                for (MethodNode method : classNode.methods) {
                    Iterator<AbstractInsnNode> insns = method.instructions.iterator();
                    for (AbstractInsnNode insn = insns.next();insns.hasNext();insn = insns.next())
                        if (insn.getOpcode() == INVOKESPECIAL && ((MethodInsnNode)insn).owner.equals(classNode.superName))
                            ((MethodInsnNode)insn).owner = parentEntityRenderer;
                }
                classNode.superName = parentEntityRenderer;
                ClassWriter classWriter = new ClassWriter(0);
                classNode.accept(classWriter);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }*/
}
