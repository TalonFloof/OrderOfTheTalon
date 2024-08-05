package sh.talonfloof.orderofthetalon.features;

import net.minecraft.level.Level;
import net.minecraft.level.structure.Structure;

import java.util.Random;

public class OreScatterFeature extends Structure {
    protected final Structure feature;
    protected final int iterations;
    protected final int minHeight;
    protected final int deltaHeight;

    public OreScatterFeature(Structure feature, int iterations, int minHeight, int maxHeight) {
        this.feature = feature;
        this.iterations = iterations;
        this.minHeight = minHeight;
        this.deltaHeight = maxHeight - minHeight + 1;
    }

    @Override
    public boolean generate(Level world, Random random, int x, int y, int z) {
        boolean result = false;
        for (int i = 0; i < iterations; i++) {
            int px = x + random.nextInt(16);
            int pz = z + random.nextInt(16);
            int py = getHeight(world, random, px, y, pz);
            result = feature.generate(world, random, px, py+64, pz) | result;
        }
        return result;
    }

    protected int getHeight(Level world, Random random, int x, int y, int z) {
        return minHeight + random.nextInt(deltaHeight);
    }
}
