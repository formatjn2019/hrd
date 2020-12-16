package hrdRewrite.Modle;

import java.util.HashMap;

public class Corrdinate {
    private static final HashMap<Short,Corrdinate> coordinateCashe = new HashMap<>();

    //减少对象的生成，缓存已经生成的对象
    public static Corrdinate getInstance(byte x,byte y){
        Short key= (short)(x << 8 | (y & 0xFF));
        Corrdinate corrdinate =coordinateCashe.get(key);
        if (corrdinate == null){
            corrdinate = new Corrdinate(x,y);
            coordinateCashe.put(key,corrdinate);
        }
        return corrdinate;
    }

    private final byte x_coordinate;
    private final byte y_coordinate;
    private Corrdinate(byte x_coordinate, byte y_coordinate) {
        this.x_coordinate =  x_coordinate;
        this.y_coordinate =  y_coordinate;
    }


    public byte getX_coordinate() {
        return x_coordinate;
    }

    public byte getY_coordinate() {
        return y_coordinate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Corrdinate that = (Corrdinate) o;
        return x_coordinate == that.x_coordinate &&
                y_coordinate == that.y_coordinate;
    }

    @Override
    public int hashCode() {
        return x_coordinate << 8 | (y_coordinate & 0xFFFFFF);
    }

    @Override
    public String toString() {
        return "ChessmanCoordinate{" +
                "x_coordinate=" + x_coordinate +
                ", y_coordinate=" + y_coordinate +
                '}';
    }

}
