package udc.doctor.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import udc.Model;
import udc.objects.time.concrete.Agenda;
import udc.objects.time.concrete.Available;

import java.io.IOException;
import java.util.ArrayList;

public class AgendaWeek extends AnchorPane {
    private Model model;
    private ArrayList<Agenda> appointments;
    private ArrayList<Available> availabilities;
    private Stage stage;
    private Node node;

    public AgendaWeek(Model md) throws IOException {
        this.setModel(md);
        this.setAppointments(md.getAccount().getAppointments());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/AgendaDay.fxml"));
        loader.setController(this);
        loader.load();

        node = loader.getRoot();
    }

    public Node getNode() { return node; }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setAppointments(ArrayList<Agenda> appointments) {
        this.appointments = appointments;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Stage getStage() {
        return stage;
    }

    public ArrayList<Agenda> getAppointments() {
        return appointments;
    }

    public Model getModel() {
        return model;
    }
}
