package sh.talonfloof.orderofthetalon.features;

import net.minecraft.level.Level;
import net.minecraft.level.structure.LargeOakStructure;
import net.minecraft.level.structure.Structure;

import java.util.Random;

public class LargeTree extends Structure {
    @Override
    public boolean generate(Level level, Random random, int x, int y, int z) {
        LargeOakStructure backend = new LargeOakStructure();
        return backend.generate(level,random,x,y,z);
    }
}
