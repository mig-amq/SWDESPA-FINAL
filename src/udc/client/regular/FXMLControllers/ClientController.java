package udc.client.regular.FXMLControllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import udc.Model;
import udc.client.Client;

import java.io.IOException;

public class ClientController extends AnchorPane {

    @FXML private AnchorPane mainPane, homePane, bookPane, managePane;
    @FXML private AnchorPane bookTablePane, manageTablePane;

    //    @FXML private AnchorPane dCalDay, dCalWeek, cCalDay, cCalWeek;
//    @FXML private AnchorPane cAgendaDay, cAgendaWeek, dAgendaDay, dAgendaWeek;
    @FXML private JFXButton home, book, manage;
    @FXML private JFXButton bookButton, editButton;
    @FXML private JFXRadioButton bAgendaView, bCalendarView, bDayView, bWeekView;
    @FXML private JFXRadioButton mAgendaView, mCalendarView, mDayView, mWeekView;
    @FXML private JFXComboBox<?> bDoctorCmbBox, mDoctorCmbBox;
    @FXML private ToggleGroup viewTypeGroup, filterViewGroup;
    @FXML private Client client;
    @FXML private Model model;
    @FXML private ClientSuperController clientSuperController;

    public ClientController(Client client, Model model) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXMLFiles/Client.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();

        this.client = client;
        this.model = model;
        initializeButtons();
    }

    private void loadFXML(String path, AnchorPane anchorPane) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            anchorPane.getChildren().clear();
            anchorPane.getChildren().add(loader.load());

            clientSuperController = loader.<ClientAgendaDayController>getController();
            clientSuperController.setModel(model);
        } catch(Exception e) {}
    }

    private void initializeButtons() {
        home.setOnAction(event -> {
            homePane.toFront();
        });

        book.setOnAction(event -> {
            bookPane.toFront();
            loadFXML("../FXMLFiles/DoctorCalDay.fxml", bookTablePane);
            bCalendarView.setSelected(true);
            bDayView.setSelected(true);
        });

        manage.setOnAction(event -> {
            managePane.toFront();
            loadFXML("../FXMLFiles/ClientCalDay.fxml", manageTablePane);
            mCalendarView.setSelected(true);
            mDayView.setSelected(true);
        });

        viewTypeGroup = new ToggleGroup();
        filterViewGroup = new ToggleGroup();

        bCalendarView.setToggleGroup(viewTypeGroup);
        bCalendarView.setToggleGroup(viewTypeGroup);
        bAgendaView.setToggleGroup(viewTypeGroup);
        bAgendaView.setToggleGroup(viewTypeGroup);

        bDayView.setToggleGroup(filterViewGroup);
        bDayView.setToggleGroup(filterViewGroup);
        bWeekView.setToggleGroup(filterViewGroup);
        bWeekView.setToggleGroup(filterViewGroup);

        mCalendarView.setToggleGroup(viewTypeGroup);
        mCalendarView.setToggleGroup(viewTypeGroup);
        mAgendaView.setToggleGroup(viewTypeGroup);
        mAgendaView.setToggleGroup(viewTypeGroup);

        mDayView.setToggleGroup(filterViewGroup);
        mDayView.setToggleGroup(filterViewGroup);
        mWeekView.setToggleGroup(filterViewGroup);
        mWeekView.setToggleGroup(filterViewGroup);

        bCalendarView.setOnAction(event -> {
            if(bDayView.isSelected()) {
                loadFXML("../FXMLFiles/DoctorCalDay.fxml", bookTablePane);
            }
            else if(bWeekView.isSelected()) {
                loadFXML("../FXMLFiles/DoctorCalWeek.fxml", bookTablePane);
            }
        });

        bAgendaView.setOnAction(event -> {
            if(bDayView.isSelected()) {
                loadFXML("../FXMLFiles/DoctorAgendaDay.fxml", bookTablePane);
            }
            else if(bWeekView.isSelected()) {
                loadFXML("../FXMLFiles/DoctorAgendaWeek.fxml", bookTablePane);
            }
        });


        bDayView.setOnAction(event -> {
            if(bCalendarView.isSelected()) {
                loadFXML("../FXMLFiles/DoctorCalDay.fxml", bookTablePane);
            }
            else if(bAgendaView.isSelected()) {
                loadFXML("../FXMLFiles/DoctorAgendaDay.fxml", bookTablePane);
            }
        });

        bWeekView.setOnAction(event -> {
            if(bCalendarView.isSelected()) {
                loadFXML("../FXMLFiles/DoctorCalWeek.fxml", bookTablePane);
            }
            else if(bAgendaView.isSelected()) {
                loadFXML("../FXMLFiles/DoctorAgendaWeek.fxml", bookTablePane);
            }
        });

        mCalendarView.setOnAction(event -> {
            if(mDayView.isSelected()) {
                loadFXML("../FXMLFiles/ClientCalDay.fxml", manageTablePane);
            }
            else if(mWeekView.isSelected()) {
                loadFXML("../FXMLFiles/ClientCalWeek.fxml", manageTablePane);
            }
        });

        mAgendaView.setOnAction(event -> {
            if(mDayView.isSelected()) {
                loadFXML("../FXMLFiles/ClientAgendaDay.fxml", manageTablePane);
            }
            else if(mWeekView.isSelected()) {
                loadFXML("../FXMLFiles/ClientAgendaWeek.fxml", manageTablePane);
            }
        });

        mDayView.setOnAction(event -> {
            if(mCalendarView.isSelected()) {
                loadFXML("../FXMLFiles/ClientCalDay.fxml", manageTablePane);
            }
            else if(mAgendaView.isSelected()) {
                loadFXML("../FXMLFiles/ClientAgendaDay.fxml", manageTablePane);
            }
        });

        mWeekView.setOnAction(event -> {
            if(mCalendarView.isSelected()) {
                loadFXML("../FXMLFiles/ClientCalWeek.fxml", manageTablePane);
            }
            else if(mAgendaView.isSelected()) {
                loadFXML("../FXMLFiles/ClientAgendaWeek.fxml", manageTablePane);
            }
        });

        bookButton.setOnAction(event -> {

        });

        editButton.setOnAction(event -> {

        });

    }
}