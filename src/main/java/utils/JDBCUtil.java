package utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class JDBCUtil { 
    
    public static Connection getConnection() {
        
        Connection connection = null;
        try {
            Class.forName("net.sf.log4jdbc.DriverSpy");
            connection = DriverManager.getConnection(
                    "jdbc:log4jdbc:postgresql://hd40.blueapple.mobi:18000/ba", "vuclip2",
                    "blue123");
        } catch (Exception e) { 
            e.printStackTrace();
        }
        return connection;
    }
}
