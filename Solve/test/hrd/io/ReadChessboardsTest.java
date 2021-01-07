package hrd.io;

import hrd.modle.ChessboardItem;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Collection;

class ReadChessboardsTest {

    static ReadChessboards readChessboards = ReadChessboards.getInstance();

    @Test
    void readChessboard() {
        readChessboards.readChessboard();
        System.out.println(IOConstantUtils.getChessboardItemMap().size());
        for (ChessboardItem chessboardItem :IOConstantUtils.getChessboardItemMap().values()){
            System.out.println(chessboardItem);
        }
    }
    @Test
    void readMiiros() {
        System.out.println(readChessboards.readMirrors());
    }

}