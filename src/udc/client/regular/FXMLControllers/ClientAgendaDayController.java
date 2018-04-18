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

    private void setList() throws Exception {
      //  items.add("00:00" + "-" + "02:30" + " " + "Dr JDC");
       // items.add("00:00" + "-" + "01:30" + " " + ":)");

        LocalDateTime now;

        if( calendar == null)
            now = LocalDateTime.now();
        else
            now =  calendar.getDate().atStartOfDay();
        


            ArrayList<Agenda> temp = model.getDbController().getAppointments(model.getAccount().getId(), "normal");

            for (int i = 0; i < temp.size(); i++) {
                LocalDateTime startTemp = temp.get(i).getStartTime();
                LocalDateTime endTemp = temp.get(i).getEndTime();
                String doctor = ((Appointment) temp.get(i)).getDoctorName();
                int startMin = startTemp.getMinute();
                int endMin = endTemp.getMinute();
                String sMin;
                String eMin;
                String eampm = temp.get(i).getEndTime().format(DateTimeFormatter.ofPattern("a"));

                if (startMin == 0)
                    sMin = "00";
                else
                    sMin = "30";

                if (endMin == 0)
                    eMin = "00";
                else
                    eMin = "30";


                if (startTemp.getDayOfYear() == now.getDayOfYear() && startTemp.getYear() == now.getYear())
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

    }

    @Override
    public void insertFilterData(LocalDate selected) {

    }
}
