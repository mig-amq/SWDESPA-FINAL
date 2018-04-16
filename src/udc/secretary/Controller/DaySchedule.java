package udc.secretary.Controller;

import javafx.beans.property.SimpleStringProperty;
import udc.objects.time.concrete.Appointment;

import java.time.format.DateTimeFormatter;

public class DaySchedule {
    private SimpleStringProperty sTime;
    private SimpleStringProperty sClientDoctor;

    public DaySchedule(String sTime, String sDoctor){
        this.sTime = new SimpleStringProperty(sTime);
        this.sClientDoctor = new SimpleStringProperty(sDoctor);
    }

    public DaySchedule(Appointment a) {
        this.sTime = new SimpleStringProperty(a.getStartTime().format(DateTimeFormatter.ofPattern("hh:mm a")));
        this.sClientDoctor = new SimpleStringProperty(a.getClientName());
    }

    public String getsTime() {
        return sTime.get();
    }

    public SimpleStringProperty sTimeProperty() {
        return sTime;
    }

    public void setsTime(String sTime) {
        this.sTime.set(sTime);
    }

    public String getsClientDoctor() {
        return sClientDoctor.get();
    }

    public SimpleStringProperty sClientDoctorProperty() {
        return sClientDoctor;
    }

    public void setsDoctor(String sDoctor) {
        this.sClientDoctor.set(sDoctor);
    }
}
