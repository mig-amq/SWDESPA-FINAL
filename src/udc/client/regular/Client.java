package udc.client.regular;

import javafx.scene.image.Image;
import udc.Model;
import udc.client.regular.FXMLControllers.ClientController;
import udc.doctor.Doctor;
import udc.objects.enums.PanelType;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Locale;

public class Client extends Doctor {

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
        super.init();
        this.initPanel(PanelType.CLIENT);

        this.getTitle().setText("Client - " +
                this.getCalendar().selectedProperty().getValue()
                        .format(DateTimeFormatter.ofPattern("LLLL dd, uuuu (E)", this.getLocale())));

        this.getCalendar().selectedProperty().addListener((observable, oldValue, newValue) -> {
            String date = newValue.format(DateTimeFormatter.ofPattern("LLLL dd, uuuu (E)", this.getLocale()));
            this.getTitle().setText("Client - " + date);
        });
    }

    @Override
    public void update() {

    }

    @Override
    public void setModel(Model model) {
        super.setModel(model);

        this.username.setText("" + model.getAccount().getFirstName() + " " + model.getAccount().getLastName());
        this.img.setImage(new Image(new ByteArrayInputStream(Base64.getDecoder().decode(model.getAccount().getImageURI()))));
    }
}
