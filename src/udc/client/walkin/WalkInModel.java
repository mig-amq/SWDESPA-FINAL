package udc.client.walkin;

import java.time.LocalDate;

public class WalkInModel {

    String name;
    LocalDate date;
    String timeStart;
    String timeEnd;
    String doctor;

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }


    public String getName() {
        return name;
    }

    public LocalDate setName(String name) {
        this.name = name;
        return null;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }



}
