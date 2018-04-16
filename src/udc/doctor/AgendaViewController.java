package udc.doctor;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javax.swing.text.html.ListView;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AgendaViewController extends SuperController implements Initializable {

    @FXML
    private ListView listview;

    @Override
    public void update(LocalDate ldt) {
        insertTime();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

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
