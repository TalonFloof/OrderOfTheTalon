package sh.talonfloof.orderofthetalon.mixins;

import net.minecraft.client.render.GameRenderer;
import org.lwjgl.opengl.Display;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class FPSLimitMixin {
    @Inject(method = "renderTick", at = @At("HEAD"))
    public void vsync(CallbackInfo ci) {
        Display.setVSyncEnabled(true);
    }

    /*@ModifyConstant(method = "method_1844", constant = @Constant(intValue = 200))
    public int modifyPerformanceTargetFps(int constant){
        return 60;
    }

    @ModifyConstant(method = "method_1844", constant = @Constant(intValue = 120))
    public int modifyBalancedTargetFps(int constant){
        return 60;
    }

    @ModifyConstant(method = "method_1844", constant = @Constant(intValue = 40))
    public int modifyPowerSaverTargetFps(int constant){
        return 60;
    }

    @Redirect(method = "method_1844", at = @At(value = "FIELD", opcode = Opcodes.GETFIELD, target = "Lnet/minecraft/client/option/GameOptions;fpsLimit:I"))
    public int overridePerformanceLevel(GameOptions instance){
        return 2;
    }*/
}