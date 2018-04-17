package udc.client.regular.Controller;

import javafx.fxml.Initializable;
import udc.Model;
import udc.customfx.calendar.Calendar;
import udc.objects.time.concrete.Agenda;

import java.time.LocalDate;
import java.util.ArrayList;

public abstract class ClientSuperController implements Initializable {
    protected Model model;
    protected Calendar calendar;
    protected ArrayList<Agenda> agendas;

    public abstract void insertFilterData(ArrayList<Agenda> data);
    public abstract void insertFilterData(LocalDate selected);

    public void setModel(Model model) { this.model = model; }
    public void setCalendar(Calendar calendar) { this.calendar = calendar; }
    
    public boolean isOdd(int i){
        return i % 2 != 0;
    }

    public ArrayList<Agenda> findData(LocalDate date) throws Exception {
        agendas =  model.getDbController().getAppointments(-1, "");
        ArrayList<Agenda> arrayList = new ArrayList<>();

//        for (int i = 0; i < agendas.size(); i++) {
//            Agenda agenda = agendas.get(i);
//            if(isEqualDate(agenda, selected))
//                arrayList.add(agenda);
//        }
        return arrayList;
    }
    public String getDispTime(int hr, int i){
        String time;
        String end;
        int temp = 0;
        if (hr > 12)
            temp -= 12;

        if(hr <= 11) end = " AM";
        else end = " PM";

        if(isOdd(i)) {//all add :00
            temp += hr;
            time = temp + ":00";
        }else{
            temp +=hr;
            time = temp +":30";
        }
        time += end;
        return time;
    }

}
