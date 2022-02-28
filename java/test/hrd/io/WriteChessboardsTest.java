package hrd.io;

import org.junit.jupiter.api.Test;

import java.util.HashSet;

class WriteChessboardsTest {

    static WriteChessboards writeChessboards = WriteChessboards.getInstance();

    @Test
    void writeChessbards() {
        writeChessboards.writeChessbards();
    }

    @Test
    void writeMirrors() {
        writeChessboards.writeMirrors(new HashSet<>());
    }
}