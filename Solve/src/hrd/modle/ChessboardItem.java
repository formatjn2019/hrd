package hrd.modle;

import java.util.EnumMap;

public class ChessboardItem implements Comparable<ChessboardItem>{
    private final Chessboard chessboard;
    private final int depth;
    private final String name;
    private final int type;

    public ChessboardItem(Chessboard chessboard, int depth, String name, int type) {
        this.chessboard = chessboard;
        this.depth = depth;
        this.name = name;
        this.type = type;
    }
    public ChessboardItem(String name, String state, String depth, String type) {
        this.chessboard = new Chessboard(new EnumMap<>(Chessman.class),Long.parseLong(state));
        this.depth = Integer.parseInt(depth);
        this.name = name;
        this.type = Integer.parseInt(type);
    }

    public Chessboard getChessboard() {
        return chessboard;
    }

    public int getDepth() {
        return depth;
    }

    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }

    public static String getHeads(){
        return "名称"+","+"状态"+","+"最优步数"+","+"类型";
    }

    public String getLine() {
        return name+","+chessboard.getState()+","+depth+","+type;
    }

    @Override
    public String toString() {
        return "ChessboardItem{" +
                "chessboard=" + chessboard +
                ", depth=" + depth +
                ", name='" + name + '\'' +
                ", type=" + type +
                '}';
    }

    @Override
    public int compareTo(ChessboardItem that) {
        return this.type == that.type ? Long.compare(this.chessboard.getState(), that.chessboard.getState()) : Integer.compare(this.type, that.type);
    }
}
