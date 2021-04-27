package hrd.modle;

import static hrd.modle.Step.Direction.*;

public enum Step {
    UP2(UP, 2),
    UP1(UP, 1),
    DOWN1(DOWN, 1),
    DOWN2(DOWN, 2),
    LEFT1(LEFT, 1),
    LEFT2(LEFT, 2),
    RIGHT1(RIGHT, 1),
    RIGHT2(RIGHT, 2),
    UPLEFT1(UPLEFT, 1),
    UPRIGHT1(UPRIGHT, 1),
    DOWNLEFT1(DOWNLEFT, 1),
    DOWNRIGHT1(DOWNRIGHT, 1);

    enum Direction {
        UP() {
            @Override
            Corrdinate moveStep(Corrdinate origin, int lenth, Chessman.ChessmanType chessmanType) {
                return Corrdinate.getInstance(origin.getX_coordinate(), origin.getY_coordinate() - lenth - (chessmanType.getHeight() - 1));
            }
        },
        DOWN {
            @Override
            Corrdinate moveStep(Corrdinate origin, int lenth, Chessman.ChessmanType chessmanType) {
                return Corrdinate.getInstance(origin.getX_coordinate(), origin.getY_coordinate() + lenth + (chessmanType.getHeight() - 1));
            }
        },
        LEFT {
            @Override
            Corrdinate moveStep(Corrdinate origin, int lenth, Chessman.ChessmanType chessmanType) {
                return Corrdinate.getInstance(origin.getX_coordinate() - lenth - (chessmanType.getWidth() - 1), origin.getY_coordinate());
            }
        },
        RIGHT {
            @Override
            Corrdinate moveStep(Corrdinate origin, int lenth, Chessman.ChessmanType chessmanType) {
                return Corrdinate.getInstance(origin.getX_coordinate() + lenth + (chessmanType.getWidth() - 1), origin.getY_coordinate());
            }
        },
        UPLEFT {
            @Override
            Corrdinate moveStep(Corrdinate origin, int lenth, Chessman.ChessmanType chessmanType) {
                return Corrdinate.getInstance(origin.getX_coordinate() - lenth, origin.getY_coordinate() - lenth);
            }
        },
        UPRIGHT {
            @Override
            Corrdinate moveStep(Corrdinate origin, int lenth, Chessman.ChessmanType chessmanType) {
                return Corrdinate.getInstance(origin.getX_coordinate() + lenth, origin.getY_coordinate() - lenth);
            }
        },
        DOWNLEFT {
            @Override
            Corrdinate moveStep(Corrdinate origin, int lenth, Chessman.ChessmanType chessmanType) {
                return Corrdinate.getInstance(origin.getX_coordinate() - lenth, origin.getY_coordinate() + lenth);
            }
        },
        DOWNRIGHT {
            @Override
            Corrdinate moveStep(Corrdinate origin, int lenth, Chessman.ChessmanType chessmanType) {
                return Corrdinate.getInstance(origin.getX_coordinate() + lenth, origin.getY_coordinate() + lenth);
            }
        };

        abstract Corrdinate moveStep(Corrdinate corrdinate, int lenth, Chessman.ChessmanType chessmanType);
    }


    private final Direction dir;
    private final byte len;

    Step(Direction dir, int len) {
        this.dir = dir;
        this.len = (byte) len;
    }

    /**
     * 不计较棋子厚度进行移动
     *
     * @param origin 棋子原始坐标
     * @return 新坐标
     */
    public Corrdinate moveStep(Corrdinate origin) {
        return this.dir.moveStep(origin, this.len, Chessman.ChessmanType.BING);
    }

    /**
     * 计算棋子厚度进行移动
     *
     * @param origin       棋子原始坐标
     * @param chessmanType 棋子类型
     * @return 新坐标
     */
    public Corrdinate moveStep(Corrdinate origin, Chessman.ChessmanType chessmanType) {
        return this.dir.moveStep(origin, this.len, chessmanType);
    }

    public Step getOppoisteStep() {
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

    /**
     * 获取枚举的步骤
     *
     * @param dir 方向
     * @param len 长度
     * @return 返回枚举长度
     */
    public static Step getInstance(Direction dir, int len) {
        switch (dir) {
            case UP -> {
                return len == 1 ? UP1 : UP2;
            }
            case DOWN -> {
                return len == 1 ? DOWN1 : DOWN2;
            }
            case LEFT -> {
                return len == 1 ? LEFT1 : LEFT2;
            }
            case RIGHT -> {
                return len == 1 ? RIGHT1 : RIGHT2;
            }
        }
        return null;
    }

    /**
     * 返回枚举的长度
     *
     * @param dir  方向1
     * @param dir2 方向2
     * @return 返回枚举长度
     */
    public static Step getInstance(Direction dir, Direction dir2) {
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

    /**
     * 获取方向
     *
     * @return 枚举的方向
     */
    public Direction getDir() {
        return dir;
    }

    /**
     * 获取长度
     *
     * @return 返回长度
     */
    public byte getLen() {
        return len;
    }
}
