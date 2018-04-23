package udc.doctor.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import udc.doctor.objects.AgendaRow;
import udc.doctor.objects.AgendaRowWeek;
import udc.objects.enums.AgendaType;
import udc.objects.time.concrete.Agenda;
import udc.objects.time.concrete.Appointment;
import udc.objects.time.concrete.Unavailable;
import udc.secretary.Controller.DaySchedule;
import udc.secretary.Controller.WeekSchedule;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Week extends TableView<AgendaRow> {
    private Node node;
    private ObservableList<TableColumn<WeekSchedule, String>> DayListColumns;
    private ArrayList<Agenda> agendas;
    private ObservableList<Node> components;
    private LocalDate date;
    private ArrayList<Agenda> data;
    private final DateTimeFormatter format = DateTimeFormatter.ofPattern("hh:mm a");

    @FXML
    private TableColumn<WeekSchedule, String> time, mon, tue, wed, thu, fri, sat, sun;

    @FXML
    private TableView<WeekSchedule> tvWeekView;

    public Week (ArrayList<Agenda> data) throws IOException{
        DayListColumns = FXCollections.observableArrayList();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/Week.fxml"));
        loader.setController(this);
        loader.load();

        node = (Node) loader.getRoot();
        components = ((AnchorPane)((ScrollPane)(node)).getContent()).getChildren();

        initComponents(components);
        initPropertyValues();

        this.setData(data);
        this.setDate(LocalDate.now());
    }

    private ArrayList<ArrayList<Agenda>> findWeekAgenda(){
        ArrayList<ArrayList<Agenda>> WeekAgenda = new ArrayList<>();
        LocalDate StDayofWeek = findStartingDay(this.date);
        for (int i = 0; i < 7; i++)
            WeekAgenda.add(findData(StDayofWeek.minusDays(-i)));
        return WeekAgenda;
    }

    private boolean isEqualDate(Agenda agenda, LocalDate selected){
        if(agenda.getType().equals(AgendaType.SINGLE))
            return nonRecurringAvailable(agenda, selected);
        else if(agenda.getType().equals(AgendaType.RECURRING))
            return recurringAvailable(agenda, selected);
        return false;//
    }

    private boolean nonRecurringAvailable(Agenda agenda, LocalDate selected){
        return dateToString(agenda.getStartTime().toLocalDate()).equals(dateToString(selected));


    }

    private boolean isBtwStEndDate(Agenda agenda, LocalDate Selected){
        LocalDate endDate = agenda.getEndTime().toLocalDate();
        if(!dateToString(agenda.getStartTime().toLocalDate()).equals(dateToString(Selected))){
            if (endDate.getYear() == Selected.getYear()) {
                if (endDate.getMonthValue() > Selected.getMonthValue())
                    return true;
                else if (endDate.getMonthValue() == Selected.getMonthValue() && endDate.getDayOfMonth() >= Selected.getDayOfMonth())
                    return true;
            } else if (endDate.getYear() > Selected.getYear())
                return true;
        } else return true;
        return false;
    }

    private boolean recurringAvailable(Agenda agenda, LocalDate selected){
        return isBtwStEndDate(agenda, selected);
    }

    private String dateToString(LocalDate localDateTime) {
        return localDateTime.getYear() + "/" + localDateTime.getMonth() + "/" + localDateTime.getDayOfMonth();
    }

    private ArrayList<Agenda> findData(LocalDate selected){
        ArrayList<Agenda> arrayList = new ArrayList<>();
        for (int i = 0; i < agendas.size(); i++) {
            Agenda agenda = agendas.get(i);
            if(isEqualDate(agenda, selected))
                arrayList.add(agenda);
        }
        return arrayList;
    }

    private LocalDate findStartingDay(LocalDate date){
        LocalDate tempDate = date;
        int subtract = date.getDayOfWeek().getValue() - 1;
        tempDate = date.minusDays(subtract);
        return tempDate;
    }

    public Week (ArrayList<Agenda> data, LocalDate ldt) throws IOException{
        this(data);
        this.setDate(ldt);
    }

    private void initComponents(ObservableList<Node> component){
        for (int i = 0; i <  component.size(); i++) {
            Node node = component.get(i);
            if(node instanceof TableView && node.getId() != null)
                if(node.getId().equals("tvWeekView")) {
                    tvWeekView = (TableView<WeekSchedule>) node;
                    tvWeekView.getSelectionModel().setCellSelectionEnabled(true);
                }
            //add more for additional components
        }
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



    private ObservableList<TableColumn> findTableView(ObservableList<Node> a){
        for (Node b: a) {
            if(b instanceof TableView) {
                tvWeekView = (TableView) b;
                return ((TableView) b).getColumns();
            }
        }
        return null;
    }

    //Converts String to simple string property
    private SimpleStringProperty toSSP(String string){
        return new SimpleStringProperty(string);
    }

    public TableView getTableView(){
        return tvWeekView;
    }

    private void instantiateColumns(){
        for (int i = 0; i < tvWeekView.getColumns().size(); i++) {
            TableColumn<WeekSchedule, String> a = (TableColumn<WeekSchedule, String>) tvWeekView.getColumns().get(i);
            if(a.getId().equals("tcTime")){
                time = a;
                DayListColumns.add(time);
            }
            else if(a.getId().equals("tcMonday")) {
                mon = a;
                DayListColumns.add(mon);
            }
            else if(a.getId().equals("tcTuesday")) {
                tue = a;
                DayListColumns.add(tue);
            }
            else if(a.getId().equals("tcWednesday")) {
                wed = a;
                DayListColumns.add(wed);
            }
            else if(a.getId().equals("tcThursday")) {
                thu = a;
                DayListColumns.add(thu);
            }
            else if(a.getId().equals("tcFriday")) {
                fri = a;
                DayListColumns.add(fri);
            }
            else if(a.getId().equals("tcSaturday")) {
                sat = a;
                DayListColumns.add(sat);
            }
            else if (a.getId().equals("tcSunday")) {
                sun = a;
                DayListColumns.add(sun);
            }
        }
    }

    private void initPropertyValues(){
        String[] cells = new String[]{"sTime", "sMon", "sTue", "sWed", "sThu", "sFri", "sSat", "sSun"};
        for (int i = 0; i < DayListColumns.size(); i++) {
            TableColumn col = DayListColumns.get(i);
            col.setCellValueFactory(new PropertyValueFactory<WeekSchedule, String>(cells[i]));
        }
    }

    public void insertFilteredData(ArrayList<ArrayList<Agenda>> dt, LocalDate stDte){
        //TODO: add Unavailability to arrList when merged with new repo
        //^^ Read comment from SecDayViewControl for instructions
        tvWeekView.getItems().clear();
        int hr = 7;
        for (int i = 0; i < 30; i++) {
            String tm = getDispTime(hr, i);
            if(!isOdd(i))
                hr++;
            if(!dt.isEmpty()) {
                insertDateToColumn(listWeekDates(stDte));
                tvWeekView.getItems().add(createWeekSchedule(dt, stDte, tm));
            }
        }
        for (int i = 1; i < DayListColumns.size(); i++) {
            setColumnCellFactory(DayListColumns.get(i), i);
        }
    }

    private ArrayList<String> listWeekDates(LocalDate startingDate){
        ArrayList<String>  list = new ArrayList<>();
        for (int i = 0; i < 7; i++)
            list.add(startingDate.minusDays(-i).getMonth().name() + " " + startingDate.minusDays(-i).getDayOfMonth());
        return list;
    }

    private void insertDateToColumn(ArrayList<String> list){
        for (int i = 0; i < list.size(); i++) {
            int size = tvWeekView.getColumns().get(i).getText().split(" | ").length;
            tvWeekView.getColumns().get(i + 1).setText(list.get(i) + " " + tvWeekView.getColumns().
                    get(i).getText().split(" | ")[size -1] );
        }
    }


    public WeekSchedule createWeekSchedule(ArrayList<ArrayList<Agenda>> dt, LocalDate stDte, String tm){
        return new WeekSchedule(tm, gtDataForDay(dt, tm, stDte), gtDataForDay(dt, tm, stDte.minusDays(-1)), // -1 = +1 day
                gtDataForDay(dt, tm, stDte.minusDays(-2)), gtDataForDay(dt, tm, stDte.minusDays(-3)),
                gtDataForDay(dt, tm, stDte.minusDays(-4)), gtDataForDay(dt, tm, stDte.minusDays(-5)),
                gtDataForDay(dt, tm, stDte.minusDays(-6)));
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

    public String convertIntHrorMintoString(int value) {
        if(value == 0)//for this case if you decided to use 12am as time
            return "00";
        else if(value < 10)
            return 0 + String.valueOf(value);
        return String.valueOf(value);
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

    //same comment as above
    public String gtDataForDay(ArrayList<ArrayList<Agenda>> datas, String time, LocalDate stDate) {
        int DayofWeek = stDate.getDayOfWeek().getValue() - 1;
        int index;
        if(!datas.get(DayofWeek).isEmpty()) {
            String index1 = getUnavailabilityFromList(datas.get(DayofWeek), time, DayofWeek);
            if ((index = getDataIndexfromList(datas.get(DayofWeek), time)) >= 0) {
                Appointment agenda = (Appointment) datas.get(DayofWeek).get(index);
                return "Dr. " + agenda.getDoctorName() + "\nClient: " + agenda.getClientName();
            } else if (!index1.equals("")) {
                String[] a = index1.split(" | ");
                if (a.length == 2) {
                    return "(Available)";
                } else if (a.length == 1) {
                    Unavailable agenda = (Unavailable) datas.get(DayofWeek).get(Integer.parseInt(a[a.length - 1]));
                    return "Dr. " + agenda.getDoctorName() + " - " + "Available";
                }
            }
        }
        return "";
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


    public void setData(ArrayList<Agenda> data) {
        this.data = data;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    public ArrayList<Agenda> getData() {
        return data;
    }
}
