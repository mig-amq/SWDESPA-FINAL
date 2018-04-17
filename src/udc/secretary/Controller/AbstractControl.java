package udc.secretary.Controller;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
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

    public void setColumnCellFactory(TableColumn<WeekSchedule, String> a, int b){
        a.setCellFactory(column -> {
            return new TableCell<>() {
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        if (item.contains("Dr. Miguel Quiambao") && !item.contains("Unavailable")) {
                            setStyle("-fx-background-color: #42f498");
//                            System.out.println(tvWeekView.getItems().get(0).getTableRow().getIndex() + " " +getIndex() + " " + getCellData(tvWeekView.getItems().get(b), b));
//                            String prev = getCellData(getTableView().getItems().get(getTableRow().getIndex() -1), b);
//                            if(!(getTableRow().getIndex != 0 && item.equals(prev))) 2 appointment slots cell spanning
                            setText(item);
                        }else if(item.contains("Dr. Mitchell Ong") && !item.contains("Unavailable")){
                            setStyle("-fx-background-color: #6aa2fc");
//                            System.out.println(tvWeekView.getItems().get(0).getTableRow().getIndex() + " " +getIndex() + " " + getCellData(tvWeekView.getItems().get(b), b));
//                            String prev = getCellData(getTableView().getItems().get(getTableRow().getIndex() -1), b);
//                            if(!(getTableRow().getIndex != 0 && item.equals(prev))) 2 appointment slots cell spanning
                            //See SecWeekControl for Code Continuation
                            setText(item);
                        } else if(item.equalsIgnoreCase("Dr. Mitchell Ong - Unavailable")){
                            setText("");
                            setStyle("-fx-background-color: #f26f29");
                        } else if(item.equalsIgnoreCase("Dr. Miguel Quiambao - Unavailable")){
                            setText("");
                            setStyle("-fx-background-color: #f2534b");
                        }else if(item.equalsIgnoreCase("(Unavailable)")){ //both
                            setText("");
                            setStyle("-fx-background-color: #3a231a");
                        }  else{
                            setStyle("-fx-background-color: #e5e2cc");
                            setStyle("-fx-border-color: #c6c5ba");
                            setText(null);
                            setGraphic(null);
                        }
                    }
                }

            };
        });
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
                        if (item.contains("Dr. Miguel Quiambao") && !item.contains("Unavailable")) {
                            setStyle("-fx-background-color: #42f498");
                            setText(item);
                        }else if(item.contains("Dr. Mitchell Ong") && !item.contains("Unavailable")){
                            setStyle("-fx-background-color: #6aa2fc");
                            setText(item);
                        } else if(item.equalsIgnoreCase("Dr. Mitchell Ong - Unavailable")){
                            setText("");
                            setStyle("-fx-background-color: #f26f29");
                        } else if(item.equalsIgnoreCase("Dr. Miguel Quiambao - Unavailable")){
                            setText("");
                            setStyle("-fx-background-color: #f2534b");
                        } else if(item.equals("(Unavailable)")){ //both
                            setText("");
                            setStyle("-fx-background-color: #3a231a");
                        } else{
                            setStyle("-fx-background-color: #e5e2cc");
                            setStyle("-fx-border-color: #c6c5ba");
                            setText(null);
                            setGraphic(null);
                        }
                    }
                }

            };
        });
    }
}
