package hrd.modle;


import java.util.EnumMap;
import java.util.Map;

public class ChessmanStep {
    private static final Map<Chessman, Map<Step, Map<SpaceChanged, ChessmanStep>>> stepCache = new EnumMap<>(Chessman.class);
    /**
     * 提前生成所有棋子步骤
     */
    private static final ChessmanStep[][][] ALL_CHESSMANSTEPS = initAllChessmanSteps();
    private final Chessman chessman;
    private final Step step;
    private final SpaceChanged spaceChanged;

    private ChessmanStep(Chessman chessman, Step step, SpaceChanged spaceChanged) {
        this.chessman = chessman;
        this.step = step;
        this.spaceChanged = spaceChanged;
    }

    /**
     * 预先填充所有的棋子步骤到三维数组中缓存
     *
     * @return  填充完成的三维数组
     */
    private static ChessmanStep[][][] initAllChessmanSteps() {
        ChessmanStep[][][] result = new ChessmanStep[SpaceChanged.values().length][][];
        for (int i = 0; i < SpaceChanged.values().length; i++) {
            result[i] = new ChessmanStep[Chessman.values().length][];
            for (int j = 0; j < Chessman.values().length; j++) {
                result[i][j] = new ChessmanStep[Step.values().length];
                for (int k = 0; k < Step.values().length; k++) {
                    result[i][j][k] = new ChessmanStep(Chessman.values()[j], Step.values()[k], SpaceChanged.values()[i]);
                }
            }
        }

        return result;
    }

    /**
     * 获取实例对象
     *
     * @param chessman     棋子类型
     * @param step         步骤
     * @param spaceChanged 空格改变
     * @return 已经缓存的棋子
     */
    public static ChessmanStep getInstance(Chessman chessman, Step step, SpaceChanged spaceChanged) {
        return ALL_CHESSMANSTEPS[spaceChanged.ordinal()][chessman.ordinal()][step.ordinal()];
    }

    public Chessman getChessman() {
        return chessman;
    }

    public Step getStep() {
        return step;
    }

    public SpaceChanged getSpaceChanged() {
        return spaceChanged;
    }

    @Override
    public String toString() {
        return "ChessmanStep{" +
                "chessman=" + chessman +
                ", step=" + step +
                ", spaceChanged=" + spaceChanged +
                '}';
    }

    /**
     * 空格移动的枚举类型
     */
    public enum SpaceChanged {
        //只移动第一个空格
        SP1() {
            @Override
            public Coordinate moveSpace2(Step step, Coordinate coordinate, Chessman.ChessmanType chessmanType) {
                return coordinate;
            }
        },
        //只移动第二个空格
        SP2 {
            @Override
            public Coordinate moveSpace1(Step step, Coordinate coordinate, Chessman.ChessmanType chessmanType) {
                return coordinate;
            }
        },
        //移动两个空格（宽为2）
        SP12 {

        },
        //忽略厚度，用于移动两步的情况
        SP21 {
            @Override
            public Coordinate moveSpace1(Step step, Coordinate coordinate, Chessman.ChessmanType chessmanType) {
                return step.moveStep(coordinate);
            }

            @Override
            public Coordinate moveSpace2(Step step, Coordinate coordinate, Chessman.ChessmanType chessmanType) {
                return step.moveStep(coordinate);
            }
        };

        public Coordinate moveSpace1(Step step, Coordinate coordinate, Chessman.ChessmanType chessmanType) {
            return step.moveStep(coordinate, chessmanType);
        }

        public Coordinate moveSpace2(Step step, Coordinate coordinate, Chessman.ChessmanType chessmanType) {
            return step.moveStep(coordinate, chessmanType);
        }
    }
}
