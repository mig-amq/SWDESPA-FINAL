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
import java.util.ResourceBundle;

public class ClientAgendaDayController extends ClientSuperController implements Initializable {


    @FXML
    private ListView<String> dayList;

    private ObservableList<String> items;

    private void setList() throws Exception {
      //  items.add("00:00" + "-" + "02:30" + " " + "Dr JDC");
       // items.add("00:00" + "-" + "01:30" + " " + ":)");

        LocalDateTime now = LocalDateTime.now();
        System.out.println(model.getDbController().getAppointments(model.getAccount().getId(), "normal"));

            ArrayList<Agenda> temp = model.getDbController().getAppointments(model.getAccount().getId(), "normal");

            for (int i = 0; i < temp.size(); i++) {
                LocalDateTime startTemp = model.getDbController().getAppointments(model.getAccount().getId(), "normal").get(i).getStartTime();
                LocalDateTime endTemp = model.getDbController().getAppointments(model.getAccount().getId(), "normal").get(i).getEndTime();
                String doctor = ((Appointment) model.getDbController().getAppointments(-1, "").get(i)).getDoctorName();
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

                if (startTemp.getDayOfYear() == now.getDayOfYear() && startTemp.getYear() == now.getYear()) {
                    String s = startTemp.getHour() + ":" + sMin + " - " + endTemp.getHour() + ":" + eMin + " " + eampm + " Dr. " + doctor;
                    items.add(s);
                }
            }
    }

@Override
    public void initialize(URL location, ResourceBundle resources) {
       items = dayList.getItems();
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
        items = dayList.getItems();
    }

    @Override
    public void insertFilterData(LocalDate selected) {

    }
}
