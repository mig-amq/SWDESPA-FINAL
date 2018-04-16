package udc;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import network.threads.ClientThread;
import udc.client.regular.Client;
import udc.client.walkin.WalkIn;
import udc.customfx.drawerpanel.DrawerPanel;
import udc.doctor.Doctor;
import udc.objects.account.Account;
import udc.objects.enums.AccountType;
//import udc.secretary.Secretary;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{
    private double x, y;
    private Model model;

    private Label settingsLog;
    private JFXTextField server, db, dbUser;
    private JFXPasswordField dbPass;

    private DrawerPanel drawerPanel;
    private Stage stage;

    @FXML
    private AnchorPane close, pnlTool, minimize, mainPanel;

    @FXML
    private Label lblAlert;

    @FXML
    private JFXDrawer drawer;

    @FXML
    private FontAwesomeIconView settings;

    @FXML
    private JFXButton btnLogin;

    @FXML
    private JFXTextField txtUsername;

    @FXML
    private JFXPasswordField txtPassword;

    @FXML
    private Label client;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        close.setOnMouseClicked(event -> {
            Platform.exit();
            System.exit(1);
        });

        pnlTool.setOnMousePressed(event -> {
            x = pnlTool.getScene().getWindow().getX() - event.getScreenX();
            y = pnlTool.getScene().getWindow().getY() - event.getScreenY();
        });

        pnlTool.setOnMouseDragged(event -> {
            pnlTool.getScene().getWindow().setX(x + event.getScreenX());
            pnlTool.getScene().getWindow().setY(y + event.getScreenY());
        });

        minimize.setOnMouseClicked(event -> ((Stage) pnlTool.getScene().getWindow()).toBack());

        GridPane gp = new GridPane();
        gp.setPrefSize(175, 473);
        gp.setPadding(new Insets(10, 10, 10, 10));
        gp.setVgap(25);

        Label label = new Label("Settings:");
        label.setFont(Font.font("Open Sans", FontWeight.BOLD, 14));

        server = new JFXTextField();
        server.setLabelFloat(true);
        server.setPromptText("Server");

        db = new JFXTextField();
        db.setLabelFloat(true);
        db.setPromptText("Database");

        dbUser = new JFXTextField();
        dbUser.setLabelFloat(true);
        dbUser.setPromptText("DB Username");

        dbPass = new JFXPasswordField();
        dbPass.setLabelFloat(true);
        dbPass.setPromptText("DB Password");

        settingsLog = new Label();
        settingsLog.setMaxWidth(145);
        settingsLog.setWrapText(true);
        JFXButton btnSave = new JFXButton("Save & Apply");
        GridPane.setHalignment(btnSave, HPos.CENTER);
        GridPane.setHalignment(settingsLog, HPos.CENTER);

        db.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\:\\d{1,5}")) {
                settingsLog.setText("Error: Database socket has an invalid format!");
                btnSave.setDisable(true);
            } else if (server.getText().matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\:\\d{1,5}")) {
                settingsLog.setText("");
                btnSave.setDisable(false);
            }
        });

        server.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\:\\d{1,5}")) {
                settingsLog.setText("Error: Server socket has an invalid format!");
                btnSave.setDisable(true);
            } else if (db.getText().matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\:\\d{1,5}")) {
                settingsLog.setText("");
                btnSave.setDisable(false);
            }
        });

        btnSave.setOnMouseClicked(event -> {
            model.setServerAddress(server.getText().split(":")[0]);
            model.setServerPort(Integer.parseInt(server.getText().split(":")[1].replaceAll("[^0-9]", "")));

            model.setDbAddress(db.getText().split(":")[0]);
            model.setDbPort(Integer.parseInt(db.getText().split(":")[1].replaceAll("[^0-9]", "")));

            model.setDbUser(dbUser.getText());
            model.setDbPass(dbPass.getText());

            model.save_settings();
        });

        gp.add(label, 0, 0);
        gp.add(server, 0, 1);
        gp.add(db, 0, 2);
        gp.add(dbUser, 0, 3);
        gp.add(dbPass, 0, 4);
        gp.add(btnSave, 0, 5);
        gp.add(settingsLog, 0, 6);

        drawerPanel = new DrawerPanel(175, 473, true);
        drawerPanel.add(gp);

        drawer.setContent(mainPanel);
        drawer.setSidePane(drawerPanel);

        settings.setOnMouseClicked(event -> {
            if (drawer.isClosed()) {
                server.setText(model.getServerAddress() + ":" + model.getServerPort());
                db.setText(model.getDbAddress() + ":" + model.getDbPort());
                dbUser.setText(model.getDbUser());
                dbPass.setText(model.getDbPass());
            }

            drawer.toggle();
        });


        client.setOnMouseClicked(event -> {
            WalkIn walkIn = new WalkIn(this.getModel());
            Stage child = new Stage(StageStyle.UNDECORATED);
            child.setScene(new Scene(walkIn));
            child.show();
        });



        btnLogin.setOnAction(event -> {
            try {
                lblAlert.setText("");
                Account account = model.getDbController().login(txtUsername.getText().replaceAll("\\s+", ""),
                        txtPassword.getText().replaceAll("\\s+", ""));

                txtUsername.setText("");
                txtPassword.setText("");

                model.setAccount(account);
                if (account.getType() == AccountType.DOCTOR) {
                    try {
                        Doctor doctor = new Doctor(model);
                        Stage child = new Stage(StageStyle.UNDECORATED);
                        child.setScene(new Scene(doctor));
                        child.show();

                        doctor.setStage(child);
                        doctor.setParentStage(this.getStage());
                        model.setViewController(doctor);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (account.getType() == AccountType.SECRETARY) {
                    /*try {
                        Secretary secretary = new Secretary(model);
                        Stage child = new Stage(StageStyle.UNDECORATED);
                        child.setScene(new Scene(secretary));
                        child.show();

                        secretary.setStage(child);
                        secretary.setParentStage(this.getStage());
                        model.setViewController(secretary);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/
                } else if (account.getType() == AccountType.CLIENT) {
                    try {
                        Client client = new Client(model);
                        Stage child = new Stage(StageStyle.UNDECORATED);
                        child.setScene(new Scene(client));
                        child.show();

                        client.setStage(child);
                        client.setParentStage(this.getStage());
                        model.setViewController(client);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                model.setThread(new ClientThread(model));
                model.getThread().start();

                this.hide();
            } catch (Exception e) {
                lblAlert.setText(e.getMessage());
            }
        });
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Model getModel() {
        return model;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return stage;
    }

    public void hide () {
        this.stage.hide();
    }

    public void show () {
        this.stage.show();
        this.stage.toFront();
    }
}
