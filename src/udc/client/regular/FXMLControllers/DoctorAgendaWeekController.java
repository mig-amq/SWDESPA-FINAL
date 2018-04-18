package udc.client.regular.FXMLControllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import udc.Model;
import udc.client.regular.Controller.ClientSuperController;
import udc.objects.time.concrete.Agenda;
import udc.objects.time.concrete.Appointment;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class DoctorAgendaWeekController extends ClientSuperController implements Initializable {


    @FXML
    private ListView<String> weekList;

    private ObservableList<String> items;


    private ObservableList<Agenda> mon;
    private ObservableList<Agenda> tues;
    private ObservableList<Agenda> wed;
    private ObservableList<Agenda> thurs;
    private ObservableList<Agenda> fri;
    private ObservableList<Agenda> sat;
    private ObservableList<Agenda> sun;

    private void setList() throws Exception {
        //  items.add("00:00" + "-" + "02:30" + " " + "Dr JDC");
        //  items.add("00:00" + "-" + "01:30" + " " + ":)");

        String s;

        LocalDateTime now = LocalDateTime.now();

        ArrayList<Agenda> temp = model.getDbController().getAppointments(-1, "");

        LocalDateTime startTemp;
        LocalDateTime endTemp;
        int startMin;
        int endMin;
        String sMin;
        String eMin;
        String doctor;

        items.add("TAKEN SLOTS");

        for (int i = 0; i < temp.size(); i++)
        {
            startTemp = model.getDbController().getAppointments(-1, "").get(i).getStartTime();
            endTemp = model.getDbController().getAppointments(-1, "").get(i).getEndTime();
            startMin = startTemp.getMinute();
            endMin = endTemp.getMinute();
            doctor = ((Appointment) model.getDbController().getAppointments(-1, "").get(i)).getDoctorName();
            String eampm = temp.get(i).getEndTime().format(DateTimeFormatter.ofPattern("a"));

            if (startMin == 0)
                sMin = "00";
            else
                sMin = "30";

            if (endMin == 0)
                eMin = "00";
            else
                eMin = "30";

            if (temp.get(i).getStartTime().getDayOfWeek().getValue() == 1) {
                s = temp.get(i).getStartTime().getDayOfWeek().toString() + "  " + temp.get(i).getStartTime().getHour() + ":" + sMin + "-" +
                        temp.get(i).getEndTime().getHour() + ":" + eMin + " " + eampm + "Dr. " + doctor;
                items.add(s);
            }
        }

        for (int i = 0; i < temp.size(); i++)
        {
            startTemp = model.getDbController().getAppointments(-1, "").get(i).getStartTime();
            endTemp = model.getDbController().getAppointments(-1, "").get(i).getEndTime();
            startMin = startTemp.getMinute();
            endMin = endTemp.getMinute();
            doctor = ((Appointment) model.getDbController().getAppointments(-1, "").get(i)).getDoctorName();
            String eampm = temp.get(i).getEndTime().format(DateTimeFormatter.ofPattern("a"));


            if (startMin == 0)
                sMin = "00";
            else
                sMin = "30";

            if (endMin == 0)
                eMin = "00";
            else
                eMin = "30";

            if (temp.get(i).getStartTime().getDayOfWeek().getValue() == 2) {
                s = temp.get(i).getStartTime().getDayOfWeek().toString() + "  " + temp.get(i).getStartTime().getHour() + ":" + sMin + "-" +
                        temp.get(i).getEndTime().getHour() + ":" + eMin + " " + eampm + " Dr. " + doctor;
                items.add(s);
            }
        }

        for (int i = 0; i < temp.size(); i++)
        {
            startTemp = model.getDbController().getAppointments(-1, "").get(i).getStartTime();
            endTemp = model.getDbController().getAppointments(-1, "").get(i).getEndTime();
            startMin = startTemp.getMinute();
            endMin = endTemp.getMinute();
            doctor = ((Appointment) model.getDbController().getAppointments(-1, "").get(i)).getDoctorName();
            String eampm = temp.get(i).getEndTime().format(DateTimeFormatter.ofPattern("a"));


            if (startMin == 0)
                sMin = "00";
            else
                sMin = "30";

            if (endMin == 0)
                eMin = "00";
            else
                eMin = "30";

            if (temp.get(i).getStartTime().getDayOfWeek().getValue() == 3) {
                s = temp.get(i).getStartTime().getDayOfWeek().toString() + "  " + temp.get(i).getStartTime().getHour() + ":" + sMin + "-" +
                        temp.get(i).getEndTime().getHour() + ":" + eMin + " " + eampm + " Dr. " + doctor;
                items.add(s);
            }
        }

        for (int i = 0; i < temp.size(); i++)
        {
            startTemp = model.getDbController().getAppointments(-1, "").get(i).getStartTime();
            endTemp = model.getDbController().getAppointments(-1, "").get(i).getEndTime();
            startMin = startTemp.getMinute();
            endMin = endTemp.getMinute();
            doctor = ((Appointment) model.getDbController().getAppointments(-1, "").get(i)).getDoctorName();
            String eampm = temp.get(i).getEndTime().format(DateTimeFormatter.ofPattern("a"));


            if (startMin == 0)
                sMin = "00";
            else
                sMin = "30";

            if (endMin == 0)
                eMin = "00";
            else
                eMin = "30";

            if (temp.get(i).getStartTime().getDayOfWeek().getValue() == 4) {
                s = temp.get(i).getStartTime().getDayOfWeek().toString() + "  " + temp.get(i).getStartTime().getHour() + ":" + sMin + "-" +
                        temp.get(i).getEndTime().getHour() + ":" + eMin + " " + eampm + " Dr. " + doctor;
                items.add(s);
            }
        }

        for (int i = 0; i < temp.size(); i++)
        {
            startTemp = model.getDbController().getAppointments(-1, "").get(i).getStartTime();
            endTemp = model.getDbController().getAppointments(-1, "").get(i).getEndTime();
            startMin = startTemp.getMinute();
            endMin = endTemp.getMinute();
            doctor = ((Appointment) model.getDbController().getAppointments(-1, "").get(i)).getDoctorName();
            String eampm = temp.get(i).getEndTime().format(DateTimeFormatter.ofPattern("a"));


            if (startMin == 0)
                sMin = "00";
            else
                sMin = "30";

            if (endMin == 0)
                eMin = "00";
            else
                eMin = "30";

            if (temp.get(i).getStartTime().getDayOfWeek().getValue() == 5) {
                s = temp.get(i).getStartTime().getDayOfWeek().toString() + "  " + temp.get(i).getStartTime().getHour() + ":" + sMin + "-" +
                        temp.get(i).getEndTime().getHour() + ":" + eMin + eampm + " Dr. " + doctor;
                items.add(s);
            }
        }

        for (int i = 0; i < temp.size(); i++)
        {
            startTemp = model.getDbController().getAppointments(-1, "").get(i).getStartTime();
            endTemp = model.getDbController().getAppointments(-1, "").get(i).getEndTime();
            startMin = startTemp.getMinute();
            endMin = endTemp.getMinute();
            doctor = ((Appointment) model.getDbController().getAppointments(-1, "").get(i)).getDoctorName();
            String eampm = temp.get(i).getEndTime().format(DateTimeFormatter.ofPattern("a"));


            if (startMin == 0)
                sMin = "00";
            else
                sMin = "30";

            if (endMin == 0)
                eMin = "00";
            else
                eMin = "30";

            if (temp.get(i).getStartTime().getDayOfWeek().getValue() == 6) {
                s = temp.get(i).getStartTime().getDayOfWeek().toString() + "  " + temp.get(i).getStartTime().getHour() + ":" + sMin + "-" +
                        temp.get(i).getEndTime().getHour() + ":" + eMin + " " + eampm + " Dr. " + doctor;
                items.add(s);
            }
        }

        for (int i = 0; i < temp.size(); i++)
        {
            startTemp = model.getDbController().getAppointments(-1, "").get(i).getStartTime();
            endTemp = model.getDbController().getAppointments(-1, "").get(i).getEndTime();
            startMin = startTemp.getMinute();
            endMin = endTemp.getMinute();
            doctor = ((Appointment) model.getDbController().getAppointments(-1, "").get(i)).getDoctorName();
            String eampm = temp.get(i).getEndTime().format(DateTimeFormatter.ofPattern("a"));


            if (startMin == 0)
                sMin = "00";
            else
                sMin = "30";

            if (endMin == 0)
                eMin = "00";
            else
                eMin = "30";

            if (temp.get(i).getStartTime().getDayOfWeek().getValue() == 7) {
                s = temp.get(i).getStartTime().getDayOfWeek().toString() + "  " + temp.get(i).getStartTime().getHour() + ":" + sMin + "-" +
                        temp.get(i).getEndTime().getHour() + ":" + eMin +  " " + eampm + " Dr. " + doctor;
                items.add(s);
            }
        }



    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//       insertFilterData();
        items = weekList.getItems();
        //         setList();

        setModel(model);
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
    public void update() {

    }

    @Override
    public void insertFilterData(ArrayList<Agenda> data) {
        items = weekList.getItems();
        //  setList();
    }

    @Override
    public void insertFilterData(LocalDate selected) {
        
    }
}
