package sh.talonfloof.orderofthetalon.util;

public class AdvMathUtil {
    public static void lerp3DPoints(DoubleArray3D src, DoubleArray3D dst, int rh, int rv) {
        double[] srcArr = src.getArray();
        int sw = src.width;
        int sh = src.height;
        int sl = src.length;
        int shl = sh * sl;
        int s2hl = sh * sl + sh;
        for (int x = 0; x < dst.width / rh; x++) {
            for (int y = 0; y < dst.height / rv; y++) {
                for (int z = 0; z < dst.length / rh; z++) {
                    int i = (x * sl + z) * sh + y;
                    double n000 = srcArr[i];
                    double n010 = srcArr[i + 1];
                    double n001 = srcArr[i + sh];
                    double n011 = srcArr[i + sh + 1];
                    double nL00 = srcArr[i + shl] - n000;
                    double nL10 = srcArr[i + shl + 1] - n010;
                    double nL01 = srcArr[i + s2hl] - n001;
                    double nL11 = srcArr[i + s2hl + 1] - n011;
                    for (int lx = 0; lx < rh; lx++) {
                        double lxlerp = lx / (double)rh;
                        double n00 = n000 + nL00 * lxlerp;
                        double n01 = n001 + nL01 * lxlerp;
                        double nL0 = n010 + nL10 * lxlerp - n00;
                        double nL1 = n011 + nL11 * lxlerp - n01;
                        for (int ly = 0; ly < rv; ly++) {
                            double lylerp = ly / (double)rv;
                            double n0 = n00 + nL0 * lylerp;
                            double nL = n01 + nL1 * lylerp - n0;
                            for (int lz = 0; lz < rh; lz++) {
                                double n = n0 + nL * (lz / (double)rh);
                                dst.set(x * rh + lx, y * rv + ly, z * rh + lz, n);
                            }
                        }
                    }
                }
            }
        }
    }
}
