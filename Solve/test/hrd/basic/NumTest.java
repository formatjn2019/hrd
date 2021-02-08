package hrd.basic;

import hrd.controller.CreateTree;
import hrd.modle.ChessmanStep;
import hrd.modle.TreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class NumTest {
    public static void main(String[] args) {
//        for(int core =1;core<100;core*=2){
        int core = 8;

        int []items= new int[core];
            for (int num=16;num<42;num++){
                System.out.println(num);
                int stepLenth=num/core;
                for (int i =0,j=num%core,start=0,end;i<core;i++,j--,start=end){
                    end=start+(j>0?stepLenth+1:stepLenth);
                    System.out.println(start+","+end);
                }

            }
//        }
    }
}
