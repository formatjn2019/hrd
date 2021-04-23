package hrd.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CreateTreeTest {
    static CreateTree tree1;
    @BeforeAll
    static void init(){
        tree1 = new CreateTree(18387170901632856L);
//        tree1 = new CreateTree(2414554999695224L);
    }
    @Test
    void printAllStep() {

    }

    @Test
    void calculateResult() {
        System.out.println(tree1.calculateResult());
        tree1.printAllStep();
    }

    @Test
    void assessNoError(){
        tree1.calculateResult();
        assertEquals(tree1.toString(),"CreateTree{134}");
    }
}