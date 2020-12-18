package hrdRewrite.Modle;

import static hrdRewrite.Modle.Chessman.ChessmanType.*;

public enum Chessman {
    曹操('a',CAO),
    关羽('b',GUAN),
    张飞('c',SHANGJIANG),
    赵云('d',SHANGJIANG),
    马超('e',SHANGJIANG),
    黄忠('f',SHANGJIANG),
    兵1('g',BING),
    兵2('h',BING),
    兵3('i',BING),
    兵4('j',BING);

    enum ChessmanType{
        CAO(2, 2),
        GUAN(2, 1),
        SHANGJIANG(1, 2),
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
    public static Chessman getInstanceByID(char id) {
        return switch (id) {
            case 'a' -> 曹操;
            case 'b' -> 关羽;
            case 'c' -> 张飞;
            case 'd' -> 赵云;
            case 'e' -> 马超;
            case 'f' -> 黄忠;
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