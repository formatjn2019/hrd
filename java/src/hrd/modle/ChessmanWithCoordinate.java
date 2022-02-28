package hrd.modle;


public class ChessmanWithCoordinate implements Comparable<ChessmanWithCoordinate> {
    //为了性能优化，预先生成所有带坐标的棋子放入三维数组中缓存
    private static final ChessmanWithCoordinate[][][] ALL_CHESSMAN_WITH_COORDINATES = initAllChessmanWithCoordinate();
    private final Chessman chessman;
    private final Coordinate coordinate;


    private ChessmanWithCoordinate(Chessman chessman, Coordinate coordinate) {
        this.chessman = chessman;
        this.coordinate = coordinate;
    }

    /**
     * 计算所有包含坐标的棋子
     *
     * @return 返回分别以横坐标，纵坐标，棋子索引为索引的三维数组
     */
    private static ChessmanWithCoordinate[][][] initAllChessmanWithCoordinate() {
        ChessmanWithCoordinate[][][] result = new ChessmanWithCoordinate[Coordinate.X_MAX_VALUE][][];
        for (int i = 0; i < Coordinate.X_MAX_VALUE; i++) {
            result[i] = new ChessmanWithCoordinate[Coordinate.Y_MAX_VALUE][];
            for (int j = 0; j < Coordinate.Y_MAX_VALUE; j++) {
                result[i][j] = new ChessmanWithCoordinate[Chessman.values().length];
                for (int k = 0; k < Chessman.values().length; k++) {
                    result[i][j][k] = new ChessmanWithCoordinate(Chessman.values()[k], Coordinate.getInstance(i, j));
                }
            }
        }
        return result;
    }

    /**
     * 获取缓存的棋子或者生成新的棋子
     *
     * @param chessman   棋子类型
     * @param coordinate 坐标
     * @return 返回缓存的棋子
     */
    public static ChessmanWithCoordinate getInstance(Chessman chessman, Coordinate coordinate) {
        return ALL_CHESSMAN_WITH_COORDINATES[coordinate.getX_coordinate()][coordinate.getY_coordinate()][chessman.ordinal()];
    }

    @Override
    public int hashCode() {
        return (chessman.getType().ordinal() << 24 | (coordinate.hashCode() & 0xFFFF) << 8 | (chessman.ordinal() & 0xFF));
    }

    public byte getXcoordinate() {
        return coordinate.getX_coordinate();
    }

    public byte getYcoordinate() {
        return coordinate.getY_coordinate();
    }

    public byte getWidth() {
        return this.chessman.getType().getWidth();
    }

    public byte getHeight() {
        return this.chessman.getType().getHeight();
    }

    public char getId() {
        return this.chessman.getId();
    }

    public Chessman getChessman() {
        return this.chessman;
    }

    //将棋子移动，产生新坐标的引用
    public ChessmanWithCoordinate movedStep(Step step) {
        return getInstance(chessman, step.moveStep(coordinate));
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "chessman=" + chessman +
                ", coordinate=" + coordinate +
                '}';
    }


    @Override
    public int compareTo(ChessmanWithCoordinate o) {
        return Integer.compare(this.hashCode(), o.hashCode());
    }
}
