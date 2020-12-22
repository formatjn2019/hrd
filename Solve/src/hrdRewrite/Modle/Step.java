package hrdRewrite.Modle;
import static hrdRewrite.Modle.Step.Direction.*;
public enum Step {
    UP1(UP,1),
    UP2(UP,2),
    DOWN1(DOWN,1),
    DOWN2(DOWN,2),
    LEFT1(LEFT,1),
    LEFT2(LEFT,2),
    RIGHT1(RIGHT,1),
    RIGHT2(RIGHT,2);

    enum Direction{
        UP,DOWN,LEFT,RIGHT;
    }


    private final Direction dir;
    private final byte len;

    Step(Direction dir, int len) {
        this.dir = dir;
        this.len = (byte) len;
    }
    public Step getOppoisteStep(){
        return switch (this) {
            case UP1 -> DOWN1;
            case UP2 -> DOWN2;
            case DOWN1 -> UP1;
            case DOWN2 -> UP2;
            case LEFT1 -> RIGHT1;
            case LEFT2 -> RIGHT2;
            case RIGHT1 -> LEFT1;
            case RIGHT2 -> LEFT2;
            default -> null;
        };
    }

    public Direction getDir() {
        return dir;
    }

    public byte getLen() {
        return len;
    }
}
