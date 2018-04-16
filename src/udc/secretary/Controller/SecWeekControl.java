package udc.secretary.Controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import udc.objects.time.concrete.Appointment;

import java.time.LocalDate;
import java.util.ArrayList;

public class SecWeekControl extends AbstractControl {
    private Node ndSecWeekViewNode;
    private TableView<WeekSchedule> tvWeekView;
    private ObservableList<TableColumn<WeekSchedule, ?>> DayListColumns;
    private ObservableList<Node> NodeComponents;

//    private ArrayList<Appointment> agendas;
    public SecWeekControl() {
        ndSecWeekViewNode = loadSecWeekView();
        NodeComponents = ((AnchorPane)((ScrollPane) ndSecWeekViewNode).getContent()).getChildren();
        initComponents();
        DayListColumns = tvWeekView.getColumns();
        initPropertyValues();
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
                if(node.getId().equals("tvWeekView"))
                    tvWeekView = (TableView<WeekSchedule>) node;
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
            //4b. need to merge to new repo for testing 20% unsure if it will works well
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

    public void insertFilteredData(ArrayList<ArrayList<Appointment>> dt, LocalDate stDte){
        //TODO: add Unavailability to arrList when merged with new repo
        //^^ Read comment from SecDayViewControl for instructions
        tvWeekView.getItems().clear();
        int hr = 7;
        for (int i = 0; i < 30; i++) {
            String tm = getDispTime(hr, i);
            if(!isOdd(i))
                hr++;
            if(!dt.isEmpty())
                tvWeekView.getItems().add(createWeekSchedule(dt,stDte, tm));

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
    public WeekSchedule createWeekSchedule(ArrayList<ArrayList<Appointment>> dt, LocalDate stDte, String tm){
        return new WeekSchedule(tm, gtDataForDay(dt, tm, stDte), gtDataForDay(dt, tm, stDte.minusDays(-1)), // -1 = +1 day
                gtDataForDay(dt, tm, stDte.minusDays(-2)), gtDataForDay(dt, tm, stDte.minusDays(-3)),
                gtDataForDay(dt, tm, stDte.minusDays(-4)), gtDataForDay(dt, tm, stDte.minusDays(-5)),
                gtDataForDay(dt, tm, stDte.minusDays(-6)));
    }

    //same comment as above
    public String gtDataForDay(ArrayList<ArrayList<Appointment>> data, String time, LocalDate stDate) {
        int DayofWeek = stDate.getDayOfWeek().getValue() - 1;
        int index;
        if (!data.get(DayofWeek).isEmpty() && (index = getDataIndexfromList(data.get(DayofWeek), time)) > -1){
            Appointment agenda = data.get(DayofWeek).get(index);
            return "Dr. " + agenda.getDoctorName() + "\nClient: " + agenda.getClientName();
        }
        return "";
    }




}
