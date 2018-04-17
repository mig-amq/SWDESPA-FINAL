package udc.database;

import udc.Model;
import udc.objects.account.Account;
import udc.objects.account.Client;
import udc.objects.account.Doctor;
import udc.objects.account.Secretary;
import udc.objects.enums.AgendaType;
import udc.objects.time.builders.RecurringAppointmentBuilder;
import udc.objects.time.builders.RecurringUnavailableBuilder;
import udc.objects.time.builders.SingleAppointmentBuilder;
import udc.objects.time.builders.SingleUnavailableBuilder;
import udc.objects.time.concrete.Agenda;
import udc.objects.time.concrete.Appointment;
import udc.objects.time.concrete.Unavailable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static udc.objects.time.concrete.Agenda.strToTime;

public class DataBaseController {
    private Connection connection = null;
    private PreparedStatement pStmt = null;
    private ResultSet rSet = null;
    private ResultSet rSet1 = null;
    private ResultSet rSet2 = null;
    private Model model;

    public DataBaseController(Model model) {
        this.model = model;
    }

    /**
     * Returns a temporary ArrayList of all the doctors from the database.
     * <p>
     * This function fetches all doctors from the database
     * and stores it into a temporary ArrayList.
     *
     * @return the temporary ArrayList
     */
    public ArrayList<String> loadDoctors() {
        ArrayList<String> doctorList = new ArrayList<>();
        try {
            connection = ConnectionConfiguration.getConnection(model);
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
        return doctorList;
    }

    /**
     * Returns a temporary ArrayList of all the clients from the database.
     * <p>
     * This function fetches all clients from the database
     * and stores it into a temporary ArrayList.
     * This function must be called before calling addClient()
     *
     * @return the temporary ArrayList
     */
    public ArrayList<String> loadClients() {
        ArrayList<String> clientList = new ArrayList<>();
        try {
            connection = ConnectionConfiguration.getConnection(model);
            pStmt = connection.prepareStatement("SELECT * FROM clinic_db.client");
            rSet = pStmt.executeQuery();

            while (rSet.next())
                clientList.add(rSet.getString("first_name"));
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
        for (int i = 0; i < clientList.size(); i++) {
            System.out.println(clientList.get(i));
        }
        return clientList;
    }

    /**
     * This function will insert a new row to the account table.
     *
     * @param userName username of the new account
     * @param pass     password of the new account
     * @param type     type of the new account
     * @param img_url the url of the account image, this can be null
     */
    public void addAccount(String userName, String pass, String type, String img_url) {
        try {
            String stmt = "INSERT INTO clinic_db.account (username, password, type) VALUES (?, ?, ?, ?)";
            connection = ConnectionConfiguration.getConnection(model);
            pStmt = connection.prepareStatement(stmt);
            pStmt.setString(1, userName);
            pStmt.setString(2, pass);
            pStmt.setString(3, type);
            pStmt.setString(4, img_url);

            if (pStmt.executeUpdate() == 1)
                System.out.println(type + " account successfully created.");
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
    }

    /**
     * This function inserts a regular client to the database.
     * However, addAccount() must be called first before calling this function,
     * because it refers to the latest account entry.
     *
     * @param firstName the first name of the client
     * @param lastName  the last name of the client
     */
    public void addClient(String firstName, String lastName) {
        try {
            connection = ConnectionConfiguration.getConnection(model);
            pStmt = connection.prepareStatement("SELECT * FROM clinic_db.account ORDER BY account_id DESC LIMIT 1");
            rSet = pStmt.executeQuery();

            if(rSet.next())
                System.out.println("Fetched latest account entry");

            String stmt = "INSERT INTO clinic_db.client (first_name, last_name, account_id, type) VALUES (?, ?, ?, ?)";

            pStmt = connection.prepareStatement(stmt);
            pStmt.setString(1, firstName);
            pStmt.setString(2, lastName);
            pStmt.setInt(3, rSet.getInt("account_id"));
            pStmt.setString(4, "REGULAR");

            if (pStmt.executeUpdate() == 1)
                System.out.println(firstName + " " + lastName + " successfully added to client table.");
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
    }

    /**
     * This function inserts a walk-in client to the database.
     *
     * @param firstName the first name of the client
     * @param lastName  the last name of the client
     */
    public void addWalkIn(String firstName, String lastName) {
        try {
            connection = ConnectionConfiguration.getConnection(model);

            String stmt = "INSERT INTO clinic_db.client (first_name, last_name, type) VALUES (?, ?, ?)";

            pStmt = connection.prepareStatement(stmt);
            pStmt.setString(1, firstName);
            pStmt.setString(2, lastName);
            pStmt.setString(3, "WALKIN");

            if(pStmt.executeUpdate() == 1)
                System.out.println(firstName + " " + lastName + " successfully inserted a new to client table as a walk-in.");
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
    }

    /**
     * Inserts a new appointment to the appointment table.
     *
     * @param time_start Starting date and time of the unavailability,
     *                   must have a format "yyyy/MM/dd hh:mm a"
     *                   and a type of LocalDateTime.
     * @param time_end   Ending date and time of the unavailability,
     *                   must have a format "yyyy/MM/dd hh:mm a"
     *                   and a type of LocalDateTime.
     * @param doctorID   — ID of the specific doctor assigned to the appointment
     * @param clientID   — ID of the client
     */
    public void addAppointment(LocalDateTime time_start, LocalDateTime time_end, int doctorID, int clientID) {
        String stmt = "INSERT INTO clinic_db.appointment (time_start, time_end, doctor_id, client_id) VALUES (?, ?, ?, ?)";
        try {
            connection = ConnectionConfiguration.getConnection(model);
            pStmt = connection.prepareStatement(stmt);
            pStmt.setString(1, timeToStr(time_start));
            pStmt.setString(2, timeToStr(time_end));
            pStmt.setInt(3, doctorID);
            pStmt.setInt(4, clientID);

            if (pStmt.executeUpdate() == 1)
                System.out.println("New appointment successfully added to database.");
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
    }

    /**
     * Inserts a new doctor unavailability date and time to the unavailability table.
     *
     * @param doctor_id  ID of the specific doctor.
     * @param time_start Starting date and time of the unavailability,
     *                   must have a format "yyyy/MM/dd hh:mm a"
     *                   and a type of LocalDateTime.
     * @param time_end   Ending date and time of the unavailability,
     *                   must have a format "yyyy/MM/dd hh:mm a"
     *                   and a type of LocalDateTime.
     * @param recurring  True or false, if the unavailability is recurring or not.
     */
    public void addUnavailability(int doctor_id, LocalDateTime time_start, LocalDateTime time_end, Boolean recurring) {
        String stmt = "INSERT INTO clinic_db.unavailability (doctor_id, time_start, time_end, recurring) VALUES (?, ?, ?, ?)";
        try {
            connection = ConnectionConfiguration.getConnection(model);
            pStmt = connection.prepareStatement(stmt);
            pStmt.setInt(1, doctor_id);
            pStmt.setString(2, timeToStr(time_start));
            pStmt.setString(3, timeToStr(time_end));
            pStmt.setBoolean(4, recurring);

            if (pStmt.executeUpdate() == 1)
                System.out.println("New doctor unavailability successfully added to database.");
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
    }

    /**
     * Updates a specific unavailability of a specific doctor.
     *
     * @param doctor_id  ID of the specific doctor.
     * @param time_start Starting date and time of the unavailability,
     *                   must have a format "yyyy/MM/dd hh:mm a"
     *                   and a type of LocalDateTime.
     * @param time_end   Ending date and time of the unavailability,
     *                   must have a format "yyyy/MM/dd hh:mm a"
     *                   and a type of LocalDateTime.
     * @param recurring  True or false, if the unavailability is recurring or not.
     */
    public void updateUnavailability(int doctor_id, LocalDateTime time_start, LocalDateTime time_end, Boolean recurring) {
        String stmt = "UPDATE clinic_db.unavailability " +
                "SET time_start = ? time_end = ?, recurring = ? " +
                "WHERE doctor_id = '" + doctor_id + "'";
        try {
            connection = ConnectionConfiguration.getConnection(model);
            pStmt = connection.prepareStatement(stmt);
            pStmt.setString(1, timeToStr(time_start));
            pStmt.setString(2, timeToStr(time_end));
            pStmt.setBoolean(3, recurring);

            if (pStmt.executeUpdate() == 1)
                System.out.println("New doctor unavailability successfully added to database.");
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
    }

    /**
     * Returns an ArrayList of Appointment based on the parameters.
     * <p>
     * This function retrieves the data from appointment table.
     * Then it stores it in a temporary ArrayList.
     *
     * @param id   — ID of the specific client or doctor.
     * @param type — the type of the account.
     * @return the temporary ArrayList, where data was instantiated.
     * @throws Exception table is empty.
     */
    public ArrayList<Agenda> getAppointments(int id, String type) throws Exception {
        SingleAppointmentBuilder builder;
        RecurringAppointmentBuilder rbuilder;

        try {
            ArrayList<Agenda> tempList = new ArrayList<>();

            connection = ConnectionConfiguration.getConnection(model);

            String stmt = "SELECT \n" +
                    "    time_start, recurring, appointment_id,\n" +
                    "    time_end,\n" +
                    "    CONCAT(D.first_name, ' ', D.last_name) AS doctor,\n" +
                    "    CONCAT(C.first_name, ' ', C.last_name) AS client\n" +
                    "FROM\n" +
                    "    clinic_db.appointment AS A \n" +
                    "        INNER JOIN\n" +
                    "    clinic_db.doctor AS D ON D.doctor_id = A.doctor_id\n" +
                    "        INNER JOIN\n" +
                    "    clinic_db.client AS C ON C.client_id = A.client_id\n";

            if (type.equalsIgnoreCase("DOCTOR")) {
                stmt += "WHERE D.doctor_id = '" + id + "'";
            } else if (type.equalsIgnoreCase("CLIENT"))
                stmt += "WHERE C.client_id = '" + id + "'";

            pStmt = connection.prepareStatement(stmt);

            rSet = pStmt.executeQuery();

            // Traversing result set and instantiating appointments to list
            while (rSet.next()) {
                builder = new SingleAppointmentBuilder(rSet.getString("doctor"), rSet.getString("client"));
                rbuilder = new RecurringAppointmentBuilder(rSet.getString("doctor"), rSet.getString("client"));

                if (rSet.getBoolean("recurring")) {
                    tempList.add(rbuilder.build(rSet.getInt("appointment_id"),
                            strToTime(rSet.getString("time_start")),
                            strToTime(rSet.getString("time_end")),
                            rSet.getString("doctor"),
                            rSet.getString("client")));
                } else {
                    tempList.add(builder.build(rSet.getInt("appointment_id"),
                            strToTime(rSet.getString("time_start")),
                            strToTime(rSet.getString("time_end")),
                            rSet.getString("doctor"),
                            rSet.getString("client")));
                }
            }

            return tempList;
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
        throw new Exception("Error: There are no appointments ( method @ getAppointments() dbcontrolller)");
    }

    /**
     * Returns an ArrayList of Appointment based on the parameters.
     * <p>
     * This function retrieves data from unavailability table.
     * Then it stores it in a temporary ArrayList.
     *
     * @param doctor_id — ID of the doctor. Set to -1 to fetch all.
     * @return an ArrayList of unavailable times of a specific Doctor
     * @throws Exception table is empty.
     */
    public ArrayList<Agenda> getUnvailability(int doctor_id) throws Exception {
        SingleUnavailableBuilder builder = new SingleUnavailableBuilder(doctor_id);
        RecurringUnavailableBuilder rbuilder = new RecurringUnavailableBuilder(doctor_id);

        try {
            ArrayList<Agenda> tempList = new ArrayList<>();

            connection = ConnectionConfiguration.getConnection(model);

            if (doctor_id < 0) {
                pStmt = connection.prepareStatement("SELECT * FROM clinic_db.unavailability");
            } else {
                pStmt = connection.prepareStatement("SELECT * FROM clinic_db.unavailability " +
                        "WHERE doctor_id = '" + doctor_id + "'");
            }
            rSet = pStmt.executeQuery();

            // Traversing result set and instantiating unavailability to temp list
            while (rSet.next()) {
                if (rSet.getBoolean("recurring")) {
                    tempList.add(builder.build(rSet.getInt("doctor_id"),
                            strToTime(rSet.getString("time_start")),
                            strToTime(rSet.getString("time_end"))));
                } else {
                    tempList.add(rbuilder.build(rSet.getInt("doctor_id"),
                            strToTime(rSet.getString("time_start")),
                            strToTime(rSet.getString("time_end"))));
                }
            }

            return tempList;
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
        throw new Exception("Error: There are no availability ( method @ getUnvailability() dbcontrolller)");
    }

    /**
     * Returns an account from the account table based on the parameters.
     * <p>
     * This function retrieves the credentials of a specific account based on the parameters.
     * Then it instantiates an account referring to the credentials found.
     *
     * @param username username of the account.
     * @param password password of the account.
     * @return the newly instantiated account.
     * @throws Exception account does not exist
     */
    public Account login(String username, String password) throws Exception {
        Account temp = null;

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
                            temp = new Doctor(rSet2.getString("first_name"), rSet2.getString("last_name"), rSet2.getInt("doctor_id"));
                            break;
                        case "client":
                            temp = new Client(rSet2.getString("first_name"), rSet2.getString("last_name"), rSet2.getInt("client_id"));
                            break;
                        default:
                            temp = new Secretary(rSet2.getString("first_name"), rSet2.getString("last_name"), rSet2.getInt("secretary_id"));
                    }

                    if (!rSet.getString("image_url").trim().replaceAll("\\s+", "").isEmpty())
                        temp.setImageURI(rSet.getString("image_url"));
                }

                rSet.close();
                rSet2.close();

                return temp;
            } else
                throw new Exception("Error: That account does not exist.");

        } catch (Exception e) {
            throw new Exception("Error: Cannot connect to the database");
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public ArrayList<Agenda> getExceptions (int doctor_id) {
        ArrayList<Agenda> temp0 = new ArrayList<>();
        Agenda temp1;

        try {
            connection = ConnectionConfiguration.getConnection(model);
            pStmt = connection.prepareStatement("SELECT U.except_dates AS ed FROM doctor D INNER JOIN unavailability U ON U.doctor_id = D.doctor_id WHERE U.doctor_id = " + doctor_id);

            rSet = pStmt.executeQuery();

            while (rSet.next()) {
                if (rSet.getBoolean("recurring")) {
                    String[] temp2 = rSet.getString("ed").split(";");
                    for (int i = 0; i < temp2.length; i++) {
                        temp1 = new Agenda();

                        String time1 = temp2[i].split(" - ")[0];
                        String time2 = temp2[i].split(" - ")[1];

                        temp1.setStartTime(strToTime(time1));
                        temp1.setEndTime(strToTime(time2));
                        temp1.setType(AgendaType.SINGLE);

                        temp0.add(temp1);
                    }
                }
            }

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

        return temp0;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    private LocalDateTime strToTime(String time) {
        return LocalDateTime.parse(time.toUpperCase(), DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm a"));
    }

    private String timeToStr(LocalDateTime time) {
        return time.format(DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm a"));
    }
}