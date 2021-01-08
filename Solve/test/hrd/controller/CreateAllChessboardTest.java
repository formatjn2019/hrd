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
        int []types={0,16,24,28,30,31};
        HashMap<Long,ChessboardItem> totalLeafMap=new HashMap<>();
        for(int type : types){
            long start=System.currentTimeMillis();
            ArrayList<Long> mirrorList=new ArrayList<>();
            Set<Chessboard> allChessboard = CreateAllChessboard.getAlltypeWithChche(type, new HashSet<>());
            System.out.println("type"+type+"共生成了"+allChessboard.size()+"棋局");
            Map<Long, ChessboardItem> chessboardItemMap = new HashMap<>();
            Map<Long, ChessboardItem> hashCodeItemMap = new HashMap<>();
            int index=0;
            for (Chessboard chessboard:allChessboard){
                CreateTree createTree = new CreateTree(chessboard.getState());
//                System.out.println("\t"+index+"\t"+chessboard.getState());
                createTree.calculateResult();
                chessboardItemMap.put(chessboard.getState(),new ChessboardItem(chessboard, createTree.getStack().size() - 1, "new"+index, type));
                hashCodeItemMap.put(chessboard.getMirror(),new ChessboardItem(chessboard, createTree.getStack().size() - 1, "new"+index, type));
                //去掉当前
                Stack<Chessboard> stack = createTree.getStack();
                if (!stack.isEmpty()){
                    stack.pop();
                }
                //去掉非叶子节点
                while (!stack.isEmpty()){
                    Chessboard pop = stack.pop();
                    mirrorList.add(pop.getMirror());
                    mirrorList.add(pop.getAdjectiveMirror());
                }
                index+=1;
            }
            for(Long mirror:mirrorList){
                hashCodeItemMap.remove(mirror);
            }
            WriteChessboards.writeChessbards("chessboards_"+type+".csv",chessboardItemMap);
            WriteChessboards.writeChessbards("chessboards_"+type+"_leafs.csv",hashCodeItemMap);
            IOConstantUtils.getChessboardItemMap().putAll(chessboardItemMap);
            totalLeafMap.putAll(hashCodeItemMap);
            System.out.println(System.currentTimeMillis()-start+"ms");
        }
        WriteChessboards.writeChessbards("totalLeafChessboards.csv",totalLeafMap);
        WriteChessboards.getInstance().writeChessbards();
    }

}