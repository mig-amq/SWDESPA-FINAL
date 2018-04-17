package udc.client.regular.Controller;

import javafx.beans.property.SimpleStringProperty;

public class WeekSchedule {
    private SimpleStringProperty time;
    private SimpleStringProperty mon;
    private SimpleStringProperty tue;
    private SimpleStringProperty wed;
    private SimpleStringProperty thu;
    private SimpleStringProperty fri;
    private SimpleStringProperty sat;
    private SimpleStringProperty sun;

    public WeekSchedule(String time, String mon, String tue, String wed, String thu, String fri, String sat, String sun) {
        this.time = new SimpleStringProperty(time);
        this.mon = new SimpleStringProperty(mon);
        this.tue = new SimpleStringProperty(tue);
        this.wed = new SimpleStringProperty(wed);
        this.thu = new SimpleStringProperty(thu);
        this.fri = new SimpleStringProperty(fri);
        this.sat = new SimpleStringProperty(sat);
        this.sun = new SimpleStringProperty(sun);
    }

    public String getTime() { return time.get(); }
    public String getMon() { return mon.get(); }
    public String getTue() { return tue.get(); }
    public String getWed() { return wed.get(); }
    public String getThu() { return thu.get(); }
    public String getFri() { return fri.get(); }
    public String getSat() { return sat.get(); }
    public String getSun() { return sun.get(); }
    public SimpleStringProperty timeProperty() { return time; }
    public SimpleStringProperty monProperty() { return mon; }
    public SimpleStringProperty tueProperty() { return tue; }
    public SimpleStringProperty wedProperty() { return wed; }
    public SimpleStringProperty thuProperty() { return thu; }
    public SimpleStringProperty friProperty() { return fri; }
    public SimpleStringProperty satProperty() { return sat; }
    public SimpleStringProperty sunProperty() { return sun; }
    public void setTime(String time) { this.time.set(time); }
    public void setMon(String mon) { this.mon.set(mon); }
    public void setTue(String tue) { this.tue.set(tue); }
    public void setWed(String wed) { this.wed.set(wed); }
    public void setThu(String thu) { this.thu.set(thu); }
    public void setFri(String fri) { this.fri.set(fri); }
    public void setSat(String sat) { this.sat.set(sat); }
    public void setSun(String sun) { this.sun.set(sun); }
}
