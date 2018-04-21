package udc.doctor.objects;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.paint.Color;
import udc.objects.time.concrete.Agenda;

import java.time.format.DateTimeFormatter;

public class AgendaRow {
    private Color color;
    private Agenda data;
    private SimpleStringProperty description, time;

    public AgendaRow(Agenda data, Color c) {
        this.setData(data);
        this.setColor(c);
        this.setTime(data.getStartTime().format(DateTimeFormatter.ofPattern("hh:mm a")));
        this.setDescription("");
    }

    public AgendaRow (String time, String description, Color c) {
        this.setColor(c);
        this.setTime(time);
        this.setDescription(description);
    }


    public Color getColor() {
        return color;
    }

    public String getDescription() {
        return description.get();
    }

    public String getTime() {
        return time.get();
    }

    public Agenda getData() {
        return data;
    }

    public void setData(Agenda data) {
        this.data = data;
    }

    public void setTime(String time) {
        this.time = new SimpleStringProperty(time);
    }

    public void setDescription(String description) {
        this.description = new SimpleStringProperty(description);
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
