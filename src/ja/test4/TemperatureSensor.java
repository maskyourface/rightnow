package ja.test4;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TemperatureSensor {
//    功能要求
//
//            每10秒钟向数据库发送一个温度值
//
//    温度值在20±5℃范围内随机变化（即15℃到25℃之间）
//
//    数据库表有两个字段：sample_time（时间戳）和sample_data（温度值，小数）
//
//    编写思路
//
//            建立数据库连接
//
//    首先，我们需要一个数据库配置类（DbConfig）来管理数据库连接信息。
//
//    在这个类中，我们加载MySQL驱动，并提供获取数据库连接的方法。

//
//    定时任务
//
//    使用定时任务，每10秒执行一次。
//
//    在定时任务中，生成随机温度，并插入数据库。
//
//    生成随机温度
//
//    使用Java的Random类生成15.0到25.0之间的随机小数。
//
//    插入数据库
//
//    使用SQL插入语句，将当前时间戳（或由数据库自动生成）和温度值插入数据库。
//
//    步骤详解
//    步骤1：创建DbConfig类
//
//    这个类负责数据库连接，我们使用单例模式或者静态方法来实现。
//    步骤2：创建温度传感器主类
//
//    在主类中，我们首先调用DbConfig的创建表方法。
//
//    然后，我们使用ScheduledExecutorService来定时执行温度采集和存储。
//
//    步骤3：编写温度生成和存储方法
//
//    温度生成：使用Random.nextDouble()生成0到1之间的随机数，然后转换为15到25之间的数。
//
//    存储方法：使用PreparedStatement来执行INSERT语句。

    private static final Random rand = new Random();

    public static void main(String[] args) {
        System.out.println("Temperature Sensor begin");

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try{
                    double temperature = random_temperateness();
                    save(temperature);
                    System.out.println("[" + new java.util.Date() + "] 采集温度: " + temperature + "℃");

                }catch (Exception e){
                    System.err.println("温度采集失败:" + e.getMessage());
                }
            }
        },0,10, TimeUnit.SECONDS);

    }

    private static double random_temperateness() {
        return 15 + rand.nextDouble() * 10;
    }

    private static void save(double temp) {
        String sql = "INSERT INTO sample(sample_time, sample_data) VALUES (NOW(), ?)";
        try(Connection conn = DbConfig.dbConfig();
            //预编译防止sql注入
        PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, temp);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("保存失败",e);
        }
    }
}
