package udc.doctor.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import udc.doctor.objects.DaySchedule;
import udc.doctor.objects.WeekSchedule;
import udc.objects.time.concrete.Agenda;
import udc.objects.time.concrete.Appointment;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class AppointmentsTableDController extends SuperController implements Initializable {
    private ArrayList<Agenda> unavilable;
    private ArrayList<Agenda> appointments;

    @FXML
    private TableView<DaySchedule> tbView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initPropertValues();

    }

    public boolean isOdd(int i){
        return i % 2 != 0;
    }

    public String getDispTime(int hr, int i){
        String time;
        String end;
        int temp = 0;
        if (hr > 12)
            temp -= 12;

        if(hr <= 11)
            end = "AM";
        else end = "PM";

        if(isOdd(i)) {//all add :00
            temp += hr;
            time = temp + ":00";
        }else{
            temp +=hr;
            time = temp +":30";
        }

        time += end;
        return time;
    }

    private void initPropertValues(){
        String[] cells = new String[]{"sTime", "sClientDoctor"};
        for (int i = 0; i < tbView.getColumns().size(); i++) {
            TableColumn col = tbView.getColumns().get(i);
            col.setCellValueFactory(new PropertyValueFactory<udc.secretary.Controller.WeekSchedule, String>(cells[i]));
        }
    }

    public void insertFilteredData(ArrayList<Agenda> data){//ArrayList<Appointment> data
//        data = sortTime(data);
        //TODO: ADD UNAVAILABILITY DISPLAY, PLACE IT INSIDE findData method()
        appointments = model.getAccount().getAppointments();//is this where the unavailable shceds are??
//        unavilable = model.getAccount().getUnavailable();
        //if(time form appointments is the same as the in the column)
        //then print unavailable and change color

    }

    @Override
    public void update(LocalDateTime ldt) {
        //call function to display unavailability here?
    }
}
