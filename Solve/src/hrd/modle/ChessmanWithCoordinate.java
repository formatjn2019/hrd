package hrd.modle;


public class ChessmanWithCoordinate implements Comparable<ChessmanWithCoordinate> {
    private final Chessman chessman;
    private final Corrdinate coordinate;

    @Override
    public int hashCode() {
        return (chessman.getType().ordinal() << 24 | (coordinate.hashCode() & 0xFFFF) << 8 | (chessman.getId() & 0xFF));
    }


    private static final ChessmanWithCoordinate[][][] ALL_CHESSMANWITHCORRDINATES = initAllChessmanWithCorrdinate();

    private static ChessmanWithCoordinate[][][] initAllChessmanWithCorrdinate() {
        ChessmanWithCoordinate[][][] result = new ChessmanWithCoordinate[Corrdinate.X_MAX_VALUE][][];
        for (int i = 0; i < Corrdinate.X_MAX_VALUE; i++) {
            result[i] = new ChessmanWithCoordinate[Corrdinate.Y_MAX_VALUE][];
            for (int j = 0; j < Corrdinate.Y_MAX_VALUE; j++) {
                result[i][j] = new ChessmanWithCoordinate[Chessman.values().length];
                for (int k = 0; k < Chessman.values().length; k++) {
                    result[i][j][k] = new ChessmanWithCoordinate(Chessman.values()[k], Corrdinate.getInstance(i, j));
                }
            }
        }
        return result;
    }

    /**
     * 获取缓存的棋子或者生成新的棋子
     *
     * @param chessman   棋子类型
     * @param corrdinate 坐标
     * @return 返回缓存的棋子
     */
    public static ChessmanWithCoordinate getInstance(Chessman chessman, Corrdinate corrdinate) {
        return ALL_CHESSMANWITHCORRDINATES[corrdinate.getX_coordinate()][corrdinate.getY_coordinate()][chessman.ordinal()];
    }

    private ChessmanWithCoordinate(Chessman chessman, Corrdinate coordinate) {
        this.chessman = chessman;
        this.coordinate = coordinate;
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
