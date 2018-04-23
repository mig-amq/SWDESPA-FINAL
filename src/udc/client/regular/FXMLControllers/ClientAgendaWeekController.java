package udc.client.regular.FXMLControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import udc.Model;
import udc.client.regular.Controller.ClientSuperController;
import udc.customfx.calendar.Calendar;
import udc.objects.time.concrete.Agenda;
import udc.objects.time.concrete.Appointment;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

public class ClientAgendaWeekController extends ClientSuperController implements Initializable {
    @FXML
    private ListView<String> weekList;

    @FXML
    private ComboBox<String> mWeekCmbBox;

    private ObservableList<String> items;

    private void setList() throws Exception
    {
        LocalDateTime now;
        LocalDateTime startTemp;
        LocalDateTime endTemp;
        int weekNo;
        int weekTemp;
        String doctor;
        String s;
        WeekFields weekFTemp;

        if(calendar == null)
            now = LocalDateTime.now();
        else
            now =  calendar.getSelected().atStartOfDay();

        weekList.getItems().clear();

        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        weekNo = now.get(weekFields.weekOfWeekBasedYear());


        ArrayList<Agenda> temp = model.getDbController().getAppointments(model.getAccount().getId(), "normal");

        for (int i = 0; i < temp.size(); i++)
        {
            startTemp = temp.get(i).getStartTime();
            endTemp = temp.get(i).getEndTime();
            doctor = ((Appointment) temp.get(i)).getDoctorName();
            weekFTemp = WeekFields.of(Locale.getDefault());
            weekTemp = startTemp.get(weekFTemp.weekOfWeekBasedYear());

            if (weekNo == weekTemp && temp.get(i).getStartTime().getDayOfWeek().getValue() == 1) {
                s = temp.get(i).getStartTime().getDayOfWeek().toString() + " " + startTemp.format(DateTimeFormatter.ofPattern("hh:mm a")) + " - " +
                        endTemp.format(DateTimeFormatter.ofPattern("hh:mm a")) + " Dr. " +  doctor;
                items.add(s);
            }
        }

        for (int i = 0; i < temp.size(); i++)
        {
            startTemp = temp.get(i).getStartTime();
            endTemp = temp.get(i).getEndTime();
            doctor = ((Appointment) temp.get(i)).getDoctorName();
            weekFTemp = WeekFields.of(Locale.getDefault());
            weekTemp = startTemp.get(weekFTemp.weekOfWeekBasedYear());

            System.out.println("Today Week NO: " + weekNo + "\n" +
                    "chosen week no: " + weekTemp);

            if (weekNo == weekTemp && temp.get(i).getStartTime().getDayOfWeek().getValue() == 2) {
                s = temp.get(i).getStartTime().getDayOfWeek().toString() + " " + startTemp.format(DateTimeFormatter.ofPattern("hh:mm a")) + " - " +
                        endTemp.format(DateTimeFormatter.ofPattern("hh:mm a")) + " Dr. " +  doctor;
                items.add(s);
            }
        }

        for (int i = 0; i < temp.size(); i++)
        {
            startTemp = temp.get(i).getStartTime();
            endTemp = temp.get(i).getEndTime();
            doctor = ((Appointment) temp.get(i)).getDoctorName();
            weekFTemp = WeekFields.of(Locale.getDefault());
            weekTemp = startTemp.get(weekFTemp.weekOfWeekBasedYear());

            if (weekNo == weekTemp && temp.get(i).getStartTime().getDayOfWeek().getValue() == 3) {
                s = temp.get(i).getStartTime().getDayOfWeek().toString() + " " + startTemp.format(DateTimeFormatter.ofPattern("hh:mm a")) + " - " +
                        endTemp.format(DateTimeFormatter.ofPattern("hh:mm a")) + " Dr. " +  doctor;
                items.add(s);
            }
        }

        for (int i = 0; i < temp.size(); i++)
        {
            startTemp = temp.get(i).getStartTime();
            endTemp = temp.get(i).getEndTime();
            doctor = ((Appointment) temp.get(i)).getDoctorName();
            weekFTemp = WeekFields.of(Locale.getDefault());
            weekTemp = startTemp.get(weekFTemp.weekOfWeekBasedYear());

            if (weekNo == weekTemp && temp.get(i).getStartTime().getDayOfWeek().getValue() == 4) {
                s = temp.get(i).getStartTime().getDayOfWeek().toString() + " " + startTemp.format(DateTimeFormatter.ofPattern("hh:mm a")) + " - " +
                        endTemp.format(DateTimeFormatter.ofPattern("hh:mm a")) + " Dr. " +  doctor;
                items.add(s);
            }
        }

        for (int i = 0; i < temp.size(); i++)
        {
            startTemp = temp.get(i).getStartTime();
            endTemp = temp.get(i).getEndTime();
            doctor = ((Appointment) temp.get(i)).getDoctorName();
            weekFTemp = WeekFields.of(Locale.getDefault());
            weekTemp = startTemp.get(weekFTemp.weekOfWeekBasedYear());

            if (weekNo == weekTemp && temp.get(i).getStartTime().getDayOfWeek().getValue() == 5) {
                s = temp.get(i).getStartTime().getDayOfWeek().toString() + " " + startTemp.format(DateTimeFormatter.ofPattern("hh:mm a")) + " - " +
                        endTemp.format(DateTimeFormatter.ofPattern("hh:mm a")) + " Dr. " +  doctor;
                items.add(s);
            }
        }

        for (int i = 0; i < temp.size(); i++)
        {
            startTemp = temp.get(i).getStartTime();
            endTemp = temp.get(i).getEndTime();
            doctor = ((Appointment) temp.get(i)).getDoctorName();
            weekFTemp = WeekFields.of(Locale.getDefault());
            weekTemp = startTemp.get(weekFTemp.weekOfWeekBasedYear());

            if (weekNo == weekTemp && temp.get(i).getStartTime().getDayOfWeek().getValue() == 6) {
                s = temp.get(i).getStartTime().getDayOfWeek().toString() + " " + startTemp.format(DateTimeFormatter.ofPattern("hh:mm a")) + " - " +
                        endTemp.format(DateTimeFormatter.ofPattern("hh:mm a")) + " Dr. " +  doctor;
                items.add(s);
            }
        }

        for (int i = 0; i < temp.size(); i++)
        {
            startTemp = temp.get(i).getStartTime();
            endTemp = temp.get(i).getEndTime();
            doctor = ((Appointment) temp.get(i)).getDoctorName();
            weekFTemp = WeekFields.of(Locale.getDefault());
            weekTemp = startTemp.get(weekFTemp.weekOfWeekBasedYear());

            if (weekNo == weekTemp && temp.get(i).getStartTime().getDayOfWeek().getValue() == 7) {
                s = temp.get(i).getStartTime().getDayOfWeek().toString() + " " + startTemp.format(DateTimeFormatter.ofPattern("hh:mm a")) + " - " +
                        endTemp.format(DateTimeFormatter.ofPattern("hh:mm a")) + " Dr. " +  doctor;
                items.add(s);
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        items = weekList.getItems();
        setCalendar(calendar);
}

    public void setCmb()
    {
        ObservableList<String> list = FXCollections.observableArrayList();
        list = FXCollections.observableArrayList(model.getDbController().loadDoctors());
        mWeekCmbBox.setItems(list);
        mWeekCmbBox.valueProperty().addListener((observable -> {
            update();
        }));
    }

    @Override
    public void setModel (Model model) {
        super.setModel(model);
        try {
            setList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        setCmb();
    }

    @Override
    public void setCalendar (Calendar calendar) {
        super.setCalendar(calendar);
    }

    @Override
    public void update() {
        try {
            setList();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insertFilterData(LocalDate selected) {

    }
}
