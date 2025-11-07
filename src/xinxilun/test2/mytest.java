package xinxilun.test2;

import java.util.*;

//信源符号
class symbolh{
    String name;
    double probability;
    double andprobability;
    int codelength;
    String codeword;

    public symbolh(String name,double probability){
        this.name=name;
        this.probability=probability;
    }

    @Override
    public String toString(){
        return String.format("%s\t%.4f\t%.4f\t%d\t%s",
                name, probability, andprobability, codelength, codeword);
    }
}





public class mytest {
    public static void main(String[] args) {
        System.out.println("香农编码");
        System.out.println("================");
        //1.创建信源
        List<symbolh> list = createsymbol();
        //2.香农编码
        performS(list);
        //3.显示结果
        display(list);
        //4.性能分析
        analyze(list);
    }

    public static List<symbolh>  createsymbol(){
        List<symbolh> list=new ArrayList<symbolh>();

//        list.add(new symbolh("x1",1.0/2));
//        list.add(new symbolh("x2",1.0/4));
//        list.add(new symbolh("x3",1.0/8));
//        list.add(new symbolh("x4",1.0/8));

        Collections.addAll(list,
                new symbolh("u1",0.15),
                new symbolh("u2",0.25),
                new symbolh("u3",0.20),
                new symbolh("u4",0.25),
                new symbolh("u5",0.05),
                new symbolh("u6",0.10)
                );



        System.out.println("步骤1：创建信源符号");
        System.out.println("符号\t概率");
        System.out.println("----------------");
        for (symbolh symbol : list) {
            System.out.printf("%s\t%.3f\n", symbol.name, symbol.probability);
        }
        System.out.println();

        return list;
    }

    public static void performS(List<symbolh> list){
        System.out.println("步骤2：执行香农编码");
        //排序     降序Double.compare(b,a)
        System.out.println("2.1 降序排序");
        list.sort((a,b)-> Double.compare(b.probability,a.probability));
        //累加概率
        System.out.println("2.2 计算累加概率");
        double sum = 0;
        for(symbolh symbol : list){
            symbol.andprobability = sum;
            sum += symbol.probability;
            System.out.printf("%s 的累加概率为 %.3f\n",symbol.name,symbol.andprobability);
        }
        //码长
        System.out.println("2.3 计算码长");
        for(symbolh symbol : list){
            double logs = Math.log(symbol.probability) / Math.log(2);
            symbol.codelength = (int) Math.ceil(-logs);
            System.out.printf("%s 的码长为 %d\n",symbol.name,symbol.codelength);
        }
        //码字
        System.out.println("2.4 计算码字");
        for(symbolh symbol : list){
            symbol.codeword = convertToBinary(symbol.andprobability,symbol.codelength);
            System.out.printf("%s 的码字为 %s\n",symbol.name,symbol.codeword);
        }
    }
    //转化二进制
    public static String convertToBinary(double andprobablilty,int length){
        StringBuffer sb = new StringBuffer();
        double temp = andprobablilty;
        for(int i = 0; i < length ; i++){
            temp = temp * 2;
            if(temp >= 1){
                sb.append("1");
                temp -= 1;
            }else{
                sb.append("0");
            }
        }
        return sb.toString();
    }

    public static void display(List<symbolh> list){
        System.out.println("步骤3：编码结果");
        System.out.println("符号\t概率\t累加概率\t码长\t码字");
        System.out.println("----------------------------------------");

        for (symbolh symbol : list) {
            System.out.println(symbol);
        }
        System.out.println();
    }

    public static void analyze(List<symbolh> list){
        System.out.println("步骤4：性能分析");

        //信息熵
        double sum = 0;
        for(symbolh symbol : list){
            double logs = Math.log(symbol.probability) / Math.log(2);
            sum = (-logs) * symbol.probability;
        }
        //平均码长
        double sum2 = 0;
        for(symbolh symbol : list){
            sum2 += symbol.probability*symbol.codelength;
        }
        //编码效率
        double sum3 = sum/sum2;
        //展示
        System.out.printf("信息熵为%.3f\n",sum);
        System.out.printf("平均码长为%.3f\n",sum2);
        System.out.printf("编码效率为%.3f\n",sum3);
    }
}
