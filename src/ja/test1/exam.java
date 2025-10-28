package ja.test1;

import java.util.ArrayList;
import java.util.Scanner;

public class exam {
    static ArrayList<Double> list = new ArrayList<Double>();
    public static void main(String[] args) {
        scanning();
        avg();
        min_max();
        stage();
        show();
    }
    public static void scanning(){
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入成绩：（输入 # 结束）");
        while(sc.hasNext()){
            if(sc.hasNextDouble()){
                double score = sc.nextDouble();
                list.add(score);
            }
            else{
                String end = sc.next();
                if(end.equals("#")){
                    System.out.println("输入完成！");
                    return;
                }
                else{
                    System.out.println("输入错误,重新输入.");
                }
            }
        }



    }
    public static void avg(){
        int n = list.size();
        double sum = 0;
        for(Double d : list){
            sum += d;
        }
        System.out.println("\n平均值为：" + sum/n);
    }
    public static void min_max(){
       // double maxx = 0;
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = 0; j < list.size() - 1 - i; j++) {
                if(list.get(j) > list.get(j + 1)){
                    double maxx = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, maxx);
                }
            }
        }
        System.out.println("\n最大值为：" + list.getLast());
        System.out.println("\n最小值为：" + list.getFirst());

    }
    public static void stage(){
        int[] ranges = {0, 60, 70, 80, 90, 101};
        String[] rangeNames = {"不及格", "60-69分", "70-79分", "80-89分", "90-100分"};

        int[] counts = new int[rangeNames.length];
        int total = list.size();

        // 统计每个分数档的人数
        for (double score : list) {
            for (int i = 0; i < ranges.length - 1; i++) {
                if (score >= ranges[i] && score < ranges[i + 1]) {
                    counts[i]++;
                    break;
                }
            }
        }
        System.out.println("\n分数档统计结果：");
        for (int i = 0; i < rangeNames.length; i++) {
            double percentage = (double) counts[i] / total * 100;
            System.out.printf("%s: %d人 (%.2f%%)\n",
                    rangeNames[i], counts[i], percentage);
        }
        System.out.printf("总人数: %d人\n", total);

    }
    public static void show(){
//        for (int i = 0; i < list.size(); i++) {
//            System.out.println(list.get(i));
//        }

    }
}
