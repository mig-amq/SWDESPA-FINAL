package udc.secretary.Controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import javafx.util.converter.IntegerStringConverter;
import udc.objects.time.concrete.Agenda;
import udc.objects.time.concrete.Appointment;
import udc.objects.time.concrete.Unavailable;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class SecWeekControl extends AbstractControl {
    private Node ndSecWeekViewNode;
    private ObservableList<Node> NodeComponents;
    private TableView<WeekSchedule> tvWeekView;
    private ObservableList<TableColumn<WeekSchedule, String>> DayListColumns;
    private TableColumn<WeekSchedule, String> time;
    private TableColumn<WeekSchedule, String> mon;
    private TableColumn<WeekSchedule, String> tue;
    private TableColumn<WeekSchedule, String> wed;
    private TableColumn<WeekSchedule, String> thu;
    private TableColumn<WeekSchedule, String> fri;
    private TableColumn<WeekSchedule, String> sat;
    private TableColumn<WeekSchedule, String> sun;
//    private ArrayList<Appointment> agendas;
    public SecWeekControl() {
        DayListColumns = FXCollections.observableArrayList();
        ndSecWeekViewNode = loadSecWeekView();
        NodeComponents = ((AnchorPane)((ScrollPane) ndSecWeekViewNode).getContent()).getChildren();
        initComponents();
        instantiateColumns();
        initPropertyValues();
//        tvWeekView.getColumns().get(0).setCellFactory(cellFactory);
//        insertFilteredData();
//        tvWeekView.getItems().clear();
    }



    private Node loadSecWeekView(){
        Node node = null;
        try{
            node = FXMLLoader.load(getClass().getResource("../FXMLFiles/SecWeekView.fxml"));
        }catch (Exception e){
            e.printStackTrace();
        }
        return node;
    }

    private void initComponents(){
        for (int i = 0; i <  NodeComponents.size(); i++) {
            Node node = NodeComponents.get(i);
            if(node instanceof TableView && node.getId() != null)
                if(node.getId().equals("tvWeekView")) {
                    tvWeekView = (TableView<WeekSchedule>) node;
                    tvWeekView.getSelectionModel().setCellSelectionEnabled(true);
                }
            //add more for additional components
        }
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

    public Node getNdSecWeekViewNode() {
        return ndSecWeekViewNode;
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
                   return "(Available)";
                } else if (a.length == 1) {
                    Unavailable agenda = (Unavailable) data.get(DayofWeek).get(Integer.parseInt(a[a.length - 1]));
                    return "Dr. " + agenda.getDoctorName() + " - " + "Available";
                }
            }
        }
        return "";
    }

}
