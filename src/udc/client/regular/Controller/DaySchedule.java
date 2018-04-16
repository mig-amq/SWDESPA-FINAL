package udc.client.regular.Controller;

import javafx.beans.property.SimpleStringProperty;
import udc.objects.time.concrete.Appointment;

import java.time.format.DateTimeFormatter;

public class DaySchedule {
    private SimpleStringProperty time;
    private SimpleStringProperty doctor;

    public DaySchedule(String time, String doctor) {
        this.time = new SimpleStringProperty(time);
        this.doctor = new SimpleStringProperty(doctor);
    }

    public DaySchedule(Appointment a) {
        time = new SimpleStringProperty(a.getStartTime().format(DateTimeFormatter.ofPattern("hh:mm a")));
        doctor = new SimpleStringProperty(a.getDoctorName());
    }

    public String getTime() { return time.get(); }
    public String getDoctor() { return doctor.get(); }
    public SimpleStringProperty timeProperty() { return time; }
    public SimpleStringProperty doctorProperty() { return doctor; }
    public void setTime(String time) { this.time.set(time); }
    public void setDoctor(String doctor) { this.doctor.set(doctor); }
}
