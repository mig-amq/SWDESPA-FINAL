package udc.secretary.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import udc.objects.time.concrete.Agenda;

import java.time.LocalDate;
import java.util.ArrayList;

public class SecWeekAgendaControl extends AbstractControl{
    private Node ndSecWeekAgendaViewNode;
    private Label lblSlots;
    private JFXListView weekAgendaList;

    public SecWeekAgendaControl(){
        ndSecWeekAgendaViewNode = loadSecWeekAgendaViewNode();
        initComponents();
        weekAgendaList.setItems(FXCollections.observableArrayList(""));
    }

    private Node loadSecWeekAgendaViewNode(){
        Node node = null;
        try{
            node = FXMLLoader.load(getClass().getResource("../FXMLFiles/SecWeekAgendaView.fxml"));
        } catch (Exception e){
            e.printStackTrace();
        }

        return node;
    }

    private void initComponents(){
        Node n = ndSecWeekAgendaViewNode.lookup("#lblSlots");
        lblSlots = (Label) n;
        n = ((AnchorPane) ndSecWeekAgendaViewNode).lookup("#agendaScroll");
        weekAgendaList = (JFXListView) ((AnchorPane) ((ScrollPane) n).getContent().lookup("#anchorAgenda")).getChildren().get(0);
    }

    public Node getNdSecWeekAgendaViewNode() {
        return ndSecWeekAgendaViewNode;
    }

    public void setLabel(LocalDate date){
        lblSlots.setText("Slots for the Week of ");
        lblSlots.setText(lblSlots.getText() + date);
    }

    public void insertFilteredData(ArrayList<ArrayList<Agenda>> data){
        //still incomplete
        weekAgendaList.setItems(FXCollections.observableArrayList(""));
    }
}
