package udc.doctor.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import udc.doctor.objects.DaySchedule;
import udc.doctor.objects.WeekSchedule;
import udc.objects.time.concrete.Agenda;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class AppointmentsTableDController implements Initializable {

    @FXML
    private TableView<DaySchedule> tbView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initPropertValues();
        insertTime();
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
            TableColumn col = (TableColumn) tbView.getColumns().get(i);
            col.setCellValueFactory(new PropertyValueFactory<WeekSchedule, String>(cells[i]));
        }
    }

    public void insertTime(){
        tbView.getItems().clear();
        int hr = 7;
        for (int i = 0; i < 30; i++) {
            String time = getDispTime(hr, i);
            if (!isOdd(i))
                hr++;
            tbView.getItems().add(new DaySchedule(time, ""));
        }
    }

}
