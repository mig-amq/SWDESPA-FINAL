package udc.doctor;

import javafx.fxml.Initializable;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AgendaViewWController extends SuperController implements Initializable {
    @Override
    public void update(LocalDate ldt) {
//        insertTime(ldt);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

//    public void insertTime(LocalDate ldt){
//        tbView.getItems().clear();
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
//
//    public void insertTime () {
//        this.insertTime(LocalDate.now());
//    }
}
