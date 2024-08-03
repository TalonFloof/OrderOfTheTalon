package sh.talonfloof.orderofthetalon.mixins.gen;

import net.minecraft.level.cave.OverworldCave;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(OverworldCave.class)
public class OverworldCaveGenerationFixMixin {
    @ModifyConstant(method = "generate(II[BDDDFFFIID)V", constant = @Constant(intValue = 120))
    public int talon$extend1(int constant) {
        return 248;
    }

    @ModifyConstant(method = "generate(II[BDDDFFFIID)V", constant = @Constant(intValue = 128))
    public int talon$extend2(int constant) {
        return 256;
    }

    @ModifyConstant(method = "generate(Lnet/minecraft/level/Level;IIII[B)V", constant = @Constant(intValue = 120))
    public int talon$extend3(int constant) {
        return 248;
    }
}
