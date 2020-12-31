package hrdRewrite.modle;

import static hrdRewrite.modle.Step.Direction.*;

public enum Step {
    UP1(UP,1),
    UP2(UP,2),
    DOWN1(DOWN,1),
    DOWN2(DOWN,2),
    LEFT1(LEFT,1),
    LEFT2(LEFT,2),
    RIGHT1(RIGHT,1),
    RIGHT2(RIGHT,2),
    UPLEFT1(UPLEFT,1),
    UPRIGHT1(UPRIGHT,1),
    DOWNLEFT1(DOWNLEFT,1),
    DOWNRIGHT1(DOWNRIGHT,1);

    enum Direction{
        UP,DOWN,LEFT,RIGHT,UPLEFT, UPRIGHT, DOWNLEFT, DOWNRIGHT

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
            case UPLEFT1 -> DOWNRIGHT1;
            case UPRIGHT1 -> DOWNLEFT1;
            case DOWNLEFT1 -> UPRIGHT1;
            case DOWNRIGHT1 -> UPLEFT1;
            default -> throw new IllegalStateException("Error Driection");
        };
    }

    public static Step getInstance(Direction dir,int len) {
        switch (dir) {
            case UP -> {
                return len==1 ? UP1 : UP2;
            }
            case DOWN -> {
                return len==1 ? DOWN1 : DOWN2;
            }
            case LEFT -> {
                return len==1 ? LEFT1 : LEFT2;
            }
            case RIGHT -> {
                return len==1 ? RIGHT1 : RIGHT2;
            }
        }
        return null;
    }

    public static Step getInstance(Direction dir,Direction dir2) {
        switch (dir) {
            case UP -> {
                switch (dir2) {
                    case LEFT -> {
                        return UPLEFT1;
                    }
                    case RIGHT -> {
                        return UPRIGHT1;
                    }
                }
            }
            case DOWN -> {
                switch (dir2) {
                    case LEFT -> {
                        return DOWNLEFT1;
                    }
                    case RIGHT -> {
                        return DOWNRIGHT1;
                    }
                }
            }
            case LEFT -> {
                switch (dir2) {
                    case UP -> {
                        return UPLEFT1;
                    }
                    case DOWN -> {
                        return DOWNLEFT1;
                    }
                }
            }
            case RIGHT -> {
                switch (dir2) {
                    case UP -> {
                        return UPRIGHT1;
                    }
                    case DOWN -> {
                        return DOWNRIGHT1;
                    }
                }
            }
        }
        return null;
    }

    public Direction getDir() {
        return dir;
    }

    public byte getLen() {
        return len;
    }
}
