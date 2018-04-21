package udc.secretary.Controller;

import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import udc.Model;
import udc.objects.time.concrete.Agenda;

public class SecAppointmentControl {
    private Node secAppointmentNode;
    private ObservableList<Node> nodeComponents;
    private JFXTextField nameTextField;
    private JFXDatePicker datePicker;
    private JFXComboBox hrStartComboBox, minStartComboBox, hrEndComboBox, minEndComboBox,
                        startComboBox, endComboBox, //am or pm
                        docComboBox;
    private JFXButton btnAdd, btnCancel;
    private Model model;

    public SecAppointmentControl(Model model){
        this.model = model;
        secAppointmentNode = loadSecAppointmentNode();
        nodeComponents = ((AnchorPane) secAppointmentNode).getChildren();
        initComponents();
        datePicker.setValue(LocalDate.now());
        initActions();
    }

    public Node getSecAppointmentNode(){
        return secAppointmentNode;
    }

    private Node loadSecAppointmentNode(){
        Node node = null;
        try{
            node = FXMLLoader.load(getClass().getResource("../FXMLFiles/SecAppointmentView.fxml"));
        }catch (Exception e){
            e.printStackTrace();
        }
        return node;
    }

    private void initComponents(){
        for (int i = 0; i < nodeComponents.size(); i++){
            Node node = nodeComponents.get(i);
            if (node.getId() != null) {
                if (node.getId().equals("nameTextField"))
                    nameTextField = (JFXTextField) node;
                else if (node.getId().equals("datePicker"))
                    datePicker = (JFXDatePicker) node;
                else if (node.getId().equals("hrStartComboBox"))
                    hrStartComboBox = (JFXComboBox) node;
                else if (node.getId().equals("minStartComboBox"))
                    minStartComboBox = (JFXComboBox) node;
                else if (node.getId().equals("hrEndComboBox"))
                    hrEndComboBox = (JFXComboBox) node;
                else if (node.getId().equals("minEndComboBox"))
                    minEndComboBox = (JFXComboBox) node;
                else if (node.getId().equals("startComboBox"))
                    startComboBox = (JFXComboBox) node;
                else if (node.getId().equals("endComboBox"))
                    endComboBox = (JFXComboBox) node;
                else if (node.getId().equals("docComboBox"))
                    docComboBox = (JFXComboBox) node;
                else if (node.getId().equals("btnAdd"))
                    btnAdd = (JFXButton) node;
                else if (node.getId().equals("btnCancel"))
                    btnCancel = (JFXButton) node;
            }
        }
        initValues();
    }

    private void initValues(){
        ArrayList<String> doctors = model.getDbController().loadDoctors();
        doctors.remove(doctors.size() - 1);
        doctors.trimToSize();
        docComboBox.setItems(FXCollections.observableArrayList(doctors));

        String[] hours = new String[12];
        for (int i = 1; i < 13; i++) {
            if (i < 10)
                hours[i - 1] = "0" + Integer.toString(i);
            else
                hours[i - 1] = Integer.toString(i);
        }

        String[] mins = new String[]{"00", "30"};
        hrStartComboBox.setItems(FXCollections.observableArrayList(hours));
        hrEndComboBox.setItems(FXCollections.observableArrayList(hours));
        minStartComboBox.setItems(FXCollections.observableArrayList(mins));
        minEndComboBox.setItems(FXCollections.observableArrayList(mins));
        startComboBox.setItems(FXCollections.observableArrayList("AM", "PM"));
        endComboBox.setItems(FXCollections.observableArrayList("AM", "PM"));
        datePicker.setValue(LocalDate.now());
    }

    private void initActions(){
        btnAdd.setOnAction(event -> {
            if (allValid()){
                addWalkIn();
//                if (addWalkIn()){
//
//                } else {
//                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                    alert.setTitle("Slot Taken!");
//                    alert.setContentText("The chosen time slot has already been taken!");
//                    alert.showAndWait();
//                }
            } else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error!");
                alert.setContentText("Invalid field(s) found!");
                alert.showAndWait();
            }
        });

