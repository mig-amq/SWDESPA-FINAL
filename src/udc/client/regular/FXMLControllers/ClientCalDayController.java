package udc.client.regular.FXMLControllers;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import udc.Model;
import udc.client.regular.Controller.ClientSuperController;
import udc.client.regular.Controller.DaySchedule;
import udc.customfx.calendar.Calendar;
import udc.objects.time.concrete.Agenda;
import udc.objects.time.concrete.Appointment;
import udc.objects.time.concrete.Unavailable;

import java.lang.reflect.Array;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ClientCalDayController extends ClientSuperController implements Initializable {

    @FXML private AnchorPane cCalDay;
    @FXML private TableView<DaySchedule> dayTable;
    @FXML private TableColumn<DaySchedule, String> time;
    @FXML private TableColumn<DaySchedule, String> doctor;
    @FXML private JFXComboBox<String> mDoctorCmbBox;


    @Override
    public void update() {

    }

    @Override
    public void insertFilterData(LocalDate selected) throws Exception {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
