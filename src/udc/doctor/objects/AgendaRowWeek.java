package udc.doctor.objects;

import javafx.scene.paint.Color;
import udc.objects.time.concrete.Agenda;

public class AgendaRowWeek extends AgendaRow {
    private AgendaRow monday, tuesday, wednesday, thursday, friday, saturday, sunday;

    public AgendaRowWeek(Agenda data, Color c) {
        super(data, c);
    }

    public AgendaRowWeek(String time, String description, Color c) {
        super(time, description, c);
    }
}
