package udc.objects.time.concrete;

import udc.objects.enums.UnavailabilityType;

public class Unavailable extends Agenda {
    private UnavailabilityType unavailabilityType;
    private String doctorName;

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
