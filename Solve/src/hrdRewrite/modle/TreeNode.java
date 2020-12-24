package hrdRewrite.modle;


import java.util.*;

import static hrdRewrite.modle.ChessmanStep.SpaceChanged.*;

//只用来辅助计算，常用方法转发chessboar的
public class TreeNode {
    private final TreeNode parent;
    private final Chessboard chessboard;
    private final char[][] chessboardArr;
    private final Corrdinate space1;
    private final Corrdinate space2;

    //测试使用
    public char[][] getChessboardArr() {
        return new char[][]{chessboardArr[0].clone(),chessboardArr[1].clone(),chessboardArr[2].clone(),chessboardArr[3].clone(),chessboardArr[4].clone()};
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
        char[][] parentChessboardArrClone=parent.getChessboardArr();
        //子类棋盘对象构建
        EnumMap<Chessman, ChessmanWithCoordinate> enumMap =new EnumMap<>(parent.chessboard.getChessmans());
        ChessmanWithCoordinate beforeChangeChessman=enumMap.get(chessmanStep.getChessman());
        ChessmanWithCoordinate afterChangeChessman=beforeChangeChessman.movedStep(chessmanStep.getStep());
        //空格坐标移动
        switch (chessmanStep.getSpaceChanged()){

            case SP1 -> {
                space1=parent.space1.moveStep(chessmanStep.getStep().getOppoisteStep(),chessmanStep.getChessman());
                space2=parent.space2;
            }
            case SP2 -> {
                space1=parent.space1;
                space2=parent.space2.moveStep(chessmanStep.getStep().getOppoisteStep(),chessmanStep.getChessman());
            }
            case SP12 -> {
                space1=parent.space1.moveStep(chessmanStep.getStep().getOppoisteStep(),chessmanStep.getChessman());
                space2=parent.space2.moveStep(chessmanStep.getStep().getOppoisteStep(),chessmanStep.getChessman());
            }
            default -> {
                //忽略厚度，用于移动两步
                space1=parent.space1.moveStep(chessmanStep.getStep().getOppoisteStep());
                space2=parent.space2.moveStep(chessmanStep.getStep().getOppoisteStep());
            }
        }
        enumMap.put(chessmanStep.getChessman(),afterChangeChessman);
        this.chessboard=new Chessboard(enumMap);

//        System.out.println("parent:"+parent);
//        System.out.println("step:"+chessmanStep);

        //节点辅助数组更改
//        int count=0;
        for (int i = afterChangeChessman.getYcoordinate();i<afterChangeChessman.getYcoordinate()+afterChangeChessman.getHeight();i++){
            for (int j = afterChangeChessman.getXcoordinate();j<afterChangeChessman.getXcoordinate()+afterChangeChessman.getWidth();j++){
                parentChessboardArrClone[i][j]=afterChangeChessman.getId();
//                count++;
            }
        }
//        System.out.println(afterChangeChessman+""+count);
        parentChessboardArrClone[space1.getY_coordinate()][space1.getX_coordinate()]='\0';
        parentChessboardArrClone[space2.getY_coordinate()][space2.getX_coordinate()]='\0';


        this.chessboardArr=parentChessboardArrClone;
        //测试使用
//        System.out.println("this:"+this);
    }
    //获取该节点能获得的所有步骤
    public LinkedList<ChessmanStep> getSteps(){
        LinkedList<ChessmanStep> steps= new LinkedList<>();
        //数组，方便批量操作
        Corrdinate[] corrdinate = {space1,space2};
        ChessmanStep.SpaceChanged[] spaceChanged = {SP1,SP2};
        char[] ids =new char[2];
        char id;
        int spaceType;
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
            if (ids[0] == '\0'){
                id=ids[1];
                spaceType=0;
            }else {
                id=ids[0];
                spaceType=1;
            }
            switch (id){
                case 'h','i','j','k'->{
                    //移动距离最远的格子
                    steps.add(ChessmanStep.getInstance(Chessman.getInstanceByID(id),Step.UP2, spaceChanged[spaceType]));
                }
                case 'c','d','e','f'->{
                    //都移动
                    steps.add(ChessmanStep.getInstance(Chessman.getInstanceByID(id),Step.UP2, SP21));
                }
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
            if (ids[0] == '\0'){
                id=ids[1];
                spaceType=0;
            }else {
                id=ids[0];
                spaceType=1;
            }
            switch (id){
                //移动距离最远的格子
                case 'h','i','j','k'->steps.add(ChessmanStep.getInstance(Chessman.getInstanceByID(id),Step.DOWN2, spaceChanged[spaceType]));
                //都移动
                case 'c','d','e','f'->steps.add(ChessmanStep.getInstance(Chessman.getInstanceByID(id),Step.DOWN2, SP21));
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
            if (ids[0] == '\0'){
                id=ids[1];
                spaceType=0;
            }else {
                id=ids[0];
                spaceType=1;
            }
            switch (id){
                case 'h','i','j','k'->{
                    //移动距离最远的格子
                    steps.add(ChessmanStep.getInstance(Chessman.getInstanceByID(id),Step.LEFT2, spaceChanged[spaceType]));
                }
                case 'b'->{
                    //都移动
                    steps.add(ChessmanStep.getInstance(Chessman.getInstanceByID(id),Step.LEFT2, SP21));
                }
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
            if (ids[0] == '\0'){
                id=ids[1];
                spaceType=0;
            }else {
                id=ids[0];
                spaceType=1;
            }
            switch (id){
                case 'h','i','j','k'->{
                    //移动距离最远的格子
                    steps.add(ChessmanStep.getInstance(Chessman.getInstanceByID(id),Step.RIGHT1, spaceChanged[spaceType]));
                }
                case 'b'->{
                    //都移动
                    steps.add(ChessmanStep.getInstance(Chessman.getInstanceByID(id),Step.RIGHT2, SP21));
                }
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

    public Chessboard getChessboard() {
        return chessboard;
    }

    //终止节点
    public boolean isEndNode(){
        ChessmanWithCoordinate chessman = chessboard.getChessmans().get(Chessman.曹操);
        return chessman.getXcoordinate() == 1 && chessman.getYcoordinate() ==3;
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
        StringBuilder sb = new StringBuilder();
        sb.append('\n');
        for (char [] line : chessboardArr){
            for (char c : line){
                sb.append(c);
                sb.append('\t');
            }
            sb.append('\n');
        }
        sb.append(chessboard.toString());
        sb.append(space1);
        sb.append(space2);
        return sb.toString();
    }
}
