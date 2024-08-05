package sh.talonfloof.orderofthetalon.mixins.render;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.block.BlockRenderer;
import net.modificationstation.stationapi.api.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sh.talonfloof.orderofthetalon.util.FastNoiseLite;

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
        if(b.id == Block.GRASS.id && ((Minecraft) FabricLoader.getInstance().getGameInstance()).options.fancyGraphics) {
            if(Math.abs(grassShowNoise.GetNoise((float)x*16,(float)z*16)) > 0.25F) {
                double val = Math.abs(grassHeightNoise.GetNoise((float)x,(float)z));
                ((BlockRenderer) (Object) this).renderCross(Block.TALL_GRASS, 1, x, y + MathHelper.lerp(val,0.15,0.8), z);
            }
        }
    }
}
