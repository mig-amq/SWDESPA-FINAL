package udc.secretary.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import udc.Model;
import udc.objects.time.concrete.Agenda;
import udc.objects.time.concrete.Appointment;
import udc.objects.time.concrete.Unavailable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class SecWalkInControl {
    private Node secWalkInNode;
    private ObservableList<Node> nodeComponents;
    private JFXListView listWalkIn;
    private JFXButton btnApprove, btnDeny;
    private ObservableList<String> observableList;
    private Model model;

    public SecWalkInControl(Model model){
        this.model = model;
        secWalkInNode = loadWalkInView();
        nodeComponents = ((AnchorPane) secWalkInNode).getChildren();
        initComponents();
        observableList = FXCollections.observableArrayList(getWalkInAppointments());
        listWalkIn.setItems(observableList);
    }

    private Node loadWalkInView(){
        Node node = null;
        try{
            node = FXMLLoader.load(getClass().getResource("../FXMLFiles/SecWalkInView.fxml"));
        }catch (Exception e){
            e.printStackTrace();
        }
        return node;
    }

    public Node getSecWalkInNode(){
        return secWalkInNode;
    }

    private void initComponents(){
        for (int i = 0; i < nodeComponents.size(); i++){
            Node node = nodeComponents.get(i);
            if (node.getId().equals("listWalkIn"))
                listWalkIn = (JFXListView) node;
            else if (node.getId().equals("btnApprove"))
                btnApprove = (JFXButton) node;
            else if (node.getId().equals("btnDeny"))
                btnDeny = (JFXButton) node;
        }
        initActions();
    }

    private void initActions(){
        btnApprove.setOnAction(event -> {
            if (listWalkIn.getItems().size() != 0) {
                String[] sub = listWalkIn.getSelectionModel().getSelectedItem().toString().split(" ");
                if (isValidAppointment(sub)) {
                    Appointment a = new Appointment();
                    a.setId(Integer.parseInt(sub[0].trim()));
                    model.getDbController().acceptWalkin(a);
                    observableList.remove(listWalkIn.getSelectionModel().getSelectedIndex());
                    listWalkIn.setItems(observableList);
                } else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Slot taken!");
                    alert.setContentText("The chosen appointment slot is already taken!");
                    alert.showAndWait();
                }
            }
        });

        btnDeny.setOnAction(event ->{
            if (listWalkIn.getItems().size() != 0) {
                String[] sub = listWalkIn.getSelectionModel().getSelectedItem().toString().split(" ");
                Appointment a = new Appointment();
                a.setId(Integer.parseInt(sub[0].trim()));
                model.getDbController().deleteAppointment(a);
                model.setState();
                observableList.remove(listWalkIn.getSelectionModel().getSelectedIndex());
                listWalkIn.setItems(observableList);
            }
        });
    }

    private boolean isValidAppointment(String[] sub){
        //id, mm-dd-yyyy:, hrS:minSsTimeofDay, -, hrE:minEeTimeofDay, Dr.Doctorname, clientname
        //String[] date = sub[1].split("-"); //date[0] month, date[1] day, date[2].substring(0,4) year
        boolean valid = true;
        String[] date = sub[1].split("-");
        int m = Integer.parseInt(date[0]);
        int d = Integer.parseInt(date[1]);
        int y = Integer.parseInt(date[2].substring(0, 4));

        //String[] starttime = sub[2].split(":") starttime[0] hour, starttime[1].substring(0, 2) minute
        String[] starttime = sub[2].split(":");
        int hrS = Integer.parseInt(starttime[0]);
        int minS = Integer.parseInt(starttime[1].substring(0, 2));
        if (starttime[1].substring(2, 4).equalsIgnoreCase("pm"))
            hrS += 12;

        //String[] endtime = sub[4].split(":") endtime[0] hour, endttime[1].substring(0, 2) minute
        String[] endtime = sub[4].split(":");
        int hrE = Integer.parseInt(endtime[0]);
        int minE = Integer.parseInt(endtime[1].substring(0, 2));
        if (endtime[1].substring(2, 4).equalsIgnoreCase("pm"))
            hrE += 12;

        LocalDateTime appSDateTime = LocalDateTime.of(y, m, d, hrS, minS);
        LocalDateTime appEDateTime = LocalDateTime.of(y, m, d, hrE, minE);

        //lacks checking if the time slot is available
        try {
            ArrayList<Agenda> appointments = model.getDbController().getAppointments(-1, "");
            for (int i = 0; i < appointments.size(); i++){
                if (appSDateTime.isEqual(appointments.get(i).getStartTime()) || appEDateTime.isEqual(appointments.get(i).getEndTime())
                    || (appSDateTime.isBefore(appointments.get(i).getEndTime()) && appSDateTime.isAfter(appointments.get(i).getStartTime()))
                    || (appEDateTime.isAfter(appointments.get(i).getStartTime()) && appEDateTime.isBefore(appointments.get(i).getEndTime())))
                    valid = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return valid;
    }

    public void resetList(){
        listWalkIn.setItems(FXCollections.observableArrayList(""));
    }

    public ArrayList<String> getWalkInAppointments(){
        ArrayList<String> walkIns = new ArrayList<String>();
        ArrayList<Agenda> agendas = model.getDbController().getWalkinAppointments();

        for (int i = 0; i < agendas.size(); i++){
            Appointment a = (Appointment) agendas.get(i);
            String hrS = Integer.toString(a.getStartTime().getHour());
            String minS = Integer.toString(a.getStartTime().getMinute());
            String hrE = "";
            String minE = "";
            hrE = Integer.toString(a.getEndTime().getHour());
            minE = Integer.toString(a.getEndTime().getMinute());

            String sTimeOfDay = "AM";
            String eTimeOfDay = "AM";

            if (a.getStartTime().getHour() < 10)
                hrS = "0" + hrS;

            else if (a.getStartTime().getHour() >= 12) {
                if (a.getStartTime().getHour() != 12)
                    hrS = Integer.toString(Integer.parseInt(hrS) - 12);
                sTimeOfDay = "PM";
            }

            if (a.getStartTime().getMinute() < 10)
                minS = "0" + minS;

            if (a.getEndTime().getHour() < 10)
                hrE = "0" + hrE;
            else if (a.getEndTime().getHour() >= 12) {
                if (a.getEndTime().getHour() != 12)
                    hrE = Integer.toString(Integer.parseInt(hrE) - 12);
                eTimeOfDay = "PM";
            }

            if (a.getEndTime().getMinute() < 10)
                minE = "0" + minE;

            walkIns.add(a.getId() + " " + a.getStartTime().getMonth() + "-" + a.getStartTime().getDayOfMonth() + "-" + a.getStartTime().getYear()
                    + ": " + hrS + ":" + minS + sTimeOfDay + " - " + hrE + minE + eTimeOfDay + ", Dr." + a.getDoctorName() + ", " + a.getClientName());
        }

        return walkIns;
    }

    private ArrayList<Agenda> getAvailableSlots(LocalDate selected, int doctor_id){
        ArrayList<Unavailable> availableSlots = new ArrayList<>();
        ArrayList<Agenda> available = new ArrayList<>();
        ArrayList<Agenda> appointments = new ArrayList<>();
        try {
            availableSlots = model.getDbController().getUnvailability(doctor_id); //returns availability of that doctor
            appointments = model.getDbController().getAppointments(-1, "");
        } catch(Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < availableSlots.size(); i++){
            if (!availableSlots.get(i).getStartTime().toLocalDate().isEqual(selected))
                availableSlots.remove(i);
        }
        availableSlots.trimToSize();

        for (int i = 0; i < availableSlots.size(); i++){
            for (int j = 0; j < appointments.size(); j++){
                if (appointments.get(j).getStartTime().toLocalDate().isEqual(selected) && availableSlots.get(i).getStartTime().toLocalDate().isEqual(selected))
                    if (availableSlots.get(i).getStartTime().toLocalTime().equals(appointments.get(j).getStartTime().toLocalTime()))
                        availableSlots.remove(i);
            }
        }
        availableSlots.trimToSize();

        for (int i = 0; i < availableSlots.size(); i++)
            available.add((Agenda) availableSlots.get(i));
        available.trimToSize();


        return available;
    }
}
