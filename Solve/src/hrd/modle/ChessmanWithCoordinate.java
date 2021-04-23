package hrd.modle;


import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ChessmanWithCoordinate implements Comparable<ChessmanWithCoordinate> {
    private final Chessman chessman;
    private final Corrdinate coordinate;
    private static final Map<Chessman, Map<Corrdinate, ChessmanWithCoordinate>> chessmanCashe = new EnumMap<>(Chessman.class);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessmanWithCoordinate that = (ChessmanWithCoordinate) o;
        return chessman == that.chessman &&
                Objects.equals(coordinate, that.coordinate);
    }

    @Override
    public int hashCode() {
        int i = switch (chessman.getType()) {

            case CAO -> 0;
            case HENG -> 2;
            case SHU -> 1;
            case BING -> 3;
        };
        return (i << 24 | (coordinate.hashCode() & 0xFFFF) << 8 | (chessman.getId() & 0xFF));
    }


    private static final ChessmanWithCoordinate [][][] ALL_CHESSMANWITHCORRDINATES=initAllChessmanWithCorrdinate(Chessman.values().length,Corrdinate.X_MAX_VALUE,Corrdinate.Y_MAX_VALUE);

    private static ChessmanWithCoordinate[][][] initAllChessmanWithCorrdinate(int chssmanSize,int corrdinateXMax,int corrdinateYmax) {
        ChessmanWithCoordinate[][][] result = new ChessmanWithCoordinate[chssmanSize][corrdinateXMax][corrdinateYmax];
        for (int i =0;i<chssmanSize;i++){
            for (int j=0;j<corrdinateXMax;j++){
                for (int k=0;k<corrdinateYmax;k++){
                    result[i][j][k]=new ChessmanWithCoordinate(Chessman.values()[i],Corrdinate.getInstance(j,k));
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
        return ALL_CHESSMANWITHCORRDINATES[chessman.ordinal()][corrdinate.getX_coordinate()][corrdinate.getY_coordinate()];
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
        return getInstance(chessman, coordinate.moveStep(step));
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
