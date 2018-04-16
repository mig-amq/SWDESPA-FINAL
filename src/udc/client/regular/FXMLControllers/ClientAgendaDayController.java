package udc.client.regular.FXMLControllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientAgendaDayController extends ClientSuperController implements Initializable {

    @FXML
    private ListView dayList;

    private ObservableList<String> items;


    private void setList() {
        items.add("00:00" + "-" + "02:30" + " " + "Dr JDC");
        items.add("00:00" + "-" + "01:30" + " " + ":)");
        //dayList.setItems(items);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        insertFilterData();
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
    public void insertFilterData() {
        items = dayList.getItems();
        setList();
    }
}
