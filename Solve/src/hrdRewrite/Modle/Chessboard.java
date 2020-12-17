package hrdRewrite.Modle;


import java.util.Arrays;

public class Chessboard {
    private long status=0L;
    private long mirror=0L;
    private final ChessmanWithCoordinate chessmans [] = new ChessmanWithCoordinate [10];
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
    public Chessboard(ChessmanWithCoordinate  chessmanarr[]) {
        int index;
        for (int i = 0;i<chessmanarr.length;i++){
            this.chessmans[chessmanarr[i].getChessman().getId()-'a']=chessmanarr[i];
        }
        changeArrtoChessman();
    }
    private void changeArrtoChessman(){
        this.caocao = chessmans[0];
        this.guanyu = chessmans[1];
        this.zhangfei = chessmans[2];
        this.zhaoyun = chessmans[3];
        this.machao = chessmans[4];
        this.huangzhong = chessmans[5];
        this.bing1 = chessmans[6];
        this.bing2 = chessmans[7];
        this.bing3 = chessmans[8];
        this.bing4 = chessmans[9];
    }

    private void changeChessmantoArr(){
        chessmans[0] =this.caocao;
        chessmans[1] = this.guanyu;
        chessmans[2]=this.zhangfei;
        chessmans[3]=this.zhaoyun;
        chessmans[4]=this.machao;
        chessmans[5]=this.huangzhong;
        chessmans[6]=this.bing1;
        chessmans[7]=this.bing2;
        chessmans[8]=this.bing3;
        chessmans[9]=this.bing4;
    }
    //采用长整型初始化
    public Chessboard(long status){

        this.status=status;
        long temp=status;
        byte x,y;
        long t2=0x3L,t3=0x7L;
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
            this.chessmans[i]=ChessmanWithCoordinate.getInstance(Chessman.getInstance(i),Corrdinate.getInstance(x,y));
        }
        changeArrtoChessman();
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chessboard that = (Chessboard) o;
        return this.getMirror() == that.getMirror();
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
                x=this.chessmans[i].getXcoordinate();
                y=this.chessmans[i].getYcoordinate();
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

    private int [] getOrder(ChessmanWithCoordinate []chessmans,int start){
        boolean virable[] = new boolean[4];
        int order[]=new int[4];
        int max,index=0;
        for (int i=0 ; i < 4 ; i ++){
            max = Integer.MIN_VALUE;
            for(int j= 0 ;j < 4; j ++){
                if (virable[j]){
                    continue;
                }
                if (chessmans[start+j].hashCode() > max){
                    max=chessmans[start+j].hashCode();
                    index=j;
                }
            }
            order[i]=index;
            virable[index]=true;
        }
        return order;
    }


    private void calculateMirror(ChessmanWithCoordinate chessmans[]){
        long temp=0;
        temp = chessmans[0].getXcoordinate() << 2 | chessmans[0].getYcoordinate();
        temp = temp <<5 | chessmans[1].getXcoordinate() << 3 | chessmans[2].getYcoordinate();

    }


    @Override
    public String toString() {
        char arr [][]= new char[5][4];
        for (ChessmanWithCoordinate chessman :chessmans){
            for (int i = chessman.getYcoordinate();i<chessman.getYcoordinate()+chessman.getHeight();i++){
                for (int j = chessman.getXcoordinate();j<chessman.getXcoordinate()+chessman.getWidth();j++){
                    arr[i][j]=chessman.getId();
                }
            }
        }
        StringBuffer sb = new StringBuffer();
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
                "status=" + status +
                ", mirror=" + mirror+
                '\n'+
                sb.toString()+
                '}';
    }

    public long getMirror() {
        if (this.mirror == 0){
           //计算镜像

        }
        return this.mirror;
    }
}
