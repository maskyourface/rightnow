package xinxilun.test3;

import java.util.*;

// 霍夫曼树的节点类
class HuffmanNode implements Comparable<HuffmanNode> {
    String symbol;          // 符号名称
    double probability;     // 概率值
    HuffmanNode left;       // 左子节点
    HuffmanNode right;      // 右子节点
    String code;            // 编码结果

    // 构造函数 - 用于创建叶子节点
    public HuffmanNode(String symbol, double probability) {
        this.symbol = symbol;
        this.probability = probability;
        this.left = null;
        this.right = null;
        this.code = "";
    }

    // 构造函数 - 用于创建内部节点
    public HuffmanNode(double probability, HuffmanNode left, HuffmanNode right) {
        this.symbol = "";
        this.probability = probability;
        this.left = left;
        this.right = right;
        this.code = "";
    }

    // 判断是否为叶子节点
    public boolean isLeaf() {
        return left == null && right == null;
    }

    // 实现比较接口，用于优先队列排序
    @Override
    public int compareTo(HuffmanNode other) {
        return Double.compare(this.probability, other.probability);
    }
}

public class test {

    public static void main(String[] args) {
        System.out.println("=== 霍夫曼编码程序 ===");

        // 1. 创建符号和概率
        List<HuffmanNode> nodes = new ArrayList<>();
        nodes.add(new HuffmanNode("x1", 0.5));
        nodes.add(new HuffmanNode("x2", 0.25));
        nodes.add(new HuffmanNode("x3", 0.125));
        nodes.add(new HuffmanNode("x4", 0.125));

        // 2. 构建霍夫曼树
        HuffmanNode root = buildHuffmanTree(nodes);

        // 3. 生成编码
        generateCodes(root, "");

        // 4. 收集叶子节点（即原始符号）的编码结果
        List<HuffmanNode> result = getLeafNodes(root);

        // 5. 排序
        //Collections.sort(result, (a, b) -> Double.compare(b.probability, a.probability));

        // 6. 显示编码结果
        printResults(result);

        // 7. 计算性能指标
        calculatePerformance(result);
    }

    // 构建霍夫曼树
    public static HuffmanNode buildHuffmanTree(List<HuffmanNode> nodes) {
        // 使用优先队列（最小堆），概率小的在前
        PriorityQueue<HuffmanNode> queue = new PriorityQueue<>(nodes);

        System.out.println("构建霍夫曼树的过程：");

        // 不断合并节点，直到只剩一个节点（根节点）
        while (queue.size() > 1) {
            // 取出概率最小的两个节点
            HuffmanNode left = queue.poll();
            HuffmanNode right = queue.poll();

            // 创建新节点，概率为两个子节点概率之和
            double newProb = left.probability + right.probability;
            HuffmanNode parent = new HuffmanNode(newProb, left, right);

            // 输出合并信息
            String leftSymbol = left.isLeaf() ? left.symbol : "内部节点";
            String rightSymbol = right.isLeaf() ? right.symbol : "内部节点";
            System.out.printf("合并: %s(%.3f) 和 %s(%.3f) -> 新节点(%.3f)\n",
                    leftSymbol, left.probability, rightSymbol, right.probability, newProb);

            // 将新节点加入队列
            queue.add(parent);
        }

        System.out.println();
        return queue.poll(); // 返回根节点
    }

    // 递归生成编码
    public static void generateCodes(HuffmanNode node, String code) {
        if (node == null) return;

        // 设置当前节点的编码
        node.code = code;

        // 如果是叶子节点，输出编码信息
        if (node.isLeaf()) {
            System.out.printf("符号 %s 的编码: %s\n", node.symbol, code);
        }

        // 递归处理左右子树：左子树加"0"，右子树加"1"
        generateCodes(node.left, code + "0");
        generateCodes(node.right, code + "1");
    }

    // 收集所有叶子节点
    public static List<HuffmanNode> getLeafNodes(HuffmanNode root) {
        List<HuffmanNode> leaves = new ArrayList<>();
        collectLeaves(root, leaves);
        return leaves;
    }

    // 递归收集叶子节点
    private static void collectLeaves(HuffmanNode node, List<HuffmanNode> leaves) {
        if (node == null) return;

        if (node.isLeaf()) {
            leaves.add(node);
        } else {
            collectLeaves(node.left, leaves);
            collectLeaves(node.right, leaves);
        }
    }

    // 打印编码结果
    public static void printResults(List<HuffmanNode> symbols) {
        System.out.println("\n霍夫曼编码结果：");
        System.out.println("符号\t概率\t码长\t码字");
        System.out.println("------------------------");

        for (HuffmanNode node : symbols) {
            System.out.printf("%s\t%.3f\t%d\t%s\n",
                    node.symbol, node.probability, node.code.length(), node.code);
        }
    }

    // 计算性能指标
    public static void calculatePerformance(List<HuffmanNode> symbols) {
        // 计算信源熵
        double entropy = 0.0;
        for (HuffmanNode node : symbols) {
            if (node.probability > 0) {
                entropy -= node.probability * (Math.log(node.probability) / Math.log(2));
            }
        }

        // 计算平均码长
        double avgLength = 0.0;
        for (HuffmanNode node : symbols) {
            avgLength += node.probability * node.code.length();
        }

        // 计算编码效率
        double efficiency = entropy / avgLength;

        System.out.println("\n性能分析：");
        System.out.printf("信源熵: %.4f 比特/符号\n", entropy);
        System.out.printf("平均码长: %.4f 比特/符号\n", avgLength);
        System.out.printf("编码效率: %.2f%%\n", efficiency * 100);

        // 计算码长方差（衡量编码的稳定性）
        double variance = 0.0;
        for (HuffmanNode node : symbols) {
            double diff = node.code.length() - avgLength;
            variance += node.probability * (diff * diff);
        }
        System.out.printf("码长方差: %.4f\n", variance);
    }
}
