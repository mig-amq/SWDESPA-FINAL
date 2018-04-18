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
import udc.objects.time.concrete.Unavailable;

import java.net.URL;
import java.time.LocalDate;
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
    public void insertFilterData(LocalDate selected) {
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
    public void update() {

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

//    public ArrayList<Agenda> findData(LocalDate selected) throws Exception {
//        ArrayList<Agenda> agendas =  model.getDbController().getAppointments(-1, "");
//        ArrayList<Agenda> arrayList = new ArrayList<>();
//
//        for (int i = 0; i < agendas.size(); i++) {
//            Agenda agenda = agendas.get(i);
//            if(isEqualDate(agenda, selected))
//                arrayList.add(agenda);
//        }
//        return arrayList;
//    }

//    private boolean isEqualDate(Agenda agenda, LocalDate selected){
//        String sDoctorName = (String) cmbBoxDoctors.getSelectionModel().getSelectedItem();
//        if(sDoctorName != null && !sDoctorName.equals("All"))
//            sDoctorName = sDoctorName.substring(4);
//
//        if(sDoctorName!= null && agenda instanceof Appointment) {
//            if (sDoctorName.equals("Miguel Quiambao") && sDoctorName.equals(((Appointment)agenda).getDoctorName())) //mq
//                return dateToString(agenda.getStartTime()).equals(dateToString(selected));
//            else if(sDoctorName.equals("Mitchell Ong") && sDoctorName.equals(((Appointment) agenda).getDoctorName()))
//                return dateToString(agenda.getStartTime()).equals(dateToString(selected));
//            else if(sDoctorName.equals("All"))
//                return dateToString(agenda.getStartTime()).equals(dateToString(selected));
//        }else if(sDoctorName != null && agenda instanceof Unavailable){
//            if (sDoctorName.equals("Miguel Quiambao") && sDoctorName.equals(((Unavailable)agenda).getDoctorName())) //mq
//                return dateToString(agenda.getStartTime()).equals(dateToString(selected));
//            else if(sDoctorName.equals("Mitchell Ong") && sDoctorName.equals(((Unavailable) agenda).getDoctorName()))
//                return dateToString(agenda.getStartTime()).equals(dateToString(selected));
//            else if(sDoctorName.equals("All"))
//                return dateToString(agenda.getStartTime()).equals(dateToString(selected));
//        }
//        return false;//
//    }
}
