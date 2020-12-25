package hrdRewrite.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CreateTreeTest {
    static CreateTree tree1;
    static CreateTree tree2;
    @BeforeAll
    static void init(){
        tree1 = new CreateTree(9484040810108L);     //横刀立马开局 98 未更改，单步执行
//        tree1 = new CreateTree(9493956003609L);     //五将逼宫开局 41 未更改，单步执行
//        tree1 = new CreateTree(11754317037660L);     //四面楚歌开局 62 未更改，单步执行
//        tree1 = new CreateTree(9486575152762L);     //将守角楼开局 84 未更改，单步执行
//        tree1 = new CreateTree(7972982964795L);     //火烧连营开局 48 未更改，单步执行
//        tree1 = new CreateTree(7972982964795L);     //火烧连营开局 48 未更改，单步执行
//        tree2 = new CreateTree(9555276870300L);
    }
    @Test
    void printAllStep() {
        tree1.calculateResult();
        tree1.printAllStep();
    }

}