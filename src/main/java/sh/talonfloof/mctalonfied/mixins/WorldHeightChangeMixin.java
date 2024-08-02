package sh.talonfloof.mctalonfied.mixins;

import net.minecraft.level.dimension.OverworldDimension;
import net.modificationstation.stationapi.api.world.dimension.StationDimension;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(OverworldDimension.class)
public class WorldHeightChangeMixin implements StationDimension {


    @Override
    public int getHeight() {
        return 256;
    }

    @Override
    public int getBottomY() {
        return 0;
    }
}