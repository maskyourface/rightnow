package xinxilun.test1;

public class fuction {
    //自信息量
    public double[] selfinfo(double[] P){
        double [] I = new double[P.length];
        for(int j = 0;j < P.length;j++){
            if(P[j] > 0){
                I[j] = -Math.log(P[j])/Math.log(2);
            }else{
                I[j] = 0 ;
            }
        }
        return I;
    }
    public double selfinfo(double P){
        double I = 0;
        if(P > 0){
            I = -Math.log(P)/Math.log(2);
        }else{
            return 0;
        }
        return I;
    }
    //信息熵
    public double entinfo(double[] P,int []N){
        double H = 0;
        for(int j = 0;j < N.length;j ++){
            double p = 1.0/N[j];
            double H_1 = -Math.log(p)/Math.log(2);
            H += P[j]*H_1;
        }
        return H;
    }
    public double entinfo(double[] P) {
        double H = 0;
        for(int j = 0;j<P.length;j ++){
            if(P[j] > 0){
                H += P[j]*(Math.log(P[j])/Math.log(2));
            }
        }
        return -H;
    }
    //联合熵
    public double unioninfo(double[][]P_XY){
        double H_XY = 0;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                if (P_XY[i][j] > 0) {
                    H_XY += P_XY[i][j] * (Math.log(P_XY[i][j]) / Math.log(2));
                }
            }
        }
        return -H_XY;
    }
    //条件商
    public double H_X_Yinfo(double[]P_X,double[]P_Y,double[][]P_XY){
        double H_X_given_Y = 0;
        for (int j = 0; j < 2; j++) {
            if (P_Y[j] > 0) {
                double[] P_X_given_Y = {P_XY[0][j] / P_Y[j], P_XY[1][j] / P_Y[j]};
                // 计算H(X|Y=y_j)
                double H_X_given_Y_j = entinfo(P_X_given_Y);
                H_X_given_Y += P_Y[j] * H_X_given_Y_j;
            }
        }
        return H_X_given_Y;
    }
    //平均互信息量
}
