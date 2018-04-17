package udc.client.regular.FXMLControllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import udc.Model;
import udc.client.Client;
import udc.client.regular.AppointmentRow;
import udc.client.regular.Controller.ClientSuperController;
import udc.objects.enums.AgendaType;
import udc.objects.time.concrete.Agenda;
import udc.objects.time.concrete.Appointment;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ClientController extends AnchorPane {

    @FXML private TableView<AppointmentRow> upcoming, previous;
    @FXML private AnchorPane mainPane, homePane, bookPane, managePane;
    @FXML private AnchorPane bookTablePane, manageTablePane;
    @FXML private JFXButton home, book, manage;
    @FXML private JFXButton bookButton, removeButton;
    @FXML private JFXRadioButton bAgendaView, bCalendarView, bDayView, bWeekView;
    @FXML private JFXRadioButton mAgendaView, mCalendarView, mDayView, mWeekView;
    @FXML private JFXComboBox<String> bDoctorCmbBox, mDoctorCmbBox;
    @FXML private ToggleGroup viewTypeGroup, filterViewGroup;

    @FXML private Client client;
    @FXML private Model model;
    @FXML private ClientSuperController clientSuperController;

    public ClientController(Client client, Model model) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXMLFiles/Client.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();

        this.client = client;
        this.model = model;
        setToggleGroup();
        setComboBox();
        initializeButtons();
        loadHome();

        upcoming.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("date"));
        upcoming.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("time"));
        upcoming.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("doctor"));

        previous.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("date"));
        previous.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("time"));
        previous.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("doctor"));
    }

    private void update () {
        loadHome();
    }

    private void loadHome () {
        upcoming.getItems().clear();
        previous.getItems().clear();

        for (Agenda a : model.getAccount().getAppointments()) {
            AppointmentRow arow = new AppointmentRow((Appointment) a);

            if (a.getStartTime().isAfter(LocalDateTime.now()) || a.getStartTime().isEqual(LocalDateTime.now())) {
                upcoming.getItems().add(arow);
            } else {

                if (a.getType() == AgendaType.RECURRING) {
                    if (a.getStartTime().getDayOfMonth() >= LocalDateTime.now().getDayOfMonth() &&
                            a.getStartTime().getHour() >= LocalDateTime.now().getHour() &&
                            a.getStartTime().getMinute() >= LocalDateTime.now().getMinute()) {
                        if (!a.isException(a.getStartTime().withDayOfMonth(LocalDateTime.now().getDayOfMonth()))) {
                            arow.setDate(a.getStartTime()
                                    .withMonth( LocalDateTime.now().getDayOfMonth())
                                    .format(DateTimeFormatter.ofPattern("yyyy/MM/dd")));
                            upcoming.getItems().add(arow);
                        }
                    }
                } else
                    previous.getItems().add(arow);
            }
        }
    }

    private void setToggleGroup() {
        viewTypeGroup = new ToggleGroup();
        filterViewGroup = new ToggleGroup();

        bCalendarView.setToggleGroup(viewTypeGroup);
        bCalendarView.setToggleGroup(viewTypeGroup);
        bAgendaView.setToggleGroup(viewTypeGroup);
        bAgendaView.setToggleGroup(viewTypeGroup);

        bDayView.setToggleGroup(filterViewGroup);
        bDayView.setToggleGroup(filterViewGroup);
        bWeekView.setToggleGroup(filterViewGroup);
        bWeekView.setToggleGroup(filterViewGroup);

        mCalendarView.setToggleGroup(viewTypeGroup);
        mCalendarView.setToggleGroup(viewTypeGroup);
        mAgendaView.setToggleGroup(viewTypeGroup);
        mAgendaView.setToggleGroup(viewTypeGroup);

        mDayView.setToggleGroup(filterViewGroup);
        mDayView.setToggleGroup(filterViewGroup);
        mWeekView.setToggleGroup(filterViewGroup);
        mWeekView.setToggleGroup(filterViewGroup);
    }

    public void setComboBox() {
        ObservableList<String> list = FXCollections.observableArrayList(model.getDbController().loadDoctors());
        list.add(0, "All");
        bDoctorCmbBox.setItems(list);
        mDoctorCmbBox.setItems(list);
    }

    private void initializeButtons() {
        home.setOnAction(event -> homePane.toFront());

        book.setOnAction(event -> {
            bookPane.toFront();
            loadFXML("../FXMLFiles/DoctorCalDay.fxml", bookTablePane);
            bCalendarView.setSelected(true);
            bDayView.setSelected(true);
        });

        manage.setOnAction(event -> {
            managePane.toFront();
            loadFXML("../FXMLFiles/ClientCalDay.fxml", manageTablePane);
            mCalendarView.setSelected(true);
            mDayView.setSelected(true);
        });

        //JFXRadioButtons
        bCalendarView.setOnAction(event -> {
            if(bDayView.isSelected()) {
                loadFXML("../FXMLFiles/DoctorCalDay.fxml", bookTablePane);
            }
            else if(bWeekView.isSelected()) {
                loadFXML("../FXMLFiles/DoctorCalWeek.fxml", bookTablePane);
            }
        });

        bAgendaView.setOnAction(event -> {
            if(bDayView.isSelected()) {
                loadFXML("../FXMLFiles/DoctorAgendaDay.fxml", bookTablePane);
            }
            else if(bWeekView.isSelected()) {
                loadFXML("../FXMLFiles/DoctorAgendaWeek.fxml", bookTablePane);
            }
        });


        bDayView.setOnAction(event -> {
            if(bCalendarView.isSelected()) {
                loadFXML("../FXMLFiles/DoctorCalDay.fxml", bookTablePane);
            }
            else if(bAgendaView.isSelected()) {
                loadFXML("../FXMLFiles/DoctorAgendaDay.fxml", bookTablePane);
            }
        });

        bWeekView.setOnAction(event -> {
            if(bCalendarView.isSelected()) {
                loadFXML("../FXMLFiles/DoctorCalWeek.fxml", bookTablePane);
            }
            else if(bAgendaView.isSelected()) {
                loadFXML("../FXMLFiles/DoctorAgendaWeek.fxml", bookTablePane);
            }
        });

        mCalendarView.setOnAction(event -> {
            if(mDayView.isSelected()) {
                loadFXML("../FXMLFiles/ClientCalDay.fxml", manageTablePane);
            }
            else if(mWeekView.isSelected()) {
                loadFXML("../FXMLFiles/ClientCalWeek.fxml", manageTablePane);
            }
        });

        mAgendaView.setOnAction(event -> {
            if(mDayView.isSelected()) {
                loadFXML("../FXMLFiles/ClientAgendaDay.fxml", manageTablePane);
            }
            else if(mWeekView.isSelected()) {
                loadFXML("../FXMLFiles/ClientAgendaWeek.fxml", manageTablePane);
            }
        });

        mDayView.setOnAction(event -> {
            if(mCalendarView.isSelected()) {
                loadFXML("../FXMLFiles/ClientCalDay.fxml", manageTablePane);
            }
            else if(mAgendaView.isSelected()) {
                loadFXML("../FXMLFiles/ClientAgendaDay.fxml", manageTablePane);
            }
        });

        mWeekView.setOnAction(event -> {
            if(mCalendarView.isSelected()) {
                loadFXML("../FXMLFiles/ClientCalWeek.fxml", manageTablePane);
            }
            else if(mAgendaView.isSelected()) {
                loadFXML("../FXMLFiles/ClientAgendaWeek.fxml", manageTablePane);
            }
        });

        bookButton.setOnAction(event -> {
            ReserveController reserve = new ReserveController(model);
            Stage child = new Stage(StageStyle.UNDECORATED);
            child.setScene(new Scene(reserve));
            child.show();
        });

        removeButton.setOnAction(event -> {
            RemoveController remove = new RemoveController(model);
            Stage child = new Stage(StageStyle.UNDECORATED);
            child.setScene(new Scene(remove));
            child.show();

        });
    }

    private void loadFXML(String path, AnchorPane anchorPane) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            anchorPane.getChildren().clear();
            anchorPane.getChildren().add(loader.load());

            clientSuperController = loader.<ClientAgendaDayController>getController();
            clientSuperController.setModel(model);
            clientSuperController.setCalendar(client.getCalendar());
            clientSuperController.insertFilterData(client.getCalendar().selectedProperty().get());

   //         System.out.println(model.getAccount().getId());

        } catch(Exception e) {}
    }

    private ArrayList<Agenda> findData(LocalDate selected) {
        ArrayList<Agenda> arrayList = new ArrayList<>();
        for (int i = 0; i < agendas.size(); i++) {
            Agenda agenda = agendas.get(i);
            if(isEqualDate(agenda, selected))
                arrayList.add(agenda);
        }
        return arrayList;
    }
}
