package udc.objects.time.builders;

import udc.objects.time.concrete.Agenda;
import udc.objects.time.concrete.Unavailable;

import java.time.LocalDateTime;

public class UnavailableBuilder implements AgendaBuilder {
    private int id;
    private Unavailable unavailable;

    public UnavailableBuilder (int id) {
        this.id = id;
    }

    @Override
    public Unavailable build(LocalDateTime start, LocalDateTime end) {
        this.setUnavailable(new Unavailable());
        this.unavailable.setId(id);

        this.getUnavailable().setStartTime(start);
        this.getUnavailable().setEndTime(end);

        return this.getUnavailable();
    }

    @Override
    public Unavailable build(int id, LocalDateTime start, LocalDateTime end) {
        this.build(start, end);
        this.unavailable.setId(id);

        this.getUnavailable().setId(id);

        return this.getUnavailable();
    }

    public void setUnavailable(Unavailable unavailable) {
        this.unavailable = unavailable;
    }

    public Unavailable getUnavailable() {
        return unavailable;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
