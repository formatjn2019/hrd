package hrd.controller;

import hrd.io.IOConstantUtils;
import hrd.io.ReadChessboards;
import hrd.io.WriteChessboards;
import hrd.modle.Chessboard;
import hrd.modle.ChessboardItem;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class CreateRandomChessboardTest {


    @Test
    void getReandomChessboard() {
        int total=0;
        while (true){
            Chessboard randomChessboard = CreateRandomChessboard.getRandomChessboard(16);
            CreateTree ct= new CreateTree(randomChessboard.getState());
            if(ct.calculateResult()){
                if (ct.getStack().size() <50){
                    continue;
                }
                System.out.println(total);
                System.out.println(randomChessboard);
                System.out.println(ct.getStack().size()-1+"步");
                break;
            }else {
                total +=1;
            }
        }
    }
    @Test
    void getReandomChessboard2() {
        System.out.println(CreateRandomChessboard.getRandomChessboard(16, 90));
    }

    @Test
    void getRandomChessbordWithCache() throws Exception {
        Set<Long> caches = ReadChessboards.getInstance().readMirrors();
        System.out.println(CreateRandomChessboard.getRandomChessbordWithCache(16, caches));
        System.out.println(caches.size());
    }

    @Test
    void calculateNewChessboards() throws Exception {
        int calculatenewChessboardNums=10;
        int type=0;
        //读取当前文件
        ReadChessboards.getInstance().readChessboard();
        //读取缓存文件
        Set<Long> caches = ReadChessboards.getInstance().readMirrors();
        Map<Long, ChessboardItem> chessboardItemMap = IOConstantUtils.getChessboardItemMap();
        System.out.println("当前棋局数量");
        System.out.println(chessboardItemMap.size());
        CreateTree createTree;
        //仅仅在第一次使用生成棋局步骤缓存
        if (caches.size() ==0){
            for (ChessboardItem chessboardItem :chessboardItemMap.values()){
                createTree=new CreateTree(chessboardItem.getChessboard().getState());
                createTree.calculateResult();
                Stack<Chessboard> stack = createTree.getStack();
                while (!stack.empty()){
                    Chessboard pop = stack.pop();
                    caches.add(pop.getMirror());
                    caches.add(pop.getAdjectiveMirror());
                }
            }
        }

        for(int i = 0;i<calculatenewChessboardNums;i++){
            boolean reCalculate=true;
            while (reCalculate){
                Chessboard newChessboard = CreateRandomChessboard.getRandomChessbordWithCache(type, caches);
                createTree=new CreateTree(newChessboard.getState());
                createTree.calculateResult();
                reCalculate= createTree.getStack().size() <= 0;
                if (reCalculate){
                    continue;
                }
                ChessboardItem newItem = new ChessboardItem(newChessboard, createTree.getStack().size() - 1, "new" + newChessboard.hashCode(), type);
                System.out.println(newItem);
                chessboardItemMap.put(newChessboard.getState(),newItem);
                caches.add(newChessboard.getMirror());
                caches.add(newChessboard.getAdjectiveMirror());
//                Stack<Chessboard> stack = createTree.getStack();
//                while (!stack.empty()){
//                    Chessboard pop = stack.pop();
////                if (chessboardItemMap.get(pop.getState())!=null &&
////                        chessboardItemMap.get(pop.getState()).getName().substring(0,3).equals("new")){
////                    chessboardItemMap.remove(pop.getState());
////                }
//                    caches.add(pop.getMirror());
//                    caches.add(pop.getAdjectiveMirror());
//                }
            }

        }
        System.out.println("当前棋局数量"+chessboardItemMap.size());
        WriteChessboards.getInstance().writeChessbards();
        WriteChessboards.getInstance().writeMirrors(caches);
    }

    @Test
    void getAllChessboard(){
        int type=16;
        int searchDepth=80;
        int count=0;
        //读取当前文件
        ReadChessboards.getInstance().readChessboard();
        //读取缓存文件
        Set<Long> caches = ReadChessboards.getInstance().readMirrors();
        Map<Long, ChessboardItem> chessboardItemMap = IOConstantUtils.getChessboardItemMap();
        System.out.println("当前棋局数量");
        System.out.println(chessboardItemMap.size());
        for (ChessboardItem chessboardItem:chessboardItemMap.values()){
            if (chessboardItem.getType() == type && chessboardItem.getDepth()> searchDepth){
                System.out.println(chessboardItem);
                count++;
            }
        }
        System.out.println(count);
    }



}