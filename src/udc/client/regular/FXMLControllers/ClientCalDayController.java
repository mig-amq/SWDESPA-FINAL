package udc.client.regular.FXMLControllers;

import com.jfoenix.controls.JFXComboBox;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import udc.Model;
import udc.client.regular.Controller.ClientSuperController;
import udc.client.regular.Controller.DaySchedule;
import udc.customfx.calendar.Calendar;
import udc.objects.time.concrete.Agenda;
import udc.objects.time.concrete.Appointment;
import udc.objects.time.concrete.Unavailable;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ClientCalDayController extends ClientSuperController implements Initializable {
    @FXML private TableView<DaySchedule> dayTable;
    @FXML private TableColumn<DaySchedule, String> time;
    @FXML private TableColumn<DaySchedule, String> doctor;
    @FXML private JFXComboBox<String> mDoctorCmbBox;
    private ArrayList<Agenda> agendas;
    private ObservableList<String> doctorList;

    @Override
    public void update() {
        try {
            agendas = model.getDbController().getAppointments(model.getAccount().getId(), "CLIENT");
            insertFilterData(calendar.getSelected());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setCellTable() {
        time.setCellValueFactory(new PropertyValueFactory<>("time"));
        doctor.setCellValueFactory(new PropertyValueFactory<>("doctor"));
    }

    @Override
    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    @Override
    public void insertFilterData(LocalDate selected) throws Exception {
        ArrayList<Agenda> data = findData(selected);

        dayTable.getItems().clear();
        int hr = 7;
        for (int i = 0; i < 30; i++) {
            int index;

            String time = getDispTime(hr, i);
            if(!isOdd(i))
                hr++;
            String index1 = getUnavailabilityFromList(data, time);
            if((index = getDataIndexfromList(data, time)) >= 0 ) {
                System.out.println("appointment HEHHEH");
                Appointment agenda = (Appointment) data.get(index);
                dayTable.getItems().add(new DaySchedule(time, "Dr. " + agenda.getDoctorName()));
            }else if(!index1.equals("")){
                System.out.println("ALIEN HEHHEH");
                String[] a = index1.split(" | ");
                if(a.length == 2) {
                    dayTable.getItems().add(new DaySchedule(time, "(Unavailable)"));
                } else if(a.length == 1){
                    Unavailable agenda = (Unavailable) data.get(Integer.parseInt(a[a.length -1]));
                    dayTable.getItems().add(new DaySchedule(time, "Dr. " + agenda.getDoctorName() + " - " + "Unavailable"));
                }else {
                    dayTable.getItems().add(new DaySchedule(time, ""));
                }
            }
            else
                dayTable.getItems().add(new DaySchedule(time, ""));
        }

        setColumnCellFactory(doctor);
    }

    public void setColumnCellFactory(TableColumn<DaySchedule, String> a){
        a.setCellFactory(column -> {
            return new TableCell<>() {
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        applyCellFactoryCondition(item, this);
                    }
                }

            };
        });
    }

    private void applyCellFactoryCondition(String item, TableCell a){
        if (item.contains("Dr. Miguel Quiambao") && !item.contains("Unavailable")) {
            a.setStyle("-fx-background-color: #42f498");
            //                            System.out.println(tvWeekView.getItems().get(0).getTableRow().getIndex() + " " +getIndex() + " " + getCellData(tvWeekView.getItems().get(b), b));
            //                            String prev = getCellData(getTableView().getItems().get(getTableRow().getIndex() -1), b);
            //                            if(!(getTableRow().getIndex != 0 && item.equals(prev))) 2 appointment slots cell spanning
            a.setText(item);
            System.out.println("QUIAMBAO");
        }else if(item.contains("Dr. Mitchell Ong") && !item.contains("Unavailable")){
            a.setStyle("-fx-background-color: #6aa2fc");
            //                            System.out.println(tvWeekView.getItems().get(0).getTableRow().getIndex() + " " +getIndex() + " " + getCellData(tvWeekView.getItems().get(b), b));
            //                            String prev = getCellData(getTableView().getItems().get(getTableRow().getIndex() -1), b);
            //                            if(!(getTableRow().getIndex != 0 && item.equals(prev))) 2 appointment slots cell spanning
            //See SecWeekControl for Code Continuation
            System.out.println("MITCHELL");
            a.setText(item);
        }
//        else if(item.equalsIgnoreCase("Dr. Mitchell Ong - Unavailable")){
//            a.setText("");
//            a.setStyle("-fx-background-color: #e25d2d");
//        } else if(item.equalsIgnoreCase("Dr. Miguel Quiambao - Unavailable")){
//            a.setText("");
//            a.setStyle("-fx-background-color: #3382bf");
//        }else if(item.equalsIgnoreCase("(Unavailable)")){ //both
//            a.setText("");
//            a.setStyle("-fx-background-color: #87312b");
//        }  else{
//            a.setStyle("-fx-background-color: #e5e2cc");
//            a.setStyle("-fx-border-color: #c6c5ba");
//            a.setText(null);
//            a.setGraphic(null);
//        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setCellTable();
    }

    @Override
    public void setModel (Model model) {
        super.setModel(model);

        doctorList = FXCollections.observableArrayList();
        doctorList.add("All");
        ArrayList<String> temp = model.getDbController().loadDoctors();
        for (int i = 0; i < temp.size()-1 ; i++) {
            doctorList.add(temp.get(i));
        }
        mDoctorCmbBox.setItems(doctorList);
        mDoctorCmbBox.getSelectionModel().select(0);
        mDoctorCmbBox.setOnAction(event -> {
            try {
                insertFilterData(calendar.getSelected());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }



    public ArrayList<Agenda> findData(LocalDate selected) throws Exception {
        agendas =  model.getDbController().getAppointments( model.getAccount().getId(), "CLIENT");
        ArrayList<Agenda> arrayList = new ArrayList<>();

        for (int i = 0; i < agendas.size(); i++) {
            Agenda agenda = agendas.get(i);
            System.out.print("Agenda ID: " + agenda.getId() + " start: " + agenda.getStartTime() + " end: " + agenda.getEndTime());
            if(agenda instanceof Appointment)
                System.out.println(" " + ((Appointment) agenda).getDoctorName());
            if(isEqualDate(agenda, selected)) {
                System.out.print("SELECTED Agenda ID: " + agenda.getId() + " start: " + agenda.getStartTime() + " end: " + agenda.getEndTime());
                System.out.println(" " + ((Appointment) agenda).getDoctorName());
                arrayList.add(agenda);
            }
        }
        return arrayList;
    }

    private boolean isEqualDate(Agenda agenda, LocalDate selected) {
        String sDoctorName = mDoctorCmbBox.getSelectionModel().getSelectedItem();

        if(sDoctorName!= null && agenda instanceof Appointment) {
            if (sDoctorName.equals("Miguel Quiambao") && sDoctorName.equals(((Appointment)agenda).getDoctorName())){
                return dateToString(agenda.getStartTime()).equals(dateToString(selected));
            }
            else if(sDoctorName.equals("Mitchell Ong") && sDoctorName.equals(((Appointment) agenda).getDoctorName()))
                return dateToString(agenda.getStartTime()).equals(dateToString(selected));
            else if(sDoctorName.equals("All"))
                return dateToString(agenda.getStartTime()).equals(dateToString(selected));
        }else if(sDoctorName != null && agenda instanceof Unavailable){
            if (sDoctorName.equals("Miguel Quiambao") && sDoctorName.equals(((Unavailable)agenda).getDoctorName())) //mq
                return dateToString(agenda.getStartTime()).equals(dateToString(selected));
            else if(sDoctorName.equals("Mitchell Ong") && sDoctorName.equals(((Unavailable) agenda).getDoctorName()))
                return dateToString(agenda.getStartTime()).equals(dateToString(selected));
            else if(sDoctorName.equals("All"))
                return dateToString(agenda.getStartTime()).equals(dateToString(selected));
        }
        return false;
    }
    private String dateToString(LocalDateTime localDateTime) {
        return localDateTime.getYear() + "/" + localDateTime.getMonth() + "/" + localDateTime.getDayOfMonth();
    }
    private String dateToString(LocalDate localDateTime) {
        return localDateTime.getYear() + "/" + localDateTime.getMonth() + "/" + localDateTime.getDayOfMonth();
    }

    public String getUnavailabilityFromList(ArrayList<Agenda> data, String time) {
        String index = "";
        int counter = 2; //increase counter for additional doctors
        for (int i = 0; i < data.size() && counter != 0; i++) {
            if (data.get(i) instanceof Agenda) {
                String agendaTime = convertIntHrorMintoString(data.get(i).getStartTime().getHour()) + convertIntHrorMintoString(data.get(i).getStartTime().getMinute());
                String endTime = convertIntHrorMintoString(data.get(i).getEndTime().getHour()) + convertIntHrorMintoString(data.get(i).getEndTime().getMinute());
                String nTime = convertTimeFromTable(time); //converts time from table to military
                if (nTime.equals(agendaTime) || (Integer.parseInt(endTime) > Integer.parseInt(nTime) && Integer.parseInt(agendaTime)
                        < Integer.parseInt(nTime))) {

                    index += i + " ";
                    counter--;
                }
            }
        }
        return index;
    }

    public String convertIntHrorMintoString(int value) {
        if(value == 0)//for this case if you decided to use 12am as time
            return "00";
        else if(value < 10)
            return 0 + String.valueOf(value);
        return String.valueOf(value);
    }

    public String convertTimeFromTable(String time){
        String[] arrSTime = time.split(" | ");
        String period = arrSTime[1];
        String[] arrTime = arrSTime[0].split( ":");
        if(period.equalsIgnoreCase("PM") && Integer.parseInt(arrTime[0]) != 12)
            arrTime[0] = String.valueOf(Integer.parseInt(arrTime[0]) + 12);
        else if(period.equalsIgnoreCase("AM") && Integer.parseInt(arrTime[0]) < 10)
            arrTime[0] =  "0" + arrTime[0];
        return arrTime[0] + arrTime[1];
    }

    public int getDataIndexfromList(ArrayList<Agenda> data, String time){
        //TODO: ADD END TIME //done
        for (int i = 0; i < data.size(); i++) {
            if(data.get(i) instanceof Appointment) {
                String agendaTime = convertIntHrorMintoString(data.get(i).getStartTime().getHour()) + convertIntHrorMintoString( data.get(i).getStartTime().getMinute());
                String endTime = convertIntHrorMintoString(data.get(i).getEndTime().getHour()) + convertIntHrorMintoString(data.get(i).getEndTime().getMinute());
                String nTime = convertTimeFromTable(time); //converts time from table to military
                if (nTime.equals(agendaTime) || (Integer.parseInt(endTime) > Integer.parseInt(nTime) && Integer.parseInt(agendaTime)
                        < Integer.parseInt(nTime))) {
                    return i;
                }
            }
        }
        return -1;
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
