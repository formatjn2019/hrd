package hrdRewrite.Modle;


import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.locks.Condition;

//只用来辅助计算，常用方法转发chessboar的
public class TreeNode {
    private final TreeNode parent;
    private final Chessboard chessboard;
    private final char chessboardArr [][];
    private final Corrdinate space1;
    private final Corrdinate space2;

    public TreeNode(Chessboard bootChessboard){
        this.chessboard=bootChessboard;
        this.parent = null;
        char[][] arr = new char[5][4];
        Corrdinate temp1=null,temp2=null;
        chessboardArr=arr;
        for (ChessmanWithCoordinate chessman :this.chessboard.getChessmans().values()){
            for (int i = chessman.getYcoordinate();i<chessman.getYcoordinate()+chessman.getHeight();i++){
                for (int j = chessman.getXcoordinate();j<chessman.getXcoordinate()+chessman.getWidth();j++){
                    arr[i][j]=chessman.getId();
                }
            }
        }

        for (int i = 0;i<5;i++){
            for (int j = 0 ;j<4;j++){
                if (arr[i][j] == '\0' && temp1 !=null) {
//                    temp1 = Corrdinate.getInstance(())
                }
            }
        }
    }

    public TreeNode(Chessboard chessboard, char[][] chessboardArr, Step step, TreeNode parent){
        this.parent=parent;
        this.chessboardArr=chessboardArr;
        this.chessboard=chessboard;
    }

    public LinkedList<ChessmanStep> getSteps(){
        LinkedList<ChessmanStep> steps= new LinkedList<>();

        return steps;
    }

    //终止节点
    public boolean isEndNode(){
        return false;
    }

    @Override
    public boolean equals(Object o) {
        return chessboard.equals(((TreeNode) o).chessboard);
    }

    @Override
    public int hashCode() {
        return chessboard.hashCode();
    }

    @Override
    public String toString() {
        return chessboard.toString();
    }
}
