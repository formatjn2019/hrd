package hrdRewrite.Modle;

import java.util.HashMap;

public class Corrdinate {
    private static final HashMap<Short,Corrdinate> coordinateCashe = new HashMap<>();
    private int hashCode=Integer.MAX_VALUE;
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

    public Corrdinate moveStep(Step step){
        return switch (step.getDir()) {
            case UP -> getInstance(this.x_coordinate, (byte) (this.y_coordinate - step.getLen()));
            case DOWN -> getInstance(this.x_coordinate, (byte) (this.y_coordinate + step.getLen()));
            case LEFT -> getInstance((byte) (this.x_coordinate-step.getLen()), this.y_coordinate);
            case RIGHT -> getInstance((byte) (this.x_coordinate+step.getLen()), this.y_coordinate);
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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Corrdinate that = (Corrdinate) o;
        return x_coordinate == that.x_coordinate &&
                y_coordinate == that.y_coordinate;
    }

    @Override
    public int hashCode() {
        if (this.hashCode == Integer.MAX_VALUE ){
            this.hashCode = x_coordinate << 8 | (y_coordinate & 0xFF);
        }
        return this.hashCode;
    }

    @Override
    public String toString() {
        return "ChessmanCoordinate{" +
                "x_coordinate=" + x_coordinate +
                ", y_coordinate=" + y_coordinate +
                '}';
    }

}