        btnCancel.setOnAction(event -> {
            //close window
        });
    }

    private boolean allValid(){
        //TODO: add checker if start time is after end time and fix the checking of times
        String hrS = hrStartComboBox.getSelectionModel().getSelectedItem().toString();
        String minS = minStartComboBox.getSelectionModel().getSelectedItem().toString();
        String hrE = hrEndComboBox.getSelectionModel().getSelectedItem().toString();
        String minE = minEndComboBox.getSelectionModel().getSelectedItem().toString();
        int startH = Integer.parseInt(hrS);
        int startM = Integer.parseInt(minS);
        int endH = Integer.parseInt(hrE);
        int endM = Integer.parseInt(minE);
        if (startComboBox.getSelectionModel().getSelectedItem().toString().equals("PM") && startH < 12)
            startH += 12;

        if (endComboBox.getSelectionModel().getSelectedItem().toString().equals("PM") && endH < 12)
            endH += 12;

        LocalTime startTime = LocalTime.of(startH, startM);
        LocalTime endTime = LocalTime.of(endH, endM);

        if (datePicker.getValue().isBefore(LocalDate.now()) || nameTextField.getText() == null
            || hrStartComboBox.getSelectionModel().getSelectedItem().toString() == null
            || minStartComboBox.getSelectionModel().getSelectedItem().toString() == null
            || startComboBox.getSelectionModel().getSelectedItem().toString() == null
            || hrEndComboBox.getSelectionModel().getSelectedItem().toString() == null
            || minEndComboBox.getSelectionModel().getSelectedItem().toString() == null
            || endComboBox.getSelectionModel().getSelectedItem().toString() == null
            || docComboBox.getSelectionModel().getSelectedItem().toString() == null
            || ((Integer.parseInt(hrStartComboBox.getSelectionModel().getSelectedItem().toString()) >= 1 && //12am to 6am start time
                Integer.parseInt(hrStartComboBox.getSelectionModel().getSelectedItem().toString()) <= 6 ||
                Integer.parseInt(hrStartComboBox.getSelectionModel().getSelectedItem().toString()) == 12)
                && startComboBox.getSelectionModel().getSelectedItem().toString().equals("AM"))
            || (Integer.parseInt(hrEndComboBox.getSelectionModel().getSelectedItem().toString()) >= 11 && //11+pm end time
                 endComboBox.getSelectionModel().getSelectedItem().toString().equals("PM"))
            || ((Integer.parseInt(hrEndComboBox.getSelectionModel().getSelectedItem().toString()) >= 1 && //12am to 6am end time
                Integer.parseInt(hrEndComboBox.getSelectionModel().getSelectedItem().toString()) <= 6 ||
                Integer.parseInt(hrEndComboBox.getSelectionModel().getSelectedItem().toString()) == 12)
                && endComboBox.getSelectionModel().getSelectedItem().toString().equals("AM"))
            || (Integer.parseInt(hrStartComboBox.getSelectionModel().getSelectedItem().toString()) >= 11 && //11+pm start time
                startComboBox.getSelectionModel().getSelectedItem().toString().equals("PM"))
            || (Integer.parseInt(hrStartComboBox.getSelectionModel().getSelectedItem().toString()) == 7 && //7am start time
                Integer.parseInt(minStartComboBox.getSelectionModel().getSelectedItem().toString()) == 0 &&
                startComboBox.getSelectionModel().getSelectedItem().toString().equals("AM"))
            || (Integer.parseInt(hrEndComboBox.getSelectionModel().getSelectedItem().toString()) == 7 && //7am end time
                Integer.parseInt(minEndComboBox.getSelectionModel().getSelectedItem().toString()) == 0 &&
                endComboBox.getSelectionModel().getSelectedItem().toString().equals("AM"))
            || startTime.isAfter(endTime) //start time is after end time
                )
            return false;

        return true;
    }

    //check if the slot chosen is not taken
    private void addWalkIn(){
    }
}
