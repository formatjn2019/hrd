package hrd.modle;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.EnumMap;

class TreeNodeTest {
    static TreeNode boot;
    static TreeNode treeNode;
    @BeforeAll
    static void init(){
        Chessboard chessboard = new Chessboard(new EnumMap<>(Chessman.class), 18387170901632856L);
//        Chessboard chessboard = new Chessboard(new EnumMap<>(Chessman.class), 9359138341900176L);
//        boot = new TreeNode(chessboard3);
        treeNode = new TreeNode(chessboard);
//        treeNode = new TreeNode(boot,ChessmanStep.getInstance(Chessman.兵2,Step.UP1,ChessmanStep.SpaceChanged.SP1));
    }
    @Test
    void getChessboardArr() {

    }

    @Test
    void getParent() {
    }

    @Test
    void getSteps() {
        for(ChessmanStep step: treeNode.getSteps()){
            System.out.println(step);
        }
    }

    @Test
    void getSpace1() {
    }

    @Test
    void getSpace2() {
    }

    @Test
    void getChessboard() {
    }

    @Test
    void isEndNode() {
    }

    @Test
    void testEquals() {
    }

    @Test
    void testHashCode() {
    }

    @Test
    void testToString() {
        System.out.println(treeNode);
    }
}