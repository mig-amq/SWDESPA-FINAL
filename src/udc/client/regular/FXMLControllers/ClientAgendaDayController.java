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
    private ListView<String> dayList;

    private ObservableList<String> items;

    private void setList() throws Exception {
        items.add("00:00" + "-" + "02:30" + " " + "Dr JDC");
        items.add("00:00" + "-" + "01:30" + " " + ":)");

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
        System.out.println(model);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//       insertFilterData();
       items = dayList.getItems();
        try {
            setList();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
      //  setList();
    }
}
