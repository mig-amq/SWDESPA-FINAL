package udc.doctor.controllers;

import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import udc.Model;
import udc.objects.time.concrete.Agenda;
import udc.objects.time.concrete.Appointment;
import udc.objects.time.concrete.Available;
import udc.objects.time.concrete.Unavailable;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class AgendaWeek extends AnchorPane {
    private Model model;
    private ArrayList<Agenda> appointments;
    private ArrayList<Available> availabilities;
    private Stage stage;
    private Node node;
    private Label lblSlots;
    @FXML
    private JFXListView agendaList;

    public AgendaWeek(Model md) throws IOException {
        this.setModel(md);
        this.setAppointments(md.getAccount().getAppointments());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/AgendaWeek.fxml"));
        loader.setController(this);
        loader.load();

        node = loader.getRoot();
    }

    public void setLabel(LocalDate date){
        lblSlots.setText("Slots for the Week of ");
        lblSlots.setText(lblSlots.getText() + date);
    }

    public void insertAvailableData(ArrayList<ArrayList<Agenda>> data){
        agendaList.getItems().clear();
        ObservableList<String> string = FXCollections.observableArrayList();
        for (int i = 0; i < 7; i++){
            for (int j = 0; j < data.get(i).size(); j++)
                addAvailable(string, data.get(i).get(j));
        }
        agendaList.setItems(string);
    }

    public void addAvailable(ObservableList<String> string, Agenda data){
        String hrS = Integer.toString(data.getStartTime().getHour());
        String minS = Integer.toString(data.getStartTime().getMinute());
        String hrE = "";
        String minE = "";
        hrE = Integer.toString(data.getEndTime().getHour());
        minE = Integer.toString(data.getEndTime().getMinute());
        String sTimeOfDay = "AM";
        String eTimeOfDay = "AM";

        if (data.getStartTime().getHour() < 10)
            hrS = "0" + hrS;
        else if (data.getStartTime().getHour() >= 12) {
            if (data.getStartTime().getHour() != 12)
                hrS = Integer.toString(Integer.parseInt(hrS) - 12);
            sTimeOfDay = "PM";
        }

        if (data.getStartTime().getMinute() < 10)
            minS = "0" + minS;

        if (data.getEndTime().getHour() < 10)
            hrE = "0" + hrE;
        else if (data.getEndTime().getHour() >= 12) {
            if (data.getEndTime().getHour() != 12)
                hrE = Integer.toString(Integer.parseInt(hrE) - 12);
            eTimeOfDay = "PM";
        }

        if (data.getEndTime().getMinute() < 10)
            minE = "0" + minE;

        string.add(data.getStartTime().getMonthValue() + "-" + data.getStartTime().getDayOfMonth() + "-" + data.getStartTime().getYear()
                + " " + hrS + ":" + minS + sTimeOfDay + ", Dr." + ((Unavailable) data).getDoctorName() + ", Open");
    }

    public void insertFilteredData(Model model){
        //still incomplete
        ArrayList<ArrayList<Agenda>> data = new ArrayList<>();
        agendaList.getItems().clear();
        ObservableList<String> string = FXCollections.observableArrayList();
        for (int i = 0; i < 7; i++){
            for (int j = 0; j < data.get(i).size(); j++)
                addString(string, data.get(i).get(j));
        }
        agendaList.setItems(string);
    }

    private void addString(ObservableList<String> string, Agenda data){
        String hrS = Integer.toString(data.getStartTime().getHour());
        String minS = Integer.toString(data.getStartTime().getMinute());
        String hrE = "";
        String minE = "";
        hrE = Integer.toString(data.getEndTime().getHour());
        minE = Integer.toString(data.getEndTime().getMinute());
        String sTimeOfDay = "AM";
        String eTimeOfDay = "AM";

        if (data.getStartTime().getHour() < 10)
            hrS = "0" + hrS;
        else if (data.getStartTime().getHour() >= 12) {
            if (data.getStartTime().getHour() != 12)
                hrS = Integer.toString(Integer.parseInt(hrS) - 12);
            sTimeOfDay = "PM";
        }

        if (data.getStartTime().getMinute() < 10)
            minS = "0" + minS;

        if (data.getEndTime().getHour() < 10)
            hrE = "0" + hrE;
        else if (data.getEndTime().getHour() >= 12) {
            if (data.getEndTime().getHour() != 12)
                hrE = Integer.toString(Integer.parseInt(hrE) - 12);
            eTimeOfDay = "PM";
        }

        if (data.getEndTime().getMinute() < 10)
            minE = "0" + minE;

        if (data instanceof Appointment){
            string.add("ID: " + data.getId() + " " + data.getStartTime().getMonthValue() + "-" + data.getStartTime().getDayOfMonth() + "-" + data.getStartTime().getYear()
                    + " " + hrS + ":" + minS + sTimeOfDay + " - " + hrE + ":" + minE + eTimeOfDay
                    + ", Dr." + ((Appointment) data).getDoctorName() + ", " + ((Appointment) data).getClientName());
        }
    }
    
    public Node getNode() { return node; }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setAppointments(ArrayList<Agenda> appointments) {
        this.appointments = appointments;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Stage getStage() {
        return stage;
    }

    public ArrayList<Agenda> getAppointments() {
        return appointments;
    }

    public Model getModel() {
        return model;
    }
}
