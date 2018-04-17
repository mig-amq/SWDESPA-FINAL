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

public class DoctorAgendaDayController extends ClientSuperController implements Initializable {


    @FXML
    private ListView<String> dayList;

    private ObservableList<String> items;

    private void setList() throws Exception {
        //  items.add("00:00" + "-" + "02:30" + " " + "Dr JDC");
        // items.add("00:00" + "-" + "01:30" + " " + ":)");

        LocalDateTime now = LocalDateTime.now();
        if (model.getDbController().getAppointments(model.getAccount().getId(), "normal") != null) {
            ArrayList<Agenda> temp = model.getDbController().getAppointments(model.getAccount().getId(), "normal");

            for (int i = 0; i < temp.size(); i++) {
                LocalDateTime startTemp = model.getDbController().getAppointments(model.getAccount().getId(), "normal").get(i).getStartTime();
                LocalDateTime endTemp = model.getDbController().getAppointments(model.getAccount().getId(), "normal").get(i).getEndTime();
                //    String doctor = model.getDbController().getAppointments(model.getAccount().getId(), "normal").;
                String doctor = "Dr. bruh";
                int startMin = startTemp.getMinute();
                int endMin = endTemp.getMinute();
                String sMin;
                String eMin;

                if (startMin == 0)
                    sMin = "00";
                else
                    sMin = "30";

                if (endMin == 0)
                    eMin = "00";
                else
                    eMin = "30";

                if (startTemp.getDayOfYear() == now.getDayOfYear() && startTemp.getYear() == now.getYear()) {
                    String s = startTemp.getHour() + ":" + sMin + " - " + endTemp.getHour() + ":" + eMin + " " + doctor;
                    items.add(s);
                }
            }
        }

        else
            items.add("No Appointments");
        //
        System.out.println(model);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//       insertFilterData();
        items = dayList.getItems();
        //         setList();

        setModel(model);

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
        items = dayList.getItems();
        //  setList();
    }
}
