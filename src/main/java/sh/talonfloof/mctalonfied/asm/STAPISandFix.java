package sh.talonfloof.mctalonfied.asm;

import net.mine_diver.spasm.api.transform.ClassTransformer;
import net.mine_diver.spasm.api.transform.TransformationResult;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.tree.ClassNode;

public class STAPISandFix implements ClassTransformer {
    @Override
    public @NotNull TransformationResult transform(@NotNull ClassLoader classLoader, @NotNull ClassNode classNode) {
        //if(classNode.name.equals(""))
        return TransformationResult.PASS;
    }
}
