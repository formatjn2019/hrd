package hrd.controller;

import hrd.modle.Chessboard;
import hrd.modle.Chessman;
import hrd.modle.ChessmanWithCoordinate;
import hrd.modle.Corrdinate;

import java.util.*;

public class CreateAllChessboard {

    /**
     * 利用缓存高速生成所有棋局
     *
     * @param type          类型
     * @param cachedMirrors 缓存的Mirrors
     * @return 所有棋局Set
     */
    public static Set<Chessboard> getAlltypeWithChche(int type, Set<Long> cachedMirrors) {
        Chessman[] chessmans = new Chessman[10];
        boolean[] types = new boolean[10];
        //解析类型
        for (int i = 5; i > 0; i--) {
            types[i] = (type & 0x1) == 1;
            type >>= 1;
        }
        //初始化棋子数组
        for (int i = 0; i < 10; i++) {
            chessmans[i] = Chessman.getInstanceByID((char) ('a' + i), types[i] ? Chessman.ChessmanType.HENG : Chessman.ChessmanType.SHU);
        }
        Set<Chessboard> resultSet = new HashSet<>();
        calculateChessmans(resultSet, new ChessmanWithCoordinate[10], new char[5][4], chessmans, 0);
        return resultSet;
    }

    /**
     * 递归计算枚举所有棋局
     * 若采用循环嵌套，则为20层循环
     *
     * @param resultSet               结果棋局
     * @param chessmanWithCoordinates 棋子数组
     * @param chars                   棋盘已有位置
     * @param chessmans               棋子类型数组
     * @param index                   将要生成的棋子位置
     */
    private static void calculateChessmans(Set<Chessboard> resultSet, ChessmanWithCoordinate[] chessmanWithCoordinates, char[][] chars, Chessman[] chessmans, int index) {
        if (index == 10) {
            EnumMap<Chessman, ChessmanWithCoordinate> chessmanMap = new EnumMap<>(Chessman.class);
            for (int i = 0; i < 10; i++) {
                chessmanMap.put(chessmans[i], chessmanWithCoordinates[i]);
            }
            resultSet.add(new Chessboard(chessmanMap));
        } else {

            for (byte y = 0; y < 6 - chessmans[index].getType().getHeight(); y++) {
                for (byte x = 0; x < 5 - chessmans[index].getType().getWidth(); x++) {
                    boolean continueFlag = true;
                    outter:
                    for (int ty = y; ty < y + chessmans[index].getType().getHeight(); ty++) {
                        for (int tx = x; tx < x + chessmans[index].getType().getWidth(); tx++) {
                            if (ty > 4 || tx > 3 || chars[ty][tx] != '\0') {
                                continueFlag = false;
                                break outter;
                            }
                        }
                    }
                    if (continueFlag) {
                        char[][] newchars = new char[][]{chars[0].clone(), chars[1].clone(), chars[2].clone(), chars[3].clone(), chars[4].clone()};
                        for (int ty = y; ty < y + chessmans[index].getType().getHeight(); ty++) {
                            for (int tx = x; tx < x + chessmans[index].getType().getWidth(); tx++) {
                                newchars[ty][tx] = chessmans[index].getId();
                            }
                        }
                        chessmanWithCoordinates[index] = ChessmanWithCoordinate.getInstance(chessmans[index], Corrdinate.getInstance(x, y));
                        calculateChessmans(resultSet, chessmanWithCoordinates, newchars, chessmans, index + 1);
                    }
                }
            }
        }
    }

}
