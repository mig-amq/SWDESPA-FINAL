package udc.objects.time.builders;

import udc.objects.time.concrete.Agenda;
import udc.objects.time.concrete.Appointment;

import java.time.LocalDateTime;

public class AppointmentBuilder implements AgendaBuilder {
    protected Appointment appointment;

    @Override
    public Agenda build(LocalDateTime start, LocalDateTime end) {
        this.setAppointment(new Appointment());

        this.getAppointment().setStartTime(start);
        this.getAppointment().setEndTime(end);

        return this.getAppointment();
    }

    @Override
    public Agenda build(int id, LocalDateTime start, LocalDateTime end) {
        this.build(start, end);

        this.getAppointment().setId(id);

        return this.getAppointment();
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public Appointment getAppointment() {
        return appointment;
    }
}
