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
        cmbType.getItems().addAll("Single", "Recurring");
        cmbSMin.getItems().addAll("00", "30");
        cmbEMin.getItems().addAll("00", "30");
    }


    public void setBtnActions(){


        btnSetUnavailable.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                /*** INSERT ACTIONS HERE ***/
                String hour, min, hours, mins;
                int startTime, endTime;
                hour = cmbSHour.getSelectionModel().getSelectedItem().toString();
                min = cmbSMin.getSelectionModel().getSelectedItem().toString();
                hours = cmbEHour.getSelectionModel().getSelectedItem().toString();
                mins = cmbEMin.getSelectionModel().getSelectedItem().toString();
                //System.out.println(hour+min);
                //System.out.println(hours+mins);
                startTime = Integer.parseInt(hour+min);
                endTime = Integer.parseInt(hours+mins);
                System.out.println(startTime < endTime);
                if(startTime < endTime){
                    LocalDateTime date =
                            LocalDateTime.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis()), ZoneId.systemDefault());


                    // ADJUST THESE
                    if((date.getHour() < startTime/100) ||
                            (date.getMinute() == startTime %100 && date.getHour() < startTime/100)){
                        boolean isConflict = false;
                        LocalTime timeStart, timeEnd;
                        LocalDateTime start, end;;
                        timeStart = LocalTime.of(startTime/100, startTime%100);
                        start = LocalDateTime.of(calendar.getSelected(), timeStart);
                        timeEnd = LocalTime.of(endTime/100, endTime%100);
                        end = LocalDateTime.of(calendar.getSelected(), timeEnd);

                        ArrayList<Agenda> appointments = model.getAccount().getAppointments();
                        for(int i = 0; i < appointments.size(); i++){
                            if(appointments.get(i).getStartTime().isEqual(start) || appointments.get(i).getEndTime().isEqual(end)||
                                    appointments.get(i).getStartTime().isEqual(end) || appointments.get(i).getEndTime().isEqual(start))
                                isConflict = true;
                        }

                        if(isConflict){
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setContentText("There is already an appointment shceduled at that time");
                        }else{
                            if(cmbType.getSelectionModel().getSelectedItem().toString().equals("Single")){
                                shceduler = new Scheduler(start, end);
                                shceduler.setBuiilder(new SingleUnavailableBuilder(model.getAccount().getId()));
                                shceduler.buildUnavailability();
                            }

                            else if(cmbType.getSelectionModel().getSelectedItem().toString().equals("Recurring")){
                                shceduler = new Scheduler(start, end);
                                shceduler.setBuiilder(new RecurringUnavailableBuilder(model.getAccount().getId()));
                                shceduler.buildUnavailability();
                            }

                        }

                        //model.getList then compare times to check for conflict
//                        if(model.getList.getStartTime != startTime && model.getList.getEndTime != endTime){

//                      }else{//shows dialogue box for conflict of time
//
//                      }
                    }else{
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setContentText("Invalid time selected");
                    }
                }else {
                    //shows dialogue box for invalid input time
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("There is already an appointment shceduled there");
                }
            }
        });
    }

    @Override
    public void update(LocalDateTime ldt) {

    }
}
