package hrd.io;

import hrd.modle.ChessboardItem;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Collection;

class ReadChessboardsTest {


    @Test
    void readChessboard() {
        ReadChessboards.readChessboard();
        System.out.println(IOConstantUtils.getChessboardItemMap().size());
        for (ChessboardItem chessboardItem :IOConstantUtils.getChessboardItemMap().values()){
            System.out.println(chessboardItem);
        }
    }
    @Test
    void readMiiros() {
        System.out.println(ReadChessboards.readMirrors());
    }

}