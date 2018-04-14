package udc.objects.time.concrete;

public class Appointment extends Agenda {
    private String clientName;
    private String doctorName;

    public String getClientName() {
        return clientName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }
}
