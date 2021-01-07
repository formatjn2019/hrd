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
import java.util.Set;

import static hrd.io.IOConstantUtils.*;

public class WriteChessboards {

    private static final WriteChessboards instance=new WriteChessboards();

    private WriteChessboards(){

    }


    //返回实例
    public static WriteChessboards getInstance(){
        return instance;
    }

    /**
     * 写入默认棋局
     */
    public void writeChessbards(){
        Map<Long, ChessboardItem> chessboardItemMap = IOConstantUtils.getChessboardItemMap();
        writeChessbards(FILE_NAME,chessboardItemMap);
    }

    /**
     * 将棋局写入指定文件
     * @param fileName  文件名
     * @param dataMap 数据
     */
    public void writeChessbards(String fileName,Map<Long,ChessboardItem> dataMap){
        try(BufferedWriter bw=Files.newBufferedWriter(LOCAL_PATH.resolve(fileName), Charset.forName(CHARSET))) {
            bw.write(ChessboardItem.getHeads());
            bw.newLine();
            for(ChessboardItem chessboardItem : dataMap.values()){
                bw.write(chessboardItem.getLine());
                bw.newLine();
            }
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将缓存的数据写入默认文件
     * @param mirrorsSet 缓存的棋局Miirror
     */
    public void writeMirrors(Set<Long> mirrorsSet){
        writeMirrors(mirrorsSet,CASHE_NAME);
    }

    /**
     * 将缓存的数据写入默认文件
     * @param mirrorsSet 缓存棋局的Mirror
     * @param fileName 文件名
     */
    public void writeMirrors(Set<Long> mirrorsSet,String fileName){
        try(BufferedWriter bw=Files.newBufferedWriter(LOCAL_PATH.resolve(fileName), Charset.forName(CHARSET))) {
            for(Long l :mirrorsSet){
                bw.write(l+"");
                bw.newLine();
            }
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
