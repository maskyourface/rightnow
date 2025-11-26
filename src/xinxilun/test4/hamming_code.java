package xinxilun.test4;

public class hamming_code {
    //监督矩阵
    private static final int[][] H = {
            {0, 1, 1, 1, 1, 0, 0},
            {1, 0, 1, 1, 0, 1, 0},
            {1, 1, 0, 1, 0, 0, 1}
    };

    //生成矩阵
    private static int[][] G;

    private static int[][] deriveGeneratorMatrix(int[][] H) {
        int r = H.length; //码长
        int n = H[0].length; //信息位
        int k = n - r; //监督位

        System.out.println("码长 n = " + n + ", 信息位数 k = " + k + ", 监督位数 r = " + r);

        int[][] PT = new int[r][k];
        for(int i = 0;i < r; i++) {
            for(int j = 0; j < k; j++) {
                PT[i][j] = H[i][j];
            }
        }

        int[][] P = new int[k][r];
        for(int i = 0;i < k; i++) {
            for(int j = 0; j < r; j++) {
                P[i][j] = PT[j][i];
            }
        }

        int[][] G = new int[k][n];
        for (int i = 0; i < k; i++) {
            for (int j = 0; j < k; j++) {
                G[i][j] = (i==j) ? 1 : 0;
            }

            for(int j = 0; j < r; j++) {
                G[i][k+j] = P[i][j];
            }
        }

        return G;
    }

    private static boolean verifyOrthogonality(int[][] H,int[][] G) {
        int r = H.length;
        int k = G.length;

        int[][] result = new int[r][k];
        for(int i = 0; i < r; i++) {
            for (int j = 0; j < k; j++) {
                int sum = 0;
                for (int m = 0; m < G[0].length; m++) {
                    sum += H[i][m] * G[j][m];
                }
                result[i][j] = sum % 2;
            }
        }

        boolean isZero = true;

        for (int i = 0; i< r; i++) {
            for (int j = 0; j< k; j++) {
                if(result[i][j] != 0) {
                    isZero = false;
                    break;
                }
            }
        }

        return isZero;
    }

    //矩阵乘法
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

    //编码
    public static int[] encode(int[] bits) {
        if (bits.length != 4) {
            throw new IllegalArgumentException("信息位长度至少为4");
        }

        return matrixMultiply(bits,G);
    }

    //译码
    public static int[] decode(int[] received) {
        if (received.length != 7) {
            throw new IllegalArgumentException("码字长度需要为7");
        }

        int[] syndrome = new int[3];
        for (int i = 0; i< 3; i++) {
            int sum = 0;
            for (int j = 0; j < 7; j++) {
                sum += H[i][j] * received[j];
            }
            syndrome[i] = sum % 2;
        }

        int errorPos = syndrome[0] * 4 + syndrome[1] * 2 + syndrome[2];

        int[] correct = received.clone();
        if(errorPos == 0 ) {
            System.out.println("无错误");
        } else {
            System.out.println("  错误位置: 第" + errorPos + "位");
            correct[errorPos - 1] ^= 1;
            System.out.print("  纠正后码字: ");
            printArray(correct);
        }

        int[] bits = new int[4];

        return bits;
    }



    private static int[] stringToIntArray(String str) {
        int[] arr = new int[str.length()];
        for (int i = 0; i < str.length(); i++) {
            arr[i] = str.charAt(i) - '0';
        }
        return arr;
    }

    private static String intArrayToString(int[] arr) {
        StringBuilder sb = new StringBuilder();
        for (int bit : arr) {
            sb.append(bit);
        }
        return sb.toString();
    }

    private static void printArray(int[] arr) {
        System.out.println(intArrayToString(arr));
    }

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
        //1
        {
            System.out.println("生成矩阵");
            G = deriveGeneratorMatrix(H);

            printMatrix("",G);

            verifyOrthogonality(H,G);
            System.out.println();
        }

        //2
        {
            String input = "110101010001";
            StringBuilder encode = new StringBuilder();

            for(int i= 0; i < input.length(); i++) {
                if (i + 4 <= input.length()) {
                    String instr = input.substring(i,i+4);
                    int[] bits = stringToIntArray(instr);
                    int[] code = encode(bits);

                    encode.append(intArrayToString(code));
                }
            }

            System.out.println("完整编码序列: " + encode.toString());
            System.out.println();
        }

        //3
        {
            String received = "11101010101010";

            StringBuilder decode = new StringBuilder();

            int group = 1;
            for(int i = 0; i < received.length(); i++) {
                if (i + 7 <= received.length()) {
                    String receivedstr = received.substring(i,i+7);
                    int[] receiveArr = stringToIntArray(receivedstr);
                    int[] info = decode(receiveArr);
                    decode.append(intArrayToString(info));
                    group++;
                }
            }


            System.out.println("最终译出的信息序列: " + decode.toString());
        }

    }

}
