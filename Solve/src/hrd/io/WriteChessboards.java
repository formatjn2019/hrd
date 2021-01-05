package hrd.io;

import hrd.modle.ChessboardItem;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import static hrd.io.IOConstantUtils.*;

public class WriteChessboards {

    private static final WriteChessboards instance=new WriteChessboards();
    private final Path path;

    private WriteChessboards(){
        path = Paths.get(FILE_PATH + File.separator+ FILE_NAME);
    }


    //返回实例
    public static WriteChessboards getInstance(){
        return instance;
    }

    public void writeChessbards(){
        Map<Long, ChessboardItem> chessboardItemMap = IOConstantUtils.getChessboardItemMap();
        try(BufferedWriter bw=Files.newBufferedWriter(path, Charset.forName(CHARSET));) {
            bw.write(ChessboardItem.getHeads());
            bw.newLine();
            for(ChessboardItem chessboardItem : chessboardItemMap.values()){
                bw.write(chessboardItem.toString());
                bw.newLine();
            }
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
