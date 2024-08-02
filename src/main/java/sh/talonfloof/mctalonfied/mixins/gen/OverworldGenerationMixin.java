package sh.talonfloof.mctalonfied.mixins.gen;

import net.minecraft.block.Block;
import net.minecraft.level.Level;
import net.minecraft.level.cave.Cave;
import net.minecraft.level.chunk.Chunk;
import net.minecraft.level.source.LevelSource;
import net.minecraft.level.source.OverworldLevelSource;
import net.minecraft.level.structure.SpringStructure;
import net.minecraft.level.structure.TallGrassStructure;
import net.minecraft.util.noise.PerlinOctaveNoise;
import net.modificationstation.stationapi.api.block.States;
import net.modificationstation.stationapi.api.util.math.MathHelper;
import net.modificationstation.stationapi.impl.world.chunk.FlattenedChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import sh.talonfloof.mctalonfied.util.AdvMathUtil;
import sh.talonfloof.mctalonfied.util.DoubleArray3D;
import sh.talonfloof.mctalonfied.util.FastNoiseLite;

import java.util.Random;

@Mixin(value = OverworldLevelSource.class)
public class OverworldGenerationMixin {
    @Shadow private PerlinOctaveNoise interpolationNoise;
    @Shadow
    private Level level;
    @Shadow
    private Random rand;
    @Shadow private Cave cave;
    @Unique
    public FastNoiseLite caveSurfaceFractals;
    @Unique
    public FastNoiseLite caveNoodleNoise;
    @Unique
    private FlattenedChunk currentChunk;

    @Inject(method = "<init>", at = @At("TAIL"))
    public void talon$genSetup(Level seed, long par2, CallbackInfo ci) {
        /*caveNoodleNoise = new FastNoiseLite(rand.nextInt());
        caveNoodleNoise.SetNoiseType(FastNoiseLite.NoiseType.Cellular);
        caveNoodleNoise.SetCellularDistanceFunction(FastNoiseLite.CellularDistanceFunction.EuclideanSq);
        caveNoodleNoise.SetCellularReturnType(FastNoiseLite.CellularReturnType.Distance2Mul);
        caveNoodleNoise.SetFractalType(FastNoiseLite.FractalType.None);

        caveNoodleNoise.SetFrequency(0.05F);
        caveSurfaceFractals = new FastNoiseLite(rand.nextInt());
        caveSurfaceFractals.SetNoiseType(FastNoiseLite.NoiseType.Cellular);
        caveSurfaceFractals.SetCellularReturnType(FastNoiseLite.CellularReturnType.CellValue);
        caveSurfaceFractals.SetCellularDistanceFunction(FastNoiseLite.CellularDistanceFunction.Hybrid);
        caveSurfaceFractals.SetFrequency(0.01F);*/
    }

    @Inject(method = "getChunk", at = @At(value = "INVOKE", target = "Lnet/minecraft/level/Level;getBiomeSource()Lnet/minecraft/level/biome/BiomeSource;"), locals = LocalCapture.CAPTURE_FAILHARD)
    public void talon$getChunk(int chunkZ, int par2, CallbackInfoReturnable<Chunk> cir, byte[] var3, Chunk var4) {
        currentChunk = (FlattenedChunk)var4;
    }

    /*@Redirect(method = "getChunk", at = @At(value = "INVOKE", target = "Lnet/minecraft/level/cave/Cave;generate(Lnet/minecraft/level/source/LevelSource;Lnet/minecraft/level/Level;II[B)V"))
    public void noCaveGen(Cave instance, LevelSource level, Level l, int chunkX, int chunkZ, byte[] blocks) {
        for(int i=0; i < (256/16); i++) {
            currentChunk.getOrCreateSection(i,false);
        }
        int h = getMaxSurfaceHeight(blocks);
        boolean[][][] samples = talon$sampleNoise(chunkX,chunkZ,h);
        for(int x=0; x < 16; x++) {
            for(int z=0; z < 16; z++) {
                for(int y=0; y < h; y++) {
                    int index = (x << 12) | (z << 8) | y;
                    if(samples[x][z][y]) {
                        int val = blocks[index];
                        if (val != Block.FLOWING_WATER.id && val != Block.STILL_WATER.id && val != Block.BEDROCK.id) {
                            if (y < 8) {
                                blocks[index] = (byte) Block.FLOWING_LAVA.id;
                            } else {
                                blocks[index] = 0;
                            }
                        }
                    }
                }
            }
        }
    }*/

