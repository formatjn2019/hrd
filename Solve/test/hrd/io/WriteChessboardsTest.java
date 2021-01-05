package hrd.io;

import org.junit.jupiter.api.Test;

class WriteChessboardsTest {

    static WriteChessboards writeChessboards = WriteChessboards.getInstance();

    @Test
    void writeChessbards() {
        writeChessboards.writeChessbards();
    }
}