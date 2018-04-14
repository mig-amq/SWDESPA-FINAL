package udc.secretary;

import javafx.scene.image.Image;
import udc.Model;
import udc.doctor.Doctor;
import udc.objects.enums.PanelType;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Locale;

public class Secretary extends Doctor {
//    MainController mainController;
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
        this.setModel(model);

        try {
//            mainController = new MainController(contentPane, pnlTool, model, getCalendar());
        }catch (Exception e){
            e.printStackTrace();
        }   
    }

    @Override
    public void init() {
       super.init();
       this.initPanel(PanelType.SECRETARY);

       this.getTitle().setText("Secretary - " +
               this.getCalendar().selectedProperty().getValue()
                       .format(DateTimeFormatter.ofPattern("LLLL dd, uuuu (E)", this.getLocale())));

       this.getCalendar().selectedProperty().addListener((observable, oldValue, newValue) -> {
           String date = newValue.format(DateTimeFormatter.ofPattern("LLLL dd, uuuu (E)", this.getLocale()));
           this.getTitle().setText("Secretary - " + date);
       });
    }

    @Override
    public void update() {
        try{
//            mainController.updateData();
        }catch (Exception e){

        }
    }

    @Override
    public void setModel(Model model) {
        super.setModel(model);

        this.username.setText("Sec. " + model.getAccount().getFirstName() + " " + model.getAccount().getLastName());
        this.img.setImage(new Image(new ByteArrayInputStream(Base64.getDecoder().decode(model.getAccount().getImageURI()))));
    }
}
