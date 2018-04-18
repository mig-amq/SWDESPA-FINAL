package udc.doctor.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import udc.Model;
import udc.objects.time.concrete.Agenda;
import udc.objects.time.concrete.Appointment;
import udc.objects.time.concrete.Available;
import udc.objects.time.concrete.Unavailable;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AgendaViewController extends SuperController implements Initializable {

    @FXML
    private JFXListView agendaList;

    @FXML
    private JFXButton btnRemove;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        remove();
    }

    @Override
    public void update(LocalDate ldt) {
        reset();
        try {
            agendas = new ArrayList<>();
            Unavailability = model.getDbController().getUnvailability(model.getAccount().getId());
            insertUnavailabilitytoAgendas();
        } catch (Exception e) {
            e.printStackTrace();
        }
        insertFilteredData(agendas);
    }

    @Override
    public void setModel(Model model){
        this.model = model;
        update(calendar.getSelected());
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

    public void remove(){
        btnRemove.setOnAction(event -> {
            String[] contents = agendaList.getSelectionModel().getSelectedItem().toString().split(" ");
            //remove in database, then call model.setState()
            Unavailable a = new Unavailable();
            a.setId(model.getAccount().getId());

            if (agendaList.getSelectionModel().getSelectedItems().size() == 1) {
                String time = calendar.getDate().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")) + " " + agendaList.getSelectionModel().getSelectedItem().toString().substring(0,8);
                a.setStartTime(Agenda.strToTime(time));

                agendaList.getItems().remove(agendaList.getSelectionModel().getSelectedIndex());
                model.getDbController().deleteUnavailability(model.getAccount().getId(), a);
                model.setState();
            }
        });
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
            string.add(data.getStartTime().format(DateTimeFormatter.ofPattern("hh")) + ":" + minS + " " + sTimeOfDay + " - " + hrE + ":" + minE + eTimeOfDay
                    + ", Dr." + ((Appointment) data).getDoctorName() + ", " + ((Appointment) data).getClientName());
        }
        else if (data instanceof Unavailable){
            string.add(data.getStartTime().format(DateTimeFormatter.ofPattern("hh")) + ":" + minS + " " + sTimeOfDay + " - " + hrE + ":" + minE + eTimeOfDay+ ", Unavailable");
        } else {
            string.add(hrS + ":" + minS + sTimeOfDay + ", Dr." + ((Available) data).getDoctorName() + ", Open");
        }
    }
}
