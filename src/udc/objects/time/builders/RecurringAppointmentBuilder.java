package udc.objects.time.builders;

import udc.objects.enums.AgendaType;
import udc.objects.time.concrete.Appointment;

import java.time.LocalDateTime;

public class RecurringAppointmentBuilder extends AppointmentBuilder {

    public RecurringAppointmentBuilder(String doctor, String client) {
        super(doctor, client);
    }

    public Appointment build (LocalDateTime start, LocalDateTime end, String doctor, String client) {
        this.build(start, end);
        this.getAppointment().setDoctorName(doctor);
        this.getAppointment().setClientName(client);

        this.getAppointment().setType(AgendaType.RECURRING);

        return this.getAppointment();
    }

    public Appointment build (int id, LocalDateTime start, LocalDateTime end, String doctor, String client) {
        this.build(id, start, end);
        this.getAppointment().setDoctorName(doctor);
        this.getAppointment().setClientName(client);

        this.getAppointment().setType(AgendaType.RECURRING);

        return this.getAppointment();
    }
}
