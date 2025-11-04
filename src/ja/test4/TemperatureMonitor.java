package ja.test4;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TemperatureMonitor {
//    程序二：温度监控程序
//            功能要求
//
//    每10秒钟在控制台显示一次实时温度（最新温度）
//
//    每1分钟在控制台显示最近1分钟的平均温度
//
//    选做：当温度超出阈值[18,22]长达1分钟时报警
//
//            编写思路
//
//    获取实时温度
//
//    从数据库中查询最新的温度值（按时间倒序取第一条）。
//
//    定时显示实时温度
//
//    使用一个定时任务，每10秒查询一次最新温度并显示。
//
//    计算平均温度
//
//    每1分钟，我们计算过去1分钟内采集的温度的平均值。
//
//    我们可以维护一个列表，每10秒获取的温度都存入这个列表，然后每分钟计算平均值并清空列表。
//
//    报警功能（选做）
//
//    我们同样维护一个最近1分钟的温度列表（6个值，因为每10秒一个）。
//
//    每分钟检查这6个值是否都超出阈值[18,22]（即都小于18或都大于22），如果是，则报警。
//
//    步骤详解
//    步骤1：创建温度监控主类
//
//    使用两个定时任务：一个每10秒执行（显示实时温度），另一个每1分钟执行（显示平均温度并检查报警）。
//
//    步骤2：实时温度显示
//
//    每10秒从数据库获取最新温度，并添加到最近温度列表中，同时显示在控制台。
//
//    步骤3：平均温度显示和报警
//
//    每1分钟，计算最近温度列表的平均值，并显示。
//
//    同时，检查最近1分钟的所有温度是否都超出阈值，如果是则报警。
//
//    然后清空列表，开始新的1分钟周期。
    private static final Queue<Double> temperatures = new LinkedList<Double>();
    private static final double MIN_TEMPERATURE = 18.0;
    private static final double MAX_TEMPERATURE = 22.0;
    private static boolean alarm = false;

    public static void main(String[] args) {
        System.out.println("Temperature Monitor begin");
        System.out.println("温度阈值: [" + MIN_TEMPERATURE + "℃, " + MAX_TEMPERATURE + "℃]");
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        //每10秒显示实时温度
        scheduler.scheduleAtFixedRate(new Runnable() {
            public void run() {
                try {
                    Double currentTemp = getTemperature();
                    if(currentTemp != null) {
                        temperatures.offer(currentTemp);
                        if(temperatures.size() > 6){
                            temperatures.poll();
                        }
                        System.out.println("[" + new java.util.Date() + "] 实时温度: " + currentTemp + "℃");
                    }
                }catch (Exception e) {
                    System.err.println("获取实时温度失败: " + e.getMessage());
                }
            }
        },0,10, TimeUnit.SECONDS);
        //每10秒检查报警条件
        scheduler.scheduleAtFixedRate(new Runnable() {
            public void run() {
                try {
                    checkTemperature();
                }catch (Exception e) {
                    System.err.println("检查报警条件失败: " + e.getMessage());
                }
            }
        },0,10, TimeUnit.SECONDS);
        //每1分钟显示平均温度
        scheduler.scheduleAtFixedRate(new Runnable() {
            public void run() {
                try {
                    if(!temperatures.isEmpty()){
                        double average = avg();
                        System.out.println("[" + new java.util.Date() + "] 最近1分钟平均温度: " +
                                String.format("%.2f", average) + "℃");
                    }
                }catch (Exception e) {
                    System.err.println("计算平均温度失败: " + e.getMessage());
                }
            }
        },60,60, TimeUnit.SECONDS);

    }
    //获取最新温度
    private static Double getTemperature() {
        String sql = "SELECT sample_data FROM sample ORDER BY sample_time DESC";
        try(Connection conn = DbConfig.dbConfig();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
            if(rs.next()) {
                return rs.getDouble("sample_data");
            }
        } catch (SQLException e) {
            System.err.println("查询最新温度失败: " + e.getMessage());
        }
        return null;
    }

    //计算平均温度
    private static double avg(){
        return temperatures.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
    }

    //检查报警条件
    private static void checkTemperature() {
        if(temperatures.size() < 6){
            return ;
        }
        boolean a = temperatures.stream().allMatch(temp -> temp > MIN_TEMPERATURE && temp < MAX_TEMPERATURE);
        if (a && !alarm) {
            alarm = true;
            System.out.println("报警！温度连续1分钟超出阈值范围 [" +
                    MIN_TEMPERATURE + "℃, " + MAX_TEMPERATURE + "℃]");
            System.out.println("最近1分钟温度记录: " + temperatures);
        } else if (!a && alarm) {
            alarm = false;
            System.out.println("报警解除！温度恢复正常范围");
        }
    }
}
