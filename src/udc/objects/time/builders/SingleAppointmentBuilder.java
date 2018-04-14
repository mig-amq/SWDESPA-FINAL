package udc.objects.time.builders;

import udc.objects.enums.AgendaType;

import java.time.LocalDateTime;

public class SingleAppointmentBuilder extends AppointmentBuilder {

    public void build (LocalDateTime start, LocalDateTime end, String doctor, String client) {
        this.build(start, end);
        this.getAppointment().setDoctorName(doctor);
        this.getAppointment().setClientName(client);

        this.getAppointment().setType(AgendaType.SINGLE);
    }

    public void build (int id, LocalDateTime start, LocalDateTime end, String doctor, String client) {
        this.build(id, start, end);
        this.getAppointment().setDoctorName(doctor);
        this.getAppointment().setClientName(client);

        this.getAppointment().setType(AgendaType.SINGLE);
    }

}
