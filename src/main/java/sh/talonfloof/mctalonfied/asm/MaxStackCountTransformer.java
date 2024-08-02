package sh.talonfloof.mctalonfied.asm;

import net.mine_diver.spasm.api.transform.ClassTransformer;
import net.mine_diver.spasm.api.transform.TransformationResult;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.IntInsnNode;
import sh.talonfloof.mctalonfied.events.init.ServerInitListener;

import java.util.concurrent.atomic.AtomicBoolean;

public class MaxStackCountTransformer implements ClassTransformer {
    @Override
    public @NotNull TransformationResult transform(@NotNull ClassLoader classLoader, @NotNull ClassNode classNode) {
        if(classNode.interfaces.contains("net/minecraft/inventory/Inventory")) {
            classNode.methods.forEach(method -> {
                if(method.name.equals("getMaxStackSize")) {
                    for(AbstractInsnNode node : method.instructions) {
                        if(node instanceof IntInsnNode) {
                            if(((IntInsnNode)node).operand == 64) {
                                ((IntInsnNode) node).operand = 100;
                                System.out.println(classNode.name + " Inventory Transformation Successful!");
                            }
                            break;
                        }
                    }
                }
            });
            return TransformationResult.SUCCESS;
        }
        return TransformationResult.PASS;
    }
}
