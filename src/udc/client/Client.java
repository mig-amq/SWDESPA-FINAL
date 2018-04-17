package udc.client;

import com.jfoenix.controls.JFXButton;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import udc.Model;
//import udc.client.regular.FXMLControllers.ClientController;
import udc.client.regular.FXMLControllers.ClientController;
import udc.customfx.calendar.Calendar;
import udc.customfx.drawerpanel.DrawerPanel;
import udc.customfx.paneledview.PaneledView;
import udc.objects.enums.PanelType;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Locale;

public class Client extends PaneledView {
    protected Label username;
    protected ImageView img;

    private Calendar calendar;
    private AnchorPane userPane;
    private AnchorPane calPane;

    private DrawerPanel drawerPane;

    private ClientController clientController;

    public Client(double width, double height, Locale lang) throws IOException {
        super(width, height, lang);

    }

    public Client(double width, double height) throws IOException {
        super(width, height);
    }

    public Client () throws Exception {
        super(800, 650);
    }

    public Client(Model model) throws IOException {
        super(800, 650);
        this.setModel(model);

        try {
            contentPane.getChildren().clear();
            clientController = new ClientController(this, model);
            contentPane.getChildren().add(clientController);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void init() {
        this.initPanel(PanelType.CLIENT);

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

        try {
            AnchorPane buttonPanel = new AnchorPane();
            JFXButton btnLogout = new JFXButton("Log Out");
            btnLogout.setLayoutX(this.drawerPane.getDrawerWidth() / 2 - 75);
            btnLogout.setOnAction(event -> {
                if (this.getModel().getAccount() != null) {
                    if (this.getModel().getThread() != null) {
                        this.getModel().getThread().off();
                    }

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
            this.getTitle().setText("Client - " +
                    this.calendar.selectedProperty().getValue()
                            .format(DateTimeFormatter.ofPattern("LLLL dd, uuuu (E)", this.getLocale())));

            this.calendar.selectedProperty().addListener((observable, oldValue, newValue) -> {
                String date = newValue.format(DateTimeFormatter.ofPattern("LLLL dd, uuuu (E)", this.getLocale()));
                this.getTitle().setText("Client - " + date);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }


        this.getDrawer().setDefaultDrawerSize(this.drawerPane.getDrawerWidth() - 25);
        this.getDrawer().setSidePane(this.drawerPane);
        this.getDrawer().setContent(contentPane);

        try {
            this.getTitle().setText("Client - " +
                    this.getCalendar().selectedProperty().getValue()
                            .format(DateTimeFormatter.ofPattern("LLLL dd, uuuu (E)", this.getLocale())));

            this.getCalendar().selectedProperty().addListener((observable, oldValue, newValue) -> {
                String date = newValue.format(DateTimeFormatter.ofPattern("LLLL dd, uuuu (E)", this.getLocale()));
                this.getTitle().setText("Client - " + date);
            });
        } catch (Exception e) {}
    }

    @Override
    public void update() {
        if (clientController != null) {
            clientController.update();
        }
    }

    @Override
    public void setModel(Model model) {
        super.setModel(model);

        this.username.setText(model.getAccount().getFirstName() + " " + model.getAccount().getLastName());
        this.img.setImage(new Image(new ByteArrayInputStream(Base64.getDecoder().decode(model.getAccount().getImageURI()))));
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }
}
