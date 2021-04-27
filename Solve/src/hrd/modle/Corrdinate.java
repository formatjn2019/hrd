package hrd.modle;

public class Corrdinate {

    //通过静态数组生成减少判断时间
    public static final int X_MAX_VALUE = 4;
    public static final int Y_MAX_VALUE = 5;
    private static final Corrdinate[][] ALL_CORRDINATES = initAllCorrdinates();

    private static Corrdinate[][] initAllCorrdinates() {
        Corrdinate[][] result = new Corrdinate[Corrdinate.X_MAX_VALUE][];
        for (int i = 0; i < X_MAX_VALUE; i++) {
            result[i] = new Corrdinate[Corrdinate.Y_MAX_VALUE];
            for (int j = 0; j < Y_MAX_VALUE; j++) {
                result[i][j] = new Corrdinate(i, j);
            }
        }
        return result;
    }

    /**
     * 减少对象的生成，缓存已经生成的对象
     *
     * @param x 横坐标
     * @param y 纵坐标
     * @return 返回已生成的坐标
     */
    public static Corrdinate getInstance(int x, int y) {
        return ALL_CORRDINATES[x][y];
    }


    private final byte x_coordinate;
    private final byte y_coordinate;

    private Corrdinate(int x_coordinate, int y_coordinate) {
        this.x_coordinate = (byte) x_coordinate;
        this.y_coordinate = (byte) y_coordinate;
    }


    public byte getX_coordinate() {
        return x_coordinate;
    }

    public byte getY_coordinate() {
        return y_coordinate;
    }

    @Override
    public boolean equals(Object o) {
        return this == o;
    }

    @Override
    public int hashCode() {
        return x_coordinate << 8 | (y_coordinate & 0xFF);
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "x_coordinate=" + x_coordinate +
                ", y_coordinate=" + y_coordinate +
                '}';
    }

}
