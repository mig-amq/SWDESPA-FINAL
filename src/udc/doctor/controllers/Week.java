package udc.doctor.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import udc.doctor.objects.AgendaRow;
import udc.doctor.objects.AgendaRowWeek;
import udc.objects.time.concrete.Agenda;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Week extends TableView<AgendaRow> {
    private LocalDate date;
    private ArrayList<Agenda> data;
    private final DateTimeFormatter format = DateTimeFormatter.ofPattern("hh:mm a");

    @FXML
    private TableColumn<AgendaRowWeek, AgendaRow> time, monday, tuesday, wednesday, thursday, friday, saturday;

    public Week (ArrayList<Agenda> data) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/Week.fxml"));
        loader.setController(this);
        loader.setRoot(this);
        loader.load();

        this.setData(data);
        this.setDate(LocalDate.now());
    }

    public Week (ArrayList<Agenda> data, LocalDate ldt) throws IOException{
        this(data);
        this.setDate(ldt);
    }

    public void setData(ArrayList<Agenda> data) {
        this.data = data;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    public ArrayList<Agenda> getData() {
        return data;
    }
}
