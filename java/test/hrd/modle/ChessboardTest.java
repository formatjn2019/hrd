package hrd.modle;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.EnumMap;

class ChessboardTest {
    static Chessboard chessboard = null;
    static Chessboard chessboard2 = null;
    static Chessboard chessboard3 = null;

    @BeforeAll
    static void init() {
        EnumMap<Chessman, ChessmanWithCoordinate> chessmans = new EnumMap<>(Chessman.class);
        ChessmanWithCoordinate caocao = ChessmanWithCoordinate.getInstance(Chessman.CAO_CAO, Coordinate.getInstance((byte) 1, (byte) 0));
        ChessmanWithCoordinate guanyu = ChessmanWithCoordinate.getInstance(Chessman.GUAN_YU_HORIZONTAL, Coordinate.getInstance((byte) 1, (byte) 2));
        ChessmanWithCoordinate zhangfei = ChessmanWithCoordinate.getInstance(Chessman.ZHANG_FEI_VERTICAL, Coordinate.getInstance((byte) 0, (byte) 0));
        ChessmanWithCoordinate zhaoyun = ChessmanWithCoordinate.getInstance(Chessman.ZHAO_YUN_VERTICAL, Coordinate.getInstance((byte) 3, (byte) 0));
        ChessmanWithCoordinate macao = ChessmanWithCoordinate.getInstance(Chessman.MA_CHAO_VERTICAL, Coordinate.getInstance((byte) 0, (byte) 2));
        ChessmanWithCoordinate huangzhong = ChessmanWithCoordinate.getInstance(Chessman.HUANG_ZHONG_VERTICAL, Coordinate.getInstance((byte) 3, (byte) 2));
        ChessmanWithCoordinate bing1 = ChessmanWithCoordinate.getInstance(Chessman.BING1, Coordinate.getInstance((byte) 0, (byte) 4));
        ChessmanWithCoordinate bing2 = ChessmanWithCoordinate.getInstance(Chessman.BING2, Coordinate.getInstance((byte) 1, (byte) 4));
        ChessmanWithCoordinate bing3 = ChessmanWithCoordinate.getInstance(Chessman.BING3, Coordinate.getInstance((byte) 2, (byte) 4));
        ChessmanWithCoordinate bing4 = ChessmanWithCoordinate.getInstance(Chessman.BING4, Coordinate.getInstance((byte) 3, (byte) 4));
        chessmans.put(Chessman.CAO_CAO, caocao);
        chessmans.put(Chessman.GUAN_YU_HORIZONTAL, guanyu);
        chessmans.put(Chessman.ZHANG_FEI_VERTICAL, zhangfei);
        chessmans.put(Chessman.ZHAO_YUN_VERTICAL, zhaoyun);
        chessmans.put(Chessman.MA_CHAO_VERTICAL, macao);
        chessmans.put(Chessman.HUANG_ZHONG_VERTICAL, huangzhong);
        chessmans.put(Chessman.BING1, bing1);
        chessmans.put(Chessman.BING2, bing2);
        chessmans.put(Chessman.BING3, bing3);
        chessmans.put(Chessman.BING4, bing4);
        chessboard = new Chessboard(chessmans);
//        chessboard = new Chessboard(new EnumMap<>(Chessman.class),20867641233797200L);
        chessboard2 = new Chessboard(new EnumMap<>(Chessman.class), 18387170901632800L);
//        chessboard2 = new Chessboard(new EnumMap<>(Chessman.class),20275547788314944L);
//        chessboard3 = new Chessboard(new EnumMap<>(Chessman.class), 9486557393564L);
    }

    @Test
    void testEquals() {
        System.out.println(chessboard.equals(chessboard2));
    }

    @Test
    void testHashCode() {
        System.out.println(chessboard.hashCode());
        System.out.println(chessboard2.hashCode());
    }

    @Test
    void getStatus() {
        long start = System.currentTimeMillis();
        System.out.println(chessboard.getState() + "L");
        System.out.println(System.currentTimeMillis() - start);
    }

    @Test
    void getMirror() {
        System.out.println(chessboard.getMirror());
        System.out.println(chessboard.getAdjectiveMirror());
        System.out.println(chessboard2.getMirror());
        System.out.println(chessboard2.getAdjectiveMirror());
    }


    @Test
    void testToString() {
        System.out.println(chessboard);
        System.out.println(chessboard2);
    }
}