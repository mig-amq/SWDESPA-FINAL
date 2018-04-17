package udc.doctor.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class AgendaViewController extends SuperController implements Initializable {

    @FXML
    private AnchorPane agendaPane;

    @FXML
    private ScrollPane agendaScrollPane;

    @FXML
    private Label lblSlots;

    @FXML
    private ListView lv;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Override
    public void update(LocalDateTime ldt) {

    }

//    public void insertTime () {
//        this.insertTime(LocalDate.now());
//    }
//
//    public void insertTime(LocalDate ldt){
//        listview.getItems().clear();
//        int hr = 7;
//        for (int i = 0; i < 30; i++) {
//            String time = getDispTime(hr, i);
//            if (!isOdd(i))
//                hr++;
//            Agenda a = this.getModel().getAccount().atTime(ldt.atTime(Integer.parseInt(time.substring(0, 2)), Integer.parseInt(time.substring(3, 5))));
//
//            if (a != null) {
//                tbView.getItems().add(new DaySchedule(a));
//            }
//        }
//    }
}
