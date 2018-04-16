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

    @FXML private AnchorPane mainPane, homePane, bookPane, managePane, smth;

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

    private void loadFXML(String path) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            smth.getChildren().clear();
            smth.getChildren().add(loader.load());

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
            loadFXML("../DoctorCalDay.fxml");
            bCalendarView.setSelected(true);
            bDayView.setSelected(true);
        });

        manage.setOnAction(event -> {
            managePane.toFront();
            loadFXML("../ClientCalDay.fxml");
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
                System.out.println("bdayView");
                //loadFXML("../DoctorCalDay.fxml");

            }
            else if(bWeekView.isSelected()) {
//                loadFXML("../DoctorCalWeek.fxml");
            }
        });

        bAgendaView.setOnAction(event -> {
            if(bDayView.isSelected()) {
//                loadFXML("../DoctorAgendaDay.fxml");
            }
            else if(bWeekView.isSelected()) {
//                loadFXML("../DoctorAgendaWeek.fxml");
            }
        });


        bDayView.setOnAction(event -> {
            if(bCalendarView.isSelected()) {
//                loadFXML("../DoctorCalDay.fxml");
            }
            else if(bAgendaView.isSelected()) {
//                loadFXML("../DoctorAgendaDay.fxml");
            }
        });

        bWeekView.setOnAction(event -> {
            if(bCalendarView.isSelected()) {
//                loadFXML("../DoctorCalWeek.fxml");
            }
            else if(bAgendaView.isSelected()) {
//                loadFXML("../DoctorAgendaWeek.fxml");
            }
        });

        mCalendarView.setOnAction(event -> {
            if(mDayView.isSelected()) {
//                loadFXML("../ClientCalDay.fxml");
            }
            else if(mWeekView.isSelected()) {
//                loadFXML("../ClientCalWeek.fxml");
            }
        });

        mAgendaView.setOnAction(event -> {
            if(mDayView.isSelected()) {
//                loadFXML("../ClientAgendaDay.fxml");
            }
            else if(mWeekView.isSelected()) {
//                loadFXML("../ClientAgendaWeek.fxml");
            }
        });

        mDayView.setOnAction(event -> {
            if(mCalendarView.isSelected()) {
//                loadFXML("../ClientCalDay.fxml");
            }
            else if(mAgendaView.isSelected()) {
//                loadFXML("../ClientAgendaDay.fxml");
            }
        });

        mWeekView.setOnAction(event -> {
            if(mCalendarView.isSelected()) {
//                loadFXML("../ClientCalWeek.fxml");
            }
            else if(mAgendaView.isSelected()) {
//                loadFXML("../ClientAgendaWeek.fxml");
            }
        });

        bookButton.setOnAction(event -> {

        });

        editButton.setOnAction(event -> {

        });

    }
}
