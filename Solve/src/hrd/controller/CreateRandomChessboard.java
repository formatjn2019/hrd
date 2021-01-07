package hrd.controller;

import hrd.modle.Chessboard;
import hrd.modle.Chessman;
import hrd.modle.ChessmanWithCoordinate;
import hrd.modle.Corrdinate;

import java.util.EnumMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class CreateRandomChessboard {
//    private static final CreateRandomChessboard instance = new CreateRandomChessboard();
//    private CreateRandomChessboard(){}
//    public static  CreateRandomChessboard getInstance(){
//        return instance;
//    }
    //获取随机棋局
    public static Chessboard getRandomChessboard(int type){
        Chessman [] chessmans = new Chessman[10];
        boolean []types=new boolean[10];
        char [][] chars = new char[5][4];
        Map<Chessman, ChessmanWithCoordinate> chessmanMap=new EnumMap<>(Chessman.class);
        //本地随机数数生成
        ThreadLocalRandom current = ThreadLocalRandom.current();
        //解析类型
        for (int i =5;i>0;i--) {
            types[i] = (type & 0x1 )==1;
            type >>= 1;
        }
        for(int i =0;i<10;i++){
            Chessman chessman=Chessman.getInstanceByID((char) ('a'+i),types[i]? Chessman.ChessmanType.HENG: Chessman.ChessmanType.SHU);
            chessmans[i]=chessman;
            ChessmanWithCoordinate chessmanWithCoordinate = null;
            int x=0,y=0;
            boolean flag=true;
            while (flag) {
                x=current.nextInt(0,5-chessman.getType().getWidth());
                y=current.nextInt(0,6-chessman.getType().getHeight());
                flag=false;
                outter1:
                for (int ty =y;ty<y+chessman.getType().getHeight();ty++){
                    for (int tx=x; tx <x + chessman.getType().getWidth();tx++){
                        if (chars[ty][tx] != '\0') {
                            flag = true;
                            break outter1;
                        }
                    }
                }
            }

            for (int ty =y;ty<y+chessman.getType().getHeight();ty++){
                for (int tx=x; tx <x + chessman.getType().getWidth();tx++){
                    chars[ty][tx] = chessman.getId();
                }
            }
            chessmanMap.put(chessmans[i],ChessmanWithCoordinate.getInstance(chessman, Corrdinate.getInstance((byte)x,(byte)y)));
        }
        return new Chessboard(chessmanMap);
    }
    //获取指定等级棋局
    public static Chessboard getRandomChessboard(int type,int step){
        int total=0;
        while (true){
            Chessboard randomChessboard = getRandomChessboard(type);
            CreateTree ct= new CreateTree(randomChessboard.getState());
            if(ct.calculateResult()){
                if (ct.getStack().size() < step){
                    continue;
                }
                System.out.println(total);
                System.out.println(randomChessboard);
                System.out.println(ct.getStack().size()-1+"步");
                return randomChessboard;
            }else {
                total +=1;
            }
        }
    }
    //使用缓存高速生成棋局
    public static Chessboard getRandomChessbordWithCache(int type, Set<Long> cachedMirrors) throws Exception {
        Chessman [] chessmans = new Chessman[10];
        boolean []types=new boolean[10];
        char [][] chars = new char[5][4];
        Map<Chessman, ChessmanWithCoordinate> chessmanMap=new EnumMap<>(Chessman.class);
        //本地随机数数生成
//        ThreadLocalRandom current = ThreadLocalRandom.current();
        Random current = new Random(System.nanoTime()+cachedMirrors.size());
        //解析类型
        for (int i =5;i>0;i--) {
            types[i] = (type & 0x1 )==1;
            type >>= 1;
        }
        Chessboard chessboard;
        int falueCount=0;
        while (true){
            for(int i =0;i<10;i++){
                Chessman chessman=Chessman.getInstanceByID((char) ('a'+i),types[i]? Chessman.ChessmanType.HENG: Chessman.ChessmanType.SHU);
                chessmans[i]=chessman;
                ChessmanWithCoordinate chessmanWithCoordinate = null;
                int x=0,y=0;
                boolean flag=true;
                while (flag) {
                    x=current.nextInt(5-chessman.getType().getWidth());
                    y=current.nextInt(6-chessman.getType().getHeight());
                    flag=false;
                    outter1:
                    for (int ty =y;ty<y+chessman.getType().getHeight();ty++){
                        for (int tx=x; tx <x + chessman.getType().getWidth();tx++){
                            if (chars[ty][tx] != '\0') {
                                flag = true;
                                break outter1;
                            }
                        }
                    }
                    falueCount+=1;
                    if (falueCount >1000000000){
                        throw new Exception("222");
                    }
                }

                for (int ty =y;ty<y+chessman.getType().getHeight();ty++){
                    for (int tx=x; tx <x + chessman.getType().getWidth();tx++){
                        chars[ty][tx] = chessman.getId();
                    }
                }
                chessmanMap.put(chessmans[i],ChessmanWithCoordinate.getInstance(chessman, Corrdinate.getInstance((byte)x,(byte)y)));
            }
            chessboard = new Chessboard(chessmanMap);
            if (cachedMirrors.add(chessboard.getMirror())){
                cachedMirrors.add(chessboard.getAdjectiveMirror());
                return chessboard;
            }

        }
    }
}
