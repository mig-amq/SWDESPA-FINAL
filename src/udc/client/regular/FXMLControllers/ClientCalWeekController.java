package udc.client.regular.FXMLControllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import udc.client.regular.Controller.ClientSuperController;
import udc.client.regular.Controller.WeekSchedule;
import udc.objects.time.concrete.Agenda;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ClientCalWeekController extends ClientSuperController implements Initializable {

    @FXML private TableView<WeekSchedule> weekTable;
    @FXML private TableColumn<WeekSchedule, ?> time;
    @FXML private TableColumn<WeekSchedule, ?> mon;
    @FXML private TableColumn<WeekSchedule, ?> tue;
    @FXML private TableColumn<WeekSchedule, ?> wed;
    @FXML private TableColumn<WeekSchedule, ?> thu;
    @FXML private TableColumn<WeekSchedule, ?> fri;
    @FXML private TableColumn<WeekSchedule, ?> sat;
    @FXML private TableColumn<WeekSchedule, ?> sun;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setCellTable();
    }

    @Override
    public void insertFilterData(ArrayList<Agenda> data) {

    }

    private void setCellTable() {
        time.setCellValueFactory(new PropertyValueFactory<>("time"));
        mon.setCellValueFactory(new PropertyValueFactory<>("mon"));
        tue.setCellValueFactory(new PropertyValueFactory<>("tue"));
        wed.setCellValueFactory(new PropertyValueFactory<>("wed"));
        thu.setCellValueFactory(new PropertyValueFactory<>("thu"));
        fri.setCellValueFactory(new PropertyValueFactory<>("fri"));
        sat.setCellValueFactory(new PropertyValueFactory<>("sat"));
        sun.setCellValueFactory(new PropertyValueFactory<>("sun"));
    }
}
