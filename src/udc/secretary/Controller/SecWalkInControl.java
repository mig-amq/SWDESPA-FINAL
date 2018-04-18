package udc.secretary.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import udc.Model;
import udc.objects.time.concrete.Agenda;
import udc.objects.time.concrete.Appointment;

import java.util.ArrayList;

public class SecWalkInControl {
    private Node secWalkInNode;
    private ObservableList<Node> nodeComponents;
    private JFXListView listWalkIn;
    private JFXButton btnApprove, btnDeny;
    private ObservableList<String> observableList;
    private Model model;

    public SecWalkInControl(Model model){
        this.model = model;
        secWalkInNode = loadWalkInView();
        nodeComponents = ((AnchorPane) secWalkInNode).getChildren();
        initComponents();
        observableList = FXCollections.observableArrayList(getWalkInAppointments());
        listWalkIn.setItems(observableList);
    }

    private Node loadWalkInView(){
        Node node = null;
        try{
            node = FXMLLoader.load(getClass().getResource("../FXMLFiles/SecWalkInView.fxml"));
        }catch (Exception e){
            e.printStackTrace();
        }
        return node;
    }

    public Node getSecWalkInNode(){
        return secWalkInNode;
    }

    private void initComponents(){
        for (int i = 0; i < nodeComponents.size(); i++){
            Node node = nodeComponents.get(i);
            if (node.getId().equals("listWalkIn"))
                listWalkIn = (JFXListView) node;
            else if (node.getId().equals("btnApprove"))
                btnApprove = (JFXButton) node;
            else if (node.getId().equals("btnDeny"))
                btnDeny = (JFXButton) node;
        }
        initActions();
    }

    private void initActions(){
        btnApprove.setOnAction(event -> {
            System.out.println("Approved");
            observableList.remove(listWalkIn.getSelectionModel().getSelectedIndex());
            listWalkIn.setItems(observableList);
        });

        btnDeny.setOnAction(event ->{
            System.out.println("Denied");
            String[] sub = listWalkIn.getSelectionModel().getSelectedItem().toString().split(" ");
            Appointment a = new Appointment();
            a.setId(Integer.parseInt(sub[0].trim()));
            model.getDbController().deleteAppointment(a);
            model.setState();
        });
    }

    public void resetList(){
        listWalkIn.setItems(FXCollections.observableArrayList(""));
    }

    public ArrayList<String> getWalkInAppointments(){
        ArrayList<String> walkIns = new ArrayList<String>();
        ArrayList<Agenda> agendas = model.getDbController().getWalkinAppointments();

        for (int i = 0; i < agendas.size(); i++){
            Appointment a = (Appointment) agendas.get(i);
            String hrS = Integer.toString(a.getStartTime().getHour());
            String minS = Integer.toString(a.getStartTime().getMinute());
            String hrE = "";
            String minE = "";
            hrE = Integer.toString(a.getEndTime().getHour());
            minE = Integer.toString(a.getEndTime().getMinute());

            String sTimeOfDay = "AM";
            String eTimeOfDay = "AM";

            if (a.getStartTime().getHour() < 10)
                hrS = "0" + hrS;

            else if (a.getStartTime().getHour() >= 12) {
                if (a.getStartTime().getHour() != 12)
                    hrS = Integer.toString(Integer.parseInt(hrS) - 12);
                sTimeOfDay = "PM";
            }

            if (a.getStartTime().getMinute() < 10)
                minS = "0" + minS;

            if (a.getEndTime().getHour() < 10)
                hrE = "0" + hrE;
            else if (a.getEndTime().getHour() >= 12) {
                if (a.getEndTime().getHour() != 12)
                    hrE = Integer.toString(Integer.parseInt(hrE) - 12);
                eTimeOfDay = "PM";
            }

            if (a.getEndTime().getMinute() < 10)
                minE = "0" + minE;

            walkIns.add(a.getId() + " " + a.getStartTime().getMonth() + "-" + a.getStartTime().getDayOfMonth() + "-" + a.getStartTime().getYear()
                    + ": " + hrS + ":" + minS + sTimeOfDay + " - " + hrE + minE + eTimeOfDay + ", Dr." + a.getDoctorName() + ", " + a.getClientName());
        }

        return walkIns;
    }
}
