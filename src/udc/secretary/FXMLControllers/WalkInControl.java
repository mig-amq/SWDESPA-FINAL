package udc.secretary.FXMLControllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import udc.Model;
import udc.objects.time.concrete.Agenda;
import udc.objects.time.concrete.Appointment;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class WalkInControl implements Initializable {
    @FXML
    private JFXButton btnApprove, btnDeny;

    @FXML
    private JFXListView listWalkIn;

    private Model model;

    private ObservableList<String> observableList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
