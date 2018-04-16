package udc.secretary.Controller;

import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import udc.objects.time.concrete.Agenda;
import udc.objects.time.concrete.Appointment;

import java.util.ArrayList;

public class SecDayAgendaControl extends AbstractControl {
    private Node ndSecDayAgendaViewNode;
    private JFXListView agendaList;

    public SecDayAgendaControl(){
        ndSecDayAgendaViewNode = loadSecDayAgendaView();
        initComponents();
        agendaList.setItems(FXCollections.observableArrayList(""));
    }

    private Node loadSecDayAgendaView(){
        Node node = null;
        try{
            node = FXMLLoader.load(getClass().getResource("../FXMLFiles/SecDayAgendaView.fxml"));
        }catch (Exception e){
            e.printStackTrace();
        }
        return node;
    }

    private void initComponents(){
        Node n = ((AnchorPane) ndSecDayAgendaViewNode).lookup("#agendaScroll");
        agendaList = (JFXListView) ((AnchorPane) ((ScrollPane) n).getContent().lookup("#anchorAgenda")).getChildren().get(0);
    }

    public Node getNdSecDayAgendaViewNode(){
        return ndSecDayAgendaViewNode;
    }


    void insertFilteredData(ArrayList<Agenda> data) {
        ObservableList<String> string = FXCollections.observableArrayList();
        for (int i = 0; i < data.size(); i++)
            if(data.get(i) instanceof Appointment)
                string.add(data.get(i).getStartTime().toString() + ", Dr." + ((Appointment)data.get(i)).getDoctorName() + ", " + ((Appointment)data.get(i)).getClientName());
        agendaList.setItems(FXCollections.observableArrayList(string));
    }

    public void reset(){
        agendaList.setItems(FXCollections.observableArrayList(""));
    }
}
