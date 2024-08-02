package sh.talonfloof.mctalonfied.util;

public class BooleanArray3D {
    private final boolean[] array;
    public final int width, length, height;
    public BooleanArray3D(int width, int height, int length) {
        this(null, width, height, length);
    }

    public BooleanArray3D(boolean[] array, int width, int height, int length) {
        if (array == null) {
            array = new boolean[width * height * length];
        } else if (array.length < width * height * length) {
            throw new IllegalArgumentException("Array is too small!");
        }
        this.array = array;
        this.width = width;
        this.height = height;
        this.length = length;
    }
    public boolean get(int x, int y, int z) {
        return this.array[(x * this.length + z) * this.height + y];
    }
    public void set(int x, int y, int z, boolean value) {
        this.array[(x * this.length + z) * this.height + y] = value;
    }
    public boolean[] getArray() {
        return array;
    }
}