package udc.secretary;


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
import udc.customfx.calendar.Calendar;
import udc.customfx.drawerpanel.DrawerPanel;
import udc.customfx.paneledview.PaneledView;
import udc.doctor.Doctor;
import udc.notifier.AppointmentNotifier;
import udc.objects.account.Account;
import udc.objects.enums.PanelType;
import udc.secretary.Controller.MainController;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Locale;

public class Secretary extends PaneledView {
    MainController mainController;
    protected Label username;
    protected ImageView img;

    private Calendar calendar;
    private AnchorPane userPane;
    private AnchorPane calPane;

    private DrawerPanel drawerPane;
    public Secretary(double width, double height, Locale lang) throws IOException {
        super(width, height, lang);

    }

    public Secretary(double width, double height) throws IOException {
        super(width, height);
    }

    public Secretary () throws Exception {
        super(800, 650);
    }

    public Secretary(Model model) throws IOException {
        super(800, 650);
        //this.setAccount(account);
        setModel(model);
        try {
            mainController = new MainController(contentPane, pnlTool, model, calendar);
            calendar.selectedProperty().addListener((observable, oldValue, newValue) -> {
                String date = newValue.format(DateTimeFormatter.ofPattern("LLLL dd, uuuu (E)", this.getLocale()));
                if (mainController.isCalendarRdBtnSelected())
                    mainController.calendarViewCondition();
                else if (mainController.isAgendaRdBtnSelected())
                    mainController.agendaViewCondition();
                this.getTitle().setText("Secretary - " + date);
            });
        }catch (Exception e){
            e.printStackTrace();
        }   
    }

    @Override
    public void init() {
        initEr();
        this.initPanel(PanelType.SECRETARY);

        this.getTitle().setText("Secretary - " +
               calendar.selectedProperty().getValue()
                       .format(DateTimeFormatter.ofPattern("LLLL dd, uuuu (E)", this.getLocale())));
    }

    @Override
    public void update() {
        try{
            mainController.updateData();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void initEr(){
        this.initPanel(PanelType.SECRETARY);

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
        } catch (IOException e) {
            e.printStackTrace();
        }


        this.getDrawer().setDefaultDrawerSize(this.drawerPane.getDrawerWidth() - 25);
        this.getDrawer().setSidePane(this.drawerPane);
        this.getDrawer().setContent(contentPane);
    }

//    public Account getAccount(){
//        return account;
//    }
//
//
    public void setModel(Model account) {
        super.setModel(account);
        this.username.setText("Sec. " + account.getAccount().getFirstName() + " " + account.getAccount().getLastName());
        this.img.setImage(new Image(new ByteArrayInputStream(Base64.getDecoder().decode(account.getAccount().getImageURI()))));
    }
}
