package sh.talonfloof.mctalonfied.util;

public class DoubleArray3D {
    private final double[] array;
    public final int width, length, height;
    public DoubleArray3D(int width, int height, int length) {
        this(null, width, height, length);
    }

    public DoubleArray3D(double[] array, int width, int height, int length) {
        if (array == null) {
            array = new double[width * height * length];
        } else if (array.length < width * height * length) {
            throw new IllegalArgumentException("Array is too small!");
        }
        this.array = array;
        this.width = width;
        this.height = height;
        this.length = length;
    }
    public double get(int x, int y, int z) {
        return this.array[(x * this.length + z) * this.height + y];
    }
    public void set(int x, int y, int z, double value) {
        this.array[(x * this.length + z) * this.height + y] = value;
    }
    public double[] getArray() {
        return array;
    }
}