package hrd.controller;

import hrd.modle.Chessboard;
import hrd.modle.Chessman;
import hrd.modle.ChessmanWithCoordinate;
import hrd.modle.Corrdinate;

import java.util.*;

public class CreateAllChessboard {
    //使用缓存高速生成棋局
    public static Set<Chessboard> getAlltypeWithChche(int type, Set<Long> cachedMirrors){
        Chessman[] chessmans = new Chessman[10];
        boolean []types=new boolean[10];
        //解析类型
        for (int i =5;i>0;i--) {
            types[i] = (type & 0x1 )==1;
            type >>= 1;
        }
        //初始化棋子数组
        for(int i =0;i<10;i++) {
            chessmans[i] = Chessman.getInstanceByID((char) ('a' + i), types[i] ? Chessman.ChessmanType.HENG : Chessman.ChessmanType.SHU);
        }
        Set<Chessboard> calculateSet=new HashSet<>();
        calculateChessmans(calculateSet,new ChessmanWithCoordinate[10],new char[5][4],chessmans,0);
        System.out.println(calculateSet.size());
        Set<Chessboard> resultSet= new HashSet<>();
        for (Chessboard chessboard :calculateSet){
            //镜像不存在
            if (cachedMirrors.add(chessboard.getMirror())){
                cachedMirrors.add(chessboard.getAdjectiveMirror());
                resultSet.add(chessboard);
            }
        }
        return resultSet;
    }

    private static void calculateChessmans(Set<Chessboard> resultSet, ChessmanWithCoordinate [] chessmanWithCoordinates,char[][]chars, Chessman[] chessmans,int index){
        if (index ==10){
            EnumMap<Chessman, ChessmanWithCoordinate> chessmanMap = new EnumMap<>(Chessman.class);
            for (int i =0;i<10;i++){
                chessmanMap.put(chessmans[i],chessmanWithCoordinates[i]);
            }
            resultSet.add(new Chessboard(chessmanMap));
        }else {

            for(byte y=0;y<6-chessmans[index].getType().getWidth();y++){
                for (byte x=0;x<5-chessmans[index].getType().getHeight();x++){
                    boolean continueFlag=true;
                    outter:
                    for (int ty =y;ty<y+chessmans[index].getType().getHeight();ty++){
                        for (int tx=x; tx <x + chessmans[index].getType().getWidth();tx++){
                            if (ty> 4 || tx> 3 ||chars[ty][tx] != '\0') {
                                continueFlag = false;
                                break outter;
                            }
                        }
                    }
                    if (continueFlag){
                        char [][] newchars = new char[][] {chars[0].clone(),chars[1].clone(),chars[2].clone(),chars[3].clone(),chars[4].clone()};
                        for (int ty =y;ty<y+chessmans[index].getType().getHeight();ty++){
                            for (int tx=x; tx <x + chessmans[index].getType().getWidth();tx++){
                                newchars[ty][tx] = chessmans[index].getId();
                            }
                        }
                        chessmanWithCoordinates[index]  = ChessmanWithCoordinate.getInstance(chessmans[index], Corrdinate.getInstance(x, y));
                        calculateChessmans(resultSet,chessmanWithCoordinates,newchars, chessmans,index+1);
                    }
                }
            }
        }
    }

}
