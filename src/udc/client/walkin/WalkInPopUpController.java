package udc.client.walkin;

import com.jfoenix.controls.JFXButton;

import java.io.IOException;
import java.net.URL;
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

    public int code;

    public WalkInPopUpController()
    {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("WalkInPopUp.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void initialize() {
        assert popUpCloseBtn != null : "fx:id=\"popUpCloseBtn\" was not injected: check your FXML file 'WalkInPopUp.fxml'.";
        assert minPopUpClloseBtn != null : "fx:id=\"minPopUpClloseBtn\" was not injected: check your FXML file 'WalkInPopUp.fxml'.";
        assert hereLbl != null : "fx:id=\"hereLbl\" was not injected: check your FXML file 'WalkInPopUp.fxml'.";
        assert presentLbl != null : "fx:id=\"presentLbl\" was not injected: check your FXML file 'WalkInPopUp.fxml'.";
        assert secLbl != null : "fx:id=\"secLbl\" was not injected: check your FXML file 'WalkInPopUp.fxml'.";
        assert codeLbl != null : "fx:id=\"codeLbl\" was not injected: check your FXML file 'WalkInPopUp.fxml'.";
        assert mainCloseBtn != null : "fx:id=\"mainCloseBtn\" was not injected: check your FXML file 'WalkInPopUp.fxml'.";
        assert mainCloseBtn != null : "fx:id=\"mainCloseBtn\" was not injected: check your FXML file 'WalkInPopUp.fxml'.";

        closepopUp();
        assign(code);
        closeMainBtn();
    }

    public  void assign (int code){
            Random rand = new Random();
            code = rand.nextInt(9999) + 1;
            codeLbl.setText(String.valueOf(code));
        }

    public void closepopUp() {
        popUpCloseBtn.setOnAction(event -> {
                Stage stage = (Stage) popUpCloseBtn.getScene().getWindow();
                stage.close()
;
        });
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

