package sh.talonfloof.orderofthetalon.features;

import net.minecraft.block.Block;
import net.minecraft.level.Level;
import net.minecraft.level.structure.Structure;
import net.minecraft.util.maths.MCMath;
import net.modificationstation.stationapi.api.block.BlockState;

import java.util.Random;
import java.util.function.Supplier;

public class NewOreStructure extends Structure {
    private Supplier<Block> block;
    private int size;

    public NewOreStructure(Supplier<Block> block, int size) {
        this.block = block;
        this.size = size;
    }

    public boolean generate(Level level, Random random, int x, int y, int z) {
        int id = this.block.get().id;
        float var6 = random.nextFloat() * 3.1415927F;
        double var7 = (double)((float)(x + 8) + MCMath.sin(var6) * (float)this.size / 8.0F);
        double var9 = (double)((float)(x + 8) - MCMath.sin(var6) * (float)this.size / 8.0F);
        double var11 = (double)((float)(z + 8) + MCMath.cos(var6) * (float)this.size / 8.0F);
        double var13 = (double)((float)(z + 8) - MCMath.cos(var6) * (float)this.size / 8.0F);
        double var15 = (double)(y + random.nextInt(3) + 2);
        double var17 = (double)(y + random.nextInt(3) + 2);

        for(int var19 = 0; var19 <= this.size; ++var19) {
            double var20 = var7 + (var9 - var7) * (double)var19 / (double)this.size;
            double var22 = var15 + (var17 - var15) * (double)var19 / (double)this.size;
            double var24 = var11 + (var13 - var11) * (double)var19 / (double)this.size;
            double var26 = random.nextDouble() * (double)this.size / 16.0;
            double var28 = (double)(MCMath.sin((float)var19 * 3.1415927F / (float)this.size) + 1.0F) * var26 + 1.0;
            double var30 = (double)(MCMath.sin((float)var19 * 3.1415927F / (float)this.size) + 1.0F) * var26 + 1.0;
            int var32 = MCMath.floor(var20 - var28 / 2.0);
            int var33 = MCMath.floor(var22 - var30 / 2.0);
            int var34 = MCMath.floor(var24 - var28 / 2.0);
            int var35 = MCMath.floor(var20 + var28 / 2.0);
            int var36 = MCMath.floor(var22 + var30 / 2.0);
            int var37 = MCMath.floor(var24 + var28 / 2.0);

            for(int var38 = var32; var38 <= var35; ++var38) {
                double var39 = ((double)var38 + 0.5 - var20) / (var28 / 2.0);
                if (var39 * var39 < 1.0) {
                    for(int var41 = var33; var41 <= var36; ++var41) {
                        double var42 = ((double)var41 + 0.5 - var22) / (var30 / 2.0);
                        if (var39 * var39 + var42 * var42 < 1.0) {
                            for(int var44 = var34; var44 <= var37; ++var44) {
                                double var45 = ((double)var44 + 0.5 - var24) / (var28 / 2.0);
                                //System.out.println(var41-64);
                                if (var39 * var39 + var42 * var42 + var45 * var45 < 1.0 && level.getBlockID(var38, var41-64, var44) != 0) {
                                    level.setBlockInChunk(var38, var41-64, var44, id);
                                }
                            }
                        }
                    }
                }
            }
        }

        return true;
    }
}
