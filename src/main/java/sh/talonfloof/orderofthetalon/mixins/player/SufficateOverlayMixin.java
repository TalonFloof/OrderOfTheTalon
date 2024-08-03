package sh.talonfloof.orderofthetalon.mixins.player;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.OverlaysRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sh.talonfloof.orderofthetalon.IExtendedPlayer;

@Mixin(value = OverlaysRenderer.class, priority = Integer.MAX_VALUE)
public class SufficateOverlayMixin {
    @Inject(method = "renderSuffocateOverlay", at = @At("HEAD"), cancellable = true)
    public void talon$cancelSuffocateOverlay(float i, int par2, CallbackInfo ci) {
        if(((IExtendedPlayer)((Minecraft) FabricLoader.getInstance().getGameInstance()).player).talon$getNoClip()) {
            ci.cancel();
        }
    }
}
