package udc.doctor;

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
import java.util.ResourceBundle;

public class DoctorController implements Initializable{
    private ToggleGroup viewTypeGroup;
    private ToggleGroup dayWeekViewGroup;
    private ToggleGroup availGroup;

    @FXML
    private JFXRadioButton rdBtnDayView;

    @FXML
    private JFXRadioButton rdBtnWeekView;

    @FXML
    private JFXRadioButton rdBtnAgendaView;

    @FXML
    private JFXRadioButton rdBtnCalendarView;

    @FXML
    private JFXRadioButton rdBtnOpenSlots;

    @FXML
    private JFXRadioButton rdBtnReservedSlots;

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
        rdBtnOpenSlots.setSelected(true);
        setViewTypeAction();
        setFilterViewAction();
        setAppointmentSlotsRdBtnActions();
        rdBtnCalendarView.setToggleGroup(viewTypeGroup);
        rdBtnCalendarView.setSelected(true);
        rdBtnAgendaView.setToggleGroup(viewTypeGroup);

        rdBtnDayView.setToggleGroup(dayWeekViewGroup);
        rdBtnDayView.setSelected(true);
        rdBtnWeekView.setToggleGroup(dayWeekViewGroup);

        rdBtnOpenSlots.setToggleGroup(availGroup);
        rdBtnOpenSlots.setSelected(true);
        rdBtnReservedSlots.setToggleGroup(availGroup);
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
                            node = FXMLLoader.load(getClass().getResource("../doctor/AppointmentsTableD.fxml"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        viewPane.getChildren().add(node);
                    }

                    if(rdBtnWeekView.isSelected()){
                        Node node = null;
                        try {
                            node = FXMLLoader.load(getClass().getResource("../doctor/AppointmentsTableS.fxml"));
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
                            node = FXMLLoader.load(getClass().getResource("../doctor/AgendaView.fxml"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    if(rdBtnWeekView.isSelected()){
                        Node node = null;
                        try {
                            node = FXMLLoader.load(getClass().getResource("../doctor/AgendaViewW.fxml"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
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
                            node = FXMLLoader.load(getClass().getResource("../doctor/AppointmentsTableD.fxml"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        viewPane.getChildren().add(node);
                    }

                    if(rdBtnWeekView.isSelected()){
                        Node node = null;
                        try {
                            node = FXMLLoader.load(getClass().getResource("../doctor/AppointmentsTableS.fxml"));
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
                            node = FXMLLoader.load(getClass().getResource("../doctor/AgendaView.fxml"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        viewPane.getChildren().add(node);
                    }

                    if(rdBtnWeekView.isSelected()){
                        Node node = null;
                        try {
                            node = FXMLLoader.load(getClass().getResource("../doctor/AgendaViewW.fxml"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        viewPane.getChildren().add(node);
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
                            node = FXMLLoader.load(getClass().getResource("../doctor/AppointmentsTableD.fxml"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        viewPane.getChildren().add(node);
                    }

                    if(rdBtnWeekView.isSelected()){
                        Node node = null;
                        try {
                            node = FXMLLoader.load(getClass().getResource("../doctor/AppointmentsTableS.fxml"));
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
                            node = FXMLLoader.load(getClass().getResource("../doctor/AgendaView.fxml"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        viewPane.getChildren().add(node);
                    }

                    if(rdBtnWeekView.isSelected()){
                        Node node = null;
                        try {
                            node = FXMLLoader.load(getClass().getResource("../doctor/AgendaViewW.fxml"));
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
                    node = FXMLLoader.load(getClass().getResource("../doctor/AvailabilityView.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                viewPane.getChildren().add(node);
            }
        });
    }

    private void setAppointmentSlotsRdBtnActions(){
        rdBtnOpenSlots.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                /*** INSERT ACTIONS HERE ***/

            }
        });

        rdBtnReservedSlots.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                /*** INSERT ACTIONS HERE ***/

            }
        });
    }

}
