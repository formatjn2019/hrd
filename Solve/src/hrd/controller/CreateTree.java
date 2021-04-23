package hrd.controller;


import hrd.modle.Chessboard;
import hrd.modle.Chessman;
import hrd.modle.ChessmanStep;
import hrd.modle.TreeNode;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

public class CreateTree {
    private final Set<Chessboard> chessboardSet;
    private final Chessboard root;
    private final StringBuffer message = new StringBuffer();
    private final Stack<Chessboard> stack = new Stack<>();
    private final Object object = new Object();
    private int totalStep = 0;
    private static final int CORE = Runtime.getRuntime().availableProcessors();
    private TreeNode endNode;
    private volatile boolean endFlag;
    public static AtomicLong waitingThread = new AtomicLong(0L);
    public static AtomicLong atomicLong = new AtomicLong(0L);
    private final ConcurrentLinkedQueue<TreeNode> calculatingTreeNodes = new ConcurrentLinkedQueue<>();

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
        while (!stack.empty()) {
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
     *
     * @return 成功返回true 失败返回false 棋局非正常状态则发生异常
     */
    public boolean calculateResult() {
        TreeNode root = new TreeNode(this.root);
        calculatingTreeNodes.add(root);
        TreeNode endNode = createlayerTree();
        if (endNode == null) {
            return false;
        }
        //计算成功后，若拥有结果，将过程棋局入栈
        for (TreeNode tempNode = endNode; tempNode != null; tempNode = tempNode.getParent()) {
            stack.add(tempNode.getChessboard());
        }
        return true;
    }

    private class CalculateNewNodes implements Runnable {

        public CalculateNewNodes() {
            atomicLong.incrementAndGet();
        }


        @Override
        protected void finalize() throws Throwable {
            atomicLong.decrementAndGet();
            super.finalize();
        }

        @Override
        public void run() {
            while (waitingThread.get() != CORE) {
                TreeNode node = calculatingTreeNodes.poll();
                if (node == null) {
                    waitingThread.incrementAndGet();
                    //如果队列为空，则进入等待状态
                    synchronized (object) {
                        try {
                            object.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    waitingThread.decrementAndGet();
                } else {
                    for (ChessmanStep newstep : node.getSteps()) {
                        TreeNode newNode = new TreeNode(node, newstep);
                        if (chessboardSet.add(newNode.getChessboard())) {
                            calculatingTreeNodes.add(newNode);
                            if (waitingThread.get() > 0) {
                                synchronized (object) {
                                    object.notifyAll();
                                }
                            }
                        }
                        if (newNode.isEndNode()) {
                            endFlag = true;
                            endNode = newNode;
                            waitingThread.incrementAndGet();
                            break;
                        }
                    }
                }
            }
        }
    }


    /**
     * 通过建树方法进行计算
     * 使用分支限界算法，广度优先遍历，递归
     *
     * @return 返回计算后的最终节点
     */
    private TreeNode createlayerTree() {

        for (int i = 0; i < CORE; i++) new Thread(new CalculateNewNodes()).start();

        while (waitingThread.get() != CORE) {
            Thread.yield();
        }
        message.append("共生成了");
        message.append(chessboardSet.size());
        message.append("节点");

        //消除引用，节省内存
        calculatingTreeNodes.clear();
        chessboardSet.clear();
        return endNode;
    }

    @Override
    public String toString() {
        return "CreateTree{" +
                stack.size()+
                '}';
    }
}
