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
            int id = 0;
            String type = "";

            String stmt = "SELECT \n" +
                    "    time_start,\n" +
                    "    time_end,\n" +
                    "    CONCAT(D.first_name, ' ', D.last_name) AS doctor,\n" +
                    "    CONCAT(C.first_name, ' ', C.last_name) AS client\n" +
                    "FROM\n" +
                    "    clinic_db.appointment\n" +
                    "        INNER JOIN\n" +
                    "    clinic_db.doctor AS D ON D.doctor_id = clinic_db.appointment.doctor_id\n" +
                    "        INNER JOIN\n" +
                    "    clinic_db.client AS C ON C.client_id = clinic_db.appointment.client_id\n";

            if (type.equalsIgnoreCase("DOCTOR")) {
                stmt += "WHERE doctor_id = '" + id + "'";
            } else if (type.equalsIgnoreCase("CLIENT"))
                stmt += "WHERE client_id = '" + id + "'";

            pStmt = connection.prepareStatement(stmt);

            rSet = pStmt.executeQuery();

            // Traversing result set and instantiating appointments to list
            while (rSet.next()) {
                System.out.println(rSet.getString("time_start") + " " + rSet.getString("doctor") + " " + rSet.getString("client"));
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

