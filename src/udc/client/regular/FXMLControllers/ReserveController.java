package udc.client.regular.FXMLControllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDrawer;

import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import udc.Model;
import udc.objects.time.concrete.Agenda;
import udc.objects.time.concrete.Unavailable;

public class ReserveController extends AnchorPane {

    @FXML private AnchorPane pnlTool;
    @FXML private AnchorPane close;
    @FXML private JFXDrawer drawer;
    @FXML private AnchorPane mainPanel;
    @FXML private JFXButton reserve;
    @FXML private Label lblAlert;
    @FXML private JFXDatePicker datePicker;
    @FXML private JFXComboBox<String> doctorCmb;
    @FXML private JFXComboBox<String> startHour, startMin, endHour, endMin, startM, endM;
    @FXML private Model model;

    public ReserveController(Model model) {
        try {
            this.model = model;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXMLFiles/Reserve.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {}

        setComboBox();
        initializeButtons();
    }

    public void setComboBox() {
        ObservableList<String> hourList, minList, doctorList, mList;

        hourList = FXCollections.observableArrayList();
        minList = FXCollections.observableArrayList("00", "30");
        doctorList = FXCollections.observableArrayList(model.getDbController().loadDoctors());
        mList = FXCollections.observableArrayList("AM", "PM");

        for (int i = 1; i < 13; i++) {
            if(i < 10)
                hourList.add("0"+i);
            else
                hourList.add((""+i));
        }

        startHour.setItems(hourList);
        endHour.setItems(hourList);
        startMin.setItems(minList);
        endMin.setItems(minList);
        doctorCmb.setItems(doctorList);
        startM.setItems(mList);
        endM.setItems(mList);

        datePicker.setValue(LocalDate.now());

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

    public void initializeButtons() {
        close.setOnMouseClicked(event -> {
            Stage stage = (Stage) close.getScene().getWindow();
            stage.close();
        });

        reserve.setOnMouseClicked(event -> {
            if(startHour.getValue()== null || startMin.getValue() == null || startM.getValue() == null ||
                    endHour.getValue() == null || endMin.getValue() == null|| endM.getValue() == null ||
                    doctorCmb.getValue() == null) {
                Alert alert = new Alert (Alert.AlertType.ERROR);
                alert.setTitle("Invalid Input");
                alert.setHeaderText(null);
                alert.setContentText("Please Enter All Info.");
                alert.showAndWait();
            }
            else {
                LocalDate date = datePicker.getValue();
                String sTime = startHour.getValue() + ":" + startMin.getValue() + " " + startM.getValue();
                String eTime = endHour.getValue() + ":" + endMin.getValue() + " " + endM.getValue();

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                String startTemp = formatter.format(date) + " " + sTime;
                String endTemp = formatter.format(date) + " " + eTime;

                LocalDateTime startTime = Agenda.strToTime(startTemp);
                LocalDateTime endTime = Agenda.strToTime(endTemp);

                //SAME TIME
                if(startTime.isEqual(endTime)) {
                    Alert alert = new Alert (Alert.AlertType.ERROR);
                    alert.setTitle("Invalid Input");
                    alert.setHeaderText(null);
                    alert.setContentText("Same start time and end time are not allowed.");
                    alert.showAndWait();
                }
                //GREATER THAN END TIME
                else if(startTime.isAfter(endTime)) {
                    Alert alert = new Alert (Alert.AlertType.ERROR);
                    alert.setTitle("Invalid Input");
                    alert.setHeaderText(null);
                    alert.setContentText("Start time is greater than the end time.");
                    alert.showAndWait();
                }
                //LESS THAN OPERATING HOURS
                else if(startTime.toLocalTime().isBefore(LocalTime.parse("07:30"))) {
                    Alert alert = new Alert (Alert.AlertType.ERROR);
                    alert.setTitle("Invalid Input");
                    alert.setHeaderText(null);
                    alert.setContentText("Clinic opens at 7:30 AM");
                    alert.showAndWait();
                }
                //DURING OPERATING HOURS
                else if(startTime.isBefore(LocalDateTime.now())) {
                    Alert alert = new Alert (Alert.AlertType.ERROR);
                    alert.setTitle("Invalid Input");
                    alert.setHeaderText(null);
                    alert.setContentText("Time has already passed");
                    alert.showAndWait();
                }
                //GREATER THAN OPERATING HOURS
                else if(startTime.toLocalTime().isAfter(LocalTime.parse("22:30")) ||
                        endTime.toLocalTime().isAfter(LocalTime.parse("22:00"))) {
                    Alert alert = new Alert (Alert.AlertType.ERROR);
                    alert.setTitle("Invalid Input");
                    alert.setHeaderText(null);
                    alert.setContentText("Clinic closes at 10:00 PM");
                    alert.showAndWait();
                }
                else {
                    System.out.println("OKAY!!");
                    boolean canAdd = true;



                    while(startTime.isBefore(endTime) || startTime.equals(endTime)) {
                        if(isOverLap(startTime)) {
                            Alert alert = new Alert (Alert.AlertType.ERROR);
                            alert.setTitle("Invalid Input");
                            alert.setHeaderText(null);
                            alert.setContentText("Dr. " + doctorCmb.getValue() + " is not available at this time.");
                            alert.showAndWait();
                            canAdd = false;
                            break;
                        }
                        if(startTime.equals(endTime)) break;
                        startTime = startTime.plusMinutes(30);
                    }
                    if(canAdd) {
                        System.out.println("CAN ADD!");
                    }

                }


            }
        });

    }

    private boolean isOverLap(LocalDateTime candidate) {
        ArrayList<Unavailable> unavailList = null;
        try {
            unavailList = model.getDbController().getUnvailability(doctorCmb.getValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("doctors " + unavailList.size());

        for(int i = 0; i < unavailList.size(); i++) {
            Unavailable u = unavailList.get(i);

            if(u.getStartTime().toLocalDate().isEqual(candidate.toLocalDate())) {
                LocalTime startTime = u.getStartTime().toLocalTime();
                LocalTime endTime = u.getEndTime().toLocalTime().minusMinutes(1);

                if(isBetween(candidate.toLocalTime(), startTime, endTime)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isBetween(LocalTime candidate, LocalTime start, LocalTime end) {
        return !candidate.isBefore(start) && !candidate.isAfter(end);
    }

}
