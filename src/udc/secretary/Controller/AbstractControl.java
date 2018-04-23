package udc.secretary.Controller;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import udc.objects.enums.AgendaType;
import udc.objects.time.concrete.Agenda;
import udc.objects.time.concrete.Appointment;
import udc.objects.time.concrete.Unavailable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        //TODO: ADD END TIME //done
        for (int i = 0; i < data.size(); i++) {
            if(data.get(i) instanceof Appointment) {
                String agendaTime = convertIntHrorMintoString(data.get(i).getStartTime().getHour()) + convertIntHrorMintoString( data.get(i).getStartTime().getMinute());
                String endTime = convertIntHrorMintoString(data.get(i).getEndTime().getHour()) + convertIntHrorMintoString(data.get(i).getEndTime().getMinute());
                String nTime = convertTimeFromTable(time); //converts time from table to military
                if (nTime.equals(agendaTime) || (Integer.parseInt(endTime) > Integer.parseInt(nTime) && Integer.parseInt(agendaTime)
                        < Integer.parseInt(nTime)))
                    return i;
            }
        }
        return -1;
    }

    public String getUnavailabilityFromList(ArrayList<Agenda> data, String time, int DayOfWeek) {
        String index = "";
        int counter = 2; //increase counter for additional doctors
        for (int i = 0; i < data.size() && counter != 0; i++) {
            if (data.get(i) instanceof Unavailable) {
                String agendaTime = convertIntHrorMintoString(data.get(i).getStartTime().getHour()) + convertIntHrorMintoString(data.get(i).getStartTime().getMinute());
                String endTime = convertIntHrorMintoString(data.get(i).getEndTime().getHour()) + convertIntHrorMintoString(data.get(i).getEndTime().getMinute());
                String nTime = convertTimeFromTable(time); //converts time from table to military
                if (nTime.equals(agendaTime) || (Integer.parseInt(endTime) > Integer.parseInt(nTime) && Integer.parseInt(agendaTime)
                        < Integer.parseInt(nTime))) {
                    if (data.get(i).getType().equals(AgendaType.SINGLE)) {
                        index += i + " ";
                        counter--;
                    } else if (data.get(i).getType().equals(AgendaType.RECURRING)) {
                        String[] days = ((Unavailable) data.get(i)).getRecurringDays().split(" | ");
                        for (int j = 0; j < days.length; j++) {
                            if (DayOfWeek == strToDayOfWeek(days[j])) {
                                index += i + " ";
                                counter--;
                                break;
                            }
                        }
                    }
                }
            }
        }
        return index;
    }

    public void setColumnCellFactory(TableColumn<WeekSchedule, String> a, int b){
        a.setCellFactory(column -> {
            return new TableCell<>() {
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        applyCellFactoryCondition(item, this);
                    }
                }

            };
        });
    }

    private void applyCellFactoryCondition(String item, TableCell a){
        System.out.println(item);
        if (item.contains("Dr. Miguel Quiambao") && !item.contains("Available")) {
            a.setStyle("-fx-border-color: #42f498");
//                            System.out.println(tvWeekView.getItems().get(0).getTableRow().getIndex() + " " +getIndex() + " " + getCellData(tvWeekView.getItems().get(b), b));
//                            String prev = getCellData(getTableView().getItems().get(getTableRow().getIndex() -1), b);
//                            if(!(getTableRow().getIndex != 0 && item.equals(prev))) 2 appointment slots cell spanning
            System.out.println(item);
            a.setText(item);
        }else if(item.contains("Dr. Mitchell Ong") && !item.contains("Available")){
            a.setStyle("-fx-border-color: #6aa2fc");
//                            System.out.println(tvWeekView.getItems().get(0).getTableRow().getIndex() + " " +getIndex() + " " + getCellData(tvWeekView.getItems().get(b), b));
//                            String prev = getCellData(getTableView().getItems().get(getTableRow().getIndex() -1), b);
//                            if(!(getTableRow().getIndex != 0 && item.equals(prev))) 2 appointment slots cell spanning
            //See SecWeekControl for Code Continuation

            a.setText(item);
        } else if(item.equalsIgnoreCase("Dr. Mitchell Ong - Available")){
            a.setText("");
            a.setStyle("-fx-border-color: #72db91");
        } else if(item.equalsIgnoreCase("Dr. Miguel Quiambao - Available")){
            a.setText("");
            a.setStyle("-fx-border-color: #2dd8b9");
        }else if(item.equalsIgnoreCase("(Available)")){ //both
            a.setText("");
            a.setStyle("-fx-border-color-color: #ffffff");
        }  else{
            a.setStyle("-fx-background-color: #ff584c");
//            a.setStyle("-fx-background-color: #c6c5ba");
            a.setText(null);
            a.setGraphic(null);
        }
    }

    public void setColumnCellFactory(TableColumn<DaySchedule, String> a){
        a.setCellFactory(column -> {
            return new TableCell<>() {
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        applyCellFactoryCondition(item, this);
                    }
                }

            };
        });
    }

    private int strToDayOfWeek(String day){
        if (day.equalsIgnoreCase("m"))
            return 0;
        else if (day.equalsIgnoreCase("t"))
            return 1;
        else if(day.equalsIgnoreCase("w"))
            return 2;
        else if(day.equalsIgnoreCase("h"))
            return 3;
        else if(day.equalsIgnoreCase("f"))
            return 4;
        else if(day.equalsIgnoreCase("s"))
            return 5;
        else if(day.equalsIgnoreCase("su"))
            return 6;
        return  -1;
    }
}
