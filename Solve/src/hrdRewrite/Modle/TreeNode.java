package hrdRewrite.Modle;


import java.util.*;

import static hrdRewrite.Modle.ChessmanStep.SpaceChanged.*;

//只用来辅助计算，常用方法转发chessboar的
public class TreeNode {
    private final TreeNode parent;
    private final Chessboard chessboard;
    private final char chessboardArr [][];
    private final Corrdinate space1;
    private final Corrdinate space2;

    //测试使用
    public char[][] getChessboardArr() {

        return chessboardArr;
    }
    //测试使用
    public TreeNode getParent() {
        return parent;
    }

    //为根结点进行初始化
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

        outter:
        for (int i = 0;i<5;i++){
            for (int j = 0 ;j<4;j++){
                if (arr[i][j] == '\0') {
                    if (temp1 == null){
                        temp1 = Corrdinate.getInstance((byte)j, (byte)i);
                    }else {
                        temp2 = Corrdinate.getInstance((byte)j, (byte)i);
                        break outter;
                    }
                }
            }
        }
        space1 = temp1;
        space2 = temp2;
    }
    //根据步骤进行初始化
    public TreeNode(TreeNode parent, ChessmanStep chessmanStep){
        this.parent=parent;
        char[][] parentChessboardArrClone=parent.getChessboardArr().clone();
        //子类棋盘对象构建
        EnumMap<Chessman, ChessmanWithCoordinate> enumMap =new EnumMap<>(parent.chessboard.getChessmans());
        ChessmanWithCoordinate beforeChangeChessman=enumMap.get(chessmanStep.getChessman());
        ChessmanWithCoordinate afterChangeChessman=beforeChangeChessman.movedStep(chessmanStep.getStep());
        //空格坐标移动
        switch (chessmanStep.getSpaceChanged()){
            case SP1 -> {
                space1=parent.space1.moveStep(chessmanStep.getStep().getOppoisteStep());
                space2=parent.space2;
            }
            case SP2 -> {
                space1=parent.space1;
                space2=parent.space2.moveStep(chessmanStep.getStep().getOppoisteStep());
            }
            case SP12 -> {
                space1=parent.space1.moveStep(chessmanStep.getStep().getOppoisteStep());
                space2=parent.space2.moveStep(chessmanStep.getStep().getOppoisteStep());
            }
            default -> {
                space1 =parent.space1;
                space2 =parent.space2;
            }
        }
        enumMap.put(chessmanStep.getChessman(),afterChangeChessman);
        this.chessboard=new Chessboard(enumMap);
        //节点辅助数组更改
        for (int i = afterChangeChessman.getYcoordinate();i<afterChangeChessman.getYcoordinate()+afterChangeChessman.getHeight();i++){
            for (int j = afterChangeChessman.getXcoordinate();j<afterChangeChessman.getXcoordinate()+afterChangeChessman.getWidth();j++){
                parentChessboardArrClone[i][j]=afterChangeChessman.getId();
            }
        }
        parentChessboardArrClone[space1.getY_coordinate()][space1.getX_coordinate()]='\0';
        parentChessboardArrClone[space2.getY_coordinate()][space2.getX_coordinate()]='\0';
        this.chessboardArr=parentChessboardArrClone;
    }
    //获取该节点能获得的所有步骤
    public LinkedList<ChessmanStep> getSteps(){
        LinkedList<ChessmanStep> steps= new LinkedList<>();
        //数组，方便批量操作
        Corrdinate[] corrdinate = {space1,space2};
        ChessmanStep.SpaceChanged[] spaceChanged = {SP1,SP2};
        char[] ids =new char[2];
        char id;
        //上1 宽1
        for (int i =0;i<2;i++){
            ids[i]=searchChessmanId(corrdinate[i],Step.UP1);
            if (ids[i] > 'b'){
                steps.add(ChessmanStep.getInstance(Chessman.getInstanceByID(ids[i]),Step.UP1, spaceChanged[i]));
            }
        }
        //上1 宽2
        //纵坐标相等，横坐标相差1
        if(space1.getY_coordinate() == space2.getY_coordinate() && Math.abs(space1.getX_coordinate()-space2.getX_coordinate())==1 && ids[0] !='\0' && ids[0] == ids[1]) {
            steps.add(ChessmanStep.getInstance(Chessman.getInstanceByID(ids[0]),Step.UP1, SP12));
        }
        //上2 宽1
        //横坐标相等，纵坐标相差1
        if(space1.getX_coordinate() == space2.getX_coordinate() && Math.abs(space1.getY_coordinate()-space2.getY_coordinate())==1) {
            id= ids[0] >ids[1] ? ids[0]:ids[1];
            if (id >'b'){
                steps.add(ChessmanStep.getInstance(Chessman.getInstanceByID(ids[0]),Step.UP2, SP12));
            }
        }
        //下1 宽1
        for (int i =0;i<2;i++){
            ids[i]=searchChessmanId(corrdinate[i],Step.DOWN1);
            if (ids[i] > 'b'){
                steps.add(ChessmanStep.getInstance(Chessman.getInstanceByID(ids[i]),Step.DOWN1, spaceChanged[i]));
            }
        }
        //下1 宽2
        //纵坐标相等，横坐标相差1
        if(space1.getY_coordinate() == space2.getY_coordinate() && Math.abs(space1.getX_coordinate()-space2.getX_coordinate())==1 && ids[0] !='\0' && ids[0] == ids[1]) {
            steps.add(ChessmanStep.getInstance(Chessman.getInstanceByID(ids[0]),Step.DOWN1, SP12));
        }
        //下2 宽1
        //横坐标相等，纵坐标相差1
        if(space1.getX_coordinate() == space2.getX_coordinate() && Math.abs(space1.getY_coordinate()-space2.getY_coordinate())==1) {
            id= ids[0] >ids[1] ? ids[0]:ids[1];
            if (id >'b'){
                steps.add(ChessmanStep.getInstance(Chessman.getInstanceByID(ids[0]),Step.DOWN2, SP12));
            }
        }

        //左1 高1
        for (int i =0;i<2;i++){
            ids[i]=searchChessmanId(corrdinate[i],Step.LEFT1);
            if (ids[i] == 'b' || ids[i] >'f'){
                steps.add(ChessmanStep.getInstance(Chessman.getInstanceByID(ids[i]),Step.LEFT1, spaceChanged[i]));
            }
        }
        //左1 高2
        //横坐标相等，纵坐标相差1
        if(space1.getX_coordinate() == space2.getX_coordinate() && Math.abs(space1.getY_coordinate()-space2.getY_coordinate())==1 && ids[0] !='\0' && ids[0] == ids[1]) {
            steps.add(ChessmanStep.getInstance(Chessman.getInstanceByID(ids[0]),Step.LEFT1, SP12));
        }
        //左2 高1
        //纵坐标相等，横坐标相差1
        if(space1.getY_coordinate() == space2.getY_coordinate() && Math.abs(space1.getX_coordinate()-space2.getX_coordinate())==1) {
            id= ids[0] >ids[1] ? ids[0]:ids[1];
            if (id == 'b' || id >'f'){
                steps.add(ChessmanStep.getInstance(Chessman.getInstanceByID(ids[0]),Step.LEFT2, SP12));
            }
        }

        //右1 高1
        for (int i =0;i<2;i++){
            ids[i]=searchChessmanId(corrdinate[i],Step.RIGHT1);
            if (ids[i] == 'b' || ids[i] >'f'){
                steps.add(ChessmanStep.getInstance(Chessman.getInstanceByID(ids[i]),Step.RIGHT1, spaceChanged[i]));
            }
        }
        //左1 高2
        //横坐标相等，纵坐标相差1
        if(space1.getX_coordinate() == space2.getX_coordinate() && Math.abs(space1.getY_coordinate()-space2.getY_coordinate())==1 && ids[0] !='\0' && ids[0] == ids[1]) {
            steps.add(ChessmanStep.getInstance(Chessman.getInstanceByID(ids[0]),Step.RIGHT1, SP12));
        }
        //左2 高1
        //纵坐标相等，横坐标相差1
        if(space1.getY_coordinate() == space2.getY_coordinate() && Math.abs(space1.getX_coordinate()-space2.getX_coordinate())==1) {
            id= ids[0] >ids[1] ? ids[0]:ids[1];
            if (id == 'b' || id >'f'){
                steps.add(ChessmanStep.getInstance(Chessman.getInstanceByID(ids[0]),Step.RIGHT2, SP12));
            }
        }

        return steps;
    }
    //返回\0代表错误
    private char searchChessmanId(Corrdinate corrdinate,Step step){
        int x = corrdinate.getX_coordinate(),y = corrdinate.getY_coordinate();
        switch (step.getDir()){
            case UP -> y+=1;
            case DOWN -> y-=1;
            case LEFT -> x+=1;
            case RIGHT -> x-=1;
        }
        if (x < 0  || x > 3 || y < 0 || y > 4){
            return '\0';
        }
        return this.chessboardArr[y][x];
    }



    public Corrdinate getSpace1() {
        return space1;
    }

    public Corrdinate getSpace2() {
        return space2;
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
