package hrd.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CreateTreeTest {
    static CreateTree tree1;
    @BeforeAll
    static void init(){
        tree1 = new CreateTree(9388466791617420L);
//        tree1 = new CreateTree(9640613352197776L);
    }
    @Test
    void printAllStep() {

    }

    @Test
    void calculateResult() {
        System.out.println(tree1.calculateResult());
        tree1.printAllStep();
    }
}