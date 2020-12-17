package hrdRewrite.Modle;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChessboardTest {
    static Chessboard chessboard=null;
    static Chessboard chessboard2=null;
    @BeforeAll
    static void init(){
        ChessmanWithCoordinate caocao=ChessmanWithCoordinate.getInstance(Chessman.曹操,Corrdinate.getInstance((byte) 1,(byte) 0));
        ChessmanWithCoordinate guanyu=ChessmanWithCoordinate.getInstance(Chessman.关羽,Corrdinate.getInstance((byte) 1,(byte) 2));
        ChessmanWithCoordinate zhangfei=ChessmanWithCoordinate.getInstance(Chessman.张飞,Corrdinate.getInstance((byte) 0,(byte) 0));
        ChessmanWithCoordinate zhaoyun=ChessmanWithCoordinate.getInstance(Chessman.赵云,Corrdinate.getInstance((byte) 3,(byte) 0));
        ChessmanWithCoordinate macao=ChessmanWithCoordinate.getInstance(Chessman.马超,Corrdinate.getInstance((byte) 0,(byte) 2));
        ChessmanWithCoordinate huangzhong=ChessmanWithCoordinate.getInstance(Chessman.黄忠,Corrdinate.getInstance((byte) 3,(byte) 2));
        ChessmanWithCoordinate bing1=ChessmanWithCoordinate.getInstance(Chessman.兵1,Corrdinate.getInstance((byte) 0,(byte) 4));
        ChessmanWithCoordinate bing2=ChessmanWithCoordinate.getInstance(Chessman.兵2,Corrdinate.getInstance((byte) 1,(byte) 4));
        ChessmanWithCoordinate bing3=ChessmanWithCoordinate.getInstance(Chessman.兵3,Corrdinate.getInstance((byte) 2,(byte) 4));
        ChessmanWithCoordinate bing4=ChessmanWithCoordinate.getInstance(Chessman.兵4,Corrdinate.getInstance((byte) 3,(byte) 4));
        ChessmanWithCoordinate chessmans[]={caocao,guanyu,zhangfei,zhaoyun,macao,huangzhong,bing1,bing2,bing3,bing4};
        chessboard=new Chessboard(chessmans);
        chessboard2 = new Chessboard(9486557393564L);
    }
    @Test
    void testEquals() {
    }

    @Test
    void testHashCode() {
    }

    @Test
    void getStatus() {
        long start = System.currentTimeMillis();
        System.out.println(chessboard.getStatus());
        System.out.println(System.currentTimeMillis()-start);
    }

    @Test
    void getMirror() {
    }


    @Test
    void testToString() {
        System.out.println(chessboard);
        System.out.println(chessboard2);
    }
}