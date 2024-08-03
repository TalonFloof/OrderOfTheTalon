package sh.talonfloof.orderofthetalon;

import org.spongepowered.asm.mixin.Unique;

public interface IExtendedPlayer {
    @Unique
    boolean talon$getNoClip();

    @Unique
    void talon$setNoClip(boolean value);
}
