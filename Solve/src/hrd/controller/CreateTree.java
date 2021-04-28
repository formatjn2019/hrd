package hrd.controller;


import hrd.modle.Chessboard;
import hrd.modle.Chessman;
import hrd.modle.TreeNode;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class CreateTree {
    private final Set<Chessboard> chessboardSet;
    private final Chessboard root;
    private final StringBuilder message = new StringBuilder();
    private final ArrayDeque<Chessboard> stack = new ArrayDeque<>();
    private int totalStep = 0;

    /**
     * 建树初始化
     *
     * @param state 长整型，状态
     */
    public CreateTree(long state) {
        chessboardSet = Collections.newSetFromMap(new ConcurrentHashMap<>());
        root = new Chessboard(new EnumMap<>(Chessman.class), state);
    }

    /**
     * 打印计算结果
     */
    public void printAllStep() {
        System.out.println(message.toString());
        while (!stack.isEmpty()) {
            System.out.println(stack.pop());
        }
    }

    public ArrayDeque<Chessboard> getStack() {
        return stack;
    }


    /**
     * 计算结果
     *
     * @return 成功返回true 失败返回false 棋局非正常状态则发生异常
     */
    public boolean calculateResult() {
        TreeNode root = new TreeNode(this.root);
        List<TreeNode> nodes = Collections.singletonList(root);
        //当前节点，直接返回
        if (root.isEndNode()) {
            stack.add(root.getChessboard());
            return true;
        }
        TreeNode endNode = createlayerTree(nodes, 1);
        if (endNode == null) {
            return false;
        }
        //计算成功后，若拥有结果，将过程棋局入栈
        for (TreeNode tempNode = endNode; tempNode != null; tempNode = tempNode.getParent()) {
            stack.add(tempNode.getChessboard());
        }
        return true;
    }


    /**
     * 通过建树方法进行计算
     * 使用分支限界算法，广度优先遍历，递归
     *
     * @param nodes 新的节点数
     * @param level 树的层数
     * @return 返回计算后的最终节点
     */
    private TreeNode createlayerTree(List<TreeNode> nodes, int level) {
        AtomicBoolean succeedFlag = new AtomicBoolean(false);
        AtomicReference<TreeNode> endNode = new AtomicReference<>();
        List<TreeNode> newNodes = nodes.stream()
                .parallel()
                .flatMap((node -> node.getSteps().stream()
                        .map((step) -> new TreeNode(node, step))))
                .filter(newNode -> chessboardSet.add(newNode.getChessboard()))
                .peek(newNode -> {
                            if (newNode.isEndNode()) {
                                succeedFlag.set(true);
                                endNode.set(newNode);
                            }
                        }
                )
                .collect(Collectors.toCollection(ArrayList::new));

        message.append("level: ");
        message.append(level);
        message.append('\t');
        message.append("newNodes: ");
        message.append(newNodes.size());
        message.append("\n");
        totalStep += newNodes.size();

        if (succeedFlag.get() || newNodes.size() == 0) {
            message.insert(0, succeedFlag.get() ? "计算成功" : "计算失败");
            message.insert(0, "totalNode:" + chessboardSet.size() + "\n");
            message.insert(0, "totalStep:" + totalStep + "\n");
            //消除引用，节省内存
            chessboardSet.clear();
            return succeedFlag.get() ? endNode.get() : null;
        }
        return createlayerTree(newNodes, level + 1);
    }

    @Override
    public String toString() {
        return "CreateTree{" +
                "message=" + message +
                '}';
    }
}
