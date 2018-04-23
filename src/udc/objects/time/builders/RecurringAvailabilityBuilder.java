package udc.objects.time.builders;

import udc.objects.enums.AgendaType;
import udc.objects.time.concrete.Agenda;
import udc.objects.time.concrete.Appointment;
import udc.objects.time.concrete.Available;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class RecurringAvailabilityBuilder extends AvaibilityBuilder {
    private ArrayList<Agenda> availablelist;

    public RecurringAvailabilityBuilder(){
        super();
    }

    public Available build (LocalDateTime start, LocalDateTime end, String doctor) {
        this.build(start, end);
        this.getAvailable().setDoctorName(doctor);

        this.getAvailable().setType(AgendaType.RECURRING);

        return this.getAvailable();
    }

    public ArrayList<Available> buildMultiple (LocalDateTime start, LocalDateTime end, String doctor, String recurringDays) {
        ArrayList<Available> list = new ArrayList<>();
        LocalDateTime temp = start;
        Available av;
        while (temp.isBefore(end)) {
            av = new Available();
            av.setRecurringDays(recurringDays);
            av.setDoctorName(doctor);
            av.setStartTime(temp);
            av.setEndTime(temp.plusMinutes(30));
            av.setType(AgendaType.RECURRING);
            temp = temp.plusMinutes(30);
            list.add(this.getAvailable());
        }

        return list;

    }
    public ArrayList<Agenda> getAvailableList(){
        return availablelist;
    }
}
