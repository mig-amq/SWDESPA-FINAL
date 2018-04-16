package udc.client.walkin;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
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
    private AnchorPane close, minimize;

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

    @FXML
    private JFXTextField contactField;

    WalkInModel w = new WalkInModel();

    public WalkInModel getW() {
        return w;
    }

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

        list = FXCollections.observableArrayList(model.getDbController().loadDoctors());
     //   list = FXCollections.observableArrayList("Dr. Mitch", "Dr. Shad", "Dr. Migs");
        doctorCmb.setItems(list);

    }

    public void popUp() {
        bookBtn.setOnAction(event -> {

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
                String smonth;
                String sday;
                String shour;
                String ehour;
                LocalDateTime start;
                LocalDateTime end;
 /*-------------------------------------------------------------------------------------*/
                if (startHour < 12)
                    sampm = "AM";
                else
                {
                    sampm = "PM";
                    if (startHour == 12)
                        startHour = 12;
                     else
                         startHour = (startHour - 12);
                }
                if(startHour < 10)
                    shour = "0" + startHour;
                else
                    shour = String.valueOf(startHour);


                if (endHour < 12)
                {
                    eampm = "AM";
                }
                else
                {
                    eampm = "PM";
                    if (endHour == 12)
                        endHour = 12;
                    else
                        endHour = (endHour - 12);
                }

                if(endHour < 10)
                    ehour = "0" + endHour;
                else
                    ehour = String.valueOf(endHour);

/*-------------------------------------------------------------------------------------*/

                if (startMin == 0)
                    smin = "00";

                else
                    smin = "30";

                if(endMin == 0)
                    emin = "00";
                else
                    emin = "30";

 /*-------------------------------------------------------------------------------------*/

                if (date.getMonthValue() < 10)
                    smonth = "0" + date.getMonthValue();
                else
                    smonth = String.valueOf(date.getMonthValue());

                if (date.getDayOfMonth() < 10)
                    sday = "0" + date.getDayOfMonth();
                else
                    sday = String.valueOf(date.getDayOfMonth());

/*-------------------------------------------------------------------------------------*/

                stemp = date.getYear() + "/" + smonth + "/" + sday
                        + " " + shour + ":" + smin + " " + sampm;

                etemp = date.getYear() + "/" + smonth + "/" + sday
                        + " " + ehour + ":" + emin + " " + eampm;

                start = sToTime(stemp);
                end = sToTime(etemp);

                w.setName(nameField.getText());
                w.setDate(datePicker.getValue());
                w.setStart(start);
                w.setEnd(end);
                w.setDoctor(doctorCmb.getValue());
                w.setContact(contactField.getText());

                System.out.println("name: " + nameField.getText() + "\n" +
                                    "Date: " + datePicker.getValue() + "\n" +
                                    "start: " + stemp + "\n" +
                                    "end: " + etemp + "\n" +
                                    "doctor: " + doctorCmb.getValue());


 ////////////////////////////////////////// /*save to database the information*////////////////////////////////////

                String[] splited = w.getName().split(" ");
                model.getDbController().addWalkIn(splited[0], splited[1]);
//                model.getDbController().addAppointment(start, end, 0, 0);

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                WalkInPopUpController popUp = new WalkInPopUpController(nameField.getText(), stemp, etemp, doctorCmb.getValue(), w.getContact());
                Stage child = new Stage(StageStyle.UNDECORATED);
                child.setScene(new Scene(popUp));
                child.show();

                Stage stage = (Stage) getScene().getWindow();
                stage.close();
            }
        });
    }

    public void close() {
        close.setOnMouseClicked(event -> {
            Stage stage = (Stage) close.getScene().getWindow();
            stage.close();
        });

        minimize.setOnMouseClicked(event -> {
            Stage stage = (Stage) close.getScene().getWindow();
            stage.toBack();
        });
    }

    public LocalDateTime sToTime(String time)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm a");
        LocalDateTime dateTime = LocalDateTime.parse(time, formatter);
        return dateTime;
    }

    
    @Override
    public Node getStyleableNode() {
        return null;
    }
}
