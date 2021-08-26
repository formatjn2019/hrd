package hrd.modle;

import java.util.*;

import static hrd.modle.Chessman.*;

public class Chessboard {
    private final Map<Chessman, ChessmanWithCoordinate> chessmanMap;
    private long state = 0L;
    private long mirror = 0L;
    private long adjectiveMirror = 0L;


    /**
     * 采用棋子数组初始化
     * 用于新步骤的生成或者手动棋局的生成
     *
     * @param chessmanMap 棋子字典，采用EnumMap
     */
    public Chessboard(Map<Chessman, ChessmanWithCoordinate> chessmanMap) {
        this.chessmanMap = chessmanMap;
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
        this.chessmanMap = chessmanMap;
        long tempState = state;
        int x, y;
        long t1 = 0x1L, t2 = 0x3L, t3 = 0x7L;
        boolean[] types = new boolean[10];
        for (int i = 5; i > 0; i--) {
            types[i] = (tempState & t1) == 1;
            tempState >>= 1;
        }
        for (char i = 'j'; i >= 'a'; i--) {
            y = (int) (tempState & t3);
            tempState >>= 3;
            x = (int) (tempState & t2);
            tempState >>= 2;
            Chessman tempChessman = Chessman.getInstanceByID(i, types[i - 'a'] ? ChessmanType.HORIZONTAL : ChessmanType.VERTICAL);
            ChessmanWithCoordinate chessman = ChessmanWithCoordinate.getInstance(tempChessman, Coordinate.getInstance(x, y));
            this.chessmanMap.put(tempChessman, chessman);
        }
    }

    public Map<Chessman, ChessmanWithCoordinate> getChessmanMap() {
        return chessmanMap;
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
        return chessmanMap.get(GUAN_YU_HORIZONTAL) == null ? chessmanMap.get(GUAN_YU_VERTICAL) : chessmanMap.get(GUAN_YU_HORIZONTAL);
    }

    private ChessmanWithCoordinate getZhangFei() {
        return chessmanMap.get(ZHANG_FEI_HORIZONTAL) == null ? chessmanMap.get(ZHANG_FEI_VERTICAL) : chessmanMap.get(ZHANG_FEI_HORIZONTAL);
    }

    private ChessmanWithCoordinate getZhaoYun() {
        return chessmanMap.get(ZHAO_YUN_HORIZONTAL) == null ? chessmanMap.get(ZHAO_YUN_VERTICAL) : chessmanMap.get(ZHAO_YUN_HORIZONTAL);
    }

    private ChessmanWithCoordinate getMaChao() {
        return chessmanMap.get(MA_CHAO_HORIZONTAL) == null ? chessmanMap.get(MA_CHAO_VERTICAL) : chessmanMap.get(MA_CHAO_HORIZONTAL);
    }

    private ChessmanWithCoordinate getHuangZhong() {
        return chessmanMap.get(HUANG_ZHONG_HORIZONTAL) == null ? chessmanMap.get(HUANG_ZHONG_VERTICAL) : chessmanMap.get(HUANG_ZHONG_HORIZONTAL);
    }


    /**
     * 获取棋局状态
     */
    public long getState() {
        if (this.state != 0) {
            return state;
        }
        long temp = 0;
        for (ChessmanWithCoordinate chessman : chessmanMap.values()) {
            temp = temp << 5 | chessman.getXcoordinate() << 3 | chessman.getYcoordinate();
        }
        temp = temp << 1 | (getGuanYu().getChessman().getType() == ChessmanType.HORIZONTAL ? 1 : 0);
        temp = temp << 1 | (getZhangFei().getChessman().getType() == ChessmanType.HORIZONTAL ? 1 : 0);
        temp = temp << 1 | (getZhaoYun().getChessman().getType() == ChessmanType.HORIZONTAL ? 1 : 0);
        temp = temp << 1 | (getMaChao().getChessman().getType() == ChessmanType.HORIZONTAL ? 1 : 0);
        temp = temp << 1 | (getHuangZhong().getChessman().getType() == ChessmanType.HORIZONTAL ? 1 : 0);
        return this.state = temp;

    }

    /**
     * 计算映像
     */
    public long calculateMirror(Map<Chessman, ChessmanWithCoordinate> chessmanMap) {
        long result = 0;
        Collection<ChessmanWithCoordinate> values = chessmanMap.values();
        ArrayList<ChessmanWithCoordinate> list = new ArrayList<>(values);
        Collections.sort(list);
        for (ChessmanWithCoordinate chessman : list) {
            result = result << 5 | chessman.getXcoordinate() << 3 | chessman.getYcoordinate();
        }
        return result;
    }


    /**
     * 计算棋局镜像
     */
    public long getMirror() {
        if (this.mirror != 0) {
            return this.mirror;
        }
        //计算镜像
        return this.mirror = calculateMirror(this.chessmanMap);
    }


    /**
     * 计算对称棋局的镜像
     *
     * @return 对称棋局的镜像
     */
    public long getAdjectiveMirror() {
        if (this.adjectiveMirror != 0) {
            return this.adjectiveMirror;
        }

        //计算对称镜像
        EnumMap<Chessman, ChessmanWithCoordinate> enumMap = new EnumMap<>(Chessman.class);
        for (ChessmanWithCoordinate chessman : chessmanMap.values()) {
            enumMap.put(chessman.getChessman(),
                    ChessmanWithCoordinate.getInstance(chessman.getChessman(),
                            Coordinate.getInstance(4 - chessman.getWidth() - chessman.getXcoordinate(), chessman.getYcoordinate())));
        }

        return this.adjectiveMirror = calculateMirror(enumMap);
    }

    @Override
    public String toString() {
        char[][] arr = new char[5][4];
        for (ChessmanWithCoordinate chessman : chessmanMap.values()) {
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
                sb +
                '}'
                + "\n";
    }
}
