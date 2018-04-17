package udc.doctor.controllers;

import udc.Model;
import udc.customfx.calendar.Calendar;

import java.time.LocalDateTime;

public abstract class SuperController {
    protected Model model;
    protected  Calendar calendar;

    public void setModel(Model model) {
        this.model = model;
    }

    public Model getModel() {
        return model;
    }

    public void setCalendar(Calendar calendar){
        this.calendar = calendar;
    }

    public Calendar getCalendar(){
        return calendar;
    }

    public abstract void update(LocalDateTime ldt);

}
