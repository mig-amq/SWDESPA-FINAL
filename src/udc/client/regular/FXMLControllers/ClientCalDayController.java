package udc.client.regular.FXMLControllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import udc.Model;
import udc.client.regular.Controller.ClientSuperController;
import udc.client.regular.Controller.DaySchedule;
import udc.objects.time.concrete.Agenda;
import udc.objects.time.concrete.Appointment;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ClientCalDayController extends ClientSuperController implements Initializable {

    @FXML private TableView<DaySchedule> dayTable;
    @FXML private TableColumn<DaySchedule, ?> time;
    @FXML private TableColumn<DaySchedule, ?> doctor;

    private void setCellTable() {
        time.setCellValueFactory(new PropertyValueFactory<>("time"));
        doctor.setCellValueFactory(new PropertyValueFactory<>("doctor"));
    }

    @Override
    public void insertFilterData(ArrayList<Agenda> data) {
        dayTable.getItems().clear();
        int hr = 7;
        for (int i = 0; i < 30; i++) {
            int index;
            String time = getDispTime(hr, i);
            if(!isOdd(i))
                hr++;
//            if((index = getDataIndexfromList(data, time)) >= 0 ) {
//                Appointment agenda = (Appointment) data.get(index);
//                dayTable.getItems().add(new DaySchedule(time, "Dr. " + agenda.getDoctorName()));
//            }
//            else
//                dayTable.getItems().add(new DaySchedule(time, ""));
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setCellTable();
        setModel(model);


    }

    @Override
    public void setModel (Model model) {
        super.setModel(model);
    }
}
