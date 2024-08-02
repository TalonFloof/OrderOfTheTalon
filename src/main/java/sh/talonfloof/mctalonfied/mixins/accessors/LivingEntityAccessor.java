package sh.talonfloof.mctalonfied.mixins.accessors;

import net.minecraft.entity.living.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LivingEntity.class)
public interface LivingEntityAccessor {
    @Accessor("perpendicularMovement")
    float talon_getFrontMovement();

    @Accessor("parallelMovement")
    float talon_getRightMovement();
}