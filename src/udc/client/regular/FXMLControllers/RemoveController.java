package udc.client.regular.FXMLControllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDrawer;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import udc.Model;
import udc.objects.account.Account;
import udc.objects.time.concrete.Agenda;
import udc.objects.time.concrete.Appointment;

public class RemoveController extends AnchorPane {
    @FXML
    private AnchorPane pnlTool;
    @FXML
    private AnchorPane close;
    @FXML
    private JFXDrawer drawer;
    @FXML
    private AnchorPane mainPanel;
    @FXML
    private JFXButton remove;
    @FXML
    private Label lblAlert;
    @FXML
    private JFXDatePicker datePicker;
    @FXML
    private JFXComboBox<String> doctorCmb;
    @FXML
    private JFXComboBox<String> startHour, startMin, endHour, endMin, startM, endM;
    @FXML
    private Model model;

    public RemoveController(Model model) {
        try {
            this.model = model;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXMLFiles/Remove.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
        }

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
            if (i < 10)
                hourList.add("0" + i);
            else
                hourList.add(("" + i));
        }

        startHour.setItems(hourList);
        endHour.setItems(hourList);
        startMin.setItems(minList);
        endMin.setItems(minList);
        doctorCmb.setItems(doctorList);
        startM.setItems(mList);
        endM.setItems(mList);

        datePicker.setValue(LocalDate.now());
    }


    public void initializeButtons() {
        close.setOnMouseClicked(event -> {
            Stage stage = (Stage) close.getScene().getWindow();
            stage.close();
        });
        remove.setOnMouseClicked(event -> {
            if (startHour.getValue() == null || startMin.getValue() == null || startM.getValue() == null ||
                    endHour.getValue() == null || endMin.getValue() == null || endM.getValue() == null ||
                    doctorCmb.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Null Input");
                alert.setHeaderText(null);
                alert.setContentText("Please Enter All Info.");
                alert.showAndWait();
            } else {
                LocalDate date = datePicker.getValue();
                String sTime = startHour.getValue() + ":" + startMin.getValue() + " " + startM.getValue();
                String eTime = endHour.getValue() + ":" + endMin.getValue() + " " + endM.getValue();

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                String startTemp = formatter.format(date) + " " + sTime;
                String endTemp = formatter.format(date) + " " + eTime;

                LocalDateTime startTime = Agenda.strToTime(startTemp);
                LocalDateTime endTime = Agenda.strToTime(endTemp);

                Account account = model.getAccount();
                ArrayList<Agenda> myAppts = null;
                boolean found = false;

                try {
                    System.out.println("Account ID: " + account.getId());
                    myAppts = model.getDbController().getAppointments(account.getId(), "client");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                for (int i = 0; i < myAppts.size(); i++) {
                    Agenda agenda = myAppts.get(i);
                    System.out.println("agenda " + i + " start: " + agenda.getStartTime() + " end: " + agenda.getEndTime());
                    if (agenda.getStartTime().isEqual(startTime) &&
                            agenda.getEndTime().isEqual(endTime)) {
                        System.out.println("REMOVED!!");
                        System.out.println("aapt id:"+myAppts.get(i).getId());
                        model.getDbController().deleteAppointment((Appointment) agenda);
                        model.setState();
                        found = true;
                        break;
                    }
                }

                if(!found) {
                    Alert alert = new Alert (Alert.AlertType.ERROR);
                    alert.setTitle("Invalid Input");
                    alert.setHeaderText(null);
                    alert.setContentText("Appointment does not exist.");
                    alert.showAndWait();
                }
                else {
                    Stage stage = (Stage) close.getScene().getWindow();
                    stage.close();
                }
            }
        });
    }
}
