package udc.doctor.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import udc.objects.time.concrete.Agenda;
import udc.objects.time.concrete.Appointment;
import udc.objects.time.concrete.Unavailable;
import udc.secretary.Controller.AbstractControl;
import udc.secretary.Controller.DaySchedule;
import udc.secretary.Controller.WeekSchedule;

import java.io.IOException;
import java.util.ArrayList;

public class Day extends TableView {
    private Node node;
    private TableView<DaySchedule> tbView;
    private ObservableList<Node> components;
    private TableColumn<DaySchedule, String> tcDoctorColumn;

    public Day() throws IOException{
        node = FXMLLoader.load(getClass().getResource("../fxml/Day.fxml"));
        components = ((AnchorPane)((ScrollPane)(node)).getContent()).getChildren();

        initComponents(components);
        initPropertValues();
    }

    public Node getNode() {
        return node;
    }

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
            a.setStyle("-fx-background-color: #e25d2d");
        } else if(item.equalsIgnoreCase("Dr. Miguel Quiambao - Unavailable")){
            a.setText("");
            a.setStyle("-fx-background-color: #3382bf");
        }else if(item.equalsIgnoreCase("(Unavailable)")){ //both
            a.setText("");
            a.setStyle("-fx-background-color: #87312b");
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

    private void initComponents(ObservableList<Node> component){
        for (int i = 0; i < component.size(); i++) {
            Node node  = component.get(i);
            if(node.getId() !=null){
                if(node.getId().equals("tbView")){
                    tbView = (TableView) node;
                    tbView.getSelectionModel().setCellSelectionEnabled(true);
                    for (int j = 0; j < tbView.getColumns().size(); j++) {
                        if (tbView.getColumns().get(j).getId().equals("colDoctors"))
                            tcDoctorColumn = (TableColumn<DaySchedule, String>) tbView.getColumns().get(j);
                    }
                }

                //add more here if you're adding more components to gui
            }
        }
    }

    private void initPropertValues(){
        String[] cells = new String[]{"sTime", "sClientDoctor"};
        for (int i = 0; i < tbView.getColumns().size(); i++) {
            TableColumn col = tbView.getColumns().get(i);
            col.setCellValueFactory(new PropertyValueFactory<WeekSchedule, String>(cells[i]));
        }
    }

    public void insertFilteredData(ArrayList<Agenda> data){//ArrayList<Appointment> data
//        data = sortTime(data);
        //TODO: ADD UNAVAILABILITY DISPLAY, PLACE IT INSIDE findData method()
        tbView.getItems().clear();
        int hr = 7;
        for (int i = 0; i < 30; i++) {
            int index;

            String time = getDispTime(hr, i);
            if(!isOdd(i))
                hr++;
            String index1 = getUnavailabilityFromList(data, time);
            if((index = getDataIndexfromList(data, time)) >= 0 ) {
                Appointment agenda = (Appointment) data.get(index);
                tbView.getItems().add(new DaySchedule(time, "Dr. " + agenda.getDoctorName() + "\nClient: " + agenda.getClientName()));
            }else if(!index1.equals("")){
                String[] a = index1.split(" | ");
                if(a.length == 2) {
                    tbView.getItems().add(new DaySchedule(time, "(Available)"));
                } else if(a.length == 1){
                    Unavailable agenda = (Unavailable) data.get(Integer.parseInt(a[a.length -1]));
                    tbView.getItems().add(new DaySchedule(time, "Dr. " + agenda.getDoctorName() + " - " + "Available"));
                }else {
                    tbView.getItems().add(new DaySchedule(time, ""));
                }
            }
            else
                tbView.getItems().add(new DaySchedule(time, ""));
        }
        setColumnCellFactory(tcDoctorColumn);
    }
    public TableView<DaySchedule> getTbView(){
        return tbView;
    }

}
