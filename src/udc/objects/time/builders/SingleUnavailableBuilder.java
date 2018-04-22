package udc.objects.time.builders;

import udc.objects.enums.AgendaType;
import udc.objects.time.concrete.Unavailable;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class SingleUnavailableBuilder extends UnavailableBuilder {

    public SingleUnavailableBuilder(int id) {
        super(id);
    }

    public Unavailable build (LocalDateTime start, LocalDateTime end) {
        super.build(start, end);
        this.getUnavailable().setType(AgendaType.SINGLE);

        return this.getUnavailable();
    }

    public Unavailable build (int id, LocalDateTime start, LocalDateTime end) {
        super.build(id, start, end);

        this.getUnavailable().setType(AgendaType.SINGLE);

        return this.getUnavailable();
    }

    public ArrayList<Unavailable> buildMultiple (int id, LocalDateTime start, LocalDateTime end, String doctor, String client) {
        ArrayList<Unavailable> list = new ArrayList<>();
        LocalDateTime temp = start;

        while (temp.isBefore(end)){
            this.build(id, temp, temp.plusMinutes(30));
            this.getUnavailable().setType(AgendaType.SINGLE);
            temp = temp.plusMinutes(30);
            list.add(this.getUnavailable());
        }
        return list;
    }
}
