package udc.secretary.FXMLControllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class  SecretaryController implements Initializable {

    private ToggleGroup viewTypeGroup;
    private ToggleGroup dayWeekViewGroup;
    private ToggleGroup availGroup;

    @FXML
    private Pane secMainPane;
    @FXML
    private AnchorPane secViewPane;
    @FXML
    private JFXRadioButton rdbtnCalendarView, rdbtnAgendaView, rdbtnDayView, rdbtnWeekView, rdbtnAvailable, rdbtnTaken;
    @FXML
    private JFXComboBox<String> cmbBoxDoctors;
    @FXML
    private JFXButton btnWalkIn;

    public AnchorPane getSecViewPane(){
        return secViewPane;
    }
    private Node secWeekView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        viewTypeGroup = new ToggleGroup();
        dayWeekViewGroup = new ToggleGroup();
        availGroup = new ToggleGroup();
        rdbtnCalendarView.setToggleGroup(viewTypeGroup);
        rdbtnAgendaView.setToggleGroup(viewTypeGroup);

        rdbtnDayView.setToggleGroup(dayWeekViewGroup);
        rdbtnDayView.setSelected(true);
        rdbtnWeekView.setToggleGroup(dayWeekViewGroup);

        rdbtnAvailable.setToggleGroup(availGroup);
        rdbtnAvailable.setSelected(true);
        rdbtnTaken.setToggleGroup(availGroup);

    }
}
