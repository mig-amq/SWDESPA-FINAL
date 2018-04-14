package udc.objects.account;

import udc.objects.enums.AccountType;
import udc.objects.enums.AgendaType;
import udc.objects.time.concrete.Appointment;
import udc.objects.time.concrete.Unavailable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Doctor extends Account {
    private ArrayList<Unavailable> unavailables;

    public Doctor(String firstName, String lastName, int id) {
        super(id, firstName, lastName);

        this.setType(AccountType.DOCTOR);
        this.setUnavailables(new ArrayList<>());
    }

    /**
     * Checks if a range of time has no appointments or no unavailabilities
     * @param start
     * @param end
     * @return
     */
    public boolean isAvailable (LocalDateTime start, LocalDateTime end) {
        boolean unavailable = true;
        for (Unavailable u : this.getUnavailables())
            if (u.getStartTime().isAfter(start) || u.getStartTime().isEqual(start) && u.getEndTime().isBefore(end) || u.getEndTime().isEqual(end))
                unavailable = false;
            else if (u.getType() == AgendaType.RECURRING) {
                LocalDateTime tempStart = start.withDayOfMonth(1).withDayOfYear(1).withYear(1);
                LocalDateTime tempEnd = end.withDayOfMonth(1).withDayOfYear(1).withYear(1);

                LocalDateTime tempA0 = u.getStartTime().withDayOfMonth(1).withDayOfYear(1).withYear(1);
                LocalDateTime tempA1 = u.getEndTime().withDayOfMonth(1).withDayOfYear(1).withYear(1);

                if (tempA0.isAfter(tempStart) || tempA0.isEqual(tempStart) && tempA1.isBefore(tempEnd) || tempA1.isEqual(tempEnd))
                    unavailable = false;
            }
        return this.appointmentAtTime(start, end) == null && !unavailable;
    }

    public void setUnavailables(ArrayList<Unavailable> unavailables) {
        this.unavailables = unavailables;
    }

    public ArrayList<Unavailable> getUnavailables() {
        return unavailables;
    }
}
