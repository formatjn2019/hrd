package hrd.modle;


import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ChessmanWithCoordinate implements Comparable<ChessmanWithCoordinate>{
    private final Chessman chessman;
    private final Corrdinate coordinate;
    private static final Map<Chessman,Map<Corrdinate,ChessmanWithCoordinate>> chessmanCashe = new EnumMap<>(Chessman.class);

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
        return ( i << 24 | (coordinate.hashCode() & 0xFFFF) <<8 | (chessman.getId() & 0xFF ));
    }

    public static ChessmanWithCoordinate getInstance(Chessman chessman, Corrdinate corrdinate){
        Map<Corrdinate, ChessmanWithCoordinate> corrdinateMap = chessmanCashe.get(chessman);
        ChessmanWithCoordinate chessmanWithCoordinate;
        if (corrdinateMap  == null){
            corrdinateMap = new HashMap<>();
            chessmanCashe.put(chessman,corrdinateMap);
            chessmanWithCoordinate = new ChessmanWithCoordinate(chessman,corrdinate);
            corrdinateMap.put(corrdinate,chessmanWithCoordinate);
            return chessmanWithCoordinate;
        }
        chessmanWithCoordinate = corrdinateMap.get(corrdinate);
        if (chessmanWithCoordinate == null){
            chessmanWithCoordinate = new ChessmanWithCoordinate(chessman,corrdinate);
            corrdinateMap.put(corrdinate,chessmanWithCoordinate);
        }

        return chessmanWithCoordinate;
    }





    private ChessmanWithCoordinate(Chessman chessman, Corrdinate coordinate) {
        this.chessman = chessman;
        this.coordinate = coordinate;
    }

    public byte getXcoordinate(){
        return coordinate.getX_coordinate();
    }
    public byte getYcoordinate(){
        return coordinate.getY_coordinate();
    }
    public byte getWidth(){
        return this.chessman.getType().getWidth();
    }
    public byte getHeight(){
        return this.chessman.getType().getHeight();
    }
    public char getId(){
        return this.chessman.getId();
    }
    public Chessman getChessman(){
        return this.chessman;
    }

    //将棋子移动，产生新坐标的引用
    public ChessmanWithCoordinate movedStep(Step step){
        return getInstance(chessman,coordinate.moveStep(step));
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
        return Integer.compare(this.hashCode(),o.hashCode());
    }
}
