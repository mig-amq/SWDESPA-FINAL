package udc.client.walkin;

import java.time.LocalDate;

public class WalkInModel {

    String name;
    LocalDate date;
    String start;
    String end;
    String doctor;

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

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }



}
