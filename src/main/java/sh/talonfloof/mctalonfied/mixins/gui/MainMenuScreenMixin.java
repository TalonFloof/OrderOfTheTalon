package sh.talonfloof.mctalonfied.mixins.gui;

import net.minecraft.client.gui.screen.menu.MainMenuScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import sh.talonfloof.mctalonfied.SharedConstants;

@Mixin(MainMenuScreen.class)
public class MainMenuScreenMixin {
    @ModifyConstant(method = "render", constant = @Constant(stringValue = "Minecraft Beta 1.7.3"))
    public String talon$changeVersion(String constant) {
        return SharedConstants.VERSION;
    }
}
