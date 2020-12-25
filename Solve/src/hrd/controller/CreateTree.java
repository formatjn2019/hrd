package hrd.controller;

import hrd.modle.Chessboard;
import hrd.modle.Chessman;
import hrd.modle.ChessmanStep;
import hrd.modle.TreeNode;

import java.util.*;

public class CreateTree {
    private final Set<Chessboard> chessboardSet;
    private final Chessboard root;
    StringBuffer message=new StringBuffer();
    private Stack<Chessboard> stack = new Stack<>();
    private int totalStep=0;
    CreateTree(long state){
        chessboardSet =new HashSet<>();
        root = new Chessboard(new EnumMap<>(Chessman.class), state);
    }

    public void printAllStep(){
        message.insert(0,"totalNode:"+chessboardSet.size()+"\n");
        message.insert(0,"totalStep:"+totalStep+"\n");
        System.out.println(message.toString());
        while (!stack.empty()){
            System.out.println(stack.pop());
        }
    }
    public void calculateResult(){
        TreeNode root = new TreeNode(this.root);
        ArrayList<TreeNode> nodes = new ArrayList<>();
        nodes.add(root);
        TreeNode endNode = createlayerTree(nodes,1);
        for (TreeNode tempNode = endNode;tempNode != null;tempNode=tempNode.getParent()){
            stack.add(tempNode.getChessboard());
        }
    }
    private TreeNode createlayerTree(ArrayList<TreeNode> nodes,int level){
        ArrayList<TreeNode> newNodes = new ArrayList<>();
        for (TreeNode node : nodes){
            for (ChessmanStep step : node.getSteps()){
                TreeNode newNode = new TreeNode(node,step);
                if (chessboardSet.add(newNode.getChessboard())){
                    newNodes.add(newNode);
                }
                if (newNode.isEndNode()){
                    return newNode;
                }
            }
        }
        message.append("level: ");
        message.append(level);
        message.append('\t');
        message.append("newNodes: ");
        message.append(newNodes.size());
        message.append("\n");
        totalStep+=newNodes.size();
//        System.out.println("level"+level);
//        System.out.println("newNodes"+newNodes.size());
        return createlayerTree(newNodes,level+1);
    }

}
