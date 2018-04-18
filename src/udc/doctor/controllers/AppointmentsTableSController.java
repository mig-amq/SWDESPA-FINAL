package udc.doctor.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import udc.Model;
import udc.objects.time.concrete.Agenda;
import udc.objects.time.concrete.Appointment;
import udc.objects.time.concrete.Unavailable;
import udc.secretary.Controller.WeekSchedule;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AppointmentsTableSController extends SuperController implements Initializable {
    private ObservableList<TableColumn<WeekSchedule, String>> DayListColumns;

    @FXML
    private TableView<WeekSchedule> tvWeekView;

    @FXML
    private TableColumn<WeekSchedule, String> time;

    @FXML
    private TableColumn<WeekSchedule, String> mon;

    @FXML
    private TableColumn<WeekSchedule, String> tue;

    @FXML
    private TableColumn<WeekSchedule, String> wed;

    @FXML
    private TableColumn<WeekSchedule, String> thu;

    @FXML
    private TableColumn<WeekSchedule, String> fri;

    @FXML
    private TableColumn<WeekSchedule, String> sat;

    @FXML
    private TableColumn<WeekSchedule, String> sun;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DayListColumns = FXCollections.observableArrayList();
    }

    @Override
    public void update(LocalDate ldt) {
        instantiateColumns();
        initPropertyValues();
        agendas = model.getAccount().getAppointments();
        insertFilteredData(findWeekAgenda(), findStartingDay(calendar.getSelected()));
    }


    @Override
    public void setModel(Model model){
        this.model = model;
        update(calendar.getSelected());
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
            int size = tvWeekView.getColumns().get(i + 1).getText().split(" | ").length;
            tvWeekView.getColumns().get(i + 1).setText(list.get(i) + " " + tvWeekView.getColumns().
                    get(i + 1).getText().split(" | ")[size -1] );
        }
    }


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
                return "Dr. " + agenda.getDoctorName() + "\nClient: " + agenda.getClientName();
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

}
