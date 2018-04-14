package udc.objects.account;

import udc.objects.time.concrete.Appointment;
import udc.objects.enums.AccountType;

import java.util.ArrayList;

public abstract class Account {
    private int id;
    private AccountType type;
    private String imageURI;
    private String firstName, lastName;
    private ArrayList<Appointment> appointments;

    public Account (int id, String firstName, String lastName) {
        this.setId(id);
        this.setFirstName(firstName);
        this.setLastName(lastName);

        this.setAppointments(new ArrayList<>());
        this.setImageURI("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sAUWFhZqMBh89rOtzc0aj8e1uLAw5anmz3g8ru3t7To6Ojrx31yzHxolWC1w48aN+vjx44mfv9/amuI0nKTb7dbi4mLTY8w1p4QtsLK6Wleu2BVtt3Z7zQOnDfNX0gLdbrfu3LnT9Bicotvt1vq6fdQ0wWqJ79bX68bNm02PwQkePXpUvZ4rKE0TrJbodDr15MmTuilardLpdOrR48e1vLLS9ChUVWdje3fc9BD833g8rvdbW7WxsXHq3Sou3/UbN+r+/fvV7/ebHoU/CVZLHR8f1+7ubu3//nsNh8M6HttN09Drdmux36/l5WUvlreQYAExXMMCYggWEEOwgBiCBcQQLCCGYAExBAuIIVhADMECYggWEEOwgBiCBcQQLCCGYAExBAuIIVhADMECYggWEEOwgBiCBcQQLCCGYAExBAuIIVhADMECYggWEEOwgBiCBcQQLCCGYAExBAuIIVhADMECYggWEEOwgBiCBcQQLCCGYAExBAuIIVhADMECYggWEEOwgBiCBcQQLCCGYAExBAuIIVhADMECYggWEEOwgBiCBcQQLCCGYAExBAuIIVhADMECYggWEEOwgBiCBcQQLCCGYAExBAuIIVhADMECYggWEEOwgBiCBcQQLCCGYAExBAuIIVhADMECYggWEEOwgBiCBcQQLCCGYAExBAuIIVhADMECYggWEEOwgBiCBcQQLCCGYAExBAuIIVhADMECYggWEEOwgBiCBcQQLCCGYAExBAuIIVhADMECYggWEEOwgBiCBcQQLCCGYAExBAuIIVhADMECYggWEOO/jJ5izESHF+4AAAAASUVORK5CYII=");
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setImageURI(String imageURI) {
        this.imageURI = imageURI;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAppointments(ArrayList<Appointment> appointments) {
        this.appointments = appointments;
    }

    public AccountType getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public String getImageURI() {
        return imageURI;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }
}
