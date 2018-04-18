package udc.doctor.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class DoctorController extends SuperController implements Initializable{
    private ToggleGroup viewTypeGroup;
    private ToggleGroup dayWeekViewGroup;
    private ToggleGroup availGroup;
    private SuperController sc;

    @FXML
    private JFXRadioButton rdBtnDayView;

    @FXML
    private JFXRadioButton rdBtnWeekView;

    @FXML
    private JFXRadioButton rdBtnAgendaView;

    @FXML
    private JFXRadioButton rdBtnCalendarView;


    @FXML
    private JFXButton btnSetAvailabitility;

    @FXML
    private AnchorPane mainpane;

    @FXML
    private AnchorPane viewPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        viewTypeGroup = new ToggleGroup();
        dayWeekViewGroup = new ToggleGroup();
        availGroup = new ToggleGroup();

        rdBtnCalendarView.setSelected(true);
        rdBtnDayView.setSelected(true);
        setViewTypeAction();
        setFilterViewAction();
        rdBtnCalendarView.setToggleGroup(viewTypeGroup);
        rdBtnCalendarView.setSelected(true);
        rdBtnAgendaView.setToggleGroup(viewTypeGroup);

        rdBtnDayView.setToggleGroup(dayWeekViewGroup);
        rdBtnDayView.setSelected(true);
        rdBtnWeekView.setToggleGroup(dayWeekViewGroup);

    }


    //Delete Comment lines if done
    private void setFilterViewAction(){
        rdBtnDayView.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                /*** INSERT ACTIONS HERE ***/
                viewPane.getChildren().clear();
                if(rdBtnCalendarView.isSelected()){
                    if(rdBtnDayView.isSelected()){
                        Node node = null;
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/AppointmentsTableD.fxml"));
                            node = loader.load();
                            sc = loader.<AppointmentsTableSController>getController();
                            loader.<AppointmentsTableDController>getController().setCalendar(calendar);
                            loader.<AppointmentsTableDController>getController().setModel(model);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        viewPane.getChildren().add(node);
                    }

                    if(rdBtnWeekView.isSelected()){
                        Node node = null;
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/AppointmentsTableS.fxml"));
                            node = loader.load();
                            sc = loader.<AppointmentsTableSController>getController();
                            loader.<AppointmentsTableSController>getController().setCalendar(calendar);
                            loader.<AppointmentsTableSController>getController().setModel(model);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        viewPane.getChildren().add(node);
                    }
                }

                if(rdBtnAgendaView.isSelected()){
                    if(rdBtnDayView.isSelected()){
                        Node node = null;
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/AgendaView.fxml"));
                            node = loader.load();
                            sc = loader.<AgendaViewController>getController();
                            loader.<AgendaViewController>getController().setCalendar(calendar);
                            loader.<AgendaViewController>getController().setModel(model);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        viewPane.getChildren().add(node);
                    }

                    if(rdBtnWeekView.isSelected()){

                    }
                }
            }
        });

        rdBtnWeekView.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                /*** INSERT ACTIONS HERE ***/
                viewPane.getChildren().clear();
                if(rdBtnCalendarView.isSelected()){
                    if(rdBtnDayView.isSelected()){
                        Node node = null;
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/AppointmentsTableD.fxml"));
                            node = loader.load();
                            sc = loader.<AppointmentsTableDController>getController();
                            loader.<AppointmentsTableDController>getController().setCalendar(calendar);
                            loader.<AppointmentsTableDController>getController().setModel(model);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        viewPane.getChildren().add(node);
                    }

                    if(rdBtnWeekView.isSelected()){
                        Node node = null;
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/AppointmentsTableS.fxml"));
                            node = loader.load();
                            sc = loader.<AppointmentsTableSController>getController();
                            loader.<AppointmentsTableSController>getController().setCalendar(calendar);
                            loader.<AppointmentsTableSController>getController().setModel(model);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        viewPane.getChildren().add(node);
                    }
                }

                if(rdBtnAgendaView.isSelected()){
                    if(rdBtnDayView.isSelected()){
                        Node node = null;
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/AgendaView.fxml"));
                            node = loader.load();
                            sc = loader.<AgendaViewController>getController();
                            loader.<AgendaViewController>getController().setCalendar(calendar);
                            loader.<AgendaViewController>getController().setModel(model);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        viewPane.getChildren().add(node);
                    }

                    if(rdBtnWeekView.isSelected()){
                        
                    }
                }
            }
        });

    }



    private void setViewTypeAction(){
        rdBtnCalendarView.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                /*** INSERT ACTIONS HERE ***/
                viewPane.getChildren().clear();
                if(rdBtnCalendarView.isSelected()){
                    if(rdBtnDayView.isSelected()){
                        Node node = null;
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/AppointmentsTableD.fxml"));
                            node = loader.load();
                            sc = loader.<AppointmentsTableDController>getController();
                            loader.<AppointmentsTableDController>getController().setCalendar(calendar);
                            loader.<AppointmentsTableDController>getController().setModel(model);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        viewPane.getChildren().add(node);
                    }

                    if(rdBtnWeekView.isSelected()){
                        Node node = null;
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/AppointmentsTableS.fxml"));
                            node = loader.load();
                            sc = loader.<AppointmentsTableSController>getController();
                            loader.<AppointmentsTableSController>getController().setCalendar(calendar);
                            loader.<AppointmentsTableSController>getController().setModel(model);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        viewPane.getChildren().add(node);
                    }
                }

            }
        });

        rdBtnAgendaView.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                /*** INSERT ACTIONS HERE ***/
                viewPane.getChildren().clear();
                if(rdBtnAgendaView.isSelected()){
                    if(rdBtnDayView.isSelected()){
                        Node node = null;
                        try {

                            FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/AgendaView.fxml"));
                            node = loader.load();
                            sc = loader.<AgendaViewController>getController();
                            loader.<AgendaViewController>getController().setCalendar(calendar);
                            loader.<AgendaViewController>getController().setModel(model);
                            viewPane.getChildren().add(node);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    if(rdBtnWeekView.isSelected()){
                        Node node = null;
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/AgendaViewW.fxml"));
                            node = loader.load();
                            sc = loader.<AgendaViewWController>getController();
                            loader.<AgendaViewWController>getController().setCalendar(calendar);
                            loader.<AgendaViewWController>getController().setModel(model);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        viewPane.getChildren().add(node);
                    }
                }
            }
        });

        btnSetAvailabitility.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                /*** INSERT ACTIONS HERE ***/
                viewPane.getChildren().clear();
                rdBtnCalendarView.setSelected(false);
                rdBtnWeekView.setSelected(false);
                Node node = null;
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/AvailabilityView.fxml"));
                    node = loader.load();
                    sc = loader.<AvailabilityViewController>getController();
                    loader.<AvailabilityViewController>getController().setCalendar(calendar);
                    loader.<AvailabilityViewController>getController().setModel(model);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                viewPane.getChildren().add(node);
            }
        });
    }



    @Override
    public void update(LocalDate ldt) {
        if (sc != null)
            sc.update(ldt);
    }
}
