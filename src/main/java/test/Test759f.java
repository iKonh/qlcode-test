package test;

/**
 * 759f:扫地机器人
 * CPU:G550
 * Lv47:坐标17,8，运行时间71716ms
 * Lv49:坐标15,12，运行时间50820ms
 * Lv50:坐标3,3，运行时间10082ms
 * Lv54:坐标15,21，运行时间197674ms
 * Lv58:坐标17,1，运行时间213163ms
 * 目前过60关
 */
public class Test759f {
    public static int x = 23;
    public static int y = 23;
    public static String mapNo = "0000111100010001100010000100100010101000010000101100000100000001111101000100000000011000000010000010000000001111000100100100000010000000101100011110001101000010011011001001100011000001000110000111001000110000011001000000000000111010000110100011000100000100000100010000001110011011100001100110001100100110001110000101000001011001000001100001111000000110110011101000100000000101111111000000011011100000000100000100010110001000001011100010100001110001110000000100000001100011000011001100111001000010110000001000000010010001100111000";
    public static int[][] map;
    public static String UP = "u";
    public static String DOWN = "d";
    public static String LEFT = "l";
    public static String RIGHT = "r";
    public static void main(String[] args) {
        createMap();
        long begin = System.currentTimeMillis();
        move();
        long end = System.currentTimeMillis();
        System.out.println((end - begin) + "ms");
    }

    public static void createMap() {
        map = new int[x][y];
        int index = 0;
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                map[i][j] = Integer.parseInt(String.valueOf(mapNo.charAt(index)));
                index++;
            }
        }
    }

    public static void move() {

        for (int i = 0; i < x; i++){
            for (int j = 0; j < y; j++){
                if(map[i][j] != 1) {
                    map[i][j] = 1;
                    String result = null;
                    // 若左侧有空余,继续左移
                    if (j > 0 && map[i][j-1] == 0) {
                        result = move(i, j, LEFT, 2);
                    }
                    // 若右侧有空余,继续右移
                    if (result == null && j < y-1 && map[i][j+1] == 0) {
                        result = move(i, j, RIGHT, 2);
                    }
                    // 若上侧有空余,继续上移
                    if (result == null && i > 0 && map[i-1][j] == 0) {
                        result = move(i, j, UP, 2);
                    }
                    // 若下侧有空余,继续下移
                    if (result == null && i < x-1 && map[i+1][j] == 0) {
                        result = move(i, j, DOWN, 2);
                    }
                    if (result == null) {
                        map[i][j] = 0;
                    } else {
                        System.out.println((i + 1) + "," + (j + 1));
                        System.out.println(result);
                        return;
                    }
                }
            }
        }
    }

    /**
     * 移动操作
     * @param i 横坐标
     * @param j 纵坐标
     * @param direction 方向
     * @param index 移动次数
     * @return 移动有效返回方向，移动无效返回null
     */
    public static String move(int i, int j, String direction, int index) {
        // 向上移动
        if (UP.equals(direction)) {
            while (i > 0 && map[i - 1][j] == 0) {
                i--;
                map[i][j] = index;
            }
        }
        // 向下移动
        else if (DOWN.equals(direction)) {
            while (i < x - 1 && map[i + 1][j] == 0) {
                i++;
                map[i][j] = index;
            }
        }
        // 向左移动
        else if (LEFT.equals(direction)) {
            while (j > 0 && map[i][j - 1] == 0) {
                j--;
                map[i][j] = index;
            }
        }
        // 向右移动
        else if (RIGHT.equals(direction)) {
            while (j < y - 1 && map[i][j + 1] == 0) {
                j++;
                map[i][j] = index;
            }
        }

        boolean needCheck = true;
        // 继续移动处理
        if (UP.equals(direction) || DOWN.equals(direction)) {
            // 若左侧有空余,继续左移
            if (j > 0 && map[i][j-1] == 0) {
                needCheck = false;
                String result = move(i, j, LEFT, index + 1);
                if (result != null) {
                    return direction + result;
                }
            }
            // 若右侧有空余,继续右移
            if (j < y-1 && map[i][j+1] == 0) {
                needCheck = false;
                String result = move(i, j, RIGHT, index + 1);
                if (result != null) {
                    return direction + result;
                }
            }
        } else if (LEFT.equals(direction) || RIGHT.equals(direction)) {
            // 若上侧有空余,继续上移
            if (i > 0 && map[i-1][j] == 0) {
                needCheck = false;
                String result = move(i, j, UP, index + 1);
                if (result != null) {
                    return direction + result;
                }
            }
            // 若下侧有空余,继续下移
            if (i < x-1 && map[i+1][j] == 0) {
                needCheck = false;
                String result = move(i, j, DOWN, index + 1);
                if (result != null) {
                    return direction + result;
                }
            }
        }
        if (needCheck && check()) {
            return direction;
        }
        revert(i, j, direction, index);
        return null;
    }

    /**
     * 地图验证
     * @return
     */
    public static boolean check() {
        for (int i = 0;i < x; i++) {
            for (int j = 0;j < y; j++) {
                if (map[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 步数回退
     * @param index
     */
    public static void revert(int i, int j, String direction, int index) {
        // 上移回退
        if (UP.equals(direction)) {
            while (i < x && map[i][j] == index) {
                map[i][j] = 0;
                i++;
            }
        }
        // 向下移动
        else if (DOWN.equals(direction)) {
            while (i >= 0 && map[i][j] == index) {
                map[i][j] = 0;
                i--;
            }
        }
        // 向左移动
        else if (LEFT.equals(direction)) {
            while (j < y && map[i][j] == index) {
                map[i][j] = 0;
                j++;
            }
        }
        // 向右移动
        else if (RIGHT.equals(direction)) {
            while (j >= 0 && map[i][j] == index) {
                map[i][j] = 0;
                j--;
            }
        }
    }
}
