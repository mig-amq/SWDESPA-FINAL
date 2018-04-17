package udc.objects.time.concrete;

public class Available extends Agenda {
    private String doctorName;

    public void setDoctorName(String doctorName){
        this.doctorName = doctorName;
    }

    public String getDoctorName(){
        return doctorName;
    }
}
