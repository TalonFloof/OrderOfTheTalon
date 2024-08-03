package sh.talonfloof.orderofthetalon.mixins.entity;

import net.minecraft.level.LevelMonsterSpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Random;

@Mixin(LevelMonsterSpawner.class)
public class MonsterSpawnerMixin {
    @Redirect(method = "getPositionWithOffset(Lnet/minecraft/level/Level;II)Lnet/minecraft/util/maths/BlockPos;", at = @At(value = "INVOKE", target = "Ljava/util/Random;nextInt(I)I", ordinal = 1))
    private static int fixRandomization(Random instance, int i) {
        return -64 + instance.nextInt(256);
    }
}
