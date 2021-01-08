package hrd.io;

import hrd.modle.ChessboardItem;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * 常量类，不可实例化
 */
public class IOConstantUtils {
    //全部棋局文件名
    public static final String FILE_NAME="chessboards.csv";
    public static final String FILE_PATH=".";
    public static final String CHARSET="GBK";
    //缓存棋局文件名
    public static final String CASHE_NAME="mirrorCache.txt";
    public static final Path LOCAL_PATH = Paths.get(FILE_PATH);

    private static final Map<Long, ChessboardItem> chessboardItemMap=new HashMap<>();
    private IOConstantUtils(){}
    public static Map<Long, ChessboardItem> getChessboardItemMap() {
        return chessboardItemMap;
    }
}