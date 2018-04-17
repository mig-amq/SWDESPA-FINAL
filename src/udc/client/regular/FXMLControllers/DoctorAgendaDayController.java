package udc.client.regular.FXMLControllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import udc.client.regular.Controller.ClientSuperController;
import udc.objects.time.concrete.Agenda;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DoctorAgendaDayController extends ClientSuperController implements Initializable {

    @FXML private ListView<String> dayList;


    public void setList() throws Exception {
        ////////////////////puts the stuff today into one arraylist

//        LocalDateTime now = LocalDateTime.now();
//
//        ArrayList<Agenda> temp = model.getDbController().getAppointments(model.getAccount().getId(), "normal");
//
//        for (int i = 0; i < temp.size(); i++)
//        {
//            LocalDateTime startTemp = model.getDbController().getAppointments(model.getAccount().getId(), "normal").get(i).getStartTime();
//            LocalDateTime endTemp = model.getDbController().getAppointments(model.getAccount().getId(), "normal").get(i).getEndTime();
//
//            if (startTemp.getDayOfYear() == now.getDayOfYear() && startTemp.getYear() == now.getYear())
//            {
//                String s = startTemp.getHour() + ":" + startTemp.getMinute() + " - " +  endTemp.getHour() + ":" + endTemp.getMinute();
//                items.add(s);
//            }
//        }
//
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {



    }

    @Override
    public void insertFilterData(ArrayList<Agenda> data) {

    }
}
