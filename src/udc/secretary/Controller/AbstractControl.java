package udc.secretary.Controller;

import udc.objects.time.concrete.Agenda;
import udc.objects.time.concrete.Appointment;

import java.util.ArrayList;

public abstract class AbstractControl {
    public boolean isOdd(int i){
        return i % 2 != 0;
    }

    public String getDispTime(int hr, int i){
        String time;
        String end;
        int temp = 0;
        if (hr > 12)
            temp -= 12;

        if(hr <= 11)
            end = " AM";
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

    public String convertTimeFromTable(String time){
        String[] arrSTime = time.split(" | ");
        String period = arrSTime[1];
        String[] arrTime = arrSTime[0].split( ":");
        if(period.equalsIgnoreCase("PM") && Integer.parseInt(arrTime[0]) != 12)
            arrTime[0] = String.valueOf(Integer.parseInt(arrTime[0]) + 12);
        else if(period.equalsIgnoreCase("AM") && Integer.parseInt(arrTime[0]) < 10)
            arrTime[0] =  "0" + arrTime[0];
        return arrTime[0] + arrTime[1];
    }

    //before using add condition to the class using this so it won't be called when condition failse
    //Example: if(hr < 1000)
    //              convertIntHrtoString(int hr)
    //this adds 0 to the front
    public String convertIntHrorMintoString(int value) {
        if(value == 0)//for this case if you decided to use 12am as time
        return "00";
        else if(value < 10)
            return 0 + String.valueOf(value);
        return String.valueOf(value);
    }

    public int getDataIndexfromList(ArrayList<Agenda> data, String time){
        //TODO: ADD END TIME
        for (int i = 0; i < data.size(); i++) {
            String agendaTime = convertIntHrorMintoString(data.get(i).getStartTime().getHour()) + convertIntHrorMintoString( data.get(i).getStartTime().getMinute());
            String nTime = convertTimeFromTable(time); //converts time from table to military
            if(nTime.equals(agendaTime))
                return i;
        }
        return -1;
    }
}
