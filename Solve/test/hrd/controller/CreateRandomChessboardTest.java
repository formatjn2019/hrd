package hrd.controller;

import hrd.io.IOConstantUtils;
import hrd.io.ReadChessboards;
import hrd.io.WriteChessboards;
import hrd.modle.Chessboard;
import hrd.modle.ChessboardItem;
import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

class CreateRandomChessboardTest {


    @Test
    void getReandomChessboard() {

        Optional<Chessboard> randomChessboard = CreateRandomChessboard.getRandomChessboard(16, 50);
        if (randomChessboard.isPresent()) {
//            CreateTree ct = new CreateTree(randomChessboard.get().getState());
            System.out.println("生成成功");
        } else {
            System.out.println("生成失败");
        }
    }

    @Test
    void getReandomChessboard2() {
        System.out.println(CreateRandomChessboard.getRandomChessboard(16, 81));
    }

    @Test
    void getRandomChessbordWithCache() {
        Set<Long> caches = ReadChessboards.readMirrors();
        System.out.println(CreateRandomChessboard.getRandomChessbordWithCache(16, caches));
        System.out.println(caches.size());
    }

    @Test
    void calculateNewChessboards() {
        int calculatenewChessboardNums = 10;
        int type = 0;
        //读取当前文件
        ReadChessboards.readChessboard();
        //读取缓存文件
        Set<Long> caches = ReadChessboards.readMirrors();
        Map<Long, ChessboardItem> chessboardItemMap = IOConstantUtils.getChessboardItemMap();
        System.out.println("当前棋局数量");
        System.out.println(chessboardItemMap.size());
        CreateTree createTree;
        //仅仅在第一次使用生成棋局步骤缓存
        if (caches.size() == 0) {
            for (ChessboardItem chessboardItem : chessboardItemMap.values()) {
                createTree = new CreateTree(chessboardItem.getChessboard().getState());
                createTree.calculateResult();
                ArrayDeque<Chessboard> stack = createTree.getStack();
                while (!stack.isEmpty()) {
                    Chessboard pop = stack.pop();
                    caches.add(pop.getMirror());
                    caches.add(pop.getAdjectiveMirror());
                }
            }
        }

        for (int i = 0; i < calculatenewChessboardNums; i++) {
            boolean reCalculate = true;
            while (reCalculate) {
                Optional<Chessboard> randomChessbordWithCache = CreateRandomChessboard.getRandomChessbordWithCache(type, caches);
                if (randomChessbordWithCache.isPresent()) {
                    Chessboard newChessboard = randomChessbordWithCache.get();
                    createTree = new CreateTree(newChessboard.getState());
                    createTree.calculateResult();
                    reCalculate = createTree.getStack().size() <= 0;
                    if (reCalculate) {
                        continue;
                    }
                    ChessboardItem newItem = new ChessboardItem(newChessboard, createTree.getStack().size() - 1, "new" + newChessboard.hashCode(), type);
                    System.out.println(newItem);
                    chessboardItemMap.put(newChessboard.getState(), newItem);
                    caches.add(newChessboard.getMirror());
                    caches.add(newChessboard.getAdjectiveMirror());
                }
            }

        }
        System.out.println("当前棋局数量" + chessboardItemMap.size());
        WriteChessboards.getInstance().writeChessbards();
        WriteChessboards.getInstance().writeMirrors(caches);
    }

    @Test
    void getAllChessboard() {
        int type = 16;
        int searchDepth = 80;
        int count = 0;
        //读取当前文件
        ReadChessboards.readChessboard();
        //读取缓存文件
        Set<Long> caches = ReadChessboards.readMirrors();
        Map<Long, ChessboardItem> chessboardItemMap = IOConstantUtils.getChessboardItemMap();
        System.out.println("当前棋局数量");
        System.out.println(chessboardItemMap.size());
        for (ChessboardItem chessboardItem : chessboardItemMap.values()) {
            if (chessboardItem.getType() == type && chessboardItem.getDepth() > searchDepth) {
                System.out.println(chessboardItem);
                count++;
            }
        }
        System.out.println(count);
    }


}