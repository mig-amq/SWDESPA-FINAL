package udc.objects.time.builders;

import udc.objects.enums.AgendaType;
import udc.objects.time.concrete.Agenda;
import udc.objects.time.concrete.Available;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class SingleAvailabilityBuilder extends AvaibilityBuilder{
    private ArrayList<Agenda> availablelist;

    public SingleAvailabilityBuilder(){
        super();
    }

    public ArrayList<Available> buildMultiple(int id, LocalDateTime start, LocalDateTime end){
        LocalDateTime temp;
        temp = start;
        Available av;
        ArrayList<Available> list = new ArrayList<>();
        while(temp.isBefore(end)){
            av = new Available();
            av.setType(AgendaType.SINGLE);
            av.setId(id);
            av.setStartTime(temp);
            av.setEndTime(temp.plusMinutes(30));
            list.add(av);
            temp = temp.plusMinutes(30);
        }
        return list;
    }

    public ArrayList<Agenda> getAvailablelist(){
        return availablelist;
    }
}
