package udc.objects.time.concrete;

public class Available extends Agenda {
    private String doctorName;
    private String recurringDays;

    public void setRecurringDays(String recurringDays){this.recurringDays = recurringDays;}

    public String getRecurringDays(){return recurringDays;}

    public void setDoctorName(String doctorName){
        this.doctorName = doctorName;
    }

    public String getDoctorName(){
        return doctorName;
    }
}
