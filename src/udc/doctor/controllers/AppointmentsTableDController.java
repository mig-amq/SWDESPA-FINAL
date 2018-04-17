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
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class AppointmentsTableDController extends SuperController implements Initializable {
    private ArrayList<Agenda> unavilable;
    private ArrayList<Agenda> appointments;

    @FXML
    private TableColumn colDoctors;

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



    public void insertFilteredData(){//ArrayList<Appointment> data
//        data = sortTime(data);
        //TODO: ADD UNAVAILABILITY DISPLAY, PLACE IT INSIDE findData method()
        appointments = model.getAccount().getAppointments();//is this where the unavailable shceds are??
        int month, day, year, thisDay, thisMonth, thisYear;
        try {
            unavilable = model.getDbController().getUnvailability(model.getAccount().getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        for(int i = 0; i < appointments.size(); i++){
            day = appointments.get(i).getStartTime().getDayOfMonth();
            month = appointments.get(i).getStartTime().getMonthValue();
            year = appointments.get(i).getStartTime().getYear();
            thisDay = calendar.getSelected().getDayOfMonth();
            thisMonth = calendar.getSelected().getMonthValue();
            thisYear = calendar.getSelected().getYear();
            if(appointments.get(i).getType().equals("Recurring")){
                DateTimeFormatter dtf = new DateTimeFormatterBuilder().appendPattern("h:mm a").toFormatter();
                LocalTime time, tempStart, tempEnd;
                int startInt, endInt;
                double startDif, endDif;
                time = LocalTime.parse("7:30 AM", dtf);
                tempStart = appointments.get(i).getStartTime().toLocalTime();
                tempEnd = appointments.get(i).getEndTime().toLocalTime();
                startDif = add(time, tempStart);
                endDif = add(time, tempEnd);
                startInt = (int) startDif * 2;
                endInt = (int) endDif * 2;
                for(int a = startInt; a < endInt; a++){
                    colDoctors.getColumns().set(a, "Appointment");
                }

            } else if(appointments.get(i).getType().equals("Single")){
                if(day == thisDay && month == thisMonth && year == thisYear){
                    DateTimeFormatter dtf = new DateTimeFormatterBuilder().appendPattern("h:mm a").toFormatter();
                    LocalTime time, tempStart, tempEnd;
                    int startInt, endInt;
                    double startDif, endDif;
                    time = LocalTime.parse("7:30 AM", dtf);
                    tempStart = appointments.get(i).getStartTime().toLocalTime();
                    tempEnd = appointments.get(i).getEndTime().toLocalTime();
                    startDif = add(time, tempStart);
                    endDif = add(time, tempEnd);
                    startInt = (int) startDif * 2;
                    endInt = (int) endDif * 2;
                    for(int a = startInt; a < endInt; a++){
                        colDoctors.getColumns().set(a, "Appointment");
                    }
                }
            }
        }

//        for(int i = 0; i < unavilable.size(); i++){
//            day = unavilable.get(i).getStartTime().getDayOfMonth();
//            month = unavilable.get(i).getStartTime().getMonthValue();
//            year = unavilable.get(i).getStartTime().getYear();
//            thisDay = calendar.getSelected().getDayOfMonth();
//            thisMonth = calendar.getSelected().getMonthValue();
//            thisYear = calendar.getSelected().getYear();
//            if(unavilable.get(i).getType().equals("Recurring")){
//
//            } else if(unavilable.get(i).getType().equals("Single")){
//                if(day == thisDay && month == thisMonth && year == thisYear){
//
//                }
//            }
//        }

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
    public void update(LocalDateTime ldt) {
        //call function to display unavailability here?
        insertFilteredData();
    }
}
