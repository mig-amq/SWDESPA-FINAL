package udc.client.regular.FXMLControllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import udc.client.regular.Controller.ClientSuperController;
import udc.objects.time.concrete.Agenda;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DoctorCalWeekController extends ClientSuperController implements Initializable {

    @FXML private TableView<?> weekTable;
    @FXML private TableColumn<?, ?> time;
    @FXML private TableColumn<?, ?> mon;
    @FXML private TableColumn<?, ?> tue;
    @FXML private TableColumn<?, ?> wed;
    @FXML private TableColumn<?, ?> thu;
    @FXML private TableColumn<?, ?> fri;
    @FXML private TableColumn<?, ?> sat;
    @FXML private TableColumn<?, ?> sun;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Override
    public void insertFilterData(ArrayList<Agenda> data) {

    }

    @Override
    public void update() {

    }
}
