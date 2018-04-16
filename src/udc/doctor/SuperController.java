package udc.doctor;

import UDC.Model;

import java.time.LocalDateTime;

public abstract class SuperController {
    private Model model;

    public void setModel(Model model) {
        this.model = model;
    }

    public Model getModel() {
        return model;
    }

    public abstract void update(LocalDateTime ldt);
}
