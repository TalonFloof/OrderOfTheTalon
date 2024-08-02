package sh.talonfloof.mctalonfied.mixins.gen;

import net.minecraft.level.Level;
import net.minecraft.level.cave.Cave;
import net.minecraft.level.source.LevelSource;
import net.minecraft.level.source.OverworldLevelSource;
import net.minecraft.level.structure.SpringStructure;
import net.minecraft.level.structure.TallGrassStructure;
import net.minecraft.util.noise.PerlinOctaveNoise;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;

import java.util.Random;

@Mixin(value = OverworldLevelSource.class)
public class OverworldGenerationMixin {
    @Shadow private PerlinOctaveNoise interpolationNoise;
    @Shadow
    private Level level;
    @Shadow
    private Random rand;

    @Redirect(method = "getChunk", at = @At(value = "INVOKE", target = "Lnet/minecraft/level/cave/Cave;generate(Lnet/minecraft/level/source/LevelSource;Lnet/minecraft/level/Level;II[B)V"))
    public void noCaveGen(Cave instance, LevelSource level, Level x, int z, int blocks, byte[] bytes) {

    }

    @Redirect(method = "decorate", at = @At(value = "INVOKE", target = "Lnet/minecraft/level/structure/SpringStructure;generate(Lnet/minecraft/level/Level;Ljava/util/Random;III)Z"))
    public boolean cancelSpring(SpringStructure instance, Level random, Random x, int y, int z, int i) {
        return false;
    }

    @Redirect(method = "decorate", at = @At(value = "INVOKE", target = "Lnet/minecraft/level/structure/TallGrassStructure;generate(Lnet/minecraft/level/Level;Ljava/util/Random;III)Z"))
    public boolean cancelTallGrass(TallGrassStructure instance, Level random, Random x, int y, int z, int i) {
        return true;
    }
}

