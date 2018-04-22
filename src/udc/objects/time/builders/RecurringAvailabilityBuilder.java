package udc.objects.time.builders;

import udc.objects.enums.AgendaType;
import udc.objects.time.concrete.Agenda;
import udc.objects.time.concrete.Appointment;
import udc.objects.time.concrete.Available;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class RecurringAvailabilityBuilder extends AvaibilityBuilder {
    private ArrayList<Agenda> availablelist;

    public void buildRecurring(int id, LocalDateTime start, LocalDateTime end){
        LocalDateTime temp;
        temp = start;
        while(temp.isBefore(end)){
            this.getAvailable().setType(AgendaType.RECURRING);
            this.getAvailable().setId(id);
            this.getAvailable().setStartTime(temp);
            this.getAvailable().setEndTime(temp.plusMinutes(30));
            availablelist.add(this.getAvailable());
            temp = temp.plusMinutes(30);
        }
    }

    public Available build (LocalDateTime start, LocalDateTime end, String doctor, String client) {
        this.build(start, end);
        this.getAvailable().setDoctorName(doctor);

        this.getAvailable().setType(AgendaType.RECURRING);

        return this.getAvailable();
    }

    public ArrayList<Available> buildMultiple (LocalDateTime start, LocalDateTime end, String doctor, String client) {
        ArrayList<Available> list = new ArrayList<>();
        LocalDateTime temp = start;
        while (temp.isBefore(end)) {

            this.getAvailable().setDoctorName(doctor);
            this.getAvailable().setStartTime(temp);
            this.getAvailable().setEndTime(temp.plusMinutes(30));
            this.getAvailable().setType(AgendaType.RECURRING);
            temp = temp.plusMinutes(30);
            list.add(this.getAvailable());
        }

        return list;

    }
    public ArrayList<Agenda> getAvailableList(){
        return availablelist;
    }
}
