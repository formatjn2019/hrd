package hrdRewrite.Modle;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.EnumMap;

import static org.junit.jupiter.api.Assertions.*;

class TreeNodeTest {
    static TreeNode boot;
    static TreeNode treeNode;
    @BeforeAll
    static void init(){
        Chessboard chessboard3 = new Chessboard(new EnumMap<>(Chessman.class), 9486557393564L);
//        boot = new TreeNode(chessboard3);
        treeNode = new TreeNode(chessboard3);
//        treeNode = new TreeNode(boot,ChessmanStep.getInstance(Chessman.兵2,Step.UP1,ChessmanStep.SpaceChanged.SP1));
    }
    @Test
    void getSteps() {
//        System.out.println(treeNode.getSteps());
        for (ChessmanStep step : treeNode.getSteps()){
            System.out.println(step);
        }
    }

    @Test
    void getSpace1() {
        System.out.println(treeNode.getSpace1());
    }

    @Test
    void getSpace2() {
        System.out.println(treeNode.getSpace2());
    }

    @Test
    void isEndNode() {
        System.out.println(treeNode.isEndNode());
    }

    @Test
    void testEquals() {
//        System.out.println();
    }

    @Test
    void testHashCode() {
        System.out.println(treeNode.hashCode());
    }

    @Test
    void testToString() {
        System.out.println(treeNode);
    }
}