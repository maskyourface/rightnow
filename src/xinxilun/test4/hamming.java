package xinxilun.test4;

public class hamming {

    // (7,4)汉明码监督矩阵 H (3×7) - 题目已知
    private static final int[][] H = {
        {0, 1, 1, 1, 1, 0, 0},
        {1, 0, 1, 1, 0, 1, 0},
        {1, 1, 0, 1, 0, 0, 1}
    };

    // 生成矩阵 G - 将从监督矩阵计算得出
    private static int[][] G;

    /**
     * 从监督矩阵 H 推导生成矩阵 G
     * 对于标准形式: H = [P^T | I_r], 则 G = [I_k | P]
     * 其中 P^T 是 H 的前 k 列，I_r 是 H 的后 r 列
     */
    private static int[][] deriveGeneratorMatrix(int[][] H) {
        int r = H.length;        // 监督位数 = 3
        int n = H[0].length;     // 码长 = 7
        int k = n - r;           // 信息位数 = 4

        System.out.println("从监督矩阵推导生成矩阵:");
        System.out.println("码长 n = " + n + ", 信息位数 k = " + k + ", 监督位数 r = " + r);
        System.out.println();

        // 提取 P^T (H的前k列)
        int[][] PT = new int[r][k];
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < k; j++) {
                PT[i][j] = H[i][j];
            }
        }

        System.out.println("P^T (H的前" + k + "列) (3×4):");
        printMatrix("", PT);

        // P = (P^T)^T，即转置
        int[][] P = new int[k][r];
        for (int i = 0; i < k; i++) {
            for (int j = 0; j < r; j++) {
                P[i][j] = PT[j][i];
            }
        }

        System.out.println("P (P^T的转置) (4×3):");
        printMatrix("", P);

        // 构造生成矩阵 G = [I_k | P]
        int[][] G = new int[k][n];
        for (int i = 0; i < k; i++) {
            // 前k列为单位矩阵 I_k
            for (int j = 0; j < k; j++) {
                G[i][j] = (i == j) ? 1 : 0;
            }
            // 后r列为 P
            for (int j = 0; j < r; j++) {
                G[i][k + j] = P[i][j];
            }
        }

        return G;
    }

    /**
     * 验证 H·G^T = 0 (模2)
     */
    private static boolean verifyOrthogonality(int[][] H, int[][] G) {
        int r = H.length;
        int k = G.length;

        System.out.println("验证 H·G^T = 0 (模2):");

        // 计算 H·G^T
        int[][] result = new int[r][k];
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < k; j++) {
                int sum = 0;
                for (int m = 0; m < G[0].length; m++) {
                    sum += H[i][m] * G[j][m];
                }
                result[i][j] = sum % 2;
            }
        }

        // 检查是否全为0
        boolean isZero = true;
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < k; j++) {
                if (result[i][j] != 0) {
                    isZero = false;
                    break;
                }
            }
        }

        System.out.println("H·G^T =");
        printMatrix("", result);
        System.out.println("验证结果: " + (isZero ? "通过 ?" : "失败 ?"));
        System.out.println();

        return isZero;
    }

    /**
     * 矩阵乘法（模2运算）
     */
    private static int[] matrixMultiply(int[] vector, int[][] matrix) {
        int cols = matrix[0].length;
        int[] result = new int[cols];

        for (int j = 0; j < cols; j++) {
            int sum = 0;
            for (int i = 0; i < vector.length; i++) {
                sum += vector[i] * matrix[i][j];
            }
            result[j] = sum % 2;
        }
        return result;
    }

    /**
     * 编码：信息位 -> 码字
     */
    public static int[] encode(int[] infoBits) {
        if (infoBits.length != 4) {
            throw new IllegalArgumentException("信息位长度必须为4");
        }
        return matrixMultiply(infoBits, G);
    }

    /**
     * 译码：接收码字 -> 信息位
     */
    public static int[] decode(int[] received) {
        if (received.length != 7) {
            throw new IllegalArgumentException("码字长度必须为7");
        }

        // 计算伴随式 S = H · received^T
        int[] syndrome = new int[3];
        for (int i = 0; i < 3; i++) {
            int sum = 0;
            for (int j = 0; j < 7; j++) {
                sum += H[i][j] * received[j];
            }
            syndrome[i] = sum % 2;
        }

        // 计算错误位置（伴随式的十进制值）
        int errorPos = syndrome[0] * 4 + syndrome[1] * 2 + syndrome[2];

        System.out.print("  接收码字: ");
        printArray(received);
        System.out.print("  伴随式 S: ");
        printArray(syndrome);
        System.out.println("  伴随式(十进制): " + errorPos);

        // 纠错
        int[] corrected = received.clone();
        if (errorPos == 0) {
            System.out.println("  无错误");
        } else {
            System.out.println("  错误位置: 第" + errorPos + "位");
            corrected[errorPos - 1] ^= 1;
            System.out.print("  纠正后码字: ");
            printArray(corrected);
        }

        // 提取信息位（前4位）
        int[] infoBits = new int[4];
        System.arraycopy(corrected, 0, infoBits, 0, 4);
        System.out.print("  译出信息位: ");
        printArray(infoBits);

        return infoBits;
    }

    /**
     * 工具方法：字符串转整数数组
     */
    private static int[] stringToIntArray(String str) {
        int[] arr = new int[str.length()];
        for (int i = 0; i < str.length(); i++) {
            arr[i] = str.charAt(i) - '0';
        }
        return arr;
    }

    /**
     * 工具方法：整数数组转字符串
     */
    private static String intArrayToString(int[] arr) {
        StringBuilder sb = new StringBuilder();
        for (int bit : arr) {
            sb.append(bit);
        }
        return sb.toString();
    }

    /**
     * 工具方法：打印数组
     */
    private static void printArray(int[] arr) {
        System.out.println(intArrayToString(arr));
    }

    /**
     * 工具方法：打印矩阵
     */
    private static void printMatrix(String name, int[][] matrix) {
        if (!name.isEmpty()) {
            System.out.println(name + ":");
        }
        for (int[] row : matrix) {
            System.out.print("  [");
            for (int j = 0; j < row.length; j++) {
                System.out.print(row[j]);
                if (j < row.length - 1) System.out.print(" ");
            }
            System.out.println("]");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        System.out.println("==================== 汉明码(7,4)实验 ====================\n");

        // 显示已知的监督矩阵
        System.out.println("已知监督矩阵 H (3×7):");
        printMatrix("", H);

        // （1）从监督矩阵推导生成矩阵
        System.out.println("============ （1）推导生成矩阵 ============\n");
        G = deriveGeneratorMatrix(H);

        System.out.println("生成矩阵 G = [I_4 | P] (4×7):");
        printMatrix("", G);

        // 验证正交性
        verifyOrthogonality(H, G);

        // （2）编码过程
        System.out.println("============ （2）编码过程 ============\n");
        String inputSequence = "110101010001";
        System.out.println("输入信息序列: " + inputSequence);
        System.out.println("每4位一组进行编码:\n");

        StringBuilder encodedSequence = new StringBuilder();

        for (int i = 0; i < inputSequence.length(); i += 4) {
            if (i + 4 <= inputSequence.length()) {
                String infoStr = inputSequence.substring(i, i + 4);
                int[] infoBits = stringToIntArray(infoStr);
                int[] codeword = encode(infoBits);

                System.out.println("信息位 u: " + infoStr);
                System.out.println("码字 c = u·G: " + intArrayToString(codeword));
                System.out.println();

                encodedSequence.append(intArrayToString(codeword));
            }
        }

        System.out.println("完整编码序列: " + encodedSequence.toString());
        System.out.println();

        // （3）译码过程
        System.out.println("============ （3）译码过程 ============\n");
        String receivedSequence = "11101010101010";
        System.out.println("接收码字序列: " + receivedSequence);
        System.out.println("每7位一组进行译码:\n");

        StringBuilder decodedInfo = new StringBuilder();

        int groupNum = 1;
        for (int i = 0; i < receivedSequence.length(); i += 7) {
            if (i + 7 <= receivedSequence.length()) {
                System.out.println("第 " + groupNum + " 组:");
                String receivedStr = receivedSequence.substring(i, i + 7);
                int[] received = stringToIntArray(receivedStr);
                int[] info = decode(received);
                decodedInfo.append(intArrayToString(info));
                System.out.println();
                groupNum++;
            }
        }

        System.out.println("最终译出的信息序列: " + decodedInfo.toString());

        System.out.println("\n==================== 实验结束 ====================");
    }
}