package udc.objects.time.builders;

import udc.objects.time.concrete.Agenda;
import udc.objects.time.concrete.Available;

import java.time.LocalDateTime;

public class AvaibilityBuilder implements AgendaBuilder {
    private int id;
    private Available available;

    public AvaibilityBuilder(){
        available = new Available();
    }

    @Override
    public Agenda build(LocalDateTime start, LocalDateTime end) {
        this.setAvailable(new Available());
        this.getAvailable().setId(id);
        this.getAvailable().setStartTime(start);
        this.getAvailable().setEndTime(end);
        return this.getAvailable();
    }

    @Override
    public Agenda build(int id, LocalDateTime start, LocalDateTime end) {
        this.setAvailable(new Available());
        this.getAvailable().setId(id);
        this.getAvailable().setStartTime(start);
        this.getAvailable().setEndTime(end);

        return null;
    }

    public void setAvailable(Available available) {
        this.available = available;
    }

    public Available getAvailable() {
        return available;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
