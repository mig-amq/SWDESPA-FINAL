package udc.objects.time.concrete;

import udc.objects.enums.UnavailabilityType;

public class Unavailable extends Agenda {
    private UnavailabilityType unavailabilityType;

    public UnavailabilityType getUnavailabilityType() {
        return unavailabilityType;
    }

    public void setUnavailabilityType(UnavailabilityType unavailabilityType) {
        this.unavailabilityType = unavailabilityType;
    }
}
