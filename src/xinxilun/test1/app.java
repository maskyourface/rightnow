package xinxilun.test1;


import java.util.Arrays;

public class app {
    static fuction one = new fuction();
    public static void main(String[] args) {
        problem1();
        problem2();
        problem3();
        problem4();
}



    public static void problem1(){
        //第一道题
        double [] P ={0.5,0.25,0.125,0.125};
        //double[]P = {0.5,0.5};
        System.out.println("第一道题");
        System.out.println("自信息量：" + Arrays.toString(one.selfinfo(P)));
        System.out.println("信息熵：" + one.entinfo(P));
    }
    public static void problem2(){
        //第二道题
        int total_char = 100;
        int choose_char = 29;
        double P1 = 1.0/choose_char;
        double I1 =total_char * one.selfinfo(P1);
        System.out.println("第二道题：");
        System.out.println("总信息量："+I1);
    }
    public static void problem3(){
        //第三道题
        double [] P2 = {1.0/7,2.0/7,3.0/7};
        int [] choose_char2 = {9,13,7};
        System.out.println("第三道题：");
        System.out.println("信息熵：" + one.entinfo(P2,choose_char2));
    }
    public static void problem4(){
        //第四道题
        System.out.println("第四道题");
        double[][] P_XY = {
                {1.0/8, 3.0/8},
                {3.0/8, 1.0/8},
        };
        //System.out.println(P_XY.length);
        //边缘分布
        double []P_X = new double[P_XY.length];
        double []P_Y = new double[P_XY.length];

        for(int i = 0;i < 2;i++){
            for(int j = 0;j< 2;j++){
                P_X[j] +=P_XY[i][j];
                P_Y[i] +=P_XY[i][j];
            }
        }
        double H_X = one.entinfo(P_X);
        System.out.println("熵X:"+H_X);
        double H_Y = one.entinfo(P_Y);
        System.out.println("熵Y:"+H_Y);
        double H_XY = one.unioninfo(P_XY);
        System.out.println("熵XY:"+H_XY);
        double H_X_given_Y = one.H_X_Yinfo(P_X,P_Y,P_XY);
        System.out.println("熵X|Y:"+H_X_given_Y);
        double I_XY = H_X - H_X_given_Y;
        System.out.println("平均互信息I:"+I_XY);
    }
}
