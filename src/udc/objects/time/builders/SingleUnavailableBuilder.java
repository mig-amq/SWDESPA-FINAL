package udc.objects.time.builders;

import udc.objects.enums.AgendaType;

import java.time.LocalDateTime;

public class SingleUnavailableBuilder extends UnavailableBuilder {

    public void build (LocalDateTime start, LocalDateTime end, String doctor, String client) {
        this.build(start, end);

        this.getUnavailable().setType(AgendaType.SINGLE);
    }

    public void build (int id, LocalDateTime start, LocalDateTime end, String doctor, String client) {
        this.build(id, start, end);

        this.getUnavailable().setType(AgendaType.SINGLE);
    }
}