    @Unique
    public boolean[][][] talon$sampleNoise(int chunkX, int chunkZ, int height) {
        double[][] surfaceBase = new double[4][4];
        for(int x=0; x < 4; x++) {
            for(int z=0; z < 4; z++) {
                float noiseX = (chunkX << 4) + (x*4);
                float noiseZ = (chunkZ << 4) + (z*4);
                float surface = caveSurfaceFractals.GetNoise(noiseX, noiseZ);
                surfaceBase[x][z] = surface;
            }
        }
        DoubleArray3D base = new DoubleArray3D(5,33,5);
        for(int x=0; x < base.width; x++) {
            for(int z =0; z < base.length; z++) {
                float noiseX = (chunkX << 4) + (x*4);
                float noiseZ = (chunkZ << 4) + (z*4);
                for (int y = 0; y < base.height; y++) {
                    base.set(x,y,z,((caveNoodleNoise.GetNoise(noiseX, y*4, noiseZ) + 1F) / 2F));
                }
            }
        }
        DoubleArray3D interp = new DoubleArray3D(16,256,16);
        AdvMathUtil.lerp3DPoints(base,interp,4,8);
        boolean[][][] vals = new boolean[16][16][height];
        for(int x = 0; x< 16;x++) {
            for (int z = 0; z < 16; z++) {
                float noiseX = (chunkX << 4) | x;
                float noiseZ = (chunkZ << 4) | z;
                for (int y = 0; y < height; y++) {
                    float threshold = y < (128 - 32) ? 0.085F : (surfaceBase[x >> 2][z >> 2] > 0.75F ? 0.085F : MathHelper.lerp((y - (128.0 - 32.0)) / 32.0, 0.085F, 0.3F));
                    vals[x][z][y] = (interp.get(x,y,z) > threshold);
                }
            }
        }
        return vals;
    }

    //tests 6 points in hexagon pattern get max height of chunk
    private int getMaxSurfaceHeight(byte[] chunk)
    {
        int max = 0;
        int[][] testcords = {{2, 6}, {3, 11}, {7, 2}, {9, 13}, {12,4}, {13, 9}};

        for (int n = 0; n < testcords.length; n++)
        {
            int testmax = getSurfaceHeight(chunk, testcords[n][0], testcords[n][1]);
            if(testmax > max)
            {
                max = testmax;
                if(max > 256)
                    return max;
            }
        }
        return max;
    }

    @Unique
    private int getSurfaceHeight(byte[] chunk, int localX, int localZ)
    {
        // Using a recursive binary search to find the surface
        return recursiveBinarySurfaceSearch(chunk, localX, localZ, 255, 0);
    }

    // Recursive binary search, this search always converges on the surface in 8 in cycles for the range 255 >= y >= 0
    @Unique
    private int recursiveBinarySurfaceSearch(byte[] chunk, int localX, int localZ, int searchTop, int searchBottom)
    {
        int top = searchTop;
        if (searchTop > searchBottom)
        {
            int searchMid = (searchBottom + searchTop) / 2;
            int index = (localX << 12) | (localZ << 8) | searchMid;
            if (chunk[index] != 0)
            {
                top = recursiveBinarySurfaceSearch(chunk, localX, localZ, searchTop, searchMid + 1);
            } else
            {
                top = recursiveBinarySurfaceSearch(chunk, localX, localZ, searchMid, searchBottom);
            }
        }
        return top;
    }

    @ModifyConstant(method = "Lnet/minecraft/level/source/OverworldLevelSource;buildSurface(II[B[Lnet/minecraft/level/biome/Biome;)V", constant = @Constant(intValue = 64))
    public int hi(int constant) {
        return 128;
    }

    @Inject(method = "decorate", at = @At(value = "HEAD"), cancellable = true)
    public void cancelDecoration(LevelSource chunkX, int chunkZ, int par3, CallbackInfo ci) {
        ci.cancel();
    }
}

