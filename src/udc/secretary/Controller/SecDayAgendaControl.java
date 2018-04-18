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
import udc.Model;
import udc.objects.time.concrete.Agenda;
import udc.objects.time.concrete.Appointment;
import udc.objects.time.concrete.Available;
import udc.objects.time.concrete.Unavailable;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class SecDayAgendaControl extends AbstractControl {
    private Node ndSecDayAgendaViewNode;
    private Label lblSlots;
    private JFXListView agendaList;
    private JFXButton btnRemove;

    public SecDayAgendaControl(Model model){
        ndSecDayAgendaViewNode = loadSecDayAgendaView();
        initComponents();
        agendaList.setItems(FXCollections.observableArrayList(""));
        btnRemove.setOnAction(event -> {
            String[] contents = agendaList.getSelectionModel().getSelectedItem().toString().split(" ");
            //remove in database, then call model.setState()
            if (!contents[contents.length - 1].equals("Unavailable")) {
                Appointment a = new Appointment();
                a.setId(Integer.parseInt(contents[0].trim()));
                model.getDbController().deleteAppointment(a);
                model.setState();
            } 
        });
    }

    public void setRemoveButtonDisabled(boolean cond){
        btnRemove.setDisable(cond);
    }

    private Node loadSecDayAgendaView(){
        Node node = null;
        try{
            node = FXMLLoader.load(getClass().getResource("../FXMLFiles/SecDayAgendaView.fxml"));
        }catch (Exception e){
            e.printStackTrace();
        }
        return node;
    }

    private void initComponents(){
        Node n = ndSecDayAgendaViewNode.lookup("#lblSlots");
        lblSlots = (Label) n;
        n = ((AnchorPane) ndSecDayAgendaViewNode).lookup("#agendaScroll");
        agendaList = (JFXListView) ((AnchorPane) ((ScrollPane) n).getContent().lookup("#anchorAgenda")).getChildren().get(0);
        btnRemove = (JFXButton) ((AnchorPane) ((ScrollPane) n).getContent().lookup("#anchorAgenda")).getChildren().get(1);
    }

    public Node getNdSecDayAgendaViewNode(){
        return ndSecDayAgendaViewNode;
    }

    public void setLabel(LocalDate date){
        lblSlots.setText("Slots for ");
        lblSlots.setText(lblSlots.getText() + date);
    }

    public void insertFilteredData(ArrayList<Agenda> data) {
        ObservableList<String> string = FXCollections.observableArrayList();
        for (int i = 0; i < data.size(); i++)
            addString(string, data.get(i));
        agendaList.setItems(FXCollections.observableArrayList(string));
    }

    public void reset(){
        agendaList.setItems(FXCollections.observableArrayList(""));
    }

    private void addString(ObservableList<String> string, Agenda data){
        String hrS = Integer.toString(data.getStartTime().getHour());
        String minS = Integer.toString(data.getStartTime().getMinute());
        String hrE = "";
        String minE = "";
        if (!(data instanceof Available)) {
            hrE = Integer.toString(data.getEndTime().getHour());
            minE = Integer.toString(data.getEndTime().getMinute());
        }
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

        if (!(data instanceof Available)) {
            if (data.getEndTime().getHour() < 10)
                hrE = "0" + hrE;
            else if (data.getEndTime().getHour() >= 12) {
                if (data.getEndTime().getHour() != 12)
                    hrE = Integer.toString(Integer.parseInt(hrE) - 12);
                eTimeOfDay = "PM";
            }

            if (data.getEndTime().getMinute() < 10)
                minE = "0" + minE;
        }

        if (data instanceof Appointment){
            string.add(data.getId() + " " + ": " + hrS + ":" + minS + sTimeOfDay + " - " + hrE + ":" + minE + eTimeOfDay
                    + ", Dr." + ((Appointment) data).getDoctorName() + ", " + ((Appointment) data).getClientName());
        }
        else if (data instanceof Unavailable){
            string.add(hrS + ":" + minS + sTimeOfDay + " - " + hrE + ":" + minE + eTimeOfDay
                    + ", Dr." + ((Unavailable) data).getDoctorName() + ", Unavailable");
        } else if (data instanceof Available){
            string.add(hrS + ":" + minS + sTimeOfDay + ", Dr." + ((Available) data).getDoctorName() + ", Open");
        }
    }
}
