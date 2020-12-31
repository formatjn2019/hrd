package hrdRewrite.modle;

import static hrdRewrite.modle.Chessman.ChessmanType.*;

public enum Chessman {
    曹操('a',CAO),
    关羽1('b',HENG),
    关羽2('b',SHU),
    张飞1('c',HENG),
    张飞2('c',SHU),
    赵云1('d',HENG),
    赵云2('d',SHU),
    马超1('e',HENG),
    马超2('e',SHU),
    黄忠1('f',HENG),
    黄忠2('f',SHU),
    兵1('g',BING),
    兵2('h',BING),
    兵3('i',BING),
    兵4('j',BING);


    public enum ChessmanType{
        CAO(2, 2),
        HENG(2, 1),
        SHU(1, 2),
        BING(1, 1);
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
    public static Chessman getInstanceByID(char id,ChessmanType chessmanType) {
        return switch (id) {
            case 'a' -> 曹操;
            case 'b' -> chessmanType == HENG ? 关羽1:关羽2;
            case 'c' ->  chessmanType == HENG ? 张飞1:张飞2;
            case 'd' ->  chessmanType == HENG ? 赵云1:赵云2;
            case 'e' ->  chessmanType == HENG ? 马超1:马超2;
            case 'f' ->  chessmanType == HENG ? 黄忠1:黄忠2;
            case 'g' -> 兵1;
            case 'h' -> 兵2;
            case 'i' -> 兵3;
            case 'j' -> 兵4;
            default -> null;
        };
    }


    private final char id;
    private final ChessmanType type;
    Chessman(char id, ChessmanType type) {
        this.id = id;
        this.type = type;
    }

    public char getId() {
        return id;
    }
    public ChessmanType getType() {
        return type;
    }
}