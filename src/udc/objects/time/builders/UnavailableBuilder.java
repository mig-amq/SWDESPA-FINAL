package udc.objects.time.builders;

import udc.objects.time.concrete.Agenda;
import udc.objects.time.concrete.Unavailable;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class UnavailableBuilder implements AgendaBuilder {
    private int id;
    private Unavailable unavailable;

    public UnavailableBuilder (int id) {
        this.id = id;
    }

    @Override
    public Unavailable build(LocalDateTime start, LocalDateTime end) {
        this.setUnavailable(new Unavailable());
        this.unavailable.setId(id);
        this.getUnavailable().setStartTime(start);
        this.getUnavailable().setEndTime(end);

        return this.getUnavailable();
    }

    @Override
    public Unavailable build(int id, LocalDateTime start, LocalDateTime end) {
        this.build(start, end);
        this.unavailable.setId(id);

        this.getUnavailable().setId(id);

        return this.getUnavailable();
    }

    public void setUnavailable(Unavailable unavailable) {
        this.unavailable = unavailable;
    }

    public Unavailable getUnavailable() {
        return unavailable;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
//
//    private int id;
//    private Unavailable unavailable;
//    private ArrayList<Unavailable> unavailablility; //for splitting purposes
//
//    public UnavailableBuilder (int id) {
//        this.id = id;
//    }
//
//    @Override
//    public ArrayList<Unavailable> build(LocalDateTime start, LocalDateTime end) {
//        LocalDateTime temp;
//        temp = start;
//        int i = 0;
//        while(temp.isBefore(end)){
//            this.setUnavailable(new Unavailable());
//            this.unavailable.setId(id);
//
//            this.getUnavailable(i).setStartTime(temp);
//            this.getUnavailable(i).setEndTime(temp.plusMinutes(30));
//            i++;
//            temp = temp.plusMinutes(30);
//        }
//
//        return this.getUnavailable();
//    }
//
//    @Override
//    public ArrayList<Unavailable> build(int id, LocalDateTime start, LocalDateTime end) {
//        this.build(start, end);
//        this.unavailable.setId(id);
//
//        this.getUnavailable().setId(id);
//
//        return this.getUnavailable();
//    }
//
//    public void setUnavailable(Unavailable unavailable) {
//        unavailablility.add(unavailable);
//    }
//
//    public ArrayList<Unavailable> getUnavailable() {
//        return unavailablility;
//    }
//
//    public Unavailable getUnavailable(int i) {
//        return unavailablility.get(i);
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public int getId() {
//        return id;
//    }
}
