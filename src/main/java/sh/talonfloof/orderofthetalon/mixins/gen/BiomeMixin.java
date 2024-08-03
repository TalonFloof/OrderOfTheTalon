package sh.talonfloof.orderofthetalon.mixins.gen;

import net.minecraft.level.biome.Biome;
import net.modificationstation.stationapi.api.worldgen.biome.StationBiome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Biome.class, priority = Integer.MAX_VALUE)
public class BiomeMixin implements StationBiome {
    @Shadow
    private static Biome[] biomes;

    @Inject(method = "createBiomeArray", at = @At("HEAD"), cancellable = true)
    private static void talon$removeBiomeArray(CallbackInfo ci) {
        for(int i=0; i < 4096; i++) {
            biomes[i] = Biome.PLAINS;
        }
        ci.cancel();
    }

    /*@Override
    public boolean noSurfaceRules() {
        return true;
    }*/
}
