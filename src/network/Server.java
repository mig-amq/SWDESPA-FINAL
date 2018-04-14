package UDC.network;

import UDC.network.threads.ServerThread;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Server implements Initializable {

    private ServerThread sthread;

    @FXML
    private AnchorPane close;

    @FXML
    private JFXButton start;

    @FXML
    private JFXTextField port;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        close.setOnMouseClicked(event -> {
            if (sthread != null) {
                try {
                    sthread.close();
                    sthread = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            Platform.exit();
            System.exit(1);
        });

        start.setOnMouseClicked(event -> {
            try {
                if (sthread != null) {
                    sthread.close();
                    sthread = null;
                    start.getStyleClass().remove("stop");
                    start.getStyleClass().add("start");
                    start.setText("Start Server");
                } else {
                    if (!port.getText().isEmpty()) {
                            sthread = new ServerThread(Integer.parseInt(port.getText()));
                            sthread.start();

                            start.getStyleClass().remove("start");
                            start.getStyleClass().add("stop");
                            start.setText("Stop Server");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
