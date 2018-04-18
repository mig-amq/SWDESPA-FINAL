package udc.client.regular.FXMLControllers;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

    private ObservableList<String> items;

    private void setList() throws Exception {
        //  items.add("00:00" + "-" + "02:30" + " " + "Dr JDC");
        // items.add("00:00" + "-" + "01:30" + " " + ":)");

        ObservableList<String> finaltemp = null;

        LocalDateTime now = LocalDateTime.now();
            ArrayList<Agenda> temp = model.getDbController().getAppointments(-1, "");

        items.add("TAKEN SLOTS");

//        int hourTime = 7;
//        int minTime = 30;
//
//        while (hourTime < 23 && minTime != 30)
//        {
//            String sHour;
//            String sMin;
//            if (hourTime < 10)
//                sHour = "0" + hourTime;
//            else
//                sHour = Integer.toString(hourTime);
//
//            if (minTime < 10)
//                sMin = "0" + minTime;
//            else
//                sMin = Integer.toString(hourTime);

            for (int i = 0; i < temp.size(); i++) {
                LocalDateTime startTemp = model.getDbController().getAppointments(-1, "").get(i).getStartTime();
                LocalDateTime endTemp = model.getDbController().getAppointments(-1, "").get(i).getEndTime();
                String doctor = ((Appointment) model.getDbController().getAppointments(-1, "").get(i)).getDoctorName();
                String eampm = temp.get(i).getEndTime().format(DateTimeFormatter.ofPattern("a"));

                {
                    int startMin = startTemp.getMinute();
                    int endMin = endTemp.getMinute();
                    String sSMin;
                    String sEMin;
                    if (startMin == 0)
                        sSMin = "00";
                    else
                        sSMin = "30";

                    if (endMin == 0)
                        sEMin = "00";
                    else
                        sEMin = "30";

                    if (startTemp.getDayOfYear() == now.getDayOfYear() && startTemp.getYear() == now.getYear()) {
                        String s = startTemp.getHour() + ":" + sSMin + " - " + endTemp.getHour() + ":" + sEMin + " " + eampm + " Dr. " + doctor;
                        items.add(s);
                    }
                }
            }

//            finaltemp.add(sHour + ":" + sMin);
//
//            if(minTime == 0)
//            {
//                minTime = 30;
//            }
//
//            else
//            {
//                minTime = 0;
//                hourTime++;
//            }
//        }

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        items = dayList.getItems();
        setModel(model);
        setCalendar(calendar);

    }

    @Override
    public void setModel (Model model) {
        super.setModel(model);

        try {
            setList();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        items = dayList.getItems();
    }
}
