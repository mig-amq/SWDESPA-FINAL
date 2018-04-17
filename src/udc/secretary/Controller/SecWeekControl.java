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

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class SecWeekControl extends AbstractControl {
    private Node ndSecWeekViewNode;
    private TableView<WeekSchedule> tvWeekView;
    private ObservableList<TableColumn<WeekSchedule, String>> DayListColumns;
    private ObservableList<Node> NodeComponents;
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
        System.out.println(tvWeekView == null);

//        tvWeekView.getColumns().get(0).setCellFactory(cellFactory);
//        insertFilteredData();
//        tvWeekView.getItems().clear();
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

    private void initPropertyValues(){
        String[] cells = new String[]{"sTime", "sMon", "sTue", "sWed", "sThu", "sFri", "sSat", "sSun"};
        for (int i = 0; i < DayListColumns.size(); i++) {
            TableColumn col = DayListColumns.get(i);
            col.setCellValueFactory(new PropertyValueFactory<WeekSchedule, String>(cells[i]));
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

    //inserts data to table
//    private void insertAllData(){
//        /***TODO: BEFORE IMPLEMENTING THIS, CREATE OR USE THREAD FOR UPDATING GATHERED DATA***/
//        //1. Doctor's clinic open time // DONE
//        //2. create method for filtering data, filter data based from SecretaryMain.fxml filters //DONE
//        //3. Color for cells??? optional
//        //4a. repeat steps for dayview or  create abstract  sec controller? // DONE
            //4b. need to merge to new repo for testing 20% unsure if it will works well //DONE PERFECT
//    }


    //removes agenda from arraylist then update database
    private void removeData(){
        /***TODO: BEFORE IMPLEMENTING THIS, CREATE OR USE THREAD FOR UPDATING GATHERED DATA***/
        //1. just remopve from arraylist
        //2. update db
        //3. repeat steps for dayview or create abstract  sec controller?
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

    public ArrayList<Appointment> sortTime(ArrayList<Appointment>  data){
        //sort time here
        return data;
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
        if (!data.get(DayofWeek).isEmpty() && (index = getDataIndexfromList(data.get(DayofWeek), time)) > -1){
            Agenda agenda = data.get(DayofWeek).get(index);
            return "Dr. " + ((Appointment)agenda).getDoctorName() + "\nClient: " + ((Appointment)agenda).getClientName();
        }
        return "";
    }

    private void setColumnCellFactory(TableColumn<WeekSchedule, String> a, int b){
        a.setCellFactory(column -> {
            return new TableCell<>() {
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    System.out.println(getIndex() + " " + item);
                    if (empty || item == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        if (item.contains("Dr. Miguel Quiambao")) {
                            setStyle("-fx-background-color: #42f498");
                            setText("");
//                            if(getTableView().getItems().get(getIndex()). != null && !getColumn(b).getCellData(getIndex() - 1).equals(item) ) //spanning cells needs fixing
                            setText(item);
                        }else if(item.contains("Dr. Mitchell Ong")){
                            setStyle("-fx-background-color: #6aa2fc");
                            setText("");
//                            if(getColumn(b).getCellData(getIndex() - 1) != null && !getColumn(b).getCellData(getIndex() - 1).equals(item) ) //spanning cells needs fixing
                                setText(item);
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

    private TableColumn<WeekSchedule, String> getColumn(int index){
        return DayListColumns.get(index);
    }


}
