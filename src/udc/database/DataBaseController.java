package udc.database;

import udc.Model;
import udc.objects.account.Account;
import udc.objects.account.Client;
import udc.objects.account.Doctor;
import udc.objects.account.Secretary;
import udc.objects.time.builders.RecurringAppointmentBuilder;
import udc.objects.time.builders.SingleAppointmentBuilder;
import udc.objects.time.builders.SingleUnavailableBuilder;
import udc.objects.time.concrete.Appointment;
import udc.objects.time.concrete.Unavailable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class DataBaseController {
    private Connection connection = null;
    private PreparedStatement pStmt = null;
    private ResultSet rSet = null;
    private ResultSet rSet1 = null;
    private ResultSet rSet2 = null;
    private Model model = null;

    public DataBaseController(Model model) {
        this.model = model;
    }

    /*
        Adds an account to the database
     */
    public void addAccount(String userName, String pass, String type) {
        try {
            String stmt = "INSERT INTO clinic_db.account (username, password, type) VALUES (?, ?, ?)";
            connection = ConnectionConfiguration.getConnection(model);
            pStmt = connection.prepareStatement(stmt);
            pStmt.setString(1, userName);
            pStmt.setString(1, pass);
            pStmt.setString(1, type);
            pStmt.executeUpdate();

            System.out.println(type + " account successfully created.");
        } catch (Exception e) {
            e.printStackTrace();
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

    /*
        Adds a normal client  to the database
        Pre-requisite: Must call addAccount first before this, because it refers to the latest account entry.
                       If client is walk in, call addWalkIn() instead.
     */
    public void addClient(String firstName, String lastName) {
        try {
            connection = ConnectionConfiguration.getConnection(model);
            pStmt = connection.prepareStatement("SELECT * FROM clinic_db.account ORDER BY account_id DESC LIMIT 1");
            rSet = pStmt.executeQuery();

            String stmt = "INSERT INTO clinic_db.client (first_name, last_name, account_id, type) VALUES (?, ?, ?, ?)";

            pStmt = connection.prepareStatement(stmt);
            pStmt.setString(1, firstName);
            pStmt.setString(2, lastName);
            pStmt.setInt(3, rSet.getInt("account_id"));
            pStmt.setString(4, rSet.getString("type"));
            pStmt.executeUpdate();
            System.out.println(firstName + " " + lastName + " Successfully added to client table.");
        } catch (Exception e) {
            e.printStackTrace();
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

    /*
        Adds a walk in client to the client table.
     */
    public void addWalkIn(String firstName, String lastName) {
        try {
            connection = ConnectionConfiguration.getConnection(model);

            String stmt = "INSERT INTO clinic_db.client (first_name, last_name, type) VALUES (?, ?, ?, ?)";

            pStmt = connection.prepareStatement(stmt);
            pStmt.setString(1, firstName);
            pStmt.setString(2, lastName);
            pStmt.setString(3, "walk-in");
            pStmt.executeUpdate();

            System.out.println(firstName + " " + lastName + " Successfully added to client table.");
        } catch (Exception e) {
            e.printStackTrace();
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

    // Add appointment to the database
    public void addAppointment(String date, int time_start, int doctorID, int clientID) {
        String stmt = "INSERT INTO clinic_db.appointment (date, time_start, time_end, doctor_doctor_id, client_client_id) VALUES (?, ?, ?, ?, ?)";
        try {
            connection = ConnectionConfiguration.getConnection(model);
            pStmt = connection.prepareStatement(stmt);
            pStmt.setString(1, date);
            pStmt.setInt(2, time_start);
            pStmt.setInt(3, time_start + 30); // + 30 cuz end time is fixed to 30 minutes from start
            pStmt.setInt(4, doctorID);
            pStmt.setInt(5, clientID);
            pStmt.executeUpdate();

            System.out.println("New appoitnment Successfully added to database.");
        } catch (Exception e) {
            e.printStackTrace();
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

    // Add doctor availability to the database
    public void addAvailability (int doctorID, String date, int time_start, int time_end) {
        String stmt = "INSERT INTO clinic_db.availability (doctor_id, date, time_start, time_end) VALUES (?, ?, ?, ?)";
        try {
            connection = ConnectionConfiguration.getConnection(model);
            pStmt = connection.prepareStatement(stmt);
            pStmt.setInt(1, doctorID);
            pStmt.setString(2, date);
            pStmt.setInt(3, time_start);
            pStmt.setInt(4, time_end);
            pStmt.executeUpdate();

            System.out.println("New Doctor Availability Successfully added to database.");
        } catch (Exception e) {
            e.printStackTrace();
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

    public void loadDoctors() {
        try {
            connection = ConnectionConfiguration.getConnection(model);
            pStmt = connection.prepareStatement("SELECT * FROM clinic_db.doctor");
            rSet = pStmt.executeQuery();

            // edit beyond this to manipulate result set...
            while(rSet.next()){
                System.out.println(rSet.getString("first_name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
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

    public void loadClients() {
        try {
            connection = ConnectionConfiguration.getConnection(model);
            pStmt = connection.prepareStatement("SELECT * FROM clinic_db.client");
            rSet = pStmt.executeQuery();

            // edit beyond this to manipulate result set...
            if(rSet.next()){
                System.out.println(rSet.getString("first_name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
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

    /*
       Retrieves data from appointment table then stores it in an ArrayList

        set doctor_id param to the desired doctor
        set to -1 if intent to get all.
        Returns appointments under a specific doctor

       Returns the array list.
     */
    public ArrayList<Appointment> getAppointments(int doctor_id) throws Exception {
        SingleAppointmentBuilder builder = new SingleAppointmentBuilder();
        RecurringAppointmentBuilder rbuilder = new RecurringAppointmentBuilder();

        try {
            ArrayList<Appointment> tempList = new ArrayList<>();
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

            connection = ConnectionConfiguration.getConnection(model);

            pStmt = connection.prepareStatement("SELECT * FROM clinic_db.appointment");

            if(doctor_id < 0) {
                pStmt = connection.prepareStatement("SELECT * FROM clinic_db.appointment");
            } else {
                pStmt = connection.prepareStatement("SELECT * FROM clinic_db.appointment WHERE doctor_id = '" + doctor_id + "'");
            }

            rSet = pStmt.executeQuery();

            pStmt = connection.prepareStatement("SELECT first_name, last_name FROM clinic_db.doctor  INNER JOIN clinic_db.appointment ON clinic_db.appointment.doctor_id = clinic_db.doctor.doctor_id");
            rSet1 = pStmt.executeQuery();

            pStmt = connection.prepareStatement("SELECT first_name, last_name FROM clinic_db.client\n" +
                    "INNER JOIN clinic_db.appointment \n" +
                    "ON clinic_db.appointment.doctor_id = clinic_db.client.client_id");
            rSet2 = pStmt.executeQuery();

            // Need to do this, to position cursor/pointer to the first row.
            if(rSet1.next() && rSet2.next()) {
                // traversing result set and instantiating appointments to list
                while (rSet.next()) {
                    if (rSet.getBoolean("recurring")) {
                        tempList.add(rbuilder.build(strToTime(rSet.getString("time_start")),
                                strToTime(rSet.getString("time_end")),
                                rSet1.getString("first_name")  + " " + rSet1.getString("last_name"),
                                rSet2.getString("first_name")  + " " + rSet2.getString("last_name")));

                    } else {
                        tempList.add(builder.build(strToTime(rSet.getString("time_start")),
                                strToTime(rSet.getString("time_end")),
                                rSet1.getString("first_name")  + " " + rSet1.getString("last_name"),
                                rSet2.getString("first_name")  + " " + rSet2.getString("last_name")));
                    }
                }
            }
            return tempList;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        throw new Exception ("Error: There are no appointments ( method @ getAppointments() dbcontrolller)");
    }

    /*
        set doctor_id param to the desired doctor or -1 to get all.
        Returns Availability of a doctor. or both.
     */
    public ArrayList<Unavailable> getAvailability(int doctor_id) throws Exception {
        SingleUnavailableBuilder builder = new SingleUnavailableBuilder();
        try {
            ArrayList<Unavailable> tempList = new ArrayList<>();
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

            connection = ConnectionConfiguration.getConnection(model);

            if(doctor_id < 0) {
                pStmt = connection.prepareStatement("SELECT * FROM clinic_db.availability");
            } else {
                pStmt = connection.prepareStatement("SELECT * FROM clinic_db.availability WHERE doctor_id = '" + doctor_id + "'");
            }
            rSet = pStmt.executeQuery();

                // traversing result set and instantiating Availability to temp list

                while (rSet.next()) {
                    tempList.add(builder.build(strToTime(rSet.getString("time_start")),
                            strToTime(rSet.getString("time_end"))));
                }

            return tempList;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        throw new Exception ("Error: There are no availability ( method @ getAvailability() dbcontrolller)");
    }

    public Account login(String username, String password) throws Exception{
        try {
            connection = ConnectionConfiguration.getConnection(model);
            pStmt = connection.prepareStatement("SELECT * FROM clinic_db.account WHERE `username` = '" + username +
                    "' AND `password` = '" + password + "'");
            rSet = pStmt.executeQuery();

            String sql = "";
            if (rSet.next()) {
                switch (rSet.getString("type")) {
                    case "doctor":
                        sql = "SELECT * FROM doctor WHERE account_id = " + rSet.getInt("account_id");
                        break;
                    case "secretary":
                        sql = "SELECT * FROM secretary WHERE account_id = " + rSet.getInt("account_id");
                        break;
                    default:
                        sql = "SELECT * FROM client WHERE account_id = " + rSet.getInt("account_id");
                        break;
                }

                pStmt = connection.prepareStatement(sql);
                ResultSet rSet2 = pStmt.executeQuery();

                if (rSet2.next()) {
                    switch (rSet.getString("type")) {
                        case "doctor":
                            return new Doctor(rSet2.getString("first_name"), rSet2.getString("last_name"), rSet2.getInt("doctor_id"));
                        case "client":
                            return new Client(rSet2.getString("first_name"), rSet2.getString("last_name"), rSet2.getInt("client_id"));
                        default:
                            return new Secretary(rSet2.getString("first_name"), rSet2.getString("last_name"), rSet2.getInt("secretary_id"));
                    }
                }

                rSet.close();
                rSet2.close();

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        throw new Exception ("Error: That account does not exist.");
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    private LocalDateTime strToTime (String time) {
        return LocalDateTime.parse(time, DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm a"));
    }

    private String timeToStr (LocalDateTime time) {
        return time.format(DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm a"));
    }
}
