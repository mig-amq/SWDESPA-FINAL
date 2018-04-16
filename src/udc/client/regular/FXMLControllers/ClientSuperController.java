package udc.client.regular.FXMLControllers;

import javafx.fxml.Initializable;
import udc.Model;

public abstract class ClientSuperController implements Initializable {
    private Model model;


    public void setModel(Model model) {
        this.model = model;
    }
    public abstract void insertFilterData();

}
