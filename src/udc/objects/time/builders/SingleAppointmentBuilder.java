package udc.objects.time.builders;

import udc.objects.enums.AgendaType;
import udc.objects.time.concrete.Appointment;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class SingleAppointmentBuilder extends AppointmentBuilder {

    public SingleAppointmentBuilder(String doctor, String client) {
        super(doctor, client);
    }

    public Appointment build (LocalDateTime start, LocalDateTime end, String doctor, String client) {
        this.build(start, end);
        this.getAppointment().setDoctorName(doctor);
        this.getAppointment().setClientName(client);

        this.getAppointment().setType(AgendaType.SINGLE);

        return this.getAppointment();
    }

    public ArrayList<Appointment> build (int id, LocalDateTime start, LocalDateTime end, String doctor, String client) {
        ArrayList<Appointment> list = new ArrayList<>();

        LocalDateTime temp = start;
        while(temp.isBefore(end)){
            this.getAppointment().setId(id);
            this.getAppointment().setDoctorName(doctor);
            this.getAppointment().setClientName(client);
            this.getAppointment().setStartTime(temp);
            this.getAppointment().setEndTime(temp.plusMinutes(30));

            this.getAppointment().setType(AgendaType.SINGLE);
            list.add(this.getAppointment());
            temp = temp.plusMinutes(30);
        }
        return list;

    }

}
