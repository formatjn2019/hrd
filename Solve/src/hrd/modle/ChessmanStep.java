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
        SP1, SP2, SP12, SP21
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

    private static ChessmanStep[][][] initAllChessmanSteps2() {
        ChessmanStep[][][] result = new ChessmanStep[Chessman.values().length][Step.values().length][SpaceChanged.values().length];
        for (int i = 0; i < Chessman.values().length; i++) {
            for (int j = 0; j < Step.values().length; j++) {
                for (int k = 0; k < SpaceChanged.values().length; k++) {
                    result[i][j][k] = new ChessmanStep(Chessman.values()[i], Step.values()[j], SpaceChanged.values()[k]);
                }
            }
        }

        return result;
    }

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
