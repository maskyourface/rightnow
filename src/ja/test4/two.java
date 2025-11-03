package ja.test4;

// TemperatureMonitorWithAlarm.java
import java.sql.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class two {
    private static final Queue<Double> temperatureHistory = new LinkedList<>();
    private static final double MIN_THRESHOLD = 18.0;
    private static final double MAX_THRESHOLD = 22.0;
    private static boolean alarmTriggered = false;

    public static void main(String[] args) {
        System.out.println("带报警功能的温度监控器启动...");
        System.out.println("温度阈值: [" + MIN_THRESHOLD + "℃, " + MAX_THRESHOLD + "℃]");

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(3);

        // 每10秒显示实时温度
        scheduler.scheduleAtFixedRate(() -> {
            try {
                Double currentTemp = getLatestTemperature();
                if (currentTemp != null) {
                    temperatureHistory.offer(currentTemp);
                    // 保持最近6个记录（1分钟的数据）
                    if (temperatureHistory.size() > 6) {
                        temperatureHistory.poll();
                    }
                    System.out.println("[" + new java.util.Date() + "] 实时温度: " + currentTemp + "℃");
                }
            } catch (Exception e) {
                System.err.println("获取实时温度失败: " + e.getMessage());
            }
        }, 0, 10, TimeUnit.SECONDS);

        // 每1分钟显示平均温度
        scheduler.scheduleAtFixedRate(() -> {
            try {
                if (!temperatureHistory.isEmpty()) {
                    double average = calculateAverage();
                    System.out.println("[" + new java.util.Date() + "] 最近1分钟平均温度: " +
                            String.format("%.2f", average) + "℃");
                }
            } catch (Exception e) {
                System.err.println("计算平均温度失败: " + e.getMessage());
            }
        }, 60, 60, TimeUnit.SECONDS);

        // 每10秒检查报警条件
        scheduler.scheduleAtFixedRate(() -> {
            try {
                checkAlarmCondition();
            } catch (Exception e) {
                System.err.println("检查报警条件失败: " + e.getMessage());
            }
        }, 0, 10, TimeUnit.SECONDS);
    }

    // 获取最新温度
    private static Double getLatestTemperature() {
        String sql = "SELECT sample_data FROM sample ORDER BY sample_time DESC LIMIT 1";
        try (Connection conn = DbConfig.dbConfig();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getDouble("sample_data");
            }
        } catch (SQLException e) {
            System.err.println("查询最新温度失败: " + e.getMessage());
        }
        return null;
    }

    // 计算平均温度
    private static double calculateAverage() {
        return temperatureHistory.stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);
    }

    // 检查报警条件
    private static void checkAlarmCondition() {
        if (temperatureHistory.size() < 6) {
            return; // 数据不足1分钟
        }

        boolean allOutOfRange = temperatureHistory.stream()
                .allMatch(temp -> temp < MIN_THRESHOLD || temp > MAX_THRESHOLD);

        if (allOutOfRange && !alarmTriggered) {
            alarmTriggered = true;
            System.out.println("❌❌❌ 报警！温度连续1分钟超出阈值范围 [" +
                    MIN_THRESHOLD + "℃, " + MAX_THRESHOLD + "℃]");
            System.out.println("最近1分钟温度记录: " + temperatureHistory);
        } else if (!allOutOfRange && alarmTriggered) {
            alarmTriggered = false;
            System.out.println("✅ 报警解除！温度恢复正常范围");
        }
    }
}