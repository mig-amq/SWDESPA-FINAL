package udc.client.regular.FXMLControllers;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import udc.Model;
import udc.client.regular.Controller.ClientSuperController;
import udc.client.regular.Controller.WeekSchedule;
import udc.objects.enums.AgendaType;
import udc.objects.time.concrete.Agenda;
import udc.objects.time.concrete.Appointment;
import udc.objects.time.concrete.Unavailable;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ClientCalWeekController extends ClientSuperController implements Initializable {
    @FXML private TableView<WeekSchedule> weekTable;
    private ObservableList<TableColumn<WeekSchedule, String>> DayListColumns;
    @FXML private TableColumn<WeekSchedule, String> time;
    @FXML private TableColumn<WeekSchedule, String> mon;
    @FXML private TableColumn<WeekSchedule, String> tue;
    @FXML private TableColumn<WeekSchedule, String> wed;
    @FXML private TableColumn<WeekSchedule, String> thu;
    @FXML private TableColumn<WeekSchedule, String> fri;
    @FXML private TableColumn<WeekSchedule, String> sat;
    @FXML private TableColumn<WeekSchedule, String> sun;
    @FXML private JFXComboBox<String> mDoctorCmbBox;
    private ObservableList<String> doctorList;
    private ArrayList<Agenda> agendas;
    private ArrayList<Unavailable> unavailables;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DayListColumns = FXCollections.observableArrayList();
        DayListColumns.add(time);
        DayListColumns.add(mon);
        DayListColumns.add(tue);
        DayListColumns.add(wed);
        DayListColumns.add(thu);
        DayListColumns.add(fri);
        DayListColumns.add(sat);
        DayListColumns.add(sun);
        initPropertyValues();

    }

    private void initPropertyValues(){
        String[] cells = new String[]{"time", "mon", "tue", "wed", "thu", "fri", "sat", "sun"};
        for (int i = 0; i < DayListColumns.size(); i++) {
            TableColumn col = DayListColumns.get(i);
            col.setCellValueFactory(new PropertyValueFactory<WeekSchedule, String>(cells[i]));
        }
    }

    private void insertUnavailabilitytoAgendas(){
//            for (int i = 0; i <unavailables.size(); i++) {
//                Unavailable u = unavailables.get(i);
//                unavailables.get(i).setDoctorName(doctorList.get(u.getId()));
//            }
//            for (int i = 0; i < a.size() ; i++)
//                b.add(a.get(i));
        for (int i = 0; i < unavailables.size() ; i++)
            agendas.add(unavailables.get(i));
    }

    @Override
    public void insertFilterData(LocalDate selected) {
//        try {
//            agendas = model.getDbController().getAppointments(-1, "");
//            unavailables = model.getDbController().getUnvailability(-1);
//            for (int i = 0; i < unavailables.size(); i++) {
//                Unavailable a = unavailables.get(i);
//                unavailables.get(i).setDoctorName(doctorList.get(a.getId()).substring(4));
//            }
//            insertUnavailabilitytoAgendas();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        insertFilteredData(findWeekAgenda(), findStartingDay(selected));
    }

    public void insertFilteredData(ArrayList<ArrayList<Agenda>> dt, LocalDate stDte){
        //TODO: add Unavailability to arrList when merged with new repo
        //^^ Read comment from SecDayViewControl for instructions
        weekTable.getItems().clear();
        int hr = 7;
        for (int i = 0; i < 30; i++) {
            String tm = getDispTime(hr, i);
            if(!isOdd(i))
                hr++;
            if(!dt.isEmpty()) {
                insertDateToColumn(listWeekDates(stDte));
                weekTable.getItems().add(createWeekSchedule(dt, stDte, tm));
            }
        }
        for (int i = 1; i < DayListColumns.size(); i++) {
            setColumnCellFactory(DayListColumns.get(i), i);
        }
    }

    private ArrayList<ArrayList<Agenda>> findWeekAgenda(){
        ArrayList<ArrayList<Agenda>> WeekAgenda = new ArrayList<>();
        LocalDate StDayofWeek = findStartingDay(calendar.getSelected());
        for (int i = 0; i < 7; i++)
            WeekAgenda.add(findData(StDayofWeek.minusDays(-i)));
        return WeekAgenda;
    }

    private LocalDate findStartingDay(LocalDate date){
        LocalDate tempDate = date;
        int subtract = date.getDayOfWeek().getValue() - 1;
        tempDate = date.minusDays(subtract);
        return tempDate;
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

    private boolean isEqualDate(Agenda agenda, LocalDate selected){
        String sDoctorName = mDoctorCmbBox.getSelectionModel().getSelectedItem();
//        if(sDoctorName != null && !sDoctorName.equals("All"))
//            sDoctorName = sDoctorName.substring(4);

        if(sDoctorName!= null && agenda instanceof Appointment) {
            if (sDoctorName.equals("Miguel Quiambao") && sDoctorName.equals(((Appointment)agenda).getDoctorName())) //mq
                return dateToString(agenda.getStartTime()).equals(dateToString(selected));
            else if(sDoctorName.equals("Mitchell Ong") && sDoctorName.equals(((Appointment) agenda).getDoctorName()))
                return dateToString(agenda.getStartTime()).equals(dateToString(selected));
            else if(sDoctorName.equals("All"))
                return dateToString(agenda.getStartTime()).equals(dateToString(selected));
        }
//        else if(sDoctorName != null && agenda instanceof Unavailable){
//            if(agenda.getType().equals(AgendaType.SINGLE))
//                return nonRecurringAvailable(agenda, sDoctorName, selected);
//            else if(agenda.getType().equals(AgendaType.RECURRING))
//                return recurringAvailable(agenda, sDoctorName, selected);
//        }
        return false;//
    }


    private String dateToString(LocalDateTime localDateTime) {
        return localDateTime.getYear() + "/" + localDateTime.getMonth() + "/" + localDateTime.getDayOfMonth();
    }

    private String dateToString(LocalDate localDateTime) {
        return localDateTime.getYear() + "/" + localDateTime.getMonth() + "/" + localDateTime.getDayOfMonth();
    }

    @Override
    public void setModel (Model model) {
        super.setModel(model);

        doctorList = FXCollections.observableArrayList();
        doctorList.add("All");
        ArrayList<String> temp = model.getDbController().loadDoctors();
        for (int i = 0; i < temp.size()-1 ; i++) {
            doctorList.add(temp.get(i));
        }
        mDoctorCmbBox.setItems(doctorList);
        mDoctorCmbBox.getSelectionModel().select(0);
        mDoctorCmbBox.setOnAction(event -> {
            try {
                insertFilterData(calendar.getSelected());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        try {
            agendas = model.getDbController().getAppointments(model.getAccount().getId(), "CLIENT");
//            unavailables = model.getDbController().getUnvailability(-1);
//            for (int i = 0; i < unavailables.size(); i++) {
//                Unavailable a = unavailables.get(i);
//                unavailables.get(i).setDoctorName(doctorList.get(a.getId()));
//            }
//        for (int i = 0; i < agendas.size(); i++) {
//            Agenda agenda = agendas.get(i);
//            System.out.println("Unavailable " + i + " (ID): " +agenda.getId() + " " + unavailables.get(i).getDoctorName()
//                    + " start: " + agenda.getStartTime() + " end:" + agenda.getEndTime());
//        }
//            insertUnavailabilitytoAgendas();
        } catch (Exception e) {}

    }

    public void setColumnCellFactory(TableColumn<WeekSchedule, String> a, int b){
        a.setCellFactory(column -> {
            return new TableCell<>() {
                @Override
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
        if (item.contains("Dr. Miguel Quiambao") && !item.contains("Available")) {
            a.setStyle("-fx-background-color: #42f498");
            a.setText(item);
            System.out.println("not avail si quiambs");
        }else if(item.contains("Dr. Mitchell Ong") && !item.contains("Available")){
            a.setStyle("-fx-background-color: #6aa2fc");
            a.setText(item);
            System.out.println("not avail si mitch");
        }
//        else if(item.equalsIgnoreCase("Dr. Mitchell Ong - Available")){
//            a.setText(item);
////                a.setStyle("-fx-border-color: #72db91");
//            a.setStyle("-fx-background-color: #42f498");
//        } else if(item.equalsIgnoreCase("Dr. Miguel Quiambao - Available")){
//            a.setText(item);
////                a.setStyle("-fx-border-color: #2dd8b9");
//            a.setStyle("-fx-background-color: #42f498");
//        }else if(item.equalsIgnoreCase("(Available)")){ //both
//            a.setText("ALL DOCTORS ARE AVAILABLE");
//            a.setStyle("-fx-background-color: #42f498");
////                a.setStyle("-fx-border-color-color: #ffffff");
//        }
        else{
//            a.setStyle("-fx-background-color: #f4f4f4");
            a.setStyle("-fx-border-color: #f4f4f4");
            a.setText(null);
            a.setGraphic(null);
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
            int size = weekTable.getColumns().get(i + 1).getText().split(" | ").length;
            weekTable.getColumns().get(i + 1).setText(list.get(i) + " " + weekTable.getColumns().
                    get(i + 1).getText().split(" | ")[size -1] );
        }
    }

    public WeekSchedule createWeekSchedule(ArrayList<ArrayList<Agenda>> dt, LocalDate stDte, String tm){
        return new WeekSchedule(tm, gtDataForDay(dt, tm, stDte), gtDataForDay(dt, tm, stDte.minusDays(-1)), // -1 = +1 day
                gtDataForDay(dt, tm, stDte.minusDays(-2)), gtDataForDay(dt, tm, stDte.minusDays(-3)),
                gtDataForDay(dt, tm, stDte.minusDays(-4)), gtDataForDay(dt, tm, stDte.minusDays(-5)),
                gtDataForDay(dt, tm, stDte.minusDays(-6)));
    }

    public String gtDataForDay(ArrayList<ArrayList<Agenda>> data, String time, LocalDate stDate) {
        int DayofWeek = stDate.getDayOfWeek().getValue() - 1;
        int index;
        if(!data.get(DayofWeek).isEmpty()) {
            String index1 = getUnavailabilityFromList(data.get(DayofWeek), time, DayofWeek);
            if ((index = getDataIndexfromList(data.get(DayofWeek), time)) >= 0) {
                Appointment agenda = (Appointment) data.get(DayofWeek).get(index);
                return "Dr. " + agenda.getDoctorName();
            } else if (!index1.equals("")) {
                System.out.println("CLIENTCALWEEK 282 ALIEN");
                String[] a = index1.split(" | ");
                if (a.length == 2) {
                    return "(Available)";
                } else if (a.length == 1) {
                    Unavailable agenda = (Unavailable) data.get(DayofWeek).get(Integer.parseInt(a[a.length - 1]));
                    return "Dr. " + agenda.getDoctorName() + " - " + "Available";
                }
            }
        }
        return "";
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

    private void matchDoctorNametoID() {
        for (int i = 0; i < unavailables.size(); i++) {
            Unavailable a = unavailables.get(i);
            unavailables.get(i).setDoctorName(doctorList.get(a.getId())); //SUBSTRING??
        }
    }
    @Override
    public void update() {
        try {
//            unavailables = model.getDbController().getUnvailability(-1);
//            agendas = model.getDbController().getAppointments(-1, "");
//            insertUnavailabilitytoAgendas();
//            insertFilterData(calendar.getSelected());
            agendas = model.getDbController().getAppointments(model.getAccount().getId(), "CLIENT");
//            unavailables = model.getDbController().getUnvailability(-1);
//            matchDoctorNametoID();
//            insertUnavailabilitytoAgendas();

        } catch (Exception e) {
            e.printStackTrace();
        }
        insertFilteredData(findWeekAgenda(), findStartingDay(calendar.getSelected()));
    }
}
