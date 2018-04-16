package udc.secretary.Controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import udc.objects.time.concrete.Appointment;

import java.io.IOException;
import java.util.ArrayList;

public class SecDayViewControl extends AbstractControl {
    private Node SecDayViewNode;
    private TableView<DaySchedule> tbView;
    private ObservableList<Node> components;

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

    public void insertFilteredData(ArrayList<Appointment> data){//ArrayList<Appointment> data
//        data = sortTime(data);
        //TODO: ADD UNAVAILABILITY DISPLAY, PLACE IT INSIDE findData method()
        tbView.getItems().clear();
        int hr = 7;
        for (int i = 0; i < 30; i++) {
            int index;
            String time = getDispTime(hr, i);
            if(!isOdd(i))
                hr++;
             if((index = getDataIndexfromList(data, time)) >= 0 ) {
                 Appointment agenda = data.get(index);
                 tbView.getItems().add(new DaySchedule(time, "Dr. " + agenda.getDoctorName() + "\nClient: " + agenda.getClientName()));
             }
            else
                tbView.getItems().add(new DaySchedule(time, ""));
        }
    }

    public Node getSecDayViewNode(){
        return SecDayViewNode;
    }


}
