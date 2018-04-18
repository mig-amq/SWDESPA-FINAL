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
import java.time.LocalDateTime;
import java.util.ArrayList;
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

//        for (int i = 0; i < temp.size(); i++)
//        {
//            if (temp.get(i).getStartTime().getDayOfWeek().getValue() == 1)
//                mon.add(temp.get(i));
//            else if (temp.get(i).getStartTime().getDayOfWeek().getValue() == 2)
//                tues.add(temp.get(i));
//            else if (temp.get(i).getStartTime().getDayOfWeek().getValue() == 3)
//                wed.add(temp.get(i));
//            else if (temp.get(i).getStartTime().getDayOfWeek().getValue() == 4)
//                thurs.add(temp.get(i));
//            else if (temp.get(i).getStartTime().getDayOfWeek().getValue() == 5)
//                fri.add(temp.get(i));
//            else if (temp.get(i).getStartTime().getDayOfWeek().getValue() == 6)
//                sat.add(temp.get(i));
//            else if(temp.get(i).getStartTime().getDayOfWeek().getValue() == 7)
//                sun.add(temp.get(i));
//        }
//        System.out.println(model);
//
//        if(mon != null)
//        {
//            for(int m = 0; m < mon.size(); m++) {
//
//                s = mon.get(m).getStartTime().getDayOfWeek().toString() + "  " + mon.get(m).getStartTime().getHour() + ":" + mon.get(m).getStartTime().getMinute() + " " +
//                        mon.get(m).getEndTime().getHour() + ":" + mon.get(m).getEndTime().getMinute();
//                items.add(s);
//            }
//        }
//
//        if(tues != null)
//        {
//            for (int tu = 0; tu < tues.size(); tu++) {
//                s = mon.get(tu).getStartTime().getDayOfWeek().toString() + "  " + mon.get(tu).getStartTime().getHour() + ":" + mon.get(tu).getStartTime().getMinute() + " " +
//                        mon.get(tu).getEndTime().getHour() + ":" + mon.get(tu).getEndTime().getMinute();
//                items.add(s);
//            }
//        }
//
//        if(wed != null) {
//            for (int w = 0; w < wed.size(); w++) {
//                s = mon.get(w).getStartTime().getDayOfWeek().toString() + "  " + mon.get(w).getStartTime().getHour() + ":" + mon.get(w).getStartTime().getMinute() + " " +
//                        mon.get(w).getEndTime().getHour() + ":" + mon.get(w).getEndTime().getMinute();
//                items.add(s);
//            }
//        }
//
//        if(thurs != null)
//        {
//            for (int th = 0; th < thurs.size(); th++) {
//                s = mon.get(th).getStartTime().getDayOfWeek().toString() + "  " + mon.get(th).getStartTime().getHour() + ":" + mon.get(th).getStartTime().getMinute() + " " +
//                        mon.get(th).getEndTime().getHour() + ":" + mon.get(th).getEndTime().getMinute();
//                items.add(s);
//            }
//        }
//
//        if(fri != null)
//        {
//            for (int f = 0; f < fri.size(); f++) {
//                s = mon.get(f).getStartTime().getDayOfWeek().toString() + "  " + mon.get(f).getStartTime().getHour() + ":" + mon.get(f).getStartTime().getMinute() + " " +
//                        mon.get(f).getEndTime().getHour() + ":" + mon.get(f).getEndTime().getMinute();
//                items.add(s);
//            }
//        }
//
//        if(sat != null)
//        {
//            for (int sa = 0; sa < sat.size(); sa++) {
//                s = mon.get(sa).getStartTime().getDayOfWeek().toString() + "  " + mon.get(sa).getStartTime().getHour() + ":" + mon.get(sa).getStartTime().getMinute() + " " +
//                        mon.get(sa).getEndTime().getHour() + ":" + mon.get(sa).getEndTime().getMinute();
//                items.add(s);
//            }
//        }
//
//        if(sun != null)
//        {
//            for (int su = 0; su < sun.size(); su++) {
//                s = mon.get(su).getStartTime().getDayOfWeek().toString() + "  " + mon.get(su).getStartTime().getHour() + ":" + mon.get(su).getStartTime().getMinute() + " " +
//                        mon.get(su).getEndTime().getHour() + ":" + mon.get(su).getEndTime().getMinute();
//                items.add(s);
//            }
//        }

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
                        temp.get(i).getEndTime().getHour() + ":" + eMin + " " + "Dr. " + doctor;
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
                        temp.get(i).getEndTime().getHour() + ":" + eMin + "Dr. " + doctor;
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
                        temp.get(i).getEndTime().getHour() + ":" + eMin + " " + "Dr. " + doctor;
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
                        temp.get(i).getEndTime().getHour() + ":" + eMin + " " + "Dr. " + doctor;
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
                        temp.get(i).getEndTime().getHour() + ":" + eMin + " " + "Dr. " + doctor;
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
                        temp.get(i).getEndTime().getHour() + ":" + eMin + " " + "Dr. " + doctor;
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
                        temp.get(i).getEndTime().getHour() + ":" + eMin +  " " + "Dr. " + doctor;
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
    public void insertFilterData(ArrayList<Agenda> data) {
        items = weekList.getItems();
        //  setList();
    }
}
