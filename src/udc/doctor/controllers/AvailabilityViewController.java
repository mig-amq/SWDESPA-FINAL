package udc.doctor.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import udc.objects.time.builders.*;
import udc.objects.time.concrete.Agenda;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AvailabilityViewController extends SuperController implements Initializable {
    private Scheduler shceduler;

    @FXML
    private JFXComboBox cmbSHour, cmbSMin, cmbEHour, cmbEMin, cmbType;

    @FXML
    private JFXButton  btnSetUnavailable;

    @FXML
    private JFXComboBox timeType;

    @FXML
    private JFXComboBox timeType2;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setData();
        setBtnActions();
    }

    public void setData(){
        for(int i = 0; i < 10; i++){
            String hour = Integer.toString(i+1);
            cmbSHour.getItems().add(hour);
            cmbEHour.getItems().add(hour);
        }
        timeType.getItems().addAll("AM", "PM");
        timeType2.getItems().addAll("AM", "PM");
        cmbType.getItems().addAll("Single", "Recurring");
        cmbType.getSelectionModel().selectFirst();
        cmbSMin.getItems().addAll("00", "30");
        cmbEMin.getItems().addAll("00", "30");
    }


    public void setBtnActions(){


        btnSetUnavailable.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                /*** INSERT ACTIONS HERE ***/
                System.out.println(getCalendar().getDate());
                /*String hour, min, hours, mins, type, typeTime, typeTime2,startString, endString;
                int startTime, endTime;
                hour = cmbSHour.getSelectionModel().getSelectedItem().toString();
                min = cmbSMin.getSelectionModel().getSelectedItem().toString();
                hours = cmbEHour.getSelectionModel().getSelectedItem().toString();
                mins = cmbEMin.getSelectionModel().getSelectedItem().toString();
                typeTime = timeType.getSelectionModel().getSelectedItem().toString();
                typeTime2 = timeType2.getSelectionModel().getSelectedItem().toString();
                type = cmbType.getSelectionModel().getSelectedItem().toString();
                //System.out.println(hour+min);
                //System.out.println(hours+mins);
                startString = hour+":"+min+" "+typeTime;
                endString = hours+":"+mins+" "+typeTime2;
                startTime = Integer.parseInt(hour+min);
                endTime = Integer.parseInt(hours+mins);
                System.out.println(startTime < endTime);
                if(startTime < endTime){
                    LocalDateTime date =
                            LocalDateTime.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis()), ZoneId.systemDefault());
                    DateTimeFormatter dtf = new DateTimeFormatterBuilder().appendPattern("h:mm a").toFormatter();
                    LocalTime timeStart, timeEnd;
                    LocalDateTime start, end;
                    start = LocalDateTime.of(calendar.getSelected(), timeStart);
                    end = LocalDateTime.of(calendar.getSelected(), timeEnd);
                    timeStart = LocalTime.parse(startString, dtf);
                    timeEnd = LocalTime.parse(endString, dtf);
                    // ADJUST THESE
                    if(){



                    }else{
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setContentText("Invalid time selected");
                    }
                }else {
                    //shows dialogue box for invalid input time
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("There is already an appointment shceduled there");
                }*/
            }
        });
    }

    @Override
    public void update(LocalDateTime ldt) {
        //model.getAccount.setUnavailability(list of unavailable times);
    }
}
