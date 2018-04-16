package udc.client.walkin;

import com.jfoenix.controls.JFXButton;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class WalkInPopUpController extends AnchorPane {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXButton popUpCloseBtn;

    @FXML
    private JFXButton minPopUpClloseBtn;

    @FXML
    private Label hereLbl;

    @FXML
    private Label presentLbl;

    @FXML
    private Label secLbl;

    @FXML
    private Label codeLbl;

    @FXML
    private JFXButton mainCloseBtn;

    @FXML
    private Label nameLbl;

    @FXML
    private Label timelbl;

    @FXML
    private Label doctorLbl;

    @FXML
    private Label contactLbl;

    String name;
    String start;
    String end;
    String doctor;
    String contact;


    public WalkInPopUpController(String name, String start, String end, String doctor, String contact)
    {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("WalkInPopUp.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.name = name;
        this.start = start;
        this.end = end;
        this.doctor = doctor;
        this.contact = contact;

        settingText();
    }

    @FXML
    void initialize() {
        closepopUp();
        closeMainBtn();
    }

    public void closepopUp() {
        popUpCloseBtn.setOnAction(event -> {
                Stage stage = (Stage) popUpCloseBtn.getScene().getWindow();
                stage.close();
        });
    }

    public void settingText()
    {
        System.out.println(name);
        System.out.println(start);
        System.out.println(end);
        System.out.println(doctor);
        nameLbl.setText(name);
        timelbl.setText(start + " - " + end);
        doctorLbl.setText(doctor);
        contactLbl.setText(contact);

    }

    public void closeMainBtn() {
        mainCloseBtn.setOnAction(event -> {
            Stage stage = (Stage) mainCloseBtn.getScene().getWindow();
            stage.close();
        });
    }

    @Override
    public Node getStyleableNode() {
        return null;
    }
}

