package udc.doctor.objects;

import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDateTime;

public class WeekSchedule {
    private SimpleStringProperty sTime;
    private SimpleStringProperty sSun;
    private SimpleStringProperty sMon;
    private SimpleStringProperty sTue;
    private SimpleStringProperty sWed;
    private SimpleStringProperty sThu;
    private SimpleStringProperty sFri;
    private SimpleStringProperty sSat;

    public WeekSchedule(String sTime, String sMon, String sTue, String sWed, String sThu, String sFri, String sSat,  String sSun) {
        this.sTime = new SimpleStringProperty(sTime);
        this.sSun = new SimpleStringProperty(sSun);
        this.sMon = new SimpleStringProperty(sMon);
        this.sTue = new SimpleStringProperty(sTue);
        this.sWed = new SimpleStringProperty(sWed);
        this.sThu = new SimpleStringProperty(sThu);
        this.sFri = new SimpleStringProperty(sFri);
        this.sSat = new SimpleStringProperty(sSat);
    }

    public String getsTime() {
        return sTime.get();
    }

    public SimpleStringProperty sTimeProperty() {
        return sTime;
    }

    public String getsSun() {
        return sSun.get();
    }

    public SimpleStringProperty sSunProperty() {
        return sSun;
    }

    public String getsMon() {
        return sMon.get();
    }

    public SimpleStringProperty sMonProperty() {
        return sMon;
    }

    public String getsTue() {
        return sTue.get();
    }

    public SimpleStringProperty sTueProperty() {
        return sTue;
    }

    public String getsWed() {
        return sWed.get();
    }

    public SimpleStringProperty sWedProperty() {
        return sWed;
    }

    public String getsThu() {
        return sThu.get();
    }

    public SimpleStringProperty sThuProperty() {
        return sThu;
    }

    public String getsFri() {
        return sFri.get();
    }

    public SimpleStringProperty sFriProperty() {
        return sFri;
    }

    public String getsSat() {
        return sSat.get();
    }

    public SimpleStringProperty sSatProperty() {
        return sSat;
    }
}
