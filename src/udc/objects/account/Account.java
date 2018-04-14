package udc.objects.account;

import udc.objects.enums.AgendaType;
import udc.objects.time.concrete.Agenda;
import udc.objects.time.concrete.Appointment;
import udc.objects.enums.AccountType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    /**
     * Returns an {@link Appointment} set between two sets of {@link LocalDateTime} (inclusive)
     * @param start - the minimum limit
     * @param end - the maximum limit
     * @return an {@link Appointment} found between the two limits; else if none is found return null
     */
    public Agenda appointmentAtTime (LocalDateTime start, LocalDateTime end) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm a");
        DateTimeFormatter dtf0 = DateTimeFormatter.ofPattern("hh:mm a");

        for (Agenda a : this.appointmentsAtDay(start)) {
            if (a.getStartTime().isAfter(start) || a.getStartTime().isEqual(start) && a.getEndTime().isBefore(end) || a.getEndTime().isEqual(end))
                return a;
            else if (a.getType() == AgendaType.RECURRING) {
                LocalDateTime tempStart = start.withDayOfMonth(1).withDayOfYear(1).withYear(1);
                LocalDateTime tempEnd = end.withDayOfMonth(1).withDayOfYear(1).withYear(1);

                LocalDateTime tempA0 = a.getStartTime().withDayOfMonth(1).withDayOfYear(1).withYear(1);
                LocalDateTime tempA1 = a.getEndTime().withDayOfMonth(1).withDayOfYear(1).withYear(1);

                if (tempA0.isAfter(tempStart) || tempA0.isEqual(tempStart) && tempA1.isBefore(tempEnd) || tempA1.isEqual(tempEnd))
                    return a;
            }
        }

        return null;
    }

    /**
     * Returns all the {@link Appointment} on a given day
     * @param day - the LocalDateTime containing the day to look for
     * @return an ArrayList of {@link Appointment}
     */
    public ArrayList<Appointment> appointmentsAtDay (LocalDateTime day) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        ArrayList<Appointment> temp = new ArrayList<>();

        synchronized (this.getAppointments()) {
            for (Appointment a : this.getAppointments()) {
                if (a.getStartTime().format(dtf).equals(day.format(dtf)))
                    temp.add(a);
            }
        }

        return temp;
    }

    /**
     * Returns an ArrayList filled with {@link Appointment} for the week.
     * This function makes sure to start at the beginning of the week {@link java.time.DayOfWeek#MONDAY} no matter
     * the parameter
     * @param day - the day selected
     * @return an ArrayList of all the {@link Appointment}s in the week
     */
    public ArrayList<Appointment> appointmentsAtWeek(LocalDateTime day) {
        ArrayList<Appointment> temp = new ArrayList<>();

        if (day.getDayOfWeek().getValue() > 1)
            day.minusDays(day.getDayOfWeek().getValue() - 1);

        for (int i = 0; i < 7; i++) {
            temp.addAll(this.appointmentsAtDay(day));

            day.plusDays(i);
        }

        return temp;
    }

    /*** SETTERS AND GETTERS ***/
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
