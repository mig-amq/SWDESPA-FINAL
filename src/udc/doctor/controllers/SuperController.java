package udc.doctor.controllers;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import udc.Model;
import udc.customfx.calendar.Calendar;
import udc.objects.time.concrete.Agenda;
import udc.objects.time.concrete.Appointment;
import udc.objects.time.concrete.Unavailable;
import udc.secretary.Controller.DaySchedule;
import udc.secretary.Controller.WeekSchedule;

import java.time.LocalDate;
import java.util.ArrayList;


public abstract class SuperController {
    protected Model model;
    protected  Calendar calendar;
    protected ArrayList<Agenda> agendas;
    protected ArrayList<Unavailable> Unavailability;

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

    public abstract void update(LocalDate ldt);

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

    public boolean isOdd(int i){
        return i % 2 != 0;
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

    public String getUnavailabilityFromList(ArrayList<Agenda> data, String time) {
        String index = "";
        int counter = 2; //increase counter for additional doctors
        for (int i = 0; i < data.size() && counter != 0; i++) {
            if (data.get(i) instanceof Unavailable) {
                String agendaTime = convertIntHrorMintoString(data.get(i).getStartTime().getHour()) + convertIntHrorMintoString(data.get(i).getStartTime().getMinute());
                String endTime = convertIntHrorMintoString(data.get(i).getEndTime().getHour()) + convertIntHrorMintoString(data.get(i).getEndTime().getMinute());
                String nTime = convertTimeFromTable(time); //converts time from table to military
                if (nTime.equals(agendaTime) || (Integer.parseInt(endTime) > Integer.parseInt(nTime) && Integer.parseInt(agendaTime)
                        < Integer.parseInt(nTime))) {

                    index += i + " ";
                    counter--;
                }
            }
        }
        System.out.println("Index1: " + index);
        return index;
    }

    protected void insertUnavailabilitytoAgendas(){
        for (int i = 0; i < Unavailability.size() ; i++)
            agendas.add(Unavailability.get(i));
    }

    protected LocalDate findStartingDay(LocalDate date){
        LocalDate tempDate = date;
        int subtract = date.getDayOfWeek().getValue() - 1;
        tempDate = date.minusDays(subtract);
        return tempDate;
    }

    private boolean isEqualDate(Agenda agenda, LocalDate selected){

        if(agenda instanceof Appointment) {
            return agenda.getStartTime().toLocalDate().isEqual(selected);
        }
        return false;//
    }

    protected ArrayList<Agenda> findData(LocalDate selected){
        ArrayList<Agenda> arrayList = new ArrayList<>();
        for (int i = 0; i < agendas.size(); i++) {
            Agenda agenda = agendas.get(i);
            if(isEqualDate(agenda, selected))
                arrayList.add(agenda);
        }
        return arrayList;
    }

    protected ArrayList<ArrayList<Agenda>> findWeekAgenda(){
        ArrayList<ArrayList<Agenda>> WeekAgenda = new ArrayList<>();
        LocalDate StDayofWeek = findStartingDay(calendar.getSelected());
        for (int i = 0; i < 7; i++)
            WeekAgenda.add(findData(StDayofWeek.minusDays(-i)));
        return WeekAgenda;
    }

    public void setColumnCellFactory(TableColumn<WeekSchedule, String> a, int b){
        a.setCellFactory(column -> {
            return new TableCell<WeekSchedule, String>() {
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
        if (item.contains("Dr. Miguel Quiambao") && !item.contains("Unavailable")) {
            a.setStyle("-fx-background-color: #42f498");
//                            System.out.println(tvWeekView.getItems().get(0).getTableRow().getIndex() + " " +getIndex() + " " + getCellData(tvWeekView.getItems().get(b), b));
//                            String prev = getCellData(getTableView().getItems().get(getTableRow().getIndex() -1), b);
//                            if(!(getTableRow().getIndex != 0 && item.equals(prev))) 2 appointment slots cell spanning
            a.setText(item);
        }else if(item.contains("Dr. Mitchell Ong") && !item.contains("Unavailable")){
            a.setStyle("-fx-background-color: #6aa2fc");
//                            System.out.println(tvWeekView.getItems().get(0).getTableRow().getIndex() + " " +getIndex() + " " + getCellData(tvWeekView.getItems().get(b), b));
//                            String prev = getCellData(getTableView().getItems().get(getTableRow().getIndex() -1), b);
//                            if(!(getTableRow().getIndex != 0 && item.equals(prev))) 2 appointment slots cell spanning
            //See SecWeekControl for Code Continuation
            a.setText(item);
        } else if(item.equalsIgnoreCase("Dr. Mitchell Ong - Unavailable")){
            a.setText("");
            a.setStyle("-fx-background-color: #d83c66");
        } else if(item.equalsIgnoreCase("Dr. Miguel Quiambao - Unavailable")){
            a.setText("");
            a.setStyle("-fx-background-color: #d35e43");
        }else if(item.equalsIgnoreCase("(Unavailable)")){ //both
            a.setText("");
            a.setStyle("-fx-background-color: #f44242");
        }  else{
            a.setStyle("-fx-background-color: #e5e2cc");
            a.setStyle("-fx-border-color: #c6c5ba");
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

}
