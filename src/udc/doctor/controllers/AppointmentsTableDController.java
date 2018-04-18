package udc.doctor.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import udc.Model;
import udc.objects.time.concrete.Agenda;
import udc.objects.time.concrete.Appointment;
import udc.objects.time.concrete.Unavailable;
import udc.secretary.Controller.DaySchedule;
import udc.secretary.Controller.WeekSchedule;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class AppointmentsTableDController extends SuperController implements Initializable {

    @FXML
    private TableColumn<DaySchedule, String> colDoctors;

    @FXML
    private TableView<DaySchedule> tbView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

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
            end = " AM";
        else end = " PM";

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



    public void insertFilteredData(ArrayList<Agenda> data){//ArrayList<Appointment> data
//        data = sortTime(data);
        //TODO: ADD UNAVAILABILITY DISPLAY, PLACE IT INSIDE findData method()
        System.out.println(data);
        tbView.getItems().clear();
        int hr = 7;
        for (int i = 0; i < 30; i++) {
            int index;

            String time = getDispTime(hr, i);
            if(!isOdd(i))
                hr++;
            String index1 = getUnavailabilityFromList(data, time);
            if((index = getDataIndexfromList(data, time)) >= 0 ) {
                Appointment agenda = (Appointment) data.get(index);
                tbView.getItems().add(new DaySchedule(time, "Dr. " + agenda.getDoctorName() + "\nClient: " + agenda.getClientName()));
            }else if(!index1.equals("")){
                String[] a = index1.split(" | ");
                if(a.length == 2) {
                    tbView.getItems().add(new DaySchedule(time, "(Unavailable)"));
                } else if(a.length == 1){
                    Unavailable agenda = (Unavailable) data.get(Integer.parseInt(a[a.length -1]));
                    tbView.getItems().add(new DaySchedule(time, "Dr. " + agenda.getDoctorName() + " - " + "Unavailable"));
                }else {
                    tbView.getItems().add(new DaySchedule(time, ""));
                }
            }
            else
                tbView.getItems().add(new DaySchedule(time, ""));
        }
        setColumnCellFactory(colDoctors);
    }

    private void initPropertValues(){
        String[] cells = new String[]{"sTime", "sClientDoctor"};
        for (int i = 0; i < tbView.getColumns().size(); i++) {
            TableColumn col = tbView.getColumns().get(i);
            col.setCellValueFactory(new PropertyValueFactory<WeekSchedule, String>(cells[i]));
        }
    }

    public double add(LocalTime timeStart, LocalTime timeEnd) {
        int x, y, a, b;
        double difference;

        x = timeStart.getHour();
        y = timeStart.getMinute();
        a = timeEnd.getHour();
        b = timeEnd.getMinute();
        if(b-y < 0) {
            difference = a-x;
            difference-=0.5;
        } else if(b-y > 0) {
            difference = a-x;
            difference+=0.5;
        } else {
            difference = a-x;
        }
        System.out.println(difference);
        return difference;
    }
    @Override
    public void setModel(Model model){
        this.model = model;
        update(calendar.getSelected());
    }

    @Override
    public void update(LocalDate ldt) {
        //call function to display unavailability here?
        initPropertValues();
        agendas = model.getAccount().getAppointments();
        try {
            Unavailability = model.getDbController().getUnvailability(model.getAccount().getId());
            insertUnavailabilitytoAgendas();
//            System.out.println(agendas);
        } catch (Exception e) {
            e.printStackTrace();
        }

        insertFilteredData(findData(calendar.selectedProperty().get()));
        System.out.println(findData(calendar.selectedProperty().get()));
    }
}
