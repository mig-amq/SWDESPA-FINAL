package udc.client.regular.FXMLControllers;

import com.jfoenix.controls.JFXComboBox;
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
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DoctorAgendaDayController extends ClientSuperController implements Initializable {


    @FXML
    private ListView<String> dayList;

    @FXML
    private ComboBox<String> bDayCmbBox;

    private ObservableList<String> items;


    private void setList() throws Exception
    {
        LocalDateTime now;

        LocalDateTime startTemp;
        LocalDateTime endTemp;
        String doctor;

        ArrayList<Agenda> temp = model.getDbController().getAppointments(-1, "");
        if( calendar == null)
            now = LocalDateTime.now();
        else
            now =  calendar.getSelected().atStartOfDay();

        dayList.getItems().clear();


        items.add("TAKEN SLOTS");

            for (int i = 0; i < temp.size(); i++) {
                startTemp = temp.get(i).getStartTime();
                endTemp = temp.get(i).getEndTime();
                doctor = ((Appointment) temp.get(i)).getDoctorName();
                if ((bDayCmbBox.getValue() == null || bDayCmbBox.getValue().equals(doctor)) && (startTemp.getDayOfMonth() == now.getDayOfMonth()
                     && startTemp.getMonthValue() == now.getMonthValue() && startTemp.getDayOfYear() == now.getDayOfYear() &&
                     startTemp.getYear() == now.getYear()))
                {
                    String s = startTemp.format(DateTimeFormatter.ofPattern("hh:mm a")) + " - " +
                            endTemp.format(DateTimeFormatter.ofPattern("hh:mm a")) + " Dr. " +  doctor;
                    items.add(s);
                }
            }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        items = dayList.getItems();
        setCalendar(calendar);
    }

    public void setCmb()
    {
        ObservableList<String> list = FXCollections.observableArrayList();
        list = FXCollections.observableArrayList(model.getDbController().loadDoctors());
        bDayCmbBox.setItems(list);
        bDayCmbBox.valueProperty().addListener((observable -> {
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
