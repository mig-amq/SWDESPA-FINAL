package udc.customfx.paneledview;

import udc.Model;
import udc.objects.enums.PanelType;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;

public abstract class PaneledView extends AnchorPane{
    private Locale locale;
    private double x, y;
    private boolean drawerOpen;
    private Model model;
    private Stage parentStage, stage;

    @FXML
    private JFXDrawer drawer;

    @FXML
    private JFXHamburger burger;

    @FXML
    protected AnchorPane mainPanel, pnlTool, contentPane, close, minimize;

    @FXML
    private Label title;

    @FXML
    public void initialize() {
        drawerOpen = false;

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

        HamburgerBackArrowBasicTransition closeTransition = new HamburgerBackArrowBasicTransition(burger);

        closeTransition.setRate(-1);

        burger.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            closeTransition.setRate(closeTransition.getRate()*-1);
            closeTransition.play();

            drawerOpen = !drawerOpen;
            drawer.toggle();
        });

        drawer.setOnDrawerClosing(event -> {
            if (drawerOpen) {
                closeTransition.setRate(closeTransition.getRate()*-1);
                closeTransition.play();
            }
        });

    }

    public PaneledView(double width, double height, Locale lang) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../customfx/paneledview/PaneledView.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();

        this.setPrefSize(width, height);
        this.setLocale(lang);
        this.init();
    }

    public PaneledView(double width, double height) throws IOException {
        this(width, height, Locale.ENGLISH);
    }

    public void initPanel (PanelType type) {
        this.drawer.close();
        switch (type) {
            case DOCTOR:
                this.mainPanel.getStylesheets().add(getClass().getResource("../res/styles/doctor.css").toString());
                this.title.setText("Doctor");
                break;
            case SECRETARY:
                this.mainPanel.getStylesheets().add(getClass().getResource("../res/styles/secretary.css").toString());
                this.title.setText("Secretary");
                break;
            case CLIENT:
                this.mainPanel.getStylesheets().add(getClass().getResource("../res/styles/client.css").toString());
                this.title.setText("Client");
                break;
            default:

        }
    }

    public abstract void init();

    public abstract void update();

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public void setTitle(Label title) {
        this.title = title;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public void setParentStage(Stage parentStage) {
        this.parentStage = parentStage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Locale getLocale() {
        return locale;
    }

    public JFXDrawer getDrawer() {
        return drawer;
    }

    public Label getTitle() {
        return title;
    }

    public Model getModel() {
        return model;
    }

    public Stage getParentStage() {
        return parentStage;
    }

    public Stage getStage() {
        return stage;
    }
}
