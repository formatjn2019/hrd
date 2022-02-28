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
    private final Coordinate space1;
    private final Coordinate space2;

    /**
     * 为根结点进行初始化
     *
     * @param bootChessboard 根结点
     */
    public TreeNode(Chessboard bootChessboard) {
        this.chessboard = bootChessboard;
        this.parent = null;
        char[][] arr = new char[5][4];
        chessboardArr = arr;
        for (ChessmanWithCoordinate chessman : this.chessboard.getChessmanMap().values()) {
            for (int i = chessman.getYcoordinate(); i < chessman.getYcoordinate() + chessman.getHeight(); i++) {
                for (int j = chessman.getXcoordinate(); j < chessman.getXcoordinate() + chessman.getWidth(); j++) {
                    arr[i][j] = chessman.getId();
                }
            }
        }
        space1 = searchSpace(arr, 0, 0, false);
        space2 = searchSpace(arr, space1.getY_coordinate(), space1.getX_coordinate(), true);
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
        EnumMap<Chessman, ChessmanWithCoordinate> enumMap = new EnumMap<>(parent.chessboard.getChessmanMap());
        ChessmanWithCoordinate beforeChangeChessman = enumMap.get(chessmanStep.getChessman());
        ChessmanWithCoordinate afterChangeChessman = beforeChangeChessman.movedStep(chessmanStep.getStep());
        //空格坐标移动
        space1 = chessmanStep.getSpaceChanged().moveSpace1(chessmanStep.getStep().getOppoisteStep(), parent.space1, chessmanStep.getChessman().getType());
        space2 = chessmanStep.getSpaceChanged().moveSpace2(chessmanStep.getStep().getOppoisteStep(), parent.space2, chessmanStep.getChessman().getType());
        enumMap.put(chessmanStep.getChessman(), afterChangeChessman);
        this.chessboard = new Chessboard(enumMap);
        //覆写新棋子
        for (int i = afterChangeChessman.getYcoordinate(); i < afterChangeChessman.getYcoordinate() + afterChangeChessman.getHeight(); i++) {
            for (int j = afterChangeChessman.getXcoordinate(); j < afterChangeChessman.getXcoordinate() + afterChangeChessman.getWidth(); j++) {
                parentChessboardArrClone[i][j] = afterChangeChessman.getId();
            }
        }
        //清空新棋子
        parentChessboardArrClone[space1.getY_coordinate()][space1.getX_coordinate()] = '\0';
        parentChessboardArrClone[space2.getY_coordinate()][space2.getX_coordinate()] = '\0';

        this.chessboardArr = parentChessboardArrClone;
    }

    /**
     * 搜索空格位置
     *
     * @param arr    搜索的数组
     * @param xStart 开始的横坐标
     * @param yStart 开始的纵坐标
     * @param flag   修改标志位
     * @return 空格坐标
     */
    private static Coordinate searchSpace(char[][] arr, int xStart, int yStart, boolean flag) {
        for (int i = xStart; i < 5; i++, flag = false) {
            for (int j = flag ? yStart : 0; j < 4; j++, flag = false) {
                if (arr[i][j] == '\0' && !flag) {
                    return Coordinate.getInstance(j, i);
                }
            }
        }
        throw new ArrayIndexOutOfBoundsException();
    }

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

    private Chessman getChessmanById(char id) {
        return switch (id) {
            case 'a' -> Chessman.CAO_CAO;
            case 'b' -> chessboard.getChessmanMap().get(Chessman.GUAN_YU_HORIZONTAL) == null ? Chessman.GUAN_YU_VERTICAL : Chessman.GUAN_YU_HORIZONTAL;
            case 'c' -> chessboard.getChessmanMap().get(Chessman.ZHANG_FEI_HORIZONTAL) == null ? Chessman.ZHANG_FEI_VERTICAL : Chessman.ZHANG_FEI_HORIZONTAL;
            case 'd' -> chessboard.getChessmanMap().get(Chessman.ZHAO_YUN_HORIZONTAL) == null ? Chessman.ZHAO_YUN_VERTICAL : Chessman.ZHAO_YUN_HORIZONTAL;
            case 'e' -> chessboard.getChessmanMap().get(Chessman.MA_CHAO_HORIZONTAL) == null ? Chessman.MA_CHAO_VERTICAL : Chessman.MA_CHAO_HORIZONTAL;
            case 'f' -> chessboard.getChessmanMap().get(Chessman.HUANG_ZHONG_HORIZONTAL) == null ? Chessman.HUANG_ZHONG_VERTICAL : Chessman.HUANG_ZHONG_HORIZONTAL;
            case 'g' -> Chessman.BING1;
            case 'h' -> Chessman.BING2;
            case 'i' -> Chessman.BING3;
            case 'j' -> Chessman.BING4;
            default -> null;
        };

    }

    /**
     * 获取该节点能获得的所有步骤
     */
    public LinkedList<ChessmanStep> getSteps() {
        LinkedList<ChessmanStep> resultSteps = new LinkedList<>();
        Coordinate[] spaces = {space1, space2};
        ChessmanStep.SpaceChanged[] spaceChangeds = {SP1, SP2};
        //当前字符
        char corrent;
        //方向字符
        char[] dirs = new char[4];
        //方向长度
        int[] lenth = new int[2];
        //根据空格判断
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
                    resultSteps.add(ChessmanStep.getInstance(nowChessman, Step.values()[(2 * i + 4) % 8], spaceChangeds[correntSprace]));
                    //与当前方向平行也为1
                    if (lenth[(i + 1) % 2] == 1) {
                        //同方向移动两步
                        if (dirs[(i + 2) % 4] == '\0') {
                            resultSteps.add(ChessmanStep.getInstance(nowChessman, Step.values()[(i + 2) % 4 + 8], spaceChangeds[1 - correntSprace]));
                        }
                        //判断逆时针45度方向
                        if (dirs[(i + 1) % 4] == '\0') {
                            resultSteps.add(ChessmanStep.getInstance(nowChessman, Step.values()[(2 * i + 3) % 8], spaceChangeds[1 - correntSprace]));
                        }
                        //判断顺时针45度方向
                        if (dirs[(i + 3) % 4] == '\0') {
                            resultSteps.add(ChessmanStep.getInstance(nowChessman, Step.values()[(2 * i + 5) % 8], spaceChangeds[1 - correntSprace]));
                        }
                    }
                    //与当前方向平行为2
                    else {
                        //同方向移动两步
                        if (dirs[(i + 2) % 4] == '\0') {
                            resultSteps.add(ChessmanStep.getInstance(nowChessman, Step.values()[(i + 2) % 4 + 8], SP21));
                        }
                    }

                }
                //与当前方向呈直角为2
                else {
                    //判断顺时针90方向
                    if (dirs[(i + 1) % 4] == '\0' && searchChessmanId(spaces[1 - correntSprace], Step.values()[2 * i]) == corrent) {
                        resultSteps.add(ChessmanStep.getInstance(nowChessman, Step.values()[(2 * i + 4) % 8], SP12));
                    }
                }
            }
        }
        return resultSteps;
    }

    /**
     * 搜索以坐标移动目标位置的棋子类型
     *
     * @param coordinate 搜索坐标
     * @param step       移动的步骤
     * @return 搜索到的坐标，若越界则返回'0'
     */
    private char searchChessmanId(Coordinate coordinate, Step step) {
        int x = step.moveX(coordinate.getX_coordinate()),
                y = step.moveY(coordinate.getY_coordinate());

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
        ChessmanWithCoordinate chessman = chessboard.getChessmanMap().get(Chessman.CAO_CAO);
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
