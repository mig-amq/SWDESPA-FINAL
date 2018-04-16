package udc.database;

import java.sql.*;
import java.util.ArrayList;

public class DBTest {
    private static String dbName = "clinic_db";
    private static String driverName = "com.mysql.cj.jdbc.Driver";
    private static String socket = "localhost:3306";

    private static String url = "jdbc:mysql://" + socket + "/"+ dbName + "?autoReconnect=true&useSSL=false";
    private static String user = "root"; // edit this to ur MySQL username
    private static String password = "admin"; // edit this to ur MySQL pass

    private static Connection connection = null;
    private static PreparedStatement pStmt = null;
    private static ResultSet rSet = null;
    private static ResultSet rSet1 = null;
    private static ResultSet rSet2 = null;

    public static void main(String[] args) {


            connection = getConnection();
            ArrayList<String> doctorList = new ArrayList<>();
            try {

                pStmt = connection.prepareStatement("SELECT * FROM clinic_db.doctor");
                rSet = pStmt.executeQuery();

                while (rSet.next())
                    doctorList.add(rSet.getString("first_name"));

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            for (int i = 0; i < doctorList.size(); i++) {
                System.out.println(doctorList.get(i));
            }


    }

    public static Connection getConnection() {
        Connection connection = null;

        connection = getConnection(connection, driverName, url, user, password);
        return connection;
    }

    public static Connection getConnection(Connection connection, String driverName, String url, String user, String password) {
        try {
            Class.forName(driverName);
            connection = DriverManager.getConnection(url, user, password);
            if (connection != null)
                System.out.println("Connection to db successful");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
}

