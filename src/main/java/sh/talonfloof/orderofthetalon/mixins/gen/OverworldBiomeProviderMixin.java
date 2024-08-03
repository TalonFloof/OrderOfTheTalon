package sh.talonfloof.orderofthetalon.mixins.gen;

import net.minecraft.level.biome.Biome;
import net.modificationstation.stationapi.api.worldgen.biome.ClimateBiomeProvider;
import net.modificationstation.stationapi.impl.worldgen.OverworldBiomeProviderImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.Collection;

@Mixin(value = OverworldBiomeProviderImpl.class, remap = false, priority = Integer.MIN_VALUE)
public abstract class OverworldBiomeProviderMixin extends ClimateBiomeProvider {
    /**
     * @author Talon Kettuso
     * @reason To help with the new worldgen
     */
    @Overwrite
    public Collection<Biome> getBiomes() {
        Collection<Biome> biomes = super.getBiomes();
        return biomes;
    }
}
