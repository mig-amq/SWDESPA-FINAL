package udc.client.regular.FXMLControllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDrawer;

import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import udc.Model;

public class ReserveController extends AnchorPane {

    @FXML private AnchorPane pnlTool;
    @FXML private AnchorPane close;
    @FXML private JFXDrawer drawer;
    @FXML private AnchorPane mainPanel;
    @FXML private JFXButton reserve;
    @FXML private Label lblAlert;
    @FXML private JFXDatePicker datePicker;
    @FXML private JFXComboBox<String> doctorCmb;
    @FXML private JFXComboBox<String> startHour, startMin, endHour, endMin;
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
        ObservableList<String> hourList = FXCollections.observableArrayList();
        ObservableList<String> minList = FXCollections.observableArrayList("00", "30");
        ObservableList<String> doctorList = FXCollections.observableArrayList(model.getDbController().loadDoctors());

        for (int i = 7; i < 21; i++) {
            if (i < 10)
                hourList.add("0" + i);
            else
                hourList.add(i + "");
        }

        startHour.setItems(hourList);
        endHour.setItems(hourList);
        startMin.setItems(minList);
        endMin.setItems(minList);
        doctorCmb.setItems(doctorList);

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

    public void initializeButtons() {

        close.setOnMouseClicked(event -> {
            Stage stage = (Stage) close.getScene().getWindow();
            stage.close();
        });

        reserve.setOnMouseClicked(event -> {
            String startTime = startHour.getValue();
            startTime += ":";
            startTime += startMin.getValue();

            String endTime = endHour.getValue();
            endTime += ":";
            endTime += endMin.getValue();

            System.out.println(startTime + " to " + endTime);
        });

    }

}
