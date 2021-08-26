package hrd.modle;

import static hrd.modle.Chessman.ChessmanType.*;

public enum Chessman {
    CAO_CAO('a', BIG),
    GUAN_YU_HORIZONTAL('b', HORIZONTAL),
    GUAN_YU_VERTICAL('b', VERTICAL),
    ZHANG_FEI_HORIZONTAL('c', HORIZONTAL),
    ZHANG_FEI_VERTICAL('c', VERTICAL),
    ZHAO_YUN_HORIZONTAL('d', HORIZONTAL),
    ZHAO_YUN_VERTICAL('d', VERTICAL),
    MA_CHAO_HORIZONTAL('e', HORIZONTAL),
    MA_CHAO_VERTICAL('e', VERTICAL),
    HUANG_ZHONG_HORIZONTAL('f', HORIZONTAL),
    HUANG_ZHONG_VERTICAL('f', VERTICAL),
    BING1('g', SMALL),
    BING2('h', SMALL),
    BING3('i', SMALL),
    BING4('j', SMALL);


    private final char id;
    private final ChessmanType type;

    /**
     * 棋局类型
     *
     * @param id   id 唯一表示，可用于棋局显示
     * @param type 类型
     */
    Chessman(char id, ChessmanType type) {
        this.id = id;
        this.type = type;
    }

    /**
     * 根据id和类型获取棋子
     *
     * @param id           id
     * @param chessmanType 棋子类型
     * @return 枚举类型的棋子
     */
    public static Chessman getInstanceByID(char id, ChessmanType chessmanType) {
        return switch (id) {
            case 'a' -> CAO_CAO;
            case 'b' -> chessmanType == HORIZONTAL ? GUAN_YU_HORIZONTAL : GUAN_YU_VERTICAL;
            case 'c' -> chessmanType == HORIZONTAL ? ZHANG_FEI_HORIZONTAL : ZHANG_FEI_VERTICAL;
            case 'd' -> chessmanType == HORIZONTAL ? ZHAO_YUN_HORIZONTAL : ZHAO_YUN_VERTICAL;
            case 'e' -> chessmanType == HORIZONTAL ? MA_CHAO_HORIZONTAL : MA_CHAO_VERTICAL;
            case 'f' -> chessmanType == HORIZONTAL ? HUANG_ZHONG_HORIZONTAL : HUANG_ZHONG_VERTICAL;
            case 'g' -> BING1;
            case 'h' -> BING2;
            case 'i' -> BING3;
            case 'j' -> BING4;
            default -> null;
        };
    }

    public char getId() {
        return id;
    }

    public ChessmanType getType() {
        return type;
    }

    public enum ChessmanType {
        //大棋子
        BIG(2, 2),
        //横向
        HORIZONTAL(2, 1),
        //纵向
        VERTICAL(1, 2),
        //小棋子
        SMALL(1, 1);
        private final byte width;
        private final byte height;

        ChessmanType(int width, int height) {
            this.width = (byte) width;
            this.height = (byte) height;
        }

        public byte getWidth() {
            return width;
        }

        public byte getHeight() {
            return height;
        }
    }
}