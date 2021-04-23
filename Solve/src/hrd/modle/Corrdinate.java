package hrd.modle;

public class Corrdinate {
    private int hashCode = Integer.MAX_VALUE;

    //通过静态数组生成减少判断时间
    public static final int X_MAX_VALUE=4;
    public static final int Y_MAX_VALUE=5;
    private static final Corrdinate[][] ALL_CORRDINATES =initAllCorrdinates();
    private static Corrdinate [][] initAllCorrdinates(){
        Corrdinate [][]result = new Corrdinate[Corrdinate.X_MAX_VALUE][Corrdinate.Y_MAX_VALUE];
        for (int i = 0; i< X_MAX_VALUE; i++){
            for (int j = 0; j< Y_MAX_VALUE; j++){
                result[i][j]=new Corrdinate(i,j);
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

    /**
     * 移动指定步骤
     * 忽略棋子宽高
     *
     * @param step 步骤
     * @return 返回新的坐标
     */
    public Corrdinate moveStep(Step step) {
        return switch (step.getDir()) {
            case UP -> getInstance(this.x_coordinate, (byte) (this.y_coordinate - step.getLen()));
            case DOWN -> getInstance(this.x_coordinate, (byte) (this.y_coordinate + step.getLen()));
            case LEFT -> getInstance((byte) (this.x_coordinate - step.getLen()), this.y_coordinate);
            case RIGHT -> getInstance((byte) (this.x_coordinate + step.getLen()), this.y_coordinate);
            case UPLEFT -> getInstance((byte) (this.x_coordinate - step.getLen()), (byte) (this.y_coordinate - step.getLen()));
            case UPRIGHT -> getInstance((byte) (this.x_coordinate + step.getLen()), (byte) (this.y_coordinate - step.getLen()));
            case DOWNLEFT -> getInstance((byte) (this.x_coordinate - step.getLen()), (byte) (this.y_coordinate + step.getLen()));
            case DOWNRIGHT -> getInstance((byte) (this.x_coordinate + step.getLen()), (byte) (this.y_coordinate + step.getLen()));
        };
    }

    /**
     * 移动指定步骤
     * 不忽略棋子宽高
     *
     * @param step     步骤
     * @param chessman 棋子
     * @return 返回新的坐标
     */
    public Corrdinate moveStep(Step step, Chessman chessman) {
        return switch (step.getDir()) {
            case UP -> getInstance(this.x_coordinate, (byte) (this.y_coordinate - step.getLen() - (chessman.getType().getHeight() - 1)));
            case DOWN -> getInstance(this.x_coordinate, (byte) (this.y_coordinate + step.getLen() + (chessman.getType().getHeight() - 1)));
            case LEFT -> getInstance((byte) (this.x_coordinate - step.getLen() - (chessman.getType().getWidth() - 1)), this.y_coordinate);
            case RIGHT -> getInstance((byte) (this.x_coordinate + step.getLen() + (chessman.getType().getWidth() - 1)), this.y_coordinate);
            case UPLEFT -> getInstance((byte) (this.x_coordinate - step.getLen()), (byte) (this.y_coordinate - step.getLen()));
            case UPRIGHT -> getInstance((byte) (this.x_coordinate + step.getLen()), (byte) (this.y_coordinate - step.getLen()));
            case DOWNLEFT -> getInstance((byte) (this.x_coordinate - step.getLen()), (byte) (this.y_coordinate + step.getLen()));
            case DOWNRIGHT -> getInstance((byte) (this.x_coordinate + step.getLen()), (byte) (this.y_coordinate + step.getLen()));
            default -> throw new IllegalStateException("Unexpected value: " + step.getDir());
        };
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
        if (this.hashCode == Integer.MAX_VALUE) {
            this.hashCode = x_coordinate << 8 | (y_coordinate & 0xFF);
        }
        return this.hashCode;
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "x_coordinate=" + x_coordinate +
                ", y_coordinate=" + y_coordinate +
                '}';
    }

}
