package hrd.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CreateTreeTest {
    static CreateTree tree1;

    @BeforeAll
    static void init() {
//        tree1 = new CreateTree(18387170901632856L);
        tree1 = new CreateTree(9359138341900176L);
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
    void assessNoError() {
        tree1.calculateResult();
        assertEquals(tree1.toString(), "CreateTree{message=totalStep:23922\n" +
                "totalNode:23922\n" +
                "计算成功level: 1\tnewNodes: 8\n" +
                "level: 2\tnewNodes: 11\n" +
                "level: 3\tnewNodes: 15\n" +
                "level: 4\tnewNodes: 18\n" +
                "level: 5\tnewNodes: 18\n" +
                "level: 6\tnewNodes: 23\n" +
                "level: 7\tnewNodes: 28\n" +
                "level: 8\tnewNodes: 42\n" +
                "level: 9\tnewNodes: 42\n" +
                "level: 10\tnewNodes: 50\n" +
                "level: 11\tnewNodes: 44\n" +
                "level: 12\tnewNodes: 50\n" +
                "level: 13\tnewNodes: 46\n" +
                "level: 14\tnewNodes: 52\n" +
                "level: 15\tnewNodes: 43\n" +
                "level: 16\tnewNodes: 59\n" +
                "level: 17\tnewNodes: 62\n" +
                "level: 18\tnewNodes: 70\n" +
                "level: 19\tnewNodes: 64\n" +
                "level: 20\tnewNodes: 74\n" +
                "level: 21\tnewNodes: 48\n" +
                "level: 22\tnewNodes: 64\n" +
                "level: 23\tnewNodes: 81\n" +
                "level: 24\tnewNodes: 91\n" +
                "level: 25\tnewNodes: 113\n" +
                "level: 26\tnewNodes: 156\n" +
                "level: 27\tnewNodes: 197\n" +
                "level: 28\tnewNodes: 187\n" +
                "level: 29\tnewNodes: 240\n" +
                "level: 30\tnewNodes: 295\n" +
                "level: 31\tnewNodes: 373\n" +
                "level: 32\tnewNodes: 456\n" +
                "level: 33\tnewNodes: 487\n" +
                "level: 34\tnewNodes: 551\n" +
                "level: 35\tnewNodes: 650\n" +
                "level: 36\tnewNodes: 717\n" +
                "level: 37\tnewNodes: 774\n" +
                "level: 38\tnewNodes: 906\n" +
                "level: 39\tnewNodes: 946\n" +
                "level: 40\tnewNodes: 1038\n" +
                "level: 41\tnewNodes: 1084\n" +
                "level: 42\tnewNodes: 1042\n" +
                "level: 43\tnewNodes: 890\n" +
                "level: 44\tnewNodes: 804\n" +
                "level: 45\tnewNodes: 739\n" +
                "level: 46\tnewNodes: 667\n" +
                "level: 47\tnewNodes: 595\n" +
                "level: 48\tnewNodes: 517\n" +
                "level: 49\tnewNodes: 420\n" +
                "level: 50\tnewNodes: 340\n" +
                "level: 51\tnewNodes: 258\n" +
                "level: 52\tnewNodes: 178\n" +
                "level: 53\tnewNodes: 153\n" +
                "level: 54\tnewNodes: 151\n" +
                "level: 55\tnewNodes: 170\n" +
                "level: 56\tnewNodes: 174\n" +
                "level: 57\tnewNodes: 162\n" +
                "level: 58\tnewNodes: 140\n" +
                "level: 59\tnewNodes: 164\n" +
                "level: 60\tnewNodes: 160\n" +
                "level: 61\tnewNodes: 172\n" +
                "level: 62\tnewNodes: 196\n" +
                "level: 63\tnewNodes: 240\n" +
                "level: 64\tnewNodes: 278\n" +
                "level: 65\tnewNodes: 274\n" +
                "level: 66\tnewNodes: 298\n" +
                "level: 67\tnewNodes: 329\n" +
                "level: 68\tnewNodes: 348\n" +
                "level: 69\tnewNodes: 381\n" +
                "level: 70\tnewNodes: 408\n" +
                "level: 71\tnewNodes: 383\n" +
                "level: 72\tnewNodes: 412\n" +
                "level: 73\tnewNodes: 371\n" +
                "level: 74\tnewNodes: 305\n" +
                "level: 75\tnewNodes: 277\n" +
                "level: 76\tnewNodes: 261\n" +
                "level: 77\tnewNodes: 260\n" +
                "level: 78\tnewNodes: 242\n" +
                "level: 79\tnewNodes: 201\n" +
                "level: 80\tnewNodes: 159\n" +
                "level: 81\tnewNodes: 130\n" +
                "}");
    }
}