package udc.objects.time.concrete;

import udc.objects.enums.AgendaType;

import java.time.LocalDateTime;

public class Agenda {
    protected int id;
    protected LocalDateTime startTime;
    protected LocalDateTime endTime;
    protected AgendaType type;

    public AgendaType getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setType(AgendaType type) {
        this.type = type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
}
