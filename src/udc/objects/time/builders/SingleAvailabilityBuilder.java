package udc.objects.time.builders;

import udc.objects.enums.AgendaType;
import udc.objects.time.concrete.Agenda;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class SingleAvailabilityBuilder extends AvaibilityBuilder{
    private ArrayList<Agenda> available;

    public void buildRecurring(int id, LocalDateTime start, LocalDateTime end){
        LocalDateTime temp;
        temp = start;
        while(temp.isBefore(end)){
            this.getAvailable().setType(AgendaType.SINGLE);
            this.getAvailable().setId(id);
            this.getAvailable().setStartTime(temp);
            this.getAvailable().setEndTime(temp.plusMinutes(30));
            available.add(this.getAvailable());
            temp = temp.plusMinutes(30);
        }
    }

    public ArrayList<Agenda> getAvailabl(){
        return available;
    }
}
