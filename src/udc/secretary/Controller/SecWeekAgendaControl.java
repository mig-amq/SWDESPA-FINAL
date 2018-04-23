package udc.secretary.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import udc.objects.time.concrete.Agenda;
import udc.objects.time.concrete.Appointment;
import udc.objects.time.concrete.Available;
import udc.objects.time.concrete.Unavailable;

import java.time.LocalDate;
import java.util.ArrayList;

public class SecWeekAgendaControl extends AbstractControl{
    private Node ndSecWeekAgendaViewNode;
    private Label lblSlots;
    private JFXListView weekAgendaList;

    public SecWeekAgendaControl(){
        ndSecWeekAgendaViewNode = loadSecWeekAgendaViewNode();
        initComponents();
        weekAgendaList.setItems(FXCollections.observableArrayList(""));
    }

    private Node loadSecWeekAgendaViewNode(){
        Node node = null;
        try{
            node = FXMLLoader.load(getClass().getResource("../FXMLFiles/SecWeekAgendaView.fxml"));
        } catch (Exception e){
            e.printStackTrace();
        }

        return node;
    }

    private void initComponents(){
        Node n = ndSecWeekAgendaViewNode.lookup("#lblSlots");
        lblSlots = (Label) n;
        n = ((AnchorPane) ndSecWeekAgendaViewNode).lookup("#agendaScroll");
        weekAgendaList = (JFXListView) ((AnchorPane) ((ScrollPane) n).getContent().lookup("#anchorAgenda")).getChildren().get(0);
    }

    public Node getNdSecWeekAgendaViewNode() {
        return ndSecWeekAgendaViewNode;
    }

    public void setLabel(LocalDate date){
        lblSlots.setText("Slots for the Week of ");
        lblSlots.setText(lblSlots.getText() + date);
    }

    public void insertAvailableData(ArrayList<ArrayList<Agenda>> data){
        weekAgendaList.getItems().clear();
        ObservableList<String> string = FXCollections.observableArrayList();
        for (int i = 0; i < 7; i++){
            for (int j = 0; j < data.get(i).size(); j++)
                addAvailable(string, data.get(i).get(j));
        }
        weekAgendaList.setItems(string);
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

    public void insertFilteredData(ArrayList<ArrayList<Agenda>> data){
        //still incomplete
        weekAgendaList.getItems().clear();
        ObservableList<String> string = FXCollections.observableArrayList();
        for (int i = 0; i < 7; i++){
            for (int j = 0; j < data.get(i).size(); j++)
                addString(string, data.get(i).get(j));
        }
        weekAgendaList.setItems(string);
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
}
