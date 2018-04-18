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
import udc.objects.time.concrete.Agenda;
import udc.objects.time.concrete.Appointment;
import udc.objects.time.concrete.Unavailable;
import udc.secretary.Controller.WeekSchedule;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DoctorCalWeekController extends ClientSuperController implements Initializable {

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
    @FXML private JFXComboBox<String> bDoctorCmbBox;
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
        String[] cells = new String[]{"sTime", "sMon", "sTue", "sWed", "sThu", "sFri", "sSat", "sSun"};
        for (int i = 0; i < DayListColumns.size(); i++) {
            TableColumn col = DayListColumns.get(i);
            col.setCellValueFactory(new PropertyValueFactory<WeekSchedule, String>(cells[i]));
        }
    }

        private void insertUnavailabilitytoAgendas(ArrayList<Unavailable> a, ArrayList<Agenda> b){
            for (int i = 0; i <unavailables.size(); i++) {
                Unavailable u = unavailables.get(i);
                unavailables.get(i).setDoctorName(doctorList.get(u.getId()));
            }
            for (int i = 0; i < a.size() ; i++)
                b.add(a.get(i));
        }

    @Override
    public void insertFilterData(LocalDate selected) {
        try {
            agendas = model.getDbController().getAppointments(-1, "");
            unavailables = model.getDbController().getUnvailability(-1);
            insertUnavailabilitytoAgendas(unavailables, agendas);
        } catch (Exception e) {
            e.printStackTrace();
        }
        insertFilteredData(findWeekAgenda(), findStartingDay(selected));
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
        String sDoctorName = (String) bDoctorCmbBox.getSelectionModel().getSelectedItem();

        if(sDoctorName!= null && agenda instanceof Appointment) {
            if (sDoctorName.equals("Miguel Quiambao") && sDoctorName.equals(((Appointment)agenda).getDoctorName())) //mq
                return dateToString(agenda.getStartTime()).equals(dateToString(selected));
            else if(sDoctorName.equals("Mitchell Ong") && sDoctorName.equals(((Appointment) agenda).getDoctorName()))
                return dateToString(agenda.getStartTime()).equals(dateToString(selected));
            else if(sDoctorName.equals("All"))
                return dateToString(agenda.getStartTime()).equals(dateToString(selected));
        }else if(sDoctorName != null && agenda instanceof Unavailable){
            if (sDoctorName.equals("Miguel Quiambao") && sDoctorName.equals(((Unavailable)agenda).getDoctorName())) //mq
                return dateToString(agenda.getStartTime()).equals(dateToString(selected));
            else if(sDoctorName.equals("Mitchell Ong") && sDoctorName.equals(((Unavailable) agenda).getDoctorName()))
                return dateToString(agenda.getStartTime()).equals(dateToString(selected));
            else if(sDoctorName.equals("All"))
                return dateToString(agenda.getStartTime()).equals(dateToString(selected));
        }
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
        bDoctorCmbBox.setItems(doctorList);
        bDoctorCmbBox.getSelectionModel().select(0);
        bDoctorCmbBox.setOnAction(event -> {
            try {
                insertFilterData(calendar.getSelected());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    try {
        agendas = model.getDbController().getAppointments(-1, "");
        unavailables = model.getDbController().getUnvailability(-1);
        insertUnavailabilitytoAgendas(unavailables, agendas);
    } catch (Exception e) {}

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

    //Purposely set as public so other modules can use this to be placed
    // in their respective constructor for weekly
    // to extract agenda for each day
    //Check AbstractController from this package to check how it works for same day
    public WeekSchedule createWeekSchedule(ArrayList<ArrayList<Agenda>> dt, LocalDate stDte, String tm){
        return new WeekSchedule(tm, gtDataForDay(dt, tm, stDte), gtDataForDay(dt, tm, stDte.minusDays(-1)), // -1 = +1 day
                gtDataForDay(dt, tm, stDte.minusDays(-2)), gtDataForDay(dt, tm, stDte.minusDays(-3)),
                gtDataForDay(dt, tm, stDte.minusDays(-4)), gtDataForDay(dt, tm, stDte.minusDays(-5)),
                gtDataForDay(dt, tm, stDte.minusDays(-6)));
    }

    //same comment as above
    public String gtDataForDay(ArrayList<ArrayList<Agenda>> data, String time, LocalDate stDate) {
        int DayofWeek = stDate.getDayOfWeek().getValue() - 1;
        int index;
        if(!data.get(DayofWeek).isEmpty()) {
            String index1 = getUnavailabilityFromList(data.get(DayofWeek), time);
            if ((index = getDataIndexfromList(data.get(DayofWeek), time)) >= 0) {
                Appointment agenda = (Appointment) data.get(DayofWeek).get(index);
                return "Dr. " + agenda.getDoctorName();
            } else if (!index1.equals("")) {
                String[] a = index1.split(" | ");
                if (a.length == 2) {
                    return "(Unavailable)";
                } else if (a.length == 1) {
                    Unavailable agenda = (Unavailable) data.get(DayofWeek).get(Integer.parseInt(a[a.length - 1]));
                    return "Dr. " + agenda.getDoctorName() + " - " + "Unavailable";
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

    @Override
    public void update() {
        try {
            unavailables = model.getDbController().getUnvailability(-1);
            agendas = model.getDbController().getAppointments(-1, "");
            insertUnavailabilitytoAgendas(unavailables, agendas);
            insertFilterData(calendar.getSelected());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
