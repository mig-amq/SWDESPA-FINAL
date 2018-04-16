package udc.secretary.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import udc.Model;
import udc.customfx.calendar.Calendar;
import udc.objects.time.concrete.Appointment;
import udc.objects.time.concrete.Unavailable;
import udc.secretary.Controller.SecDayAgendaControl;
import udc.secretary.Controller.SecDayViewControl;
import udc.secretary.Controller.SecWeekControl;
import udc.secretary.Controller.WalkInControl;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class MainController {
    private AnchorPane contentPane, secViewPane;
    private Node SecretaryMainNode, secWeekView, secDayView, secDayAgendaView, secWeekAgendaView, secWalkInView;
    private Pane SecretaryMainPane;
    private ObservableList<Node> SCMpaneComponents;
    private SecWeekControl secWeekControl;
    private SecDayViewControl secDayViewControl;
    private SecDayAgendaControl secDayAgendaControl;
    private WalkInControl walkInControl;
    private JFXRadioButton rdbtnCalendarView, rdbtnAgendaView,
            rdbtnDayView, rdbtnWeekView, rdbtnAvailable, rdbtnTaken;
    private JFXComboBox cmbBoxDoctors;
    private JFXButton btnWalkIn;
    private Model model;
    private ArrayList<String> doctorList;
    private ArrayList<Appointment> agendas;
    private Calendar calendar;

    private ArrayList<Unavailable> Unavailability;

    public MainController(AnchorPane contentPane, AnchorPane pnlTool, Model model, Calendar calendar) throws Exception{
        doctorList = new ArrayList<>();
        this.model = model;
        this.calendar = calendar;
        this.contentPane = contentPane;
        this.calendar = calendar;
        SecretaryMainNode = null;
        Unavailability = model.getDbController().getUnvailability(-1);
        initContainerComponents();
        initData(pnlTool);
        initNodesChildren();
        calendar.setOnMouseClicked(event -> {

        });
        agendas = model.getDbController().getAppointments(-1, "");
        secViewPane.getChildren().setAll(secDayView);
        setDisableButtons(true);
    }

    private void appendDoctorsToList(ArrayList<String> tempList){
        for (int i = 0; i < tempList.size(); i++) {
            doctorList.add(tempList.get(i));
        }
        doctorList.remove(doctorList.size() - 1);
    }

    private void initContainerComponents(){
        secWeekControl = new SecWeekControl();
        secDayViewControl = new SecDayViewControl();
        secDayAgendaControl = new SecDayAgendaControl();
        walkInControl = new WalkInControl();
    }

    private void initNodesChildren(){
        instantiateButtons();
        secDayView = secDayViewControl.getSecDayViewNode(); //scrollpane
        secWeekView = secWeekControl.getNdSecWeekViewNode();//scrollpane
        secDayAgendaView = secDayAgendaControl.getNdSecDayAgendaViewNode();
    }

    private void initData(AnchorPane pnlTool){
        doctorList.add("All");
        ArrayList<String> tempList = model.getDbController().loadDoctors();
        initMainPane(pnlTool, tempList);
        try {
            Unavailability = model.getDbController().getUnvailability(-1);
            agendas = model.getDbController().getAppointments(-1, "");
        }catch(Exception e){
            Unavailability = null;
        }

    }

    private void initMainPane(AnchorPane pnlTool, ArrayList<String> tempList){
        try { SecretaryMainNode = FXMLLoader.load(getClass().getResource("../FXMLFiles/SecretaryMain.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        SecretaryMainNode.setLayoutX(0);
        SecretaryMainNode.setLayoutY(pnlTool.getLayoutY() + 3);
        contentPane.getChildren().setAll(SecretaryMainNode);
        SecretaryMainPane = (Pane) SecretaryMainNode;
        SCMpaneComponents = SecretaryMainPane.getChildren();
        appendDoctorsToList(tempList);
    }

    private AnchorPane getAnchorpane(ObservableList<Node> nodes, Node node) {
        for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i) instanceof AnchorPane)
                return (AnchorPane) nodes.get(i);
        }
        return null;
    }

    private void instantiateButtons() {
        for (int i = 0; i < SCMpaneComponents.size(); i++) {
            Node node = SCMpaneComponents.get(i);

            if (node.getId() != null) {
                if (node instanceof JFXRadioButton && node.getId().equals("rdbtnCalendarView"))
                    rdbtnCalendarView = (JFXRadioButton) node;
                else if (node instanceof JFXRadioButton && node.getId().equals("rdbtnAgendaView"))
                    rdbtnAgendaView = (JFXRadioButton) node;
                else if (node instanceof JFXRadioButton && node.getId().equals("rdbtnDayView"))
                    rdbtnDayView = (JFXRadioButton) node;
                else if (node instanceof JFXRadioButton && node.getId().equals("rdbtnWeekView"))
                    rdbtnWeekView = (JFXRadioButton) node;
                else if (node instanceof JFXRadioButton && node.getId().equals("rdbtnAvailable"))
                    rdbtnAvailable = (JFXRadioButton) node;
                else if (node instanceof JFXRadioButton && node.getId().equals("rdbtnTaken"))
                    rdbtnTaken = (JFXRadioButton) node;
                else if (node instanceof AnchorPane && node.getId().equals("secViewPane"))
                    secViewPane = (AnchorPane) node;
                else if (node instanceof JFXComboBox && node.getId().equals("cmbBoxDoctors"))
                    cmbBoxDoctors = (JFXComboBox) node;
                else if (node instanceof JFXButton && node.getId().equals("btnWalkIn"))
                    btnWalkIn = (JFXButton) node;
            }

        }
        cmbBoxDoctors.getItems().setAll(doctorList);

        setButtonActions();
    }

    private void setButtonActions() {
//        calendar.selectedProperty().addListener();

        rdbtnCalendarView.setOnAction(event -> {
            setDisableButtons(true);
            secViewPane.getChildren().clear();
            calendarViewCondition();
        });

        rdbtnAgendaView.setOnAction(event -> {
            setDisableButtons(false);
            secViewPane.getChildren().clear();
            secDayAgendaControl.reset();
            if (rdbtnDayView.isSelected()){
                if (rdbtnAvailable.isSelected()){
                    secViewPane.getChildren().setAll(secDayAgendaView);
                }
                else{
//                    try {
//                        secDayAgendaControl.insertFilteredData(model.getDbController().getAppointments(cmbBoxDoctors.getSelectionModel().getSelectedIndex()));
//                    } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                    secViewPane.getChildren().setAll(secDayAgendaView);
                }
            } else if (rdbtnWeekView.isSelected()) {
                if (rdbtnAvailable.isSelected()) {

                } else {

                }
            }
        });

        //filter then setAll
        rdbtnDayView.setOnAction(event -> {
            secViewPane.getChildren().clear();
            if (rdbtnCalendarView.isSelected()){
                //filter
                secDayViewControl.insertFilteredData(findData(calendar.selectedProperty().get()));
                secViewPane.getChildren().setAll(secDayView);
            } else if (rdbtnAgendaView.isSelected()){
                secDayAgendaControl.reset();
                if (rdbtnAvailable.isSelected()){
                    secViewPane.getChildren().setAll(secDayAgendaView);
                } else{
                    try {
                        secDayAgendaControl.insertFilteredData(model.getDbController().getAppointments(cmbBoxDoctors.getSelectionModel().getSelectedIndex(), ""));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    secViewPane.getChildren().setAll(secDayAgendaView);
                }
            }
        });

        rdbtnWeekView.setOnAction(event -> {
            secViewPane.getChildren().clear();
            if (rdbtnCalendarView.isSelected()) {
                secWeekControl.insertFilteredData(findWeekAgenda(), findStartingDay(calendar.getSelected()));
                secViewPane.getChildren().setAll(secWeekView);
            } else if (rdbtnAgendaView.isSelected()){
                secDayAgendaControl.reset();
                if (rdbtnAvailable.isSelected()){
                    secViewPane.getChildren().setAll(secDayAgendaView);
                } else{
                    try {
                        secDayAgendaControl.insertFilteredData(model.getDbController().getAppointments(cmbBoxDoctors.getSelectionModel().getSelectedIndex(), ""));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    secViewPane.getChildren().setAll(secDayAgendaView);
                }
            }
        });

        rdbtnAvailable.setOnAction(event -> {
            secViewPane.getChildren().clear();
            if (rdbtnAgendaView.isSelected()){
                secDayAgendaControl.reset();
                if (rdbtnDayView.isSelected()){
                    try {
                        secDayAgendaControl.insertFilteredData(model.getDbController().getAppointments(cmbBoxDoctors.getSelectionModel().getSelectedIndex(), ""));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    secViewPane.getChildren().setAll(secDayAgendaView);
                } else if (rdbtnWeekView.isSelected()){

                }
            }
        });
        //don't forget to filter data before setall
        rdbtnTaken.setOnAction(event -> {
            secViewPane.getChildren().clear();
            if (rdbtnAgendaView.isSelected()){
                secDayAgendaControl.reset();
                if (rdbtnDayView.isSelected()){
                    try {
                        secDayAgendaControl.insertFilteredData(model.getDbController().getAppointments(cmbBoxDoctors.getSelectionModel().getSelectedIndex(), ""));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    secViewPane.getChildren().setAll(secDayAgendaView);
                } else if (rdbtnWeekView.isSelected()){

                }
            }
        });

        cmbBoxDoctors.setOnAction(event -> {
            secViewPane.getChildren().clear();
            if (rdbtnCalendarView.isSelected()) {
                calendarViewCondition();
            } else if (rdbtnAgendaView.isSelected()){
                secDayAgendaControl.reset();
                if (rdbtnDayView.isSelected()){
                    if (rdbtnAvailable.isSelected()){
                        secViewPane.getChildren().setAll(secDayAgendaView);
                    } else{
                        try {
                            secDayAgendaControl.insertFilteredData(model.getDbController().getAppointments(cmbBoxDoctors.getSelectionModel().getSelectedIndex(), ""));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        secViewPane.getChildren().setAll(secDayAgendaView);
                    }
                } else if (rdbtnWeekView.isSelected()) {
                    if (rdbtnAvailable.isSelected()) {

                    } else {

                    }
                }
            }
        });

        btnWalkIn.setOnAction(event ->{
            secViewPane.getChildren().clear();

        });
    }

    public void updateData() throws Exception {
        agendas = model.getDbController().getAppointments(-1, "");
        if (rdbtnCalendarView.isSelected()) {
            if (rdbtnDayView.isSelected()) {
                if (rdbtnAvailable.isSelected()) {

                } else {

                }
            } else if (rdbtnWeekView.isSelected()) {
                if (rdbtnAvailable.isSelected()) {

                } else {

                }
            }
        } else if (rdbtnAgendaView.isSelected()){
            secDayAgendaControl.reset();
            if (rdbtnDayView.isSelected()){
                if (rdbtnAvailable.isSelected()){

                } else{
                    try {
                        secDayAgendaControl.insertFilteredData(model.getDbController().getAppointments(cmbBoxDoctors.getSelectionModel().getSelectedIndex(), ""));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    secViewPane.getChildren().setAll(secDayAgendaView);
                }
            } else if (rdbtnWeekView.isSelected()){
                if (rdbtnAvailable.isSelected()){

                } else{

                }
            }
        }
    }

    private ArrayList<Appointment> findData(LocalDate selected){
        ArrayList<Appointment> arrayList = new ArrayList<>();
        for (int i = 0; i < agendas.size(); i++) {
            Appointment agenda = agendas.get(i);
            if(isEqualDate(agenda, selected))
                arrayList.add(agenda);
        }
        return arrayList;
    }



    private String dateToString(LocalDateTime localDateTime) {
        return localDateTime.getYear() + "/" + localDateTime.getMonth() + "/" + localDateTime.getDayOfMonth();
    }

    private String dateToString(LocalDate localDateTime) {
        return localDateTime.getYear() + "/" + localDateTime.getMonth() + "/" + localDateTime.getDayOfMonth();
    }

    private String timeToString(LocalDateTime localDateTime) {
        return localDateTime.getHour() + ":" + localDateTime.getMinute();
    }

    private boolean isEqualDate(Appointment agenda, LocalDate selected){
        String sDoctorName = (String) cmbBoxDoctors.getSelectionModel().getSelectedItem();
//        System.out.println(sDoctorName == null);

        if(sDoctorName!= null) {
            System.out.println(dateToString(agenda.getStartTime()) + " startTime | selected" + dateToString(selected));
            if (sDoctorName.equals("Miguel") && sDoctorName.equals(agenda.getDoctorName().split(" | ")[0])) //mq
                return dateToString(agenda.getStartTime()).equals(dateToString(selected));
            else if(sDoctorName.equals("Mitchell") && sDoctorName.equals(agenda.getDoctorName().split(" | ")[0]))
                return dateToString(agenda.getStartTime()).equals(dateToString(selected));
            else if(sDoctorName.equals("All"))
                return dateToString(agenda.getStartTime()).equals(dateToString(selected));
//            System.out.println("Doctor: " + sDoctorName + "\nAgenda Doctor: " + agenda.getsDoctorName().split(" | ")[0]);

        }
        return false;//
    }

    private void setDisableButtons(boolean a){
        rdbtnTaken.setDisable(a);
        rdbtnAvailable.setDisable(a);
    }

    private LocalDate findStartingDay(LocalDate date){
        LocalDate tempDate = date;
        int subtract = date.getDayOfWeek().getValue() - 1;
        tempDate = date.minusDays(subtract);
        return tempDate;
    }

    private ArrayList<ArrayList<Appointment>> findWeekAgenda(){
        ArrayList<ArrayList<Appointment>> WeekAgenda = new ArrayList<>();
        LocalDate StDayofWeek = findStartingDay(calendar.getSelected());
        for (int i = 0; i < 7; i++)
            WeekAgenda.add(findData(StDayofWeek.minusDays(-i)));
        return WeekAgenda;
    }

    private void calendarViewCondition(){
        if (rdbtnDayView.isSelected()){
            secDayViewControl.insertFilteredData(findData(calendar.selectedProperty().get()));
            secViewPane.getChildren().setAll(secDayView);
        } else if (rdbtnWeekView.isSelected()){
            secWeekControl.insertFilteredData(findWeekAgenda(), findStartingDay(calendar.getSelected()));
            secViewPane.getChildren().setAll(secWeekView);
        }
    }

}