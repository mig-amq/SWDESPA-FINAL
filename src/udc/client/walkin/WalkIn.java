package udc.client.walkin;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import udc.Model;
import udc.database.DataBaseController;

import javax.swing.*;

public class WalkIn extends AnchorPane {

    private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label Welcomelbl;

    @FXML
    private Label datelbl;

    @FXML
    private Label nameLbl;

    @FXML
    private JFXTextField nameField;

    @FXML
    private JFXDatePicker datePicker;

    @FXML
    private Label timeLbl;

    @FXML
    private JFXComboBox<String> starthourCmb;

    @FXML
    private JFXButton closeBtn;

    @FXML
    private JFXButton minBtn;

    @FXML
    private JFXButton bookBtn;

    @FXML
    private Label doctorLbl1;

    @FXML
    private JFXComboBox<String> doctorCmb;

    @FXML
    private JFXComboBox<String> startminCmb;

    @FXML
    private Label colonLbl;

    @FXML
    private Label toLbl;

    @FXML
    private JFXComboBox<String> endhourCmb;

    @FXML
    private JFXComboBox<String> endminCmb;

    @FXML
    private Label colonLbl2;

    DataBaseController db;

    public WalkIn(Model model)
    {
        try {
            this.model = model;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("WalkIn.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void initialize() {
        assert Welcomelbl != null : "fx:id=\"Welcomelbl\" was not injected: check your FXML file 'WalkIn.fxml'.";
        assert datelbl != null : "fx:id=\"datelbl\" was not injected: check your FXML file 'WalkIn.fxml'.";
        assert nameLbl != null : "fx:id=\"nameLbl\" was not injected: check your FXML file 'WalkIn.fxml'.";
        assert nameField != null : "fx:id=\"nameField\" was not injected: check your FXML file 'WalkIn.fxml'.";
        assert datePicker != null : "fx:id=\"datePicker\" was not injected: check your FXML file 'WalkIn.fxml'.";
        assert timeLbl != null : "fx:id=\"timeLbl\" was not injected: check your FXML file 'WalkIn.fxml'.";
        assert starthourCmb != null : "fx:id=\"starthourCmb\" was not injected: check your FXML file 'WalkIn.fxml'.";
        assert closeBtn != null : "fx:id=\"closeBtn\" was not injected: check your FXML file 'WalkIn.fxml'.";
        assert minBtn != null : "fx:id=\"minBtn\" was not injected: check your FXML file 'WalkIn.fxml'.";
        assert bookBtn != null : "fx:id=\"bookBtn\" was not injected: check your FXML file 'WalkIn.fxml'.";
        assert doctorLbl1 != null : "fx:id=\"doctorLbl1\" was not injected: check your FXML file 'WalkIn.fxml'.";
        assert doctorCmb != null : "fx:id=\"doctorCmb\" was not injected: check your FXML file 'WalkIn.fxml'.";
        assert startminCmb != null : "fx:id=\"startminCmb\" was not injected: check your FXML file 'WalkIn.fxml'.";
        assert colonLbl != null : "fx:id=\"colonLbl\" was not injected: check your FXML file 'WalkIn.fxml'.";
        assert toLbl != null : "fx:id=\"toLbl\" was not injected: check your FXML file 'WalkIn.fxml'.";
        assert endhourCmb != null : "fx:id=\"endhourCmb\" was not injected: check your FXML file 'WalkIn.fxml'.";
        assert endminCmb != null : "fx:id=\"endminCmb\" was not injected: check your FXML file 'WalkIn.fxml'.";
        assert colonLbl2 != null : "fx:id=\"colonLbl2\" was not injected: check your FXML file 'WalkIn.fxml'.";

        setComboBox();
        popUp();
        close();
    }

    public void setComboBox() {
        ObservableList<String> list = FXCollections.observableArrayList();
        for (int i = 7; i < 21; i++) {
            if (i < 10)
                list.add("0" + i);
            else
                list.add(i + "");
        }

        starthourCmb.setItems(list);
        endhourCmb.setItems(list);

        list = FXCollections.observableArrayList("00", "30");
        startminCmb.setItems(list);
        endminCmb.setItems(list);

/////////////////////////////////////*LOAD THE DOCTORS*//////////////////////////////////////////////////////////
     //   list = FXCollections.observableArrayList(db.loadDoctors());
        list = FXCollections.observableArrayList("Dr. Mitch", "Dr. Shad", "Dr. Migs");

        doctorCmb.setItems(list);



    }

    public void popUp() {
        bookBtn.setOnAction(event -> {

            WalkInModel w = new WalkInModel();

            String stimeTemp;
            String etimeTemp;

            LocalDate date = datePicker.getValue();
            stimeTemp = starthourCmb.getValue();
            stimeTemp += ":" + startminCmb.getValue();
            etimeTemp = endhourCmb.getValue();
            etimeTemp += ":" + endminCmb.getValue();

            String stemp;
            String etemp;

            int startHour;
            int endHour;
            int startMin;
            int endMin;

            int sHour;
            int eHour;
            String sampm;
            String eampm;


            if(starthourCmb.getValue() == null || startminCmb.getValue() == null
                    || endhourCmb.getValue() == null || endminCmb.getValue() == null)
            {
                startHour = 0;
                endHour = 0;
                startMin = 0;
                endMin = 0;
            }

            else
            {
                startHour = Integer.parseInt(starthourCmb.getValue());
                endHour = Integer.parseInt(endhourCmb.getValue());
                startMin = Integer.parseInt(startminCmb.getValue());
                endMin = Integer.parseInt(endminCmb.getValue());
            }

            w.setDoctor(doctorCmb.getValue());

            if(nameField.getText() == null || stimeTemp == null || etimeTemp == null
                    || date == null || doctorCmb.getValue() == null)
            {
                Alert alert = new Alert (Alert.AlertType.ERROR);
                alert.setTitle("Invalid Input");
                alert.setHeaderText(null);
                alert.setContentText("Please Enter All Info.");
                alert.showAndWait();

            }

            else if(startHour == endHour && startMin == endMin) {
                Alert alert = new Alert (Alert.AlertType.ERROR);
                alert.setTitle("Invalid Input");
                alert.setHeaderText(null);
                alert.setContentText("Same start time and end time are not allowed.");
                alert.showAndWait();
            }

            else if(startHour == endHour && startMin > endMin) {
                Alert alert = new Alert (Alert.AlertType.ERROR);
                alert.setTitle("Invalid Input");
                alert.setHeaderText(null);
                alert.setContentText("End time is greater than the start time.");
                alert.showAndWait();
            }

            else if(startHour > endHour) {
                Alert alert = new Alert (Alert.AlertType.ERROR);
                alert.setTitle("Invalid Input");
                alert.setHeaderText(null);
                alert.setContentText("End time is greater than the start time.");
                alert.showAndWait();
            }


            else {

                String smin;
                String emin;
                if (startHour < 12)
                {
                    sampm = "am";
                }
                else
                {
                    sampm = "pm";
                    if (startHour == 12)
                        startHour = 12;
                     else
                         startHour = (startHour - 12);
                }

                if (endHour < 12)
                {
                    eampm = "am";
                }
                else
                {
                    eampm = "pm";

                    if (endHour == 12)
                        endHour = 12;
                    else
                        endHour = (endHour - 12);
                }

                if (startMin == 0)
                    smin = "00";

                else
                    smin = "30";

                if(endMin == 0)
                    emin = "00";
                else
                    emin = "30";

                stemp = date.getYear() + "/" + date.getMonthValue() + "/" + date.getDayOfMonth()
                        + " " + startHour + ":" + smin + " " + sampm;

                etemp = date.getYear() + "/" + date.getMonthValue() + "/" + date.getDayOfMonth()
                        + " " + endHour + ":" + emin + " " + sampm;

                w.setName(nameField.getText());
                w.setDate(datePicker.getValue());
                w.setStart(stemp);
                w.setEnd(etemp);
                w.setDoctor(doctorCmb.getValue());

                System.out.println("name: " + nameField.getText() + "\n" +
                                    "Date: " + datePicker.getValue() + "\n" +
                                    "start: " + stemp + "\n" +
                                    "end: " + etemp + "\n" +
                                    "doctor: " + doctorCmb.getValue());

 /////////////////////////////////////////////// /*save to database the information*////////////////////////////////////

                WalkInPopUpController popUp = new WalkInPopUpController();
                Stage child = new Stage(StageStyle.UNDECORATED);
                child.setScene(new Scene(popUp));
                child.show();

                Stage stage = (Stage) getScene().getWindow();
                stage.close();


            }
        });
    }

    public void close() {
        closeBtn.setOnAction(event -> {

            Stage stage = (Stage) closeBtn.getScene().getWindow();
            stage.close();
        });
    }

    
    public Node getStyleableNode() {
        return null;
    }
}
