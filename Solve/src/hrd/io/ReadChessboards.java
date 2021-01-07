package hrd.io;

import hrd.modle.Chessboard;
import hrd.modle.ChessboardItem;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static hrd.io.IOConstantUtils.*;

public class ReadChessboards {

    private static final ReadChessboards instance=new ReadChessboards();

    private ReadChessboards(){

    }


    //返回实例
    public static ReadChessboards getInstance(){
        return instance;
    }
    //读取chessboar数据放入缓存
    public void readChessboard(){
        Map<Long, ChessboardItem> chessboardItemMap = IOConstantUtils.getChessboardItemMap();

        chessboardItemMap.putAll(readChessboard(FILE_NAME));
    }

    //读取chessboar数据放入缓存
    public Map<Long, ChessboardItem> readChessboard(String fileName){
        Map<Long, ChessboardItem> resultMap = new HashMap<>();
        try(BufferedReader br =Files.newBufferedReader(LOCAL_PATH.resolve(fileName), Charset.forName(CHARSET))) {
            String line = br.readLine();
            while ((line=br.readLine()) != null){
                String[] strings = line.split(",");
                ChessboardItem chessboardItem = new ChessboardItem(strings[0],strings[1],strings[2],strings[3]);
                resultMap.put(chessboardItem.getChessboard().getState(),chessboardItem);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  resultMap;
    }
    //读取Mirror缓存文件
    public Set<Long> readMirrors(){
        return readMirrors(CASHE_NAME);
    }
    //读取Mirror缓存文件
    public Set<Long> readMirrors(String fileName){
        Set<Long> resultSet = new HashSet<>();
        try(BufferedReader br =Files.newBufferedReader(LOCAL_PATH.resolve(fileName), Charset.forName(CHARSET))) {
            String line;
            while ((line=br.readLine()) != null){
                resultSet.add(Long.parseLong(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultSet;
    }
}
