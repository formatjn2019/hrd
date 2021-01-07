package hrd.controller;

import hrd.io.IOConstantUtils;
import hrd.io.WriteChessboards;
import hrd.modle.Chessboard;
import hrd.modle.ChessboardItem;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class CreateAllChessboardTest {

    @Test
    void getAlltypeWithChche() {
        int type=0;
        Set<Chessboard> allChessboard = CreateAllChessboard.getAlltypeWithChche(type, new HashSet<>());
        System.out.println(allChessboard.size());
        Map<Long, ChessboardItem> chessboardItemMap = new HashMap<>();
        Map<Long, ChessboardItem> hashCodeItemMap = new HashMap<>();
        ArrayList<CreateTree> createTrees = new ArrayList<>();
        int index=0;
        for (Chessboard chessboard:allChessboard){
            CreateTree createTree = new CreateTree(chessboard.getState());
            createTree.calculateResult();
            createTrees.add(createTree);
            chessboardItemMap.put(chessboard.getState(),new ChessboardItem(chessboard, createTree.getStack().size() - 1, "new"+index, type));
            hashCodeItemMap.put(chessboard.getMirror(),new ChessboardItem(chessboard, createTree.getStack().size() - 1, "new"+index, type));
            index+=1;
        }
        for (CreateTree createTree: createTrees){
//            去掉当前
            Stack<Chessboard> stack = createTree.getStack();
            if (!stack.isEmpty()){
                stack.pop();
            }
            //去掉非叶子节点
            while (!stack.empty()){
                Chessboard pop = stack.pop();
                hashCodeItemMap.remove(pop.getMirror());
                hashCodeItemMap.remove(pop.getAdjectiveMirror());
            }
        }

        IOConstantUtils.getChessboardItemMap().clear();
        IOConstantUtils.getChessboardItemMap().putAll(chessboardItemMap);
        WriteChessboards.getInstance().writeChessbards();
        IOConstantUtils.getChessboardItemMap().clear();
        IOConstantUtils.getChessboardItemMap().putAll(hashCodeItemMap);
        WriteChessboards.getInstance().writeChessbards();
    }

}