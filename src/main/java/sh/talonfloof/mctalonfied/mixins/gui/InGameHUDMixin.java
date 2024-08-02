package sh.talonfloof.mctalonfied.mixins.gui;

import it.unimi.dsi.fastutil.Pair;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.InGame;
import net.minecraft.client.render.TextRenderer;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.hit.HitType;
import net.minecraft.util.maths.Vec2I;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sh.talonfloof.mctalonfied.SharedConstants;

@Mixin(InGame.class)
public class InGameHUDMixin {
    @Shadow
    private Minecraft minecraft;

    @ModifyConstant(method = "renderHud", constant = @Constant(stringValue = "Minecraft Beta 1.7.3 ("))
    public String talon$changeVersion(String constant) {
        return SharedConstants.VERSION+" (";
    }

    @Inject(method = "renderHud", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/InGame;drawTextWithShadow(Lnet/minecraft/client/render/TextRenderer;Ljava/lang/String;III)V", ordinal = 5))
    public void talon$customDebugInfo(float bl, boolean i, int j, int par4, CallbackInfo ci) {
        TextRenderer tr = minecraft.textRenderer;
        HitResult hit = minecraft.hitResult;
        if (hit != null && hit.type == HitType.BLOCK) {
            ((InGame)(Object)this).drawTextWithShadow(tr,"Looking at ("+hit.x+", "+hit.y+", "+hit.z+")",0,88+16,14737632);
        }
    }
}
