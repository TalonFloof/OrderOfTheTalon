package sh.talonfloof.orderofthetalon.mixins.gen;

import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.worldgen.biome.BiomeProvider;
import net.modificationstation.stationapi.impl.worldgen.WorldgenListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = WorldgenListener.class, remap = false)
public class WorldgenListenerMixin {
    @Redirect(method = "registerBiomes", at = @At(value = "INVOKE", target = "Lnet/modificationstation/stationapi/api/worldgen/BiomeAPI;addOverworldBiomeProvider(Lnet/modificationstation/stationapi/api/util/Identifier;Lnet/modificationstation/stationapi/api/worldgen/biome/BiomeProvider;)V"))
    public void talon$disableDefaultBiomes(Identifier id, BiomeProvider provider) {

    }
}
