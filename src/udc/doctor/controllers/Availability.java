package udc.doctor.controllers;

import com.jfoenix.controls.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import udc.Model;
import udc.objects.account.Account;
import udc.objects.enums.AgendaType;
import udc.objects.time.builders.RecurringAvailabilityBuilder;
import udc.objects.time.builders.SingleAvailabilityBuilder;
import udc.objects.time.concrete.Agenda;
import udc.objects.time.concrete.Available;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Availability extends AnchorPane {

    private Model model;
    private ArrayList<Agenda> appointments;
    private ArrayList<Available> availabilities;
    private Stage stage;

    @FXML
    private JFXButton add;

    @FXML
    private AnchorPane close;

    @FXML
    private JFXCheckBox M, T, W, H, F, S, SU;

    @FXML
    private JFXDatePicker date;

    @FXML
    private JFXComboBox<String> fh, fm, fa, th, tm, ta;

    @FXML
    private Text err;

    public Availability(Model md) throws IOException{
        this.setModel(md);
        this.setAppointments(md.getAccount().getAppointments());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/Availability.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();
    }

    public void initialize() {
        close.setOnMouseClicked(event -> stage.close());
        date.setValue(LocalDate.now());
        Callback<DatePicker, DateCell> dayCellFactory= this.getDayCellFactory();
        date.setDayCellFactory(dayCellFactory);
        add.setOnAction(event -> {
            System.out.println("henlo");
            try {
                availabilities = this.getAvailable();
                System.out.println(availabilities);
                for(int i = 0; i < availabilities.size(); i++){
                    System.out.println(availabilities.get(i).getStartTime()+" "+availabilities.get(i).getEndTime()+" "+availabilities.size());
                    this.model.getDbController().addAvailability(availabilities.get(i));
                }
            } catch (Exception e) {
                err.setText(e.getMessage());
                e.printStackTrace();
            }

            model.setState();
        });

        for (int i = 7; i <= 12; i++) {
            String str = String.format("%02d", i);
            fh.getItems().add(str);

            if (i > 7)
                th.getItems().add(str);
        }

        for (int i = 1; i <= 10; i++) {
            String str = String.format("%02d", i);
            th.getItems().add(str);

            if (i < 10)
                fh.getItems().add(str);
        }

        fm.getItems().addAll("00", "30");
        tm.getItems().addAll("00", "30");

        fa.getItems().addAll("AM", "PM");
        ta.getItems().addAll("AM", "PM");

        fh.setValue("07");
        th.setValue("08");
        fm.setValue("30");
        tm.setValue("00");
        fa.setValue("AM");
        ta.setValue("AM");
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setAppointments(ArrayList<Agenda> appointments) {
        this.appointments = appointments;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Stage getStage() {
        return stage;
    }

    public ArrayList<Agenda> getAppointments() {
        return appointments;
    }

    public Model getModel() {
        return model;
    }

    public ArrayList<Available> getAvailable () throws Exception{
        int i = 0;
        String recurring[] = new String[7];

        if (M.isSelected())
            recurring[i++] = "1";

        if (T.isSelected())
            recurring[i++] = "2";

        if (W.isSelected())
            recurring[i++] = "3";

        if (H.isSelected())
            recurring[i++] = "4";

        if (F.isSelected())
            recurring[i++] = "5";

        if (S.isSelected())
            recurring[i++] = "6";

        if (SU.isSelected())
            recurring[i++] = "7";

        String recur = String.join(",", recurring).replaceAll("null,|null", "");
        String fromStr = fh.getValue() + ":" + fm.getValue() + " " + fa.getValue();
        String toStr = th.getValue() + ":" + tm.getValue() + " " + ta.getValue();

        LocalDateTime from = date.getValue().atTime(LocalTime.parse(fromStr, DateTimeFormatter.ofPattern("hh:mm a")));
        LocalDateTime to = date.getValue().atTime(LocalTime.parse(toStr, DateTimeFormatter.ofPattern("hh:mm a")));

        ArrayList<Available> av;


        if (recur.isEmpty()) {
            SingleAvailabilityBuilder single = new SingleAvailabilityBuilder();
            av = single.buildMultiple(this.getModel().getAccount().getId(), from, to);
        }
        else{
            RecurringAvailabilityBuilder notSingle = new RecurringAvailabilityBuilder();
            String name = this.getModel().getAccount().getFirstName()+" "+this.getModel().getAccount().getLastName();
            av = notSingle.buildMultiple(from, to, name, recur.substring(0, recur.length() + 1));

        }

        if (Agenda.clashes(this.getAppointments(), from, to))
            throw new Exception("Error: An appointment has already been booked at that time range");
        else if (from.isBefore(LocalDateTime.now()) || to.isBefore(LocalDateTime.now()))
            throw new Exception("Error: Cannot set an availability in the past");
        else if (to.isBefore(from))
            throw new Exception("Error: End time cannot come before start time");
        else if (from.toLocalTime().isAfter(LocalTime.parse("09:59 PM", DateTimeFormatter.ofPattern("hh:mm a"))))
            throw new Exception("Error: Start time cannot be between 10:00 PM and 07:30 AM");
        else if (to.toLocalTime().isAfter(LocalTime.parse("10:00 PM", DateTimeFormatter.ofPattern("hh:mm a"))))
            throw new Exception("Error: End time cannot be between 10:00 PM and 08:00 AM");

        return av;
    }

    private Callback<DatePicker, DateCell> getDayCellFactory() {
        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<>() {

            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item.isBefore(LocalDate.now())) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ffffff;");
                        }
                    }
                };
            }
        };
        return dayCellFactory;
    }

}
