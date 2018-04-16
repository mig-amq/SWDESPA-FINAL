package udc.database;

import udc.Model;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionConfiguration {
    private static String dbName = "clinic_db";
    private static String driverName = "com.mysql.cj.jdbc.Driver";
    private static String socket = "localhost:3306";

    private static String url = "jdbc:mysql://" + socket + "/"+ dbName + "?autoReconnect=true&useSSL=false";
    private static String user = "root"; // edit this to ur MySQL username
    private static String password = "admin"; // edit this to ur MySQL pass

    public ConnectionConfiguration() {
        user = "root";
        password = "admin";
        socket = "localhost:3306";
        dbName = "clinic_db";
        driverName = "com.mysql.cj.jdbc.Driver";

        url = "jdbc:mysql://" + socket + "/"+ dbName + "?autoReconnect=true&useSSL=false";
    }

    private static void setSettings(Model model) {
        dbName = "clinic_db";
        user = model.getDbUser();
        password = model.getDbPass();
        socket = model.getDbAddress() + ":" + model.getDbPort();
        driverName = "com.mysql.cj.jdbc.Driver";

        url = "jdbc:mysql://" + socket + "/"+ dbName + "?autoReconnect=true&useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        System.out.println(url);
    }

    public static Connection getConnection(Model model) {
        setSettings(model);
        Connection connection = null;

        try {
            Class.forName(driverName);
            connection = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return connection;
    }

}
