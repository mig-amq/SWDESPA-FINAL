package udc.secretary.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import udc.Model;
import udc.customfx.calendar.Calendar;
import udc.objects.time.concrete.Agenda;
import udc.objects.time.concrete.Appointment;
import udc.objects.time.concrete.Available;
import udc.objects.time.concrete.Unavailable;
import udc.secretary.FXMLControllers.WalkInControl;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class MainController {
    private AnchorPane contentPane, secViewPane;
    private Node SecretaryMainNode, secWeekView, secDayView, secDayAgendaView, secWeekAgendaView;
    private Pane SecretaryMainPane;
    private ObservableList<Node> SCMpaneComponents;
    private SecWeekControl secWeekControl;
    private SecDayViewControl secDayViewControl;
    private SecDayAgendaControl secDayAgendaControl;
    private SecWeekAgendaControl secWeekAgendaControl;
    private SecWalkInControl walkInControl;
    private SecAppointmentControl secAppointmentControl;
    private JFXRadioButton rdbtnCalendarView, rdbtnAgendaView,
            rdbtnDayView, rdbtnWeekView, rdbtnAvailable, rdbtnTaken;
    private JFXComboBox cmbBoxDoctors;
    private JFXButton btnWalkIn, btnAdd;
    private Model model;
    private ArrayList<String> doctorList;
    private ArrayList<Agenda> agendas;
    private Calendar calendar;
    private ArrayList<Unavailable> Unavailability; //implement later when bored

    public MainController(AnchorPane contentPane, AnchorPane pnlTool, Model model, Calendar calendar) throws Exception{
        doctorList = new ArrayList<>();
        this.model = model;
        this.calendar = calendar;
        this.contentPane = contentPane;
        this.calendar = calendar;
        SecretaryMainNode = null;
        initContainerComponents();
        initData(pnlTool);
        initNodesChildren();
        addAction();
        secViewPane.getChildren().setAll(secDayView);
        setDisableButtons(true);
    }

    private void appendDoctorsToList(ArrayList<String> tempList){
        for (int i = 0; i < tempList.size(); i++) {
            doctorList.add("Dr. " + tempList.get(i));
        }
        doctorList.remove(doctorList.size() - 1);
    }

    private void initContainerComponents(){
        secWeekControl = new SecWeekControl();
        secDayViewControl = new SecDayViewControl();
        secDayAgendaControl = new SecDayAgendaControl(model);
        secWeekAgendaControl = new SecWeekAgendaControl();
    }

    private void initNodesChildren(){
        instantiateButtons();
        secDayView = secDayViewControl.getSecDayViewNode(); //scrollpane
        secWeekView = secWeekControl.getNdSecWeekViewNode();//scrollpane
        secDayAgendaView = secDayAgendaControl.getNdSecDayAgendaViewNode();
        secWeekAgendaView = secWeekAgendaControl.getNdSecWeekAgendaViewNode();
    }

    private void initData(AnchorPane pnlTool){
        doctorList.add("All");
        ArrayList<String> tempList = model.getDbController().loadDoctors();
        initMainPane(pnlTool, tempList);
        try {
            agendas = model.getDbController().getAppointments(-1, "");
            Unavailability = model.getDbController().getUnvailability(-1);
            for (int i = 0; i < Unavailability.size(); i++) {
                Unavailable a = Unavailability.get(i);
                Unavailability.get(i).setDoctorName(doctorList.get(a.getId()).substring(4));
            }
            insertUnavailabilitytoAgendas();
        }catch(Exception e){
            e.printStackTrace();
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
                else if (node instanceof JFXButton && node.getId().equals("btnAdd"))
                    btnAdd = (JFXButton) node;
            }

        }
        cmbBoxDoctors.getItems().setAll(doctorList); // 0 = all  1 = doctor id of doctor1 2 = doctor id of doctor 2
        cmbBoxDoctors.getSelectionModel().select(0);
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
            agendaViewCondition();
        });

        //filter then setAll
        rdbtnDayView.setOnAction(event -> {
            secViewPane.getChildren().clear();
            if (rdbtnCalendarView.isSelected()){
                //filter
                secDayViewControl.insertFilteredData(findData(calendar.selectedProperty().get()));
                secViewPane.getChildren().setAll(secDayView);
            } else if (rdbtnAgendaView.isSelected()){
                agendaViewCondition();
            }
        });

        rdbtnWeekView.setOnAction(event -> {
            secViewPane.getChildren().clear();
            if (rdbtnCalendarView.isSelected()) {
                secWeekControl.insertFilteredData(findWeekAgenda(), findStartingDay(calendar.getSelected()));
                secViewPane.getChildren().setAll(secWeekView);
            } else if (rdbtnAgendaView.isSelected()){
                agendaViewCondition();
            }
        });

        rdbtnAvailable.setOnAction(event -> {
            agendaViewCondition();
        });

        //don't forget to filter data before setall
        rdbtnTaken.setOnAction(event -> {
            agendaViewCondition();
        });

        cmbBoxDoctors.setOnAction(event -> {
            secViewPane.getChildren().clear();
            if (rdbtnCalendarView.isSelected()) {
                calendarViewCondition();
            } else if (rdbtnAgendaView.isSelected()){
                agendaViewCondition();
            }
        });

        btnWalkIn.setOnAction(event ->{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXMLFiles/SecWalkInView.fxml"));
            walkInControl = new SecWalkInControl(model);
            loader.setController(walkInControl);
            loader.setRoot(walkInControl.getSecWalkInNode());
            Parent root;
            try {
                root = (Parent) walkInControl.getSecWalkInNode();
                Stage stage = new Stage();
                stage.setTitle("Pending Walk-Ins");
                stage.setScene(new Scene(root, 587, 620));
                stage.show();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        });

        btnAdd.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXMLFiles/SecAppointmentView.fxml"));
            secAppointmentControl = new SecAppointmentControl(model);
            loader.setController(secAppointmentControl);
            loader.setRoot(secAppointmentControl.getSecAppointmentNode());
            Parent root;
            try{
                root = (Parent) secAppointmentControl.getSecAppointmentNode();
                Stage stage = new Stage();
                stage.setTitle("New Walk-in");
                stage.setScene(new Scene(root, 482, 524));
                stage.show();
            } catch(Exception e){
                e.printStackTrace();
            }
        });
    }

    private void insertUnavailabilitytoAgendas(){
        for (int i = 0; i < Unavailability.size() ; i++)
            agendas.add(Unavailability.get(i));
    }

    private void matchDoctorNametoID() {
        for (int i = 0; i < Unavailability.size(); i++) {
            Unavailable a = Unavailability.get(i);
            Unavailability.get(i).setDoctorName(doctorList.get(a.getId()).substring(4));
        }
    }

    public void updateData() throws Exception {
        agendas = model.getDbController().getAppointments(-1, "");
        Unavailability = model.getDbController().getUnvailability(-1);
       matchDoctorNametoID();
        insertUnavailabilitytoAgendas();

        if (rdbtnCalendarView.isSelected()) {
            calendarViewCondition();
        } else if (rdbtnAgendaView.isSelected()){
            agendaViewCondition();
        }
    }

    private ArrayList<Agenda> findData(LocalDate selected){
        ArrayList<Agenda> arrayList = new ArrayList<>();
        for (int i = 0; i < agendas.size(); i++) {
            Agenda agenda = agendas.get(i);
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

    private boolean isEqualDate(Agenda agenda, LocalDate selected){
        String sDoctorName = (String) cmbBoxDoctors.getSelectionModel().getSelectedItem();
        if(sDoctorName != null && !sDoctorName.equals("All"))
            sDoctorName = sDoctorName.substring(4);

        if(sDoctorName!= null && agenda instanceof Appointment) {
            if (sDoctorName.equals("Miguel Quiambao") && sDoctorName.equals(((Appointment)agenda).getDoctorName())) //mq
                return dateToString(agenda.getStartTime()).equals(dateToString(selected));
            else if(sDoctorName.equals("Mitchell Ong") && sDoctorName.equals(((Appointment) agenda).getDoctorName()))
                return dateToString(agenda.getStartTime()).equals(dateToString(selected));
            else if(sDoctorName.equals("All"))
                return dateToString(agenda.getStartTime()).equals(dateToString(selected));
        }else if(sDoctorName != null && agenda instanceof Unavailable){
            if (sDoctorName.equals("Miguel Quiambao") && sDoctorName.equals(((Unavailable)agenda).getDoctorName())) //mq
                return dateToString(agenda.getStartTime()).equals(dateToString(selected));
            else if(sDoctorName.equals("Mitchell Ong") && sDoctorName.equals(((Unavailable) agenda).getDoctorName()))
                return dateToString(agenda.getStartTime()).equals(dateToString(selected));
            else if(sDoctorName.equals("All"))
                return dateToString(agenda.getStartTime()).equals(dateToString(selected));
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

    private ArrayList<ArrayList<Agenda>> findWeekAgenda(){
        ArrayList<ArrayList<Agenda>> WeekAgenda = new ArrayList<>();
        LocalDate StDayofWeek = findStartingDay(calendar.getSelected());
        for (int i = 0; i < 7; i++)
            WeekAgenda.add(findData(StDayofWeek.minusDays(-i)));
        return WeekAgenda;
    }

    public void calendarViewCondition(){
        if (rdbtnDayView.isSelected()){
            secDayViewControl.insertFilteredData(findData(calendar.selectedProperty().get()));
            setColumnName((String) cmbBoxDoctors.getSelectionModel().getSelectedItem());
            secViewPane.getChildren().setAll(secDayView);
        } else if (rdbtnWeekView.isSelected()){
            secWeekControl.insertFilteredData(findWeekAgenda(), findStartingDay(calendar.getSelected()));
            secViewPane.getChildren().setAll(secWeekView);
        }
    }

    private void addAction(){

    }

    private void setColumnName(String name){
        secDayViewControl.getTbView().getColumns().get(secDayViewControl.getTbView().getColumns().size()-1).setText(name);
    }

    public void agendaViewCondition(){
        if (rdbtnDayView.isSelected()){
            secDayAgendaControl.setLabel(calendar.selectedProperty().get());
            if (rdbtnAvailable.isSelected()){
                secDayAgendaControl.setRemoveButtonDisabled(true);
                secDayAgendaControl.insertFilteredData(getAvailableSlots(calendar.selectedProperty().get(), cmbBoxDoctors.getSelectionModel().getSelectedItem().toString()), calendar.getSelected());
                secViewPane.getChildren().setAll(secDayAgendaView);
            } else{
                secDayAgendaControl.setRemoveButtonDisabled(false);
                try {
                    secDayAgendaControl.insertFilteredData(model.getDbController().getAppointments(-1, ""), calendar.getSelected());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                secViewPane.getChildren().setAll(secDayAgendaView);
            }
        } else if (rdbtnWeekView.isSelected()){
            secWeekAgendaControl.setLabel(calendar.selectedProperty().get());
            if (rdbtnAvailable.isSelected()){
                secViewPane.getChildren().setAll(secWeekAgendaView);
            } else{
                secWeekAgendaControl.insertFilteredData(findWeekAgenda());
                secViewPane.getChildren().setAll(secWeekAgendaView);
            }
        }
    }

    public boolean isCalendarRdBtnSelected(){
        return rdbtnCalendarView.isSelected();
    }

    public boolean isAgendaRdBtnSelected(){
        return rdbtnAgendaView.isSelected();
    }

    //old way of getting available slots
    private ArrayList<Agenda> getAvailableSlots(LocalDate selected, String doctorName){
        //TODO: fix this to reflect the new way to get available slots
        ArrayList<Agenda> availableSlots = new ArrayList<Agenda>();
        int hr = 7;
        int min;
        for (int i = 0; i < 30; i++){
            if (i % 2 != 0){
                hr++;
                min = 0;
            }
            else
                min = 30;

            Available a = new Available();
            a.setStartTime(LocalDateTime.of(selected, LocalTime.of(hr, min)));
            if (!doctorName.equalsIgnoreCase("All"))
                a.setDoctorName(doctorName.substring(4));
            availableSlots.add(a);
        }

        try {
            if (!doctorName.equalsIgnoreCase("All")) {
                ArrayList<Unavailable> unavailable = model.getDbController().getUnvailability(-1);
                for (int i = 0; i < availableSlots.size(); i++) {
                    for (int j = 0; j < unavailable.size(); j++) {
                        if (availableSlots.get(i).getStartTime().equals(unavailable.get(j).getStartTime())
                                || (availableSlots.get(i).getStartTime().toLocalTime().isAfter(unavailable.get(j).getStartTime().toLocalTime())
                                && availableSlots.get(i).getStartTime().toLocalTime().isBefore(unavailable.get(j).getEndTime().toLocalTime()))){
                            availableSlots.remove(i);

                        }
                    }
                }
                availableSlots.trimToSize();

                ArrayList<Agenda> appointments = findData(selected); //returns data for the day selected
                for (int i = 0; i < availableSlots.size(); i++){
                    for (int j = 0; j < appointments.size(); j++){
                        if (availableSlots.get(i).getStartTime().toLocalTime().equals(appointments.get(j).getStartTime().toLocalTime())
                                || (availableSlots.get(i).getStartTime().toLocalTime().isAfter(appointments.get(j).getStartTime().toLocalTime()) //start time of the available slot is after the start time of the appointment and before the end time of the appointment iremove mo
                                && availableSlots.get(i).getStartTime().toLocalTime().isBefore(appointments.get(j).getEndTime().toLocalTime()))
                                && !(appointments.get(j) instanceof Unavailable)
                                /*&& doctorName.substring(4).equals(((Appointment) appointments.get(j)).getDoctorName())*/){
                            availableSlots.remove(i);

                        }
                    }
                }
                //|| availableSlots.get(i).getStartTime().toLocalTime().isBefore(((Appointment) appointments.get(i)).getEndTime().toLocalTime())

                availableSlots.trimToSize();
            } else{
                ArrayList<Unavailable> unavailable = model.getDbController().getUnvailability(-1);

                for (int i = 0; i < availableSlots.size(); i++)
                    for (int j = 0; j < unavailable.size(); j++)
                        if (availableSlots.get(i).getStartTime().equals(unavailable.get(j).getStartTime())
                                || (availableSlots.get(i).getStartTime().toLocalTime().isAfter(unavailable.get(j).getStartTime().toLocalTime())
                                && availableSlots.get(i).getStartTime().toLocalTime().isBefore(unavailable.get(j).getEndTime().toLocalTime()))){
                            availableSlots.remove(i);
                        }
                availableSlots.trimToSize();

                ArrayList<Agenda> appointments = findData(selected);
                for (int i = 0; i < availableSlots.size(); i++)
                    for (int j = 0; j < appointments.size(); j++)
                        if (availableSlots.get(i).getStartTime().equals(appointments.get(j).getStartTime())
                                || (availableSlots.get(i).getStartTime().toLocalTime().isAfter(appointments.get(j).getStartTime().toLocalTime())
                                && availableSlots.get(i).getStartTime().toLocalTime().isBefore(appointments.get(j).getEndTime().toLocalTime()))){
                            availableSlots.remove(i);
                        }
                availableSlots.trimToSize();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return availableSlots;
    }

//    NEW IMPLEMENTATION OF getAvailableSlots()
//    private ArrayList<Agenda> getAvailableSlots(LocalDate selected, int doctor_id){
//        if (doctor_id > 0)
//              ArrayList<Agenda> availableSlots = model.getDbController().getUnvailability(doctor_id); //returns availability of that doctor
    //    else doctor_id = -1, getUnvailability(doctor_id);
//        //now you have the available slots, get the appointment slots of all doctors? then check all pag may nagequal start time, tanggalin mo yung availability na yun
//        //after the loop ok na return it
//    }
}