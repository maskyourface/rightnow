package ja.test4;

import java.sql.*;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class one {
    private static final Random random = new Random();

    public static void main(String[] args) {
        System.out.println("温度传感器启动...");

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        // 每10秒执行一次温度采集和存储
        scheduler.scheduleAtFixedRate(() -> {
            try {
                double temperature = generateTemperature();
                saveTemperature(temperature);
                System.out.println("[" + new java.util.Date() + "] 采集温度: " + temperature + "℃");
            } catch (Exception e) {
                System.err.println("温度采集失败: " + e.getMessage());
            }
        }, 0, 10, TimeUnit.SECONDS);
    }

    // 生成20±5℃范围内的随机温度
    private static double generateTemperature() {
        // 20±5℃ 即 15-25℃
        return 15 + random.nextDouble() * 10;
    }

    // 保存温度到数据库
    private static void saveTemperature(double temperature) {
        String sql = "INSERT INTO sample (sample_time, sample_data) VALUES (NOW(), ?)";
        try (Connection conn = DbConfig.dbConfig();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, temperature);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("保存温度数据失败", e);
        }
    }
}