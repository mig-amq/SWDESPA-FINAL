package udc.doctor.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import udc.Model;
import udc.objects.time.concrete.Agenda;
import udc.objects.time.concrete.Available;
import udc.objects.time.concrete.Unavailable;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Content implements Initializable {
    private Model model;
    private LocalDate date;

    private ArrayList<Agenda> agendas;
    private ArrayList<Available> availability; //implement later when bored
    private Day dayView;
    private Week weekView;
    private AgendaDay agendaDayView;
    private AgendaWeek agendaWeekView;

    @FXML private JFXButton add, remove;
    @FXML private AnchorPane content;
    @FXML private JFXRadioButton day, week, agendaDay, agendaWeek;

    public Content () {
        this.setDate(LocalDate.now());
    }

    public void setAgendas(ArrayList<Agenda> agendas) {
        this.agendas = agendas;
    }

    public void setAvailability(ArrayList<Available> availability){
        this.availability = availability;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        day.setOnMouseClicked(event -> update());
        week.setOnMouseClicked(event -> update());
        agendaDay.setOnMouseClicked(event -> update());
        agendaWeek.setOnMouseClicked(event -> update());

        add.setOnAction(event -> {
            Availability availability = null;
            try {
                availability = new Availability(this.getModel());
                Stage child = new Stage(StageStyle.UNDECORATED);
                child.initOwner(add.getScene().getWindow());
                child.initModality(Modality.WINDOW_MODAL);
                child.setScene(new Scene(availability));
                availability.setStage(child);
                child.showAndWait();

            } catch (IOException e) {
                e.printStackTrace();
            }

        });
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public LocalDate getDate() {
        return date;
    }

    public Model getModel() {
        return model;
    }

    public void update () {
        this.content.getChildren().clear();
        try {
            if (day.isSelected()) {
                dayView = new Day();
                this.content.getChildren().add(dayView.getNode());
                dayView.insertFilteredData(findData(this.getDate()));
            } else if(week.isSelected()) {
                weekView = new Week(this.model.getAccount().getAppointments(), this.getDate());
                this.content.getChildren().add(weekView.getNode());
                weekView.insertFilteredData(findWeekAgenda(this.getDate()), findStartingDay(this.date));
            } else if(agendaDay.isSelected()){
                agendaDayView = new AgendaDay(this.model);
                this.content.getChildren().add(agendaDayView.getNode());
            } else if(agendaWeek.isSelected()){
                agendaWeekView = new AgendaWeek(this.model);
                this.content.getChildren().add(agendaWeekView.getNode());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /*** FROM SECRETARY ***/
    private void insertUnavailabilitytoAgendas(){
        for (int i = 0; i < availability.size() ; i++)
            agendas.add(availability.get(i));
    }

    private ArrayList<Agenda> findData(LocalDate selected){
        ArrayList<Agenda> arrayList = new ArrayList<>();
        for (int i = 0; i < agendas.size(); i++) {
            Agenda agenda = agendas.get(i);
            if(agenda.getStartTime().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")).equals(
                    selected.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
            ))
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

    private LocalDate findStartingDay(LocalDate date){
        LocalDate tempDate = date;
        int subtract = date.getDayOfWeek().getValue() - 1;
        tempDate = date.minusDays(subtract);
        return tempDate;
    }

    private ArrayList<ArrayList<Agenda>> findWeekAgenda(LocalDate ldt) {
        ArrayList<ArrayList<Agenda>> WeekAgenda = new ArrayList<>();
        LocalDate StDayofWeek = findStartingDay(ldt);
        for (int i = 0; i < 7; i++)
            WeekAgenda.add(findData(StDayofWeek.minusDays(-i)));
        return WeekAgenda;
    }

    private ArrayList<Agenda> getAvailableSlots(LocalDate selected, String doctorName){
        //TODO: fix doctor names and timeslots
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
}
