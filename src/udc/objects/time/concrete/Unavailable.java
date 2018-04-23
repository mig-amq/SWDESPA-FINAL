package udc.objects.time.concrete;

import udc.objects.enums.UnavailabilityType;

import java.util.ArrayList;

public class Unavailable extends Agenda {

    private UnavailabilityType unavailabilityType;
    private String doctorName;
    private String recurringDays;

    public void setRecurringDays(String recurringDays){this.recurringDays = recurringDays;}

    public String getRecurringDays(){return recurringDays;}

    public UnavailabilityType getUnavailabilityType() {
        return unavailabilityType;
    }

    public void setUnavailabilityType(UnavailabilityType unavailabilityType) {
        this.unavailabilityType = unavailabilityType;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorName() {
        return doctorName;
    }

}
