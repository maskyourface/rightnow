package test4;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class test1 {
    public static void main(String[] args) throws SQLException {
        Connection conn =  new DbConfig().dbConfig();
        String sql = "select * from sample";
        ResultSet rs = conn.createStatement().executeQuery(sql);
        while (rs.next()) {
            System.out.println(rs.getString("sample_time"));

        }
    }
}