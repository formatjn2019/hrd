package hrd.modle;


import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;

import static hrd.modle.Chessman.*;

public class Chessboard {
    private long state=0L;
    private long mirror=0L;
    private long adjectiveMirror=0L;
    private final Map<Chessman,ChessmanWithCoordinate> chessmans ;

    //采用棋子数组初始化
    public Chessboard(Map<Chessman,ChessmanWithCoordinate> chessmanMap) {
        chessmans=chessmanMap;
    }
    //采用长整型初始化
    public Chessboard(Map<Chessman,ChessmanWithCoordinate> chessmanMap,long state){
        this.state=state;
        long temp=state;
        chessmans = chessmanMap;
        byte x,y;
        long t2=0x3L,t3=0x7L;
        Chessman cm;
        for (char i='j';i>='a';i--){
            //曹操和四上将纵坐标压缩为2位
            switch (i) {
                case 'a', 'c', 'd', 'e', 'f' -> {
                    y = (byte) (temp & t2);
                    temp >>= 2;
                    x = (byte) (temp & t2);
                    temp >>= 2;
                }
                default -> {
                    y = (byte) (temp & t3);
                    temp >>= 3;
                    x = (byte) (temp & t2);
                    temp >>= 2;
                }
            }
            cm=Chessman.getInstanceByID(i);
            ChessmanWithCoordinate chessman = ChessmanWithCoordinate.getInstance(cm, Corrdinate.getInstance(x, y));
            this.chessmans.put(cm,chessman);
        }
    }

    public Map<Chessman, ChessmanWithCoordinate> getChessmans() {
        return chessmans;
    }
    //使用映像 约360ms
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chessboard that = (Chessboard) o;
        return (this.getMirror() == that.getMirror()) || (this.getAdjectiveMirror() == that.getMirror());
//        return (this.getMirror() == that.getMirror()) ;
    }
    //不使用映像，仅判断非重复节点 约14min 10s 68 ms
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        Chessboard that = (Chessboard) o;
//        return (this.getState() == that.getState());
//    }
    //重写hashcode方法但不一定完全散列
    @Override
    public int hashCode() {
        return (int) (this.getMirror()>>32 ^ this.getMirror());
    }

    public long getState() {
        if (this.state == 0){
            //计算状态存储值
            long temp=0;
            byte x,y;
            for (char i='a';i<'k';i++){
                //曹操和四上将纵坐标压缩为2位
                x=this.chessmans.get(getInstanceByID(i)).getXcoordinate();
                y=this.chessmans.get(getInstanceByID(i)).getYcoordinate();
                temp = switch (i) {
                    case 'a', 'c', 'd', 'e', 'f' -> temp << 4 | x << 2 | y;
                    default -> temp << 5 | x << 3 | y;
                };
            }
            this.state=temp;
        }
        return state;
    }

    public ChessmanWithCoordinate [] sortChessman(ChessmanWithCoordinate ... chessmans){
        Arrays.sort(chessmans);
        return chessmans;
    }


    public long calculateMirror(Map<Chessman,ChessmanWithCoordinate> chessmanMap){
        long temp;
        temp = chessmanMap.get(曹操).getXcoordinate() << 2 | chessmanMap.get(曹操).getYcoordinate();
        temp = temp <<5 | chessmanMap.get(关羽).getXcoordinate() << 3 | chessmanMap.get(关羽).getYcoordinate();
        ChessmanWithCoordinate[] tempChessmans = sortChessman(chessmanMap.get(张飞), chessmanMap.get(赵云), chessmanMap.get(马超), chessmanMap.get(黄忠));
        for (ChessmanWithCoordinate chessman : tempChessmans){
            temp = temp <<4 | chessman.getXcoordinate() << 2 | chessman.getYcoordinate();
        }
        tempChessmans= sortChessman(chessmanMap.get(兵1),chessmanMap.get(兵2),chessmanMap.get(兵3),chessmanMap.get(兵4));
        for (ChessmanWithCoordinate chessman : tempChessmans){
            temp = temp <<5 | chessman.getXcoordinate() << 3 | chessman.getYcoordinate();
        }
        return temp;
    }



    //计算棋局镜像
    public long getMirror() {
        if (this.mirror == 0){
           //计算镜像
            this.mirror=calculateMirror(this.chessmans);
        }
        return this.mirror;
    }
    //计算对称棋局的镜像
    public long getAdjectiveMirror(){
        if (this.adjectiveMirror == 0){
            //计算对称镜像
            EnumMap<Chessman, ChessmanWithCoordinate> enumMap = new EnumMap<>(Chessman.class);
            for (ChessmanWithCoordinate chessman : chessmans.values()){
                enumMap.put(chessman.getChessman(),
                        ChessmanWithCoordinate.getInstance(chessman.getChessman(),
                                Corrdinate.getInstance((byte) (4-chessman.getWidth()-chessman.getXcoordinate()),chessman.getYcoordinate())));
            }
            this.adjectiveMirror=calculateMirror(enumMap);
        }
        return this.adjectiveMirror;
    }

    @Override
    public String toString() {
        char[][] arr = new char[5][4];
        for (ChessmanWithCoordinate chessman :chessmans.values()){
            for (int i = chessman.getYcoordinate();i<chessman.getYcoordinate()+chessman.getHeight();i++){
                for (int j = chessman.getXcoordinate();j<chessman.getXcoordinate()+chessman.getWidth();j++){
                    arr[i][j]=chessman.getId();
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        String lines="---------------------\n";
        sb.append(lines);
        for (char [] line : arr){
            sb.append('|');
            sb.append('\t');
            for(char c : line){
                sb.append(c);
                sb.append('\t');
            }
            sb.append('|');
            sb.append('\n');
        }
        sb.append(lines);

        return "Chessboard{" +
                "status=" + getState() +
                ", mirror=" + getMirror()+
                ", adjectiveMirror=" + getAdjectiveMirror()+
                '\n'+
                sb.toString()+
                '}';
    }
}
