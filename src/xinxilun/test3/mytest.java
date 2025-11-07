package xinxilun.test3;

import java.util.*;

class Huffman implements Comparable<Huffman>{
    String name;
    double probability;
    Huffman left;
    Huffman right;
    String code;

    public Huffman(String name, double probability) {
        this.name = name;
        this.probability = probability;
        this.left = null;
        this.right = null;
        this.code = "";
    }

    public Huffman(double probability,Huffman left, Huffman right) {
        this.name = "";
        this.probability = probability;
        this.left = left;
        this.right = right;
        this.code = "";
    }

    public boolean isleaf(){
        return left == null && right == null;
    }

    //比较接口
    //如果第一个大于第二个 则是右子节点 否则为左子节点
    @Override
    public int compareTo(Huffman o) {
        return Double.compare(this.probability, o.probability);
    }
}




public class mytest {
    public static void main(String[] args) {
        //1.添加节点
        List<Huffman> nodes = new ArrayList<Huffman>();
//        Collections.addAll(nodes,new Huffman("x1",0.5),
//                                new Huffman("x2",0.25),
//                                new Huffman("x3",0.125),
//                                new Huffman("x4",0.125));

        Collections.addAll(nodes,
                new Huffman("u1",0.1),
                new Huffman("u2",0.18),
                new Huffman("u3",0.4),
                new Huffman("u4",0.05),
                new Huffman("u5",0.06),
                new Huffman("u6",0.10),
                new Huffman("u7",0.07),
                new Huffman("u8",0.04)
        );
        //2.构造霍夫曼树
        Huffman root = buildHuffmantrees(nodes);
        //3.形成编码
        create_Huffman_code(root , "");
        //4.收集编码
        List<Huffman> nodes2 = get_node_code(root);
        //5.显示结果
        Printcode(nodes2);
        //6.计算性能指标
        calculatePerformance(nodes2);

    }


    public static Huffman buildHuffmantrees(List<Huffman> node){
        PriorityQueue<Huffman> pq = new PriorityQueue<Huffman>(node);
        System.out.println("==构造霍夫曼树==");

        while(pq.size()>1){
            Huffman left = pq.poll();
            Huffman right = pq.poll();

            double newProb = left.probability + right.probability;
            Huffman newNode = new Huffman(newProb,left,right);

            String leftname = left.isleaf() ? left.name : "内部节点";
            String rightname = right.isleaf() ? right.name : "内部节点";
            System.out.printf("合并："+leftname+"(%f)和"+rightname+"(%f)->新节点(%f)\n",left.probability,right.probability,newProb);

            pq.add(newNode);
        }
        System.out.println("==霍夫曼树构造完毕==\n");
        return pq.poll();
    }

    public static void create_Huffman_code(Huffman node,String code){
        if(node == null){
            return;
        }
        node.code = code;

        if(node.isleaf()){
            System.out.printf("符号%s的编码为%s\n",node.name,code);
        }
        create_Huffman_code(node.left,code + "0");
        create_Huffman_code(node.right,code + "1");
    }

    public static List<Huffman> get_node_code(Huffman root){
        List<Huffman> nodes = new ArrayList<Huffman>();
        get_code(root,nodes);
        return nodes;
    }

    public static void get_code(Huffman root,List<Huffman> node){
        if(root == null){
            return;
        }
        if(root.isleaf()){
            node.add(root);
        }
        else{
            get_code(root.left,node);
            get_code(root.right,node);
        }
    }

    public static void Printcode(List<Huffman> nodes){
        System.out.println("\n霍夫曼编码结果：");
        System.out.println("符号\t概率\t码长\t码字");
        System.out.println("------------------------");
        for(Huffman node : nodes){
            System.out.printf("%s\t%f\t%d\t%s\n",
                    node.name,node.probability,node.code.length(),node.code);
        }
    }

    private static void calculatePerformance(List<Huffman> nodes2) {
        //信息熵
        double a = 0;
        for(Huffman node : nodes2){
            a -= node.probability * (Math.log(node.probability)/Math.log(2));
        }
        //平均码长
        double b = 0;
        for(Huffman node : nodes2){
            b += node.probability * node.code.length();
        }
        //编码效率
        double c = a / b;
        System.out.println("==性能分析==");
        System.out.printf("信息熵：%f比特/符号\n",a);
        System.out.printf("平均码长：%f比特/符号\n",b);
        System.out.printf("编码效率：%f%%",c*100);


    }

}
