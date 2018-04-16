package udc.doctor;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import udc.Model;
import udc.customfx.calendar.Calendar;
import udc.customfx.drawerpanel.DrawerPanel;
import udc.customfx.paneledview.PaneledView;
import udc.doctor.controllers.DoctorController;
import udc.notifier.AppointmentNotifier;
import udc.objects.account.Account;
import udc.objects.enums.PanelType;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Locale;

public class Doctor extends PaneledView {

    protected Label username;
    protected ImageView img;

    private Calendar calendar;
    private AnchorPane userPane;
    private AnchorPane calPane;
    private DoctorController dc;

    private DrawerPanel drawerPane;
    protected AppointmentNotifier notifier;

    public Doctor(double width, double height, Locale lang) throws IOException {
        super(width, height, lang);
    }

    public Doctor(double width, double height) throws IOException {
        super(width, height);
    }

    public Doctor(Model model) throws IOException {
        super(800, 650);
        this.setModel(model);
    }

    @Override
    public void init() {
        this.initPanel(PanelType.DOCTOR);

        this.drawerPane = new DrawerPanel(265, 650, false);
        userPane = new AnchorPane();
        userPane.setPrefHeight(200);

        img = new ImageView();
        img.setClip(new Circle(50, 50, 50));
        img.setFitHeight(100);
        img.setFitWidth(100);
        img.setLayoutX(drawerPane.getDrawerWidth() / 2 - img.getFitWidth() / 2);
        img.setLayoutY(15);

        Circle c = new Circle(50, 50, 52, Paint.valueOf("#ffffff"));
        c.setLayoutX(img.getLayoutX());
        c.setLayoutY(img.getLayoutY());

        username = new Label();
        username.setFont(Font.font("Open Sans", FontWeight.LIGHT, 18));
        username.setTextFill(Paint.valueOf("#ffffff"));

        username.setLayoutX(0);
        username.setLayoutY(c.getLayoutY() + 110);
        username.setMinWidth(drawerPane.getDrawerWidth());
        username.setAlignment(Pos.CENTER);

        userPane.getChildren().add(c);
        userPane.getChildren().add(img);
        userPane.getChildren().add(username);

        calPane = new AnchorPane();
        calPane.setPrefHeight(255);

        dc = new DoctorController();

        try {
            AnchorPane buttonPanel = new AnchorPane();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/Doctor.fxml"));
            this.contentPane.getChildren().add(loader.load());

            dc = loader.<DoctorController>getController();
            dc.setModel(this.getModel());
            dc.setCalendar(this.calendar);

            JFXButton test = new JFXButton("Test Server");
            test.setLayoutX(this.drawerPane.getDrawerWidth() / 2 - 75);
            test.setLayoutY(60);

            test.setMinWidth(150);
            test.setOnAction(event -> {
                if (this.getModel().getThread() != null && this.getModel().getThread().isStarted())
                    this.getModel().setState();
            });

            buttonPanel.getChildren().add(test);
            JFXButton btnLogout = new JFXButton("Log Out");
            btnLogout.setLayoutX(this.drawerPane.getDrawerWidth() / 2 - 75);
            btnLogout.setOnAction(event -> {
                if (this.getModel().getAccount() != null && this.notifier != null && this.notifier.isStarted()) {
                    if (this.getModel().getThread() != null) {
                        this.getModel().getThread().off();
                    }

                    this.notifier.off();

                    if (this.getParentStage() != null) {
                        this.getParentStage().show();
                        this.getParentStage().toFront();
                        this.getStage().close();
                    }
                }
            });

            buttonPanel.getChildren().add(btnLogout);

            this.calendar = new Calendar(250, 250, this.getLocale());
            this.calendar.setLayoutX(5);
            this.calendar.setLayoutY(12.5);
            this.calPane.getChildren().add(this.calendar);

            this.drawerPane.add(userPane);
            this.drawerPane.add(buttonPanel);

            this.drawerPane.add(drawerPane.SPACER(180));
            this.drawerPane.add(calPane);
            this.getTitle().setText("Doctor - " +
                    this.calendar.selectedProperty().getValue()
                            .format(DateTimeFormatter.ofPattern("LLLL dd, uuuu (E)", this.getLocale())));

            this.calendar.selectedProperty().addListener((observable, oldValue, newValue) -> {
                String date = newValue.format(DateTimeFormatter.ofPattern("LLLL dd, uuuu (E)", this.getLocale()));
                this.getTitle().setText("Doctor - " + date);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }


        this.getDrawer().setDefaultDrawerSize(this.drawerPane.getDrawerWidth() - 25);
        this.getDrawer().setSidePane(this.drawerPane);
        this.getDrawer().setContent(contentPane);
    }

    @Override
    public void update() {

    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    @Override
    public void setModel(Model model) {
        super.setModel(model);

        this.username.setText("Dr. " + model.getAccount().getFirstName() + " " + model.getAccount().getLastName());
        this.img.setImage(new Image(new ByteArrayInputStream(Base64.getDecoder().decode(model.getAccount().getImageURI()))));
        this.notifier = new AppointmentNotifier(this.getModel().getAccount());
        this.notifier.start();

    }
}
