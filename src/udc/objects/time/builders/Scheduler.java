package udc.objects.time.builders;

import udc.objects.time.concrete.Appointment;
import udc.objects.time.concrete.Unavailable;

import java.time.LocalDateTime;
import java.util.ArrayList;
public class Scheduler {
    private AgendaBuilder builder;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public Scheduler(LocalDateTime startTime, LocalDateTime endTime){
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public void setBuiilder(AgendaBuilder buiilder){
        this.builder = buiilder;
    }

    public Unavailable buildUnavailability () {
        if (this.builder instanceof UnavailableBuilder)
            return (Unavailable) this.builder.build(startTime, endTime);
        else
            return null;
    }

    public Appointment buildAppointment () {
        if (this.builder instanceof AppointmentBuilder)
            return (Appointment) this.builder.build(startTime, endTime);
        else
            return null;
    }

}
