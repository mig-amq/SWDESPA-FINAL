package udc.client.regular.FXMLControllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import udc.Model;
import udc.client.regular.Controller.ClientSuperController;
import udc.objects.time.concrete.Agenda;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ClientAgendaDayController extends ClientSuperController implements Initializable {


    @FXML
    private ListView dayList;

    private ObservableList<String> items;

    Model model;


    private void setList() {
        items.add("00:00" + "-" + "02:30" + " " + "Dr JDC");
        items.add("00:00" + "-" + "01:30" + " " + ":)");
        //dayList.setItems(items);

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
//        insertFilterData();
//        items = dayList.getItems();
//        setList();
//        dayList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
//            @Override
//            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
//                System.out.println("oldValue = " + oldValue + " to newValue = " + newValue);
//                switch(newValue) {
//                    case "Monday":
//                    case "Tuesday":
//                    case "Wednesday":
//                    case "Thursday":
//                    case "Friday":
//                    case "Saturday":
//                    case "Sunday":
//                    default:
//                }
//            }
//        });
    }

    @Override
    public void insertFilterData(ArrayList<Agenda> data) {
        items = dayList.getItems();
        setList();
    }
}
