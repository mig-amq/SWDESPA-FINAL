package udc.secretary.Controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import udc.objects.time.concrete.Agenda;
import udc.objects.time.concrete.Appointment;
import udc.objects.time.concrete.Unavailable;

import java.io.IOException;
import java.util.ArrayList;

public class SecDayViewControl extends AbstractControl {
    private Node SecDayViewNode;
    private TableView<DaySchedule> tbView;
    private ObservableList<Node> components;
    private TableColumn<DaySchedule, String> tcDoctorColumn;

    public SecDayViewControl() {
        SecDayViewNode = loadSecDayView();
        components = ((AnchorPane)((ScrollPane)(SecDayViewNode)).getContent()).getChildren();
        initComponents(components);
        initPropertValues();

//        insertFilteredData();
    }

    private Node loadSecDayView(){
        Node node = null;
        try{
            node = FXMLLoader.load(getClass().getResource("../FXMLFiles/SecDayView.fxml"));
        }catch (IOException e){
            e.printStackTrace();
        }
        return node;
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

    public void insertFilteredData(ArrayList<Agenda> data, int dayOfWeek){//ArrayList<Appointment> data, decremented dayofweek
//        data = sortTime(data);
        //TODO: ADD UNAVAILABILITY DISPLAY, PLACE IT INSIDE findData method()
        tbView.getItems().clear();
        int hr = 7;
        for (int i = 0; i < 30; i++) {
            int index;
            String time = getDispTime(hr, i);
            if(!isOdd(i))
                hr++;
             String index1 = getUnavailabilityFromList(data, time, dayOfWeek);
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

    public Node getSecDayViewNode(){
        return SecDayViewNode;
    }

    public TableView<DaySchedule> getTbView(){
        return tbView;
    }

}
