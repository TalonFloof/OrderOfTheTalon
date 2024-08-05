package sh.talonfloof.orderofthetalon.asm;

import net.mine_diver.spasm.api.transform.ClassTransformer;
import net.mine_diver.spasm.api.transform.TransformationResult;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;
import org.objectweb.asm.util.Printer;
import org.objectweb.asm.util.Textifier;
import org.objectweb.asm.util.TraceClassVisitor;

import java.io.PrintWriter;

public class MojangFixCompatTransformer implements ClassTransformer {
    @Override
    public @NotNull TransformationResult transform(@NotNull ClassLoader classLoader, @NotNull ClassNode classNode) {
        if(classNode.name.equals("pl/telvarost/mojangfixstationapi/Config$ConfigFields")) {
            for(MethodNode method : classNode.methods) {
                if(method.name.equals("<init>")) {
                    for(int i=0; i < method.instructions.size(); i++) {
                        AbstractInsnNode insn = method.instructions.get(i);
                        if(insn.getOpcode() == Opcodes.PUTFIELD) {
                            if ((!(insn instanceof FieldInsnNode))) throw new AssertionError();
                            AbstractInsnNode constNode = method.instructions.get(i-4);
                            if(((FieldInsnNode) insn).name.equals("enableMojangFixTextOnTitleScreen")) {
                                if ((constNode.getOpcode() != Opcodes.ICONST_1)) throw new AssertionError();
                                method.instructions.set(constNode,new InsnNode(Opcodes.ICONST_0));
                            } else if(((FieldInsnNode) insn).name.equals("prioritizeMojangProvider")) {
                                if ((constNode.getOpcode() != Opcodes.ICONST_0)) throw new AssertionError();
                                method.instructions.set(constNode,new InsnNode(Opcodes.ICONST_1));
                            }
                        }
                    }
                }
            }
            return TransformationResult.SUCCESS;
        }
        return TransformationResult.PASS;
    }
}
