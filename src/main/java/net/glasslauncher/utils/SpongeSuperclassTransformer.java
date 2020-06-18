package net.glasslauncher.utils;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.util.CheckClassAdapter;

import java.util.ArrayList;
import java.util.List;

public class SpongeSuperclassTransformer implements IClassTransformer {

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        String superclass = SpongeSuperclassRegistry.getSuperclass(name);
        if (superclass != null) {
            ClassNode node = this.readClass(basicClass);

            node.methods.stream().forEach(m -> this.transformMethod(m, name, node.superName, superclass));
            node.superName = superclass;

            node.accept(new CheckClassAdapter(new ClassWriter(0)));

            ClassWriter writer = new ClassWriter(0);
            node.accept(writer);
            return writer.toByteArray();

        }
        return basicClass;
    }

    private void transformMethod(MethodNode node, String name, String originalSuperclass, String superClass) {
        for (MethodInsnNode insn: this.findSuper(node, originalSuperclass, name)) {
            insn.owner = superClass;
        }
    }

    private List<MethodInsnNode> findSuper(MethodNode method, String originalSuperClass, String name) {
        List<MethodInsnNode> nodes = new ArrayList<>();
        for (AbstractInsnNode node: method.instructions.toArray()) {
            if (node.getOpcode() == Opcodes.INVOKESPECIAL && originalSuperClass.equals(((MethodInsnNode) node).owner)) {
                nodes.add((MethodInsnNode) node);
            }
        }
        return nodes;
    }

    private ClassNode readClass(byte[] basicClass) {
        ClassReader classReader = new ClassReader(basicClass);

        ClassNode classNode = new ClassNode();
        classReader.accept(classNode, 0);
        return classNode;
    }
}