package hrdRewrite.Modle;

import com.sun.jdi.Mirror;

import java.util.Objects;

public class Chessboard {
    private long status=0L;
    private long mirror=0L;
    private final ChessmanWithCoordinate chessmanas [] = new ChessmanWithCoordinate [10];
    private ChessmanWithCoordinate caocao;
    private ChessmanWithCoordinate guanyu;
    private ChessmanWithCoordinate zhangfei;
    private ChessmanWithCoordinate zhaoyun;
    private ChessmanWithCoordinate machao;
    private ChessmanWithCoordinate huangzhong;
    private ChessmanWithCoordinate bing1;
    private ChessmanWithCoordinate bing2;
    private ChessmanWithCoordinate bing3;
    private ChessmanWithCoordinate bing4;
    //采用棋子数组初始化
    public Chessboard(ChessmanWithCoordinate ... chessmanarr) {
        for (int i = 0;i<chessmanarr.length;i++){
            this.chessmanas[chessmanarr[i].getChessman().getId()-'a']=chessmanarr[i];
        }
        changeArrtoChessman();
    }
    private void changeArrtoChessman(){
        this.caocao = chessmanas[0];
        this.guanyu = chessmanas[1];
        this.zhangfei = chessmanas[2];
        this.zhaoyun = chessmanas[3];
        this.machao = chessmanas[4];
        this.huangzhong = chessmanas[5];
        this.bing1 = chessmanas[6];
        this.bing2 = chessmanas[7];
        this.bing3 = chessmanas[8];
        this.bing4 = chessmanas[9];
    }

    private void changeChessmantoArr(){
        chessmanas[0] =this.caocao;
        chessmanas[1] = this.guanyu;
        chessmanas[2]=this.zhangfei;
        chessmanas[3]=this.zhaoyun;
        chessmanas[4]=this.machao;
        chessmanas[5]=this.huangzhong;
        chessmanas[6]=this.bing1;
        chessmanas[7]=this.bing2;
        chessmanas[8]=this.bing3;
        chessmanas[9]=this.bing4;
    }
    //采用长整型初始化
    public Chessboard(long status){

        this.status=status;
        long temp=status;
        byte x,y;
        long t2=0x0000000000000003L,t3=0x0000000000000007L;
        for (int i=9;i>=0;i--){
            //曹操和四上将纵坐标压缩为2位
            switch (i){
                case 0:
                case 2:
                case 3:
                case 4:
                case 5:
                    y= (byte) (temp & t2);temp >>=2;
                    x= (byte) (temp & t2);temp >>=2;
                    break;
                default:
                    y= (byte) (temp & t3);temp >>=3;
                    x= (byte) (temp & t2);temp >>=2;
                    break;
            }
            this.chessmanas[i]=ChessmanWithCoordinate.getInstance(Chessman.getInstance(i),Corrdinate.getInstance(x,y));
        }
        changeArrtoChessman();
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chessboard that = (Chessboard) o;
        return mirror == that.mirror;
    }

    //重写hashcode方法但不一定完全散列
    @Override
    public int hashCode() {
        return (int) (this.getMirror()>>32 ^ this.getMirror());
    }

    public long getStatus() {
        if (this.status == 0){
            //计算状态存储值
            long temp=0;
            byte x,y;
            for (int i=0;i<10;i++){
                //曹操和四上将纵坐标压缩为2位
                x=this.chessmanas[i].getXcoordinate();
                y=this.chessmanas[i].getYcoordinate();
                switch (i){
                    case 0:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                        temp= temp <<4 | x <<2 | y;
                        break;
                    default:
                        temp = temp <<5 | x << 3 | y;
                        break;
                }
            }
            this.status=temp;
        }
        return status;
    }

    public long getMirror() {
        if (this.mirror == 0){
           //计算镜像
        }
        return 0;
    }
}
