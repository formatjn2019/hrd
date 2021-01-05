package hrd.modle;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.EnumMap;

class ChessboardTest {
    static Chessboard chessboard=null;
    static Chessboard chessboard2=null;
    static Chessboard chessboard3=null;
    @BeforeAll
    static void init(){
        EnumMap<Chessman, ChessmanWithCoordinate> chessmans = new EnumMap<>(Chessman.class);
        ChessmanWithCoordinate caocao= ChessmanWithCoordinate.getInstance(Chessman.曹操, Corrdinate.getInstance((byte) 1,(byte) 0));
        ChessmanWithCoordinate guanyu= ChessmanWithCoordinate.getInstance(Chessman.关羽1, Corrdinate.getInstance((byte) 1,(byte) 2));
        ChessmanWithCoordinate zhangfei= ChessmanWithCoordinate.getInstance(Chessman.张飞2, Corrdinate.getInstance((byte) 0,(byte) 0));
        ChessmanWithCoordinate zhaoyun= ChessmanWithCoordinate.getInstance(Chessman.赵云2, Corrdinate.getInstance((byte) 0,(byte) 2));
        ChessmanWithCoordinate macao= ChessmanWithCoordinate.getInstance(Chessman.马超2, Corrdinate.getInstance((byte) 3,(byte) 0));
        ChessmanWithCoordinate huangzhong= ChessmanWithCoordinate.getInstance(Chessman.黄忠2, Corrdinate.getInstance((byte) 3,(byte) 2));
        ChessmanWithCoordinate bing1= ChessmanWithCoordinate.getInstance(Chessman.兵1, Corrdinate.getInstance((byte) 0,(byte) 4));
        ChessmanWithCoordinate bing2= ChessmanWithCoordinate.getInstance(Chessman.兵2, Corrdinate.getInstance((byte) 1,(byte) 3));
        ChessmanWithCoordinate bing3= ChessmanWithCoordinate.getInstance(Chessman.兵3, Corrdinate.getInstance((byte) 2,(byte) 3));
        ChessmanWithCoordinate bing4= ChessmanWithCoordinate.getInstance(Chessman.兵4, Corrdinate.getInstance((byte) 3,(byte) 4));
        chessmans.put(Chessman.曹操,caocao);
        chessmans.put(Chessman.关羽1,guanyu);
        chessmans.put(Chessman.张飞2,zhangfei);
        chessmans.put(Chessman.赵云2,zhaoyun);
        chessmans.put(Chessman.马超2,macao);
        chessmans.put(Chessman.黄忠2,huangzhong);
        chessmans.put(Chessman.兵1,bing1);
        chessmans.put(Chessman.兵2,bing2);
        chessmans.put(Chessman.兵3,bing3);
        chessmans.put(Chessman.兵4,bing4);
        chessboard=new Chessboard(chessmans);
        chessboard2 = new Chessboard(new EnumMap<>(Chessman.class),9359138341900176L);
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
        System.out.println(chessboard.getState()+"L");
        System.out.println(System.currentTimeMillis()-start);
    }

    @Test
    void getMirror() {
        System.out.println(chessboard.getMirror());
        System.out.println(chessboard.getAdjectiveMirror());
    }


    @Test
    void testToString() {
        System.out.println(chessboard);
        System.out.println(chessboard2);
    }
}