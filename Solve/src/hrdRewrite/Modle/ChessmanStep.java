package hrdRewrite.Modle;

import java.util.EnumMap;
import java.util.Map;

public class ChessmanStep {
    private static final Map<Chessman,Map<Step,Map<SpaceChanged,ChessmanStep>>> stepCache= new EnumMap<>(Chessman.class);
    private final Chessman chessman;
    private final Step step;
    private final SpaceChanged spaceChanged;
    private ChessmanStep(Chessman chessman,Step step,SpaceChanged spaceChanged){
        this.chessman=chessman;
        this.step=step;
        this.spaceChanged= spaceChanged;
    }
    public enum SpaceChanged {
        SP1,SP2,SP12;
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

    public static ChessmanStep getInstance(Chessman chessman, Step step, SpaceChanged spaceChanged){
        Map<Step, Map<SpaceChanged, ChessmanStep>> stepMap = stepCache.get(chessman);
        if (stepMap == null){
            stepMap = new EnumMap<>(Step.class);
            stepCache.put(chessman,stepMap);
            }
        Map<SpaceChanged, ChessmanStep> spaceMap = stepMap.get(step);
        if (spaceMap == null){
            spaceMap = new EnumMap<>(SpaceChanged.class);
            stepMap.put(step,spaceMap);
        }
        ChessmanStep chessmanStep = spaceMap.get(spaceChanged);
        if (chessmanStep ==null){
            chessmanStep=new ChessmanStep(chessman, step,spaceChanged);
            spaceMap.put(spaceChanged,chessmanStep);
        }
        return chessmanStep;
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
