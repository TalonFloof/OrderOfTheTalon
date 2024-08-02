package sh.talonfloof.mctalonfied.mixins.render;

import net.minecraft.block.GrassBlock;
import net.minecraft.level.BlockView;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Debug(print = true)
@Mixin(value = GrassBlock.class, priority = Integer.MIN_VALUE)
public class GrassBlockMixin {
    @Inject(
            method = "getColorMultiplier",
            at = @At("HEAD"),
            cancellable = true,
            expect = 1
    )
    private void talon$getBiomeColor(BlockView view, int x, int y, int z, CallbackInfoReturnable<Integer> info) {
        info.setReturnValue(0x4dd97f);
    }
}
