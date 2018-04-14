package udc.objects.account;

import udc.objects.enums.AccountType;
import udc.objects.time.concrete.Unavailable;

import java.util.ArrayList;

public class Doctor extends Account {
    private ArrayList<Unavailable> unavailables;

    public Doctor(int id, String firstName, String lastName) {
        super(id, firstName, lastName);

        this.setType(AccountType.DOCTOR);
        this.setUnavailables(new ArrayList<>());
    }

    public void setUnavailables(ArrayList<Unavailable> unavailables) {
        this.unavailables = unavailables;
    }

    public ArrayList<Unavailable> getUnavailables() {
        return unavailables;
    }
}
