package sh.talonfloof.mctalonfied.mixins.gen;

import net.minecraft.block.Block;
import net.minecraft.level.Level;
import net.minecraft.level.biome.Biome;
import net.minecraft.level.chunk.Chunk;
import net.minecraft.level.source.OverworldLevelSource;
import net.minecraft.util.noise.PerlinOctaveNoise;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.world.HeightLimitView;
import net.modificationstation.stationapi.impl.world.chunk.ChunkSection;
import net.modificationstation.stationapi.impl.world.chunk.FlattenedChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import sh.talonfloof.mctalonfied.util.FastNoiseLite;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.IntStream;

@Mixin(OverworldLevelSource.class)
public class OverworldGenerationMixin {
    @Unique
    private FastNoiseLite terrainBaseNoise;
    @Unique
    private FastNoiseLite biomeCellNoise;

    @Shadow private PerlinOctaveNoise interpolationNoise;
    @Shadow
    private Level level;
    @Shadow
    private Random rand;

    @Unique
    private ForkJoinPool customPool = new ForkJoinPool(8);

    @Inject(method = "<init>", at = @At("TAIL"))
    public void talon$addCustomNoise(Level seed, long par2, CallbackInfo ci) {
        int baseNoiseSeed = rand.nextInt();
        terrainBaseNoise = new FastNoiseLite(baseNoiseSeed);
        terrainBaseNoise.SetNoiseType(FastNoiseLite.NoiseType.OpenSimplex2);
        terrainBaseNoise.SetFractalType(FastNoiseLite.FractalType.FBm);
        terrainBaseNoise.SetFrequency(0.005F);

        int biomeCellSeed = rand.nextInt();
        biomeCellNoise = new FastNoiseLite(biomeCellSeed);
        biomeCellNoise.SetNoiseType(FastNoiseLite.NoiseType.Cellular);
        biomeCellNoise.SetFractalType(FastNoiseLite.FractalType.None);
        biomeCellNoise.SetCellularReturnType(FastNoiseLite.CellularReturnType.CellValue);
        biomeCellNoise.SetCellularJitter(1.0F);
    }

    @Inject(method = "getChunk", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/level/source/OverworldLevelSource;shapeChunk(II[B[Lnet/minecraft/level/biome/Biome;[D)V"
    ), locals = LocalCapture.CAPTURE_FAILHARD)
    private void onGetChunk(int chunkX, int chunkZ, CallbackInfoReturnable<Chunk> info, byte[] blocks, Chunk chunk, double[] var5) {
        short worldHeight = (short) ((HeightLimitView) level).getTopY();
        if (worldHeight < 129) return;

        ChunkSection[] sections = ((FlattenedChunk)chunk).sections;

        short max = 0;
        short min = worldHeight;
        short[] map = makeHeightmap(chunkX << 4, chunkZ << 4, worldHeight /2);
        for (short i = 0; i < 256; i++) {
            if (map[i] > max) {
                max = map[i];
            }
            if (map[i] < min) {
                min = map[i];
            }
        }

        short maxSection = (short)Math.ceil((float)(max)/16F);
        if (maxSection >= sections.length) {
            maxSection = (short) (sections.length - 1);
        }

        for (short y = 0; y < sections.length; y++) {
            if (sections[y] == null) {
                sections[y] = new ChunkSection(y);
            }
        }

        final int finalMax = maxSection;

        customPool.submit(() -> IntStream.range(0,finalMax).parallel().forEach(n -> {
            ChunkSection section = sections[n];
            for (short i = 0; i < 256; i++) {
                byte x = (byte) (i & 15);
                byte z = (byte) (i >> 4);
                int height = Short.toUnsignedInt(map[i]);
                for (int y = 0; y < 16; y++) {
                    int yy = section.getYOffset() + y;
                    BlockState block = null;
                    if (yy < height - 4) {
                        block = Block.STONE.getDefaultState();
                    } else if (yy < height - 1) {
                        if(height <= (worldHeight/2)+2) {
                            block = Block.SAND.getDefaultState();
                        } else {
                            block = Block.DIRT.getDefaultState();
                        }
                    } else if (yy < height) {
                        if(height <= (worldHeight/2)+2) {
                            block = Block.SAND.getDefaultState();
                        } else {
                            block = Block.GRASS.getDefaultState();
                        }
                    } else if(yy < (worldHeight/2)) {
                        block = Block.STILL_WATER.getDefaultState();
                    }
                    if (block != null) {
                        section.setBlockState(x,y,z,block);
                    }
                }
            }
        }));
    }

    @Inject(method = "shapeChunk", at = @At("HEAD"), cancellable = true)
    private void disableShapeChunk(int chunkX, int chunkZ, byte[] tiles, Biome[] biomes, double[] temperatures, CallbackInfo info) {
        if (canApply()) {
            info.cancel();
        }
    }

    @Inject(method = "buildSurface", at = @At("HEAD"), cancellable = true)
    private void disableBuildSurface(int chunkX, int chunkZ, byte[] tiles, Biome[] biomes, CallbackInfo info) {
        if (canApply()) {
            info.cancel();
        }
    }

    @Redirect(method = "decorate", at = @At(value = "INVOKE", target = "Lnet/minecraft/level/Level;isAir(III)Z"))
    public boolean talon$cancelSnowGen(Level instance, int y, int z, int i) {
        return false;
    }

    @Unique
    private int getNoise(int x, int z, int base) {
        float baseValue = terrainBaseNoise.GetNoise(x,z);
        float biomeSeeds = biomeCellNoise.GetNoise(x,z);
        int totalHeight = Math.round((float)base+(baseValue*16F));
        return totalHeight;
    }

    @Unique
    private short[] makeHeightmap(int wx, int wz, int base) {
        short[] map = new short[256];
        for (short i = 0; i < 256; i++) {
            int px = wx | (i & 15);
            int pz = wz | (i >> 4);
            map[i] = (short)(getNoise(px,pz,base) + 1);
        }
        return map;
    }

    @Unique
    private boolean canApply() {
        return ((HeightLimitView) level).getTopY() > 128;
    }
}

