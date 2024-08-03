package sh.talonfloof.orderofthetalon.mixins;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.Session;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(Session.class)
public class PlayerSessionMixin {
    @ModifyVariable(method = "<init>", at = @At("HEAD"), argsOnly = true, ordinal = 0)
    private static String talon$devPlayerModify(String playerName) {
        return "TalonFloof";
    }
    @ModifyVariable(method = "<init>", at = @At("HEAD"), argsOnly = true, ordinal = 1)
    private static String talon$devPlayerModify2(String playerName) {
        return "";
    }
}
