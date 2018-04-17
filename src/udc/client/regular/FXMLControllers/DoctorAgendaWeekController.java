package udc.client.regular.FXMLControllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import udc.client.regular.Controller.ClientSuperController;
import udc.objects.time.concrete.Agenda;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DoctorAgendaWeekController extends ClientSuperController implements Initializable {

    @FXML private ListView<String> weekList;

    private ObservableList<Agenda> mon;
    private ObservableList<Agenda> tues;
    private ObservableList<Agenda> wed;
    private ObservableList<Agenda> thurs;
    private ObservableList<Agenda> fri;
    private ObservableList<Agenda> sat;
    private ObservableList<Agenda> sun;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Override
    public void insertFilterData(ArrayList<Agenda> data) {
//        for (int i = 0; i < data.size(); i++) {
//            if (data.get(i).getStartTime().getDayOfWeek().getValue() == 1)
//                mon.add(data.get(i));
//            else if (data.get(i).getStartTime().getDayOfWeek().getValue() == 2)
//                tues.add(data.get(i));
//            else if (data.get(i).getStartTime().getDayOfWeek().getValue() == 3)
//                wed.add(data.get(i));
//            else if (data.get(i).getStartTime().getDayOfWeek().getValue() == 4)
//                thurs.add(data.get(i));
//            else if (data.get(i).getStartTime().getDayOfWeek().getValue() == 5)
//                fri.add(data.get(i));
//            else if (data.get(i).getStartTime().getDayOfWeek().getValue() == 6)
//                sat.add(data.get(i));
//            else if(data.get(i).getStartTime().getDayOfWeek().getValue() == 7)
//                sun.add(data.get(i));
//        }
   }
}
