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
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import udc.Model;
import udc.database.DataBaseController;
import udc.objects.time.concrete.Agenda;

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

    @FXML
    private JFXComboBox<String> startampmCmb;

    @FXML
    private JFXComboBox<String> endampmCmb;

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
        for (int i = 1; i < 13; i++)
                list.add(i + "");

        starthourCmb.setItems(list);
        endhourCmb.setItems(list);

        list = FXCollections.observableArrayList("00", "30");
        startminCmb.setItems(list);
        endminCmb.setItems(list);

        list = FXCollections.observableArrayList("am", "pm");
        startampmCmb.setItems(list);
        endampmCmb.setItems(list);

        list = FXCollections.observableArrayList(model.getDbController().loadDoctors());
     //   list = FXCollections.observableArrayList("Dr. Mitch", "Dr. Shad", "Dr. Migs");
        doctorCmb.setItems(list);

        datePicker.setValue(LocalDate.now());
        datePicker.setShowWeekNumbers(true);

        Callback<DatePicker, DateCell> dayCellFactory= this.getDayCellFactory();
        datePicker.setDayCellFactory(dayCellFactory);

    }

    private Callback<DatePicker, DateCell> getDayCellFactory() {
        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<>() {

            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item.isBefore(LocalDate.now())) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ffffff;");
                        }
                    }
                };
            }
        };
        return dayCellFactory;
    }

    public void popUp() {
        bookBtn.setOnAction(event -> {

            String stimeTemp;
            String etimeTemp;
            String stemp;
            String etemp;
            int startHour;
            int endHour;
            int startMin;
            int endMin;

            int sHour;
            int eHour;
            String sampm = null;
            String eampm = null;

            LocalDateTime now = LocalDateTime.now();

            LocalDate date = datePicker.getValue();
            stimeTemp = starthourCmb.getValue();
            stimeTemp += ":" + startminCmb.getValue();
            etimeTemp = endhourCmb.getValue();
            etimeTemp += ":" + endminCmb.getValue();

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

                if (startampmCmb.getValue().equals("pm"))
                {
                    if (startHour == 12)
                        startHour = 12;
                    else
                        startHour = startHour + 12;

                    System.out.println("start hour: " + startHour);

                }

                if (endampmCmb.getValue().equals("pm"))
                {
                    if (endHour == 12)
                        endHour = 12;
                    else
                        endHour = endHour + 12;

                    System.out.println("end hour: " + endHour);

                }
                sampm = startampmCmb.getValue();
                eampm = endampmCmb.getValue();

            }

            if(nameField.getText() == null || stimeTemp == null || etimeTemp == null
                    || date == null || doctorCmb.getValue() == null  || contactField.getText() == null)
            {
                Alert alert = new Alert (Alert.AlertType.ERROR);
                alert.setTitle("Invalid Input");
                alert.setHeaderText(null);
                alert.setContentText("Please Enter All Info.");
                alert.showAndWait();

            }

            else if ((startHour == 12 || startHour == 1 || startHour == 2 || startHour == 3
                    || startHour == 4 || startHour == 5 || startHour == 6) && sampm.equals("am"))
            {
                Alert alert = new Alert (Alert.AlertType.ERROR);
                alert.setTitle("Invalid Input");
                alert.setHeaderText(null);
                alert.setContentText("Invalid Time.");
                alert.showAndWait();
            }

            else if ((startHour == 22 || startHour == 23) && sampm.equals("pm"))
            {
                Alert alert = new Alert (Alert.AlertType.ERROR);
                alert.setTitle("Invalid Input");
                alert.setHeaderText(null);
                alert.setContentText("Invalid Time.");
                alert.showAndWait();
            }

            else if ((endHour == 12 || endHour == 1 || endHour == 2 || endHour == 3
                    || endHour == 4 || endHour == 5 || endHour == 6) && eampm.equals("am"))
            {
                Alert alert = new Alert (Alert.AlertType.ERROR);
                alert.setTitle("Invalid Input");
                alert.setHeaderText(null);
                alert.setContentText("Invalid Time.");
                alert.showAndWait();
            }

            else if (endHour == 23 && eampm.equals("pm"))
            {
                Alert alert = new Alert (Alert.AlertType.ERROR);
                alert.setTitle("Invalid Input");
                alert.setHeaderText(null);
                alert.setContentText("Invalid Time.");
                alert.showAndWait();
            }

            else if(startHour == 7 && startMin == 00)
            {
                Alert alert = new Alert (Alert.AlertType.ERROR);
                alert.setTitle("Invalid Input");
                alert.setHeaderText(null);
                alert.setContentText("Clinic opens at 7:30 am");
                alert.showAndWait();

            }

//            else if(startHour == 22 && (startMin == 00 || startMin == 30))
//            {
//                Alert alert = new Alert (Alert.AlertType.ERROR);
//                alert.setTitle("Invalid Input");
//                alert.setHeaderText(null);
//                alert.setContentText("Clinic closes at 10:00");
//                alert.showAndWait();
//            }

            else if(endHour == 22 && endMin == 30)
            {
                Alert alert = new Alert (Alert.AlertType.ERROR);
                alert.setTitle("Invalid Input");
                alert.setHeaderText(null);
                alert.setContentText("Clinic closes at 10:00");
                alert.showAndWait();
            }


            else if(startHour == endHour && startMin == endMin) {
                Alert alert = new Alert (Alert.AlertType.ERROR);
                alert.setTitle("Invalid Input");
                alert.setHeaderText(null);
                alert.setContentText("Same start time and end time are not allowed.");
                alert.showAndWait();
                System.out.println(" ");
                System.out.println("error: same time");
            }

            else if(startHour == endHour && startMin > endMin) {
                Alert alert = new Alert (Alert.AlertType.ERROR);
                alert.setTitle("Invalid Input");
                alert.setHeaderText(null);
                alert.setContentText("End time is greater than the start time.");
                alert.showAndWait();
                System.out.println(" ");
                System.out.println("error: startmin > endmin");
            }

            else if(startHour > endHour) {
                Alert alert = new Alert (Alert.AlertType.ERROR);
                alert.setTitle("Invalid Input");
                alert.setHeaderText(null);
                alert.setContentText("End time is greater than the start time.");
                alert.showAndWait();

                System.out.println(" ");
                System.out.println("error: starthour > endhouur");
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
                         startHour = startHour - 12;
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


                start = Agenda.strToTime(stemp.toUpperCase());
                end = Agenda.strToTime(etemp.toUpperCase());

                w.setName(nameField.getText());
                w.setDate(datePicker.getValue());
                w.setStart(start);
                w.setEnd(end);
                w.setDoctor(doctorCmb.getValue());
                w.setContact(contactField.getText());

                System.out.println(w.start);


                if (now.isAfter(w.getStart()) || now.isEqual(w.getStart()))
                {
                    Alert alert = new Alert (Alert.AlertType.ERROR);
                    alert.setTitle("Invalid Input");
                    alert.setHeaderText(null);
                    alert.setContentText("That Time has already passed");
                    alert.showAndWait();
                } else
                {
                    String[] splited = w.getName().split(" ");
                    model.getDbController().addWalkIn(splited[0], splited[1]);
                    model.getDbController().addAppointment(start, end, w.getDoctor() , w.getName());

                    WalkInPopUpController popUp = new WalkInPopUpController(nameField.getText(), stemp, etemp, doctorCmb.getValue(), w.getContact());
                    Stage child = new Stage(StageStyle.UNDECORATED);
                    child.setScene(new Scene(popUp));
                    child.show();

                    Stage stage = (Stage) getScene().getWindow();
                    stage.close();
                }
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

    
    public Node getStyleableNode() {
        return null;
    }
}
