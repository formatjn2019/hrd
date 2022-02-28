package hrd.modle;

import java.util.EnumMap;

public class ChessboardItem implements Comparable<ChessboardItem> {
    private final Chessboard chessboard;
    private final int depth;
    private final String name;
    private final int type;

    /**
     * 构造方法，用于写入
     *
     * @param chessboard 棋局
     * @param depth      棋局求解步数
     * @param name       棋局名称
     * @param type       棋局类型
     */
    public ChessboardItem(Chessboard chessboard, int depth, String name, int type) {
        this.chessboard = chessboard;
        this.depth = depth;
        this.name = name;
        this.type = type;
    }

    /**
     * 构造方法，用于读取
     *
     * @param state 棋局状态
     * @param depth 棋局求解步数
     * @param name  棋局名称
     * @param type  棋局类型
     */
    public ChessboardItem(String name, String state, String depth, String type) {
        this.chessboard = new Chessboard(new EnumMap<>(Chessman.class), Long.parseLong(state.replace("L", "")));
        this.depth = Integer.parseInt(depth);
        this.name = name;
        this.type = Integer.parseInt(type);
    }

    /**
     * 生成文本文件头
     *
     * @return 文件头的字符串
     */
    public static String getHeads() {
        return "名称" + "," + "状态" + "," + "最优步数" + "," + "类型";
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

    /**
     * 获取当前类转为行的数据
     *
     * @return 文件行字符串
     */
    public String getLine() {
        return name + "," + chessboard.getState() + "L," + depth + "," + type;
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
