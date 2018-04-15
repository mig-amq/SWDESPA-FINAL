package udc.database;

import java.sql.*;

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
        try {

            connection = getConnection();
            pStmt = connection.prepareStatement("SELECT * FROM clinic_db.appointment");

            if (-1 < 0) {
                pStmt = connection.prepareStatement("SELECT * FROM clinic_db.appointment");
            } else {
                pStmt = connection.prepareStatement("SELECT * FROM clinic_db.appointment WHERE doctor_id = '" + 4 + "'");
            }

            rSet = pStmt.executeQuery();

            pStmt = connection.prepareStatement("SELECT first_name, last_name FROM clinic_db.doctor  INNER JOIN clinic_db.appointment ON clinic_db.appointment.doctor_id = clinic_db.doctor.doctor_id");
            rSet1 = pStmt.executeQuery();

            pStmt = connection.prepareStatement("SELECT first_name, last_name FROM clinic_db.client\n" +
                    "INNER JOIN clinic_db.appointment \n" +
                    "ON clinic_db.appointment.doctor_id = clinic_db.client.client_id");
            rSet2 = pStmt.executeQuery();

            // traversing result set and instantiating appointments to list
            while (rSet.next() && rSet1.next() && rSet2.next()) {
                System.out.println(rSet.getString("time_start"));
            }

        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            if(connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
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

