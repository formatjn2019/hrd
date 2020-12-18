package hrdRewrite.Modle;

import java.util.EnumMap;
import java.util.Map;

public class ChessmanStep {
    private static final Map<Chessman,Map<Step,ChessmanStep>> stepCache= new EnumMap<>(Chessman.class);
    private final Chessman chessman;
    private final Step step;
    private ChessmanStep(Chessman chessman,Step step){
        this.chessman=chessman;
        this.step=step;
    }
    public ChessmanStep getInstance(Chessman chessman,Step step){
        Map<Step, ChessmanStep> stepMap = stepCache.get(chessman);
        if (stepMap == null){
            stepMap = new EnumMap<>(Step.class);
            stepCache.put(chessman,stepMap);
            ChessmanStep chessmanStep = new ChessmanStep(chessman, step);
            stepMap.put(step,chessmanStep);
            return chessmanStep;
        }
        ChessmanStep chessmanStep = stepMap.get(step);
        if (chessmanStep ==null){
            new ChessmanStep(chessman, step);
            stepMap.put(step,chessmanStep);
        }

        return chessmanStep;
    }
}
