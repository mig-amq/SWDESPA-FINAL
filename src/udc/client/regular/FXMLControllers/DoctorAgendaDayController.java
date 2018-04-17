package udc.client.regular.FXMLControllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import udc.client.regular.Controller.ClientSuperController;
import udc.objects.time.concrete.Agenda;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DoctorAgendaDayController extends ClientSuperController implements Initializable {

    @FXML private ListView<?> dayList;


    public void setList()
    {
        //        ArrayList<Agenda> today = new ArrayList<Agenda>();
//
//
//        for (int i = 0; i < temp.size(); i++)
//        {
//            LocalDateTime timetemp = model.getDbController().getAppointments().get(i).getStartTime();
//            LocalDateTime now = LocalDateTime.now();
//
//            if (timetemp.getDayOfYear() == now.getDayOfYear() && timetemp.getYear() == now.getYear())
//            {
//                today.add(model.getDbController().getAppointments().get(i));
//            }
//        }
//
//        items.add(info);
//
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {



    }

    @Override
    public void insertFilterData(ArrayList<Agenda> data) {

    }
}
