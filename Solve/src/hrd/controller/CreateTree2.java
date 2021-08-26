package hrd.controller;


import hrd.modle.Chessboard;
import hrd.modle.Chessman;
import hrd.modle.ChessmanStep;
import hrd.modle.TreeNode;

import java.util.*;
import java.util.concurrent.*;

public class CreateTree2 {
    private final Set<Chessboard> chessboardSet;
    private final Chessboard root;
    private final StringBuffer message=new StringBuffer();
    private final Stack<Chessboard> stack = new Stack<>();
    private int totalStep=0;
    private static final int CORE =Runtime.getRuntime().availableProcessors();
    private final ExecutorService executorService =Executors.newFixedThreadPool(CORE);
    private TreeNode endNode;
    private volatile boolean endFlag;

    /**
     * 建树初始化
     * @param state 长整型，状态
     */
    public CreateTree2(long state){
        chessboardSet = Collections.newSetFromMap(new ConcurrentHashMap<>());
        root = new Chessboard(new EnumMap<>(Chessman.class), state);
    }

    /**
     * 打印计算结果
     */
    public void printAllStep(){
        System.out.println(message.toString());
        while (!stack.empty()){
            System.out.println(stack.pop());
        }
    }

    public Stack<Chessboard> getStack() {
        return stack;
    }

    public int getTotalStep() {
        return totalStep;
    }

    /**
     * 计算结果
     * @return 成功返回true 失败返回false 棋局非正常状态则发生异常
     */
    public boolean calculateResult(){
        TreeNode root = new TreeNode(this.root);
        ArrayList<TreeNode> nodes = new ArrayList<>();
        nodes.add(root);
        //当前节点，直接返回
        if (root.isEndNode()){
            stack.add(root.getChessboard());
            return true;
        }
        TreeNode endNode = createlayerTree(nodes,1);
        if (endNode == null){
            return false;
        }
        //计算成功后，若拥有结果，将过程棋局入栈
        for (TreeNode tempNode = endNode;tempNode != null;tempNode=tempNode.getParent()){
            stack.add(tempNode.getChessboard());
        }
        return true;
    }

    private class CalculateNewNodes implements Callable<List<TreeNode>> {
        private final List<TreeNode> treeNodes ;

        //分块计算
        public CalculateNewNodes(List<TreeNode> treeNodes) {
            this.treeNodes = treeNodes;
        }

        @Override
        public List<TreeNode> call(){
            ArrayList<TreeNode> newNodes = new ArrayList<>();
            for (TreeNode treeNode:treeNodes){
                for (ChessmanStep step : treeNode.getSteps()) {
                    TreeNode newNode = new TreeNode(treeNode, step);
                    if (chessboardSet.add(newNode.getChessboard())) {
                        if (newNode.isEndNode()){
                            endNode=newNode;
                            endFlag = true;
                            return newNodes;
                        }else {
                            newNodes.add(newNode);
                        }
                    }
                }
            }
            return newNodes;
        }

    }


    /**
     * 通过建树方法进行计算
     * 使用分支限界算法，广度优先遍历，递归
     * @param nodes 新的节点数
     * @param level 树的层数
     * @return 返回计算后的最终节点
     */
    private TreeNode createlayerTree(ArrayList<TreeNode> nodes,int level){
        ArrayList<TreeNode> newNodes = new ArrayList<>();
        List<Future<List<TreeNode>>> futureTask=new ArrayList<>();
        int stepLenth=nodes.size()/CORE;
        //太少了的时候，不进行多线程分割
        if (stepLenth<3){
            for (TreeNode node : nodes){
                for (ChessmanStep newstep : node.getSteps()){
                    TreeNode newNode = new TreeNode(node,newstep);
                    if (chessboardSet.add(newNode.getChessboard())){
                        newNodes.add(newNode);
                    }
                    if (newNode.isEndNode()){
                        message.append("level: ");
                        message.append(level);
                        message.append('\t');
                        message.append("newNodes: ");
                        message.append(newNodes.size());
                        message.append("\n");
                        totalStep+=newNodes.size();
                        message.insert(0,"计算成功");
                        message.insert(0,"totalNode:"+chessboardSet.size()+"\n");
                        message.insert(0,"totalStep:"+totalStep+"\n");
                        //消除引用，节省内存
                        chessboardSet.clear();
                        return newNode;
                    }
                }
            }
        }else {
            for (int i = 0,j=nodes.size()%CORE,start=0,end;i<CORE;i++,j--,start=end){
                end=start+(j>0?stepLenth+1:stepLenth);
                List<TreeNode> subList  = nodes.subList(start,end);
                Future<List<TreeNode>> future = executorService.submit(new CalculateNewNodes(subList));
                futureTask.add(future);
            }

            for (Future<List<TreeNode>> future:futureTask){
                try {
                    newNodes.addAll(future.get());
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }


        if (newNodes.size() == 0){
            message.insert(0,"计算失败");
            message.insert(0,"totalNode:"+chessboardSet.size()+"\n");
            message.insert(0,"totalStep:"+totalStep+"\n");
            //消除引用，节省内存
            chessboardSet.clear();
            return null;
        }

        message.append("level: ");
        message.append(level);
        message.append('\t');
        message.append("newNodes: ");
        message.append(newNodes.size());
        message.append("\n");
        totalStep+=newNodes.size();
        if(endFlag){
            return endNode;
        }
        return createlayerTree(newNodes,level+1);
    }

}
