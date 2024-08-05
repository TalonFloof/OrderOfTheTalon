package sh.talonfloof.orderofthetalon.mixins.gameplay;

import net.minecraft.block.LeavesBaseBlock;
import net.minecraft.block.LeavesBlock;
import net.minecraft.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(LeavesBlock.class)
public class LeavesBlockMixin {
    @Inject(method = "onScheduledTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/LeavesBlock;dropAndRemove(Lnet/minecraft/level/Level;III)V"))
    private void accelerateLeafDecay(Level world, int x, int y, int z, Random random, CallbackInfo ci) {
        if (!world.isRemote) {
            for (int offsetX = -3; offsetX <= 3; offsetX++) {
                for (int offsetY = -3; offsetY <= 3; offsetY++) {
                    for (int offsetZ = -3; offsetZ <= 3; offsetZ++) {
                        world.scheduleTick(x + offsetX, y + offsetY, z + offsetZ, ((LeavesBaseBlock) (Object) this).id, random.nextInt(10, 25));
                    }
                }
            }
        }
    }
}
