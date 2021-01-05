package hrd.io;

import hrd.modle.ChessboardItem;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import static hrd.io.IOConstantUtils.*;

public class ReadChessboards {

    private static final ReadChessboards instance=new ReadChessboards();
    private final Path path;



    private ReadChessboards(){
        path = Paths.get(FILE_PATH + File.separator+ FILE_NAME);
    }


    //返回实例
    public static ReadChessboards getInstance(){
        return instance;
    }
    //读取csv书籍放入缓存
    public void readChessboard(){
        Map<Long, ChessboardItem> chessboardItemMap = IOConstantUtils.getChessboardItemMap();
        try(BufferedReader br =Files.newBufferedReader(path, Charset.forName(CHARSET))) {
            String line = br.readLine();
            while ((line=br.readLine()) != null){
                String[] strings = line.split(",");
                ChessboardItem chessboardItem = new ChessboardItem(strings[0],strings[1],strings[2],strings[3]);
                chessboardItemMap.put(chessboardItem.getChessboard().getState(),chessboardItem);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
