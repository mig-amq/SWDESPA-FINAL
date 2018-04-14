package udc.objects.time.builders;

import udc.objects.time.concrete.Unavailable;

import java.time.LocalDateTime;

public class UnavailableBuilder implements AgendaBuilder {
    private Unavailable unavailable;

    @Override
    public void build(LocalDateTime start, LocalDateTime end) {
        this.setUnavailable(new Unavailable());

        this.getUnavailable().setStartTime(start);
        this.getUnavailable().setEndTime(end);
    }

    @Override
    public void build(int id, LocalDateTime start, LocalDateTime end) {
        this.build(start, end);

        this.getUnavailable().setId(id);
    }

    public void setUnavailable(Unavailable unavailable) {
        this.unavailable = unavailable;
    }

    public Unavailable getUnavailable() {
        return unavailable;
    }
}
