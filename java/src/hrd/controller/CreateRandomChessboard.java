package hrd.controller;

import hrd.modle.Chessboard;
import hrd.modle.Chessman;
import hrd.modle.ChessmanWithCoordinate;
import hrd.modle.Coordinate;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class CreateRandomChessboard {
    private static final int MAX_TRY_NUMS = 10_0000_0000;

    /**
     * 获取随机棋局
     *
     * @param type 类型，共有0，16，24，28，30，31六种类型
     * @return 根据当前时间生成随机数制造的棋局，但是不能保证棋局有解
     */
    public static Optional<Chessboard> getRandomChessboard(int type) {
        Chessman[] chessmans = new Chessman[10];
        boolean[] types = new boolean[10];
        char[][] chars = new char[5][4];
        Map<Chessman, ChessmanWithCoordinate> chessmanMap = new EnumMap<>(Chessman.class);
        //本地随机数数生成
        ThreadLocalRandom current = ThreadLocalRandom.current();
        //解析类型
        for (int i = 5; i > 0; i--) {
            types[i] = (type & 0x1) == 1;
            type >>= 1;
        }
        for (int i = 0; i < 10; i++) {
            Chessman chessman = Chessman.getInstanceByID((char) ('a' + i), types[i] ? Chessman.ChessmanType.HORIZONTAL : Chessman.ChessmanType.VERTICAL);
            chessmans[i] = chessman;
            int x = 0, y = 0;
            boolean flag = true;
            while (flag) {
                x = current.nextInt(0, 5 - chessman.getType().getWidth());
                y = current.nextInt(0, 6 - chessman.getType().getHeight());
                flag = false;
                outter1:
                for (int ty = y; ty < y + chessman.getType().getHeight(); ty++) {
                    for (int tx = x; tx < x + chessman.getType().getWidth(); tx++) {
                        if (chars[ty][tx] != '\0') {
                            flag = true;
                            break outter1;
                        }
                    }
                }
            }

            for (int ty = y; ty < y + chessman.getType().getHeight(); ty++) {
                for (int tx = x; tx < x + chessman.getType().getWidth(); tx++) {
                    chars[ty][tx] = chessman.getId();
                }
            }
            chessmanMap.put(chessmans[i], ChessmanWithCoordinate.getInstance(chessman, Coordinate.getInstance((byte) x, (byte) y)));
        }
        return Optional.of(new Chessboard(chessmanMap));
    }

    /**
     * 获取指定等级棋局
     *
     * @param type 棋局类型
     * @param step 步骤下限
     * @return 返回有解的棋局
     */
    public static Optional<Chessboard> getRandomChessboard(int type, int step) {
        int total = 0;
        while (true) {
            Optional<Chessboard> optionalChessboard = getRandomChessboard(type);
            if (optionalChessboard.isPresent()) {
                Chessboard randomChessboard = optionalChessboard.get();
                CreateTree ct = new CreateTree(randomChessboard.getState());
                if (ct.calculateResult()) {
                    if (ct.getStack().size() <= step) {
                        continue;
                    }
                    System.out.println(total);
                    System.out.println(randomChessboard);
                    System.out.println(ct.getStack().size() - 1 + "步");
                    return optionalChessboard;
                }
            }

            total += 1;
            if (total > MAX_TRY_NUMS) {
                return Optional.empty();
            }

        }
    }

    /**
     * 使用缓存高速生成棋局
     *
     * @param type          类型
     * @param cachedMirrors 已有的镜像
     * @return 返回生成的棋局
     */
    public static Optional<Chessboard> getRandomChessbordWithCache(int type, Set<Long> cachedMirrors) {
        Chessman[] chessmans = new Chessman[10];
        boolean[] types = new boolean[10];
        char[][] chars = new char[5][4];
        Map<Chessman, ChessmanWithCoordinate> chessmanMap = new EnumMap<>(Chessman.class);
        //本地随机数数生成
        ThreadLocalRandom current = ThreadLocalRandom.current();
        //解析类型
        for (int i = 5; i > 0; i--) {
            types[i] = (type & 0x1) == 1;
            type >>= 1;
        }
        Chessboard chessboard;
        int falueCount = 0;
        while (true) {
            for (int i = 0; i < 10; i++) {
                Chessman chessman = Chessman.getInstanceByID((char) ('a' + i), types[i] ? Chessman.ChessmanType.HORIZONTAL : Chessman.ChessmanType.VERTICAL);
                chessmans[i] = chessman;
                int x = 0, y = 0;
                boolean flag = true;
                while (flag) {
                    x = current.nextInt(5 - chessman.getType().getWidth());
                    y = current.nextInt(6 - chessman.getType().getHeight());
                    flag = false;
                    outter1:
                    for (int ty = y; ty < y + chessman.getType().getHeight(); ty++) {
                        for (int tx = x; tx < x + chessman.getType().getWidth(); tx++) {
                            if (chars[ty][tx] != '\0') {
                                flag = true;
                                break outter1;
                            }
                        }
                    }
                    falueCount += 1;
                    if (falueCount > MAX_TRY_NUMS) {
                        return Optional.empty();
                    }
                }

                for (int ty = y; ty < y + chessman.getType().getHeight(); ty++) {
                    for (int tx = x; tx < x + chessman.getType().getWidth(); tx++) {
                        chars[ty][tx] = chessman.getId();
                    }
                }
                chessmanMap.put(chessmans[i], ChessmanWithCoordinate.getInstance(chessman, Coordinate.getInstance((byte) x, (byte) y)));
            }
            chessboard = new Chessboard(chessmanMap);
            if (cachedMirrors.add(chessboard.getMirror())) {
                cachedMirrors.add(chessboard.getAdjectiveMirror());
                return Optional.of(chessboard);
            }
        }
    }

    /**
     * 根据已有缓存生成棋局
     *
     * @param type          棋局类型
     * @param step          棋局步数下限
     * @param cachedMirrors 缓存的镜像
     * @return 返回适合的棋局(有解)
     */
    public static Optional<Chessboard> getRandomChessbordWithCache(int type, int step, Set<Long> cachedMirrors) {
        int total = 0;
        while (true) {
            Optional<Chessboard> optionalChessboard = getRandomChessbordWithCache(type, cachedMirrors);
            if (optionalChessboard.isPresent()) {
                Chessboard randomChessboard = optionalChessboard.get();
                CreateTree ct = new CreateTree(randomChessboard.getState());
                if (ct.calculateResult()) {
                    if (ct.getStack().size() < step) {
                        continue;
                    }
                    System.out.println(total);
                    System.out.println(randomChessboard);
                    System.out.println(ct.getStack().size() - 1 + "步");
                    return optionalChessboard;
                }
            }

            total += 1;
            if (total > MAX_TRY_NUMS) {
                return Optional.empty();
            }

        }
    }
}
