package ja.test4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//配置链接mysql
public class DbConfig {
    public static Connection dbConfig() throws SQLException{
        //try-catch
        try{
            //链接jdbc
            Class.forName("com.mysql.cj.jdbc.Driver");
            //如果成功
            System.out.println("Connecting to database...");
        }
        //捕捉异常
        catch(Exception e){
            //链接失败
            System.out.println("Driver not found");
            //打印日志
            e.printStackTrace();
        }
        //39.107.192.129:3306 是服务器
        //test是数据库
        String url = "jdbc:mysql://39.107.192.129:3306/test?useUnicode=false&serverTimezone=Asia/Shanghai&characterEncoding=utf8&autoReconnect=true";
        String user = "myq";
        String pass = "mmyq";
        Connection connection = DriverManager.getConnection(url, user, pass);
        return connection;
    }
}
