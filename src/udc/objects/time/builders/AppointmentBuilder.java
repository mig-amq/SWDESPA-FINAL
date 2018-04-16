package udc.objects.time.builders;

import udc.objects.time.concrete.Agenda;
import udc.objects.time.concrete.Appointment;

import java.time.LocalDateTime;

public class AppointmentBuilder implements AgendaBuilder {
    private String doctor, client;
    protected Appointment appointment;

    public AppointmentBuilder (String doctor, String client) {
        this.doctor = doctor;
        this.client = client;
    }

    @Override
    public Agenda build(LocalDateTime start, LocalDateTime end) {
        this.setAppointment(new Appointment());
        this.getAppointment().setClientName(this.client);
        this.getAppointment().setDoctorName(this.doctor);

        this.getAppointment().setStartTime(start);
        this.getAppointment().setEndTime(end);

        return this.getAppointment();
    }

    @Override
    public Agenda build(int id, LocalDateTime start, LocalDateTime end) {
        this.build(start, end);
        this.getAppointment().setClientName(this.client);
        this.getAppointment().setDoctorName(this.doctor);

        this.getAppointment().setId(id);

        return this.getAppointment();
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getClient() {
        return client;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getDoctor() {
        return doctor;
    }
}
