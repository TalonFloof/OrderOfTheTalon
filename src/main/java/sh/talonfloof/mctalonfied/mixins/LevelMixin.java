package sh.talonfloof.mctalonfied.mixins;

import net.minecraft.level.Level;
import net.minecraft.level.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Level.class)
public class LevelMixin {
   @Redirect(method = "processLoadedChunks", at = @At(value = "INVOKE", target = "Lnet/minecraft/level/biome/Biome;canSnow()Z"))
   public boolean talon$stopFreezing(Biome instance) {
       return false;
   }
}
