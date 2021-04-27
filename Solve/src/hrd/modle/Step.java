package hrd.modle;

import static hrd.modle.Step.Direction.*;

public enum Step {
    UP1(UP, 1),
    UPRIGHT1(UPRIGHT, 1),
    RIGHT1(RIGHT, 1),
    DOWNRIGHT1(DOWNRIGHT, 1),
    DOWN1(DOWN, 1),
    DOWNLEFT1(DOWNLEFT, 1),
    LEFT1(LEFT, 1),
    UPLEFT1(UPLEFT, 1),
    UP2(UP, 2),
    RIGHT2(RIGHT, 2),
    DOWN2(DOWN, 2),
    LEFT2(LEFT, 2);

    enum Direction {
        UP() {
            @Override
            int moveX(int xOrigin, int lenth) {
                return xOrigin;
            }

            @Override
            int moveY(int yOrigin, int lenth) {
                return yOrigin - lenth;
            }
        },
        DOWN {
            @Override
            int moveX(int xOrigin, int lenth) {
                return xOrigin;
            }

            @Override
            int moveY(int yOrigin, int lenth) {
                return yOrigin + lenth;
            }
        },
        LEFT {
            @Override
            int moveX(int xOrigin, int lenth) {
                return xOrigin - lenth;
            }

            @Override
            int moveY(int yOrigin, int lenth) {
                return yOrigin;
            }
        },
        RIGHT {
            @Override
            int moveX(int xOrigin, int lenth) {
                return xOrigin + lenth;
            }

            @Override
            int moveY(int yOrigin, int lenth) {
                return yOrigin;
            }
        },
        UPLEFT {
            @Override
            int moveX(int xOrigin, int lenth) {
                return xOrigin - 1;
            }

            @Override
            int moveY(int yOrigin, int lenth) {
                return yOrigin - 1;
            }
        },
        UPRIGHT {
            @Override
            int moveX(int xOrigin, int lenth) {
                return xOrigin + 1;
            }

            @Override
            int moveY(int yOrigin, int lenth) {
                return yOrigin - 1;
            }
        },
        DOWNLEFT {
            @Override
            int moveX(int xOrigin, int lenth) {
                return xOrigin - 1;
            }

            @Override
            int moveY(int yOrigin, int lenth) {
                return yOrigin + 1;
            }

        },
        DOWNRIGHT {
            @Override
            int moveX(int xOrigin, int lenth) {
                return xOrigin + 1;
            }

            @Override
            int moveY(int yOrigin, int lenth) {
                return yOrigin + 1;
            }
        };

        abstract int moveX(int xOrigin, int lenth);

        abstract int moveY(int yOrigin, int lenth);

        public Corrdinate moveStep(Corrdinate origin, int lenth, Chessman.ChessmanType chessmanType) {
            return Corrdinate.getInstance(moveX(origin.getX_coordinate(), lenth + chessmanType.getWidth() - 1), moveY(origin.getY_coordinate(), lenth + chessmanType.getHeight() - 1));
        }
    }


    private final Direction dir;
    private final byte len;

    Step(Direction dir, int len) {
        this.dir = dir;
        this.len = (byte) len;
    }

    public int moveX(int xOrigin) {
        return this.dir.moveX(xOrigin, this.len);
    }

    public int moveY(int yOrigin) {
        return this.dir.moveY(yOrigin, this.len);
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
        return Step.values()[this.ordinal() < 8 ? (this.ordinal() + 4) % 8 : ((this.ordinal() - 6) % 4 + 8)];
    }

//    /**
//     * 获取枚举的步骤
//     *
//     * @param dir 方向
//     * @param len 长度
//     * @return 返回枚举长度
//     */
//    public static Step getInstance(Direction dir, int len) {
//        switch (dir) {
//            case UP -> {
//                return len == 1 ? UP1 : UP2;
//            }
//            case DOWN -> {
//                return len == 1 ? DOWN1 : DOWN2;
//            }
//            case LEFT -> {
//                return len == 1 ? LEFT1 : LEFT2;
//            }
//            case RIGHT -> {
//                return len == 1 ? RIGHT1 : RIGHT2;
//            }
//        }
//        return null;
//    }

    /**
     * 返回枚举的长度
     *
     * @param dir  方向1
     * @param dir2 方向2
     * @return 返回枚举长度
     */
//    public static Step getInstance(Direction dir, Direction dir2) {
//        switch (dir) {
//            case UP -> {
//                switch (dir2) {
//                    case LEFT -> {
//                        return UPLEFT1;
//                    }
//                    case RIGHT -> {
//                        return UPRIGHT1;
//                    }
//                }
//            }
//            case DOWN -> {
//                switch (dir2) {
//                    case LEFT -> {
//                        return DOWNLEFT1;
//                    }
//                    case RIGHT -> {
//                        return DOWNRIGHT1;
//                    }
//                }
//            }
//            case LEFT -> {
//                switch (dir2) {
//                    case UP -> {
//                        return UPLEFT1;
//                    }
//                    case DOWN -> {
//                        return DOWNLEFT1;
//                    }
//                }
//            }
//            case RIGHT -> {
//                switch (dir2) {
//                    case UP -> {
//                        return UPRIGHT1;
//                    }
//                    case DOWN -> {
//                        return DOWNRIGHT1;
//                    }
//                }
//            }
//        }
//        return null;
//    }

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
