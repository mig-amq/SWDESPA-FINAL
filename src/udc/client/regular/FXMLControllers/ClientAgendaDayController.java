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

public class ClientAgendaDayController extends ClientSuperController implements Initializable {


    @FXML
    private ListView<String> dayList;

    private ObservableList<String> items;

    @FXML
    private ComboBox<String> mDayCmbBox;

    private void setList() throws Exception
    {
        LocalDateTime now;

        LocalDateTime startTemp;
        LocalDateTime endTemp;
        String doctor;
        int startMin;
        int endMin;
        String sMin;
        String eMin;

        ArrayList<Agenda> temp = model.getDbController().getAppointments(model.getAccount().getId(), "normal");

        if( calendar == null)
            now = LocalDateTime.now();
        else
            now =  calendar.getDate().atStartOfDay();

            for (int i = 0; i < temp.size(); i++) {
                startTemp = temp.get(i).getStartTime();
                endTemp = temp.get(i).getEndTime();
                doctor = ((Appointment) temp.get(i)).getDoctorName();

                if (startTemp.getDayOfMonth() == now.getDayOfMonth() && startTemp.getMonthValue() == now.getMonthValue() && startTemp.getDayOfYear() == now.getDayOfYear() && startTemp.getYear() == now.getYear())
                {
                    String s = startTemp.format(DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm a")) + " - " +
                            endTemp.format(DateTimeFormatter.ofPattern("hh:mm a")) + " Dr." +  doctor;
                    items.add(s);
                }
            }
    }


@Override
    public void initialize(URL location, ResourceBundle resources) {

        //items.add("Appointments");

       items = dayList.getItems();

            setCalendar(calendar);

    }

    public void setCmb()
    {
        ObservableList<String> list = FXCollections.observableArrayList();
        list = FXCollections.observableArrayList(model.getDbController().loadDoctors());
        mDayCmbBox.setItems(list);
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
