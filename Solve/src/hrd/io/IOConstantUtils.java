package hrd.io;

import hrd.modle.ChessboardItem;

import java.util.HashMap;
import java.util.Map;

/**
 * 常量类，不可实例化
 */
public class IOConstantUtils {
    public static final String FILE_NAME="chessboards.csv";
    public static final String FILE_PATH=".";
    public static final String CHARSET="GBK";
    private static final Map<Long, ChessboardItem> chessboardItemMap=new HashMap<>();
    private IOConstantUtils(){};

    public static Map<Long, ChessboardItem> getChessboardItemMap() {
        if (chessboardItemMap.size() ==0){
            ReadChessboards.getInstance().readChessboard();
        }
        return chessboardItemMap;
    }
}