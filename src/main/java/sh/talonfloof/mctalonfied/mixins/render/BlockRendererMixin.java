package sh.talonfloof.mctalonfied.mixins.render;

import net.minecraft.block.Block;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.block.BlockRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import sh.talonfloof.mctalonfied.util.FastNoiseLite;

@Mixin(BlockRenderer.class)
public class BlockRendererMixin {
    @Unique
    public boolean initialized = false;

    @Unique
    public FastNoiseLite grassShowNoise = new FastNoiseLite();
    @Unique
    public FastNoiseLite grassHeightNoise = new FastNoiseLite();

    @Inject(method = "renderTopFace", at = @At("TAIL"))
    public void talon$addGrassPoofs(Block b, double x, double y, double z, int t, CallbackInfo ci) {
        if(!initialized) {
            grassShowNoise = new FastNoiseLite("i have such a cute snep as a bf >w<".hashCode());
            grassHeightNoise = new FastNoiseLite("he's so nice and adorbs ~w~".hashCode());
            grassHeightNoise.SetNoiseType(FastNoiseLite.NoiseType.Value);
            grassHeightNoise.SetFrequency(1.0F);
            initialized = true;
        }
        if(b.id == Block.GRASS.id) {
            if(Math.abs(grassShowNoise.GetNoise((float)x*16,(float)z*16)) > 0.25F) {
                ((BlockRenderer) (Object) this).renderCross(Block.TALL_GRASS, 1, x, y + Math.abs(grassHeightNoise.GetNoise((float)x,(float)z))*0.6, z);
            }
        }
    }
}
