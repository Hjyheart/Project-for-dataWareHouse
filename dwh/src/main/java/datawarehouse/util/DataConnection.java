package datawarehouse.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by hongjiayong on 2017/1/1.
 */
public class DataConnection {

    private static final String mysql_driver = "com.mysql.jdbc.Driver";
    private static final String mysql_url = "jdbc:mysql://10.60.42.202:3306/comment";
    //    private static final String mysql_url = "jdbc:mysql://192.168.1.111:3306/datawarehouse";
    private static final String mysql_user = "root";
    //    private static final String mysql_password = "qianyu123";
    private static final String mysql_password = "GCers+518";

    private static final String hive_driver = "org.apache.hive.jdbc.HiveDriver";
    private static final String hive_url = "jdbc:hive2://10.60.42.201:10000/comment";
    private static final String hive_user = "root";
    private static final String hive_password = "ibmclub";


    private static Connection getMysqlConnection() throws ClassNotFoundException, SQLException {
        Class.forName(mysql_driver);
        Connection connection = DriverManager.getConnection(mysql_url, mysql_user, mysql_password);

        return connection;
    }

    private static Connection getHiveConnection() throws ClassNotFoundException, SQLException {
        Class.forName(hive_driver);
        Connection connection = DriverManager.getConnection(hive_url, hive_user, hive_password);

        return connection;
    }

    public static Connection getConnection(String type) {
        try {
            if (type == "hive")
                return getHiveConnection();
            else
                return getMysqlConnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

