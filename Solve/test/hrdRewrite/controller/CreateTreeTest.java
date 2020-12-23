package hrdRewrite.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CreateTreeTest {
    static CreateTree tree1;
    static CreateTree tree2;
    @BeforeAll
    static void init(){
//        tree1 = new CreateTree(9486557393564L);
//        tree1 = new CreateTree(9486557392508L);
        tree1 = new CreateTree(10036381364852L);
        tree2 = new CreateTree(9555276870300L);
    }
    @Test
    void printAllStep() {
        tree1.calculateResult();
        tree1.printAllStep();
    }

}