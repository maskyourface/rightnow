package xinxilun.test2;

import java.util.*;

/**
 * 信源符号类 - 表示一个符号及其编码信息
 */
class SourceSymbol {
    String name;        // 符号名称，如 "x1", "x2"
    double probability; // 符号出现的概率
    double cumulativeProbability; // 累加概率
    int codeLength;     // 码字长度
    String codeWord;    // 最终的二进制码字

    // 构造函数
    public SourceSymbol(String name, double probability) {
        this.name = name;
        this.probability = probability;
    }
    @Override
    public String toString() {
        return String.format("%s\t%.4f\t%.4f\t%d\t%s",
                name, probability, cumulativeProbability, codeLength, codeWord);
    }

}

/**
 * 香农编码器 - 主要编码逻辑
 */
public class test {

    public static void main(String[] args) {
        System.out.println("=== 香农编码演示程序 ===");
        System.out.println();

        // 步骤1：创建信源符号
        List<SourceSymbol> symbols = createSourceSymbols();

        // 步骤2：执行香农编码
        performShannonCoding(symbols);

        // 步骤3：显示编码结果
        displayResults(symbols);

        // 步骤4：性能分析
        analyzePerformance(symbols);

    }

    /**
     * 步骤1：创建信源符号
     * 我们使用例子：X = [x1, x2, x3, x4], P = [1/2, 1/4, 1/8, 1/8]
     */
    public static List<SourceSymbol> createSourceSymbols() {
        List<SourceSymbol> symbols = new ArrayList<>();

        // 添加符号和对应的概率
        symbols.add(new SourceSymbol("x1", 1.0/2));  // 概率 0.5
        symbols.add(new SourceSymbol("x2", 1.0/4));  // 概率 0.25
        symbols.add(new SourceSymbol("x3", 1.0/8));  // 概率 0.125
        symbols.add(new SourceSymbol("x4", 1.0/8));  // 概率 0.125

        System.out.println("步骤1：创建信源符号");
        System.out.println("符号\t概率");
        System.out.println("----------------");
        for (SourceSymbol symbol : symbols) {
            System.out.printf("%s\t%.3f\n", symbol.name, symbol.probability);
        }
        System.out.println();

        return symbols;
    }

    /**
     * 步骤2：执行香农编码
     */
    public static void performShannonCoding(List<SourceSymbol> symbols) {
        System.out.println("步骤2：执行香农编码");

        // 2.1 按概率从大到小排序
        System.out.println("2.1 按概率降序排序");
        symbols.sort((a, b) -> Double.compare(b.probability, a.probability));

        // 2.2 计算累加概率
        System.out.println("2.2 计算累加概率");
        double cumulative = 0.0;
        for (SourceSymbol symbol : symbols) {
            symbol.cumulativeProbability = cumulative;
            cumulative += symbol.probability;
            System.out.printf("  %s: 累加概率 = %.3f\n", symbol.name, symbol.cumulativeProbability);
        }

        // 2.3 计算码长
        System.out.println("2.3 计算码长");
        for (SourceSymbol symbol : symbols) {
            // 码长公式：l_i = ceil(-log2(p_i))
            // Math.log(x) 是自然对数，所以要除以 Math.log(2) 换成以2为底的对数
            double logValue = Math.log(symbol.probability) / Math.log(2);
            symbol.codeLength = (int) Math.ceil(-logValue);

            System.out.printf("  %s: -log2(%.3f) = %.3f, 码长 = %d\n",
                    symbol.name, symbol.probability, -logValue, symbol.codeLength);
        }

        // 2.4 生成码字
        System.out.println("2.4 生成码字");
        for (SourceSymbol symbol : symbols) {
            symbol.codeWord = convertToBinary(symbol.cumulativeProbability, symbol.codeLength);
            System.out.printf("  %s: 累加概率 %.3f → 二进制 → 取前%d位 → %s\n",
                    symbol.name, symbol.cumulativeProbability, symbol.codeLength, symbol.codeWord);
        }

        System.out.println();
    }

    /**
     * 将十进制小数转换为二进制字符串
     * @param decimal 十进制小数
     * @param length 要转换的二进制位数
     * @return 二进制字符串
     */
    public static String convertToBinary(double decimal, int length) {
        StringBuilder binary = new StringBuilder();
        double temp = decimal;  // 临时变量，用于计算

        // 重复 length 次，每次计算一位二进制
        for (int i = 0; i < length; i++) {
            temp = temp * 2;      // 乘以2
            if (temp >= 1) {
                binary.append("1");  // 如果大于等于1，该位为1
                temp = temp - 1;     // 减去1
            } else {
                binary.append("0");  // 否则该位为0
            }
        }

        return binary.toString();
    }

    /**
     * 步骤3：显示编码结果
     */
    public static void displayResults(List<SourceSymbol> symbols) {
        System.out.println("步骤3：编码结果");
        System.out.println("符号\t概率\t累加概率\t码长\t码字");
        System.out.println("----------------------------------------");

        for (SourceSymbol symbol : symbols) {
            System.out.println(symbol);
        }
        System.out.println();
    }

    /**
     * 步骤4：性能分析
     */
    public static void analyzePerformance(List<SourceSymbol> symbols) {
        System.out.println("步骤4：性能分析");

        // 计算信源熵 H(X) = -Σ p_i * log2(p_i)
        double entropy = 0.0;
        for (SourceSymbol symbol : symbols) {
            if (symbol.probability > 0) {  // 避免log(0)的情况
                double logValue = Math.log(symbol.probability) / Math.log(2);
                entropy -= symbol.probability * logValue;
            }
        }

        // 计算平均码长 L = Σ p_i * l_i
        double avgCodeLength = 0.0;
        for (SourceSymbol symbol : symbols) {
            avgCodeLength += symbol.probability * symbol.codeLength;
        }

        // 计算编码效率 η = H(X) / L
        double efficiency = entropy / avgCodeLength;

        System.out.printf("信源熵 H(X) = %.4f 比特/符号\n", entropy);
        System.out.printf("平均码长 L = %.4f 比特/符号\n", avgCodeLength);
        System.out.printf("编码效率 η = %.4f (%.2f%%)\n", efficiency, efficiency * 100);

        // 验证概率和是否为1
        double totalProbability = 0.0;
        for (SourceSymbol symbol : symbols) {
            totalProbability += symbol.probability;
        }
        System.out.printf("概率总和 = %.4f\n", totalProbability);
        System.out.println();
    }


    }

