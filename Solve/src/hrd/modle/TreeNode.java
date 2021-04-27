package hrd.modle;

import java.util.EnumMap;
import java.util.LinkedList;

import static hrd.modle.ChessmanStep.SpaceChanged.*;

/**
 * 树的节点，用于生成树
 * 常用转发chessboar的方法
 */
public class TreeNode {
    private final TreeNode parent;
    private final Chessboard chessboard;
    private final char[][] chessboardArr;
    private final Corrdinate space1;
    private final Corrdinate space2;

    /**
     * 克隆棋盘数组
     *
     * @return 返回新的数组
     */
    public char[][] getChessboardArr() {
        return new char[][]{chessboardArr[0].clone(), chessboardArr[1].clone(), chessboardArr[2].clone(), chessboardArr[3].clone(), chessboardArr[4].clone()};
    }

    /**
     * 获取父节点
     *
     * @return 返回父节点
     */
    public TreeNode getParent() {
        return parent;
    }

    /**
     * 为根结点进行初始化
     *
     * @param bootChessboard 根结点
     */
    public TreeNode(Chessboard bootChessboard) {
        this.chessboard = bootChessboard;
        this.parent = null;
        char[][] arr = new char[5][4];
        Corrdinate temp1 = null, temp2 = null;
        chessboardArr = arr;
        for (ChessmanWithCoordinate chessman : this.chessboard.getChessmans().values()) {
            for (int i = chessman.getYcoordinate(); i < chessman.getYcoordinate() + chessman.getHeight(); i++) {
                for (int j = chessman.getXcoordinate(); j < chessman.getXcoordinate() + chessman.getWidth(); j++) {
                    arr[i][j] = chessman.getId();
                }
            }
        }

        outter:
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 4; j++) {
                if (arr[i][j] == '\0') {
                    if (temp1 == null) {
                        temp1 = Corrdinate.getInstance((byte) j, (byte) i);
                    } else {
                        temp2 = Corrdinate.getInstance((byte) j, (byte) i);
                        break outter;
                    }
                }
            }
        }
        space1 = temp1;
        space2 = temp2;
    }

    /**
     * 根据步骤步骤进行初始化
     *
     * @param parent       父节点
     * @param chessmanStep 棋子的步骤
     */
    public TreeNode(TreeNode parent, ChessmanStep chessmanStep) {
        this.parent = parent;
        char[][] parentChessboardArrClone = parent.getChessboardArr();
        //子类棋盘对象构建
        EnumMap<Chessman, ChessmanWithCoordinate> enumMap = new EnumMap<>(parent.chessboard.getChessmans());
        ChessmanWithCoordinate beforeChangeChessman = enumMap.get(chessmanStep.getChessman());
        ChessmanWithCoordinate afterChangeChessman = beforeChangeChessman.movedStep(chessmanStep.getStep());
        //空格坐标移动
        space1 = chessmanStep.getSpaceChanged().moveSpace1(chessmanStep.getStep().getOppoisteStep(),parent.space1,chessmanStep.getChessman().getType());
        space2 = chessmanStep.getSpaceChanged().moveSpace2(chessmanStep.getStep().getOppoisteStep(),parent.space2,chessmanStep.getChessman().getType());
        enumMap.put(chessmanStep.getChessman(), afterChangeChessman);
        this.chessboard = new Chessboard(enumMap);
//      测试使用
//        System.out.println("parent:"+parent);
//        System.out.println("step:"+chessmanStep);

        for (int i = afterChangeChessman.getYcoordinate(); i < afterChangeChessman.getYcoordinate() + afterChangeChessman.getHeight(); i++) {
            for (int j = afterChangeChessman.getXcoordinate(); j < afterChangeChessman.getXcoordinate() + afterChangeChessman.getWidth(); j++) {
                parentChessboardArrClone[i][j] = afterChangeChessman.getId();
            }
        }

        parentChessboardArrClone[space1.getY_coordinate()][space1.getX_coordinate()] = '\0';
        parentChessboardArrClone[space2.getY_coordinate()][space2.getX_coordinate()] = '\0';


        this.chessboardArr = parentChessboardArrClone;
//        测试使用
//        System.out.println("this:"+this);
    }

    private Chessman getChessmanById(char id) {
        return switch (id) {
            case 'a' -> Chessman.曹操;
            case 'b' -> chessboard.getChessmans().get(Chessman.关羽1) == null ? Chessman.关羽2 : Chessman.关羽1;
            case 'c' -> chessboard.getChessmans().get(Chessman.张飞1) == null ? Chessman.张飞2 : Chessman.张飞1;
            case 'd' -> chessboard.getChessmans().get(Chessman.赵云1) == null ? Chessman.赵云2 : Chessman.赵云1;
            case 'e' -> chessboard.getChessmans().get(Chessman.马超1) == null ? Chessman.马超2 : Chessman.马超1;
            case 'f' -> chessboard.getChessmans().get(Chessman.黄忠1) == null ? Chessman.黄忠2 : Chessman.黄忠1;
            case 'g' -> Chessman.兵1;
            case 'h' -> Chessman.兵2;
            case 'i' -> Chessman.兵3;
            case 'j' -> Chessman.兵4;
            default -> null;
        };

    }

    //获取该节点能获得的所有步骤
    public LinkedList<ChessmanStep> getSteps() {
        LinkedList<ChessmanStep> resultSteps = new LinkedList<>();
        Corrdinate[] spaces = {space1, space2};
        ChessmanStep.SpaceChanged[] spaceChangeds = {SP1, SP2};
        //当前字符
        char corrent;
        //方向字符
        char[] dirs = new char[4];
        //方向长度
        int[] lenth = new int[2];
        Step[] directions = {Step.UP1, Step.UPRIGHT1, Step.RIGHT1, Step.DOWNRIGHT1, Step.DOWN1, Step.DOWNLEFT1, Step.LEFT1, Step.UPLEFT1, Step.UP2, Step.RIGHT2, Step.DOWN2, Step.LEFT2};

        //更改，根据空格判断
        for (int correntSprace = 0; correntSprace < 2; correntSprace++) {
            dirs[0] = searchChessmanId(spaces[correntSprace], Step.UP1);
            dirs[1] = searchChessmanId(spaces[correntSprace], Step.RIGHT1);
            dirs[2] = searchChessmanId(spaces[correntSprace], Step.DOWN1);
            dirs[3] = searchChessmanId(spaces[correntSprace], Step.LEFT1);
            for (int i = 0; i < 4; i++) {
                //dirs[i]为当前方向
                corrent = dirs[i];
                Chessman nowChessman = this.getChessmanById(corrent);
                //为性能考虑，未使用optinnal进行判断
                if (nowChessman == null) {
                    continue;
                }
                lenth[0] = nowChessman.getType().getWidth();
                lenth[1] = nowChessman.getType().getHeight();
                //与当前方向呈直角为1
                if (lenth[i % 2] == 1) {
                    //加入当前方向
                    resultSteps.add(ChessmanStep.getInstance(nowChessman, directions[(2 * i + 4) % 8], spaceChangeds[correntSprace]));
                    //与当前方向平行也为1
                    if (lenth[(i + 1) % 2] == 1) {
                        //同方向移动两步
                        if (dirs[(i + 2) % 4] == '\0') {
                            resultSteps.add(ChessmanStep.getInstance(nowChessman, directions[(i + 2) % 4 + 8], spaceChangeds[1 - correntSprace]));
                        }
                        //判断逆时针45度方向
                        if (dirs[(i + 1) % 4] == '\0') {
                            resultSteps.add(ChessmanStep.getInstance(nowChessman, directions[(2 * i + 3) % 8], spaceChangeds[1 - correntSprace]));
                        }
                        //判断顺时针45度方向
                        if (dirs[(i + 3) % 4] == '\0') {
                            resultSteps.add(ChessmanStep.getInstance(nowChessman, directions[(2 * i + 5) % 8], spaceChangeds[1 - correntSprace]));
                        }
                    }
                    //与当前方向平行为2
                    else {
                        //同方向移动两步
                        if (dirs[(i + 2) % 4] == '\0') {
                            resultSteps.add(ChessmanStep.getInstance(nowChessman, directions[(i + 2) % 4 + 8], SP21));
                        }
                    }

                }
                //与当前方向呈直角为2
                else {
                    //判断顺时针90方向
                    if (dirs[(i + 1) % 4] == '\0' && searchChessmanId(spaces[1 - correntSprace], directions[2 * i]) == corrent) {
                        resultSteps.add(ChessmanStep.getInstance(nowChessman, directions[(2 * i + 4) % 8], SP12));
                    }
                }
            }
        }
        return resultSteps;
    }

    /**
     * 搜索以坐标移动目标位置的棋子类型
     *
     * @param corrdinate 搜索坐标
     * @param step       移动的步骤
     * @return 搜索到的坐标，若越界则返回'0'
     */
    private char searchChessmanId(Corrdinate corrdinate, Step step) {
        int x = step.moveX(corrdinate.getX_coordinate()),
                y = step.moveY(corrdinate.getY_coordinate());

        if (x < 0 || x > 3 || y < 0 || y > 4) {
            return '0';
        }
        return this.chessboardArr[y][x];
    }

    public Chessboard getChessboard() {
        return chessboard;
    }

    /**
     * 判断是否达到终止节点
     *
     * @return true代表终止节点
     */
    public boolean isEndNode() {
        ChessmanWithCoordinate chessman = chessboard.getChessmans().get(Chessman.曹操);
        return chessman.getXcoordinate() == 1 && chessman.getYcoordinate() == 3;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
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
        for (char[] line : chessboardArr) {
            for (char c : line) {
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
