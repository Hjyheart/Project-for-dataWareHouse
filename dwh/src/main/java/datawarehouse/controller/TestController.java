package datawarehouse.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.*;

/**
 * Created by hongjiayong on 2016/12/30.
 */
@Controller
public class TestController {

    private static String driverName = "org.apache.hive.jdbc.HiveDriver";
    @RequestMapping("/test")
    @ResponseBody
    public String test() throws SQLException {
        Connection connection = null;

        connection = DriverManager.getConnection("jdbc:mysql://10.60.42.202:3306/comment", "root", "GCers+518");

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM actor LIMIT 20");
        while (rs.next()){
            System.out.println(rs.getString("id"));
        }

        connection.close();

        return "hehe";
    }


    @RequestMapping("/hive")
    @ResponseBody
    public String hiveTest() throws SQLException, ClassNotFoundException {
        Class.forName(driverName);
        Connection connection = null;

        connection = DriverManager.getConnection("jdbc:hive2://10.60.42.201:10000/comment;transportMode=http", "root", "ibmclub");
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM actor LIMIT 20");
        while (rs.next()){
            System.out.println(rs.getString("id"));
        }

        connection.close();


//        String host = "10.60.42.201";
//        int port = 10000;

//        TTransport transport = new TSocket(host, port);
//        TProtocol protocol = new TBinaryProtocol(transport);
//        HiveAuthFactory hiveAuthFactory;

        return "hehe";
    }
}
