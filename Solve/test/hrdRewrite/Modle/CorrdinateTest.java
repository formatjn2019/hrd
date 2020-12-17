package hrdRewrite.Modle;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CorrdinateTest {
    static Corrdinate cor1,cor2,cor3,cor4;

    @BeforeAll
    static void init(){
        byte x=1,y=2;
        cor1=Corrdinate.getInstance(x,y);
        cor2=Corrdinate.getInstance(x,y);
        cor3=Corrdinate.getInstance(y,x);
        cor4=Corrdinate.getInstance(y,y);
    }



    @Test
    void getInstance() {
        System.out.println(cor1 == cor2);
    }

    @Test
    void testEquals() {
        byte x=1,y=1;
        Corrdinate cor1,cor2;
        cor1=Corrdinate.getInstance(x,y);
        cor2=Corrdinate.getInstance(x,y);
        System.out.println(cor1.hashCode());
        System.out.println(cor2.hashCode());
    }

    @Test
    void testHashCode() {
        System.out.println(cor1.hashCode());
        System.out.println(cor3.hashCode());
    }

    @Test
    void testToString() {
        System.out.println(cor1);
        System.out.println(cor4);
    }
}