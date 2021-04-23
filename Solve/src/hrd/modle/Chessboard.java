package hrd.modle;

import java.util.*;

import static hrd.modle.Chessman.*;

public class Chessboard {
    private long state = 0L;
    private long mirror = 0L;
    private long adjectiveMirror = 0L;
    private final Map<Chessman, ChessmanWithCoordinate> chessmans;


    /**
     * 采用棋子数组初始化
     * 用于新步骤的生成或者手动棋局的生成
     *
     * @param chessmanMap 棋子字典，采用EnumMap
     */
    public Chessboard(Map<Chessman, ChessmanWithCoordinate> chessmanMap) {
        chessmans = chessmanMap;
    }


    /**
     * 采用长整型初始化
     * 方便移动和存储
     * 用于已有棋局的加载
     *
     * @param chessmanMap 空的棋子字典
     * @param state       状态
     */
    public Chessboard(Map<Chessman, ChessmanWithCoordinate> chessmanMap, long state) {
        this.state = state;
        chessmans = chessmanMap;
        long temp = state;
        byte x, y;
        long t1 = 0x1L, t2 = 0x3L, t3 = 0x7L;
        boolean[] types = new boolean[10];
        for (int i = 5; i > 0; i--) {
            types[i] = (temp & t1) == 1;
            temp >>= 1;
        }
        Chessman cm;
        for (char i = 'j'; i >= 'a'; i--) {
            y = (byte) (temp & t3);
            temp >>= 3;
            x = (byte) (temp & t2);
            temp >>= 2;
            cm = Chessman.getInstanceByID(i, types[i - 'a'] ? ChessmanType.HENG : ChessmanType.SHU);
            ChessmanWithCoordinate chessman = ChessmanWithCoordinate.getInstance(cm, Corrdinate.getInstance(x, y));
            this.chessmans.put(cm, chessman);
        }
    }

    public Map<Chessman, ChessmanWithCoordinate> getChessmans() {
        return chessmans;
    }

    //使用映像 速度明显加快很多不止百倍
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chessboard that = (Chessboard) o;
        return (this.getMirror() == that.getMirror()) || (this.getAdjectiveMirror() == that.getMirror());
    }

    //重写hashcode方法但不一定完全散列
    @Override
    public int hashCode() {
        return (int) (this.getMirror() >> 32 ^ this.getMirror());
    }

    private ChessmanWithCoordinate getGuanYu() {
        return chessmans.get(关羽1) == null ? chessmans.get(关羽2) : chessmans.get(关羽1);
    }

    private ChessmanWithCoordinate getZhangFei() {
        return chessmans.get(张飞1) == null ? chessmans.get(张飞2) : chessmans.get(张飞1);
    }

    private ChessmanWithCoordinate getZhaoYun() {
        return chessmans.get(赵云1) == null ? chessmans.get(赵云2) : chessmans.get(赵云1);
    }

    private ChessmanWithCoordinate getMaChao() {
        return chessmans.get(马超1) == null ? chessmans.get(马超2) : chessmans.get(马超1);
    }

    private ChessmanWithCoordinate getHuangZhong() {
        return chessmans.get(黄忠1) == null ? chessmans.get(黄忠2) : chessmans.get(黄忠1);
    }


    /**
     * 获取棋局状态
     */
    public long getState() {
        if (this.state == 0) {
            long temp = 0;
            byte x, y;
            for (ChessmanWithCoordinate chessman : chessmans.values()) {
                x = chessman.getXcoordinate();
                y = chessman.getYcoordinate();
                temp = temp << 5 | x << 3 | y;
            }
            temp = temp << 1 | (getGuanYu().getChessman().getType() == ChessmanType.HENG ? 1 : 0);
            temp = temp << 1 | (getZhangFei().getChessman().getType() == ChessmanType.HENG ? 1 : 0);
            temp = temp << 1 | (getZhaoYun().getChessman().getType() == ChessmanType.HENG ? 1 : 0);
            temp = temp << 1 | (getMaChao().getChessman().getType() == ChessmanType.HENG ? 1 : 0);
            temp = temp << 1 | (getHuangZhong().getChessman().getType() == ChessmanType.HENG ? 1 : 0);
            this.state = temp;
        }
        return state;
    }

    /**
     * 计算映像
     */
    public long calculateMirror(Map<Chessman, ChessmanWithCoordinate> chessmanMap) {
        long temp = 0;
        Collection<ChessmanWithCoordinate> values = chessmanMap.values();
        ArrayList<ChessmanWithCoordinate> list = new ArrayList<>(values);
        Collections.sort(list);
        for (ChessmanWithCoordinate chessman : list) {
            temp = temp << 5 | chessman.getXcoordinate() << 3 | chessman.getYcoordinate();
        }
        return temp;
    }


    /**
     * 计算棋局镜像
     */
    public long getMirror() {
        if (this.mirror == 0) {
            //计算镜像
            this.mirror = calculateMirror(this.chessmans);
        }
        return this.mirror;
    }


    /**
     * 计算对称棋局的镜像
     *
     * @return 对称棋局的镜像
     */
    public long getAdjectiveMirror() {
        if (this.adjectiveMirror == 0) {
            //计算对称镜像
            EnumMap<Chessman, ChessmanWithCoordinate> enumMap = new EnumMap<>(Chessman.class);
            for (ChessmanWithCoordinate chessman : chessmans.values()) {
                enumMap.put(chessman.getChessman(),
                        ChessmanWithCoordinate.getInstance(chessman.getChessman(),
                                Corrdinate.getInstance((byte) (4 - chessman.getWidth() - chessman.getXcoordinate()), chessman.getYcoordinate())));
            }
            this.adjectiveMirror = calculateMirror(enumMap);
        }
        return this.adjectiveMirror;
    }

    @Override
    public String toString() {
        char[][] arr = new char[5][4];
        for (ChessmanWithCoordinate chessman : chessmans.values()) {
            for (int i = chessman.getYcoordinate(); i < chessman.getYcoordinate() + chessman.getHeight(); i++) {
                for (int j = chessman.getXcoordinate(); j < chessman.getXcoordinate() + chessman.getWidth(); j++) {
                    arr[i][j] = chessman.getId();
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        String lines = "---------------------\n";
        sb.append(lines);
        for (char[] line : arr) {
            sb.append('|');
            sb.append('\t');
            for (char c : line) {
                sb.append(c);
                sb.append('\t');
            }
            sb.append('|');
            sb.append('\n');
        }
        sb.append(lines);

        return "Chessboard{" +
                "status=" + getState() +
                ", mirror=" + getMirror() +
                ", adjectiveMirror=" + getAdjectiveMirror() +
                '\n' +
                sb.toString() +
                '}'
                + "\n";
    }
}
