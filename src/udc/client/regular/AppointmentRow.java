package udc.client.regular;

import javafx.beans.property.SimpleStringProperty;
import udc.objects.time.concrete.Appointment;

import java.time.format.DateTimeFormatter;

public class AppointmentRow {
    private SimpleStringProperty date;
    private SimpleStringProperty time;
    private SimpleStringProperty doctor;

    public AppointmentRow(Appointment a) {
        this.date = new SimpleStringProperty(a.getStartTime().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")));
        this.time = new SimpleStringProperty(a.getStartTime().format(DateTimeFormatter.ofPattern("hh:mm a")) + " - " +
        a.getEndTime().format(DateTimeFormatter.ofPattern("hh:mm a")));
        this.doctor = new SimpleStringProperty(a.getDoctorName());
    }

    public void setDoctor(String doctor) {
        this.doctor.set(doctor);
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public void setTime(String time) {
        this.time.set(time);
    }

    public String getDate() {
        return date.get();
    }

    public String getDoctor() {
        return doctor.get();
    }

    public String getTime() {
        return time.get();
    }
}
