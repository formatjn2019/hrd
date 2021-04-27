package hrd.modle;


import java.util.EnumMap;
import java.util.Map;

public class ChessmanStep {
    private static final Map<Chessman, Map<Step, Map<SpaceChanged, ChessmanStep>>> stepCache = new EnumMap<>(Chessman.class);
    private final Chessman chessman;
    private final Step step;
    private final SpaceChanged spaceChanged;

    private ChessmanStep(Chessman chessman, Step step, SpaceChanged spaceChanged) {
        this.chessman = chessman;
        this.step = step;
        this.spaceChanged = spaceChanged;
    }

    public enum SpaceChanged {
        //只移动第一个空格
        SP1() {
            @Override
            public Corrdinate moveSpace2(Step step, Corrdinate corrdinate, Chessman.ChessmanType chessmanType) {
                return corrdinate;
            }
        },
        //只移动第二个空格
        SP2 {
            @Override
            public Corrdinate moveSpace1(Step step, Corrdinate corrdinate, Chessman.ChessmanType chessmanType) {
                return corrdinate;
            }
        },
        //移动两个空格（宽为2）
        SP12 {

        },
        //忽略厚度，用于移动两步的情况
        SP21 {
            @Override
            public Corrdinate moveSpace1(Step step, Corrdinate corrdinate, Chessman.ChessmanType chessmanType) {
                return step.moveStep(corrdinate);
            }
            @Override
            public Corrdinate moveSpace2(Step step, Corrdinate corrdinate, Chessman.ChessmanType chessmanType) {
                return step.moveStep(corrdinate);
            }
        };

        public Corrdinate moveSpace1(Step step, Corrdinate corrdinate, Chessman.ChessmanType chessmanType) {
            return step.moveStep(corrdinate, chessmanType);
        }

        public Corrdinate moveSpace2(Step step, Corrdinate corrdinate, Chessman.ChessmanType chessmanType) {
            return step.moveStep(corrdinate, chessmanType);
        }
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


    private static final ChessmanStep[][][] ALL_CHESSMANSTEPS = initAllChessmanSteps();

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

    @Override
    public String toString() {
        return "ChessmanStep{" +
                "chessman=" + chessman +
                ", step=" + step +
                ", spaceChanged=" + spaceChanged +
                '}';
    }
}
